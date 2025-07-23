package com.neoCamp.footballMatch.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import static org.springframework.amqp.core.QueueBuilder.durable;

@Configuration // ← ADICIONADO
public class RabbitConfig {

    public static final String PARTIDA_INFORMACOES_QUEUE = "partida.informacoes";
    public static final String PARTIDA_RESULTADO_QUEUE = "partida.resultado";
    public static final String PARTIDA_EXCHANGE = "partida.exchange";
    public static final String PARTIDA_INFORMACOES_ROUTING_KEY = "partida.informacoes";
    public static final String PARTIDA_RESULTADO_ROUTING_KEY = "partida.resultado";

    @Bean
    public TopicExchange partidaExchange() {
        return ExchangeBuilder.topicExchange(PARTIDA_EXCHANGE)
                .durable(true)
                .build();
    }

    @Bean
    public Queue partidaInformacoesQueue() {
        return durable(PARTIDA_INFORMACOES_QUEUE).build();
    }

    @Bean
    public Queue partidaResultadoQueue() {
        // CORRIGIDO: estava usando PARTIDA_INFORMACOES_QUEUE
        return durable(PARTIDA_RESULTADO_QUEUE).build();
    }

    @Bean
    public Binding partidaInformacoesBinding() {
        return BindingBuilder
                .bind(partidaInformacoesQueue())
                .to(partidaExchange())
                .with(PARTIDA_INFORMACOES_ROUTING_KEY);
    }

    @Bean
    public Binding partidaResultadoBinding() {
        return BindingBuilder
                .bind(partidaResultadoQueue())
                .to(partidaExchange())
                .with(PARTIDA_RESULTADO_ROUTING_KEY);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {

        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        template.setRetryTemplate(retryTemplate());
        return template;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();

        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jsonMessageConverter());
        factory.setDefaultRequeueRejected(false);
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        return factory;
    }

    @Bean
    public RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();

        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(3);
        retryTemplate.setRetryPolicy(retryPolicy);

        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(1000); // 1 segundo
        backOffPolicy.setMultiplier(2.0); // Dobra o tempo de espera a cada tentativa
        backOffPolicy.setMaxInterval(10000); // 10 segundos máximo
        retryTemplate.setBackOffPolicy(backOffPolicy);

        return retryTemplate;
    }
}