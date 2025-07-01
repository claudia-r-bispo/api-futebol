package com.neoCamp.footballMatch.service;

import com.neoCamp.footballMatch.entity.ClubEntity;
import com.neoCamp.footballMatch.mapper.ClubMapper;
import com.neoCamp.footballMatch.repository.ClubRepository;
import com.neoCamp.footballMatch.dto.ClubDTO;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ClubService {


    private ClubRepository clubRepository;

    public ClubDTO createClube(ClubDTO dto) {
        ClubEntity entity = ClubMapper.toEntity(dto);
        ClubEntity savedClube = clubRepository.save(entity);
        return ClubMapper.toDto(savedClube);

    }

    public ClubDTO updateClube(Long id, ClubDTO dto) {
        ClubEntity clube = clubRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Clube não encontrado com o ID: " + id));
        clube.setName(dto.getName());
        clube.setUf(dto.getUf());
        clube.setDateCreation(dto.getDateCreation());
        clube.setActive(dto.getActive());
        ClubEntity saved = clubRepository.save(clube);
        return ClubMapper.toDto(saved); // agora retorna um DTO
    }


    public void inativar(Long idClube) {
        ClubEntity clube = clubRepository.findById(idClube)
                .orElseThrow(() -> new EntityNotFoundException("Clube não encontrado"));
        clube.setActive(false);
        clubRepository.save(clube);
    }

    public ClubDTO findById(Long id) {
        ClubEntity entity = clubRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Clube não encontrado"));
        return ClubMapper.toDto(entity);
    }

    public ClubEntity findEntityById(Long id) {
        return clubRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Clube não encontrado"));
    }


    public Page<ClubDTO> listClubsWithFilters(String nome, String uf, Boolean ativo, Pageable pageable) {
        if (ativo != null) {
            Page<ClubEntity> page;
            if (ativo != null) {
                page = clubRepository.findByNameContainingIgnoreCaseAndUfContainingIgnoreCaseAndActive(nome == null ? "" : nome, uf == null ? "" : uf, ativo, pageable);
            } else {
                page = clubRepository.findByNameContainingIgnoreCaseAndUfContainingIgnoreCase(nome == null ? "" : nome, uf == null ? "" : uf, pageable);
            }
            return page.map(ClubMapper::toDto);
        }


        return clubRepository.findAll(pageable).map(ClubMapper::toDto);
}}