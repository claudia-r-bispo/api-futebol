package com.neoCamp.footballMatch.mapper;

import com.neoCamp.footballMatch.dto.ClubDTO;
import com.neoCamp.footballMatch.entity.ClubEntity;

public class ClubMapper {

    public static ClubEntity toEntity(ClubDTO dto) {
        if (dto == null) return null;
        // Se active é null, defina como true!
        boolean active = dto.getActive() != null ? dto.getActive() : true;
        return new ClubEntity(
                dto.getId(),
                dto.getName(),
                dto.getUf(),
                dto.getDateCreation(),
                active
        );
    }

    public static ClubDTO toDto(ClubEntity entity) {
        if (entity == null) return null;
        return new ClubDTO(
                entity.getId(),
                entity.getName(),
                entity.getUf(),
                entity.getDateCreation(),
                entity.isActive()
        );
    }

}