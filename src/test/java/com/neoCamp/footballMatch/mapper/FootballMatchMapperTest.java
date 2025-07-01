package com.neoCamp.footballMatch.mapper;

import com.neoCamp.footballMatch.dto.FootballMatchDTO;
import com.neoCamp.footballMatch.entity.ClubEntity;
import com.neoCamp.footballMatch.entity.StadiumEntity;
import com.neoCamp.footballMatch.entity.FootballMatch;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class FootballMatchMapperTest {

    @Test
    void testToDTO() {
        ClubEntity mandante = new ClubEntity(1L, "Palmeiras", "SP", LocalDate.of(1914,8,26), true);
        ClubEntity visitante = new ClubEntity(2L, "Cruzeiro", "MG", LocalDate.of(1921,1,2), true);
        StadiumEntity estadio = new StadiumEntity(3L, "Allianz Parque", "SP", true, LocalDate.of(2014,11,19));
        LocalDateTime data = LocalDateTime.of(2024,7,1,16,30);

        FootballMatch entity = new FootballMatch(10L, mandante, visitante, estadio, data, 3, 1);

        FootballMatchDTO dto = FootballMatchMapper.toDTO(entity);

        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(mandante.getId(), dto.getHomeClubId());
        assertEquals(visitante.getId(), dto.getClubVisitorId());
        assertEquals(estadio.getId(), dto.getStadiumId());
        assertEquals(data, dto.getDateTimeDeparture());
        assertEquals(entity.getHomeTeamGoals(), dto.getHomeTeamGoals());
        assertEquals(entity.getGoalsVisitor(), dto.getGoalsVisitor());
    }

    @Test
    void testToDTOWithNulls() {
        // Entidade sem mandante/visitante/estadio
        LocalDateTime data = LocalDateTime.now();
        FootballMatch entity = new FootballMatch(15L, null, null, null, data, 0, 0);

        FootballMatchDTO dto = FootballMatchMapper.toDTO(entity);

        assertNotNull(dto);
        assertNull(dto.getHomeClubId());
        assertNull(dto.getClubVisitorId());
        assertNull(dto.getStadiumId());
        assertEquals(data, dto.getDateTimeDeparture());
    }

    @Test
    void testToDTOWithNullInput() {
        assertNull(FootballMatchMapper.toDTO(null));
    }

    @Test
    void testToEntity() {
        Long mandanteId = 1L, visitanteId = 2L, estadioId = 3L;
        LocalDateTime data = LocalDateTime.of(2024, 7, 1, 18, 0);
        FootballMatchDTO dto = new FootballMatchDTO(99L, mandanteId, visitanteId, estadioId, data, 4, 2);

        ClubEntity mandante = new ClubEntity(mandanteId, "Home", "SP", LocalDate.of(2000,1,1), true);
        ClubEntity visitante = new ClubEntity(visitanteId, "Away", "MG", LocalDate.of(1998,12,31), true);
        StadiumEntity estadio = new StadiumEntity(estadioId, "Mineirão", "MG", true, LocalDate.of(1965,9,5));

        FootballMatch entity = FootballMatchMapper.toEntity(dto, mandante, visitante, estadio);

        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(mandante, entity.getHomeClub());
        assertEquals(visitante, entity.getClubVisitor());
        assertEquals(estadio, entity.getStadium());
        assertEquals(data, entity.getDateTimeDeparture());
        assertEquals(dto.getHomeTeamGoals(), entity.getHomeTeamGoals());
        assertEquals(dto.getGoalsVisitor(), entity.getGoalsVisitor());
    }

    @Test
    void testToEntityWithNullInput() {
        assertNull(FootballMatchMapper.toEntity(null, null, null, null));
    }

    @Test
    void testToDtoEqualsToDTO() {
        ClubEntity mandante = new ClubEntity(5L, "Galo", "MG", LocalDate.of(1908,3,25), true);
        ClubEntity visitante = new ClubEntity(6L, "Vasco", "RJ", LocalDate.of(1898,8,21), true);
        StadiumEntity estadio = new StadiumEntity(8L, "Independência", "MG", true, LocalDate.of(1950,6,25));
        LocalDateTime data = LocalDateTime.of(2025,3,18,19,0);
        FootballMatch entity = new FootballMatch(20L, mandante, visitante, estadio, data, 1, 2);

        // Deve ser igual pois toDto e toDTO fazem a mesma função
        FootballMatchDTO dtoWithDto = FootballMatchMapper.toDto(entity);
        FootballMatchDTO dtoWithDTO = FootballMatchMapper.toDTO(entity);

        assertNotNull(dtoWithDto);
        assertEquals(dtoWithDto.getHomeClubId(), dtoWithDTO.getHomeClubId());
        assertEquals(dtoWithDto.getClubVisitorId(), dtoWithDTO.getClubVisitorId());
        assertEquals(dtoWithDto.getStadiumId(), dtoWithDTO.getStadiumId());
        assertEquals(dtoWithDto.getId(), dtoWithDTO.getId());
    }

    @Test
    void testToDtoWithNull() {
        assertNull(FootballMatchMapper.toDto(null));
    }
}