package com.neoCamp.partidaFutebol.mapper;

import com.neoCamp.partidaFutebol.dto.EstadioDTO;
import com.neoCamp.partidaFutebol.entity.EstadioEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EstadioMapperTest {

    @Test
    void testToEntity() {
        LocalDate dataCriacao = LocalDate.of(2001, 7, 8);
        EstadioDTO dto = new EstadioDTO(
                12L, "Arena Castelão", "CE", dataCriacao, false,
                null, null, null, null
        );

        EstadioEntity entity = EstadioMapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getNome(), entity.getNome());
        assertEquals(dto.getUf(), entity.getUf());
        assertEquals(dto.getDtCriacao(), entity.getDtCriacao());
        assertFalse(entity.isAtivo());
    }

    @Test
    void testToEntityWithAtivoNull() {
        LocalDate dataCriacao = LocalDate.of(1982, 6, 3);
        EstadioDTO dto = new EstadioDTO();

        EstadioEntity entity = EstadioMapper.toEntity(dto);

        assertNotNull(entity);
        // Ativo deve ser true por default
        assertTrue(entity.isAtivo());
    }

    @Test
    void testToEntityWithNull() {
        assertNull(EstadioMapper.toEntity(null));
    }

    @Test
    void testToDTO() {
        LocalDate dataCriacao = LocalDate.of(2014, 11, 19);
        EstadioEntity entity = new EstadioEntity(22L, "Allianz Parque", "SP", true, dataCriacao);

        EstadioDTO dto = EstadioMapper.toDTO(entity);

        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getNome(), dto.getNome());
        assertEquals(entity.getUf(), dto.getUf());
        assertEquals(entity.getDtCriacao(), dto.getDtCriacao());
        assertTrue(dto.getAtivo());
    }

    @Test
    void testToDTOWithNull() {
        assertNull(EstadioMapper.toDTO(null));
        assertNull(EstadioMapper.toDto(null));
    }
}