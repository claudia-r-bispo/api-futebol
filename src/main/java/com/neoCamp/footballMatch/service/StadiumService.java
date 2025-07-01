package com.neoCamp.footballMatch.service;

import com.neoCamp.footballMatch.entity.StadiumEntity;
import com.neoCamp.footballMatch.mapper.StadiumMapper;
import com.neoCamp.footballMatch.repository.StadiumRepository;
import com.neoCamp.footballMatch.dto.StadiumDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class StadiumService {

    private final StadiumRepository stadiumRepository;

    public StadiumService(StadiumRepository stadiumRepository) {
        this.stadiumRepository = stadiumRepository;
    }

    public StadiumDTO createEstadio(StadiumDTO dto) {
        StadiumEntity entity = StadiumMapper.toEntity(dto);
        entity.setActive(true);
        StadiumEntity saved = stadiumRepository.save(entity);
        return StadiumMapper.toDto(saved);
    }

    public StadiumDTO updateEstadio(Long id, StadiumDTO dto) {
        StadiumEntity entity = stadiumRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estádio não encontrado com o ID: " + id));
        entity.setName(dto.getName());
        entity.setUf(dto.getUf());
        entity.setDateCreation(dto.getDateCreation());
        entity.setActive(dto.getActive() != null ? dto.getActive() : entity.isActive());
        StadiumEntity saved = stadiumRepository.save(entity);
        return StadiumMapper.toDto(saved);
    }

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







