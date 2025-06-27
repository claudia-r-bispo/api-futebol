package com.neoCamp.partidaFutebol.repository;

import com.neoCamp.partidaFutebol.dto.ConfrontoDiretoDTO;
import com.neoCamp.partidaFutebol.dto.RankingDTO;
import com.neoCamp.partidaFutebol.dto.RetrospectoDTO;
import com.neoCamp.partidaFutebol.entity.PartidaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PartidaRepository extends JpaRepository<PartidaEntity, Long> {
    Page<PartidaEntity> findByClubeMandanteIdOrClubeVisitanteId (Long mandanteID, Long visitanteId, Pageable pageable);

    @Query("""
SELECT new com.neoCamp.partidaFutebol.dto.RankingDTO(
    p.clubeMandante.id,
    p.clubeMandante.nome,
    COUNT(p),
    SUM(CASE WHEN p.golsMandante > p.golsVisitante THEN 1 ELSE 0 END),
    SUM(CASE WHEN p.golsMandante = p.golsVisitante THEN 1 ELSE 0 END),
    SUM(CASE WHEN p.golsMandante < p.golsVisitante THEN 1 ELSE 0 END),
    SUM(p.golsMandante),
    SUM(p.golsVisitante),
   
    SUM(CASE
        WHEN p.golsMandante > p.golsVisitante THEN 3
        WHEN p.golsMandante = p.golsVisitante THEN 1
        ELSE 0 END)
)
FROM PartidaEntity p
GROUP BY p.clubeMandante.id, p.clubeMandante.nome
ORDER BY SUM(CASE
        WHEN p.golsMandante > p.golsVisitante THEN 3
        WHEN p.golsMandante = p.golsVisitante THEN 1
        ELSE 0 END) DESC
""")
    List<RankingDTO> rankingMandantes();


    @Query("""
SELECT new com.neoCamp.partidaFutebol.dto.RetrospectoDTO(
    :clubeId,
    COUNT(p),
    SUM(CASE WHEN (p.clubeMandante.id = :clubeId AND p.golsMandante > p.golsVisitante)
                 OR (p.clubeVisitante.id = :clubeId AND p.golsVisitante > p.golsMandante)
             THEN 1 ELSE 0 END),
    SUM(CASE WHEN p.golsMandante = p.golsVisitante THEN 1 ELSE 0 END),
    SUM(CASE WHEN (p.clubeMandante.id = :clubeId AND p.golsMandante < p.golsVisitante)
                 OR (p.clubeVisitante.id = :clubeId AND p.golsVisitante < p.golsMandante)
             THEN 1 ELSE 0 END),
    SUM(CASE WHEN p.clubeMandante.id = :clubeId THEN p.golsMandante ELSE p.golsVisitante END),
    SUM(CASE WHEN p.clubeMandante.id = :clubeId THEN p.golsVisitante ELSE p.golsMandante END)
)
FROM PartidaEntity p
WHERE p.clubeMandante.id = :clubeId OR p.clubeVisitante.id = :clubeId
""")
    RetrospectoDTO retrospectoGeral(@Param("clubeId") Long clubeId);

    @Query("""
SELECT new com.neoCamp.partidaFutebol.dto.ConfrontoDiretoDTO(
    :clubeA,
    :clubeB,
    COUNT(p),
    SUM(CASE WHEN p.clubeMandante.id = :clubeA AND p.golsMandante > p.golsVisitante THEN 1
             WHEN p.clubeVisitante.id = :clubeA AND p.golsVisitante > p.golsMandante THEN 1
             ELSE 0 END),
    SUM(CASE WHEN p.clubeMandante.id = :clubeB AND p.golsMandante > p.golsVisitante THEN 1
             WHEN p.clubeVisitante.id = :clubeB AND p.golsVisitante > p.golsMandante THEN 1
             ELSE 0 END),
    SUM(CASE WHEN p.golsMandante = p.golsVisitante THEN 1 ELSE 0 END)
)
FROM PartidaEntity p
WHERE (p.clubeMandante.id = :clubeAId AND p.clubeVisitante.id = :clubeBId)
   OR (p.clubeMandante.id = :clubeBId AND p.clubeVisitante.id = :clubeAId)
""")
    ConfrontoDiretoDTO confrontoDireto(@Param("clubeA") Long clubeA, @Param("clubeB") Long clubeB);
}
