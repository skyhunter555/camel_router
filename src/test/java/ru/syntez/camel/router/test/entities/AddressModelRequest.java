package ru.syntez.camel.router.test.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

/**
 * Тестовая сущность для маппинга адреса из JSON
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class AddressModelRequest {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("label")
    private String label;
    @JsonProperty("country")
    private String country;
    @JsonProperty("state")
    private String state;
    @JsonProperty("city")
    private String city;
    @JsonProperty("district")
    private String district;
    @JsonProperty("street")
    private String street;
    @JsonProperty("houseNumber")
    private String houseNumber;
    @JsonProperty("additionalData")
    private List<AdditionalData> additionalData;
}

