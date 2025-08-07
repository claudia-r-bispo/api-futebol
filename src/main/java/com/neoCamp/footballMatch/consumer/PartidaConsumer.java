package com.neoCamp.footballMatch.consumer;

import com.neoCamp.footballMatch.config.RabbitConfig;
import com.neoCamp.footballMatch.dto.rabbitmq.PartidaInformacaoMessage;
import com.neoCamp.footballMatch.service.MatchMessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class PartidaConsumer {

    private final MatchMessageService matchMessageService;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = RabbitConfig.PARTIDA_INFORMACOES_QUEUE)
    public void receiveMessage(@Payload Message message) {
        try {
            log.info("Mensagem recebida da fila {}: {}",
                    RabbitConfig.PARTIDA_INFORMACOES_QUEUE,
                    new String(message.getBody()));

            PartidaInformacaoMessage partidaMessage = objectMapper.readValue(
                    message.getBody(),
                    PartidaInformacaoMessage.class);

            log.info("Processando informações da partida: {}", partidaMessage.getIdPartida());
            matchMessageService.processMatchInfo(partidaMessage);

        } catch (IOException e) {
            log.error("Erro ao processar mensagem da fila {}: {}",
                    RabbitConfig.PARTIDA_INFORMACOES_QUEUE,
                    e.getMessage(),
                    e);
            // Rejeita a mensagem para que seja enviada para a DLQ (se configurada)
            throw new RuntimeException("Falha ao processar mensagem", e);
        } catch (Exception e) {
            log.error("Erro inesperado ao processar mensagem: {}", e.getMessage(), e);
            // Rejeita a mensagem para que seja enviada para a DLQ (se configurada)
            throw new RuntimeException("Erro inesperado ao processar mensagem", e);
        }
    }
}
