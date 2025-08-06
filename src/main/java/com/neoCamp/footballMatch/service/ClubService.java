package com.neoCamp.footballMatch.service;

import com.neoCamp.footballMatch.entity.ClubEntity;
import com.neoCamp.footballMatch.mapper.ClubMapper;
import com.neoCamp.footballMatch.repository.ClubRepository;
import com.neoCamp.footballMatch.dto.ClubDTO;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ClubService {

    @Autowired
    private ClubRepository clubRepository;

    public ClubDTO createClube(ClubDTO dto) {
        ClubEntity entity = ClubMapper.toEntity(dto);
        ClubEntity savedClube = clubRepository.save(entity);
        return ClubMapper.toDto(savedClube);
    }

    public ClubDTO updateClube(UUID id, ClubDTO dto) {
        ClubEntity clube = clubRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Clube não encontrado com o ID: " + id));
        clube.setName(dto.getName());
        clube.setUf(dto.getUf());
        clube.setDateCreation(dto.getDateCreation());
        clube.setActive(dto.getActive());
        ClubEntity saved = clubRepository.save(clube);
        return ClubMapper.toDto(saved);
    }

    public void inativar(UUID idClube) {
        ClubEntity clube = clubRepository.findById(idClube)
                .orElseThrow(() -> new EntityNotFoundException("Clube não encontrado"));
        clube.setActive(false);
        clubRepository.save(clube);
    }

    public ClubDTO findById(UUID id) {
        ClubEntity entity = clubRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Clube não encontrado"));
        return ClubMapper.toDto(entity);
    }

    public ClubEntity findEntityById(UUID id) {
        return clubRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Clube não encontrado"));
    }

    public Page<ClubDTO> listClubsWithFilters(String nome, String uf, Boolean ativo, Pageable pageable) {
        if (ativo != null) {
            return clubRepository
                    .findByNameContainingIgnoreCaseAndUfContainingIgnoreCaseAndActive(
                            nome == null ? "" : nome,
                            uf == null ? "" : uf,
                            ativo,
                            pageable
                    )
                    .map(ClubMapper::toDto);
        }
        return clubRepository
                .findByNameContainingIgnoreCaseAndUfContainingIgnoreCase(
                        nome == null ? "" : nome,
                        uf == null ? "" : uf,
                        pageable
                )
                .map(ClubMapper::toDto);
    }
}