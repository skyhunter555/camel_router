package ru.syntez.camel.router.test.entities

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlType

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AddressModelExt")
data class AddressModelDataExt(
    @XmlAttribute(name = "addressCountry", required = true)
    val addressCountry: String,
    @XmlAttribute(name = "addressState")
    val addressState: String?,
    @XmlAttribute(name = "addressDistrict")
    val addressDistrict: String?,
    @XmlAttribute(name = "houseNumber")
    val houseNumber: String?
)