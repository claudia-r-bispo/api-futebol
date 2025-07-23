package com.neoCamp.footballMatch.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neoCamp.footballMatch.dto.ClubDTO;
import com.neoCamp.footballMatch.entity.ClubEntity;
import com.neoCamp.footballMatch.service.ClubService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDate;
import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ClubControllerTest {

    private MockMvc mockMvc;
    private ClubService clubServiceMock;
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());


    private ClubController controller;

    private ClubEntity getClubeStub() {
        return new ClubEntity(1L, "Corinthians", "SP", LocalDate.of(1910, 9, 1), true);
    }

    private ClubDTO getClubDTOStub() {
        return new ClubDTO(1L, "Corinthians", "SP", LocalDate.of(1910, 9, 1), true);
    }

    @BeforeEach
    void setup() {
        clubServiceMock = Mockito.mock(ClubService.class);
        controller = new ClubController(clubServiceMock);

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    void testCreateClube() throws Exception {
        ClubDTO clubDTO = getClubDTOStub();
        ClubDTO dto = new ClubDTO(null, "Corinthians", "SP", LocalDate.of(1910, 9, 1), true);

        Mockito.when(clubServiceMock.createClube(any(ClubDTO.class))).thenReturn(clubDTO);

        mockMvc.perform(post("/api/clubes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(clubDTO.getId().intValue())))
                .andExpect(jsonPath("$.name", is(clubDTO.getName())));
    }

    @Test
    void testUpdateClub() throws Exception {
        ClubDTO clubDTO = getClubDTOStub();
        ClubDTO dto = getClubDTOStub();

        Mockito.when(clubServiceMock.updateClube(eq(1L), any(ClubDTO.class))).thenReturn(clubDTO);
        mockMvc.perform(put("/api/clubes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(clubDTO.getId().intValue())))
                .andExpect(jsonPath("$.name", is(clubDTO.getName())));
    }

    @Test
    void testDeleteClub() throws Exception {
        Mockito.doNothing().when(clubServiceMock).inativar(1L);

        mockMvc.perform(delete("/api/clubes/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetClubById() throws Exception {
        ClubDTO clubDTO = getClubDTOStub();
        Mockito.when(clubServiceMock.findById(1L)).thenReturn(clubDTO);

        mockMvc.perform(get("/api/clubes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(clubDTO.getId().intValue())))
                .andExpect(jsonPath("$.name", is(clubDTO.getName())));
    }

    @Test
    void testListClube() throws Exception {
        ClubDTO clubDTO = getClubDTOStub();
        Page<ClubDTO> page = new PageImpl<>(Collections.singletonList(clubDTO), PageRequest.of(0, 10), 1);

        Mockito.when(clubServiceMock.listClubsWithFilters(any(), any(), any(), any()))
                .thenReturn(page);

        mockMvc.perform(get("/api/clubes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id", is(clubDTO.getId().intValue())))
                .andExpect(jsonPath("$.content[0].name", is(clubDTO.getName())));
    }
}






