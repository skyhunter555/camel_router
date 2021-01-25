package ru.syntez.camel.router.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.Data;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.util.ResourceUtils;
import ru.syntez.camel.router.test.entities.AddressModel;
import ru.syntez.camel.router.test.entities.AddressModelExt;
import ru.syntez.camel.router.test.entities.AddressModelRequest;
import java.io.File;
import java.io.IOException;

public class OrikaTest {

    private final static MapperFacade mapperOrikaFromRequest;

    static {
        final MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.classMap(AddressModelRequest.class, AddressModel.class)
                .field("id", "addressId")
                .field("city", "addressCity")
                .field("street", "addressStreet")
                .field("additionalData[0].value", "rateValue1")
                .mapNulls(false) // TODO ???
                .field("additionalData[1].value", "rateValue2")
                .byDefault()
                .register();
        mapperOrikaFromRequest = mapperFactory.getMapperFacade();
    }

    private final static MapperFacade mapperOrikaToRequest;
    static {
        final MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.classMap(AddressModel.class, AddressModelRequest.class)
                .field("addressId", "id")
                .field("addressCity", "city")
                .field("addressStreet", "street")
                .field("rateValue1", "additionalData[0].value")
                .byDefault()
                .register();
        mapperOrikaToRequest = mapperFactory.getMapperFacade();
    }

    private final static MapperFacade mapperWrapperToRequest;
    static {
        final MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.classMap(AddressWrapper.class, AddressModelRequest.class)
                .field("addressModel.addressId", "id")
                .field("addressModel.addressCity", "city")
                .field("addressModel.addressStreet", "street")
                .field("addressModel.rateValue1", "additionalData[0].value")
                .field("addressModelExt.addressCountry", "country")
                .field("addressModelExt.addressState", "state")
                .field("addressModelExt.addressDistrict", "district")
                .field("addressModelExt.houseNumber", "houseNumber")
                .byDefault()
                .register();
        mapperWrapperToRequest = mapperFactory.getMapperFacade();
    }

    private ObjectMapper jsonMapper = new ObjectMapper();

    private final static ObjectMapper xmlMapper;
    static {
        JacksonXmlModule xmlModule = new JacksonXmlModule();
        xmlModule.setDefaultUseWrapper(false);
        xmlMapper = new XmlMapper(xmlModule);
    }

    @Test
    public void fromJSONToModelTest() {
        try {

            File fileJson = ResourceUtils.getFile(this.getClass().getResource("/addressRequest.json"));
            File fileXml = ResourceUtils.getFile(this.getClass().getResource("/addressModel.xml"));
            AddressModelRequest requestJson = jsonMapper.readValue(fileJson, AddressModelRequest.class);
            AddressModel expectedModelXml = xmlMapper.readValue(fileXml, AddressModel.class);

            AddressModel addressModel = mapperOrikaFromRequest.map(requestJson, AddressModel.class);

            Assert.assertEquals(expectedModelXml, addressModel);
            Assert.assertEquals(Integer.valueOf(123), addressModel.getAddressId());
            Assert.assertEquals("Ропшинское сельское поселение", addressModel.getAddressCity());
            Assert.assertEquals("Тихий переулок", addressModel.getAddressStreet());
            Assert.assertEquals(Integer.valueOf(500), addressModel.getRateValue1());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Data
    public class AddressWrapper {
        private AddressModel addressModel;
        private AddressModelExt addressModelExt;
        AddressWrapper(AddressModel addressModel, AddressModelExt addressModelExt) {
            this.addressModel = addressModel;
            this.addressModelExt = addressModelExt;
        }
    }

    @Test
    public void fromXMLToModelTest() {

        try {

            File fileJson = ResourceUtils.getFile(this.getClass().getResource("/addressRequest.json"));
            File fileXml = ResourceUtils.getFile(this.getClass().getResource("/addressModel.xml"));
            File fileExtXml = ResourceUtils.getFile(this.getClass().getResource("/addressModelExt.xml"));
            AddressModel modelXml = xmlMapper.readValue(fileXml, AddressModel.class);
            AddressModelExt modelExtXml = xmlMapper.readValue(fileExtXml, AddressModelExt.class);
            AddressModelRequest expectedRequestJson = jsonMapper.readValue(fileJson, AddressModelRequest.class);

            //Отдельный доп. класс для объединения сущностей
            AddressWrapper address = new AddressWrapper(modelXml, modelExtXml);

            long startTime = System.currentTimeMillis();
            System.out.println("Starting transformation: " + startTime);

            AddressModelRequest request = new AddressModelRequest();
            for (int i = 0; i < 1000000; i++) {
                request = mapperWrapperToRequest.map(address, AddressModelRequest.class);
            }
            long finishTime = System.currentTimeMillis();
            System.out.println("Transformated all: " + finishTime);
            System.out.println("Total time: " + (finishTime - startTime) + " ms.");

            request.setLabel("Тестовый адрес JSON");
            request.getAdditionalData().get(0).setKey("тариф");

            Assert.assertEquals(expectedRequestJson, request);
            Assert.assertEquals(request.getId(), Integer.valueOf(123));
            Assert.assertEquals(request.getCity(), "Ропшинское сельское поселение");
            Assert.assertEquals(request.getStreet(), "Тихий переулок");
            Assert.assertEquals(request.getAdditionalData().get(0).getValue(), "500");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
