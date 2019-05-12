package com.sb.solutions.api.eligibility.applicant.service;

import com.sb.solutions.api.eligibility.applicant.entity.Applicant;
import com.sb.solutions.api.eligibility.document.dto.DocumentDTO;
import com.sb.solutions.core.service.BaseService;

import java.util.List;

public interface ApplicantService extends BaseService<Applicant> {

    Applicant saveDocuments(List<DocumentDTO> documents, long applicantId);

}
