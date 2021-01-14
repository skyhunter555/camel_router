package ru.syntez.camel.router.config;

import org.apache.activemq.broker.BrokerService;
import org.apache.camel.CamelContext;
import org.apache.camel.component.activemq.ActiveMQComponent;
import org.apache.camel.component.jms.JmsConfiguration;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnectionFactory;

import org.apache.camel.impl.DefaultCamelContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.syntez.camel.router.entities.CamelRouteBuilder;

/**
 * Configuration for camel jms
 * 1. Start AMQ BrokerService
 * 2. Created pooled ConnectionFactory
 * 3. Created camel context with ConnectionFactory and custom routeBuilder
 *
 * @author Skyhunter
 * @date 13.01.2021
 */
@Configuration
public class JmsConfig {

    @Value("${camel.broker.connector}")
    private String brokerConnector = "tcp://localhost:61616";

    @Bean
    public BrokerService brokerService() {
        BrokerService broker = new BrokerService();
        broker.setBrokerName("activemq");
        broker.setPersistent(false);
        broker.setUseShutdownHook(false);
        broker.setUseJmx(false);
        try {
            broker.addConnector(brokerConnector);
            broker.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return broker;
    }

    @Bean
    public ActiveMQConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(brokerConnector);
        return connectionFactory;
    }

    @Bean
    public PooledConnectionFactory pooledConnectionFactory() {
        PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory(connectionFactory());
        pooledConnectionFactory.setConnectionTimeout(5000);
        pooledConnectionFactory.setMaxConnections(10);
        return pooledConnectionFactory;
    }

    @Bean
    public CamelContext camelContext() {
        CamelContext camelContext = new DefaultCamelContext();
        JmsConfiguration jmsConfiguration = new JmsConfiguration(pooledConnectionFactory());
        jmsConfiguration.setConcurrentConsumers(4); //Пул потоков JMS слушателей для обслуживания входящих сообщений
        ActiveMQComponent activeMQComponent = ActiveMQComponent.activeMQComponent();
        activeMQComponent.setConfiguration(jmsConfiguration);
        camelContext.addComponent("jmsComponent", activeMQComponent);
        CamelRouteBuilder routeBuilder = new CamelRouteBuilder();
        try {
            camelContext.addRoutes(routeBuilder);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return camelContext;
    }

}

