package com.sb.solutions.api.eligibility.criteria.service.impl;

import com.sb.solutions.api.eligibility.criteria.entity.EligibilityCriteria;
import com.sb.solutions.api.eligibility.criteria.repository.EligibilityCriteriaRepository;
import com.sb.solutions.api.eligibility.criteria.service.EligibilityCriteriaService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EligibilityCriteriaServiceImpl implements EligibilityCriteriaService {

    private final Logger logger = LoggerFactory.getLogger(EligibilityCriteriaServiceImpl.class);

    private final EligibilityCriteriaRepository eligibilityCriteriaRepository;

    @Override
    public List<EligibilityCriteria> findAll() {
        logger.debug("Retrieving all eligibility criteria.");
        return eligibilityCriteriaRepository.findAll();
    }

    @Override
    public EligibilityCriteria findOne(Long id) {
        logger.debug("Retrieving the eligibility criteria with id [{}].", id);
        return eligibilityCriteriaRepository.getOne(id);
    }

    @Override
    public EligibilityCriteria save(EligibilityCriteria eligibilityCriteria) {
        logger.debug("Saving the eligibility criteria in database.");
        return eligibilityCriteriaRepository.save(eligibilityCriteria);
    }

    @Override
    public Page<EligibilityCriteria> findAllPageable(Object t, Pageable pageable) {
        logger.debug("Retrieving a page of eligibility criteria.");
        return eligibilityCriteriaRepository.findAll(pageable);
    }
}
