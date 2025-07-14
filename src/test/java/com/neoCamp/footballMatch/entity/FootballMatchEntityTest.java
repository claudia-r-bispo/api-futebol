package com.neoCamp.footballMatch.entity;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class FootballMatchEntityTest {

    @Test
    void createFootballMatch_Success() {
        // Arrange
        ClubEntity homeClub = createTestClub(1L, "São Paulo FC");
        ClubEntity visitorClub = createTestClub(2L, "Corinthians");
        StadiumEntity stadium = createTestStadium(1L, "Arena Test");

        // Act
        FootballMatch match = new FootballMatch();
        match.setId(1L);
        match.setHomeClub(homeClub);
        match.setClubVisitor(visitorClub);
        match.setStadium(stadium);
        match.setHomeTeamGoals(2);
        match.setGoalsVisitor(1);
        match.setDateTimeDeparture(LocalDateTime.now());

        // Assert
        assertEquals(1L, match.getId());
        assertEquals(homeClub, match.getHomeClub());
        assertEquals(visitorClub, match.getClubVisitor());
        assertEquals(stadium, match.getStadium());
        assertEquals(2, match.getHomeTeamGoals());
        assertEquals(1, match.getGoalsVisitor());
        assertNotNull(match.getDateTimeDeparture());
    }

    @Test
    void createFootballMatch_AllArgsConstructor() {
        // Arrange
        ClubEntity homeClub = createTestClub(1L, "São Paulo FC");
        ClubEntity visitorClub = createTestClub(2L, "Corinthians");
        StadiumEntity stadium = createTestStadium(1L, "Arena Test");
        LocalDateTime matchTime = LocalDateTime.of(2024, 6, 15, 20, 0);

        // Act - CORRIGIR: A ordem correta dos parâmetros
        FootballMatch match = new FootballMatch(
                1L,                // id
                homeClub,          // homeClub
                visitorClub,       // clubVisitor
                stadium,           // stadium
                matchTime,         // dateTimeDeparture ← LocalDateTime deve vir ANTES dos Integer
                2,                 // homeTeamGoals
                1                  // goalsVisitor
        );

        // Assert
        assertEquals(1L, match.getId());
        assertEquals(homeClub, match.getHomeClub());
        assertEquals(visitorClub, match.getClubVisitor());
        assertEquals(stadium, match.getStadium());
        assertEquals(2, match.getHomeTeamGoals());
        assertEquals(1, match.getGoalsVisitor());
        assertEquals(matchTime, match.getDateTimeDeparture());
    }

    @Test
    void footballMatch_SettersAndGetters() {
        // Arrange
        FootballMatch match = new FootballMatch();
        ClubEntity homeClub = createTestClub(1L, "Flamengo");
        ClubEntity visitorClub = createTestClub(2L, "Vasco");
        StadiumEntity stadium = createTestStadium(1L, "Maracanã");
        LocalDateTime matchTime = LocalDateTime.now();

        // Act
        match.setId(2L);
        match.setHomeClub(homeClub);
        match.setClubVisitor(visitorClub);
        match.setStadium(stadium);
        match.setHomeTeamGoals(3);
        match.setGoalsVisitor(0);
        match.setDateTimeDeparture(matchTime);

        // Assert
        assertEquals(2L, match.getId());
        assertEquals(homeClub, match.getHomeClub());
        assertEquals(visitorClub, match.getClubVisitor());
        assertEquals(stadium, match.getStadium());
        assertEquals(3, match.getHomeTeamGoals());
        assertEquals(0, match.getGoalsVisitor());
        assertEquals(matchTime, match.getDateTimeDeparture());
    }

    @Test
    void footballMatch_NullValues() {
        // Arrange & Act
        FootballMatch match = new FootballMatch();

        // Assert
        assertNull(match.getId());
        assertNull(match.getHomeClub());
        assertNull(match.getClubVisitor());
        assertNull(match.getStadium());
        assertNull(match.getHomeTeamGoals()); // Integer pode ser null
        assertNull(match.getGoalsVisitor());  // Integer pode ser null
        assertNull(match.getDateTimeDeparture());
    }

    @Test
    void footballMatch_EqualsAndHashCode() {
        // Arrange
        ClubEntity homeClub = createTestClub(1L, "Palmeiras");
        ClubEntity visitorClub = createTestClub(2L, "Santos");
        StadiumEntity stadium = createTestStadium(1L, "Allianz Parque");

        FootballMatch match1 = new FootballMatch();
        match1.setId(1L);
        match1.setHomeClub(homeClub);
        match1.setClubVisitor(visitorClub);
        match1.setStadium(stadium);

        FootballMatch match2 = new FootballMatch();
        match2.setId(1L);
        match2.setHomeClub(homeClub);
        match2.setClubVisitor(visitorClub);
        match2.setStadium(stadium);

        // Act & Assert - Lombok @Data gera equals e hashCode
        assertEquals(match1, match2);
        assertEquals(match1.hashCode(), match2.hashCode());
    }

    @Test
    void footballMatch_ToString() {
        // Arrange
        FootballMatch match = new FootballMatch();
        match.setId(1L);
        match.setHomeTeamGoals(2);
        match.setGoalsVisitor(1);

        // Act
        String toString = match.toString();

        // Assert - Lombok @Data gera toString
        assertNotNull(toString);
        assertTrue(toString.contains("1"));
        assertTrue(toString.contains("2"));
    }

    // Helper Methods
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
        // Criar address primeiro
        AddressEntity address = new AddressEntity();
        address.setId(1L);
        address.setLogradouro("Avenida Paulista");
        address.setCidade("São Paulo");
        address.setEstado("SP");
        address.setCep("01310-100");

        // Criar stadium com address
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