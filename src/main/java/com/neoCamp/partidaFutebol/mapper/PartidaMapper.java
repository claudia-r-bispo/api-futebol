package com.neoCamp.partidaFutebol.mapper;

import com.neoCamp.partidaFutebol.dto.PartidaDTO;
import com.neoCamp.partidaFutebol.entity.PartidaEntity;
import org.springframework.data.support.PersistableIsNewStrategy;

public class PartidaMapper {
    public static PartidaEntity mapToDTO(PartidaEntity partidaEntity) {
        if (partidaEntity == null) {
            return new PartidaDTO(
                    partida.getId(),
                    partida.getClubeMandante().getId(),
                    partida.getClubeVisitante().getId(),
                    partida.getEstadio().getId(),
                    partida.getDataHora(),
                    partida.getGolsMandante(),
                    partida.getGolsVisitante());
        }
        public static PartidaEntity toEntity (
                PartidaDTO dto,
                ClubeEntity mandante,
                ClubeEntity visitante,
                EstadioEntity estadio){
            {
                if (dto == null) return null;
                PartidaEntity partidaEntity = new PartidaEntity();
                partidaEntity.setId(dto.getId());
                partidaEntity.setClubeMandante(mandante);
                partidaEntity.setClubeVisitante(visitante);
                partidaEntity.setEstadio(estadio);
                partidaEntity.setDataHora(dto.getDataHora());
                partidaEntity.setGolsMandante(dto.getGolsMandante());
                partidaEntity.setGolsVisitante(dto.getGolsVisitante());
                return partidaEntity;
            }
        }
    }}