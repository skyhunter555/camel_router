package ru.syntez.camel.router.entities;

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
public class RoutingDocument {

    private String docType; //TODO enum: invoice, order
    private int docId;

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public int getDocId() {
        return docId;
    }

    public void setDocId(int docId) {
        this.docId = docId;
    }

}
