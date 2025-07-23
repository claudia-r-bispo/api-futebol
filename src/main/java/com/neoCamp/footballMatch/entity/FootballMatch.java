package com.neoCamp.footballMatch.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "partida")
public class FootballMatch {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

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

