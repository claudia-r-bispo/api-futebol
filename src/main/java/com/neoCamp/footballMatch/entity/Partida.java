package com.neoCamp.footballMatch.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "partidas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Partida {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "data_hora_inicio", nullable = false)
    private LocalDateTime dataHoraInicio;

    @Column(name = "data_hora_fim")
    private LocalDateTime dataHoraFim;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "vencedor")
    private String vencedor;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "partida_jogadores",
            joinColumns = @JoinColumn(name = "partida_id"),
            inverseJoinColumns = @JoinColumn(name = "jogador_id")
    )
    private List<Jogador> jogadores;

    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = UUID.randomUUID();
        }
    }
}
