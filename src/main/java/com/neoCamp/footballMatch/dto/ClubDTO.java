package com.neoCamp.footballMatch.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClubDTO {

    private Long id;
    private String name;
    private String uf;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateCreation;
    private Boolean active;
    private Long homeClub;
    private Long clubVisitor;
    private Long homeClubId;
    private Long clubVisitorId;

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
