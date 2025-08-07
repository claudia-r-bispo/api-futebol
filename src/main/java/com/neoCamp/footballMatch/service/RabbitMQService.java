package com.neoCamp.footballMatch.service;

import com.neoCamp.footballMatch.config.RabbitConfig;
import com.neoCamp.footballMatch.config.Resilience4jConfig;
import com.neoCamp.footballMatch.dto.rabbitmq.PartidaResultadoMessage;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class RabbitMQService {

    private final RabbitTemplate rabbitTemplate;
    private final MeterRegistry meterRegistry;
    private static final String PUBLISH_TIMER = "rabbitmq.publish.timer";

    @CircuitBreaker(name = Resilience4jConfig.RABBITMQ_CIRCUIT_BREAKER, fallbackMethod = "publicarResultadoFallback")
    @Retry(name = "rabbitmqRetry")
    public void publicarResultadoPartida(PartidaResultadoMessage message) {
        Timer.Sample timer = Timer.start(meterRegistry);
        try {
            log.info("Publicando resultado da partida {} na fila {}",
                    message.getIdPartida(), RabbitConfig.PARTIDA_RESULTADO_QUEUE);

            rabbitTemplate.convertAndSend(
                    RabbitConfig.PARTIDA_EXCHANGE,
                    RabbitConfig.PARTIDA_RESULTADO_ROUTING_KEY,
                    message
            );

            log.info("Resultado da partida {} publicado com sucesso", message.getIdPartida());
            recordPublishMetrics(timer, "success");
        } catch (Exception e) {
            log.error("Erro ao publicar resultado da partida {}: {}",
                    message.getIdPartida(), e.getMessage(), e);
            recordPublishMetrics(timer, "error");
            throw new RuntimeException("Falha ao publicar resultado da partida", e);
        }
    }

    private void recordPublishMetrics(Timer.Sample timer, String status) {
        timer.stop(meterRegistry.timer(PUBLISH_TIMER, "status", status));
    }

    // Método de fallback para o Circuit Breaker
    public void publicarResultadoFallback(PartidaResultadoMessage message, Exception e) {
        log.error("Circuit Breaker ativado para a partida {}. Mensagem não enviada: {}", 
                message.getIdPartida(), e.getMessage());

    }
}
