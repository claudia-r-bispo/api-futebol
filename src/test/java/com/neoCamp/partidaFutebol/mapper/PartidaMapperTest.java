package com.neoCamp.partidaFutebol.mapper;

import com.neoCamp.partidaFutebol.dto.PartidaDTO;
import com.neoCamp.partidaFutebol.entity.ClubeEntity;
import com.neoCamp.partidaFutebol.entity.EstadioEntity;
import com.neoCamp.partidaFutebol.entity.PartidaEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PartidaMapperTest {

    @Test
    void testToDTO() {
        ClubeEntity mandante = new ClubeEntity(1L, "Palmeiras", "SP", LocalDate.of(1914,8,26), true);
        ClubeEntity visitante = new ClubeEntity(2L, "Cruzeiro", "MG", LocalDate.of(1921,1,2), true);
        EstadioEntity estadio = new EstadioEntity(3L, "Allianz Parque", "SP", true, LocalDate.of(2014,11,19));
        LocalDateTime data = LocalDateTime.of(2024,7,1,16,30);

        PartidaEntity entity = new PartidaEntity(10L, mandante, visitante, estadio, data, 3, 1);

        PartidaDTO dto = PartidaMapper.toDTO(entity);

        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(mandante.getId(), dto.getClubeMandanteId());
        assertEquals(visitante.getId(), dto.getClubeVisitanteId());
        assertEquals(estadio.getId(), dto.getEstadioId());
        assertEquals(data, dto.getDataHoraPartida());
        assertEquals(entity.getGolsMandante(), dto.getGolsMandante());
        assertEquals(entity.getGolsVisitante(), dto.getGolsVisitante());
    }

    @Test
    void testToDTOWithNulls() {
        // Entidade sem mandante/visitante/estadio
        LocalDateTime data = LocalDateTime.now();
        PartidaEntity entity = new PartidaEntity(15L, null, null, null, data, 0, 0);

        PartidaDTO dto = PartidaMapper.toDTO(entity);

        assertNotNull(dto);
        assertNull(dto.getClubeMandanteId());
        assertNull(dto.getClubeVisitanteId());
        assertNull(dto.getEstadioId());
        assertEquals(data, dto.getDataHoraPartida());
    }

    @Test
    void testToDTOWithNullInput() {
        assertNull(PartidaMapper.toDTO(null));
    }

    @Test
    void testToEntity() {
        Long mandanteId = 1L, visitanteId = 2L, estadioId = 3L;
        LocalDateTime data = LocalDateTime.of(2024, 7, 1, 18, 0);
        PartidaDTO dto = new PartidaDTO(99L, mandanteId, visitanteId, estadioId, data, 4, 2);

        ClubeEntity mandante = new ClubeEntity(mandanteId, "Home", "SP", LocalDate.of(2000,1,1), true);
        ClubeEntity visitante = new ClubeEntity(visitanteId, "Away", "MG", LocalDate.of(1998,12,31), true);
        EstadioEntity estadio = new EstadioEntity(estadioId, "Mineirão", "MG", true, LocalDate.of(1965,9,5));

        PartidaEntity entity = PartidaMapper.toEntity(dto, mandante, visitante, estadio);

        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(mandante, entity.getClubeMandante());
        assertEquals(visitante, entity.getClubeVisitante());
        assertEquals(estadio, entity.getEstadio());
        assertEquals(data, entity.getDataHoraPartida());
        assertEquals(dto.getGolsMandante(), entity.getGolsMandante());
        assertEquals(dto.getGolsVisitante(), entity.getGolsVisitante());
    }

    @Test
    void testToEntityWithNullInput() {
        assertNull(PartidaMapper.toEntity(null, null, null, null));
    }

    @Test
    void testToDtoEqualsToDTO() {
        ClubeEntity mandante = new ClubeEntity(5L, "Galo", "MG", LocalDate.of(1908,3,25), true);
        ClubeEntity visitante = new ClubeEntity(6L, "Vasco", "RJ", LocalDate.of(1898,8,21), true);
        EstadioEntity estadio = new EstadioEntity(8L, "Independência", "MG", true, LocalDate.of(1950,6,25));
        LocalDateTime data = LocalDateTime.of(2025,3,18,19,0);
        PartidaEntity entity = new PartidaEntity(20L, mandante, visitante, estadio, data, 1, 2);

        // Deve ser igual pois toDto e toDTO fazem a mesma função
        PartidaDTO dtoWithDto = PartidaMapper.toDto(entity);
        PartidaDTO dtoWithDTO = PartidaMapper.toDTO(entity);

        assertNotNull(dtoWithDto);
        assertEquals(dtoWithDto.getClubeMandanteId(), dtoWithDTO.getClubeMandanteId());
        assertEquals(dtoWithDto.getClubeVisitanteId(), dtoWithDTO.getClubeVisitanteId());
        assertEquals(dtoWithDto.getEstadioId(), dtoWithDTO.getEstadioId());
        assertEquals(dtoWithDto.getId(), dtoWithDTO.getId());
    }

    @Test
    void testToDtoWithNull() {
        assertNull(PartidaMapper.toDto(null));
    }
}