package com.sb.solutions.api.eligibility.question.service.impl;

import com.sb.solutions.api.eligibility.question.entity.EligibilityQuestion;
import com.sb.solutions.api.eligibility.question.repository.EligibilityQuestionRepository;
import com.sb.solutions.api.eligibility.question.service.EligibilityQuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EligibilityQuestionServiceImpl implements EligibilityQuestionService {

    private final Logger logger = LoggerFactory.getLogger(EligibilityQuestionServiceImpl.class);

    private final EligibilityQuestionRepository eligibilityQuestionRepository;

    public EligibilityQuestionServiceImpl(EligibilityQuestionRepository eligibilityQuestionRepository) {
        this.eligibilityQuestionRepository = eligibilityQuestionRepository;
    }

    @Override
    public List<EligibilityQuestion> findAll() {
        logger.debug("Retrieving all the eligibility questions.");
        return eligibilityQuestionRepository.findAll();
    }

    @Override
    public EligibilityQuestion findOne(Long id) {
        logger.debug("Retrieving the eligibility question with id: [{}].", id);
        return eligibilityQuestionRepository.getOne(id);
    }

    @Override
    public EligibilityQuestion save(EligibilityQuestion eligibilityQuestion) {
        logger.debug("Saving the eligibility question to the database.");
        eligibilityQuestion = eligibilityQuestionRepository.save(eligibilityQuestion);
        return eligibilityQuestion;
    }

    @Override
    public Page<EligibilityQuestion> findAllPageable(Object t, Pageable pageable) {
        logger.debug("Retrieving a page of eligibility question.");
        return eligibilityQuestionRepository.findAll(pageable);
    }
}
