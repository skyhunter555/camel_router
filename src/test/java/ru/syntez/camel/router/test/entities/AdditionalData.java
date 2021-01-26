package ru.syntez.camel.router.test.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.inspiresoftware.lib.dto.geda.annotations.Dto;
import com.inspiresoftware.lib.dto.geda.annotations.DtoField;
import lombok.Data;
import org.junit.Ignore;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Dto
@Ignore
public class AdditionalData {
    @JsonProperty("key")
    @DtoField
    private String key;
    @JsonProperty("value")
    @DtoField
    private String value;
}
