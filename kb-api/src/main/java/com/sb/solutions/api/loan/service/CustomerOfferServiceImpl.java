package com.sb.solutions.api.loan.service;

import java.util.List;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sb.solutions.api.loan.OfferLetterStage;
import com.sb.solutions.api.loan.entity.CustomerLoan;
import com.sb.solutions.api.loan.entity.CustomerOfferLetter;
import com.sb.solutions.api.loan.repository.CustomerLoanRepository;
import com.sb.solutions.api.loan.repository.CustomerOfferRepository;
import com.sb.solutions.api.user.entity.User;
import com.sb.solutions.api.user.service.UserService;
import com.sb.solutions.core.constant.UploadDir;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.enums.DocAction;
import com.sb.solutions.core.enums.DocStatus;
import com.sb.solutions.core.utils.PathBuilder;
import com.sb.solutions.core.utils.file.FileUploadUtils;

@Service
public class CustomerOfferServiceImpl implements CustomerOfferService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerOfferService.class);

    private CustomerOfferRepository customerOfferRepository;

    private CustomerLoanRepository customerRepository;

    private UserService userService;

    public CustomerOfferServiceImpl(
        @Autowired CustomerOfferRepository customerOfferRepository,
        @Autowired CustomerLoanRepository customerRepository,
        @Autowired UserService userService) {
        this.customerOfferRepository = customerOfferRepository;
        this.customerRepository = customerRepository;
        this.userService = userService;
    }

    @Override
    public List<CustomerOfferLetter> findAll() {
        return null;
    }

    @Override
    public CustomerOfferLetter findOne(Long id) {
        return null;
    }

    @Override
    public CustomerOfferLetter save(CustomerOfferLetter customerOfferLetter) {
        Preconditions
            .checkNotNull(customerOfferLetter.getCustomerLoanId(), "Customer Cannot be empty");
        customerOfferLetter.setOfferLetterStage(this.initStage());
        CustomerOfferLetter customerOfferLetter1 = customerOfferRepository
            .save(customerOfferLetter);
        final CustomerLoan customerLoan = customerRepository
            .getOne(customerOfferLetter.getCustomerLoanId());
        customerLoan.setCustomerOfferLetter(customerOfferLetter1);
        customerRepository.save(customerLoan);
        return customerOfferLetter1;
    }

    @Override
    public Page<CustomerOfferLetter> findAllPageable(Object t, Pageable pageable) {
        return null;
    }

    @Override
    public List<CustomerOfferLetter> saveAll(List<CustomerOfferLetter> list) {
        return null;
    }

    @Override
    public List<CustomerOfferLetter> findByCustomerLoanId(Long id) {
        return customerOfferRepository.findByCustomerLoanId(id);

    }

    @Override
    public CustomerOfferLetter action(CustomerOfferLetter stageDto) {
        return null;
    }

    @Override
    public CustomerOfferLetter saveWithMultipartFile(MultipartFile multipartFile,
        Long customerLoanId) {
        final CustomerLoan customerLoan = customerRepository.getOne(customerLoanId);
        String action = "new";
        switch (customerLoan.getLoanType()) {
            case NEW_LOAN:
                action = "new";
                break;
            case CLOSURE_LOAN:
                action = "close";
                break;

            case RENEWED_LOAN:
                action = "renew";
                break;
            default:
        }
        String uploadPath = new PathBuilder(UploadDir.initialDocument).withAction(action)
            .isJsonPath(false).withBranch(customerLoan.getBranch().getName())
            .withCitizenship(customerLoan.getCustomerInfo().getCitizenshipNumber())
            .withCustomerName(customerLoan.getCustomerInfo().getCustomerName())
            .withLoanType(customerLoan.getLoan().getName()).build();

        uploadPath = new StringBuilder().
            append(uploadPath).
            append("offer-letter/").toString();

        final StringBuilder nameBuilder = new StringBuilder().append(action).append("-")
            .append(customerLoan.getBranch().getName()).append("-")
            .append("offer-letter");
        logger.info("File Upload Path offer letter {}", uploadPath, nameBuilder);
        ResponseEntity responseEntity = FileUploadUtils
            .uploadFile(multipartFile, uploadPath, nameBuilder.toString());
        CustomerOfferLetter customerOfferLetter = new CustomerOfferLetter();
        List<CustomerOfferLetter> customerOfferLetterList = customerOfferRepository
            .findByCustomerLoanId(customerLoanId);
        if (!customerOfferLetterList.isEmpty()) {
            for (CustomerOfferLetter c : customerOfferLetterList) {
                if (c.getPath().equalsIgnoreCase("") || c.getPath() == null) {
                    customerOfferLetter = c;
                }
            }
        }
        customerOfferLetter.setCustomerLoanId(customerLoanId);
        customerOfferLetter.setOfferLetterStage(this.initStage());
        RestResponseDto restResponseDto = (RestResponseDto) responseEntity.getBody();
        customerOfferLetter.setDocStatus(DocStatus.PENDING);
        customerOfferLetter.setPath(restResponseDto.getDetail().toString());
        customerOfferLetter.setIsOfferLetterIssued(true);
        final CustomerOfferLetter customerOfferLetter1 = customerOfferRepository
            .save(customerOfferLetter);
        customerLoan.setCustomerOfferLetter(customerOfferLetter1);
        customerRepository.save(customerLoan);
        return customerOfferLetter1;
    }


    private OfferLetterStage initStage() {
        User user = userService.getAuthenticated();
        final OfferLetterStage offerLetterStage = new OfferLetterStage();
        offerLetterStage.setFromRole(user.getRole());
        offerLetterStage.setToRole(user.getRole());
        offerLetterStage.setFromUser(user);
        offerLetterStage.setToUser(user);
        offerLetterStage.setComment("DRAFT");
        offerLetterStage.setDocAction(DocAction.DRAFT);
        return offerLetterStage;
    }
}
