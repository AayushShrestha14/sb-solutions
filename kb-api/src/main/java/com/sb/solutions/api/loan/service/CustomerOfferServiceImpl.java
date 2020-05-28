package com.sb.solutions.api.loan.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.base.Preconditions;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sb.solutions.api.loan.OfferLetterStage;
import com.sb.solutions.api.loan.dto.CustomerOfferLetterDto;
import com.sb.solutions.api.loan.entity.CustomerLoan;
import com.sb.solutions.api.loan.entity.CustomerOfferLetter;
import com.sb.solutions.api.loan.entity.CustomerOfferLetterPath;
import com.sb.solutions.api.loan.repository.CustomerLoanRepository;
import com.sb.solutions.api.loan.repository.CustomerOfferRepository;
import com.sb.solutions.api.loan.repository.specification.CustomerLoanOfferSpecBuilder;
import com.sb.solutions.api.loan.repository.specification.CustomerLoanSpecBuilder;
import com.sb.solutions.api.offerLetter.entity.OfferLetter;
import com.sb.solutions.api.offerLetter.repository.OfferLetterRepository;
import com.sb.solutions.api.user.entity.User;
import com.sb.solutions.api.user.service.UserService;
import com.sb.solutions.core.constant.UploadDir;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.enums.DocAction;
import com.sb.solutions.core.enums.DocStatus;
import com.sb.solutions.core.exception.ServiceValidationException;
import com.sb.solutions.core.utils.PathBuilder;
import com.sb.solutions.core.utils.file.FileUploadUtils;

