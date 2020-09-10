package com.sb.solutions.api.crg.service;

import com.sb.solutions.api.crg.entity.CrgQuestion;
import com.sb.solutions.api.crg.repository.CrgQuestionRepository;
import com.sb.solutions.api.eligibility.answer.entity.Answer;
import com.sb.solutions.api.eligibility.answer.service.AnswerService;
import com.sb.solutions.api.eligibility.question.entity.Question;
import com.sb.solutions.api.loanConfig.entity.LoanConfig;
import com.sb.solutions.api.loanConfig.service.LoanConfigService;
import com.sb.solutions.core.enums.Status;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequiredArgsConstructor
public class CrgQuestionServiceImpl implements CrgQuestionService {

    private final Logger logger = LoggerFactory.getLogger(CrgQuestionServiceImpl.class);

    private final CrgQuestionRepository questionRepository;

    private final AnswerService answerService;

    private final LoanConfigService loanConfigService;



    @Override
    public List<CrgQuestion> findAll() {
        return questionRepository.findAll();
    }

    @Override
    public CrgQuestion findOne(Long id) {
        return questionRepository.getOne(id);
    }

    @Override
    public CrgQuestion save(CrgQuestion question) {
        return questionRepository.save(question);
    }

    @Override
    public List<CrgQuestion> save(List<CrgQuestion> questions) {
        List<CrgQuestion> savedQuestions = new ArrayList<>();
        for (CrgQuestion question : questions) {
            question.setLastModifiedAt(new Date());
            question.setStatus(Status.ACTIVE);
            final CrgQuestion savedQuestion = questionRepository.save(question);
            question.getAnswers().forEach(answer -> answer.setQuestion(savedQuestion));
            savedQuestion.setAnswers(answerService.save(question.getAnswers()));
            savedQuestion.setMaximumPoints(question.getAnswers().stream()
                .map(Answer::getPoints).max(Comparator.comparing(Long::valueOf)).get());
            savedQuestions.add(questionRepository.save(savedQuestion));
        }
        LoanConfig loanConfig = loanConfigService.findOne(savedQuestions.stream()
            .map(CrgQuestion::getLoanConfig).distinct().findAny().orElse(null).getId());
        loanConfig.setTotalPoints(
            loanConfig.getTotalPoints() + savedQuestions.stream().map(Question::getMaximumPoints)
                .mapToLong(Long::longValue).sum());
        loanConfigService.save(loanConfig);
        return savedQuestions;
    }

    @Override
    public Page<CrgQuestion> findAllPageable(Object question, Pageable pageable) {
        return questionRepository.findAll(pageable);
    }

    @Override
    public List<CrgQuestion> saveAll(List<CrgQuestion> list) {
        return questionRepository.saveAll(list);
    }

    @Override
    public List<CrgQuestion> findByLoanConfigId(Long loanConfigId) {
        return questionRepository.findByLoanConfigIdAndStatusNot(loanConfigId, Status.DELETED);
    }

    @Override
    public CrgQuestion update(CrgQuestion question) {
        final List<Answer> answers = new ArrayList<>();
        answers.addAll(question.getAnswers());
        question.setLastModifiedAt(new Date());
        question.getAnswers().removeAll(question.getAnswers());
        CrgQuestion updatedQuestion = questionRepository.save(question);
        for (Answer answer : answers) {
            answer.setQuestion(updatedQuestion);
        }
        final List<Answer> updatedAnswers = answerService.update(answers, updatedQuestion);
        updatedQuestion.setMaximumPoints(updatedAnswers.stream().map(Answer::getPoints)
            .max(Comparator.comparing(Long::valueOf)).orElse(0L));
        updatedQuestion.setAnswers(updatedAnswers);
        updatedQuestion = questionRepository.save(updatedQuestion);
        LoanConfig loanConfig = loanConfigService.findOne(updatedQuestion.getLoanConfig().getId());
        List<CrgQuestion> allQuestions = findByLoanConfigId(loanConfig.getId());
        loanConfig.setTotalPoints(
            allQuestions.stream().map(Question::getMaximumPoints).mapToLong(Long::longValue).sum());
        loanConfigService.save(loanConfig);
        return updatedQuestion;
    }

    @Override
    public void delete(long id) {
        logger.debug("Setting status to deleted for the question with id [{}].", id);
        final CrgQuestion question = questionRepository.getOne(id);
        if (question != null) {
            final List<Answer> answers = question.getAnswers();
            answerService.delete(answers);
            question.setStatus(Status.DELETED);
            questionRepository.save(question);
        }
    }
}
