package com.neoCamp.footballMatch.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class StadiumEntityTest {

    @BeforeEach
    @Test
    void testAllArgsConstructorAndGetters() {
        LocalDate dateCreation = LocalDate.of(2022, 6, 30);
        StadiumEntity stadium = new StadiumEntity(1L, "Maracanã", "RJ", true, dateCreation);

        assertEquals(1L, stadium.getId());
        assertEquals("Maracanã", stadium.getName());
        assertEquals("RJ", stadium.getUf());
        assertTrue(stadium.isActive());
        assertEquals(dateCreation, stadium.getDateCreation());
    }

    @Test
    void testSetters() {
        LocalDate dateCreation = LocalDate.of(2021, 2, 2);
        StadiumEntity stadium = new StadiumEntity();
        stadium.setId(2L);
        stadium.setName("Allianz Parque");
        stadium.setUf("SP");
        stadium.setActive(false);
        stadium.setDateCreation(dateCreation);

        assertEquals(2L, stadium.getId());
        assertEquals("Allianz Parque", stadium.getName());
        assertEquals("SP", stadium.getUf());
        assertFalse(stadium.isActive());
        assertEquals(dateCreation, stadium.getDateCreation());
    }

    @Test
    void testEqualsAndHashCode() {
        LocalDate dataCriacao = LocalDate.of(2022, 6, 30);
        StadiumEntity est1 = new StadiumEntity(1L, "Maracanã", "RJ", true, dataCriacao);
        StadiumEntity est2 = new StadiumEntity(1L, "Maracanã", "RJ", true, dataCriacao);

        assertEquals(est1, est2);
        assertEquals(est1.hashCode(), est2.hashCode());
    }

    @Test
    void testToString() {
        LocalDate dataCriacao = LocalDate.of(2022, 6, 30);
        StadiumEntity estadio = new StadiumEntity(1L, "Maracanã", "RJ", true, dataCriacao);
        String str = estadio.toString();
        assertTrue(str.contains("Maracanã"));
        assertTrue(str.contains("RJ"));
        assertTrue(str.contains("2022"));
    }
}