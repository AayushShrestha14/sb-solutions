package com.sb.solutions.api.eligibility.applicant.service.impl;

import com.sb.solutions.api.eligibility.answer.entity.Answer;
import com.sb.solutions.api.eligibility.applicant.entity.Applicant;
import com.sb.solutions.api.eligibility.applicant.repository.ApplicantRepository;
import com.sb.solutions.api.eligibility.applicant.service.ApplicantService;
import com.sb.solutions.api.eligibility.common.EligibilityConstants;
import com.sb.solutions.api.eligibility.common.EligibilityStatus;
import com.sb.solutions.api.eligibility.document.dto.DocumentDTO;
import com.sb.solutions.api.eligibility.document.entity.SubmissionDocument;
import com.sb.solutions.api.eligibility.document.service.SubmissionDocumentService;
import com.sb.solutions.api.filestorage.service.FileStorageService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class ApplicantServiceImpl implements ApplicantService {

    private final Logger logger = LoggerFactory.getLogger(ApplicantServiceImpl.class);

    private final ApplicantRepository applicantRepository;

    private final FileStorageService fileStorageService;

    private final SubmissionDocumentService submissionDocumentService;

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
        applicant.setObtainedMarks(applicant.getAnswers().stream().map(Answer::getPoints).mapToLong(Long::valueOf).sum());
        return applicantRepository.save(applicant);
    }

    @Override
    public Page<Applicant> findAllPageable(Object t, Pageable pageable) {
        logger.debug("Retrieving a page of applicant list.");
        return applicantRepository.findAll(pageable);
    }

    @Override
    public Applicant saveDocuments(List<DocumentDTO> documents, long applicantId) {
        logger.debug("Saving documents of the applicant.");
        Applicant applicant = applicantRepository.getOne(applicantId);
        List<SubmissionDocument> submittedDocuments = new ArrayList<>();
        for (DocumentDTO document : documents) {
            String destinationPath = fileStorageService.getFilePath(EligibilityConstants.ELIGIBILITY_DIRECTORY,
                    EligibilityConstants.LOAN_CONFIG_DIRECTORY, String.valueOf(applicant.getLoanConfig().getId()),
                    EligibilityConstants.APPLICANT_DIRECTORY,
                    String.valueOf(applicant.getId()));
            String imageUrl = "/applicant-documents/" + fileStorageService.storeDocument(document.getImage(),
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
