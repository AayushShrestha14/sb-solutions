package com.sb.solutions.api.eligibility.question.repository;

import com.sb.solutions.api.eligibility.question.entity.EligibilityQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EligibilityQuestionRepository extends JpaRepository<EligibilityQuestion, Long> {

}
