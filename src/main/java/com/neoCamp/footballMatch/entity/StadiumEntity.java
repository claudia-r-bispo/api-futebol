package com.neoCamp.footballMatch.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor 
@AllArgsConstructor
public class StadiumEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false, length=2)
    private String uf;

    @Builder.Default 
    @Column(nullable = false)
    private boolean active = true;

    @Column(nullable=false)
    private LocalDate dateCreation;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private AddressEntity address;
}
