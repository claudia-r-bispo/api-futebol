package com.neoCamp.partidaFutebol.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import java.time.LocalDate;


@Entity
@Data
public class Estadio {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable=false)
        private String nome;


    @Column(nullable=false, length=2)
    private String Uf;

    @Column(nullable=false)
    private boolean ativo;

    @Column(nullable=false)
    private LocalDate dtCriacao;

    public Estadio(Long id, String nome, String uf, LocalDate dtCriacao, boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.Uf =uf;
        this.dtCriacao = dtCriacao;
        this.ativo = ativo;
    }

    public Estadio() {

    }
}

