package com.neoCamp.partidaFutebol.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EstadioEntityTest {

    @Test
    void testAllArgsConstructorAndGetters() {
        LocalDate dataCriacao = LocalDate.of(2022, 6, 30);
        EstadioEntity estadio = new EstadioEntity(1L, "Maracanã", "RJ", true, dataCriacao);

        assertEquals(1L, estadio.getId());
        assertEquals("Maracanã", estadio.getNome());
        assertEquals("RJ", estadio.getUf());
        assertTrue(estadio.isAtivo());
        assertEquals(dataCriacao, estadio.getDtCriacao());
    }

    @Test
    void testSetters() {
        LocalDate dataCriacao = LocalDate.of(2021, 2, 2);
        EstadioEntity estadio = new EstadioEntity();
        estadio.setId(2L);
        estadio.setNome("Allianz Parque");
        estadio.setUf("SP");
        estadio.setAtivo(false);
        estadio.setDtCriacao(dataCriacao);

        assertEquals(2L, estadio.getId());
        assertEquals("Allianz Parque", estadio.getNome());
        assertEquals("SP", estadio.getUf());
        assertFalse(estadio.isAtivo());
        assertEquals(dataCriacao, estadio.getDtCriacao());
    }

    @Test
    void testEqualsAndHashCode() {
        LocalDate dataCriacao = LocalDate.of(2022, 6, 30);
        EstadioEntity est1 = new EstadioEntity(1L, "Maracanã", "RJ", true, dataCriacao);
        EstadioEntity est2 = new EstadioEntity(1L, "Maracanã", "RJ", true, dataCriacao);

        assertEquals(est1, est2);
        assertEquals(est1.hashCode(), est2.hashCode());
    }

    @Test
    void testToString() {
        LocalDate dataCriacao = LocalDate.of(2022, 6, 30);
        EstadioEntity estadio = new EstadioEntity(1L, "Maracanã", "RJ", true, dataCriacao);
        String str = estadio.toString();
        assertTrue(str.contains("Maracanã"));
        assertTrue(str.contains("RJ"));
        assertTrue(str.contains("2022"));
    }
}