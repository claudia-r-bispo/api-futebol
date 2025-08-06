package com.neoCamp.footballMatch.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class RetrospectoDTO {
    private UUID clubId;            // :clubeId
    private Long totalGames;        // COUNT(p)
    private Long victorie;          // Soma de vitórias
    private Long draw;              // Soma de empates
    private Long defeats;           // Soma de derrotas
    private Long goalsPro;          // Gols marcados
    private Long goalsAgainst;      // Gols sofridos

    // Construtor que corresponde à consulta JPQL
    public RetrospectoDTO(UUID clubId, Long totalGames, Long victorie, 
                         Long draw, Long defeats, Long goalsPro, Long goalsAgainst) {
        this.clubId = clubId;
        this.totalGames = totalGames;
        this.victorie = victorie;
        this.draw = draw;
        this.defeats = defeats;
        this.goalsPro = goalsPro;
        this.goalsAgainst = goalsAgainst;
    }

    // Método para calcular o saldo de gols
    public Long getGoalDifference() {
        if (goalsPro == null || goalsAgainst == null) return null;
        return goalsPro - goalsAgainst;
    }
}
