package com.neoCamp.partidaFutebol.Service;

import com.neoCamp.partidaFutebol.Entity.Clube;
import com.neoCamp.partidaFutebol.Repository.ClubeRepository;
import com.neoCamp.partidaFutebol.dto.ClubeDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;

@Service
public class ClubeService {

    @Autowired
    private ClubeRepository clubeRepository;

    public Clube createClube(ClubeDTO dto) {
        Clube clube = new Clube();
        clube.setNome(dto.getNome());
        clube.setUf(dto.getUf());
        clube.setDtCriacao(dto.getDtCriacao());
        clube.setAtivo(dto.isAtivo());
        return clubeRepository.save(clube);
    }

    //inativar clube
    public void inativar (Long idClube) {
        Clube clube = clubeRepository.findById(idClube).orElseThrow(() -> new EntityNotFoundException("Clube não encontrado"));
        clube.setAtivo(false);
        clubeRepository.save(clube);
    }

    public Clube findById(Long idClube) {
        return clubeRepository.findById(idClube)
                .orElseThrow(() -> new EntityNotFoundException("Clube não encontrado com o ID: " + idClube));
    }

    public Page<Clube> listarComFiltros(String nome, String uf, Boolean ativo, Pageable pageable) {
        if (ativo != null) {
            return clubeRepository.findByNomeContainingIgnoreCaseAndUfContainingIgnoreCaseAndAtivo((
                    nome == null ? "": nome),
                    uf == null ? "":uf,
                    ativo,
                    pageable);
        } else {
            return clubeRepository.findByNomeContainingIgnoreCaseAndUfContainingIgnoreCase(
                    nome == null ? " ": nome,
                    uf  == null ? "": uf,

                    pageable);
        }
    }

    public Clube updateClube(Long id, ClubeDTO dto) {
        Clube clube = clubeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Clube não encontrado com o ID: " + id));
        clube.setNome(dto.getNome());
        clube.setUf(dto.getUf());
        clube.setDtCriacao(dto.getDtCriacao());
        clube.setAtivo(dto.isAtivo());
        return clubeRepository.save(clube);
    }
}