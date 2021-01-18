package ru.syntez.camel.router.component;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.syntez.camel.router.entities.DocumentTypeEnum;
import ru.syntez.camel.router.entities.RoutingDocument;
import javax.xml.bind.JAXBContext;

/**
 * Configuration custom CamelRouteBuilder
 *
 * @author Skyhunter
 * @date 13.01.2021
 */
@Component
public class CamelRouteBuilder extends RouteBuilder {

    @Value("${camel.rabbit-mq.queue-input-endpoint}")
    private String queueInputEndpoint = "input";

    @Value("${camel.rabbit-mq.queue-output-order-endpoint}")
    private String queueOutputOrderEndpoint = "output";

    @Value("${camel.rabbit-mq.queue-output-invoice-endpoint}")
    private String queueOutputInvoiceEndpoint = "output";

    public String getQueueInputEndpoint() {
        return queueInputEndpoint;
    }

    private JaxbDataFormat xmlDataFormat = new JaxbDataFormat();

    @Override
    public void configure() throws Exception {

        JAXBContext context = JAXBContext.newInstance(RoutingDocument.class);
        xmlDataFormat.setContext(context);
        String selectOrder = (String.format("/routingDocument/docType='%s'", DocumentTypeEnum.order));

        from(queueInputEndpoint)
                .doTry().unmarshal(xmlDataFormat)
                .choice()
                .when(xpath(selectOrder))
                .log("******** SELECTED ORDER OUTPUT QUEUE FOR DOCTYPE: ${body.docType}")
                .to(queueOutputOrderEndpoint)
                .endChoice()
                .otherwise()
                .log("******** SELECTED INVOICE OUTPUT QUEUE FOR DOCTYPE: ${body.docType}")
                .to(queueOutputInvoiceEndpoint);
    }
}
