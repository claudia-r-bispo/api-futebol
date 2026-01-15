package com.neoCamp.footballMatch.repository;

import com.neoCamp.footballMatch.dto.RankingDTO;
import com.neoCamp.footballMatch.entity.ClubEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClubRepository extends JpaRepository<ClubEntity, Long> {

    Page<ClubEntity> findByNameContainingIgnoreCaseAndUfContainingIgnoreCaseAndActive(String name, String uf, Boolean active, Pageable pageable);

    Page<ClubEntity> findByNameContainingIgnoreCaseAndUfContainingIgnoreCase(String name, String uf, Pageable pageable);


    @Query("""
SELECT new com.neoCamp.footballMatch.dto.RankingDTO(
    c.id,
    c.name,
    COUNT(m),
    SUM(CASE WHEN (m.homeClub.id = c.id AND m.homeTeamGoals > m.goalsVisitor) OR (m.clubVisitor.id = c.id AND m.goalsVisitor > m.homeTeamGoals) THEN 1 ELSE 0 END),
    SUM(CASE WHEN m.homeTeamGoals = m.goalsVisitor THEN 1 ELSE 0 END),
    SUM(CASE WHEN (m.homeClub.id = c.id AND m.homeTeamGoals < m.goalsVisitor) OR (m.clubVisitor.id = c.id AND m.goalsVisitor < m.homeTeamGoals) THEN 1 ELSE 0 END),
    SUM(CASE WHEN m.homeClub.id = c.id THEN m.homeTeamGoals ELSE m.goalsVisitor END),
    SUM(CASE WHEN m.homeClub.id = c.id THEN m.goalsVisitor ELSE m.homeTeamGoals END),
    SUM(
        CASE
            WHEN (m.homeClub.id = c.id AND m.homeTeamGoals > m.goalsVisitor)
              OR (m.clubVisitor.id = c.id AND m.goalsVisitor > m.homeTeamGoals)
            THEN 3
            WHEN m.homeTeamGoals = m.goalsVisitor THEN 1
            ELSE 0
        END
    )
)
FROM ClubEntity c
LEFT JOIN FootballMatch m ON c.id = m.homeClub.id OR c.id = m.clubVisitor.id
GROUP BY c.id, c.name
ORDER BY 
    SUM(
        CASE
            WHEN (m.homeClub.id = c.id AND m.homeTeamGoals > m.goalsVisitor)
              OR (m.clubVisitor.id = c.id AND m.goalsVisitor > m.homeTeamGoals)
            THEN 3
            WHEN m.homeTeamGoals = m.goalsVisitor THEN 1
            ELSE 0
        END
    ) DESC
""")
    List<RankingDTO> rankingGeral();
}