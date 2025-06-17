package com.neoCamp.partidaFutebol.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;




    @Getter
    @Setter
    public class EstadioDTO {

        private String nome;
        private String uf;

        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate dtCriacao;
        private Boolean ativo;

        public EstadioDTO() {
        }

        public EstadioDTO(String nome, String uf, LocalDate dtCriacao, Boolean ativo) {
            this.nome = nome;
            this.uf = uf;
            this.dtCriacao = dtCriacao;
            this.ativo = ativo;
        }
    }
