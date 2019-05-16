package com.sb.solutions.api.eligibility.question.service.impl;

import com.sb.solutions.api.eligibility.answer.entity.Answer;
import com.sb.solutions.api.eligibility.answer.service.AnswerService;
import com.sb.solutions.api.eligibility.question.entity.Question;
import com.sb.solutions.api.eligibility.question.repository.QuestionRepository;
import com.sb.solutions.api.eligibility.question.service.QuestionService;
import com.sb.solutions.api.eligibility.scheme.entity.Scheme;
import com.sb.solutions.api.eligibility.scheme.service.SchemeService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    private final AnswerService answerService;

    private final SchemeService schemeService;

    @Override
    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    @Override
    public Question findOne(Long id) {
        return questionRepository.getOne(id);
    }

    @Override
    public Question save(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public Page<Question> findAllPageable(Object question, Pageable pageable) {
        return questionRepository.findAll(pageable);
    }

    @Override
    public List<Question> save(List<Question> questions) {
        List<Question> savedQuestions = new ArrayList<>();
        for (Question question : questions) {
            question.setLastModifiedAt(new Date());
            final Question savedQuestion = questionRepository.save(question);
            question.getAnswers().forEach(answer -> answer.setQuestion(savedQuestion));
            savedQuestion.setAnswers(answerService.save(question.getAnswers()));
            savedQuestion.setMaximumPoints(question.getAnswers().stream()
                    .map(Answer::getPoints).max(Comparator.comparing(Long::valueOf)).get());
            savedQuestions.add(questionRepository.save(savedQuestion));
        }
        Scheme scheme = schemeService.findOne(savedQuestions.stream()
                .map(Question::getScheme).distinct().findAny().orElse(null).getId());
        scheme.setTotalPoints(scheme.getTotalPoints() + savedQuestions.stream().map(Question::getMaximumPoints)
                .mapToLong(Long::longValue).sum());
        schemeService.save(scheme);
        return savedQuestions;
    }

    @Override
    public List<Question> findBySchemeId(Long schemeId) {
        return questionRepository.findBySchemeId(schemeId);
    }

    @Override
    public Question update(Question question) {
        final List<Answer> answers = new ArrayList<>();
        answers.addAll(question.getAnswers());
        question.setLastModifiedAt(new Date());
        question.getAnswers().removeAll(question.getAnswers());
        Question updatedQuestion = questionRepository.save(question);
        for (Answer answer : answers) {
            answer.setQuestion(updatedQuestion);
        }
        final List<Answer> updatedAnswers = answerService.update(answers, updatedQuestion);
        updatedQuestion.setMaximumPoints(updatedAnswers.stream().map(Answer::getPoints)
                .max(Comparator.comparing(Long::valueOf)).orElse(0L));
        updatedQuestion.setAnswers(updatedAnswers);
        updatedQuestion = questionRepository.save(updatedQuestion);
        Scheme scheme = schemeService.findOne(updatedQuestion.getScheme().getId());
        List<Question> allQuestions = findBySchemeId(scheme.getId());
        scheme.setTotalPoints(allQuestions.stream().map(Question::getMaximumPoints).mapToLong(Long::longValue).sum());
        schemeService.save(scheme);
        return updatedQuestion;
    }
}
