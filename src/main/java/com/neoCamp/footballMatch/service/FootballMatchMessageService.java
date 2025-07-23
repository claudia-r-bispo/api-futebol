package com.neoCamp.footballMatch.service;

import com.neoCamp.footballMatch.dto.rabbitmq.PartidaInformacaoMessage;
import com.neoCamp.footballMatch.dto.rabbitmq.PartidaResultadoMessage;
import com.neoCamp.footballMatch.entity.FootballMatch;
import com.neoCamp.footballMatch.entity.Jogador;
import com.neoCamp.footballMatch.repository.FootballMatchRepository;
import com.neoCamp.footballMatch.repository.JogadorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FootballMatchMessageService {

    private final FootballMatchRepository footballMatchRepository;
    private final JogadorRepository jogadorRepository;
    private final RabbitMQService rabbitMQService;
    private final Validator validator;

    @Transactional
    public void processarPartidaInformacao(PartidaInformacaoMessage message) {
        log.info("Iniciando processamento da partida: {}", message.getIdPartida());

        if (message.getIdPartida() == null) {
            log.error("Erro ao processar a partida: idPartida é nulo");
            return;
        }
        validarMensagem(message);


        FootballMatch match = footballMatchRepository.findById(message.getIdPartida())
                .orElse(new FootballMatch());

        mapearEPersistirMatch(match, message);

        log.info("Partida {} processada e persistida com sucesso", message.getIdPartida());
    }

    @Transactional
    public void finalizarPartida(UUID idPartida, String vencedor, List<PartidaResultadoMessage.PontuacaoMessage> pontuacoes) {
        log.info("Finalizando partida: {}", idPartida);

        FootballMatch match = footballMatchRepository.findById(idPartida)
                .orElseThrow(() -> new RuntimeException("Partida não encontrada: " + idPartida));




        footballMatchRepository.save(match);

        // Publicar resultado na fila
        PartidaResultadoMessage resultadoMessage = new PartidaResultadoMessage();
        resultadoMessage.setIdPartida(idPartida);
        resultadoMessage.setVencedor(vencedor);
        resultadoMessage.setPontuacoes(pontuacoes);
        // resultadoMessage.setDataHoraFim(match.getDataHoraFim()); // Adapte conforme seus campos

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

    private void mapearEPersistirMatch(FootballMatch match, PartidaInformacaoMessage message) {
        match.setId(message.getIdPartida());
        // Adapte o mapeamento dos campos do DTO para FootballMatch conforme necessário
        // Exemplo:
        // match.setDateTimeDeparture(message.getDataHoraInicio());
        // match.setStatus(message.getStatus());
        // ...
        footballMatchRepository.save(match);
    }
}
