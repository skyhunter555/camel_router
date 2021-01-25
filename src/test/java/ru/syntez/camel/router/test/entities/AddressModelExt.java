package ru.syntez.camel.router.test.entities;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AddressModelExt")
@Data
public class AddressModelExt {
    @XmlAttribute(name = "addressCountry", required = true)
    protected String addressCountry;
    @XmlAttribute(name = "addressState")
    protected String addressState;
    @XmlAttribute(name = "addressDistrict")
    private String addressDistrict;
    @XmlAttribute(name = "houseNumber")
    protected String houseNumber;
}