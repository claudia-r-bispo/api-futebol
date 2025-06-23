package com.neoCamp.partidaFutebol.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class PartidaDTO {

    private Long id;
    private Long clubeMandanteId;
    private Long clubeVisitanteId;
    private Long estadioId;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime dataHoraPartida;

    private Integer golsMandante;
    private Integer golsVisitante;

public PartidaDTO(Long id, Long clubeMandanteId, Long clubeVisitanteId, Long estadioId, LocalDateTime dataPartida, Integer golsMandante, Integer golsVisitante) {
        this.id = id;
        this.clubeMandanteId = clubeMandanteId;
        this.clubeVisitanteId = clubeVisitanteId;
        this.estadioId = estadioId;
        this.dataHoraPartida = dataHoraPartida;
        this.golsMandante = golsMandante;
        this.golsVisitante = golsVisitante;
    }

}
