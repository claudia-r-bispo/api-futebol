package com.neoCamp.footballMatch.service;

import com.neoCamp.footballMatch.dto.FootballMatchDTO;
import com.neoCamp.footballMatch.entity.ClubEntity;
import com.neoCamp.footballMatch.entity.StadiumEntity;
import com.neoCamp.footballMatch.entity.FootballMatch;
import com.neoCamp.footballMatch.mapper.FootballMatchMapper;
import com.neoCamp.footballMatch.repository.ClubRepository;
import com.neoCamp.footballMatch.repository.FootballMatchRepository;
import com.neoCamp.footballMatch.repository.StadiumRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FootballMatchServiceTest {

    @Mock
    private FootballMatchRepository footballMatchRepository;
    @Mock
    private ClubRepository clubRepository;
    @Mock
    private StadiumRepository stadiumRepository;

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
        mandante.setId(UUID.randomUUID());
        visitante = new ClubEntity();
        visitante.setId(UUID.randomUUID());
        estadio = new StadiumEntity();
        estadio.setId(UUID.randomUUID());

        footballMatchDTO = createFootballMatchDTO(UUID.randomUUID(), mandante.getId(), visitante.getId(), estadio.getId());
        footballMatch = createFootballMatch(UUID.randomUUID(), mandante, visitante, estadio);
    }

    private FootballMatch createFootballMatch(UUID id, ClubEntity mandante, ClubEntity visitante, StadiumEntity estadio) {
        FootballMatch partida = new FootballMatch();
        partida.setId(id);
        partida.setHomeClub(mandante);
        partida.setClubVisitor(visitante);
        partida.setStadium(estadio);
        partida.setDateTimeDeparture(LocalDateTime.now());
        partida.setHomeTeamGoals(2);
        partida.setGoalsVisitor(1);
        return partida;
    }

    private FootballMatchDTO createFootballMatchDTO(UUID id, UUID mandanteId, UUID visitanteId, UUID estadioId) {
        FootballMatchDTO dto = new FootballMatchDTO();
        dto.setId(id);
        dto.setHomeClubId(mandanteId);
        dto.setClubVisitorId(visitanteId);
        dto.setStadiumId(estadioId);
        dto.setDateTimeDeparture(LocalDateTime.now());
        dto.setHomeTeamGoals(2);
        dto.setGoalsVisitor(1);
        return dto;
    }

    @Test
    void testCreatePartida() {
        // Configuração do cenário
        UUID partidaId = UUID.randomUUID();
        footballMatch.setId(partidaId);
        
        // Configuração dos mocks
        when(clubRepository.findById(mandante.getId())).thenReturn(Optional.of(mandante));
        when(clubRepository.findById(visitante.getId())).thenReturn(Optional.of(visitante));
        when(stadiumRepository.findById(estadio.getId())).thenReturn(Optional.of(estadio));

        try (MockedStatic<FootballMatchMapper> mapperMock = mockStatic(FootballMatchMapper.class)) {
            // Configuração do mock do mapper
            FootballMatch partidaSalva = new FootballMatch();
            partidaSalva.setId(partidaId);
            partidaSalva.setHomeClub(mandante);
            partidaSalva.setClubVisitor(visitante);
            partidaSalva.setStadium(estadio);
            partidaSalva.setDateTimeDeparture(footballMatchDTO.getDateTimeDeparture());
            partidaSalva.setHomeTeamGoals(footballMatchDTO.getHomeTeamGoals());
            partidaSalva.setGoalsVisitor(footballMatchDTO.getGoalsVisitor());
            
            // Configura o mock para retornar a entidade correta quando o mapper for chamado
            mapperMock.when(() -> FootballMatchMapper.toEntity(any(FootballMatchDTO.class), eq(mandante), eq(visitante), eq(estadio)))
                    .thenReturn(partidaSalva);
                    
            // Configura o mock para retornar a entidade salva
            when(footballMatchRepository.save(any(FootballMatch.class))).thenReturn(partidaSalva);
            
            // Configura o mock para retornar o DTO esperado
            mapperMock.when(() -> FootballMatchMapper.toDto(partidaSalva)).thenReturn(footballMatchDTO);

            // Execução do método a ser testado
            FootballMatchDTO result = footballMatchService.create(footballMatchDTO);

            // Verificações
            assertNotNull(result);
            assertEquals(footballMatchDTO, result);

            // Verifica se os métodos foram chamados corretamente
            verify(clubRepository).findById(mandante.getId());
            verify(clubRepository).findById(visitante.getId());
            verify(stadiumRepository).findById(estadio.getId());
            verify(footballMatchRepository).save(any(FootballMatch.class));
        }
    }

    @Test
    void testUpdatePartidaSuccess() {
        // Configuração do cenário
        UUID partidaId = UUID.randomUUID();
        footballMatch.setId(partidaId);
        footballMatch.setHomeClub(mandante);
        footballMatch.setClubVisitor(visitante);
        footballMatch.setStadium(estadio);
        
        // Configuração dos mocks
        when(footballMatchRepository.findById(partidaId)).thenReturn(Optional.of(footballMatch));
        when(clubRepository.findById(mandante.getId())).thenReturn(Optional.of(mandante));
        when(clubRepository.findById(visitante.getId())).thenReturn(Optional.of(visitante));
        when(stadiumRepository.findById(estadio.getId())).thenReturn(Optional.of(estadio));
        when(footballMatchRepository.save(any(FootballMatch.class))).thenReturn(footballMatch);

        try (MockedStatic<FootballMatchMapper> mapperMock = mockStatic(FootballMatchMapper.class)) {
            // Configuração do mock do mapper
            mapperMock.when(() -> FootballMatchMapper.toDto(any(FootballMatch.class))).thenReturn(footballMatchDTO);

            // Execução do método a ser testado
            FootballMatchDTO result = footballMatchService.update(partidaId, footballMatchDTO);

            // Verificações
            assertNotNull(result);
            assertEquals(footballMatchDTO, result);

            // Verifica se os métodos foram chamados corretamente
            verify(footballMatchRepository).findById(partidaId);
            verify(clubRepository).findById(mandante.getId());
            verify(clubRepository).findById(visitante.getId());
            verify(stadiumRepository).findById(estadio.getId());
            verify(footballMatchRepository).save(any(FootballMatch.class));
        }
    }

    @Test
    void testUpdatePartidaNotFound() {
        when(footballMatchRepository.findById(footballMatch.getId())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> footballMatchService.update(footballMatch.getId(), footballMatchDTO));
    }

    @Test
    void testRemoverPartida() {
        // Configuração do cenário
        UUID partidaId = UUID.randomUUID();
        footballMatch.setId(partidaId);
        
        // Configuração dos mocks
        when(footballMatchRepository.findById(partidaId)).thenReturn(Optional.of(footballMatch));
        doNothing().when(footballMatchRepository).delete(footballMatch);
        
        // Execução do método a ser testado
        footballMatchService.delete(partidaId);
        
        // Verifica se os métodos foram chamados corretamente
        verify(footballMatchRepository).findById(partidaId);
        verify(footballMatchRepository).delete(footballMatch);
    }

    @Test
    void testFindByIdSuccess() {
        try (MockedStatic<FootballMatchMapper> mapperMock = mockStatic(FootballMatchMapper.class)) {
            when(footballMatchRepository.findById(footballMatch.getId())).thenReturn(Optional.of(footballMatch));
            mapperMock.when(() -> FootballMatchMapper.toDto(footballMatch)).thenReturn(footballMatchDTO);

            FootballMatchDTO result = footballMatchService.findById(footballMatch.getId());

            assertNotNull(result);
            assertEquals(footballMatchDTO, result);

            verify(footballMatchRepository).findById(footballMatch.getId());
        }
    }

    @Test
    void testFindByIdNotFound() {
        UUID nonExistentId = UUID.randomUUID();
        when(footballMatchRepository.findById(nonExistentId)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> footballMatchService.findById(nonExistentId));
    }

    @Test
    void testFindEntityByIdSuccess() {
        when(footballMatchRepository.findById(footballMatch.getId())).thenReturn(Optional.of(footballMatch));
        FootballMatch found = footballMatchService.findEntityById(footballMatch.getId());

        assertNotNull(found);
        assertEquals(footballMatch, found);
    }

    @Test
    void testFindEntityByIdNotFound() {
        UUID nonExistentId = UUID.randomUUID();
        when(footballMatchRepository.findById(nonExistentId)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> footballMatchService.findEntityById(nonExistentId));
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
