package com.neoCamp.footballMatch.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClubEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false, length=2)
    private String uf;

    @Column(nullable=false)
    private LocalDate dateCreation;

    @Column(nullable=false)
    private boolean active = true;
}
