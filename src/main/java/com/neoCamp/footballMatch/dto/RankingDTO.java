package com.neoCamp.footballMatch.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RankingDTO {
    private Long clubId;
    private String clubName;
    private Long games;
    private Long victorie;
    private Long draw;
    private Long defeat;
    private Long goalsPro;
    private Long goalsAgainst;

    private Long points;

    public Long getSaldoGols() {
        if (goalsPro == null || goalsAgainst == null) return null;
        return goalsPro - goalsAgainst;
    }

}
//empate = draw
//derrotas = defeats
//golsPro = goalsPro
//golsContra = goalsagainst
//pontos = points