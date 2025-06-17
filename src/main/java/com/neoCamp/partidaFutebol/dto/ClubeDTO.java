package com.neoCamp.partidaFutebol.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClubeDTO {

    private Long Id;
    private String nome;
    private LocalDate dtCriacao;
    private String uf;
    private boolean ativo;


    public ClubeDTO(Long id, String nome, String uf, LocalDate dtCriacao, boolean ativo) {
        this.Id = id;
        this.nome = nome;
        this.uf = uf;
        this.dtCriacao = dtCriacao;
        this.ativo = ativo;
    }


}

