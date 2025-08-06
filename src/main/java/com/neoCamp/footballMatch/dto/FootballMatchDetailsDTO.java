package com.neoCamp.footballMatch.dto;

import com.neoCamp.footballMatch.entity.FootballMatch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FootballMatchDetailsDTO {
    private UUID id;                // ID da partida
    private UUID homeClubId;        // ID do clube mandante
    private String homeClubName;
    private String homeClubLogo;
    private UUID clubVisitorId;     // ID do clube visitante
    private String clubVisitorName;
    private String clubVisitorLogo;
    private UUID stadiumId;         // ID do estádio
    private String stadiumName;     // Nome do estádio
    private LocalDateTime dateTimeDeparture;
    private Integer homeTeamGoals;
    private Integer goalsVisitor;

    public static FootballMatchDetailsDTO fromEntity(FootballMatch match) {
        if (match == null) {
            return null;
        }

        FootballMatchDetailsDTO dto = new FootballMatchDetailsDTO();
        dto.setId(match.getId());

        // Mapeamento do time mandante
        if (match.getHomeClub() != null) {
            dto.setHomeClubId(match.getHomeClub().getId());
            dto.setHomeClubName(match.getHomeClub().getName());
            // Se a entidade ClubEntity tiver logo, descomente a linha abaixo
            // dto.setHomeClubLogo(match.getHomeClub().getLogoUrl());
        }

        // Mapeamento do time visitante
        if (match.getClubVisitor() != null) {
            dto.setClubVisitorId(match.getClubVisitor().getId());
            dto.setClubVisitorName(match.getClubVisitor().getName());
            // Se a entidade ClubEntity tiver logo, descomente a linha abaixo
            // dto.setClubVisitorLogo(match.getClubVisitor().getLogoUrl());
        }

        // Mapeamento do estádio
        if (match.getStadium() != null) {
            dto.setStadiumId(match.getStadium().getId());
            dto.setStadiumName(match.getStadium().getName());
        }

        // Mapeamento dos demais campos
        dto.setDateTimeDeparture(match.getDateTimeDeparture());
        dto.setHomeTeamGoals(match.getHomeTeamGoals());
        dto.setGoalsVisitor(match.getGoalsVisitor());

        return dto;
    }
}