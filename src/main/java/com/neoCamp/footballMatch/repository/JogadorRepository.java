package com.neoCamp.footballMatch.repository;

import com.neoCamp.footballMatch.entity.Jogador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JogadorRepository extends JpaRepository<Jogador, Long> {
    Optional<Jogador> findByIdJogador(String idJogador);
}

