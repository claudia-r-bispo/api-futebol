package com.neoCamp.footballMatch.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ClubeEntityTest {

    @BeforeEach
    @Test
    void testAllArgsConstructorAndGetters() {
        LocalDate dateCreation = LocalDate.of(2020, 1, 1);
        ClubEntity club = new ClubEntity(1L, "Flamengo", "RJ", dateCreation, true);
        assertEquals(1L, club.getId());
        assertEquals("Flamengo", club.getName());
        assertEquals("RJ", club.getUf());
        assertEquals(dateCreation, club.getDateCreation());
        assertTrue(club.isActive());
    }

    @Test
    void testSetters() {
        LocalDate dateCreation = LocalDate.of(2021, 2, 2);
        ClubEntity club = new ClubEntity();
        club.setId(2L);
        club.setName("Palmeiras");
        club.setUf("SP");
        club.setDateCreation(dateCreation);
        club.setActive(false);

        assertEquals(2L, club.getId());
        assertEquals("Palmeiras", club.getName());
        assertEquals("SP", club.getUf());
        assertEquals(dateCreation, club.getDateCreation());
        assertFalse(club.isActive());
    }

    @Test
    void testEqualsAndHashCode() {
        LocalDate dateCreation = LocalDate.of(2020, 1, 1);
        ClubEntity club1 = new ClubEntity(1L, "Flamengo", "RJ", dateCreation, true);
        ClubEntity club2 = new ClubEntity(1L, "Flamengo", "RJ", dateCreation, true);

        assertEquals(club1, club2);
        assertEquals(club1.hashCode(), club2.hashCode());
    }

    @Test
    void testToString() {
        LocalDate dateCreation = LocalDate.of(2020, 1, 1);
        ClubEntity club = new ClubEntity(1L, "Flamengo", "RJ", dateCreation, true);
        String str = club.toString();
        assertTrue(str.contains("Flamengo"));
        assertTrue(str.contains("RJ"));
        assertTrue(str.contains("2020"));
    }
}
