package com.neoCamp.footballMatch.service;

import com.neoCamp.footballMatch.dto.FootballMatchDTO;
import com.neoCamp.footballMatch.entity.ClubEntity;
import com.neoCamp.footballMatch.entity.StadiumEntity;
import com.neoCamp.footballMatch.entity.FootballMatch;
import com.neoCamp.footballMatch.mapper.FootballMatchMapper;
import com.neoCamp.footballMatch.repository.FootballMatchRepository;
import com.neoCamp.footballMatch.repository.StadiumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FootballMatchService {

    private final FootballMatchRepository footballMatchRepository;
    private final ClubService clubService;
    private final StadiumService stadiumService;
    private final StadiumRepository stadiumRepository;

    @Autowired
    public FootballMatchService(FootballMatchRepository footballMatchRepository,
                                ClubService clubService,
                                StadiumService stadiumService,
                                StadiumRepository stadiumRepository) {
        this.footballMatchRepository = footballMatchRepository;
        this.clubService = clubService;
        this.stadiumService = stadiumService;
        this.stadiumRepository = stadiumRepository;
    }

    public FootballMatchDTO createPartida(FootballMatchDTO dto) {
        ClubEntity mandante = clubService.findEntityById(dto.getHomeClubId());
        ClubEntity visitante = clubService.findEntityById(dto.getClubVisitorId());
        StadiumEntity estadio = stadiumService.findEntityById(dto.getStadiumId());
        FootballMatch entity = FootballMatchMapper.toEntity(dto, mandante, visitante, estadio);
        FootballMatch saved = footballMatchRepository.save(entity);
        return FootballMatchMapper.toDto(saved);
    }

    public FootballMatchDTO updatePartida(Long id, FootballMatchDTO dto) {
        FootballMatch entity = footballMatchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partida não encontrada!"));
        ClubEntity mandante = clubService.findEntityById(dto.getHomeClubId());
        ClubEntity visitante = clubService.findEntityById(dto.getClubVisitorId());
        StadiumEntity estadio = stadiumService.findEntityById(dto.getStadiumId());
        entity.setHomeClub(mandante);
        entity.setClubVisitor(visitante);
        entity.setStadium(estadio);
        entity.setDateTimeDeparture(dto.getDateTimeDeparture());
        entity.setHomeTeamGoals(dto.getHomeTeamGoals());
        entity.setGoalsVisitor(dto.getGoalsVisitor());
        FootballMatch saved = footballMatchRepository.save(entity);
        return FootballMatchMapper.toDto(saved);
    }

    public void removerPartida(Long id) {
        footballMatchRepository.deleteById(id);
    }

    public FootballMatchDTO findById(Long id) {
        return footballMatchRepository.findById(id)
                .map(FootballMatchMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Partida não encontrada!"));
    }

    public FootballMatch findEntityById(Long id) {
        return footballMatchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partida não encontrada!"));
    }

    public Page<FootballMatchDTO> listar(Pageable pageable) {
        return footballMatchRepository.findAll(pageable).map(FootballMatchMapper::toDto);
    }

}

