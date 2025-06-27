package com.neoCamp.partidaFutebol.repository;

import com.neoCamp.partidaFutebol.entity.ClubeEntity;
import com.neoCamp.partidaFutebol.entity.EstadioEntity;
import com.neoCamp.partidaFutebol.entity.PartidaEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PartidaRepositoryTest {

    @Autowired
    PartidaRepository partidaRepository;
    @Autowired
    ClubeRepository clubeRepository;
    @Autowired
    EstadioRepository estadioRepository;

    @Test
    @DisplayName("Testa save, findById e método custom (mandante ou visitante)")
    void testCrudAndFindByMandanteOrVisitante() {
        // Salva clubes e estádio
        ClubeEntity mandante = clubeRepository.save(new ClubeEntity(null, "Grêmio", "RS", LocalDate.now(), true));
        ClubeEntity visitante = clubeRepository.save(new ClubeEntity(null, "Cruzeiro", "MG", LocalDate.now(), true));
        ClubeEntity outro = clubeRepository.save(new ClubeEntity(null, "Bahia", "BA", LocalDate.now(), true));
        EstadioEntity estadio = estadioRepository.save(new EstadioEntity(null, "Beira-Rio", "RS", true, LocalDate.of(1969, 4, 6)));

        // Cria partidas
        var partida1 = new PartidaEntity(null, mandante, visitante, estadio, LocalDateTime.now(), 2, 0);
        var partida2 = new PartidaEntity(null, outro, mandante, estadio, LocalDateTime.now(), 1, 2);
        var partida3 = new PartidaEntity(null, outro, outro, estadio, LocalDateTime.now(), 0, 0);
        partida1 = partidaRepository.save(partida1);
        partida2 = partidaRepository.save(partida2);
        partida3 = partidaRepository.save(partida3);

        // Teste básico de findById
        var p = partidaRepository.findById(partida1.getId());
        assertTrue(p.isPresent());
        assertEquals(mandante.getId(), p.get().getClubeMandante().getId());

        // Teste do método customizado: partidas em que o mandante OU visitante é o Grêmio
        Pageable pageable = PageRequest.of(0, 10);
        Page<PartidaEntity> page = partidaRepository.findByClubeMandanteIdOrClubeVisitanteId(
                mandante.getId(),
                mandante.getId(),
                pageable);

        assertEquals(2, page.getContent().size());
        assertTrue(
                page.getContent().stream().anyMatch(pe -> pe.getClubeMandante().getId().equals(mandante.getId()))
        );
        assertTrue(
                page.getContent().stream().anyMatch(pe -> pe.getClubeVisitante().getId().equals(mandante.getId()))
        );
    }
}
