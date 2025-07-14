package com.neoCamp.footballMatch.dto;

import lombok.*;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;


    @Data
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

        // NOVO: Campo para CEP e dados do endereço
        private String cep;
        private AddressDTO address;

        private Boolean active;
        public Boolean getActive() {
            return active;
        }

    }


