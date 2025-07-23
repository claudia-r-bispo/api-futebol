package com.neoCamp.footballMatch.repository;

import com.neoCamp.footballMatch.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class FootballMatchRepositoryTest {

    @Autowired
    private FootballMatchRepository footballMatchRepository;

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private StadiumRepository stadiumRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Test
    void save_FootballMatch_Success() {

        ClubEntity homeClub = createAndSaveClub("São Paulo FC");
        ClubEntity visitorClub = createAndSaveClub("Corinthians");
        StadiumEntity stadium = createAndSaveStadium("Arena Test");

        FootballMatch match = new FootballMatch();
        match.setHomeClub(homeClub);
        match.setClubVisitor(visitorClub);
        match.setStadium(stadium);
        match.setDateTimeDeparture(LocalDateTime.now());
        match.setHomeTeamGoals(2);
        match.setGoalsVisitor(1);


        FootballMatch savedMatch = footballMatchRepository.save(match);


        assertNotNull(savedMatch.getId());
        assertEquals(homeClub.getId(), savedMatch.getHomeClub().getId());
        assertEquals(visitorClub.getId(), savedMatch.getClubVisitor().getId());
        assertEquals(stadium.getId(), savedMatch.getStadium().getId());
    }

    @Test
    void findById_FootballMatch_Success() {

        ClubEntity homeClub = createAndSaveClub("Flamengo");
        ClubEntity visitorClub = createAndSaveClub("Vasco");
        StadiumEntity stadium = createAndSaveStadium("Maracanã");

        FootballMatch match = new FootballMatch();
        match.setHomeClub(homeClub);
        match.setClubVisitor(visitorClub);
        match.setStadium(stadium);
        match.setDateTimeDeparture(LocalDateTime.now());
        match.setHomeTeamGoals(3);
        match.setGoalsVisitor(0);

        FootballMatch savedMatch = footballMatchRepository.save(match);


        FootballMatch foundMatch = footballMatchRepository.findById(savedMatch.getId()).orElse(null);


        assertNotNull(foundMatch);
        assertEquals(savedMatch.getId(), foundMatch.getId());
        assertEquals(3, foundMatch.getHomeTeamGoals());
        assertEquals(0, foundMatch.getGoalsVisitor());
    }


    private ClubEntity createAndSaveClub(String name) {
        ClubEntity club = new ClubEntity();
        club.setName(name);
        club.setUf("SP");
        club.setActive(true);
        club.setDateCreation(LocalDate.now());
        return clubRepository.save(club);
    }

    private StadiumEntity createAndSaveStadium(String name) {

        AddressEntity address = new AddressEntity();
        address.setStreet("Avenida Paulista");
        address.setNumber("1000");
        address.setCity("São Paulo");
        address.setState("SP");
        address.setZipCode("01310-100");
        AddressEntity savedAddress = addressRepository.save(address);


        StadiumEntity stadium = new StadiumEntity();
        stadium.setName(name);
        stadium.setUf("SP");
        stadium.setActive(true);
        stadium.setDateCreation(LocalDate.now());
        stadium.setAddress(savedAddress);

        return stadiumRepository.save(stadium);
    }
}