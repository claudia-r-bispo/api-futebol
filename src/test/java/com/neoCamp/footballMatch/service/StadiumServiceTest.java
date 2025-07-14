package com.neoCamp.footballMatch.service;

import com.neoCamp.footballMatch.dto.StadiumDTO;
import com.neoCamp.footballMatch.entity.AddressEntity;
import com.neoCamp.footballMatch.entity.StadiumEntity;
import com.neoCamp.footballMatch.mapper.StadiumMapper;
import com.neoCamp.footballMatch.repository.StadiumRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StadiumServiceTest {

    @Mock
    private StadiumRepository stadiumRepository;

    @Mock
    private AddressService addressService;

    @InjectMocks
    private StadiumService stadiumService;

    private StadiumDTO stadiumDTO;
    private StadiumEntity stadiumEntity;
    private AddressEntity addressEntity;

    @BeforeEach
    void setUp() {
        addressEntity = new AddressEntity();
        addressEntity.setId(1L);
        addressEntity.setLogradouro("Avenida Paulista");
        addressEntity.setCidade("São Paulo");
        addressEntity.setEstado("SP");
        addressEntity.setCep("01310-100");

        stadiumEntity = new StadiumEntity();
        stadiumEntity.setId(1L);
        stadiumEntity.setName("Arena Test");
        stadiumEntity.setUf("SP");
        stadiumEntity.setDateCreation(LocalDate.now());
        stadiumEntity.setActive(true);
        stadiumEntity.setAddress(addressEntity);

        stadiumDTO = new StadiumDTO();
        stadiumDTO.setId(1L);
        stadiumDTO.setName("Arena Test");
        stadiumDTO.setUf("SP");
        stadiumDTO.setDateCreation(LocalDate.now());
        stadiumDTO.setCep("01310-100");
        stadiumDTO.setActive(true);
    }

    @Test
    void testCreateEstadio() {
        // Arrange
        when(addressService.createAddressPorCep("01310-100")).thenReturn(addressEntity);
        when(stadiumRepository.save(any(StadiumEntity.class))).thenReturn(stadiumEntity);

        // Act
        StadiumDTO result = stadiumService.createEstadio(stadiumDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Arena Test", result.getName());
        assertEquals("SP", result.getUf());
        assertTrue(result.getActive());

        verify(addressService, times(1)).createAddressPorCep("01310-100");
        verify(stadiumRepository, times(1)).save(any(StadiumEntity.class));
    }

    @Test
    void testCreateEstadio_SemCep_DeveLancarExcecao() {
        // Arrange
        stadiumDTO.setCep(null); // ← Sem CEP

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> stadiumService.createEstadio(stadiumDTO)
        );

        assertEquals("CEP é obrigatório para cadastro do estádio", exception.getMessage());

        // Verificar que os métodos não foram chamados
        verify(addressService, never()).createAddressPorCep(any());
        verify(stadiumRepository, never()).save(any());
    }

    @Test
    void testCreateEstadio_CepVazio_DeveLancarExcecao() {
        // Arrange
        stadiumDTO.setCep(""); // ← CEP vazio

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> stadiumService.createEstadio(stadiumDTO)
        );

        assertEquals("CEP é obrigatório para cadastro do estádio", exception.getMessage());
    }

    @Test
    void testUpdateEstadio() {
        // Arrange
        Long stadiumId = 1L;
        when(stadiumRepository.findById(stadiumId)).thenReturn(java.util.Optional.of(stadiumEntity));
        when(addressService.createAddressPorCep("01310-100")).thenReturn(addressEntity);
        when(stadiumRepository.save(any(StadiumEntity.class))).thenReturn(stadiumEntity);

        // Act
        StadiumDTO result = stadiumService.updateEstadio(stadiumId, stadiumDTO);

        // Assert
        assertNotNull(result);
        verify(stadiumRepository).findById(stadiumId);
        verify(addressService).createAddressPorCep("01310-100");
        verify(stadiumRepository).save(any(StadiumEntity.class));
    }

    @Test
    void testFindById() {
        // Arrange
        Long stadiumId = 1L;
        when(stadiumRepository.findById(stadiumId)).thenReturn(java.util.Optional.of(stadiumEntity));

        // Act
        StadiumDTO result = stadiumService.findById(stadiumId);

        // Assert
        assertNotNull(result);
        assertEquals("Arena Test", result.getName());
        verify(stadiumRepository).findById(stadiumId);
    }

    @Test
    void testFindById_NaoEncontrado() {
        // Arrange
        Long stadiumId = 999L;
        when(stadiumRepository.findById(stadiumId)).thenReturn(java.util.Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> stadiumService.findById(stadiumId)
        );

        assertEquals("Estádio não encontrado!", exception.getMessage());
    }
}