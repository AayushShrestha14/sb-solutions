package com.sb.solutions.api.loan.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.sb.solutions.api.loan.entity.CustomerLoan;
import com.sb.solutions.api.loan.entity.CustomerOfferLetter;
import com.sb.solutions.core.service.BaseService;

public interface CustomerOfferService extends BaseService<CustomerOfferLetter> {

    CustomerOfferLetter findByCustomerLoanId(Long id);

    CustomerOfferLetter action(CustomerOfferLetter stageDto);

    Page<CustomerLoan> getIssuedOfferLetter(Object searchDto, Pageable pageable);

    CustomerOfferLetter saveWithMultipartFile(MultipartFile multipartFile, Long customerLoanId,
        Long offerLetterId);

}
