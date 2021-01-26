package ru.syntez.camel.router.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.inspiresoftware.lib.dto.geda.adapter.ValueConverter;
import com.inspiresoftware.lib.dto.geda.assembler.Assembler;
import com.inspiresoftware.lib.dto.geda.assembler.DTOAssembler;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.util.ResourceUtils;
import ru.syntez.camel.router.test.entities.*;
import ru.syntez.camel.router.test.utils.AdditionalDataConverter;
import ru.syntez.camel.router.test.utils.AddressWrapper;
import ru.syntez.camel.router.test.utils.RateValueConverter;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GeDaTest {

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

            final AddressModel addressModel = new AddressModel();

            final ValueConverter convDataToRate = new AdditionalDataConverter();
            final Map<String, Object> converters = new HashMap<String, Object>();
            converters.put("dataToRateValue", convDataToRate);

            final Assembler assembler = DTOAssembler.newAssembler(AddressModel.class, AddressModelRequest.class);
            assembler.assembleDto(addressModel, requestJson, converters, null);

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

            //Отдельный доп. класс для объединения сущностей
            AddressWrapper address = new AddressWrapper(modelXml, modelExtXml);

            AddressModelRequest request = new AddressModelRequest();

            final ValueConverter rateToDataValue = new RateValueConverter();
            final Map<String, Object> converters = new HashMap<String, Object>();
            converters.put("rateToDataValue", rateToDataValue);

            final Assembler assembler = DTOAssembler.newAssembler(AddressModelRequest.class, AddressWrapper.class);

            long startTime = System.currentTimeMillis();
            System.out.println("Starting transformation: " + startTime);

            for (int i = 0; i < 1000000; i++) {
                assembler.assembleDto(request, address, converters, null);
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