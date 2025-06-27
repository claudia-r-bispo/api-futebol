package com.neoCamp.partidaFutebol.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfrontoDiretoDTO {
    private Long clubeAId;
    private Long clubeBId;
    private Long jogos;
    private Long vitoriasClubeA;
    private Long vitoriasClubeB;
    private Long empates;
}