package ru.syntez.camel.router.entities;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import javax.xml.bind.JAXBContext;

/**
 * Configuration custom CamelRouteBuilder
 *
 * @author Skyhunter
 * @date 13.01.2021
 */
@Component
public class CamelRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        JaxbDataFormat xmlDataFormat = new JaxbDataFormat();
        JAXBContext context = JAXBContext.newInstance(RouterDocument.class);
        xmlDataFormat.setContext(context);

        from("jmsComponent:queue:inputqueue")
                .doTry().unmarshal(xmlDataFormat)
                .choice()
                    .when().simple("${body.docType} == 'order'")
                    .log("******** SELECTED ORDER OUTPUT QUEUE FOR DOCTYPE: ${body.docType}")
                    .to("jmsComponent:queue:orderoutputqueue")
                .endChoice()
                .otherwise()
                    .log("******** SELECTED TEXT OUTPUT QUEUE FOR DOCTYPE: ${body.docType}")
                    .to("jmsComponent:queue:textoutputqueue");
    }

}
