package com.neoCamp.footballMatch.mapper;

import com.neoCamp.footballMatch.dto.StadiumDTO;
import com.neoCamp.footballMatch.entity.StadiumEntity;

import java.util.UUID;

public class StadiumMapper {

    public static StadiumEntity toEntity(StadiumDTO dto) {
        if (dto == null) return null;

        StadiumEntity stadiumEntity = new StadiumEntity();
        // Não é necessário definir o ID manualmente, pois é gerado automaticamente
        stadiumEntity.setName(dto.getName());
        stadiumEntity.setUf(dto.getUf());
        stadiumEntity.setDateCreation(dto.getDateCreation());
        stadiumEntity.setActive(dto.getActive() == null ? true : dto.getActive());

        if (dto.getAddress() != null) {
            stadiumEntity.setAddress(AddressMapper.toEntity(dto.getAddress()));
        }

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

        if (stadiumEntity.getAddress() != null) {
            stadiumDTO.setAddress(AddressMapper.toDto(stadiumEntity.getAddress()));
            stadiumDTO.setCep(stadiumEntity.getAddress().getCep());
        }

        return stadiumDTO;
    }

    // Método para manter compatibilidade com código existente
    public static StadiumDTO toDto(StadiumEntity saved) {
        return toDTO(saved);
    }
}