package com.sb.solutions.api.eligibility.answer.service;

import com.sb.solutions.api.eligibility.answer.entity.Answer;
import com.sb.solutions.core.service.BaseService;

import java.util.List;

public interface AnswerService extends BaseService<Answer> {

    List<Answer> save(List<Answer> answers);

}
