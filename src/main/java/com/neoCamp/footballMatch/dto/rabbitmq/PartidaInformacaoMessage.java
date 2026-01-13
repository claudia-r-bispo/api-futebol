package com.neoCamp.footballMatch.dto.rabbitmq;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartidaInformacaoMessage {

    @NotNull(message = "ID da partida é obrigatório")
    @JsonProperty("idPartida")
    private UUID idPartida;

    @NotNull(message = "Data e hora de início são obrigatórias")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    @JsonProperty("dataHoraInicio")
    private LocalDateTime dataHoraInicio;

    @NotEmpty(message = "Lista de jogadores não pode ser vazia")
    @Valid
    @JsonProperty("jogadores")
    private List<JogadorMessage> jogadores;

    @NotBlank(message = "Status é obrigatório")
    @JsonProperty("status")
    private String status;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JogadorMessage {

        @NotBlank(message = "ID do jogador é obrigatório")
        @JsonProperty("id")
        private String idJogador;

        @NotBlank(message = "Nome do jogador é obrigatório")
        @JsonProperty("nome")
        private String nomeJogador;
    }
}