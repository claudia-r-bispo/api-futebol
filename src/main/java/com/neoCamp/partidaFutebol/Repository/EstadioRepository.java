package com.neoCamp.partidaFutebol.Repository;

import com.neoCamp.partidaFutebol.Entity.Estadio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstadioRepository<Estadio> extends JpaRepository<Estadio,Long> {
}
