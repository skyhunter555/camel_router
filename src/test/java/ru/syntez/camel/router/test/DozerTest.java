package ru.syntez.camel.router.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.util.ResourceUtils;
import ru.syntez.camel.router.test.entities.AddressModel;
import ru.syntez.camel.router.test.entities.AddressModelRequest;

import java.io.File;
import java.io.IOException;

public class DozerTest {


    private ObjectMapper jsonMapper = new ObjectMapper();

    private final static ObjectMapper xmlMapper;
    static {
        JacksonXmlModule xmlModule = new JacksonXmlModule();
        xmlModule.setDefaultUseWrapper(false);
        xmlMapper = new XmlMapper(xmlModule);
    }

    /*
    @Test
    public void fromJSONToModelTest() {
        try {

            File fileJson = ResourceUtils.getFile(this.getClass().getResource("/AddressRequest.json"));
            File fileXml = ResourceUtils.getFile(this.getClass().getResource("/AddressModel.xml"));
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

    @Test
    public void fromXMLToModelTest() {

        try {

            File fileJson = ResourceUtils.getFile(this.getClass().getResource("/AddressRequest.json"));
            File fileXml = ResourceUtils.getFile(this.getClass().getResource("/AddressModel.xml"));
            AddressModel modelXml = xmlMapper.readValue(fileXml, AddressModel.class);
            AddressModelRequest expectedRequestJson = jsonMapper.readValue(fileJson, AddressModelRequest.class);

            AddressModelRequest request = mapperOrikaToRequest.map(modelXml, AddressModelRequest.class);
            request.setLabel("Тестовый адрес JSON");
            request.setCountry("Россия");
            request.setState("Ленинградская область");
            request.setHouseNumber("7");
            request.setDistrict("деревня Михайловская");
            request.getAdditionalData().get(0).setKey("тариф");

            Assert.assertEquals(expectedRequestJson, request);
            Assert.assertEquals(request.getId(), Integer.valueOf(123));
            Assert.assertEquals(request.getCity(), "Ропшинское сельское поселение");
            Assert.assertEquals(request.getStreet(), "Тихий переулок");
            Assert.assertEquals(request.getAdditionalData().get(0).getValue(), Integer.valueOf(500));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

}
