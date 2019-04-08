package com.sb.solutions.api.eligibility.answer.service.impl;

import com.sb.solutions.api.eligibility.answer.entity.Answer;
import com.sb.solutions.api.eligibility.answer.repository.AnswerRepository;
import com.sb.solutions.api.eligibility.answer.service.AnswerService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;

    @Override
    public List<Answer> findAll() {
        return answerRepository.findAll();
    }

    @Override
    public Answer findOne(Long id) {
        return answerRepository.getOne(id);
    }

    @Override
    public Answer save(Answer answer) {
        return answerRepository.save(answer);
    }

    @Override
    public Page<Answer> findAllPageable(Answer answer, Pageable pageable) {
        return answerRepository.findAll(pageable);
    }

    @Override
    public List<Answer> save(List<Answer> answers) {
        return answerRepository.saveAll(answers);
    }
}
