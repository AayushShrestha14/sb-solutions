package com.sb.solutions.web.loan.v1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sb.solutions.api.loan.entity.CustomerOfferLetter;
import com.sb.solutions.api.loan.service.CustomerOfferService;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.web.common.stage.dto.StageDto;


@RestController
@RequestMapping(CustomerOfferController.URL)
public class CustomerOfferController {

    static final String URL = "/v1/customer-offer-letter";

    private static final Logger logger = LoggerFactory.getLogger(CustomerLoanController.class);

    private CustomerOfferService customerOfferService;

    public CustomerOfferController(
        @Autowired CustomerOfferService customerOfferService) {
        this.customerOfferService = customerOfferService;
    }

    @PostMapping(path = "/uploadFile")
    public ResponseEntity<?> uploadOfferLetter(@RequestParam("file") MultipartFile multipartFile,
        @RequestParam("id") Long customerLoanId) {
        logger.info("uploading offer letter{}",multipartFile.getOriginalFilename());
        return new RestResponseDto().successModel(
            customerOfferService.saveWithMultipartFile(multipartFile, customerLoanId));
    }

    @PostMapping(path = "/action")
    public ResponseEntity<?> OfferLetterAction(@RequestBody StageDto stageDto) {
        return null;
    }

    @PostMapping(path = "/save")
    public ResponseEntity<?> initialSave(@RequestBody CustomerOfferLetter customerOfferLetter) {
        return new RestResponseDto().successModel(
            customerOfferService.save(customerOfferLetter));
    }

}
