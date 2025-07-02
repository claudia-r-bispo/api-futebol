package com.neoCamp.footballMatch.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class FootballMatchDTO {

    private Long id;
    private Long homeClubId;
    private Long clubVisitorId;
    private Long stadiumId;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime dateTimeDeparture;

    private Integer homeTeamGoals;
    private Integer goalsVisitor;

public FootballMatchDTO(Long id, Long homeClubId, Long clubVisitorId, Long stadiumId, LocalDateTime dateTimeDeparture, Integer homeTeamGoals, Integer goalsVisitor) {
        this.id = id;
        this.homeClubId = homeClubId;
        this.clubVisitorId = clubVisitorId;
        this.stadiumId = stadiumId;
        this.dateTimeDeparture = dateTimeDeparture;
        this.homeTeamGoals = homeTeamGoals;
        this.goalsVisitor = goalsVisitor;
    }

}

//clubeMandanteId = homeClubId
//clubeVisitanteId = clubVisitorId
//estadioId = stadiumId
//dataHoraPartida = dateTimeDeparture
//golsMandante = homeTeamGoals
//golsVisitante = goalsVisitor