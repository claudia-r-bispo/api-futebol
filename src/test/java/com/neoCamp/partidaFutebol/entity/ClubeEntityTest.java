package com.neoCamp.partidaFutebol.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ClubeEntityTest {

    @Test
    void testAllArgsConstructorAndGetters() {
        LocalDate dataCriacao = LocalDate.of(2020, 1, 1);
        ClubeEntity clube = new ClubeEntity(1L, "Flamengo", "RJ", dataCriacao, true);
        assertEquals(1L, clube.getId());
        assertEquals("Flamengo", clube.getNome());
        assertEquals("RJ", clube.getUf());
        assertEquals(dataCriacao, clube.getDtCriacao());
        assertTrue(clube.isAtivo());
    }

    @Test
    void testSetters() {
        LocalDate dataCriacao = LocalDate.of(2021, 2, 2);
        ClubeEntity clube = new ClubeEntity();
        clube.setId(2L);
        clube.setNome("Palmeiras");
        clube.setUf("SP");
        clube.setDtCriacao(dataCriacao);
        clube.setAtivo(false);

        assertEquals(2L, clube.getId());
        assertEquals("Palmeiras", clube.getNome());
        assertEquals("SP", clube.getUf());
        assertEquals(dataCriacao, clube.getDtCriacao());
        assertFalse(clube.isAtivo());
    }

    @Test
    void testEqualsAndHashCode() {
        LocalDate dataCriacao = LocalDate.of(2020, 1, 1);
        ClubeEntity clube1 = new ClubeEntity(1L, "Flamengo", "RJ", dataCriacao, true);
        ClubeEntity clube2 = new ClubeEntity(1L, "Flamengo", "RJ", dataCriacao, true);

        assertEquals(clube1, clube2);
        assertEquals(clube1.hashCode(), clube2.hashCode());
    }

    @Test
    void testToString() {
        LocalDate dataCriacao = LocalDate.of(2020, 1, 1);
        ClubeEntity clube = new ClubeEntity(1L, "Flamengo", "RJ", dataCriacao, true);
        String str = clube.toString();
        assertTrue(str.contains("Flamengo"));
        assertTrue(str.contains("RJ"));
        assertTrue(str.contains("2020"));
    }
}
