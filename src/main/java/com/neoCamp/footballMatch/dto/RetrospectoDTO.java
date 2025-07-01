package com.neoCamp.footballMatch.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RetrospectoDTO {
    private Long clubId;
    private Long totalGames;
    private Long victorie;
    private Long draw;
    private Long defeats;
    private Long goalsPro;
    private Long goalsAgainst;
}


//totalJogos= totalGames
//empate = draw
//derrotas = defeats