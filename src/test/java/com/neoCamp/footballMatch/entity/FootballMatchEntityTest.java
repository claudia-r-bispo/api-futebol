package com.neoCamp.footballMatch.entity;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class FootballMatchEntityTest {

    @Test
    void createFootballMatch_Success() {

        ClubEntity homeClub = createTestClub(1L, "São Paulo FC");
        ClubEntity visitorClub = createTestClub(2L, "Corinthians");
        StadiumEntity stadium = createTestStadium(1L, "Arena Test");


        FootballMatch match = new FootballMatch();
        match.setId(1L);
        match.setHomeClub(homeClub);
        match.setClubVisitor(visitorClub);
        match.setStadium(stadium);
        match.setHomeTeamGoals(2);
        match.setGoalsVisitor(1);
        match.setDateTimeDeparture(LocalDateTime.now());


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

        ClubEntity homeClub = createTestClub(1L, "São Paulo FC");
        ClubEntity visitorClub = createTestClub(2L, "Corinthians");
        StadiumEntity stadium = createTestStadium(1L, "Arena Test");
        LocalDateTime matchTime = LocalDateTime.of(2024, 6, 15, 20, 0);


        FootballMatch match = new FootballMatch(
                1L,                // id
                homeClub,          // homeClub
                visitorClub,       // clubVisitor
                stadium,           // stadium
                matchTime,         // dateTimeDeparture
                2,                 // homeTeamGoals
                1                  // goalsVisitor
        );


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

        FootballMatch match = new FootballMatch();
        ClubEntity homeClub = createTestClub(1L, "Flamengo");
        ClubEntity visitorClub = createTestClub(2L, "Vasco");
        StadiumEntity stadium = createTestStadium(1L, "Maracanã");
        LocalDateTime matchTime = LocalDateTime.now();


        match.setId(2L);
        match.setHomeClub(homeClub);
        match.setClubVisitor(visitorClub);
        match.setStadium(stadium);
        match.setHomeTeamGoals(3);
        match.setGoalsVisitor(0);
        match.setDateTimeDeparture(matchTime);


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

        FootballMatch match = new FootballMatch();


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


        assertEquals(match1, match2);
        assertEquals(match1.hashCode(), match2.hashCode());
    }

    @Test
    void footballMatch_ToString() {

        FootballMatch match = new FootballMatch();
        match.setId(1L);
        match.setHomeTeamGoals(2);
        match.setGoalsVisitor(1);


        String toString = match.toString();

        assertNotNull(toString);
        assertTrue(toString.contains("1"));
        assertTrue(toString.contains("2"));
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