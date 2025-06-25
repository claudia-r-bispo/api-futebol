package com.neoCamp.partidaFutebol.controller;

import com.neoCamp.partidaFutebol.dto.PartidaDTO;
import com.neoCamp.partidaFutebol.service.PartidaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PartidaControllerTest {

    @Mock
    private PartidaService partidaService;

    @InjectMocks
    private PartidaController partidaController;

    private PartidaDTO partidaDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.partidaDTO = new PartidaDTO(1L, 1L, 2L, 2L, java.time.LocalDateTime.now(), 1, 2);
    }

    @Test
    void testCreatePartida() {
        when(partidaService.createPartida(any(PartidaDTO.class)))
            .thenReturn(partidaDTO);

        ResponseEntity<PartidaDTO> response = partidaController.createPartida(partidaDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(partidaService).createPartida(any(PartidaDTO.class));
    }

    @Test
    void testUpdatePartida() {
        Long id = 1L;
        when(partidaService.updatePartida(eq(id), any(PartidaDTO.class)))
            .thenReturn(partidaDTO);

        ResponseEntity<PartidaDTO> response = partidaController.updatePartida(id, partidaDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(partidaDTO, response.getBody());
        verify(partidaService).updatePartida(any(Long.class), any(PartidaDTO.class));
    }

    @Test
    void testDeletePartida() {
        Long id = 1L;
        ResponseEntity<Void> response = partidaController.deletePartida(id);

        assertEquals(204, response.getStatusCodeValue());
        verify(partidaService).removerPartida(eq(id));
    }

    @Test
    void estGetById() {
        Long id = 1L;
        when(partidaService.findById(eq(id)))
            .thenReturn(partidaDTO);

        ResponseEntity<PartidaDTO> response = partidaController.getById(id);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(partidaDTO, response.getBody());
        verify(partidaService).findById(eq(id));
    }
    @Test
    void testListar() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<PartidaDTO> page = new PageImpl<>(Collections.singletonList(partidaDTO), pageable, 1);

        when(partidaService.listar(pageable)).thenReturn(page);


        ResponseEntity<Page<PartidaDTO>> response = partidaController.listar(pageable);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getTotalElements());
        verify(partidaService).listar(pageable);
    }


}
