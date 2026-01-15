package com.neoCamp.footballMatch.service;

import com.neoCamp.footballMatch.dto.FootballMatchDTO;
import com.neoCamp.footballMatch.entity.ClubEntity;
import com.neoCamp.footballMatch.entity.StadiumEntity;
import com.neoCamp.footballMatch.entity.FootballMatch;
import com.neoCamp.footballMatch.mapper.FootballMatchMapper;
import com.neoCamp.footballMatch.repository.FootballMatchRepository;
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
class FootballMatchServiceTest {

    @Mock
    private FootballMatchRepository footballMatchRepository;
    @Mock
    private ClubService clubService;
    @Mock
    private StadiumService stadiumService;

    @InjectMocks
    private FootballMatchService footballMatchService;

    private FootballMatchDTO footballMatchDTO;
    private FootballMatch footballMatch;
    private ClubEntity mandante;
    private ClubEntity visitante;
    private StadiumEntity estadio;

    @BeforeEach
    void setUp() {
        mandante = new ClubEntity();
        mandante.setId(10L);
        visitante = new ClubEntity();
        visitante.setId(20L);
        estadio = new StadiumEntity();
        estadio.setId(100L);

        // Ajuste conforme seu construtor real do DTO!
        footballMatchDTO = new FootballMatchDTO(1L, 10L, 20L, 100L, LocalDateTime.now(), 2, 1);

        footballMatch = new FootballMatch();
        footballMatch.setId(1L);
        footballMatch.setHomeClub(mandante);
        footballMatch.setClubVisitor(visitante);
        footballMatch.setStadium(estadio);
        footballMatch.setDateTimeDeparture(footballMatchDTO.getDateTimeDeparture());
        footballMatch.setHomeTeamGoals(footballMatchDTO.getHomeTeamGoals());
        footballMatch.setGoalsVisitor(footballMatchDTO.getGoalsVisitor());
    }

    @Test
    void testCreatePartida() {
        when(clubService.findEntityById(10L)).thenReturn(mandante);
        when(clubService.findEntityById(20L)).thenReturn(visitante);
        when(stadiumService.findEntityById(100L)).thenReturn(estadio);

        try (MockedStatic<FootballMatchMapper> mapperMock = mockStatic(FootballMatchMapper.class)) {
            mapperMock.when(() -> FootballMatchMapper.toEntity(eq(footballMatchDTO), eq(mandante), eq(visitante), eq(estadio))).thenReturn(footballMatch);
            when(footballMatchRepository.save(any(FootballMatch.class))).thenReturn(footballMatch);
            mapperMock.when(() -> FootballMatchMapper.toDto(any(FootballMatch.class))).thenReturn(footballMatchDTO);

            FootballMatchDTO result = footballMatchService.createPartida(footballMatchDTO);

            assertNotNull(result);
            assertEquals(footballMatchDTO, result);

            verify(clubService).findEntityById(10L);
            verify(clubService).findEntityById(20L);
            verify(stadiumService).findEntityById(100L);
            verify(footballMatchRepository).save(footballMatch);
        }
    }

    @Test
    void testUpdatePartidaSuccess() {
        when(footballMatchRepository.findById(1L)).thenReturn(Optional.of(footballMatch));
        when(clubService.findEntityById(10L)).thenReturn(mandante);
        when(clubService.findEntityById(20L)).thenReturn(visitante);
        when(stadiumService.findEntityById(100L)).thenReturn(estadio);

        try (MockedStatic<FootballMatchMapper> mapperMock = mockStatic(FootballMatchMapper.class)) {
            when(footballMatchRepository.save(any(FootballMatch.class))).thenReturn(footballMatch);
            mapperMock.when(() -> FootballMatchMapper.toDto(any(FootballMatch.class))).thenReturn(footballMatchDTO);

            FootballMatchDTO result = footballMatchService.updatePartida(1L, footballMatchDTO);

            assertNotNull(result);
            assertEquals(footballMatchDTO, result);

            verify(footballMatchRepository).findById(1L);
            verify(clubService).findEntityById(10L);
            verify(clubService).findEntityById(20L);
            verify(stadiumService).findEntityById(100L);
            verify(footballMatchRepository).save(footballMatch);
        }
    }

    @Test
    void testUpdatePartidaNotFound() {
        when(footballMatchRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> footballMatchService.updatePartida(1L, footballMatchDTO));
    }

    @Test
    void testRemoverPartida() {
        doNothing().when(footballMatchRepository).deleteById(1L);
        footballMatchService.removerPartida(1L);
        verify(footballMatchRepository).deleteById(1L);
    }

    @Test
    void testFindByIdSuccess() {
        try (MockedStatic<FootballMatchMapper> mapperMock = mockStatic(FootballMatchMapper.class)) {
            when(footballMatchRepository.findById(1L)).thenReturn(Optional.of(footballMatch));
            mapperMock.when(() -> FootballMatchMapper.toDto(footballMatch)).thenReturn(footballMatchDTO);

            FootballMatchDTO result = footballMatchService.findById(1L);

            assertNotNull(result);
            assertEquals(footballMatchDTO, result);

            verify(footballMatchRepository).findById(1L);
        }
    }

    @Test
    void testFindByIdNotFound() {
        when(footballMatchRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> footballMatchService.findById(1L));
    }

    @Test
    void testFindEntityByIdSuccess() {
        when(footballMatchRepository.findById(1L)).thenReturn(Optional.of(footballMatch));
        FootballMatch found = footballMatchService.findEntityById(1L);

        assertNotNull(found);
        assertEquals(footballMatch, found);
    }

    @Test
    void testFindEntityByIdNotFound() {
        when(footballMatchRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> footballMatchService.findEntityById(1L));
    }

    @Test
    void testListar() {
        try (MockedStatic<FootballMatchMapper> mapperMock = mockStatic(FootballMatchMapper.class)) {
            Pageable pageable = PageRequest.of(0, 10);
            Page<FootballMatch> partidaEntities = new PageImpl<>(java.util.List.of(footballMatch));
            when(footballMatchRepository.findAll(eq(pageable))).thenReturn(partidaEntities);
            mapperMock.when(() -> FootballMatchMapper.toDto(any(FootballMatch.class))).thenReturn(footballMatchDTO);

            Page<FootballMatchDTO> result = footballMatchService.listar(pageable);

            assertNotNull(result);
            assertEquals(1, result.getTotalElements());
        }
    }
}
