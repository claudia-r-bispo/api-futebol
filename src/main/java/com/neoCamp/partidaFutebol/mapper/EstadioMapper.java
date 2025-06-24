package com.neoCamp.partidaFutebol.mapper;

import com.neoCamp.partidaFutebol.dto.EstadioDTO;
import com.neoCamp.partidaFutebol.entity.EstadioEntity;

public class EstadioMapper {

    public static EstadioEntity toEntity(EstadioDTO dto) {
        if (dto == null) return null;
        EstadioEntity estadioEntity = new EstadioEntity();
        estadioEntity.setId(dto.getId());
        estadioEntity.setNome(dto.getNome());
        estadioEntity.setUf(dto.getUf());
        estadioEntity.setDtCriacao(dto.getDtCriacao());
        estadioEntity.setAtivo(dto.getAtivo() != null ? dto.getAtivo() : true);
        return estadioEntity;
    }

    public static EstadioDTO toDTO(EstadioEntity  estadioEntity) {
        if (estadioEntity == null) {
            return null;

        }

        EstadioDTO estadioDTO = new EstadioDTO();
        estadioDTO.setId(estadioEntity.getId());
        estadioDTO.setNome(estadioEntity.getNome());
        estadioDTO.setUf(estadioEntity.getUf());
        estadioDTO.setDtCriacao(estadioEntity.getDtCriacao());
        estadioDTO.setAtivo(estadioEntity.isAtivo());
        return estadioDTO;
    }

    public static EstadioDTO toDto(EstadioEntity saved) {
        return toDTO(saved);
    }
}











