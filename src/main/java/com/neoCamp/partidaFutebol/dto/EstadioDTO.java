package com.neoCamp.partidaFutebol.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadioDTO {


    private String nome;

    private String Uf;

    public EstadioDTO(Long id, Object nome, Object uf, Object dtCriacao, Object ativo) {
    }
}
