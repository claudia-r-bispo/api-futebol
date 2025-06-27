package com.neoCamp.partidaFutebol.mapper;

import com.neoCamp.partidaFutebol.dto.ClubeDTO;
import com.neoCamp.partidaFutebol.entity.ClubeEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ClubeMapperTest {

    @Test
    void testToDto() {
        LocalDate data = LocalDate.of(2021, 5, 20);
        ClubeEntity entity = new ClubeEntity(10L, "Corinthians", "SP", data, true);

        ClubeDTO dto = ClubeMapper.toDto(entity);

        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getNome(), dto.getNome());
        assertEquals(entity.getUf(), dto.getUf());
        assertEquals(entity.getDtCriacao(), dto.getDtCriacao());
        assertEquals(entity.isAtivo(), dto.getAtivo());
    }

    @Test
    void testToDtoWithNull() {
        assertNull(ClubeMapper.toDto(null));
    }

    @Test
    void testToEntity() {
        LocalDate data = LocalDate.of(2022, 6, 30);
        ClubeDTO dto = new ClubeDTO(5L, "Atlético-MG", "MG", data, false);

        ClubeEntity entity = ClubeMapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getNome(), entity.getNome());
        assertEquals(dto.getUf(), entity.getUf());
        assertEquals(dto.getDtCriacao(), entity.getDtCriacao());
        assertEquals(dto.getAtivo(), entity.isAtivo());
    }

    @Test
    void testToEntityWithAtivoNull() {
        // testa default ativo "true" quando DTO.getAtivo() é null
        LocalDate data = LocalDate.now();
        ClubeDTO dto = new ClubeDTO(6L, "Cuiabá", "MT", data, null);

        ClubeEntity entity = ClubeMapper.toEntity(dto);

        assertNotNull(entity);
        assertTrue(entity.isAtivo());
    }

    @Test
    void testToEntityWithNull() {
        assertNull(ClubeMapper.toEntity(null));
    }
}