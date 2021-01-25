package ru.syntez.camel.router.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.Data;
import org.junit.Assert;
import org.junit.Test;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.springframework.util.ResourceUtils;
import ru.syntez.camel.router.test.entities.AdditionalData;
import ru.syntez.camel.router.test.entities.AddressModel;
import ru.syntez.camel.router.test.entities.AddressModelExt;
import ru.syntez.camel.router.test.entities.AddressModelRequest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapStructTest {

    private ObjectMapper jsonMapper = new ObjectMapper();

    private final static ObjectMapper xmlMapper;
    static {
        JacksonXmlModule xmlModule = new JacksonXmlModule();
        xmlModule.setDefaultUseWrapper(false);
        xmlMapper = new XmlMapper(xmlModule);
    }

    public interface Converter {
        AddressModel convert(AddressModelRequest addressModelRequest);
        AddressModelRequest convert(AddressModel  addressModel, AddressModelExt addressModelExt);
    }

    @Mapper
    public interface MapStructConverter extends Converter {
        MapStructConverter MAPPER = Mappers.getMapper(MapStructConverter.class);

        @Mappings({
            @Mapping(source="id", target="addressId"),
            @Mapping(source="city", target="addressCity"),
            @Mapping(source="street", target="addressStreet"),
            @Mapping(source="additionalData", target="rateValue1")
        })
        @Override
        AddressModel convert(AddressModelRequest addressModelRequest);

        default Integer map(List<AdditionalData> additionalData) {
            return Integer.valueOf(additionalData.get(0).getValue());
        }

        default List<AdditionalData> map(Integer rateValue) {
            List<AdditionalData> additionalDataList = new ArrayList<>();
            AdditionalData additionalData = new AdditionalData();
            additionalData.setValue("500");
            additionalDataList.add(additionalData);
            return additionalDataList;
        }

        @Mappings({
            @Mapping(source="addressModel.addressId",           target="id"),
            @Mapping(source="addressModel.addressCity",         target="city"),
            @Mapping(source="addressModel.addressStreet",       target="street"),
            @Mapping(source="addressModel.rateValue1",          target="additionalData"),
            @Mapping(source="addressModelExt.addressCountry",   target="country"),
            @Mapping(source="addressModelExt.addressState",     target="state"),
            @Mapping(source="addressModelExt.addressDistrict",  target="district"),
            @Mapping(source="addressModelExt.houseNumber",      target="houseNumber")
        })
        @Override
        AddressModelRequest convert(AddressModel addressModel, AddressModelExt addressModelExt);
    }

    @Test
    public void fromJSONToModelTest() {
        try {

            File fileJson = ResourceUtils.getFile(this.getClass().getResource("/addressRequest.json"));
            File fileXml = ResourceUtils.getFile(this.getClass().getResource("/addressModel.xml"));
            AddressModelRequest requestJson = jsonMapper.readValue(fileJson, AddressModelRequest.class);
            AddressModel expectedModelXml = xmlMapper.readValue(fileXml, AddressModel.class);

            AddressModel addressModel = MapStructConverter.MAPPER.convert(requestJson);

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

            File fileJson = ResourceUtils.getFile(this.getClass().getResource("/addressRequest.json"));
            File fileXml = ResourceUtils.getFile(this.getClass().getResource("/addressModel.xml"));
            File fileExtXml = ResourceUtils.getFile(this.getClass().getResource("/addressModelExt.xml"));
            AddressModel modelXml = xmlMapper.readValue(fileXml, AddressModel.class);
            AddressModelExt modelExtXml = xmlMapper.readValue(fileExtXml, AddressModelExt.class);
            AddressModelRequest expectedRequestJson = jsonMapper.readValue(fileJson, AddressModelRequest.class);

            long startTime = System.currentTimeMillis();
            System.out.println("Starting transformation: " + startTime);

            AddressModelRequest request = new AddressModelRequest();
            for (int i = 0; i < 1000000; i++) {
                request = MapStructConverter.MAPPER.convert(modelXml, modelExtXml);
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
