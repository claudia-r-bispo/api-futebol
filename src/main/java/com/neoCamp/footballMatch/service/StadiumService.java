package com.neoCamp.footballMatch.service;

import com.neoCamp.footballMatch.entity.AddressEntity;
import com.neoCamp.footballMatch.entity.StadiumEntity;
import com.neoCamp.footballMatch.entity.AddressEntity;
import com.neoCamp.footballMatch.mapper.StadiumMapper;
import com.neoCamp.footballMatch.repository.StadiumRepository;
import com.neoCamp.footballMatch.dto.StadiumDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class StadiumService {

    private final StadiumRepository stadiumRepository;
    private final AddressService enderecoService; // ← Nome correto da classe

    public StadiumService(StadiumRepository stadiumRepository, AddressService enderecoService) {
        this.stadiumRepository = stadiumRepository;
        this.enderecoService = enderecoService;
    }

    public StadiumDTO createEstadio(StadiumDTO dto) {
        if (dto.getCep() == null || dto.getCep().trim().isEmpty()) {
            throw new IllegalArgumentException("CEP é obrigatório para cadastro do estádio");
        }

        try {
            // ← NOME CORRETO DO MÉTODO
            AddressEntity endereco = enderecoService.createAddressPorCep(dto.getCep());

            StadiumEntity entity = StadiumMapper.toEntity(dto);
            entity.setActive(true);
            entity.setAddress(endereco);

            StadiumEntity saved = stadiumRepository.save(entity);
            return StadiumMapper.toDto(saved);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar estádio: " + e.getMessage());
        }
    }

    public StadiumDTO updateEstadio(Long id, StadiumDTO dto) {
        StadiumEntity entity = stadiumRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estádio não encontrado com o ID: " + id));

        if (dto.getCep() != null && !dto.getCep().trim().isEmpty()) {
            // ← NOME CORRETO DO MÉTODO
            AddressEntity novoEndereco = enderecoService.createAddressPorCep(dto.getCep());
            entity.setAddress(novoEndereco);
        }

        entity.setName(dto.getName());
        entity.setUf(dto.getUf());
        entity.setDateCreation(dto.getDateCreation());
        entity.setActive(dto.getActive() != null ? dto.getActive() : entity.isActive());

        StadiumEntity saved = stadiumRepository.save(entity);
        return StadiumMapper.toDto(saved);
    }

    // ... resto dos métodos permanecem iguais
    public StadiumDTO findById(Long id) {
        return stadiumRepository.findById(id)
                .map(StadiumMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Estádio não encontrado!"));
    }

    public StadiumEntity findEntityById(Long id) {
        return stadiumRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estádio não encontrado!"));
    }

    public Page<StadiumDTO> listar(Pageable pageable) {
        return stadiumRepository.findAll(pageable).map(StadiumMapper::toDto);
    }
}