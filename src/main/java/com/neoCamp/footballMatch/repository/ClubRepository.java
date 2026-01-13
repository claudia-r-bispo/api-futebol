package com.neoCamp.footballMatch.repository;

import com.neoCamp.footballMatch.dto.RankingDTO;
import com.neoCamp.footballMatch.entity.ClubEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ClubRepository extends JpaRepository<ClubEntity, UUID> {

    Page<ClubEntity> findByNameContainingIgnoreCaseAndUfContainingIgnoreCaseAndActive(String name, String uf, Boolean active, Pageable pageable);

    Page<ClubEntity> findByNameContainingIgnoreCaseAndUfContainingIgnoreCase(String name, String uf, Pageable pageable);

    @Query("""
    SELECT new com.neoCamp.footballMatch.dto.RankingDTO(
        c.id, 
        c.name,
        COUNT(m) as jogos,
        SUM(CASE 
            WHEN (m.homeClub.id = c.id AND m.homeTeamGoals > m.goalsVisitor) OR 
                 (m.clubVisitor.id = c.id AND m.goalsVisitor > m.homeTeamGoals) THEN 1 
            ELSE 0 
        END) as vitorias,
        SUM(CASE 
            WHEN (m.homeClub.id = c.id AND m.homeTeamGoals = m.goalsVisitor) OR 
                 (m.clubVisitor.id = c.id AND m.goalsVisitor = m.homeTeamGoals) THEN 1 
            ELSE 0 
        END) as empates,
        SUM(CASE 
            WHEN (m.homeClub.id = c.id AND m.homeTeamGoals < m.goalsVisitor) OR 
                 (m.clubVisitor.id = c.id AND m.goalsVisitor < m.homeTeamGoals) THEN 1 
            ELSE 0 
        END) as derrotas,
        SUM(CASE 
            WHEN m.homeClub.id = c.id THEN m.homeTeamGoals 
            ELSE m.goalsVisitor 
        END) as golsPro,
        SUM(CASE 
            WHEN m.homeClub.id = c.id THEN m.goalsVisitor 
            ELSE m.homeTeamGoals 
        END) as golsContra,
        SUM(CASE 
            WHEN m.homeClub.id = c.id THEN m.homeTeamGoals - m.goalsVisitor 
            ELSE m.goalsVisitor - m.homeTeamGoals 
        END) as saldoGols,
        SUM(CASE 
            WHEN (m.homeClub.id = c.id AND m.homeTeamGoals > m.goalsVisitor) OR 
                 (m.clubVisitor.id = c.id AND m.goalsVisitor > m.homeTeamGoals) THEN 3 
            WHEN (m.homeClub.id = c.id AND m.homeTeamGoals = m.goalsVisitor) OR 
                 (m.clubVisitor.id = c.id AND m.goalsVisitor = m.homeTeamGoals) THEN 1 
            ELSE 0 
        END) as pontos
    ) 
    FROM ClubEntity c 
    LEFT JOIN FootballMatch m ON (m.homeClub.id = c.id OR m.clubVisitor.id = c.id) 
    GROUP BY c.id, c.name 
    ORDER BY pontos DESC, vitorias DESC, saldoGols DESC, golsPro DESC, c.name
    """)
    List<RankingDTO> findRanking();
}