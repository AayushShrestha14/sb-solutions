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

import com.sb.solutions.api.loan.entity.CustomerLoan;
import com.sb.solutions.api.loan.entity.CustomerOfferLetter;
import com.sb.solutions.api.loan.service.CustomerLoanService;
import com.sb.solutions.api.loan.service.CustomerOfferService;
import com.sb.solutions.api.user.service.UserService;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.web.common.stage.dto.StageDto;
import com.sb.solutions.web.common.stage.mapper.OfferLetterStageMapper;


@RestController
@RequestMapping(CustomerOfferController.URL)
public class CustomerOfferController {

    static final String URL = "/v1/customer-offer-letter";

    private static final Logger logger = LoggerFactory.getLogger(CustomerLoanController.class);

    private CustomerOfferService customerOfferService;
    private CustomerLoanService customerLoanService;

    private OfferLetterStageMapper offerLetterStageMapper;

    private UserService userService;

    public CustomerOfferController(
        @Autowired CustomerOfferService customerOfferService,
        @Autowired CustomerLoanService customerLoanService,
        @Autowired UserService userService,
        @Autowired OfferLetterStageMapper offerLetterStageMapper) {
        this.customerOfferService = customerOfferService;
        this.customerLoanService = customerLoanService;
        this.userService = userService;
        this.offerLetterStageMapper = offerLetterStageMapper;
    }

    @PostMapping(path = "/uploadFile")
    public ResponseEntity<?> uploadOfferLetter(@RequestParam("file") MultipartFile multipartFile,
        @RequestParam("id") Long customerLoanId) {
        logger.info("uploading offer letter{}", multipartFile.getOriginalFilename());
        return new RestResponseDto().successModel(
            customerOfferService.saveWithMultipartFile(multipartFile, customerLoanId));
    }

    @PostMapping(path = "/action")
    public ResponseEntity<?> OfferLetterAction(@RequestBody StageDto stageDto) {
        final CustomerLoan customerLoan =  customerLoanService.findOne(stageDto.getCustomerLoanId());
        final CustomerOfferLetter customerOfferLetter = offerLetterStageMapper
            .actionMapper(stageDto,
              customerLoan.getCustomerOfferLetter(),
                userService.getAuthenticated(),customerLoan.getBranch().getId());
        return new RestResponseDto().successModel(customerOfferService.action(customerOfferLetter));
    }

    @PostMapping(path = "/save")
    public ResponseEntity<?> initialSave(@RequestBody CustomerOfferLetter customerOfferLetter) {
        return new RestResponseDto().successModel(
            customerOfferService.save(customerOfferLetter));
    }

}
