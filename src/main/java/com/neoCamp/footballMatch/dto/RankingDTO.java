package com.neoCamp.footballMatch.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RankingDTO {
    private UUID clubId;        // p.homeClub.id
    private String clubName;    // p.homeClub.name
    private Long jogos;         // COUNT(p)
    private Long vitorias;      // SUM(CASE WHEN p.homeTeamGoals > p.goalsVisitor THEN 1 ELSE 0 END)
    private Long empates;       // SUM(CASE WHEN p.homeTeamGoals = p.goalsVisitor THEN 1 ELSE 0 END)
    private Long derrotas;      // SUM(CASE WHEN p.homeTeamGoals < p.goalsVisitor THEN 1 ELSE 0 END)
    private Long golsPro;       // SUM(p.homeTeamGoals)
    private Long golsContra;    // SUM(p.goalsVisitor)
    private Long pontos;        // Soma dos pontos (3 para vitória, 1 para empate)
    private Long saldoGols;     // Saldo de gols

    // Construtor que corresponde à consulta JPQL
    public RankingDTO(UUID clubId, String clubName, Long jogos, Long vitorias, 
                     Long empates, Long derrotas, Long golsPro, Long golsContra,
                     Long pontos) {
        this.clubId = clubId;
        this.clubName = clubName;
        this.jogos = jogos;
        this.vitorias = vitorias;
        this.empates = empates;
        this.derrotas = derrotas;
        this.golsPro = golsPro;
        this.golsContra = golsContra;
        this.pontos = pontos;
        // Calcula o saldo de gols baseado nos gols pró e contra
        this.saldoGols = (golsPro != null && golsContra != null) ? golsPro - golsContra : null;
    }

    // Método para cálculo do saldo de gols (mantido para compatibilidade)
    public Long getSaldoGols() {
        if (golsPro == null || golsContra == null) return null;
        return golsPro - golsContra;
    }
}
