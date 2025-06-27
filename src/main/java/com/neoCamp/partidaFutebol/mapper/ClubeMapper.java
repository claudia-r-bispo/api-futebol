package com.neoCamp.partidaFutebol.mapper;

import com.neoCamp.partidaFutebol.dto.ClubeDTO;
import com.neoCamp.partidaFutebol.entity.ClubeEntity;

public class ClubeMapper {

    public static ClubeDTO toDto(ClubeEntity entity) {
        if (entity == null) return null;
        return new ClubeDTO(entity.getId(),
                entity.getNome(),
                entity.getUf(),
                entity.getDtCriacao(),
                entity.isAtivo());
    }
    public static ClubeEntity toEntity(ClubeDTO dto) {
        if (dto == null) return null;
        ClubeEntity entity = new ClubeEntity();
        entity.setId(dto.getId());
        entity.setNome(dto.getNome());
        entity.setUf(dto.getUf());
        entity.setDtCriacao(dto.getDtCriacao());
        entity.setAtivo(dto.getAtivo() != null ? dto.getAtivo() : true);
        return entity;
    }


}

