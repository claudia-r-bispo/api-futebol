//package com.neoCamp.partidaFutebol.controller;
//
//import com.neoCamp.partidaFutebol.entity.EstadioEntity;
//import com.neoCamp.partidaFutebol.dto.EstadioDTO;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.ResponseEntity;
//import static org.mockito.Mockito.*;
//
//import java.time.LocalDate;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//public class EstadioControllerTest {
//
//    @Mock
//    private EstadioController estadioController;
//
//
//    @InjectMocks
//    private EstadioControllerTest estadioControllerTest;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    void testCreateEstadio() {
//        EstadioDTO estadioDTO = new EstadioDTO();
//        estadioDTO.setNome("Maracanã");
//        estadioDTO.setUf("RJ");
//        estadioDTO.setDtCriacao(LocalDate.of(1950, 6, 16));
//        estadioDTO.setAtivo(true);
//
//
//        when(estadioRepository.save(any(EstadioEntity.class))).thenReturn(new EstadioEntity);
//
//        ResponseEntity<EstadioEntity> response = estadioController.createEstadio(estadioDTO);
//
//        assertThat(response.getStatusCodeValue().isEqualTo(200));
//        EstadioEntity responseBody = response.getBody();
//        assertThat(response.getBody().getNome()).isEqualTo("Msaracanã");
//        assertThat(responseBody.getUf()).isEqualTo("RJ");
//        assertThat(responseBody.getDtCriacao()).isEqualTo(LocalDate.of(1950, 6, 16));
//        assertThat(responseBody.getAtivo()).isTrue();
//        // Verifica se o método save foi chamado uma vez com qualquer EstadioEntity
//
//        verify(estadioRepository, times (1)).save(any(EstadioEntity.class));
//    }
//
//
//
//}
