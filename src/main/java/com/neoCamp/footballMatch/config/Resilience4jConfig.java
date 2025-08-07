package com.neoCamp.footballMatch.config;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class Resilience4jConfig {

    public static final String RABBITMQ_CIRCUIT_BREAKER = "rabbitmqCircuitBreaker";

    @Bean
    public CircuitBreaker rabbitmqCircuitBreaker() {
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(50) // % de falhas para abrir o circuito
                .waitDurationInOpenState(Duration.ofSeconds(30)) // tempo para tentar novamente
                .slidingWindowSize(10) // número de chamadas para calcular a taxa de falhas
                .minimumNumberOfCalls(5) // número mínimo de chamadas antes de começar a calcular a taxa
                .permittedNumberOfCallsInHalfOpenState(3) // número de chamadas permitidas no estado half-open
                .recordExceptions(Exception.class) // exceções que contam como falhas
                .build();

        return CircuitBreakerRegistry.of(circuitBreakerConfig)
                .circuitBreaker(RABBITMQ_CIRCUIT_BREAKER, circuitBreakerConfig);
    }
}
