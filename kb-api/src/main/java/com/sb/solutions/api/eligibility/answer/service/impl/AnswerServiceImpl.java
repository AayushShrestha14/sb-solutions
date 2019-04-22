package com.sb.solutions.api.eligibility.answer.service.impl;

import com.sb.solutions.api.eligibility.answer.entity.Answer;
import com.sb.solutions.api.eligibility.answer.repository.AnswerRepository;
import com.sb.solutions.api.eligibility.answer.service.AnswerService;
import com.sb.solutions.api.eligibility.question.entity.Question;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
        answer.setLastModifiedAt(new Date());
        return answerRepository.save(answer);
    }

    @Override
    public Page<Answer> findAllPageable(Answer answer, Pageable pageable) {
        return answerRepository.findAll(pageable);
    }

    @Override
    public List<Answer> save(List<Answer> answers) {
        answers.forEach(answer -> answer.setLastModifiedAt(new Date()));
        return answerRepository.saveAll(answers);
    }

    @Transactional
    @Override
    public List<Answer> update(List<Answer> answers, Question question) {
        final List<Answer> savedAnswers = answerRepository.findAllByQuestionId(question.getId());
        final List<Answer> newAnswers = answers.stream().filter(answer -> answer.getId() == null || answer.getId() == 0)
                .collect(Collectors.toList());
        final List<Answer> modifiedAnswers = answers.stream().filter(answer -> !newAnswers.contains(answer)
                && savedAnswers.stream().map(Answer::getId).collect(Collectors.toList()).contains(answer.getId()))
                .collect(Collectors.toList());
        final List<Answer> deletedAnswers = savedAnswers.stream().filter(answer -> modifiedAnswers.stream()
                .noneMatch(modifiedAnswer -> answer.getId().equals(modifiedAnswer.getId()))).collect(Collectors.toList());
        List<Answer> answersToPersist = new ArrayList<>();
        answersToPersist.addAll(newAnswers);
        answersToPersist.addAll(modifiedAnswers);
        answersToPersist.forEach(answerToPersist -> answerToPersist.setLastModifiedAt(new Date()));
        answerRepository.deleteAll(deletedAnswers);
        answersToPersist = answerRepository.saveAll(answersToPersist);
        return answersToPersist;
    }
}
