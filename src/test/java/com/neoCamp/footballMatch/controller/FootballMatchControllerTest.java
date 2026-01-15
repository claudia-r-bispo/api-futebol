package com.neoCamp.footballMatch.controller;

import com.neoCamp.footballMatch.dto.FootballMatchDTO;
import com.neoCamp.footballMatch.service.FootballMatchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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

public class FootballMatchControllerTest {

    @Mock
    private FootballMatchService footballMatchService;

    @InjectMocks
    private FootballMatchController footballMatchController;

    private FootballMatchDTO footballMatchDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.footballMatchDTO = new FootballMatchDTO(1L, 1L, 2L, 2L, java.time.LocalDateTime.now(), 1, 2);
    }

    @Test
    void testCreatePartida() {
        when(footballMatchService.createPartida(any(FootballMatchDTO.class)))
            .thenReturn(footballMatchDTO);

        ResponseEntity<FootballMatchDTO> response = footballMatchController.createFootballMatch(footballMatchDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(footballMatchService).createPartida(any(FootballMatchDTO.class));
    }

    @Test
    void testUpdateFootballMatch() {
        Long id = 1L;
        when(footballMatchService.updatePartida(eq(id), any(FootballMatchDTO.class)))
            .thenReturn(footballMatchDTO);

        ResponseEntity<FootballMatchDTO> response = footballMatchController.updateFootballMatch(id, footballMatchDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(footballMatchDTO, response.getBody());
        verify(footballMatchService).updatePartida(any(Long.class), any(FootballMatchDTO.class));
    }

    @Test
    void testDeleteFootballMatch() {
        Long id = 1L;
        ResponseEntity<Void> response = footballMatchController.deleteFootballMatch(id);

        assertEquals(204, response.getStatusCodeValue());
        verify(footballMatchService).removerPartida(eq(id));
    }

    @Test
    void testGetById() {
        Long id = 1L;
        when(footballMatchService.findById(eq(id)))
            .thenReturn(footballMatchDTO);

        ResponseEntity<FootballMatchDTO> response = footballMatchController.getById(id);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(footballMatchDTO, response.getBody());
        verify(footballMatchService).findById(eq(id));
    }
    @Test
    void testList() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<FootballMatchDTO> page = new PageImpl<>(Collections.singletonList(footballMatchDTO), pageable, 1);

        when(footballMatchService.listar(pageable)).thenReturn(page);


        ResponseEntity<Page<FootballMatchDTO>> response = footballMatchController.list(pageable);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getTotalElements());
        verify(footballMatchService).listar(pageable);
    }


}
