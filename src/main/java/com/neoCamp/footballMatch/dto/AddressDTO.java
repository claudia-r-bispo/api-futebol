package com.neoCamp.footballMatch.dto;

import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor @AllArgsConstructor
public class AddressDTO {

    private UUID id;
    private String logradouro;
    private String cidade;
    private String estado;
    private String cep;
}