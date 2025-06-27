package com.neoCamp.partidaFutebol.controller;

import com.neoCamp.partidaFutebol.dto.EstadioDTO;
import com.neoCamp.partidaFutebol.entity.EstadioEntity;
import com.neoCamp.partidaFutebol.service.EstadioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class EstadioControllerTest {

    @Mock
    private EstadioService estadioService;

    @InjectMocks
    private EstadioController estadioController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateEstadio() {

        EstadioDTO estadioDTO = new EstadioDTO();
        estadioDTO.setNome("Maracanã");
        estadioDTO.setUf("RJ");
        estadioDTO.setDtCriacao(LocalDate.of(1950, 6, 16));
        estadioDTO.setAtivo(true);


        when(estadioService.createEstadio(any(EstadioDTO.class))).thenReturn(estadioDTO);


        ResponseEntity<EstadioDTO> response = estadioController.createEstadio(estadioDTO);


        assertThat(response.getStatusCodeValue()).isEqualTo(201); // Supondo que seu controller retorna CREATED
        EstadioDTO responseBody = response.getBody();
        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getNome()).isEqualTo("Maracanã"); // Corrija o typo
        assertThat(responseBody.getUf()).isEqualTo("RJ");
        assertThat(responseBody.getDtCriacao()).isEqualTo(LocalDate.of(1950, 6, 16));
        assertThat(responseBody.getAtivo()).isTrue();

        verify(estadioService, times(1)).createEstadio(any(EstadioDTO.class));
    }
}