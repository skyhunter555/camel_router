package ru.syntez.camel.router.test.entities

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class AdditionalModelData(
    @JsonProperty("key")
    var key: String?,
    @JsonProperty("value")
    var value: String
)