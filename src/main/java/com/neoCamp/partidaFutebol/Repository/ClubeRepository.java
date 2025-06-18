package com.neoCamp.partidaFutebol.Repository;

import com.neoCamp.partidaFutebol.Entity.Clube;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubeRepository extends JpaRepository<Clube, Long> {

    Page<Clube> findByNomeContainingIgnoreCaseAndUfContainingIgnoreCaseAndAtivo(String nome, String uf, Boolean ativo, Pageable pageable);

    Page<Clube> findByNomeContainingIgnoreCaseAndUfContainingIgnoreCase(String nome, String uf, Pageable pageable);


}