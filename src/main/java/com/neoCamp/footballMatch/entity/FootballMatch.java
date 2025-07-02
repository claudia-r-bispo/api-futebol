package com.neoCamp.footballMatch.entity;

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
public class FootballMatch {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "mandante_id", nullable = false)
    private ClubEntity homeClub;

    @ManyToOne
    @JoinColumn(name = "visitante_id", nullable = false)
    private ClubEntity clubVisitor;

    @ManyToOne
    @JoinColumn(name = "estadio_id", nullable = false)
    private StadiumEntity stadium;

    @Column(nullable = false)
    private LocalDateTime dateTimeDeparture;

    @Column(nullable = false)
    private Integer homeTeamGoals;

    @Column(nullable = false)
    private Integer goalsVisitor;


}

