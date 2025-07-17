package com.neoCamp.footballMatch.mapper;

import com.neoCamp.footballMatch.dto.AddressDTO;
import com.neoCamp.footballMatch.entity.AddressEntity;

public class AddressMapper {

    public static AddressEntity toEntity(AddressDTO dto) {
        if (dto == null) return null;

        AddressEntity entity = new AddressEntity();
        entity.setId(dto.getId());
        entity.setStreet(dto.getLogradouro());
        entity.setCity(dto.getCidade());
        entity.setState(dto.getEstado());
        entity.setZipCode(dto.getCep());
        // Não existe getNumero() em AddressDTO, então não setar number
        return entity;
    }

    public static AddressDTO toDto(AddressEntity entity) {
        if (entity == null) return null;

        AddressDTO dto = new AddressDTO();
        dto.setId(entity.getId());
        dto.setLogradouro(entity.getStreet());
        dto.setCidade(entity.getCity());
        dto.setEstado(entity.getState());
        dto.setCep(entity.getZipCode());
        // Não existe setNumero() em AddressDTO, então não setar numero
        return dto;
    }
}
