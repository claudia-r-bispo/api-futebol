package com.neoCamp.footballMatch.service;

import com.neoCamp.footballMatch.dto.ClubDTO;
import com.neoCamp.footballMatch.entity.ClubEntity;
import com.neoCamp.footballMatch.mapper.ClubMapper;
import com.neoCamp.footballMatch.repository.ClubRepository;
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
    private ClubRepository clubRepository;

    @InjectMocks
    private ClubService clubService;

    private ClubDTO clubDTO;
    private ClubEntity clubEntity;

    @BeforeEach
    public void setUp() {
        clubDTO = new ClubDTO(1L, "Clube Teste", "SP", null, true);
        clubEntity = new ClubEntity();
        clubEntity.setId(clubDTO.getId());
        clubEntity.setName(clubDTO.getName());
        clubEntity.setUf(clubDTO.getUf());
        clubEntity.setDateCreation(clubDTO.getDateCreation());
        clubEntity.setActive(clubDTO.getActive());
    }

    @Test
    void testCreateClube() {
        try (MockedStatic<ClubMapper> clubeMapperMock = mockStatic(ClubMapper.class)) {
            clubeMapperMock.when(() -> ClubMapper.toEntity(any(ClubDTO.class))).thenReturn(clubEntity);
            when(clubRepository.save(any(ClubEntity.class))).thenReturn(clubEntity);
            clubeMapperMock.when(() -> ClubMapper.toDto(any(ClubEntity.class))).thenReturn(clubDTO);

            ClubDTO result = clubService.createClube(clubDTO);

            assertNotNull(result);
            assertEquals(clubDTO, result);
            verify(clubRepository).save(clubEntity);
        }
    }

    @Test
    void testUpdateClube() {
        try (MockedStatic<ClubMapper> clubeMapperMock = mockStatic(ClubMapper.class)) {
            when(clubRepository.findById(1L)).thenReturn(Optional.of(clubEntity));
            when(clubRepository.save(any(ClubEntity.class))).thenReturn(clubEntity);
            clubeMapperMock.when(() -> ClubMapper.toDto(any(ClubEntity.class))).thenReturn(clubDTO);

            ClubDTO result = clubService.updateClube(1L, clubDTO);

            assertNotNull(result);
            assertEquals(clubDTO, result);
            verify(clubRepository).findById(1L);
            verify(clubRepository).save(clubEntity);
        }
    }

    @Test
    void testUpdateClubeNotFound() {
        when(clubRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> clubService.updateClube(1L, clubDTO));

    }

    @Test
    void testInativar() {
        when(clubRepository.findById(1L)).thenReturn(Optional.of(clubEntity));
        when(clubRepository.save(any(ClubEntity.class))).thenReturn(clubEntity);

        clubService.inativar(1L);

        assertFalse(clubEntity.isActive());
        verify(clubRepository).save(clubEntity);
    }

    @Test
    void testInativarNotFound() {
        when(clubRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> clubService.inativar(1L));
    }

    @Test
    void testFindById() {
        try (MockedStatic<ClubMapper> clubeMapperMock = mockStatic(ClubMapper.class)) {
            when(clubRepository.findById(1L)).thenReturn(Optional.of(clubEntity));
            clubeMapperMock.when(() -> ClubMapper.toDto(clubEntity)).thenReturn(clubDTO);

            ClubDTO result = clubService.findById(1L);

            assertNotNull(result);
            assertEquals(clubDTO, result);
        }
    }

    @Test
    void testFindByIdNotFound() {
        when(clubRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> clubService.findById(1L));
    }

    @Test
    void testFindEntityById() {
        when(clubRepository.findById(1L)).thenReturn(Optional.of(clubEntity));
        ClubEntity result = clubService.findEntityById(1L);

        assertNotNull(result);
        assertEquals(clubEntity, result);
    }

    @Test
    void testFindEntityByIdNotFound() {
        when(clubRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> clubService.findEntityById(1L));
    }

    @Test
    void testListWithFiltersActiveNotNull() {
        try (MockedStatic<ClubMapper> clubMapperMock = mockStatic(ClubMapper.class)) {
            Pageable pageable = PageRequest.of(0, 10);
            Page<ClubEntity> clubEntities = new PageImpl<>(java.util.List.of(clubEntity));
            when(clubRepository.findByNameContainingIgnoreCaseAndUfContainingIgnoreCaseAndActive(anyString(), anyString(), eq(true), eq(pageable))).thenReturn(clubEntities);
            clubMapperMock.when(() -> ClubMapper.toDto(any(ClubEntity.class))).thenReturn(clubDTO);

            Page<ClubDTO> result = clubService.listClubsWithFilters("Clube", "SP", true, pageable);

            assertNotNull(result);
            assertEquals(1, result.getTotalElements());
        }
    }


    @Test
    void testListarComFiltrosAtivoNulo() {
        try (MockedStatic<ClubMapper> clubeMapperMock = mockStatic(ClubMapper.class)) {
            Pageable pageable = PageRequest.of(0, 10);
            Page<ClubEntity> clubeEntities = new PageImpl<>(java.util.List.of(clubEntity));
            when(clubRepository.findAll(eq(pageable))).thenReturn(clubeEntities);
            clubeMapperMock.when(() -> ClubMapper.toDto(any(ClubEntity.class))).thenReturn(clubDTO);

            Page<ClubDTO> result = clubService.listClubsWithFilters("Clube", "SP", null, pageable);

            assertNotNull(result);
            assertEquals(1, result.getTotalElements());
        }
    }
}




