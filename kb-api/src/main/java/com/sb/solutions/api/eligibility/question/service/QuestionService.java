package com.sb.solutions.api.eligibility.question.service;

import com.sb.solutions.api.eligibility.question.entity.Question;
import com.sb.solutions.core.service.BaseService;

import java.util.List;

public interface QuestionService extends BaseService<Question> {

    List<Question> save(List<Question> questions);

    List<Question> findBySchemeId(Long schemeId);

}
