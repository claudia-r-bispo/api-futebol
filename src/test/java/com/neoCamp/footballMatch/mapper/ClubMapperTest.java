package com.neoCamp.footballMatch.mapper;

import com.neoCamp.footballMatch.dto.ClubDTO;
import com.neoCamp.footballMatch.entity.ClubEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ClubMapperTest {
    private UUID testId1;
    private UUID testId2;
    
    @BeforeEach
    void setUp() {
        testId1 = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        testId2 = UUID.fromString("223e4567-e89b-12d3-a456-426614174001");
    }

    @Test
    void testToDto() {
        LocalDate data = LocalDate.of(2021, 5, 20);
        ClubEntity entity = new ClubEntity();
        entity.setId(testId1);
        entity.setName("Corinthians");
        entity.setUf("SP");
        entity.setDateCreation(data);
        entity.setActive(true);

        ClubDTO dto = ClubMapper.toDto(entity);

        assertNotNull(dto, "DTO should not be null");
        assertEquals(entity.getId(), dto.getId(), "IDs should match");
        assertEquals(entity.getName(), dto.getName(), "Names should match");
        assertEquals(entity.getUf(), dto.getUf(), "UFs should match");
        assertEquals(entity.getDateCreation(), dto.getDateCreation(), "Creation dates should match");
        assertEquals(entity.isActive(), dto.getActive(), "Active status should match");
    }

    @Test
    void testToDtoWithNull() {
        assertNull(ClubMapper.toDto(null));
    }

    @Test
    void testToEntity() {
        LocalDate data = LocalDate.of(2022, 6, 30);
        ClubDTO dto = new ClubDTO();
        dto.setId(testId2);
        dto.setName("Atlético-MG");
        dto.setUf("MG");
        dto.setDateCreation(data);
        dto.setActive(false);

        ClubEntity entity = ClubMapper.toEntity(dto);

        assertNotNull(entity, "Entity should not be null");
        assertEquals(dto.getId(), entity.getId(), "IDs should match");
        assertEquals(dto.getName(), entity.getName(), "Names should match");
        assertEquals(dto.getUf(), entity.getUf(), "UFs should match");
        assertEquals(dto.getDateCreation(), entity.getDateCreation(), "Creation dates should match");
        assertEquals(Boolean.TRUE.equals(dto.getActive()), entity.isActive(), "Active status should match");
    }

    @Test
    void testToEntityWithAtivoNull() {
        LocalDate data = LocalDate.now();
        ClubDTO dto = new ClubDTO();
        dto.setId(testId1);
        dto.setName("Cuiabá");
        dto.setUf("MT");
        dto.setDateCreation(data);
        dto.setActive(null);

        ClubEntity entity = ClubMapper.toEntity(dto);

        assertNotNull(entity, "Entity should not be null");
        assertTrue(entity.isActive(), "Entity should be active by default when active is null");
    }

    @Test
    void testToEntityWithNull() {
        assertNull(ClubMapper.toEntity(null));
    }
}