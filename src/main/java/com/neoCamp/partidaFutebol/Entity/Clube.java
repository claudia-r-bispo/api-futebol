package com.neoCamp.partidaFutebol.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

public class Clube {
    @Entity
    @Data
    @Getter
    @Setter

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @Column(nullable=false)
        private String nome;
        @Column(nullable=false, length=2)
        private String estado;
        @Column(nullable=false)
        private LocalDate dataCriacao;
        @Column(nullable=false)
        private boolean ativo;

}
