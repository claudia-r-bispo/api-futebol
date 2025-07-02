package com.neoCamp.footballMatch.mapper;

import com.neoCamp.footballMatch.dto.StadiumDTO;
import com.neoCamp.footballMatch.entity.StadiumEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class StadiumMapperTest {

    @BeforeEach
    @Test
    void testToEntity() {
        LocalDate dataCriacao = LocalDate.of(2001, 7, 8);
        StadiumDTO dto = new StadiumDTO
                (
                12L, "Arena Castelão", "CE", dataCriacao, null,
                null, null, null, null
        );

        StadiumEntity entity = StadiumMapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getUf(), entity.getUf());
        assertEquals(dto.getDateCreation(), entity.getDateCreation());
        assertTrue(entity.isActive());
    }

    @Test
    void testToEntityWithAtivoNull() {
        LocalDate dataCriacao = LocalDate.of(1982, 6, 3);
        StadiumDTO dto = new StadiumDTO();

        StadiumEntity entity = StadiumMapper.toEntity(dto);



        assertTrue(entity.isActive());
    }

    @Test
    void testToEntityWithNull() {
        assertNull(StadiumMapper.toEntity(null));
    }

    @Test
    void testToDTO() {
        LocalDate dataCriacao = LocalDate.of(2014, 11, 19);
        StadiumEntity entity = new StadiumEntity(22L, "Allianz Parque", "SP", true, dataCriacao);

        StadiumDTO dto = StadiumMapper.toDTO(entity);

        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getName(), dto.getName());
        assertEquals(entity.getUf(), dto.getUf());
        assertEquals(entity.getDateCreation(), dto.getDateCreation());
        assertTrue(dto.getActive());
    }

    @Test
    void testToDTOWithNull() {
        assertNull(StadiumMapper.toDTO(null));
        assertNull(StadiumMapper.toDto(null));
    }
}