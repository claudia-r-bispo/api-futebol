package com.neoCamp.partidaFutebol.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PartidaEntityTest {

    @Test
    void testAllArgsConstructorAndGetters() {
        ClubeEntity mandante = new ClubeEntity(1L, "Mandante", "RJ", LocalDate.of(2020,1,1), true);
        ClubeEntity visitante = new ClubeEntity(2L, "Visitante", "SP", LocalDate.of(2021,2,2), true);
        EstadioEntity estadio = new EstadioEntity(3L, "Maracanã", "RJ", true, LocalDate.of(1950,6,16));
        LocalDateTime dataHora = LocalDateTime.of(2024, 7, 1, 16, 30);

        PartidaEntity partida = new PartidaEntity(10L, mandante, visitante, estadio, dataHora, 3, 1);

        assertEquals(10L, partida.getId());
        assertEquals(mandante, partida.getClubeMandante());
        assertEquals(visitante, partida.getClubeVisitante());
        assertEquals(estadio, partida.getEstadio());
        assertEquals(dataHora, partida.getDataHoraPartida());
        assertEquals(3, partida.getGolsMandante());
        assertEquals(1, partida.getGolsVisitante());
    }

    @Test
    void testSetters() {
        PartidaEntity partida = new PartidaEntity();
        ClubeEntity mandante = new ClubeEntity();
        mandante.setId(101L);

        partida.setId(777L);
        partida.setClubeMandante(mandante);
        partida.setClubeVisitante(new ClubeEntity());
        partida.setEstadio(new EstadioEntity());
        partida.setDataHoraPartida(LocalDateTime.of(2024, 2, 21, 20, 30));
        partida.setGolsMandante(2);
        partida.setGolsVisitante(2);

        assertEquals(777L, partida.getId());
        assertEquals(mandante, partida.getClubeMandante());
        assertNotNull(partida.getClubeVisitante());
        assertNotNull(partida.getEstadio());
        assertEquals(LocalDateTime.of(2024, 2, 21, 20, 30), partida.getDataHoraPartida());
        assertEquals(2, partida.getGolsMandante());
        assertEquals(2, partida.getGolsVisitante());
    }

    @Test
    void testNoArgsConstructor() {
        PartidaEntity partida = new PartidaEntity();
        assertNotNull(partida);
    }
}
