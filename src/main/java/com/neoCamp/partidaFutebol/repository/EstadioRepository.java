package com.neoCamp.partidaFutebol.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.neoCamp.partidaFutebol.entity.EstadioEntity;

public interface EstadioRepository extends JpaRepository<EstadioEntity, Long> {

}
