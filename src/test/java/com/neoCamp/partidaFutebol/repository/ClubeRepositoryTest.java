package com.neoCamp.partidaFutebol.repository;

import com.neoCamp.partidaFutebol.entity.ClubeEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.*;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ClubeRepositoryTest {

    @Autowired
    private ClubeRepository clubeRepository;

    @Test
    @DisplayName("findByNomeContainingIgnoreCaseAndUfContainingIgnoreCaseAndAtivo works")
    void testFindByNomeContainingAndUfContainingAndAtivo() {
        ClubeEntity clube1 = new ClubeEntity(null, "Flamengo", "RJ", LocalDate.of(1895,11,15), true);
        ClubeEntity clube2 = new ClubeEntity(null, "Fluminense", "RJ", LocalDate.of(1902,10,1), false);
        clubeRepository.saveAll(List.of(clube1, clube2));

        Pageable pageable = PageRequest.of(0, 10);

        Page<ClubeEntity> page = clubeRepository.findByNomeContainingIgnoreCaseAndUfContainingIgnoreCaseAndAtivo(
                "flam", "rj", true, pageable);

        assertEquals(1, page.getTotalElements());
        assertEquals("Flamengo", page.getContent().get(0).getNome());
    }

    @Test
    @DisplayName("findByNomeContainingIgnoreCaseAndUfContainingIgnoreCase works")
    void testFindByNomeContainingAndUfContaining() {
        ClubeEntity clube1 = new ClubeEntity(null, "Palmeiras", "SP", LocalDate.of(1914,8,26), true);
        ClubeEntity clube2 = new ClubeEntity(null, "São Paulo", "SP", LocalDate.of(1930,12,16), true);
        clubeRepository.saveAll(List.of(clube1, clube2));

        Pageable pageable = PageRequest.of(0, 10);

        Page<ClubeEntity> page = clubeRepository.findByNomeContainingIgnoreCaseAndUfContainingIgnoreCase(
                "pa", "sp", pageable);

        assertEquals(2, page.getTotalElements());
        List<String> nomes = page.map(ClubeEntity::getNome).getContent();
        assertTrue(nomes.contains("Palmeiras"));
        assertTrue(nomes.contains("São Paulo"));
    }
}

