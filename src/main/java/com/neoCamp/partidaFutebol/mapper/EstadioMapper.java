package com.neoCamp.partidaFutebol.mapper;

import com.neoCamp.partidaFutebol.dto.EstadioDTO;
import com.neoCamp.partidaFutebol.entity.EstadioEntity;

public class EstadioMapper {
    public static EstadioDTO  toDto(EstadioEntity  estadioEntity){
        if (estadioEntity == null) return null;{
            return new EstadioDTO(entity.getId(), entity.getNome(), entity.getDtCriacao(), entity.getUf(), entity.isAtivo());
        }
        public static EstadioEntity toEntity(EstadioDTO dto) {
            if (dto == null) return null;
            EstadioEntity estadioEntity = new EstadioEntity();
            estadioEntity.setId(dto.getId());
            estadioEntity.setNome(dto.getNome());
            estadioEntity.setDtCriacao(dto.getDtCriacao());
            estadioEntity.setUf(dto.getUf());
            estadioEntity.setAtivo(dto.isAtivo() != null ? dto.isAtivo() : true);
            return estadioEntity;
        }
    }
}
