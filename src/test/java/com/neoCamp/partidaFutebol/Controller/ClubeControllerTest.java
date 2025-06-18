package com.neoCamp.partidaFutebol.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neoCamp.partidaFutebol.Entity.Clube;
import com.neoCamp.partidaFutebol.Service.ClubeService;
import com.neoCamp.partidaFutebol.dto.ClubeDTO;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;


import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ClubeController.class)
public class ClubeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClubeService clubeService;

    @Autowired
    private ObjectMapper objectMapper;

    private Clube getClubeStub() {
        return new Clube(1L, "Corinthians", "SP", LocalDate.of(1910, 9, 1), true);
    }

    private ClubeDTO getClubeDTOStub() {
        return new ClubeDTO(1L, "Corinthians", "SP", LocalDate.of(1910, 9, 1), true);
    }

    @Test
    void testCreateClube() throws Exception {
        Clube clube = getClubeStub();
        ClubeDTO dto = new ClubeDTO(null, "Corinthians", "SP", LocalDate.of(1910, 9, 1), true);

        Mockito.when(clubeService.createClube(any(ClubeDTO.class))).thenReturn(clube);

        mockMvc.perform(post("/api/clubes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(clube.getId().intValue())))
                .andExpect(jsonPath("$.nome", is(clube.getNome())));
    }

    @Test
    void testUpdateClube() throws Exception {
        Clube clube = getClubeStub();
        ClubeDTO dto = getClubeDTOStub();

        Mockito.when(clubeService.updateClube(eq(1L), any(ClubeDTO.class))).thenReturn(clube);

        mockMvc.perform(put("/api/clubes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(clube.getId().intValue())))
                .andExpect(jsonPath("$.nome", is(clube.getNome())));
    }

    @Test
    void testDeleteClube() throws Exception {
        Mockito.doNothing().when(clubeService).inativar(1L);

        mockMvc.perform(delete("/api/clubes/1"))
                .andExpect(status().isNoContent());
    }
    @Test
    void testGetClubeById() throws Exception {
        Clube clube = getClubeStub();

        Mockito.when(clubeService.findById(1L)).thenReturn(clube);

        mockMvc.perform(get("/api/clubes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(clube.getId().intValue())))
                .andExpect(jsonPath("$.nome", is(clube.getNome())));
    }

    @Test
    void testListarClube() throws Exception {
        Clube clube = getClubeStub();
        Page<Clube> page = new PageImpl<>(Collections.singletonList(clube), PageRequest.of(0, 10), 1);

        Mockito.when(clubeService.listarComFiltros(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(page);

        mockMvc.perform(get("/api/clubes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id", is(clube.getId().intValue())))
                .andExpect(jsonPath("$.content[0].nome", is(clube.getNome())));
    }
}
