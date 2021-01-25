package ru.syntez.camel.router.test

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.junit.Assert
import org.junit.Test
import ru.syntez.camel.router.test.entities.AddressModelData
import ru.syntez.camel.router.test.entities.AddressModelDataExt
import ru.syntez.camel.router.test.entities.AddressModelRequestData

class KotlinTransformTest {

    private val jsonMapper = ObjectMapper().registerModule(KotlinModule())

    private val kotlinXmlMapper = XmlMapper(JacksonXmlModule().apply {
        setDefaultUseWrapper(false)
    }).registerKotlinModule()
        .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    @Test
    fun fromJSONToModelTest() {

        val addressRequest = jsonMapper.readValue(javaClass.getResource("/addressRequest.json").readText(), AddressModelRequestData::class.java)
        val expectedAddressModel = kotlinXmlMapper.readValue(javaClass.getResource("/addressModel.xml").readText(), AddressModelData::class.java)

        val addressModel = AddressModelData.ModelMapper.fromRequest(addressRequest)

        Assert.assertEquals(expectedAddressModel, addressModel)
        Assert.assertEquals(Integer.valueOf(123), addressModel.addressId)
        Assert.assertEquals("Ропшинское сельское поселение", addressModel.addressCity)
        Assert.assertEquals("Тихий переулок", addressModel.addressStreet)
        Assert.assertEquals(Integer.valueOf(500), addressModel.rateValue1)
    }

    @Test
    fun fromXMLToModelTest() {


        val addressModel = kotlinXmlMapper.readValue(javaClass.getResource("/addressModel.xml").readText(), AddressModelData::class.java)
        val addressModelExt = kotlinXmlMapper.readValue(javaClass.getResource("/addressModelExt.xml").readText(), AddressModelDataExt::class.java)
        val expectedAddressRequest = jsonMapper.readValue(javaClass.getResource("/addressRequest.json").readText(), AddressModelRequestData::class.java)

        val startTime = System.currentTimeMillis()
        println("Starting transformation: $startTime")

        for (i in 1..1000000) {
            AddressModelRequestData.ModelMapper.fromModels(addressModel, addressModelExt)
        }

        val finishTime = System.currentTimeMillis()
        println("Transformated all: $finishTime")
        println("Total time: " + (finishTime - startTime) + " ms.")

        val request = AddressModelRequestData.ModelMapper.fromModels(addressModel, addressModelExt)
        request.label = "Тестовый адрес JSON"
        request.additionalModelData.first().key = "тариф"

        Assert.assertEquals(expectedAddressRequest, request)
        Assert.assertEquals(request.id, Integer.valueOf(123))
        Assert.assertEquals(request.city, "Ропшинское сельское поселение")
        Assert.assertEquals(request.street, "Тихий переулок")
        Assert.assertEquals(request.additionalModelData[0].value, "500")

    }


}