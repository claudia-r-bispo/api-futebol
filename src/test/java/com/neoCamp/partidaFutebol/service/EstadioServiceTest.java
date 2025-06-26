package com.neoCamp.partidaFutebol.service;

import com.neoCamp.partidaFutebol.dto.EstadioDTO;
import com.neoCamp.partidaFutebol.entity.EstadioEntity;
import com.neoCamp.partidaFutebol.mapper.EstadioMapper;
import com.neoCamp.partidaFutebol.repository.EstadioRepository;
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
public class EstadioServiceTest {

    @Mock
    private EstadioRepository estadioRepository;

    @InjectMocks
    private EstadioService estadioService;

    private EstadioDTO estadioDTO;
    private EstadioEntity estadioEntity;

    @BeforeEach
    void setUp() {
        estadioDTO = new EstadioDTO();
        estadioEntity = new EstadioEntity();
        estadioEntity.setId(1L);
        estadioEntity.setNome("Estádio Exemplo");
        estadioEntity.setUf("SP");
        estadioEntity.setDtCriacao(LocalDate.now());
        estadioEntity.setAtivo(true);
    }

    @Test
    void testCreateEstadio() {
        try (MockedStatic<EstadioMapper> estadioMapperMock = mockStatic(EstadioMapper.class)) {
            estadioMapperMock.when(() -> EstadioMapper.toEntity(any(EstadioDTO.class))).thenReturn(estadioEntity);
            when(estadioRepository.save(any(EstadioEntity.class))).thenReturn(estadioEntity);
            estadioMapperMock.when(() -> EstadioMapper.toDto(any(EstadioEntity.class))).thenReturn(estadioDTO);

            EstadioDTO result = estadioService.createEstadio(estadioDTO);

            assertNotNull(result);
            assertEquals(estadioDTO, result);
            verify(estadioRepository).save(estadioEntity);
            assertTrue(estadioEntity.isAtivo());
        }
    }

    @Test
    void testUpdateEstadioSuccess() {
        try (MockedStatic<EstadioMapper> estadioMapperMock = mockStatic(EstadioMapper.class)) {
            when(estadioRepository.findById(1L)).thenReturn(Optional.of(estadioEntity));
            when(estadioRepository.save(any(EstadioEntity.class))).thenReturn(estadioEntity);
            estadioMapperMock.when(() -> EstadioMapper.toDto(any(EstadioEntity.class))).thenReturn(estadioDTO);

            EstadioDTO result = estadioService.updateEstadio(1L, estadioDTO);
            assertNotNull(result);
            assertEquals(estadioDTO, result);
            verify(estadioRepository).findById(1L);
            verify(estadioRepository).save(estadioEntity);
        }
    }

    @Test
    void testUpdateEstadioNotFound() {
        when(estadioRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> estadioService.updateEstadio(1L, estadioDTO));
    }

    @Test
    void testFindByIdSuccess() {
        try (MockedStatic<EstadioMapper> estadioMapperMock = mockStatic(EstadioMapper.class)) {
            when(estadioRepository.findById(1L)).thenReturn(Optional.of(estadioEntity));
            estadioMapperMock.when(() -> EstadioMapper.toDto(estadioEntity)).thenReturn(estadioDTO);

            EstadioDTO result = estadioService.findById(1L);
            assertNotNull(result);
            assertEquals(estadioDTO, result);
        }
    }

    @Test
    void testFindByIdNotFound() {
        when(estadioRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> estadioService.findById(1L));
    }

    @Test
    void testFindEntityByIdSuccess() {
        when(estadioRepository.findById(1L)).thenReturn(Optional.of(estadioEntity));
        EstadioEntity result = estadioService.findEntityById(1L);
        assertNotNull(result);
        assertEquals(estadioEntity, result);
    }

    @Test
    void testFindEntityByIdNotFound() {
        when(estadioRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> estadioService.findEntityById(1L));
    }

    @Test
    void testListar() {
        try (MockedStatic<EstadioMapper> estadioMapperMock = mockStatic(EstadioMapper.class)) {
            Pageable pageable = PageRequest.of(0, 10);
            Page<EstadioEntity> estadioEntities = new PageImpl<>(java.util.List.of(estadioEntity));
            when(estadioRepository.findAll(eq(pageable))).thenReturn(estadioEntities);
            estadioMapperMock.when(() -> EstadioMapper.toDto(any(EstadioEntity.class))).thenReturn(estadioDTO);

            Page<EstadioDTO> result = estadioService.listar(pageable);

            assertNotNull(result);
            assertEquals(1, result.getTotalElements());
        }
    }
}