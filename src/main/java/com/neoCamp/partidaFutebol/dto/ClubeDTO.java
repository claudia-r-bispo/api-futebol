package com.neoCamp.partidaFutebol.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClubeDTO {

    private Long Id;
    private String nome;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dtCriacao;
    private String uf;
    private boolean ativo;
    private Long clubeMandante;
    private Long clubeVisitante;
    private Long clubeMandanteId;
    private Long clubeVisitanteId;


}

