package com.neoCamp.footballMatch.dto;

import lombok.*;

@Data
@NoArgsConstructor @AllArgsConstructor
public class AddressDTO {

    private Long id;
    private String logradouro;
    private String cidade;
    private String estado;
    private String cep;
}