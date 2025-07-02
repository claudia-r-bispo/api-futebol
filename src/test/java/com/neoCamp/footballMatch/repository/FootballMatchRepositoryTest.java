package com.neoCamp.footballMatch.repository;

import com.neoCamp.footballMatch.entity.ClubEntity;
import com.neoCamp.footballMatch.entity.StadiumEntity;
import com.neoCamp.footballMatch.entity.FootballMatch;
import lombok.Data;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class FootballMatchRepositoryTest {

    @Autowired
    FootballMatchRepository footballMatchRepository;

    @Autowired
    ClubRepository clubRepository;

    @Autowired
    StadiumRepository stadiumRepository;

    @BeforeEach
    @Test
    @DisplayName("Testa save, findById e método custom (homeclub ou visitor)")
    void testCrudAndFindByHomeClubOrVisitor() {

        ClubEntity homeClub = clubRepository.save(new ClubEntity(null, "Grêmio", "RS", LocalDate.now(), true));
        ClubEntity Visitor = clubRepository.save(new ClubEntity(null, "Cruzeiro", "MG", LocalDate.now(), true));
        ClubEntity outro = clubRepository.save(new ClubEntity(null, "Bahia", "BA", LocalDate.now(), true));
        StadiumEntity stadium = stadiumRepository.save(new StadiumEntity(null, "Beira-Rio", "RS", true, LocalDate.of(1969, 4, 6)));


        var partida1 = new FootballMatch(null, homeClub, Visitor, stadium, LocalDateTime.now(), 2, 0);
        var partida2 = new FootballMatch(null, outro, homeClub, stadium, LocalDateTime.now(), 1, 2);
        var partida3 = new FootballMatch(null, outro, outro, stadium, LocalDateTime.now(), 0, 0);
        partida1 = footballMatchRepository.save(partida1);
        partida2 = footballMatchRepository.save(partida2);
        partida3 = footballMatchRepository.save(partida3);


        var p = footballMatchRepository.findById(partida1.getId());
        assertTrue(p.isPresent());
        assertEquals(homeClub.getId(), p.get().getHomeClub().getId());

        // Teste do método customizado: partidas em que o mandante OU visitante é o Grêmio
        Pageable pageable = PageRequest.of(0, 10);
        Page<FootballMatch> page = footballMatchRepository.findByHomeClubIdOrClubVisitorId(
                homeClub.getId(),
                homeClub.getId(),
                pageable);

        assertEquals(2, page.getContent().size());
        assertTrue(
                page.getContent().stream().anyMatch(pe -> pe.getHomeClub().getId().equals(homeClub.getId()))
        );
        assertTrue(
                page.getContent().stream().anyMatch(pe -> pe.getClubVisitor().getId().equals(homeClub.getId()))
        );
    }
}
