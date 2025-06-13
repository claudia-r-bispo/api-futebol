package com.neoCamp.partidaFutebol.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

public class Partida {
    @Entity
    @Getter
    @Setter

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name="mandante_id", nullable=false)
        private Clube clubeMandante;

        @ManyToOne
        @JoinColumn(name="visitante_id", nullable=false)
        private Clube clubeVisitante;

        @ManyToOne
        @JoinColumn(name="estadio_id", nullable=false)
        private Estadio estadio;

        @Column(nullable=false)
        private LocalDateTime dataHora;


        private Integer golsMandante;
        private Integer golsVisitante;


    }

