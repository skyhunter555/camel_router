package ru.syntez.camel.router.test.entities;

import com.inspiresoftware.lib.dto.geda.annotations.Dto;
import com.inspiresoftware.lib.dto.geda.annotations.DtoField;
import lombok.Data;
import org.junit.Ignore;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AddressModel")
@Data
@Dto
@Ignore
public class AddressModel {
    @XmlAttribute(name = "addressId", required = true)
    @DtoField("id")
    private Integer addressId;
    @XmlAttribute(name = "addressCity", required = true)
    @DtoField("city")
    private String addressCity;
    @XmlAttribute(name = "addressStreet")
    @DtoField("street")
    private String addressStreet;
    @XmlAttribute(name = "rateValue1", required = true)
    @DtoField(value = "additionalData", converter = "dataToRateValue")
    private Integer rateValue1;
    @XmlAttribute(name = "rateValue2", required = true)
    private Integer rateValue2;
}