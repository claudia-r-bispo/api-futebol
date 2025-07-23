package com.neoCamp.footballMatch.mapper;

import com.neoCamp.footballMatch.dto.FootballMatchDTO;
import com.neoCamp.footballMatch.entity.FootballMatch;
import com.neoCamp.footballMatch.entity.ClubEntity;
import com.neoCamp.footballMatch.entity.StadiumEntity;

public class FootballMatchMapper {

    public static FootballMatchDTO toDTO(FootballMatch footballMatch) {
        if (footballMatch == null) return null;

        return new FootballMatchDTO(
                footballMatch.getId(),
                footballMatch.getHomeClub() != null ? footballMatch.getHomeClub().getId() : null,
                footballMatch.getClubVisitor() != null ? footballMatch.getClubVisitor().getId() : null,
                footballMatch.getStadium() != null ? footballMatch.getStadium().getId() : null,
                footballMatch.getDateTimeDeparture(),
                footballMatch.getHomeTeamGoals(),
                footballMatch.getGoalsVisitor()
        );
    }

    public static FootballMatch toEntity(FootballMatchDTO dto, ClubEntity homeClub, ClubEntity visitor, StadiumEntity stadium) {
        if (dto == null) return null;

        FootballMatch entity = new FootballMatch();
        entity.setId(dto.getId());
        entity.setHomeClub(homeClub);
        entity.setClubVisitor(visitor);
        entity.setStadium(stadium);
        entity.setDateTimeDeparture(dto.getDateTimeDeparture());
        entity.setHomeTeamGoals(dto.getHomeTeamGoals());
        entity.setGoalsVisitor(dto.getGoalsVisitor());

        return entity;
    }

    public static FootballMatchDTO toDto(FootballMatch saved) {
        return toDTO(saved); // ← Reutilizar o método toDTO
    }
}



