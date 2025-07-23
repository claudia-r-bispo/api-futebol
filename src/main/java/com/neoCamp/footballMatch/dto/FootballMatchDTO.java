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
    private Long homeClubId;
    private Long clubVisitorId;
    private Long stadiumId;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime dateTimeDeparture;

    private Integer homeTeamGoals;
    private Integer goalsVisitor;


}

