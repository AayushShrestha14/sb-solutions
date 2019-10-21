package com.sb.solutions.api.loan.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.sb.solutions.api.loan.entity.CustomerOfferLetter;
import com.sb.solutions.core.service.BaseService;

public interface CustomerOfferService extends BaseService<CustomerOfferLetter> {

    List<CustomerOfferLetter> findByCustomerLoanId(Long id);

    CustomerOfferLetter action(CustomerOfferLetter stageDto);

    CustomerOfferLetter saveWithMultipartFile(MultipartFile multipartFile,Long customerLoanId);

}
