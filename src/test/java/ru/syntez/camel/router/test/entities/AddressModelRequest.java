package ru.syntez.camel.router.test.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.inspiresoftware.lib.dto.geda.annotations.Dto;
import com.inspiresoftware.lib.dto.geda.annotations.DtoField;
import lombok.Data;
import org.junit.Ignore;

import java.util.List;

/**
 * Тестовая сущность для маппинга адреса из JSON
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Dto
@Ignore
public class AddressModelRequest {
    @JsonProperty("id")
    @DtoField("addressId")
    private Integer id;
    @JsonProperty("label")
    private String label;
    @JsonProperty("country")
    @DtoField("addressCountry")
    private String country;
    @JsonProperty("state")
    @DtoField("addressState")
    private String state;
    @JsonProperty("city")
    @DtoField("addressCity")
    private String city;
    @JsonProperty("district")
    @DtoField("addressDistrict")
    private String district;
    @JsonProperty("street")
    @DtoField("addressStreet")
    private String street;

    @JsonProperty("houseNumber")
    @DtoField("houseNumber")
    private String houseNumber;
    @JsonProperty("additionalData")
    @DtoField(value = "rateValue1", converter = "rateToDataValue")
    private List<AdditionalData> additionalData;
}

