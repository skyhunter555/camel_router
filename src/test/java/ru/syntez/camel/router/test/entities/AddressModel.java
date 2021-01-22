package ru.syntez.camel.router.test.entities;

import lombok.Data;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AddressModel")
@Data
public class AddressModel {
    @XmlAttribute(name = "addressId", required = true)
    protected Integer addressId;
    @XmlAttribute(name = "addressCity", required = true)
    private String addressCity;
    @XmlAttribute(name = "addressStreet")
    protected String addressStreet;
    @XmlAttribute(name = "rateValue1", required = true)
    protected Integer rateValue1;
    @XmlAttribute(name = "rateValue2", required = true)
    protected Integer rateValue2;
}