package com.neoCamp.footballMatch.mapper;

import com.neoCamp.footballMatch.dto.ClubDTO;
import com.neoCamp.footballMatch.entity.ClubEntity;

import java.util.UUID;

public class ClubMapper {

    public static ClubEntity toEntity(ClubDTO dto) {
        if (dto == null) return null;

        boolean active = dto.getActive() != null ? dto.getActive() : true;
        ClubEntity entity = new ClubEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setUf(dto.getUf());
        entity.setDateCreation(dto.getDateCreation());
        entity.setActive(active);
        return entity;
    }

    public static ClubDTO toDto(ClubEntity entity) {
        if (entity == null) return null;

        ClubDTO dto = new ClubDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setUf(entity.getUf());
        dto.setDateCreation(entity.getDateCreation());
        dto.setActive(entity.isActive());
        return dto;
    }
}