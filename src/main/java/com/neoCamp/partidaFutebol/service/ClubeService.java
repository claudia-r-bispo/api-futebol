package com.neoCamp.partidaFutebol.service;

import com.neoCamp.partidaFutebol.entity.ClubeEntity;
import com.neoCamp.partidaFutebol.mapper.ClubeMapper;
import com.neoCamp.partidaFutebol.repository.ClubeRepository;
import com.neoCamp.partidaFutebol.dto.ClubeDTO;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ClubeService {


    private ClubeRepository clubeRepository;

    public ClubeDTO createClube(ClubeDTO dto) {
        ClubeEntity clube = ClubeMapper.toEntity(dto);
        clube.setAtivo(true);
        ClubeEntity savedClube = clubeRepository.save(clube);
        return ClubeMapper.toDto(savedClube);

    }

    public ClubeEntity updateClube(Long id, ClubeDTO dto) {
        ClubeEntity clube = clubeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Clube não encontrado com o ID: " + id));
        clube.setNome(dto.getNome());
        clube.setUf(dto.getUf());
        clube.setDtCriacao(dto.getDtCriacao());
        clube.setAtivo(dto.isAtivo());
        return clubeRepository.save(clube);
    }

    public void inativar(Long idClube) {
        ClubeEntity clube = clubeRepository.findById(idClube)
                .orElseThrow(() -> new EntityNotFoundException("Clube não encontrado"));
        clube.setAtivo(false);
        clubeRepository.save(clube);
    }

    public ClubeEntity findEntityById(Long id) {
        return clubeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Clube não encontrado"));
    }

    public Page<ClubeDTO> listarComFiltros(String nome, String uf, Boolean ativo, Pageable pageable) {
        if (ativo != null) {
            Page<ClubeEntity> page;
            if (ativo != null) {
                page = clubeRepository.findByNomeContainingIgnoreCaseAndUfContainingIgnoreCaseAndAtivo(nome == null ? "" : nome, uf == null ? "" : uf, ativo, pageable);
            } else {
                page = clubeRepository.findByNomeContainingIgnoreCaseAndUfContainingIgnoreCase(nome == null ? "" : nome, uf == null ? "" : uf, pageable);
            }
            return page.map(ClubeMapper::toDto);
        }



}