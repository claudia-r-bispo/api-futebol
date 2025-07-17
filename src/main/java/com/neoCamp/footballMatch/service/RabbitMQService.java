package com.neoCamp.footballMatch.service;

import com.neoCamp.footballMatch.config.RabbitConfig;
import com.neoCamp.footballMatch.dto.rabbitmq.PartidaResultadoMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RabbitMQService {

    private final RabbitTemplate rabbitTemplate;

    public void publicarResultadoPartida(PartidaResultadoMessage message) {
        try {
            log.info("Publicando resultado da partida {} na fila {}",
                    message.getIdPartida(), RabbitConfig.PARTIDA_RESULTADO_QUEUE);

            rabbitTemplate.convertAndSend(
                    RabbitConfig.PARTIDA_EXCHANGE,
                    RabbitConfig.PARTIDA_RESULTADO_ROUTING_KEY,
                    message
            );

            log.info("Resultado da partida {} publicado com sucesso", message.getIdPartida());

        } catch (Exception e) {
            log.error("Erro ao publicar resultado da partida {}: {}",
                    message.getIdPartida(), e.getMessage(), e);
            throw new RuntimeException("Falha ao publicar resultado da partida", e);
        }
    }
}
