package com.sams.demo.repository;

import com.sams.demo.model.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query(value = "FROM QUESTION q WHERE q.level LIKE COALESCE(:level, '%')")
    Page<Question> findAll(@Param("level") String level, Pageable pageable);
}