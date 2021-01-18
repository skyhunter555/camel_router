package ru.syntez.camel.router.entities;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * RoutingDocument model
 *
 * @author Skyhunter
 * @date 13.01.2021
 */
@XmlRootElement(name = "routingDocument")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class RoutingDocument {

    private String docType; //TODO enum: invoice, order
    private int docId;

}
