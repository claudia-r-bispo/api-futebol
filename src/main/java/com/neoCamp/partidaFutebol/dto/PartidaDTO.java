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
    private LocalDateTime dataPartida;

    private Integer golsMandante;
    private Integer golsVisitante;


}
