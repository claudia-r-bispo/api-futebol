package com.neoCamp.partidaFutebol.service;

import com.neoCamp.partidaFutebol.entity.EstadioEntity;
import com.neoCamp.partidaFutebol.mapper.EstadioMapper;
import com.neoCamp.partidaFutebol.repository.EstadioRepository;
import com.neoCamp.partidaFutebol.dto.EstadioDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EstadioService {

    private final EstadioRepository estadioRepository;

    public EstadioService(EstadioRepository estadioRepository) {
        this.estadioRepository = estadioRepository;
    }

    public EstadioDTO createEstadio(EstadioDTO dto) {
        EstadioEntity entity = EstadioMapper.toEntity(dto);
        entity.setAtivo(true);
        EstadioEntity saved = estadioRepository.save(entity);
        return EstadioMapper.toDto(saved);
    }

    public EstadioDTO updateEstadio(Long id, EstadioDTO dto) {
        EstadioEntity entity = estadioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estádio não encontrado com o ID: " + id));
        entity.setNome(dto.getNome());
        entity.setUf(dto.getUf());
        entity.setDtCriacao(dto.getDtCriacao());
        entity.setAtivo(dto.getAtivo() != null ? dto.getAtivo() : entity.isAtivo());
        EstadioEntity saved = estadioRepository.save(entity);
        return EstadioMapper.toDto(saved);
    }

    public EstadioDTO findById(Long id) {
        EstadioEntity entity = estadioRepository.findById(id)
        return estadioRepository.findById(id).orElseThrow(() -> new RuntimeException("Estádio não encontrado!"));
        return EstadioMapper.toDto(entity);
    }

    public Page<EstadioDTO> listar(Pageable pageable) {
        return estadioRepository.findAll(pageable).map(EstadioMapper::toDto);
    }
}







