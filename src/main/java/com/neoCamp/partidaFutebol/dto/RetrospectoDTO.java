package com.neoCamp.partidaFutebol.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RetrospectoDTO {
    private Long clubeId;
    private Long totalJogos;
    private Long vitorias;
    private Long empates;
    private Long derrotas;
    private Long golsPro;
    private Long golsContra;
}
