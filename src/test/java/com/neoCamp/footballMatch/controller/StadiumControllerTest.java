package com.neoCamp.footballMatch.controller;

import com.neoCamp.footballMatch.dto.StadiumDTO;
import com.neoCamp.footballMatch.service.StadiumService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class StadiumControllerTest {

    @Mock
    private StadiumService stadiumService;

    @InjectMocks
    private StadiumController stadiumController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateStadium() {

        StadiumDTO stadiumDTO = new StadiumDTO();
        stadiumDTO.setName("Maracanã");
        stadiumDTO.setUf("RJ");
        stadiumDTO.setDateCreation(LocalDate.of(1950, 6, 16));
        stadiumDTO.setActive(true);


        when(stadiumService.createEstadio(any(StadiumDTO.class))).thenReturn(stadiumDTO);


        ResponseEntity<StadiumDTO> response = stadiumController.createStadium(stadiumDTO);


        assertThat(response.getStatusCodeValue()).isEqualTo(201);
        StadiumDTO responseBody = response.getBody();
        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getName()).isEqualTo("Maracanã");
        assertThat(responseBody.getUf()).isEqualTo("RJ");
        assertThat(responseBody.getDateCreation()).isEqualTo(LocalDate.of(1950, 6, 16));
        assertThat(responseBody.getActive()).isTrue();

        verify(stadiumService, times(1)).createEstadio(any(StadiumDTO.class));
    }
}