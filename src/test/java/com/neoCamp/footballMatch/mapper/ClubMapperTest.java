package com.neoCamp.footballMatch.mapper;

import com.neoCamp.footballMatch.dto.ClubDTO;
import com.neoCamp.footballMatch.entity.ClubEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ClubMapperTest {

    @Test
    void testToDto() {
        LocalDate data = LocalDate.of(2021, 5, 20);
        ClubEntity entity = new ClubEntity(10L, "Corinthians", "SP", data, true);

        ClubDTO dto = ClubMapper.toDto(entity);

        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getName(), dto.getName());
        assertEquals(entity.getUf(), dto.getUf());
        assertEquals(entity.getDateCreation(), dto.getDateCreation());
        assertEquals(entity.isActive(), dto.getActive());
    }

    @Test
    void testToDtoWithNull() {
        assertNull(ClubMapper.toDto(null));
    }

    @Test
    void testToEntity() {
        LocalDate data = LocalDate.of(2022, 6, 30);
        ClubDTO dto = new ClubDTO(5L, "Atlético-MG", "MG", data, false);

        ClubEntity entity = ClubMapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getUf(), entity.getUf());
        assertEquals(dto.getDateCreation(), entity.getDateCreation());
        assertEquals(Boolean.TRUE.equals(dto.getActive()), entity.isActive());
    }

        @Test
    void testToEntityWithAtivoNull() {
        LocalDate data = LocalDate.now();
        ClubDTO dto = new ClubDTO(6L, "Cuiabá", "MT", data, null);

        ClubEntity entity = ClubMapper.toEntity(dto);

        assertNotNull(entity);
        assertTrue(entity.isActive());
    }

    @Test
    void testToEntityWithNull() {
        assertNull(ClubMapper.toEntity(null));
    }
}