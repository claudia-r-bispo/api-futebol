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
import java.util.UUID;

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
    private UUID testId;
    private UUID homeClubId;
    private UUID clubVisitorId;
    private UUID stadiumId;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        testId = UUID.randomUUID();
        homeClubId = UUID.randomUUID();
        clubVisitorId = UUID.randomUUID();
        stadiumId = UUID.randomUUID();
        this.footballMatchDTO = new FootballMatchDTO(testId, homeClubId, clubVisitorId, stadiumId, java.time.LocalDateTime.now(), 1, 2);
    }

    @Test
    void testCreatePartida() {
        when(footballMatchService.create(any(FootballMatchDTO.class)))
                .thenReturn(footballMatchDTO);

        ResponseEntity<FootballMatchDTO> response = footballMatchController.createFootballMatch(footballMatchDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(footballMatchService).create(any(FootballMatchDTO.class));
    }

    @Test
    void testUpdateFootballMatch() {
        when(footballMatchService.update(eq(testId), any(FootballMatchDTO.class)))
            .thenReturn(footballMatchDTO);

        ResponseEntity<FootballMatchDTO> response = footballMatchController.updateFootballMatch(testId, footballMatchDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(footballMatchDTO, response.getBody());
        verify(footballMatchService).update(eq(testId), any(FootballMatchDTO.class));
    }

    @Test
    void testDeleteFootballMatch() {
        ResponseEntity<Void> response = footballMatchController.deleteFootballMatch(testId);

        assertEquals(204, response.getStatusCodeValue());
        verify(footballMatchService).delete(eq(testId));
    }

    @Test
    void testGetById() {
        when(footballMatchService.findById(eq(testId)))
            .thenReturn(footballMatchDTO);

        ResponseEntity<FootballMatchDTO> response = footballMatchController.getById(testId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(footballMatchDTO, response.getBody());
        verify(footballMatchService).findById(eq(testId));
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
