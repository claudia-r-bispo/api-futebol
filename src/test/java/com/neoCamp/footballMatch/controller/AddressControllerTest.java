package com.neoCamp.footballMatch.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neoCamp.footballMatch.entity.AddressEntity;
import com.neoCamp.footballMatch.service.AddressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AddressController.class)
class AddressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddressService addressService;

    @Autowired
    private ObjectMapper objectMapper;

    private AddressEntity address;

    @BeforeEach
    void setUp() {
        address = new AddressEntity();
        address.setId(1L);
        address.setStreet("Rua Teste");
        address.setNumber("123");
        address.setCity("Cidade");
        address.setState("Estado");
        address.setZipCode("00000-000");
    }

    @Test
    void testCreate() throws Exception {
        when(addressService.save(any(AddressEntity.class))).thenReturn(address);
        mockMvc.perform(post("/api/addresses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(address)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.street").value("Rua Teste"));
    }

    @Test
    void testGetByIdFound() throws Exception {
        when(addressService.findById(1L)).thenReturn(Optional.of(address));
        mockMvc.perform(get("/api/addresses/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testGetByIdNotFound() throws Exception {
        when(addressService.findById(2L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/addresses/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetAll() throws Exception {
        when(addressService.findAll()).thenReturn(Arrays.asList(address));
        mockMvc.perform(get("/api/addresses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    void testDelete() throws Exception {
        doNothing().when(addressService).deleteById(1L);
        mockMvc.perform(delete("/api/addresses/1"))
                .andExpect(status().isNoContent());
    }
}
