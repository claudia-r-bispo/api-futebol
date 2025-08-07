package com.neoCamp.footballMatch.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ClubeEntityTest {
    private UUID testId1;
    private UUID testId2;
    private LocalDate dateCreation1;
    private LocalDate dateCreation2;

    @BeforeEach
    void setUp() {
        testId1 = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        testId2 = UUID.fromString("223e4567-e89b-12d3-a456-426614174001");
        dateCreation1 = LocalDate.of(2020, 1, 1);
        dateCreation2 = LocalDate.of(2021, 2, 2);
    }

    @Test
    void testAllArgsConstructorAndGetters() {
        ClubEntity club = new ClubEntity(testId1, "Flamengo", "RJ", dateCreation1, true);
        
        assertEquals(testId1, club.getId(), "ID should match");
        assertEquals("Flamengo", club.getName(), "Name should match");
        assertEquals("RJ", club.getUf(), "UF should match");
        assertEquals(dateCreation1, club.getDateCreation(), "Creation date should match");
        assertTrue(club.isActive(), "Active status should be true");
    }

    @Test
    void testSetters() {
        ClubEntity club = new ClubEntity();
        club.setId(testId2);
        club.setName("Palmeiras");
        club.setUf("SP");
        club.setDateCreation(dateCreation2);
        club.setActive(false);

        assertEquals(testId2, club.getId(), "ID should be set correctly");
        assertEquals("Palmeiras", club.getName(), "Name should be set correctly");
        assertEquals("SP", club.getUf(), "UF should be set correctly");
        assertEquals(dateCreation2, club.getDateCreation(), "Creation date should be set correctly");
        assertFalse(club.isActive(), "Active status should be false");
    }

    @Test
    void testEqualsAndHashCode() {
        ClubEntity club1 = new ClubEntity(testId1, "Flamengo", "RJ", dateCreation1, true);
        ClubEntity club2 = new ClubEntity(testId1, "Flamengo", "RJ", dateCreation1, true);
        ClubEntity club3 = new ClubEntity(testId2, "Palmeiras", "SP", dateCreation2, false);

        // Test equality
        assertEquals(club1, club2, "Clubs with same ID should be equal");
        assertNotEquals(club1, club3, "Clubs with different IDs should not be equal");
        
        // Test hash code
        assertEquals(club1.hashCode(), club2.hashCode(), "Hash codes should be equal for equal objects");
        assertNotEquals(club1.hashCode(), club3.hashCode(), "Hash codes should be different for different objects");
    }

    @Test
    void testToString() {
        ClubEntity club = new ClubEntity(testId1, "Flamengo", "RJ", dateCreation1, true);
        String str = club.toString();
        assertTrue(str.contains("Flamengo"), "String should contain club name");
        assertTrue(str.contains("RJ"), "String should contain UF");
        assertTrue(str.contains("2020"), "String should contain year");
    }
}
