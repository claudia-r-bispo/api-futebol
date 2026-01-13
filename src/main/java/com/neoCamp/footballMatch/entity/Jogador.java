package com.neoCamp.footballMatch.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "jogadores")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Jogador {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "id_jogador", unique = true, nullable = false)
    private String idJogador;

    @Column(name = "nome_jogador", nullable = false)
    private String nomeJogador;
}
