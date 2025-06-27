package com.neoCamp.partidaFutebol.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neoCamp.partidaFutebol.dto.ClubeDTO;
import com.neoCamp.partidaFutebol.entity.ClubeEntity;
import com.neoCamp.partidaFutebol.service.ClubeService;
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

public class ClubeControllerTest {

    private MockMvc mockMvc;
    private ClubeService clubeServiceMock;
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());


    private ClubeController controller;

    private ClubeEntity getClubeStub() {
        return new ClubeEntity(1L, "Corinthians", "SP", LocalDate.of(1910, 9, 1), true);
    }

    private ClubeDTO getClubeDTOStub() {
        return new ClubeDTO(1L, "Corinthians", "SP", LocalDate.of(1910, 9, 1), true);
    }

    @BeforeEach
    void setup() {
        clubeServiceMock = Mockito.mock(ClubeService.class);
        controller = new ClubeController(clubeServiceMock);

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    void testCreateClube() throws Exception {
        ClubeDTO clubeDTO = getClubeDTOStub();
        ClubeDTO dto = new ClubeDTO(null, "Corinthians", "SP", LocalDate.of(1910, 9, 1), true);

        Mockito.when(clubeServiceMock.createClube(any(ClubeDTO.class))).thenReturn(clubeDTO);

        mockMvc.perform(post("/api/clubes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(clubeDTO.getId().intValue())))
                .andExpect(jsonPath("$.nome", is(clubeDTO.getNome())));
    }

    @Test
    void testUpdateClube() throws Exception {
        ClubeDTO clubeDTO = getClubeDTOStub();
        ClubeDTO dto = getClubeDTOStub();

        Mockito.when(clubeServiceMock.updateClube(eq(1L), any(ClubeDTO.class))).thenReturn(clubeDTO);
        mockMvc.perform(put("/api/clubes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(clubeDTO.getId().intValue())))
                .andExpect(jsonPath("$.nome", is(clubeDTO.getNome())));
    }

    @Test
    void testDeleteClube() throws Exception {
        Mockito.doNothing().when(clubeServiceMock).inativar(1L);

        mockMvc.perform(delete("/api/clubes/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetClubeById() throws Exception {
        ClubeDTO clubeDTO = getClubeDTOStub();
        Mockito.when(clubeServiceMock.findById(1L)).thenReturn(clubeDTO);

        mockMvc.perform(get("/api/clubes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(clubeDTO.getId().intValue())))
                .andExpect(jsonPath("$.nome", is(clubeDTO.getNome())));
    }

    @Test
    void testListarClube() throws Exception {
        ClubeDTO clubeDTO = getClubeDTOStub();
        Page<ClubeDTO> page = new PageImpl<>(Collections.singletonList(clubeDTO), PageRequest.of(0, 10), 1);

        Mockito.when(clubeServiceMock.listarComFiltros(any(), any(), any(), any()))
                .thenReturn(page);

        mockMvc.perform(get("/api/clubes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id", is(clubeDTO.getId().intValue())))
                .andExpect(jsonPath("$.content[0].nome", is(clubeDTO.getNome())));
    }
}





//package com.neoCamp.partidaFutebol.controller;
//
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.neoCamp.partidaFutebol.entity.ClubeEntity;
//import com.neoCamp.partidaFutebol.service.ClubeService;
//import com.neoCamp.partidaFutebol.dto.ClubeDTO;
//import com.neoCamp.partidaFutebol.dto.EstadioDTO;
//import com.neoCamp.partidaFutebol.entity.EstadioEntity;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.http.MediaType;
//
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.time.LocalDate;
//import java.util.Collections;
//import static org.hamcrest.Matchers.is;
//import static org.mockito.ArgumentMatchers.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//
//@WebMvcTest(ClubeController.class)
//public class ClubeControllerTest {
//
//    @MockBean
//    private ClubeService clubeService;
//
//    private MockMvc mockMvc;
//
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    private ClubeEntity getClubeStub() {
//        return new ClubeEntity(1L, "Corinthians", "SP", LocalDate.of(1910, 9, 1), true);
//    }
//
//    private ClubeDTO getClubeDTOStub() {
//        return new ClubeDTO(1L, "Corinthians", "SP", LocalDate.of(1910, 9, 1), true);
//    }
//
//    @Test
//    void testCreateClube() throws Exception {
//        ClubeDTO clubeDTO = getClubeDTOStub();
//        ClubeDTO dto = new ClubeDTO(null, "Corinthians", "SP", LocalDate.of(1910, 9, 1), true);
//
//        Mockito.when(clubeService.createClube(any(ClubeDTO.class))).thenReturn(clubeDTO);
//
//        mockMvc.perform(post("/api/clubes")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(dto)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.id", is(clubeDTO.getId().intValue())))
//                .andExpect(jsonPath("$.nome", is(clubeDTO.getNome())));
//    }
//
//    @Test
//    void testUpdateClube() throws Exception {
//        ClubeDTO clubeDTO = getClubeDTOStub();
//        ClubeDTO dto = getClubeDTOStub();
//
//        Mockito.when(clubeService.createClube(any(ClubeDTO.class))).thenReturn(clubeDTO);
//
//        mockMvc.perform(put("/api/clubes/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(dto)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id", is(clubeDTO.getId().intValue())))
//                .andExpect(jsonPath("$.nome", is(clubeDTO.getNome())));
//    }
//
//    @Test
//    void testDeleteClube() throws Exception {
//        Mockito.doNothing().when(clubeService).inativar(1L);
//
//        mockMvc.perform(delete("/api/clubes/1"))
//                .andExpect(status().isNoContent());
//    }
//
//
//    @Test
//    void testGetClubeById() throws Exception {
//        ClubeDTO clubeDTO = getClubeDTOStub();
//        Mockito.when(clubeService.findById(1L)).thenReturn(clubeDTO);
//
//        mockMvc.perform(get("/api/clubes/1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id", is(clubeDTO.getId().intValue())))
//                .andExpect(jsonPath("$.nome", is(clubeDTO.getNome())));
//    }
//
//    @Test
//    void testListarClube() throws Exception {
//        ClubeDTO clubeDTO = getClubeDTOStub();
//        Page<ClubeDTO> page = new PageImpl<>(Collections.singletonList(clubeDTO), PageRequest.of(0, 10), 1);
//
//        Mockito.when(clubeService.listarComFiltros(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
//                .thenReturn(page);
//
//        mockMvc.perform(get("/api/clubes"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.content[0].id", is(clubeDTO.getId().intValue())))
//                .andExpect(jsonPath("$.content[0].nome", is(clubeDTO.getNome())));
//    }
//}
