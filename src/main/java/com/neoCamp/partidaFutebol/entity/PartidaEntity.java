package com.neoCamp.partidaFutebol.entity;

import jakarta.persistence.*;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Getter  @Setter @NoArgsConstructor @AllArgsConstructor
@Table(name = "partida")
public class PartidaEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "mandante_id", nullable = false)
    private ClubeEntity clubeMandante;

    @ManyToOne
    @JoinColumn(name = "visitante_id", nullable = false)
    private ClubeEntity clubeVisitante;

    @ManyToOne
    @JoinColumn(name = "estadio_id", nullable = false)
    private EstadioEntity estadio;

    @Column(nullable = false)
    private LocalDateTime dataHoraPartida;

    @Column(nullable = false)
    private Integer golsMandante;

    @Column(nullable = false)
    private Integer golsVisitante;


}

