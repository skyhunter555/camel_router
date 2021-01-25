package ru.syntez.camel.router.test.entities

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlType

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AddressModel")
data class AddressModelData(
    @XmlAttribute(name = "addressId", required = true)
    val addressId: Int,
    @XmlAttribute(name = "addressCity", required = true)
    val addressCity: String,
    @XmlAttribute(name = "addressStreet")
    val addressStreet: String,
    @XmlAttribute(name = "rateValue1")
    val rateValue1: Int?,
    @XmlAttribute(name = "rateValue2")
    val rateValue2: Int?
) {
   object ModelMapper {
        fun fromRequest(request: AddressModelRequestData) =
            AddressModelData(
                request.id,
                request.city,
                request.street,
                request.additionalModelData!![0].value.toInt(),
                null
            )
   }
}