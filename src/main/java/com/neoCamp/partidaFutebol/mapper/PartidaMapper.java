package com.neoCamp.partidaFutebol.mapper;

import com.neoCamp.partidaFutebol.dto.PartidaDTO;
import com.neoCamp.partidaFutebol.entity.PartidaEntity;
import com.neoCamp.partidaFutebol.entity.ClubeEntity;
import com.neoCamp.partidaFutebol.entity.EstadioEntity;

public class PartidaMapper {
    public static PartidaDTO toDTO(PartidaEntity partidaEntity) {
        if (partidaEntity == null) return null;
            return new PartidaDTO(
                    partidaEntity.getId(),
                    partidaEntity.getClubeMandante() != null ? partidaEntity.getClubeMandante().getId() : null,
                    partidaEntity.getClubeVisitante() != null ? partidaEntity.getClubeVisitante().getId() : null,
                    partidaEntity.getEstadio() != null ? partidaEntity.getEstadio().getId() : null,
                    partidaEntity.getDataHoraPartida(),
                    partidaEntity.getGolsMandante(),
                    partidaEntity.getGolsVisitante()
            );
        }
        public static PartidaEntity toEntity(PartidaDTO dto, ClubeEntity mandante, ClubeEntity visitante, EstadioEntity estadio) {
            if (dto == null) return null;

            PartidaEntity entity = new PartidaEntity();
            entity.setId(dto.getId());
            entity.setClubeMandante(mandante);
            entity.setClubeVisitante(visitante);
            entity.setEstadio(estadio);
            entity.setDataHoraPartida(dto.getDataHoraPartida());
            entity.setGolsMandante(dto.getGolsMandante());
            entity.setGolsVisitante(dto.getGolsVisitante());
                return entity;
            }
        }
