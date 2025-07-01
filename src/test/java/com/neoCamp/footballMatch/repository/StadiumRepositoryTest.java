package com.neoCamp.footballMatch.repository;

import com.neoCamp.footballMatch.entity.StadiumEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class StadiumRepositoryTest {

    @Autowired
    private StadiumRepository stadiumRepository;

    @Test
    @DisplayName("Test save and findById works")
    void testSaveAndFindById() {
        StadiumEntity estadio = new StadiumEntity(
                null,
                "Beira Rio",
                "RS",
                true,
                LocalDate.of(1969, 4, 6)
        );

        StadiumEntity saved = stadiumRepository.save(estadio);
        assertNotNull(saved.getId());

        Optional<StadiumEntity> found = stadiumRepository.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("Beira Rio", found.get().getName());
        assertEquals("RS", found.get().getUf());
        assertTrue(found.get().isActive());
        assertEquals(LocalDate.of(1969, 4, 6), found.get().getDateCreation());
    }

    @Test
    @DisplayName("Test findAll works")
    void testFindAll() {
        StadiumEntity estadio1 = new StadiumEntity(null, "Estádio 1", "SP", true, LocalDate.now());
        StadiumEntity estadio2 = new StadiumEntity(null, "Estádio 2", "RJ", false, LocalDate.now());
        stadiumRepository.save(estadio1);
        stadiumRepository.save(estadio2);

        var list = stadiumRepository.findAll();
        assertTrue(list.size() >= 2);
        assertTrue(list.stream().anyMatch(e -> e.getName().equals("Estádio 1")));
        assertTrue(list.stream().anyMatch(e -> e.getName().equals("Estádio 2")));
    }

    @Test
    @DisplayName("Test delete works")
    void testDelete() {
        StadiumEntity estadio = new StadiumEntity(null, "DeleteMe", "MG", true, LocalDate.now());
        estadio = stadiumRepository.save(estadio);
        Long id = estadio.getId();
        assertNotNull(id);

        stadiumRepository.deleteById(id);
        assertFalse(stadiumRepository.findById(id).isPresent());
    }
}
