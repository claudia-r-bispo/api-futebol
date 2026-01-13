package com.neoCamp.footballMatch.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class FootballMatchEntityTest {
    private UUID testId1;
    private UUID testId2;
    private UUID testStadiumId1;
    private UUID testAddressId1;
    
    @BeforeEach
    void setUp() {
        testId1 = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        testId2 = UUID.fromString("223e4567-e89b-12d3-a456-426614174001");
        testStadiumId1 = UUID.fromString("323e4567-e89b-12d3-a456-426614174002");
        testAddressId1 = UUID.fromString("423e4567-e89b-12d3-a456-426614174003");
    }

    @Test
    void createFootballMatch_Success() {
        ClubEntity homeClub = createTestClub(testId1, "São Paulo FC");
        ClubEntity visitorClub = createTestClub(testId2, "Corinthians");
        StadiumEntity stadium = createTestStadium(testStadiumId1, "Arena Test");

        FootballMatch match = new FootballMatch();
        match.setId(testId1);
        match.setHomeClub(homeClub);
        match.setClubVisitor(visitorClub);
        match.setStadium(stadium);
        match.setHomeTeamGoals(2);
        match.setGoalsVisitor(1);
        match.setDateTimeDeparture(LocalDateTime.now());

        assertEquals(testId1, match.getId(), "ID should match");
        assertEquals(homeClub, match.getHomeClub(), "Home club should match");
        assertEquals(visitorClub, match.getClubVisitor(), "Visitor club should match");
        assertEquals(stadium, match.getStadium(), "Stadium should match");
        assertEquals(2, match.getHomeTeamGoals(), "Home team goals should be 2");
        assertEquals(1, match.getGoalsVisitor(), "Visitor goals should be 1");
        assertNotNull(match.getDateTimeDeparture(), "Match date should not be null");
    }

    @Test
    void createFootballMatch_AllArgsConstructor() {
        ClubEntity homeClub = createTestClub(testId1, "São Paulo FC");
        ClubEntity visitorClub = createTestClub(testId2, "Corinthians");
        StadiumEntity stadium = createTestStadium(testStadiumId1, "Arena Test");
        LocalDateTime matchTime = LocalDateTime.of(2024, 6, 15, 20, 0);

        FootballMatch match = new FootballMatch(
                testId1,          // id
                homeClub,         // homeClub
                visitorClub,      // clubVisitor
                stadium,          // stadium
                2,                // homeTeamGoals
                1,                // goalsVisitor
                matchTime         // dateTimeDeparture
        );

        assertEquals(testId1, match.getId(), "ID should match");
        assertEquals(homeClub, match.getHomeClub(), "Home club should match");
        assertEquals(visitorClub, match.getClubVisitor(), "Visitor club should match");
        assertEquals(stadium, match.getStadium(), "Stadium should match");
        assertEquals(2, match.getHomeTeamGoals(), "Home team goals should be 2");
        assertEquals(1, match.getGoalsVisitor(), "Visitor goals should be 1");
        assertEquals(matchTime, match.getDateTimeDeparture(), "Match time should match");
    }

    @Test
    void footballMatch_SettersAndGetters() {
        FootballMatch match = new FootballMatch();
        ClubEntity homeClub = createTestClub(testId1, "Flamengo");
        ClubEntity visitorClub = createTestClub(testId2, "Vasco");
        StadiumEntity stadium = createTestStadium(testStadiumId1, "Maracanã");
        LocalDateTime matchTime = LocalDateTime.now();

        match.setId(testId2);
        match.setHomeClub(homeClub);
        match.setClubVisitor(visitorClub);
        match.setStadium(stadium);
        match.setHomeTeamGoals(3);
        match.setGoalsVisitor(0);
        match.setDateTimeDeparture(matchTime);

        assertEquals(testId2, match.getId(), "ID should match");
        assertEquals(homeClub, match.getHomeClub(), "Home club should match");
        assertEquals(visitorClub, match.getClubVisitor(), "Visitor club should match");
        assertEquals(stadium, match.getStadium(), "Stadium should match");
        assertEquals(3, match.getHomeTeamGoals(), "Home team goals should be 3");
        assertEquals(0, match.getGoalsVisitor(), "Visitor goals should be 0");
        assertEquals(matchTime, match.getDateTimeDeparture(), "Match time should match");
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
        ClubEntity homeClub = createTestClub(testId1, "Palmeiras");
        ClubEntity visitorClub = createTestClub(testId2, "Santos");
        StadiumEntity stadium = createTestStadium(testStadiumId1, "Allianz Parque");

        FootballMatch match1 = new FootballMatch();
        match1.setId(testId1);
        match1.setHomeClub(homeClub);
        match1.setClubVisitor(visitorClub);
        match1.setStadium(stadium);

        FootballMatch match2 = new FootballMatch();
        match2.setId(testId1);
        match2.setHomeClub(homeClub);
        match2.setClubVisitor(visitorClub);
        match2.setStadium(stadium);

        FootballMatch match3 = new FootballMatch();
        match3.setId(testId2);
        match3.setHomeClub(homeClub);
        match3.setClubVisitor(visitorClub);
        match3.setStadium(stadium);

        // Test equality
        assertEquals(match1, match2, "Matches with same ID should be equal");
        assertNotEquals(match1, match3, "Matches with different IDs should not be equal");
        
        // Test hash code
        assertEquals(match1.hashCode(), match2.hashCode(), 
            "Hash codes should be equal for equal objects");
        assertNotEquals(match1.hashCode(), match3.hashCode(),
            "Hash codes should be different for different objects");
    }

    @Test
    void footballMatch_ToString() {
        FootballMatch match = new FootballMatch();
        match.setId(testId1);
        match.setHomeTeamGoals(2);
        match.setGoalsVisitor(1);

        String toString = match.toString();

        assertNotNull(toString, "toString() should not return null");
        assertTrue(toString.contains(testId1.toString()), 
            "String should contain the match ID");
        assertTrue(toString.contains("2"), 
            "String should contain home team goals (2)");
        assertTrue(toString.contains("1"), 
            "String should contain visitor goals (1)");
    }


    private ClubEntity createTestClub(UUID id, String name) {
        ClubEntity club = new ClubEntity();
        club.setId(id);
        club.setName(name);
        club.setUf("SP");
        club.setActive(true);
        club.setDateCreation(LocalDate.now());
        return club;
    }

    private StadiumEntity createTestStadium(UUID id, String name) {
        AddressEntity address = new AddressEntity();
        address.setId(testAddressId1);
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