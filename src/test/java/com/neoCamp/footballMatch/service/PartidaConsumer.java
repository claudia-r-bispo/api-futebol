package com.neoCamp.footballMatch.service;

import com.neoCamp.footballMatch.dto.rabbitmq.PartidaInformacaoMessage;
import com.neoCamp.footballMatch.dto.rabbitmq.PartidaResultadoMessage;
import com.neoCamp.footballMatch.entity.Partida;
import com.neoCamp.footballMatch.entity.Jogador;
import com.neoCamp.footballMatch.repository.FootballMatchRepository;
import com.neoCamp.footballMatch.repository.JogadorRepository;
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
public class FootballMatchMessageService {

    private final FootballMatchRepository FootballMatchRepository;
    private final     JogadorRepository jogadorRepository;
    private final RabbitMQService rabbitMQService;
    private final Validator validator;

    @Transactional
    public void processarPartidaInformacao(PartidaInformacaoMessage message) {
        log.info("Iniciando processamento da partida: {}", message.getIdPartida());


        validarMensagem(message);


        Partida partida = FootballMatchRepository.findById(message.getIdPartida())
                .orElse(new Partida());


        mapearEPersistirPartida(partida, message);

        log.info("Partida {} processada e persistida com sucesso", message.getIdPartida());
    }

    @Transactional
    public void finalizarPartida(UUID idPartida, String vencedor, List<PartidaResultadoMessage.PontuacaoMessage> pontuacoes) {
        log.info("Finalizando partida: {}", idPartida);

        Partida partida = partidaRepository.findById(idPartida)
                .orElseThrow(() -> new RuntimeException("Partida não encontrada: " + idPartida));


        partida.setStatus("FINALIZADA");
        partida.setVencedor(vencedor);
        partida.setDataHoraFim(java.time.LocalDateTime.now());

        // salvar as pontuações na sua estrutura de dados


        partida = partidaRepository.save(partida);


        PartidaResultadoMessage resultadoMessage = new PartidaResultadoMessage();
        resultadoMessage.setIdPartida(idPartida);
        resultadoMessage.setVencedor(vencedor);
        resultadoMessage.setPontuacoes(pontuacoes);
        resultadoMessage.setDataHoraFim(partida.getDataHoraFim());

        rabbitMQService.publicarResultadoPartida(resultadoMessage);

        log.info("Partida {} finalizada e resultado publicado", idPartida);
    }

    private void validarMensagem(PartidaInformacaoMessage message) {
        Set<ConstraintViolation<PartidaInformacaoMessage>> violations = validator.validate(message);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder("Erro de validação: ");
            for (ConstraintViolation<PartidaInformacaoMessage> violation : violations) {
                sb.append(violation.getMessage()).append("; ");
            }
            throw new IllegalArgumentException(sb.toString());
        }
    }

    private void mapearEPersistirPartida(Partida partida, PartidaInformacaoMessage message) {
        partida.setId(message.getIdPartida());
        partida.setDataHoraInicio(message.getDataHoraInicio());
        partida.setStatus(message.getStatus());


        List<Jogador> jogadores = new ArrayList<>();
        for (PartidaInformacaoMessage.JogadorMessage jogadorMsg : message.getJogadores()) {
            Jogador jogador = jogadorRepository.findByIdJogador(jogadorMsg.getIdJogador())
                    .orElse(new Jogador());

            jogador.setIdJogador(jogadorMsg.getIdJogador());
            jogador.setNomeJogador(jogadorMsg.getNomeJogador());

            jogador = jogadorRepository.save(jogador);
            jogadores.add(jogador);
        }

        partida.setJogadores(jogadores);
        partidaRepository.save(partida);
    }
}