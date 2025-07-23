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


        StadiumEntity entity = StadiumMapper.toEntity(dto);


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

        StadiumEntity entity = StadiumMapper.toEntity(null);


        assertNull(entity);
    }

    @Test
    void toEntity_ActiveNull_DefaultsToTrue() {

        StadiumDTO dto = new StadiumDTO();
        dto.setName("Arena Test");
        dto.setUf("SP");
        dto.setDateCreation(LocalDate.now());
        dto.setActive(null); // null


        StadiumEntity entity = StadiumMapper.toEntity(dto);


        assertTrue(entity.isActive());
    }

    @Test
    void toDTO_Success() {

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


        StadiumDTO dto = StadiumMapper.toDTO(entity);

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

        StadiumDTO dto = StadiumMapper.toDTO(null);


        assertNull(dto);
    }

    @Test
    void toDTO_NullAddress_HandlesGracefully() {

        StadiumEntity entity = new StadiumEntity();
        entity.setId(1L);
        entity.setName("Arena Test");
        entity.setUf("SP");
        entity.setDateCreation(LocalDate.now());
        entity.setActive(true);
        entity.setAddress(null); // Address nulo


        StadiumDTO dto = StadiumMapper.toDTO(entity);


        assertNotNull(dto);
        assertNull(dto.getAddress());
        assertNull(dto.getCep());
    }

    @Test
    void toDto_CallsToDTO() {

        StadiumEntity entity = new StadiumEntity();
        entity.setId(1L);
        entity.setName("Arena Test");
        entity.setUf("SP");
        entity.setDateCreation(LocalDate.now());
        entity.setActive(true);


        StadiumDTO dto = StadiumMapper.toDto(entity);


        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getName(), dto.getName());
    }
}