package com.sb.solutions.api.eligibility.applicant.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.sb.solutions.api.eligibility.answer.entity.Answer;
import com.sb.solutions.api.eligibility.answer.entity.EligibilityAnswer;
import com.sb.solutions.api.eligibility.answer.service.AnswerService;
import com.sb.solutions.api.eligibility.applicant.entity.Applicant;
import com.sb.solutions.api.eligibility.applicant.repository.ApplicantRepository;
import com.sb.solutions.api.eligibility.applicant.repository.specification.ApplicantSpecificationBuilder;
import com.sb.solutions.api.eligibility.applicant.service.ApplicantService;
import com.sb.solutions.api.eligibility.common.EligibilityConstants;
import com.sb.solutions.api.eligibility.common.EligibilityStatus;
import com.sb.solutions.api.eligibility.criteria.entity.EligibilityCriteria;
import com.sb.solutions.api.eligibility.criteria.service.EligibilityCriteriaService;
import com.sb.solutions.api.eligibility.document.dto.DocumentDTO;
import com.sb.solutions.api.eligibility.document.entity.SubmissionDocument;
import com.sb.solutions.api.eligibility.document.service.SubmissionDocumentService;
import com.sb.solutions.api.eligibility.utility.EligibilityUtility;
import com.sb.solutions.api.filestorage.service.FileStorageService;
import com.sb.solutions.core.enums.Status;
import com.sb.solutions.core.utils.ArithmeticExpressionUtils;


@Service
public class ApplicantServiceImpl implements ApplicantService {

    private final Logger logger = LoggerFactory.getLogger(ApplicantServiceImpl.class);

    private final ApplicantRepository applicantRepository;

    private final FileStorageService fileStorageService;

    private final SubmissionDocumentService submissionDocumentService;

    private final AnswerService answerService;

    private final EligibilityCriteriaService eligibilityCriteriaService;

    public ApplicantServiceImpl(ApplicantRepository applicantRepository,
        FileStorageService fileStorageService,
        SubmissionDocumentService submissionDocumentService, AnswerService answerService,
        EligibilityCriteriaService eligibilityCriteriaService) {
        this.applicantRepository = applicantRepository;
        this.fileStorageService = fileStorageService;
        this.submissionDocumentService = submissionDocumentService;
        this.answerService = answerService;
        this.eligibilityCriteriaService = eligibilityCriteriaService;
    }

    @Override
    public List<Applicant> findAll() {
        logger.debug("Retrieving list of all the applicants.");
        return applicantRepository.findAll();
    }

    @Override
    public Applicant findOne(Long id) {
        logger.debug("Retrieving applicant with the id [{}].", id);
        return applicantRepository.getOne(id);
    }

    @Override
    public Applicant save(Applicant applicant) {
        logger.debug("Saving the applicant information.");
        applicant.setObtainedMarks(
            applicant.getAnswers().stream().map(Answer::getPoints).mapToLong(Long::valueOf).sum());
        return applicantRepository.save(applicant);
    }

    @Override
    public Applicant save(Applicant applicant, Long loanConfigId) {
        logger.debug("Saving the applicant information.");
        final EligibilityCriteria eligibilityCriteria = eligibilityCriteriaService
            .getByStatus(Status.ACTIVE);
        String formula = eligibilityCriteria.getFormula();
        Map<String, Long> operands = EligibilityUtility
            .extractOperands(formula, eligibilityCriteria.getQuestions());
        for (Map.Entry<String, Long> operand : operands.entrySet()) {
            for (EligibilityAnswer eligibilityAnswer : applicant.getEligibilityAnswers()) {
                if (eligibilityAnswer.getEligibilityQuestion().getId().equals(operand.getValue())) {
                    formula = formula
                        .replace(operand.getKey(), String.valueOf(eligibilityAnswer.getValue()));
                }
            }
        }
        double remainingAmount = ArithmeticExpressionUtils
            .parseExpression(formula); // new Expression
        if (remainingAmount <= 0) {
            applicant.setEligibilityStatus(EligibilityStatus.NOT_ELIGIBLE);
            return applicantRepository.save(applicant);
        }
        double eligibleAmount =
            remainingAmount * eligibilityCriteria.getPercentageOfAmount() / 100D;
        if (eligibleAmount < eligibilityCriteria.getThresholdAmount()) {
            applicant.setEligibilityStatus(EligibilityStatus.NOT_ELIGIBLE);
            return applicantRepository.save(applicant);
        }
        applicant.setEligibleAmount(eligibleAmount);
        applicant.setEligibilityStatus(EligibilityStatus.ELIGIBLE);
        List<Answer> answers =
            answerService.findByIds(
                applicant.getAnswers().stream().map(Answer::getId).collect(Collectors.toList()));
        applicant.setObtainedMarks(
            answers.stream().map(Answer::getPoints).mapToLong(Long::valueOf).sum());
        applicant.getEligibilityAnswers()
            .forEach(eligibilityAnswer -> eligibilityAnswer.setApplicant(applicant));
        return applicantRepository.save(applicant);
    }

    @Override
    public Page<Applicant> findAllPageable(Object t, Pageable pageable) {
        logger.debug("Retrieving a page of applicant list.");
        ApplicantSpecificationBuilder applicantSpecificationBuilder = new ApplicantSpecificationBuilder();
        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
        Matcher matcher = pattern.matcher(t + ",");
        while (matcher.find()) {
            applicantSpecificationBuilder
                .with(matcher.group(1), matcher.group(3), matcher.group(2));
        }
        Specification<Applicant> specification = applicantSpecificationBuilder.build();
        return applicantRepository.findAll(specification, pageable);
    }

    @Override
    public Applicant saveDocuments(List<DocumentDTO> documents, long applicantId) {
        logger.debug("Saving documents of the applicant.");
        Applicant applicant = applicantRepository.getOne(applicantId);
        List<SubmissionDocument> submittedDocuments = new ArrayList<>();
        for (DocumentDTO document : documents) {
            String destinationPath = fileStorageService
                .getFilePath(EligibilityConstants.ELIGIBILITY_DIRECTORY,
                    EligibilityConstants.LOAN_CONFIG_DIRECTORY,
                    String.valueOf(applicant.getLoanConfig().getId()),
                    EligibilityConstants.APPLICANT_DIRECTORY,
                    String.valueOf(applicant.getId()));
            String imageUrl =
                "/applicant-documents/" + fileStorageService.storeDocument(document.getImage(),
                    document.getName(), destinationPath);
            submittedDocuments.add(getSubmissionDocument(document, imageUrl));
        }
        applicant.setDocuments(submittedDocuments);
        applicant = applicantRepository.save(applicant);
        return applicant;
    }

    private SubmissionDocument getSubmissionDocument(DocumentDTO document, String imageUrl) {
        SubmissionDocument submissionDocument = new SubmissionDocument();
        submissionDocument.setName(document.getName());
        submissionDocument.setType(document.getType());
        submissionDocument.setUrl(imageUrl);
        submissionDocument = submissionDocumentService.save(submissionDocument);
        return submissionDocument;
    }
}
