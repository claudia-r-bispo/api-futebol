package com.neoCamp.footballMatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.neoCamp.footballMatch.entity.StadiumEntity;

public interface StadiumRepository extends JpaRepository<StadiumEntity, Long> {

}
