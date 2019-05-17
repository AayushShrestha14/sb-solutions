package com.sb.solutions.api.eligibility.answer.repository;

import com.sb.solutions.api.eligibility.answer.entity.Answer;
import com.sb.solutions.core.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    List<Answer> findAllByQuestionIdAndStatusNot(long questionId, Status status);

}
