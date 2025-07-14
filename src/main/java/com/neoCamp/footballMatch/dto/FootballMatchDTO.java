package com.neoCamp.footballMatch.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FootballMatchDTO {

    private Long id;
    private Long homeClubId;
    private Long clubVisitorId;
    private Long stadiumId;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime dateTimeDeparture;

    private Integer homeTeamGoals;
    private Integer goalsVisitor;


}

