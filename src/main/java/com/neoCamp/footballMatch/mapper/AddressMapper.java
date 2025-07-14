package com.neoCamp.footballMatch.mapper;

import com.neoCamp.footballMatch.dto.AddressDTO;
import com.neoCamp.footballMatch.dto.AddressDTO;
import com.neoCamp.footballMatch.entity.AddressEntity;
import com.neoCamp.footballMatch.entity.AddressEntity;

public class AddressMapper {

    public static AddressEntity toEntity(AddressDTO dto) {
        if (dto == null) return null;

       AddressEntity entity = new AddressEntity();
        entity.setId(dto.getId());
        entity.setLogradouro(dto.getLogradouro());
        entity.setCidade(dto.getCidade());
        entity.setEstado(dto.getEstado());
        entity.setCep(dto.getCep());
        return entity;
    }

    public static AddressDTO toDto(AddressEntity entity) {
        if (entity == null) return null;

        AddressDTO dto = new AddressDTO();
        dto.setId(entity.getId());
        dto.setLogradouro(entity.getLogradouro());
        dto.setCidade(entity.getCidade());
        dto.setEstado(entity.getEstado());
        dto.setCep(entity.getCep());
        return dto;
    }
}
