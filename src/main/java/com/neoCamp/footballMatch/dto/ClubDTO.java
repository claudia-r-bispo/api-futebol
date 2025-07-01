package com.neoCamp.footballMatch.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClubDTO {

    public Long id;
    public String name;
    public String uf;
    @JsonFormat(pattern = "yyyy-MM-dd")
    public LocalDate dateCreation;
    public Boolean active;
    public Long homeClub;
    public Long clubVisitor;
    public Long homeClubId;
    public Long clubVisitorId;

    public ClubDTO(Long id, String name, String uf, LocalDate dateCreation, Boolean active) {
        this.id = id;
        this.name = name;
        this.uf = uf;
        this.dateCreation = dateCreation;
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }
}

//dataHoraPartida = dateTimeDeparture
//dataCriacao = dateCreation
//ativo = active
//clubeVisitante = clubVisitor
//clubeMandante = homeClub
//golsMandante = homeTeamGoals
//golsVisitante = goalsVisitor
