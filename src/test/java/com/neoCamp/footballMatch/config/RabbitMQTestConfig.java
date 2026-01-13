package com.neoCamp.footballMatch.config;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.Set;

@TestConfiguration
public class RabbitMQTestConfig {

    private static final String RABBIT_IMAGE = "rabbitmq:3.11-management";
    private static final String USERNAME = "guest";
    private static final String PASSWORD = "guest";

    @Bean(initMethod = "start", destroyMethod = "stop")
    public RabbitMQContainer rabbitMQContainer() {
        return new RabbitMQContainer(DockerImageName.parse(RABBIT_IMAGE))
                .withExposedPorts(5672, 15672)
                .withUser(USERNAME, PASSWORD, Set.of("administrator"));
    }

    @Bean
    @Primary
    public ConnectionFactory testConnectionFactory(RabbitMQContainer container) {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(container.getHost());
        connectionFactory.setPort(container.getAmqpPort());
        connectionFactory.setUsername(USERNAME);
        connectionFactory.setPassword(PASSWORD);
        return connectionFactory;
    }

    @Bean
    @Primary
    public RabbitTemplate testRabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }
}
