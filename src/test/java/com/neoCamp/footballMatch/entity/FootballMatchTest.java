package com.neoCamp.footballMatch.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class FootballMatchTest {

    private ClubEntity homeClub;
    private ClubEntity clubVisitor;
    private StadiumEntity stadium;
    private LocalDateTime dateTime;

    @BeforeEach
    @Test
    void testAllArgsConstructorAndGetters() {
        ClubEntity homeClub = new ClubEntity(1L, "Mandante", "RJ", LocalDate.of(2020,1,1), true);
        ClubEntity clubVisitor = new ClubEntity(2L, "Visitante", "SP", LocalDate.of(2021,2,2), true);
        StadiumEntity stadium = new StadiumEntity(3L, "Maracanã", "RJ", true, LocalDate.of(1950,6,16));
        LocalDateTime dateTime = LocalDateTime.of(2024, 7, 1, 16, 30);

        FootballMatch match = new FootballMatch(10L, homeClub, clubVisitor, stadium, dateTime, 3, 1);

        assertEquals(10L, match.getId());
        assertEquals(homeClub, match.getHomeClub());
        assertEquals(clubVisitor, match.getClubVisitor());
        assertEquals(stadium, match.getStadium());
        assertEquals(dateTime, match.getDateTimeDeparture());
        assertEquals(3, match.getHomeTeamGoals());
        assertEquals(1, match.getGoalsVisitor());
    }

    @Test
    void testSetters() {
        FootballMatch match = new FootballMatch();
        ClubEntity homeClub = new ClubEntity();
        homeClub.setId(101L);

        match.setId(777L);
        match.setHomeClub(homeClub);
        match.setClubVisitor(new ClubEntity());
        match.setStadium(new StadiumEntity());
        match.setDateTimeDeparture(LocalDateTime.of(2024, 2, 21, 20, 30));
        match.setHomeTeamGoals(2);
        match.setGoalsVisitor(2);

        assertEquals(777L, match.getId());
        assertEquals(homeClub, match.getHomeClub());
        assertNotNull(match.getClubVisitor());
        assertNotNull(match.getStadium());
        assertEquals(LocalDateTime.of(2024, 2, 21, 20, 30), match.getDateTimeDeparture());
        assertEquals(2, match.getHomeTeamGoals());
        assertEquals(2, match.getGoalsVisitor());
    }

    @Test
    void testNoArgsConstructor() {
        FootballMatch match = new FootballMatch();
        assertNotNull(match);
    }
}
