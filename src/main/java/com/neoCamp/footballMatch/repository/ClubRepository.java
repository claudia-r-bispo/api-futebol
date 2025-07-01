package com.neoCamp.footballMatch.repository;

import com.neoCamp.footballMatch.entity.ClubEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubRepository extends JpaRepository<ClubEntity, Long> {

    Page<ClubEntity> findByNameContainingIgnoreCaseAndUfContainingIgnoreCaseAndActive(String name, String uf, Boolean active, Pageable pageable);

    Page<ClubEntity> findByNameContainingIgnoreCaseAndUfContainingIgnoreCase(String name, String uf, Pageable pageable);


}