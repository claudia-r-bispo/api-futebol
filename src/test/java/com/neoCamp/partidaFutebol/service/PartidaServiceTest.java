package com.neoCamp.partidaFutebol.service;

import com.neoCamp.partidaFutebol.dto.PartidaDTO;
import com.neoCamp.partidaFutebol.entity.ClubeEntity;
import com.neoCamp.partidaFutebol.entity.EstadioEntity;
import com.neoCamp.partidaFutebol.entity.PartidaEntity;
import com.neoCamp.partidaFutebol.mapper.PartidaMapper;
import com.neoCamp.partidaFutebol.repository.PartidaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PartidaServiceTest {

    @Mock
    private PartidaRepository partidaRepository;
    @Mock
    private ClubeService clubeService;
    @Mock
    private EstadioService estadioService;

    @InjectMocks
    private PartidaService partidaService;

    private PartidaDTO partidaDTO;
    private PartidaEntity partidaEntity;
    private ClubeEntity mandante;
    private ClubeEntity visitante;
    private EstadioEntity estadio;

    @BeforeEach
    void setUp() {
        mandante = new ClubeEntity();
        mandante.setId(10L);
        visitante = new ClubeEntity();
        visitante.setId(20L);
        estadio = new EstadioEntity();
        estadio.setId(100L);

        // Ajuste conforme seu construtor real do DTO!
        partidaDTO = new PartidaDTO(1L, 10L, 20L, 100L, LocalDateTime.now(), 2, 1);

        partidaEntity = new PartidaEntity();
        partidaEntity.setId(1L);
        partidaEntity.setClubeMandante(mandante);
        partidaEntity.setClubeVisitante(visitante);
        partidaEntity.setEstadio(estadio);
        partidaEntity.setDataHoraPartida(partidaDTO.getDataHoraPartida());
        partidaEntity.setGolsMandante(partidaDTO.getGolsMandante());
        partidaEntity.setGolsVisitante(partidaDTO.getGolsVisitante());
    }

    @Test
    void testCreatePartida() {
        when(clubeService.findEntityById(10L)).thenReturn(mandante);
        when(clubeService.findEntityById(20L)).thenReturn(visitante);
        when(estadioService.findEntityById(100L)).thenReturn(estadio);

        try (MockedStatic<PartidaMapper> mapperMock = mockStatic(PartidaMapper.class)) {
            mapperMock.when(() -> PartidaMapper.toEntity(eq(partidaDTO), eq(mandante), eq(visitante), eq(estadio))).thenReturn(partidaEntity);
            when(partidaRepository.save(any(PartidaEntity.class))).thenReturn(partidaEntity);
            mapperMock.when(() -> PartidaMapper.toDto(any(PartidaEntity.class))).thenReturn(partidaDTO);

            PartidaDTO result = partidaService.createPartida(partidaDTO);

            assertNotNull(result);
            assertEquals(partidaDTO, result);

            verify(clubeService).findEntityById(10L);
            verify(clubeService).findEntityById(20L);
            verify(estadioService).findEntityById(100L);
            verify(partidaRepository).save(partidaEntity);
        }
    }

    @Test
    void testUpdatePartidaSuccess() {
        when(partidaRepository.findById(1L)).thenReturn(Optional.of(partidaEntity));
        when(clubeService.findEntityById(10L)).thenReturn(mandante);
        when(clubeService.findEntityById(20L)).thenReturn(visitante);
        when(estadioService.findEntityById(100L)).thenReturn(estadio);

        try (MockedStatic<PartidaMapper> mapperMock = mockStatic(PartidaMapper.class)) {
            when(partidaRepository.save(any(PartidaEntity.class))).thenReturn(partidaEntity);
            mapperMock.when(() -> PartidaMapper.toDto(any(PartidaEntity.class))).thenReturn(partidaDTO);

            PartidaDTO result = partidaService.updatePartida(1L, partidaDTO);

            assertNotNull(result);
            assertEquals(partidaDTO, result);

            verify(partidaRepository).findById(1L);
            verify(clubeService).findEntityById(10L);
            verify(clubeService).findEntityById(20L);
            verify(estadioService).findEntityById(100L);
            verify(partidaRepository).save(partidaEntity);
        }
    }

    @Test
    void testUpdatePartidaNotFound() {
        when(partidaRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> partidaService.updatePartida(1L, partidaDTO));
    }

    @Test
    void testRemoverPartida() {
        doNothing().when(partidaRepository).deleteById(1L);
        partidaService.removerPartida(1L);
        verify(partidaRepository).deleteById(1L);
    }

    @Test
    void testFindByIdSuccess() {
        try (MockedStatic<PartidaMapper> mapperMock = mockStatic(PartidaMapper.class)) {
            when(partidaRepository.findById(1L)).thenReturn(Optional.of(partidaEntity));
            mapperMock.when(() -> PartidaMapper.toDto(partidaEntity)).thenReturn(partidaDTO);

            PartidaDTO result = partidaService.findById(1L);

            assertNotNull(result);
            assertEquals(partidaDTO, result);

            verify(partidaRepository).findById(1L);
        }
    }

    @Test
    void testFindByIdNotFound() {
        when(partidaRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> partidaService.findById(1L));
    }

    @Test
    void testFindEntityByIdSuccess() {
        when(partidaRepository.findById(1L)).thenReturn(Optional.of(partidaEntity));
        PartidaEntity found = partidaService.findEntityById(1L);

        assertNotNull(found);
        assertEquals(partidaEntity, found);
    }

    @Test
    void testFindEntityByIdNotFound() {
        when(partidaRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> partidaService.findEntityById(1L));
    }

    @Test
    void testListar() {
        try (MockedStatic<PartidaMapper> mapperMock = mockStatic(PartidaMapper.class)) {
            Pageable pageable = PageRequest.of(0, 10);
            Page<PartidaEntity> partidaEntities = new PageImpl<>(java.util.List.of(partidaEntity));
            when(partidaRepository.findAll(eq(pageable))).thenReturn(partidaEntities);
            mapperMock.when(() -> PartidaMapper.toDto(any(PartidaEntity.class))).thenReturn(partidaDTO);

            Page<PartidaDTO> result = partidaService.listar(pageable);

            assertNotNull(result);
            assertEquals(1, result.getTotalElements());
        }
    }
}
