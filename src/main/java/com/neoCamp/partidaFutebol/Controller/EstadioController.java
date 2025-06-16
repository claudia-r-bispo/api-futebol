package com.neoCamp.partidaFutebol.Controller;

import com.neoCamp.partidaFutebol.Entity.Estadio;
import com.neoCamp.partidaFutebol.Repository.EstadioRepository;
import com.neoCamp.partidaFutebol.dto.EstadioDTO;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
class EstadioService {

    private final EstadioRepository estadioRepository;


    public EstadioService(EstadioRepository estadioRepository) {
        this.estadioRepository = estadioRepository;
    }

    public Object createEstadio(EstadioDTO dto) {
        Estadio estadio = new Estadio();
        estadio.setNome(dto.getNome());
        // estadio.setUf(dto.getUf());
        // estadio.setDtCriacao(dto.getDtCriacao());
        // estadio.setAtivo(true);
        return estadioRepository.save(estadio);
    }

    public Object updateEstadio(Long id, EstadioDTO dto) throws Throwable {
        Estadio estadio = (Estadio) estadioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estádio não encontrado com o ID: " + id));
        estadio.setNome(dto.getNome());
        // estadio.setUf(dto.getUf());
        // estadio.setDtCriacao(dto.getDtCriacao());
        // estadio.setAtivo(dto.isAtivo());
        return estadioRepository.save(estadio);
    }

    public Object findById(Long id) throws Throwable {
        return estadioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estádio não encontrado!"));
    }

    public Page<Estadio> listar(SpringDataWebProperties.Pageable pageable) {
        return estadioRepository.findAll((Pageable) pageable);
    }
}

