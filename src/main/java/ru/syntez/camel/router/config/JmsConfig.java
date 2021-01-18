package ru.syntez.camel.router.config;

import com.rabbitmq.client.ConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.SimpleRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.HashMap;
import java.util.Map;

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

    @Value("${camel.rabbit-mq.host}")
    private String brokerHost = "localhost";

    @Value("${camel.rabbit-mq.port}")
    private Integer brokerPort = 5672;

    @Value("${camel.rabbit-mq.virtual-host}")
    private String virtualHost = "user";

    @Value("${camel.rabbit-mq.username}")
    private String username = "user";

    @Value("${camel.rabbit-mq.password}")
    private String password = "user";

    @Bean
    public ConnectionFactory rabbitConnectionFactory() {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(brokerHost);
        connectionFactory.setPort(brokerPort);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);
        connectionFactory.setRequestedChannelMax(1);
        return connectionFactory;
    }

    @Bean
    public CamelContext camelContext() {
        SimpleRegistry registry = new SimpleRegistry();
        Map<Class<?>, Object> beanMap = new HashMap<>();
        beanMap.put(ConnectionFactory.class, rabbitConnectionFactory());
        registry.put("rabbitConnectionFactory", beanMap);
        return new DefaultCamelContext(registry);
    }

}

