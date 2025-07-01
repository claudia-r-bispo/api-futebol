package com.neoCamp.footballMatch.repository;

import com.neoCamp.footballMatch.entity.ClubEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.*;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ClubRepositoryTest {

    @Autowired
    private ClubRepository clubRepository;

    @Test
    @DisplayName("findByNomeContainingIgnoreCaseAndUfContainingIgnoreCaseAndAtivo works")
    void testFindByNameContainingAndUfContainingAndAtivo() {
        ClubEntity clube1 = new ClubEntity(null, "Flamengo", "RJ", LocalDate.of(1895,11,15), true);
        ClubEntity clube2 = new ClubEntity(null, "Fluminense", "RJ", LocalDate.of(1902,10,1), false);
        clubRepository.saveAll(List.of(clube1, clube2));

        Pageable pageable = PageRequest.of(0, 10);

        Page<ClubEntity> page = clubRepository.findByNameContainingIgnoreCaseAndUfContainingIgnoreCaseAndActive(
                "flam", "rj", true, pageable);

        assertEquals(1, page.getTotalElements());
        assertEquals("Flamengo", page.getContent().get(0).getName());
    }

    @Test
    @DisplayName("findByNameContainingIgnoreCaseAndUfContainingIgnoreCase works")
    void testFindByNameContainingAndUfContaining() {
        ClubEntity clube1 = new ClubEntity(null, "Palmeiras", "SP", LocalDate.of(1914,8,26), true);
        ClubEntity clube2 = new ClubEntity(null, "São Paulo", "SP", LocalDate.of(1930,12,16), true);
        clubRepository.saveAll(List.of(clube1, clube2));

        Pageable pageable = PageRequest.of(0, 10);

        Page<ClubEntity> page = clubRepository.findByNameContainingIgnoreCaseAndUfContainingIgnoreCase(
                "pa", "sp", pageable);

        assertEquals(2, page.getTotalElements());
        List<String> nomes = page.map(ClubEntity::getName).getContent();
        assertTrue(nomes.contains("Palmeiras"));
        assertTrue(nomes.contains("São Paulo"));
    }
}

