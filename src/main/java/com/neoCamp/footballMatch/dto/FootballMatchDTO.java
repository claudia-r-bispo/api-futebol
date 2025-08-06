package com.neoCamp.footballMatch.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FootballMatchDTO {
    private UUID id;
    private UUID homeClubId;        // Alterado de Long para UUID
    private UUID clubVisitorId;     // Alterado de Long para UUID
    private UUID stadiumId;         // Alterado de Long para UUID

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime dateTimeDeparture;

    private Integer homeTeamGoals;
    private Integer goalsVisitor;
}
