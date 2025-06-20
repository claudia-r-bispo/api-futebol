package com.neoCamp.partidaFutebol.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;
import jakarta.persistence.Id;

import java.time.LocalDate;


@Entity
@Data
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class EstadioEntity {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable=false)
        private String nome;


    @Column(nullable=false, length=2)
    private String uf;

    @Column(nullable=false)
    private boolean ativo = true;

    @Column(nullable=false)
    private LocalDate dtCriacao;

//    public EstadioEntity(Long id, String nome, String uf, LocalDate dtCriacao, boolean ativo) {
//        this.id = id;
//        this.nome = nome;
//        this.uf =uf;
//        this.dtCriacao = dtCriacao;
//        this.ativo = ativo;
//    }
//
//    public EstadioEntity() {
//
//    }
}

