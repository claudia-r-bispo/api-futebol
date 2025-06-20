package com.neoCamp.partidaFutebol.service;


import com.neoCamp.partidaFutebol.dto.PartidaDTO;
import com.neoCamp.partidaFutebol.entity.ClubeEntity;
import com.neoCamp.partidaFutebol.entity.EstadioEntity;
import com.neoCamp.partidaFutebol.entity.PartidaEntity;
import com.neoCamp.partidaFutebol.repository.PartidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PartidaService {


    private PartidaRepository partidaRepository;

    @Autowired
    private ClubeService clubeService;

    @Autowired
    private EstadioService estadioService;

    public PartidaDTO createPartida(PartidaDTO dto) {
        ClubeEntity mandante = clubeService.findEntityById(dto.getClubeMandanteId());
        ClubeEntity visitante = clubeService.findEntityById(dto.getClubeVisitanteId());
        EstadioEntity estadio = estadioService.findEntityById(dto.getEstadioId());
        PartidaEntity entity = PartidaMapper.toEntity(dto, mandante, visitante, estadio);
        PartidaEntity saved = partidaRepository.save(entity);
        return PartidaMapper.toDto(saved);
    }

    public PartidaDTO updatePartida(Long id, PartidaDTO dto) {
        PartidaEntity entity = partidaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partida não encontrada!"));
        ClubeEntity mandante = clubeService.findEntityById(dto.getClubeMandanteId());
        ClubeEntity visitante = clubeService.findEntityById(dto.getClubeVisitanteId());
        EstadioEntity estadio = estadioService.findEntityById(dto.getEstadioId());
        entity.setClubeMandante(mandante);
        entity.setClubeVisitante(visitante);
        entity.setEstadio(estadio);
        entity.setDataHora(dto.getDataHora());
        entity.setGolsMandante(dto.getGolsMandante());
        entity.setGolsVisitante(dto.getGolsVisitante());
        PartidaEntity saved = partidaRepository.save(entity);
        return PartidaMapper.toDto(saved);
    }

    public void removerPartida(Long id) {
        partidaRepository.deleteById(id);
    }

    public PartidaDTO findById(Long id) {
        PartidaEntity entity = partidaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partida não encontrada!"));
        return PartidaMapper.toDto(entity);
    }

    public Page<PartidaDTO> listar(Pageable pageable) {
        return partidaRepository.findAll(pageable).map(PartidaMapper::toDto);
    }

    public PartidaEntity findEntityById(Long id) {
        return partidaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partida não encontrada!"));
    }
}