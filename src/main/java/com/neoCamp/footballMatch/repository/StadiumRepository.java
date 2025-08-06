package com.neoCamp.footballMatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.neoCamp.footballMatch.entity.StadiumEntity;

import java.util.UUID;

public interface StadiumRepository extends JpaRepository<StadiumEntity, UUID> {
    
}