@Service
public class CustomerOfferServiceImpl implements CustomerOfferService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerOfferService.class);

    private CustomerOfferRepository customerOfferRepository;

    private CustomerLoanRepository customerRepository;

    private UserService userService;

    private OfferLetterRepository offerLetterRepository;

    public CustomerOfferServiceImpl(
        @Autowired CustomerOfferRepository customerOfferRepository,
        @Autowired CustomerLoanRepository customerRepository,
        @Autowired UserService userService,
        @Autowired OfferLetterRepository offerLetterRepository) {
        this.customerOfferRepository = customerOfferRepository;
        this.customerRepository = customerRepository;
        this.userService = userService;
        this.offerLetterRepository = offerLetterRepository;
    }

    @Override
    public List<CustomerOfferLetter> findAll() {
        return null;
    }

    @Override
    public CustomerOfferLetter findOne(Long id) {
        return customerOfferRepository.getOne(id);
    }

    @Override
    public CustomerOfferLetter save(CustomerOfferLetter customerOfferLetter) {
        Preconditions
            .checkNotNull(customerOfferLetter.getCustomerLoan(), "Customer Cannot be empty");
        if (customerOfferLetter.getId() == null) {
            customerOfferLetter.setOfferLetterStage(this.initStage());
        }
        if (customerOfferLetter.getCustomerOfferLetterPath().isEmpty()) {
            logger.error("customer offer letter path is empty", customerOfferLetter);
            throw new ServiceValidationException(
                "Cannot perform task please fill offer letter to save");
        }

        CustomerOfferLetter customerOfferLetter1 = customerOfferRepository
            .save(customerOfferLetter);
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
    public CustomerOfferLetter findByCustomerLoanId(Long id) {
        return customerOfferRepository.findByCustomerLoanId(id);

    }

    @Override
    public CustomerOfferLetter action(CustomerOfferLetter customerOfferLetter) {

        return customerOfferRepository.save(customerOfferLetter);
    }

    @Override
    public Page<CustomerLoan> getIssuedOfferLetter(Object searchDto, Pageable pageable) {
        final ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> s = objectMapper.convertValue(searchDto, Map.class);
        String branchAccess = userService.getRoleAccessFilterByBranch().stream()
            .map(Object::toString).collect(Collectors.joining(","));
        if (s.containsKey("branchIds")) {
            branchAccess = s.get("branchIds");
        }
        s.put("branchIds", branchAccess);
        s.put("documentStatus", DocStatus.APPROVED.name());

        final CustomerLoanSpecBuilder customerLoanSpecBuilder = new CustomerLoanSpecBuilder(s);
        final Specification<CustomerLoan> loanSpecification = customerLoanSpecBuilder.build();

        Page customerLoanPage = customerRepository.findAll(loanSpecification, pageable);

        s.put("currentOfferLetterStage", String.valueOf(userService.getAuthenticatedUser().getId()));
        final CustomerLoanOfferSpecBuilder customerLoanOfferSpecBuilder = new CustomerLoanOfferSpecBuilder(
            s);
        final Specification<CustomerOfferLetter> specification = customerLoanOfferSpecBuilder
            .build();

        Page customerOfferLetterPage = customerOfferRepository.findAll(specification, pageable);

        List<CustomerOfferLetter> customerOfferLetterPageContent = customerOfferLetterPage
            .getContent();
        List<CustomerLoan> customerLoanList = customerLoanPage.getContent();
        List<CustomerLoan> tempLoan = new ArrayList<>();
        customerLoanList.forEach(c -> {
            for (CustomerOfferLetter customerOfferLetter : customerOfferLetterPageContent) {
                CustomerOfferLetterDto customerOfferLetterDto = new CustomerOfferLetterDto();
                if (customerOfferLetter.getCustomerLoan().getId() == c.getId()) {
                    BeanUtils.copyProperties(customerOfferLetter, customerOfferLetterDto);
                    customerOfferLetterDto.setId(customerOfferLetter.getId());
                    c.setCustomerOfferLetter(customerOfferLetterDto);
                    c.setUploadedOfferLetterStat(
                        customerOfferLetter.getCustomerOfferLetterPath().size());
                }
            }
            c.setOfferLetterStat(c.getLoan().getOfferLetters().size());
            tempLoan.add(c);
        });

        Page tempPage = new PageImpl(tempLoan, pageable, tempLoan.size());
        return tempPage;
    }


    @Override
    public CustomerOfferLetter saveWithMultipartFile(MultipartFile multipartFile,
        Long customerLoanId, Long offerLetterId) {
        final CustomerLoan customerLoan = customerRepository.getOne(customerLoanId);
        final OfferLetter offerLetter = offerLetterRepository.getOne(offerLetterId);
        CustomerOfferLetter customerOfferLetter = this.customerOfferRepository
            .findByCustomerLoanId(customerLoanId);
        if (customerOfferLetter == null) {
            logger.info("Please select a offer letter and save before uploading file {}",
                customerLoan);
            throw new ServiceValidationException(
                "Please select a offer letter and save before uploading file");
        }
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

        uploadPath = new StringBuilder()
            .append(uploadPath)
            .append("offer-letter/").toString();

        final StringBuilder nameBuilder = new StringBuilder().append(action).append("-")
            .append(customerLoan.getBranch().getName()).append("-")
            .append(offerLetter.getName());
        logger.info("File Upload Path offer letter {}", uploadPath, nameBuilder);
        ResponseEntity responseEntity = FileUploadUtils
            .uploadFile(multipartFile, uploadPath, nameBuilder.toString());
        if (customerOfferLetter.getId() == null) {
            customerOfferLetter.setOfferLetterStage(this.initStage());
        }
        RestResponseDto restResponseDto = (RestResponseDto) responseEntity.getBody();
        customerOfferLetter.setDocStatus(DocStatus.PENDING);
        List<CustomerOfferLetterPath> customerOfferLetterPathList = customerOfferLetter
            .getCustomerOfferLetterPath();

        if (customerOfferLetterPathList.isEmpty()) {
            CustomerOfferLetterPath customerOfferLetterPath = new CustomerOfferLetterPath();
            customerOfferLetterPath.setPath(restResponseDto.getDetail().toString());
            customerOfferLetterPathList.add(customerOfferLetterPath);
            customerOfferLetter.setCustomerOfferLetterPath(customerOfferLetterPathList);
        } else {
            for (CustomerOfferLetterPath c : customerOfferLetterPathList) {
                if (c.getOfferLetter().getId().equals(offerLetterId)) {
                    c.setPath(restResponseDto.getDetail().toString());
                    break;
                }
            }
            customerOfferLetter.setCustomerOfferLetterPath(customerOfferLetterPathList);
        }

        customerOfferLetter.setIsOfferLetterIssued(true);

        return customerOfferRepository
            .save(customerOfferLetter);


    }


    private OfferLetterStage initStage() {
        User user = userService.getAuthenticatedUser();
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
