package com.neoCamp.footballMatch.service;

import com.neoCamp.footballMatch.dto.rabbitmq.PartidaInformacaoMessage;
import com.neoCamp.footballMatch.dto.rabbitmq.PartidaResultadoMessage;
import com.neoCamp.footballMatch.entity.Partida;
import com.neoCamp.footballMatch.entity.Jogador;
import com.neoCamp.footballMatch.repository.JogadorRepository;
import com.neoCamp.footballMatch.repository.PartidaRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MatchMessageService {

    private final PartidaRepository partidaRepository;
    private final JogadorRepository jogadorRepository;
    private final RabbitMQService rabbitMQService;
    private final Validator validator;
    private UUID idPartida;

    @Transactional
    public void processMatchInfo(PartidaInformacaoMessage message) {
        log.info("PROCESSANDO A PARTIDA: {}", message.getIdPartida());
        validateMessage(message);
        Partida match = partidaRepository.findById(message.getIdPartida())
                .orElse(new Partida());
        mapAndPersistMatch(match, message);
        log.info("A partida {}  foi processada e persistida com sucesso", message.getIdPartida());
    }

    @Transactional
    public void finishMatch(UUID matchId, String winner, List<PartidaResultadoMessage.PontuacaoMessage> scores) {
        log.info("Finishing match: {}", matchId);
        Partida match = partidaRepository.findById(matchId)
                .orElseThrow(() -> new RuntimeException("PARTIDA NAO ENCONTRDA: " + matchId));
        match.setStatus("FINALIZADA");
        match.setVencedor(winner);
        match.setDataHoraFim(java.time.LocalDateTime.now());

        partidaRepository.save(match);
        PartidaResultadoMessage resultMessage = new PartidaResultadoMessage();
        resultMessage.setIdPartida(matchId);
        resultMessage.setVencedor(winner);
        resultMessage.setPontuacoes(scores);
        resultMessage.setDataHoraFim(match.getDataHoraFim());
        rabbitMQService.publicarResultadoPartida(resultMessage);
        log.info("Partida {} finalizada e resultado publicado", matchId);
    }

    private void validateMessage(PartidaInformacaoMessage message) {
        Set<ConstraintViolation<PartidaInformacaoMessage>> violations = validator.validate(message);
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder("Validation error: ");
            for (ConstraintViolation<PartidaInformacaoMessage> violation : violations) {
                sb.append(violation.getMessage()).append("; ");
            }
            throw new IllegalArgumentException(sb.toString());
        }
    }

    private void mapAndPersistMatch(Partida match, PartidaInformacaoMessage message) {
        match.setId(message.getIdPartida());
        match.setDataHoraInicio(message.getDataHoraInicio());
        match.setStatus(message.getStatus());
        List<Jogador> players = new ArrayList<>();
        for (PartidaInformacaoMessage.JogadorMessage playerMsg : message.getJogadores()) {
            Jogador player = jogadorRepository.findByIdJogador(playerMsg.getIdJogador())
                .orElse(new Jogador());
            player.setIdJogador(playerMsg.getIdJogador());
            player.setNomeJogador(playerMsg.getNomeJogador());
            player = jogadorRepository.save(player);
            players.add(player);
        }
        match.setJogadores(players);
        partidaRepository.save(match);
    }
}
