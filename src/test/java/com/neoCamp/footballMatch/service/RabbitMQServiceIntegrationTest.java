package com.neoCamp.footballMatch.service;

import com.neoCamp.footballMatch.config.RabbitMQTestConfig;
import com.neoCamp.footballMatch.dto.rabbitmq.PartidaResultadoMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = {RabbitMQTestConfig.class})
@Testcontainers
class RabbitMQServiceIntegrationTest {

    @Container
    private static final RabbitMQContainer rabbitMQContainer =
        new RabbitMQContainer("rabbitmq:3.11-management")
            .withExposedPorts(5672, 15672);

    @Autowired
    private RabbitMQService rabbitMQService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private final String exchange = "partida.exchange";
    private final String routingKey = "partida.resultado";

    @BeforeEach
    void setUp() {
        // Limpa as filas antes de cada teste
        try {
            rabbitMQContainer.execInContainer(
                "rabbitmqadmin", "purge", "queue", "name=partida.resultado"
            );
            rabbitMQContainer.execInContainer(
                "rabbitmqadmin", "purge", "queue", "name=partida.informacoes"
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to clean up RabbitMQ queues", e);
        }
    }

    @Test
    void quandoPublicarResultado_entaoDeveEnviarParaFila() {
        // Arrange
        PartidaResultadoMessage resultado = criarResultadoPartida();

        // Act
        rabbitMQService.publicarResultadoPartida(resultado);

        // Assert
        await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
            Object message = rabbitTemplate.receiveAndConvert("partida.resultado");
            assertNotNull(message, "A mensagem não foi recebida na fila");
            assertTrue(message instanceof PartidaResultadoMessage);

            PartidaResultadoMessage mensagemRecebida = (PartidaResultadoMessage) message;
            assertEquals(resultado.getIdPartida(), mensagemRecebida.getIdPartida());
            assertEquals(resultado.getVencedor(), mensagemRecebida.getVencedor());
            assertEquals(resultado.getPontuacoes().size(), mensagemRecebida.getPontuacoes().size());
        });
    }

    @Test
    void quandoPublicarResultadoComErro_entaoDeveAtivarCircuitBreaker() {
        // Arrange
        // Força um erro desconectando o RabbitMQ
        rabbitMQContainer.stop();

        try {
            PartidaResultadoMessage resultado = criarResultadoPartida();

            // Act & Assert
            // Deve lançar exceção
            assertThrows(RuntimeException.class,
                () -> rabbitMQService.publicarResultadoPartida(resultado));
        } finally {
            // Garante que o container seja reiniciado mesmo se o teste falhar
            if (!rabbitMQContainer.isRunning()) {
                rabbitMQContainer.start();
                // Aguarda o RabbitMQ estar pronto
                await().atMost(30, TimeUnit.SECONDS)
                    .until(rabbitMQContainer::isRunning);
            }
        }
    }

    private PartidaResultadoMessage criarResultadoPartida() {
        PartidaResultadoMessage.PontuacaoMessage pontuacao1 =
            new PartidaResultadoMessage.PontuacaoMessage("jogador1", 10);
        PartidaResultadoMessage.PontuacaoMessage pontuacao2 =
            new PartidaResultadoMessage.PontuacaoMessage("jogador2", 8);

        PartidaResultadoMessage resultado = new PartidaResultadoMessage();
        resultado.setIdPartida(UUID.randomUUID());
        resultado.setVencedor("jogador1");
        resultado.setPontuacoes(List.of(pontuacao1, pontuacao2));
        resultado.setDataHoraFim(LocalDateTime.now());

        return resultado;
    }
}
