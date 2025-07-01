package com.neoCamp.footballMatch.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;
import jakarta.persistence.Id;
import java.time.LocalDate;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClubEntity {



        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable=false)
        private String name;

        @Column(nullable=false, length=2)
        private String uf;

        @Column(nullable=false)
        private LocalDate dateCreation;

        @Column(nullable=false)
        private boolean active;



    }


