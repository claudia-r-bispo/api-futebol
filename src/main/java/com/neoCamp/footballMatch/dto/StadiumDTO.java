package com.neoCamp.footballMatch.dto;

import lombok.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor 
@AllArgsConstructor
public class StadiumDTO {
    private UUID id;
        private String name;
        private String uf;
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate dateCreation;
        private UUID clubVisitor;
        private UUID homeClub;
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


