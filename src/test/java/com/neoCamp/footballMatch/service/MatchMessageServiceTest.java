package com.neoCamp.footballMatch.service;

import com.neoCamp.footballMatch.dto.rabbitmq.PartidaInformacaoMessage;
import com.neoCamp.footballMatch.dto.rabbitmq.PartidaResultadoMessage;
import com.neoCamp.footballMatch.entity.Jogador;
import com.neoCamp.footballMatch.entity.Partida;
import com.neoCamp.footballMatch.repository.JogadorRepository;
import com.neoCamp.footballMatch.repository.PartidaRepository;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MatchMessageServiceTest {

    @Mock
    private PartidaRepository partidaRepository;
    @Mock
    private JogadorRepository jogadorRepository;
    @Mock
    private RabbitMQService rabbitMQService;
    @Mock
    private Validator validator;

    @InjectMocks
    private MatchMessageService matchMessageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void finishMatch_shouldUpdateAndPublishResult() {
        UUID matchId = UUID.randomUUID();
        Partida partida = new Partida();
        partida.setId(matchId);
        when(partidaRepository.findById(matchId)).thenReturn(Optional.of(partida));
        when(partidaRepository.save(any(Partida.class))).thenReturn(partida);

        List<PartidaResultadoMessage.PontuacaoMessage> scores = List.of(
                new PartidaResultadoMessage.PontuacaoMessage("1", 10)
        );

        matchMessageService.finishMatch(matchId, "winner", scores);

        verify(partidaRepository).save(any(Partida.class));
        verify(rabbitMQService).publicarResultadoPartida(any(PartidaResultadoMessage.class));
        assertEquals("FINISHED", partida.getStatus());
        assertEquals("winner", partida.getVencedor());
        assertNotNull(partida.getDataHoraFim());
    }

    @Test
    void processMatchInfo_shouldSaveMatchAndPlayers() {
        UUID matchId = UUID.randomUUID();
        Partida partida = new Partida();
        when(partidaRepository.findById(matchId)).thenReturn(Optional.of(partida));
        when(jogadorRepository.findByIdJogador(any())).thenReturn(Optional.empty());
        when(jogadorRepository.save(any(Jogador.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(partidaRepository.save(any(Partida.class))).thenReturn(partida);

        PartidaInformacaoMessage.JogadorMessage jogadorMsg = new PartidaInformacaoMessage.JogadorMessage();
        jogadorMsg.setIdJogador("1");
        jogadorMsg.setNomeJogador("Jogador Teste");

        PartidaInformacaoMessage message = new PartidaInformacaoMessage();
        message.setIdPartida(matchId);
        message.setDataHoraInicio(LocalDateTime.now());
        message.setStatus("EM_ANDAMENTO");
        message.setJogadores(List.of(jogadorMsg));

        matchMessageService.processMatchInfo(message);

        verify(jogadorRepository).save(any(Jogador.class));
        verify(partidaRepository, atLeastOnce()).save(any(Partida.class));
    }

    @Test
    void finishMatch_shouldThrowIfNotFound() {
        UUID matchId = UUID.randomUUID();
        when(partidaRepository.findById(matchId)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> matchMessageService.finishMatch(matchId, "winner", List.of()));
    }
}
