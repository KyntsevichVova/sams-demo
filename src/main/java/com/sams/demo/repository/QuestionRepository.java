package com.sams.demo.repository;

import com.sams.demo.model.dto.ReadAllQuestionDTO;
import com.sams.demo.model.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query(value = "SELECT new com.sams.demo.model.dto.ReadAllQuestionDTO( " +
            "q.id AS id, t.title AS title, q.link AS link, ll.levelLocalized AS level) " +
            "FROM QUESTION q JOIN q.titles t JOIN q.level.localizedLevels ll " +
            "WHERE t.locale.code = :locale AND ll.locale.code = :locale " +
            "AND q.level.type LIKE COALESCE(:level, '%')")
    Page<ReadAllQuestionDTO> findAll(@Param("level") String level,
                                     @Param("locale") String locale,
                                     Pageable pageable);
}