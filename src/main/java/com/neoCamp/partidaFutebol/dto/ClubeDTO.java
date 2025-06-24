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

    private Long id;
    private String nome;
    private String uf;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dtCriacao;
    private Boolean ativo;
    private Long clubeMandante;
    private Long clubeVisitante;
    private Long clubeMandanteId;
    private Long clubeVisitanteId;

    public ClubeDTO(Long id, String nome, String uf, LocalDate dtCriacao, Boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.uf = uf;
        this.dtCriacao = dtCriacao;
        this.ativo = ativo;
    }

    public boolean isAtivo() {
        return ativo != null && ativo;
    }
}

