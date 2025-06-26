package com.neoCamp.partidaFutebol.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Uf {
    AC("Acre"),
    AL("Alagoas"),
    AM("Amazonas"),
    AP("Amapá"),
    BA("Bahia"),
    CE("Ceará"),
    DF("Distrito Federal"),
    ES("Espírito Santo"),
    GO("Goiás"),
    MA("Maranhão"),
    MG("Minas Gerais"),
    MS("Mato Grosso do Sul"),
    MT("Mato Grosso"),
    PA("Pará"),
    PB("Paraíba"),
    PE("Pernambuco"),
    PI("Piauí"),
    PR("Paraná"),
    RJ("Rio de Janeiro"),
    RN("Rio Grande do Norte"),
    RO("Rondônia"),
    RR("Roraima"),
    RS("Rio Grande do Sul"),
    SC("Santa Catarina"),
    SE("Sergipe"),
    SP("São Paulo"),
    TO("Tocantins");

    private final String nome;

    Uf(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    @JsonValue
    public String getSigla() {
        return this.name();
    }

    @JsonCreator
    public static Uf fromSigla(String sigla) {
        for (Uf uf : Uf.values()) {
            if (uf.name().equalsIgnoreCase(sigla)) {
                return uf;
            }
        }
        throw new IllegalArgumentException("Sigla de estado inválida: " + sigla);
    }
}
