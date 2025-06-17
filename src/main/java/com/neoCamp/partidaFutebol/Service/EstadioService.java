package com.neoCamp.partidaFutebol.Service;

import com.neoCamp.partidaFutebol.Entity.Estadio;
import com.neoCamp.partidaFutebol.Repository.EstadioRepository;
import com.neoCamp.partidaFutebol.dto.EstadioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EstadioService {

    private final EstadioRepository estadioRepository;

    //@Autowired
    public EstadioService(EstadioRepository estadioRepository) {
        this.estadioRepository = estadioRepository;
    }

    public Estadio createEstadio(EstadioDTO dto) {
        Estadio estadio = new Estadio();
        estadio.setNome(dto.getNome());
        // estadio.setUf(dto.getUf());
        // estadio.setDtCriacao(dto.getDtCriacao());
        // estadio.setAtivo(true);
        return estadioRepository.save(estadio);
    }

    public Estadio updateEstadio(Long id, EstadioDTO dto) {
        Estadio estadio = estadioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estádio não encontrado com o ID: " + id));
        estadio.setNome(dto.getNome());
        return estadioRepository.save(estadio);
    }

    public Estadio findById(Long id) {
        return estadioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estádio não encontrado!"));
    }

    public Page<Estadio> listar(Pageable pageable) {
        return estadioRepository.findAll(pageable);
    }
}







