package com.neoCamp.footballMatch.mapper;

import com.neoCamp.footballMatch.dto.StadiumDTO;
import com.neoCamp.footballMatch.dto.AddressDTO;
import com.neoCamp.footballMatch.entity.StadiumEntity;
import com.neoCamp.footballMatch.entity.AddressEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class StadiumMapperTest {

    @Test
    void toEntity_Success() {
        // Arrange
        StadiumDTO dto = new StadiumDTO();
        dto.setId(1L);
        dto.setName("Arena Test");
        dto.setUf("SP");
        dto.setDateCreation(LocalDate.of(2024, 1, 1));
        dto.setCep("01310-100");
        dto.setActive(true);

        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setId(1L);
        addressDTO.setLogradouro("Avenida Paulista");
        addressDTO.setCidade("São Paulo");
        addressDTO.setEstado("SP");
        addressDTO.setCep("01310-100");
        dto.setAddress(addressDTO);

        // Act
        StadiumEntity entity = StadiumMapper.toEntity(dto);

        // Assert
        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getUf(), entity.getUf());
        assertEquals(dto.getDateCreation(), entity.getDateCreation());
        assertTrue(entity.isActive());
        assertNotNull(entity.getAddress());
        assertEquals("Avenida Paulista", entity.getAddress().getStreet());
    }

    @Test
    void toEntity_NullDto_ReturnsNull() {
        // Act
        StadiumEntity entity = StadiumMapper.toEntity(null);

        // Assert
        assertNull(entity);
    }

    @Test
    void toEntity_ActiveNull_DefaultsToTrue() {
        // Arrange
        StadiumDTO dto = new StadiumDTO();
        dto.setName("Arena Test");
        dto.setUf("SP");
        dto.setDateCreation(LocalDate.now());
        dto.setActive(null); // null

        // Act
        StadiumEntity entity = StadiumMapper.toEntity(dto);

        // Assert
        assertTrue(entity.isActive()); // Deve ser true por padrão
    }

    @Test
    void toDTO_Success() {
        // Arrange
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setId(1L);
        addressEntity.setStreet("Avenida Paulista");
        addressEntity.setCity("São Paulo");
        addressEntity.setState("SP");
        addressEntity.setZipCode("01310-100");

        StadiumEntity entity = new StadiumEntity();
        entity.setId(1L);
        entity.setName("Arena Test");
        entity.setUf("SP");
        entity.setDateCreation(LocalDate.of(2024, 1, 1));
        entity.setActive(true);
        entity.setAddress(addressEntity);

        // Act
        StadiumDTO dto = StadiumMapper.toDTO(entity);

        // Assert
        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getName(), dto.getName());
        assertEquals(entity.getUf(), dto.getUf());
        assertEquals(entity.getDateCreation(), dto.getDateCreation());
        assertEquals(entity.isActive(), dto.getActive());
        assertNotNull(dto.getAddress());
        assertEquals("01310-100", dto.getCep());
        assertEquals("Avenida Paulista", dto.getAddress().getLogradouro());
    }

    @Test
    void toDTO_NullEntity_ReturnsNull() {
        // Act
        StadiumDTO dto = StadiumMapper.toDTO(null);

        // Assert
        assertNull(dto);
    }

    @Test
    void toDTO_NullAddress_HandlesGracefully() {
        // Arrange
        StadiumEntity entity = new StadiumEntity();
        entity.setId(1L);
        entity.setName("Arena Test");
        entity.setUf("SP");
        entity.setDateCreation(LocalDate.now());
        entity.setActive(true);
        entity.setAddress(null); // Address nulo

        // Act
        StadiumDTO dto = StadiumMapper.toDTO(entity);

        // Assert
        assertNotNull(dto);
        assertNull(dto.getAddress());
        assertNull(dto.getCep());
    }

    @Test
    void toDto_CallsToDTO() {
        // Arrange
        StadiumEntity entity = new StadiumEntity();
        entity.setId(1L);
        entity.setName("Arena Test");
        entity.setUf("SP");
        entity.setDateCreation(LocalDate.now());
        entity.setActive(true);

        // Act
        StadiumDTO dto = StadiumMapper.toDto(entity);

        // Assert
        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getName(), dto.getName());
    }
}