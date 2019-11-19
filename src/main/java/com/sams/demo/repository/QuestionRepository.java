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
    @Query(value = "SELECT * FROM QUESTION Q inner join DIFFICULTY AS D " +
            "on Q.DIFFICULTY_ID = D.ID " +
            "where D.DIFFICULTY_NAME LIKE coalesce(:difficulty, '%')", nativeQuery = true)
    Page<Question> findAll(@Param("difficulty") String difficulty, Pageable pageable);
}