package ru.syntez.camel.router;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.util.ResourceUtils;
import ru.syntez.camel.router.component.CamelRouteBuilder;
import java.io.File;
import java.util.Arrays;
import java.util.List;

import static java.lang.System.exit;

/**
 * Main class for console running
 *
 * @author Skyhunter
 * @date 13.01.2021
 */
@SpringBootApplication
@EnableJms
public class CamelRouterMain {

    private static Logger LOG = LogManager.getLogger(CamelRouterMain.class);

    public static void main(String[] args) {

        LOG.info("STARTING THE APPLICATION");
        ConfigurableApplicationContext context = SpringApplication.run(CamelRouterMain.class, args);
        CamelRouteBuilder routeBuilder = context.getBean(CamelRouteBuilder.class);
        CamelContext camelContext = context.getBean(CamelContext.class);
        try {
            camelContext.addRoutes(routeBuilder);
            camelContext.start();
            List<File> xmlFileList = Arrays.asList(
                    ResourceUtils.getFile(CamelRouterMain.class.getResource("/xmls/router_doc_1.xml")),
                    ResourceUtils.getFile(CamelRouterMain.class.getResource("/xmls/router_doc_2.xml"))
            );
            long startTime = System.currentTimeMillis();
            LOG.info("Starting send: " + startTime);
            for (int i = 0; i < 100; i++) {
                sendMessage(camelContext, xmlFileList, routeBuilder.getQueueInputEndpoint());
            }
            long finishTime = System.currentTimeMillis();
            LOG.info("Send all: " + finishTime);
            LOG.info("Total time: " + (finishTime - startTime) + " ms.");
        } catch (Exception e) {
             LOG.error(String.format("Error send files %s", e.getMessage()));
        }
        camelContext.stop();
        exit(0);
        LOG.info("APPLICATION FINISHED");
    }

    private static void sendMessage(CamelContext camelContext, List<File> xmlFileList, String queueInputEndpoint) {
        for (File xmlFile: xmlFileList) {
            ProducerTemplate producerTemplate = camelContext.createProducerTemplate();
            producerTemplate.setDefaultEndpoint(camelContext.getEndpoint(queueInputEndpoint));
            producerTemplate.sendBody(xmlFile);
        }
    }

}