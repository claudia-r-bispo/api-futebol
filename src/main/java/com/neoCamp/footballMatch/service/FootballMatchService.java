package com.neoCamp.footballMatch.service;

import com.neoCamp.footballMatch.dto.FootballMatchDTO;
import com.neoCamp.footballMatch.dto.FootballMatchDetailsDTO;
import com.neoCamp.footballMatch.entity.ClubEntity;
import com.neoCamp.footballMatch.entity.FootballMatch;
import com.neoCamp.footballMatch.entity.StadiumEntity;
import com.neoCamp.footballMatch.mapper.FootballMatchMapper;
import com.neoCamp.footballMatch.repository.ClubRepository;
import com.neoCamp.footballMatch.repository.FootballMatchRepository;
import com.neoCamp.footballMatch.repository.StadiumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class FootballMatchService {
    
    private final FootballMatchRepository footballMatchRepository;
    private final ClubRepository clubRepository;
    private final StadiumRepository stadiumRepository;
    
    @Transactional(readOnly = true)
    public FootballMatchDTO findById(UUID id) {
        return footballMatchRepository.findById(id)
                .map(FootballMatchMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Partida não encontrada!"));
    }
    
    @Transactional(readOnly = true)
    public FootballMatch findEntityById(UUID id) {
        return footballMatchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partida não encontrada!"));
    }
    
    @Transactional(readOnly = true)
    public FootballMatchDetailsDTO findMatchDetailsById(UUID id) {
        return footballMatchRepository.findById(id)
                .map(FootballMatchMapper::toDetailsDto)
                .orElseThrow(() -> new RuntimeException("Partida não encontrada!"));
    }

    @Transactional(readOnly = true)
    public Page<FootballMatchDTO> listar(Pageable pageable) {
        return footballMatchRepository.findAll(pageable)
                .map(FootballMatchMapper::toDto);
    }
    
    public FootballMatchDTO create(FootballMatchDTO dto) {
        validateMatchData(dto);
        
        // Busca as entidades relacionadas
        ClubEntity homeClub = clubRepository.findById(dto.getHomeClubId())
            .orElseThrow(() -> new RuntimeException("Clube mandante não encontrado com o ID: " + dto.getHomeClubId()));
            
        ClubEntity visitor = clubRepository.findById(dto.getClubVisitorId())
            .orElseThrow(() -> new RuntimeException("Clube visitante não encontrado com o ID: " + dto.getClubVisitorId()));
            
        StadiumEntity stadium = stadiumRepository.findById(dto.getStadiumId())
            .orElseThrow(() -> new RuntimeException("Estádio não encontrado com o ID: " + dto.getStadiumId()));
        
        // Cria a entidade com as relações
        FootballMatch match = new FootballMatch();
        match.setHomeClub(homeClub);
        match.setClubVisitor(visitor);
        match.setStadium(stadium);
        match.setDateTimeDeparture(dto.getDateTimeDeparture());
        match.setHomeTeamGoals(dto.getHomeTeamGoals());
        match.setGoalsVisitor(dto.getGoalsVisitor());
        
        FootballMatch saved = footballMatchRepository.save(match);
        return FootballMatchMapper.toDto(saved);
    }
    
    public FootballMatchDTO update(UUID id, FootballMatchDTO dto) {
        validateMatchData(dto);
        
        FootballMatch match = findEntityById(id);
        
        // Atualiza os campos necessários
        match.setDateTimeDeparture(dto.getDateTimeDeparture());
        match.setHomeTeamGoals(dto.getHomeTeamGoals());
        match.setGoalsVisitor(dto.getGoalsVisitor());
        
        // Atualiza as relações se necessário
        if (dto.getHomeClubId() != null && !dto.getHomeClubId().equals(match.getHomeClub().getId())) {
            ClubEntity homeClub = clubRepository.findById(dto.getHomeClubId())
                .orElseThrow(() -> new RuntimeException("Clube mandante não encontrado com o ID: " + dto.getHomeClubId()));
            match.setHomeClub(homeClub);
        }
        
        if (dto.getClubVisitorId() != null && !dto.getClubVisitorId().equals(match.getClubVisitor().getId())) {
            ClubEntity visitor = clubRepository.findById(dto.getClubVisitorId())
                .orElseThrow(() -> new RuntimeException("Clube visitante não encontrado com o ID: " + dto.getClubVisitorId()));
            match.setClubVisitor(visitor);
        }
        
        if (dto.getStadiumId() != null && !dto.getStadiumId().equals(match.getStadium().getId())) {
            StadiumEntity stadium = stadiumRepository.findById(dto.getStadiumId())
                .orElseThrow(() -> new RuntimeException("Estádio não encontrado com o ID: " + dto.getStadiumId()));
            match.setStadium(stadium);
        }
        
        FootballMatch updated = footballMatchRepository.save(match);
        return FootballMatchMapper.toDto(updated);
    }
    
    public void delete(UUID id) {
        FootballMatch match = findEntityById(id);
        footballMatchRepository.delete(match);
    }
    
    private void validateMatchData(FootballMatchDTO dto) {
        if (dto.getHomeClubId() == null || dto.getClubVisitorId() == null || 
            dto.getStadiumId() == null || dto.getDateTimeDeparture() == null) {
            throw new IllegalArgumentException("Todos os campos obrigatórios devem ser preenchidos");
        }
        if (dto.getHomeClubId().equals(dto.getClubVisitorId())) {
            throw new IllegalArgumentException("Clube mandante e visitante devem ser diferentes");
        }
        
        // Validações adicionais
        if (dto.getHomeTeamGoals() != null && dto.getHomeTeamGoals() < 0) {
            throw new IllegalArgumentException("Gols do time mandante não podem ser negativos");
        }
        
        if (dto.getGoalsVisitor() != null && dto.getGoalsVisitor() < 0) {
            throw new IllegalArgumentException("Gols do time visitante não podem ser negativos");
        }
    }
}
