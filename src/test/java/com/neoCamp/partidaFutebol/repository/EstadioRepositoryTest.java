package com.neoCamp.partidaFutebol.repository;

import com.neoCamp.partidaFutebol.entity.EstadioEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class EstadioRepositoryTest {

    @Autowired
    private EstadioRepository estadioRepository;

    @Test
    @DisplayName("Test save and findById works")
    void testSaveAndFindById() {
        EstadioEntity estadio = new EstadioEntity(
                null,
                "Beira Rio",
                "RS",
                true,
                LocalDate.of(1969, 4, 6)
        );

        EstadioEntity saved = estadioRepository.save(estadio);
        assertNotNull(saved.getId());

        Optional<EstadioEntity> found = estadioRepository.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("Beira Rio", found.get().getNome());
        assertEquals("RS", found.get().getUf());
        assertTrue(found.get().isAtivo());
        assertEquals(LocalDate.of(1969, 4, 6), found.get().getDtCriacao());
    }

    @Test
    @DisplayName("Test findAll works")
    void testFindAll() {
        EstadioEntity estadio1 = new EstadioEntity(null, "Estádio 1", "SP", true, LocalDate.now());
        EstadioEntity estadio2 = new EstadioEntity(null, "Estádio 2", "RJ", false, LocalDate.now());
        estadioRepository.save(estadio1);
        estadioRepository.save(estadio2);

        var list = estadioRepository.findAll();
        assertTrue(list.size() >= 2);
        assertTrue(list.stream().anyMatch(e -> e.getNome().equals("Estádio 1")));
        assertTrue(list.stream().anyMatch(e -> e.getNome().equals("Estádio 2")));
    }

    @Test
    @DisplayName("Test delete works")
    void testDelete() {
        EstadioEntity estadio = new EstadioEntity(null, "DeleteMe", "MG", true, LocalDate.now());
        estadio = estadioRepository.save(estadio);
        Long id = estadio.getId();
        assertNotNull(id);

        estadioRepository.deleteById(id);
        assertFalse(estadioRepository.findById(id).isPresent());
    }
}
