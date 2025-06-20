package com.neoCamp.partidaFutebol.repository;

import com.neoCamp.partidaFutebol.entity.ClubeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubeRepository extends JpaRepository<ClubeEntity, Long> {

    Page<ClubeEntity> findByNomeContainingIgnoreCaseAndUfContainingIgnoreCaseAndAtivo(String nome, String uf, Boolean ativo, Pageable pageable);

    Page<ClubeEntity> findByNomeContainingIgnoreCaseAndUfContainingIgnoreCase(String nome, String uf, Pageable pageable);


}