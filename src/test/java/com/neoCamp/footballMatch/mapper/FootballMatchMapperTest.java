package com.neoCamp.footballMatch.mapper;

import com.neoCamp.footballMatch.dto.FootballMatchDTO;
import com.neoCamp.footballMatch.entity.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class FootballMatchMapperTest {

    @Test
    void toEntity_Success() {

        FootballMatchDTO dto = createFootballMatchDTO();
        ClubEntity homeClub = createTestClub(1L, "São Paulo FC");
        ClubEntity visitorClub = createTestClub(2L, "Corinthians");
        StadiumEntity stadium = createTestStadium(1L, "Arena Test");


        FootballMatch entity = FootballMatchMapper.toEntity(dto, homeClub, visitorClub, stadium);


        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getHomeTeamGoals(), entity.getHomeTeamGoals());
        assertEquals(dto.getGoalsVisitor(), entity.getGoalsVisitor());
        assertEquals(dto.getDateTimeDeparture(), entity.getDateTimeDeparture());
        assertEquals(homeClub, entity.getHomeClub());
        assertEquals(visitorClub, entity.getClubVisitor());
        assertEquals(stadium, entity.getStadium());
    }

    @Test
    void toEntity_NullDto_ReturnsNull() {

        ClubEntity homeClub = createTestClub(1L, "São Paulo FC");
        ClubEntity visitorClub = createTestClub(2L, "Corinthians");
        StadiumEntity stadium = createTestStadium(1L, "Arena Test");


        FootballMatch entity = FootballMatchMapper.toEntity(null, homeClub, visitorClub, stadium);


        assertNull(entity);
    }

    @Test
    void toDTO_Success() {

        FootballMatch entity = createFootballMatchEntity();


        FootballMatchDTO dto = FootballMatchMapper.toDTO(entity);

        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getHomeTeamGoals(), dto.getHomeTeamGoals());
        assertEquals(entity.getGoalsVisitor(), dto.getGoalsVisitor());
        assertEquals(entity.getDateTimeDeparture(), dto.getDateTimeDeparture());
        assertEquals(entity.getHomeClub().getId(), dto.getHomeClubId());
        assertEquals(entity.getClubVisitor().getId(), dto.getClubVisitorId());
        assertEquals(entity.getStadium().getId(), dto.getStadiumId());
    }

    @Test
    void toDTO_NullEntity_ReturnsNull() {

        FootballMatchDTO dto = FootballMatchMapper.toDTO(null);


        assertNull(dto);
    }


    private FootballMatchDTO createFootballMatchDTO() {
        FootballMatchDTO dto = new FootballMatchDTO();
        dto.setId(1L);
        dto.setHomeClubId(1L);
        dto.setClubVisitorId(2L);
        dto.setStadiumId(1L);
        dto.setDateTimeDeparture(LocalDateTime.of(2024, 6, 15, 20, 0));
        dto.setHomeTeamGoals(2);
        dto.setGoalsVisitor(1);
        return dto;
    }

    private FootballMatch createFootballMatchEntity() {
        ClubEntity homeClub = createTestClub(1L, "São Paulo FC");
        ClubEntity visitorClub = createTestClub(2L, "Corinthians");
        StadiumEntity stadium = createTestStadium(1L, "Arena Test");

        FootballMatch match = new FootballMatch();
        match.setId(1L);
        match.setHomeClub(homeClub);
        match.setClubVisitor(visitorClub);
        match.setStadium(stadium);
        match.setDateTimeDeparture(LocalDateTime.of(2024, 6, 15, 20, 0));
        match.setHomeTeamGoals(2);
        match.setGoalsVisitor(1);

        return match;
    }

    private ClubEntity createTestClub(Long id, String name) {
        ClubEntity club = new ClubEntity();
        club.setId(id);
        club.setName(name);
        club.setUf("SP");
        club.setActive(true);
        club.setDateCreation(LocalDate.now());
        return club;
    }

    private StadiumEntity createTestStadium(Long id, String name) {
        AddressEntity address = new AddressEntity();
        address.setId(1L);
        address.setStreet("Avenida Paulista");
        address.setNumber("1000");
        address.setCity("São Paulo");
        address.setState("SP");
        address.setZipCode("01310-100");

        StadiumEntity stadium = new StadiumEntity();
        stadium.setId(id);
        stadium.setName(name);
        stadium.setUf("SP");
        stadium.setActive(true);
        stadium.setDateCreation(LocalDate.now());
        stadium.setAddress(address);

        return stadium;
    }
}