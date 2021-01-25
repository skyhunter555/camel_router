package ru.syntez.camel.router.test.entities

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class AddressModelRequestData (
    @JsonProperty("id")
    val id: Int,
    @JsonProperty("label")
    var label: String? = null,
    @JsonProperty("country")
    val country: String,
    @JsonProperty("state")
    val state: String?,
    @JsonProperty("city")
    val city: String,
    @JsonProperty("district")
    val district: String?,
    @JsonProperty("street")
    val street: String,
    @JsonProperty("houseNumber")
    val houseNumber: String?,
    @JsonProperty("additionalData")
    val additionalModelData: List<AdditionalModelData>
) {
    object ModelMapper {
        fun fromModels(addressModel: AddressModelData, addressModelExt: AddressModelDataExt) =
            AddressModelRequestData(
                id = addressModel.addressId,
                country = addressModelExt.addressCountry,
                city = addressModel.addressCity,
                street = addressModel.addressStreet,
                additionalModelData = arrayListOf(AdditionalModelData(null, addressModel.rateValue1.toString())),
                houseNumber = addressModelExt.houseNumber,
                district = addressModelExt.addressDistrict,
                state = addressModelExt.addressState
            )
    }
}