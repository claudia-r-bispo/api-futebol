package com.neoCamp.footballMatch.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PartidaStatus {
    AGENDADA("AGENDADA"),
    EM_ANDAMENTO("EM_ANDAMENTO"),
    PAUSADA("PAUSADA"),
    ENCERRADA("ENCERRADA"),
    CANCELADA("CANCELADA"),
    ADIADA("ADIADA");

    private final String valor;

    PartidaStatus(String valor) {
        this.valor = valor;
    }

    @JsonValue
    public String getValor() {
        return valor;
    }

    @JsonCreator
    public static PartidaStatus fromValor(String valor) {
        for (PartidaStatus status : PartidaStatus.values()) {
            if (status.valor.equalsIgnoreCase(valor)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Status de partida inválido: " + valor);
    }
}
