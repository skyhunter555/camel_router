package ru.syntez.camel.router.test;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.dozer.DozerBeanMapper;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.util.ResourceUtils;
import ru.syntez.camel.router.test.entities.AddressModel;
import ru.syntez.camel.router.test.entities.AddressModelExt;
import ru.syntez.camel.router.test.entities.AddressModelRequest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DozerTest {


    private ObjectMapper jsonMapper = new ObjectMapper();

    private final static DozerBeanMapper dozerMapper;
    static {
        List myMappingFiles =  new ArrayList<>(Arrays.asList("dozerBeanMapping.xml", "dozerBeanMappingExt.xml"));
        dozerMapper = new DozerBeanMapper();
        dozerMapper.setMappingFiles(myMappingFiles);
    }

    private final static ObjectMapper xmlMapper;
    static {
        JacksonXmlModule xmlModule = new JacksonXmlModule();
        xmlModule.setDefaultUseWrapper(false);
        xmlMapper = new XmlMapper(xmlModule);
        xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Test
    public void fromJSONToModelTest() {
        try {

            File fileJson = ResourceUtils.getFile(this.getClass().getResource("/dddressRequest.json"));
            File fileXml = ResourceUtils.getFile(this.getClass().getResource("/dddressModel.xml"));
            AddressModelRequest requestJson = jsonMapper.readValue(fileJson, AddressModelRequest.class);
            AddressModel expectedModelXml = xmlMapper.readValue(fileXml, AddressModel.class);

            AddressModel addressModel = new AddressModel();
            dozerMapper.map(requestJson, addressModel);

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

            File fileJson = ResourceUtils.getFile(this.getClass().getResource("/dddressRequest.json"));
            File fileXml = ResourceUtils.getFile(this.getClass().getResource("/dddressModel.xml"));
            File fileExtXml = ResourceUtils.getFile(this.getClass().getResource("/addressModelExt.xml"));
            AddressModel modelXml = xmlMapper.readValue(fileXml, AddressModel.class);
            AddressModelExt modelExtXml = xmlMapper.readValue(fileExtXml, AddressModelExt.class);
            AddressModelRequest expectedRequestJson = jsonMapper.readValue(fileJson, AddressModelRequest.class);

            long startTime = System.currentTimeMillis();
            System.out.println("Starting transformation: " + startTime);

            AddressModelRequest request = new AddressModelRequest();
            for (int i = 0; i < 1000000; i++) {
                request = new AddressModelRequest();
                dozerMapper.map(modelXml, request);
                dozerMapper.map(modelExtXml, request);
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
