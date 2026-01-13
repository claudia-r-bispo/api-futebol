package com.neoCamp.footballMatch.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    private String street;
    private String number;
    private String city;
    private String state;
    private String zipCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stadium_id")
    private StadiumEntity stadium;

    public String getCep() {
        return zipCode;
    }
}
