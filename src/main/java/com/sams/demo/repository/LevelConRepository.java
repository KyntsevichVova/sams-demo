package com.sams.demo.repository;

import com.sams.demo.model.entity.LevelCon;
import com.sams.demo.model.enums.LevelType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LevelConRepository extends JpaRepository<LevelCon, Long> {

    LevelCon findByType(LevelType type);
}