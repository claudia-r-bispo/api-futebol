package com.neoCamp.partidaFutebol.service;

import com.neoCamp.partidaFutebol.dto.ClubeDTO;
import com.neoCamp.partidaFutebol.entity.ClubeEntity;
import com.neoCamp.partidaFutebol.mapper.ClubeMapper;
import com.neoCamp.partidaFutebol.repository.ClubeRepository;
import jakarta.persistence.EntityNotFoundException;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClubeServiceTest {

    @Mock
    private ClubeRepository clubeRepository;

    @InjectMocks
    private ClubeService clubeService;

    private ClubeDTO clubeDTO;
    private ClubeEntity clubeEntity;

    @BeforeEach
    public void setUp() {
        clubeDTO = new ClubeDTO(1L, "Clube Teste", "SP", null, true);
        clubeEntity = new ClubeEntity();
        clubeEntity.setId(clubeDTO.getId());
        clubeEntity.setNome(clubeDTO.getNome());
        clubeEntity.setUf(clubeDTO.getUf());
        clubeEntity.setDtCriacao(clubeDTO.getDtCriacao());
        clubeEntity.setAtivo(clubeDTO.isAtivo());
    }

    @Test
    void testCreateClube() {
        try (MockedStatic<ClubeMapper> clubeMapperMock = mockStatic(ClubeMapper.class)) {
            clubeMapperMock.when(() -> ClubeMapper.toEntity(any(ClubeDTO.class))).thenReturn(clubeEntity);
            when(clubeRepository.save(any(ClubeEntity.class))).thenReturn(clubeEntity);
            clubeMapperMock.when(() -> ClubeMapper.toDto(any(ClubeEntity.class))).thenReturn(clubeDTO);

            ClubeDTO result = clubeService.createClube(clubeDTO);

            assertNotNull(result);
            assertEquals(clubeDTO, result);
            verify(clubeRepository).save(clubeEntity);
        }
    }

    @Test
    void testUpdateClube() {
        try (MockedStatic<ClubeMapper> clubeMapperMock = mockStatic(ClubeMapper.class)) {
            when(clubeRepository.findById(1L)).thenReturn(Optional.of(clubeEntity));
            when(clubeRepository.save(any(ClubeEntity.class))).thenReturn(clubeEntity);
            clubeMapperMock.when(() -> ClubeMapper.toDto(any(ClubeEntity.class))).thenReturn(clubeDTO);

            ClubeDTO result = clubeService.updateClube(1L, clubeDTO);

            assertNotNull(result);
            assertEquals(clubeDTO, result);
            verify(clubeRepository).findById(1L);
            verify(clubeRepository).save(clubeEntity);
        }
    }

    @Test
    void testUpdateClubeNotFound() {
        when(clubeRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> clubeService.updateClube(1L, clubeDTO));

    }

    @Test
    void testInativar() {
        when(clubeRepository.findById(1L)).thenReturn(Optional.of(clubeEntity));
        when(clubeRepository.save(any(ClubeEntity.class))).thenReturn(clubeEntity);

        clubeService.inativar(1L);

        assertFalse(clubeEntity.isAtivo());
        verify(clubeRepository).save(clubeEntity);
    }

    @Test
    void testInativarNotFound() {
        when(clubeRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> clubeService.inativar(1L));
    }

    @Test
    void testFindById() {
        try (MockedStatic<ClubeMapper> clubeMapperMock = mockStatic(ClubeMapper.class)) {
            when(clubeRepository.findById(1L)).thenReturn(Optional.of(clubeEntity));
            clubeMapperMock.when(() -> ClubeMapper.toDto(clubeEntity)).thenReturn(clubeDTO);

            ClubeDTO result = clubeService.findById(1L);

            assertNotNull(result);
            assertEquals(clubeDTO, result);
        }
    }

    @Test
    void testFindByIdNotFound() {
        when(clubeRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> clubeService.findById(1L));
    }

    @Test
    void testFindEntityById() {
        when(clubeRepository.findById(1L)).thenReturn(Optional.of(clubeEntity));
        ClubeEntity result = clubeService.findEntityById(1L);

        assertNotNull(result);
        assertEquals(clubeEntity, result);
    }

    @Test
    void testFindEntityByIdNotFound() {
        when(clubeRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> clubeService.findEntityById(1L));
    }

    @Test
    void testListarComFiltrosAtivoNaoNulo() {
        try (MockedStatic<ClubeMapper> clubeMapperMock = mockStatic(ClubeMapper.class)) {
            Pageable pageable = PageRequest.of(0, 10);
            Page<ClubeEntity> clubeEntities = new PageImpl<>(java.util.List.of(clubeEntity));
            when(clubeRepository.findByNomeContainingIgnoreCaseAndUfContainingIgnoreCaseAndAtivo(anyString(), anyString(), eq(true), eq(pageable))).thenReturn(clubeEntities);
            clubeMapperMock.when(() -> ClubeMapper.toDto(any(ClubeEntity.class))).thenReturn(clubeDTO);

            Page<ClubeDTO> result = clubeService.listarComFiltros("Clube", "SP", true, pageable);

            assertNotNull(result);
            assertEquals(1, result.getTotalElements());
        }
    }


    @Test
    void testListarComFiltrosAtivoNulo() {
        try (MockedStatic<ClubeMapper> clubeMapperMock = mockStatic(ClubeMapper.class)) {
            Pageable pageable = PageRequest.of(0, 10);
            Page<ClubeEntity> clubeEntities = new PageImpl<>(java.util.List.of(clubeEntity));
            when(clubeRepository.findAll(eq(pageable))).thenReturn(clubeEntities);
            clubeMapperMock.when(() -> ClubeMapper.toDto(any(ClubeEntity.class))).thenReturn(clubeDTO);

            Page<ClubeDTO> result = clubeService.listarComFiltros("Clube", "SP", null, pageable);

            assertNotNull(result);
            assertEquals(1, result.getTotalElements());
        }
    }
}




