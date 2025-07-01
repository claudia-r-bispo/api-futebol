package com.neoCamp.footballMatch.mapper;

import com.neoCamp.footballMatch.dto.StadiumDTO;
import com.neoCamp.footballMatch.entity.StadiumEntity;

public class StadiumMapper {

    public static StadiumEntity toEntity(StadiumDTO dto) {
        if (dto == null) return null;
        StadiumEntity stadiumEntity = new StadiumEntity();
        stadiumEntity.setId(dto.getId());
        stadiumEntity.setName(dto.getName());
        stadiumEntity.setUf(dto.getUf());
        stadiumEntity.setDateCreation(dto.getDateCreation());
        stadiumEntity.setActive(dto.getActive() == null ? true : dto.getActive());
        return stadiumEntity;
    }


    public static StadiumDTO toDTO(StadiumEntity stadiumEntity) {
        if (stadiumEntity == null) {
            return null;

        }

        StadiumDTO stadiumDTO = new StadiumDTO();
        stadiumDTO.setId(stadiumEntity.getId());
        stadiumDTO.setName(stadiumEntity.getName());
        stadiumDTO.setUf(stadiumEntity.getUf());
        stadiumDTO.setDateCreation(stadiumEntity.getDateCreation());
        stadiumDTO.setActive(stadiumEntity.isActive());
        return stadiumDTO;
    }

    public static StadiumDTO toDto(StadiumEntity saved) {
        return toDTO(saved);
    }
}











