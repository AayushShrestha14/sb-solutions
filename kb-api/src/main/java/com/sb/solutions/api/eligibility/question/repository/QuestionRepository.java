package com.sb.solutions.api.eligibility.question.repository;

import com.sb.solutions.api.eligibility.question.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findBySchemeId(@Param("schemeId") Long schemeId);

}
