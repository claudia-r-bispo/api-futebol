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
import java.util.UUID;

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
        UUID clubId = UUID.randomUUID();
        clubDTO = new ClubDTO(clubId, "Clube Teste", "SP", null, true);
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
            UUID clubId = clubEntity.getId();
            when(clubRepository.findById(clubId)).thenReturn(Optional.of(clubEntity));
            when(clubRepository.save(any(ClubEntity.class))).thenReturn(clubEntity);
            clubeMapperMock.when(() -> ClubMapper.toDto(any(ClubEntity.class))).thenReturn(clubDTO);

            ClubDTO result = clubService.updateClube(clubId, clubDTO);

            assertNotNull(result);
            assertEquals(clubDTO, result);
            verify(clubRepository).findById(clubId);
            verify(clubRepository).save(clubEntity);
        }
    }

    @Test
    void testUpdateClubeNotFound() {
        UUID nonExistentId = UUID.randomUUID();
        when(clubRepository.findById(nonExistentId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> clubService.updateClube(nonExistentId, clubDTO));
    }

    @Test
    void testInativar() {
        UUID clubId = clubEntity.getId();
        when(clubRepository.findById(clubId)).thenReturn(Optional.of(clubEntity));
        when(clubRepository.save(any(ClubEntity.class))).thenReturn(clubEntity);

        clubService.inativar(clubId);

        assertFalse(clubEntity.isActive());
        verify(clubRepository).save(clubEntity);
    }

    @Test
    void testInativarNotFound() {
        UUID nonExistentId = UUID.randomUUID();
        when(clubRepository.findById(nonExistentId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> clubService.inativar(nonExistentId));
    }

    @Test
    void testFindById() {
        try (MockedStatic<ClubMapper> clubeMapperMock = mockStatic(ClubMapper.class)) {
            UUID clubId = clubEntity.getId();
            when(clubRepository.findById(clubId)).thenReturn(Optional.of(clubEntity));
            clubeMapperMock.when(() -> ClubMapper.toDto(clubEntity)).thenReturn(clubDTO);

            ClubDTO result = clubService.findById(clubId);

            assertNotNull(result);
            assertEquals(clubDTO, result);
        }
    }

    @Test
    void testFindByIdNotFound() {
        UUID nonExistentId = UUID.randomUUID();
        when(clubRepository.findById(nonExistentId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> clubService.findById(nonExistentId));
    }

    @Test
    void testFindEntityById() {
        UUID clubId = clubEntity.getId();
        when(clubRepository.findById(clubId)).thenReturn(Optional.of(clubEntity));
        ClubEntity result = clubService.findEntityById(clubId);

        assertNotNull(result);
        assertEquals(clubEntity, result);
    }

    @Test
    void testFindEntityByIdNotFound() {
        UUID nonExistentId = UUID.randomUUID();
        when(clubRepository.findById(nonExistentId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> clubService.findEntityById(nonExistentId));
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
            when(clubRepository.findByNameContainingIgnoreCaseAndUfContainingIgnoreCase(
                eq("Clube"), 
                eq("SP"), 
                eq(pageable))
            ).thenReturn(clubeEntities);
            
            clubeMapperMock.when(() -> ClubMapper.toDto(any(ClubEntity.class))).thenReturn(clubDTO);

            Page<ClubDTO> result = clubService.listClubsWithFilters("Clube", "SP", null, pageable);

            assertNotNull(result);
            assertEquals(1, result.getTotalElements());
            verify(clubRepository).findByNameContainingIgnoreCaseAndUfContainingIgnoreCase("Clube", "SP", pageable);
        }
    }
}




