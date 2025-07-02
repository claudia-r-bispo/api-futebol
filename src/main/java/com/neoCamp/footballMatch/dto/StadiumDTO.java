package com.neoCamp.footballMatch.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;


    @Getter
    @Setter
    @NoArgsConstructor @AllArgsConstructor
    public class StadiumDTO {

        private Long id;
        private String name;
        private String uf;
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate dateCreation;
        private Long clubVisitor;
        private Long homeClub;
        private Integer homeTeamGoals;
        private Integer goalsVisitor;

        private Boolean active;
        public Boolean getActive() {
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