package com.neoCamp.partidaFutebol.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;




    @Getter
    @Setter
    @NoArgsConstructor @AllArgsConstructor
    public class EstadioDTO {

        private Long id;
        private String nome;
        private String uf;

        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate dtCriacao;
        private Boolean ativo;
        private Long clubeVisitante;
        private Long clubeMandante;
        private Integer golsMandante;
        private Integer golsVisitante;




//        public EstadioDTO() {
//        }
//
//        public EstadioDTO(String nome, String uf, LocalDate dtCriacao, Boolean ativo) {
//            this.nome = nome;
//            this.uf = uf;
//            this.dtCriacao = dtCriacao;
//            this.ativo = ativo;
//        }
    }
