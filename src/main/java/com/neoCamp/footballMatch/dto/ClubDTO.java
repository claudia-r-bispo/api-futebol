package com.neoCamp.footballMatch.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClubDTO {

    private UUID id;
    private String name;
    private String uf;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateCreation;
    private Boolean active;
    private UUID homeClub;
    private UUID clubVisitor;
    private UUID homeClubId;
    private UUID clubVisitorId;

    public ClubDTO(UUID id, String name, String uf, LocalDate dateCreation, Boolean active) {
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
