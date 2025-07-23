package com.neoCamp.footballMatch.repository;

import com.neoCamp.footballMatch.dto.DirectConfrontationDTO;
import com.neoCamp.footballMatch.dto.RankingDTO;
import com.neoCamp.footballMatch.dto.RetrospectoDTO;
import com.neoCamp.footballMatch.entity.FootballMatch;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface FootballMatchRepository extends JpaRepository<FootballMatch, UUID> {
    Page<FootballMatch> findByHomeClubIdOrClubVisitorId(Long homeClubId, Long clubVisitorId, Pageable pageable);
    @Query("""
SELECT new com.neoCamp.footballMatch.dto.RankingDTO(
    p.homeClub.id,
    p.homeClub.name,
    COUNT(p),
    SUM(CASE WHEN p.homeTeamGoals > p.goalsVisitor THEN 1 ELSE 0 END),
    SUM(CASE WHEN p.homeTeamGoals = p.goalsVisitor THEN 1 ELSE 0 END),
    SUM(CASE WHEN p.homeTeamGoals < p.goalsVisitor THEN 1 ELSE 0 END),
    SUM(p.homeTeamGoals),
    SUM(p.goalsVisitor),
   
    SUM(CASE
        WHEN p.homeTeamGoals > p.goalsVisitor THEN 3
        WHEN p.homeTeamGoals = p.goalsVisitor THEN 1
        ELSE 0 END)
)
FROM FootballMatch p
GROUP BY p.homeClub.id, p.homeClub.name
ORDER BY SUM(CASE
        WHEN p.homeTeamGoals > p.goalsVisitor THEN 3
        WHEN p.homeTeamGoals = p.goalsVisitor THEN 1
        ELSE 0 END) DESC
""")
    List<RankingDTO> rankingMandantes();


    @Query("""
SELECT new com.neoCamp.footballMatch.dto.RetrospectoDTO(
    :clubeId,
    COUNT(p),
    SUM(CASE WHEN (p.homeClub.id = :clubeId AND p.homeTeamGoals > p.goalsVisitor)
                 OR (p.clubVisitor.id = :clubeId AND p.goalsVisitor > p.homeTeamGoals)
             THEN 1 ELSE 0 END),
    SUM(CASE WHEN p.homeTeamGoals = p.goalsVisitor THEN 1 ELSE 0 END),
    SUM(CASE WHEN (p.homeClub.id = :clubeId AND p.homeTeamGoals < p.goalsVisitor)
                 OR (p.clubVisitor.id = :clubeId AND p.goalsVisitor < p.homeTeamGoals)
             THEN 1 ELSE 0 END),
    SUM(CASE WHEN p.homeClub.id = :clubeId THEN p.homeTeamGoals ELSE p.goalsVisitor END),
    SUM(CASE WHEN p.homeClub.id = :clubeId THEN p.goalsVisitor ELSE p.homeTeamGoals END)
)
FROM FootballMatch p
WHERE p.homeClub.id = :clubeId OR p.clubVisitor.id = :clubeId
""")
    RetrospectoDTO retrospectoGeral(@Param("clubeId") Long clubeId);

    @Query("""
SELECT new com.neoCamp.footballMatch.dto.DirectConfrontationDTO(
    :clubeA,
    :clubeB,
    COUNT(p),
    SUM(CASE WHEN p.homeClub.id = :clubeA AND p.homeTeamGoals > p.goalsVisitor THEN 1
             WHEN p.clubVisitor.id = :clubeA AND p.goalsVisitor > p.homeTeamGoals THEN 1
             ELSE 0 END),
    SUM(CASE WHEN p.homeClub.id = :clubeB AND p.homeTeamGoals > p.goalsVisitor THEN 1
             WHEN p.clubVisitor.id = :clubeB AND p.goalsVisitor > p.homeTeamGoals THEN 1
             ELSE 0 END),
    SUM(CASE WHEN p.homeTeamGoals = p.goalsVisitor THEN 1 ELSE 0 END)
)
FROM FootballMatch p
WHERE (p.homeClub.id = :clubeAId AND p.clubVisitor.id = :clubeBId)
   OR (p.homeClub.id = :clubeBId AND p.clubVisitor.id = :clubeAId)
""")
    DirectConfrontationDTO confrontoDireto(@Param("clubeA") Long clubeA, @Param("clubeB") Long clubeB);


}
