package com.neoCamp.footballMatch.dto.rabbitmq;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
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
public class PartidaResultadoMessage {

    @NotNull(message = "ID da partida é obrigatório")
    @JsonProperty("idPartida")
    private UUID idPartida;

    @JsonProperty("vencedor")
    private String vencedor;

    @NotEmpty(message = "Pontuações não podem estar vazias")
    @Valid
    @JsonProperty("pontuacoes")
    private List<PontuacaoMessage> pontuacoes;

    @NotNull(message = "Data e hora de fim são obrigatórias")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    @JsonProperty("dataHoraFim")
    private LocalDateTime dataHoraFim;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PontuacaoMessage {

        @JsonProperty("id")
        private String id;

        @JsonProperty("pontos")
        private Integer pontos;
    }
}