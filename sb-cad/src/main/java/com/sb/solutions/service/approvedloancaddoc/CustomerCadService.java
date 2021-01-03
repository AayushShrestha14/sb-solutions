package com.sb.solutions.service.approvedloancaddoc;

import com.sb.solutions.api.loan.entity.CustomerOfferLetter;
import com.sb.solutions.core.service.BaseService;
import com.sb.solutions.entity.CustomerApprovedLoanCadDocumentation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

public interface CustomerCadService extends BaseService<CustomerApprovedLoanCadDocumentation> {
    @Transactional
    CustomerOfferLetter saveWithMultipartFile(MultipartFile multipartFile, Long customerApprovedDocId,
                                              Long offerLetterId, String type);
}
