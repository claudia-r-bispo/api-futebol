package com.neoCamp.footballMatch.service;

import com.neoCamp.footballMatch.dto.StadiumDTO;
import com.neoCamp.footballMatch.entity.StadiumEntity;
import com.neoCamp.footballMatch.mapper.StadiumMapper;
import com.neoCamp.footballMatch.repository.StadiumRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StadiumServiceTest {

    @Mock
    private StadiumRepository stadiumRepository;

    @InjectMocks
    private StadiumService stadiumService;

    private StadiumDTO stadiumDTO;
    private StadiumEntity stadiumEntity;

    @BeforeEach
    void setUp() {
        stadiumDTO = new StadiumDTO();
        stadiumEntity = new StadiumEntity();
        stadiumEntity.setId(1L);
        stadiumEntity.setName("Estádio Exemplo");
        stadiumEntity.setUf("SP");
        stadiumEntity.setDateCreation(LocalDate.now());
        stadiumEntity.setActive(true);
    }

    @Test
    void testCreateEstadio() {
        try (MockedStatic<StadiumMapper> estadioMapperMock = mockStatic(StadiumMapper.class)) {
            estadioMapperMock.when(() -> StadiumMapper.toEntity(any(StadiumDTO.class))).thenReturn(stadiumEntity);
            when(stadiumRepository.save(any(StadiumEntity.class))).thenReturn(stadiumEntity);
            estadioMapperMock.when(() -> StadiumMapper.toDto(any(StadiumEntity.class))).thenReturn(stadiumDTO);

            StadiumDTO result = stadiumService.createEstadio(stadiumDTO);

            assertNotNull(result);
            assertEquals(stadiumDTO, result);
            verify(stadiumRepository).save(stadiumEntity);
            assertTrue(stadiumEntity.isActive());
        }
    }

    @Test
    void testUpdateEstadioSuccess() {
        try (MockedStatic<StadiumMapper> estadioMapperMock = mockStatic(StadiumMapper.class)) {
            when(stadiumRepository.findById(1L)).thenReturn(Optional.of(stadiumEntity));
            when(stadiumRepository.save(any(StadiumEntity.class))).thenReturn(stadiumEntity);
            estadioMapperMock.when(() -> StadiumMapper.toDto(any(StadiumEntity.class))).thenReturn(stadiumDTO);

            StadiumDTO result = stadiumService.updateEstadio(1L, stadiumDTO);
            assertNotNull(result);
            assertEquals(stadiumDTO, result);
            verify(stadiumRepository).findById(1L);
            verify(stadiumRepository).save(stadiumEntity);
        }
    }

    @Test
    void testUpdateEstadioNotFound() {
        when(stadiumRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> stadiumService.updateEstadio(1L, stadiumDTO));
    }

    @Test
    void testFindByIdSuccess() {
        try (MockedStatic<StadiumMapper> estadioMapperMock = mockStatic(StadiumMapper.class)) {
            when(stadiumRepository.findById(1L)).thenReturn(Optional.of(stadiumEntity));
            estadioMapperMock.when(() -> StadiumMapper.toDto(stadiumEntity)).thenReturn(stadiumDTO);

            StadiumDTO result = stadiumService.findById(1L);
            assertNotNull(result);
            assertEquals(stadiumDTO, result);
        }
    }

    @Test
    void testFindByIdNotFound() {
        when(stadiumRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> stadiumService.findById(1L));
    }

    @Test
    void testFindEntityByIdSuccess() {
        when(stadiumRepository.findById(1L)).thenReturn(Optional.of(stadiumEntity));
        StadiumEntity result = stadiumService.findEntityById(1L);
        assertNotNull(result);
        assertEquals(stadiumEntity, result);
    }

    @Test
    void testFindEntityByIdNotFound() {
        when(stadiumRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> stadiumService.findEntityById(1L));
    }

    @Test
    void testListar() {
        try (MockedStatic<StadiumMapper> estadioMapperMock = mockStatic(StadiumMapper.class)) {
            Pageable pageable = PageRequest.of(0, 10);
            Page<StadiumEntity> estadioEntities = new PageImpl<>(java.util.List.of(stadiumEntity));
            when(stadiumRepository.findAll(eq(pageable))).thenReturn(estadioEntities);
            estadioMapperMock.when(() -> StadiumMapper.toDto(any(StadiumEntity.class))).thenReturn(stadiumDTO);

            Page<StadiumDTO> result = stadiumService.listar(pageable);

            assertNotNull(result);
            assertEquals(1, result.getTotalElements());
        }
    }
}