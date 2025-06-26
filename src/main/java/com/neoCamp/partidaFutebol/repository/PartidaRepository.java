package com.neoCamp.partidaFutebol.repository;

import com.neoCamp.partidaFutebol.entity.PartidaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;

public interface PartidaRepository extends JpaRepository<PartidaEntity, Long> {
    Page<PartidaEntity> findByClubeMandanteIdOrClubeVisitanteId (Long mandanteID, Long visitanteId, Pageable pageable);
}
