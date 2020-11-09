package com.sb.solutions.api.loan.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.google.common.base.Preconditions;
import com.sb.solutions.api.authorization.approval.ApprovalRoleHierarchyService;
import com.sb.solutions.core.enums.RoleType;
import com.sb.solutions.core.enums.Status;
import com.sb.solutions.core.utils.ApprovalType;
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
import org.springframework.util.ObjectUtils;
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

    private final CustomerOfferRepository customerOfferRepository;

    private final CustomerLoanRepository customerLoanRepository;

    private final UserService userService;

    private final OfferLetterRepository offerLetterRepository;
    private final ApprovalRoleHierarchyService approvalRoleHierarchyService;

    public CustomerOfferServiceImpl(
            @Autowired CustomerOfferRepository customerOfferRepository,
            @Autowired CustomerLoanRepository customerLoanRepository,
            @Autowired UserService userService,
            @Autowired OfferLetterRepository offerLetterRepository, ApprovalRoleHierarchyService approvalRoleHierarchyService) {
        this.customerOfferRepository = customerOfferRepository;
        this.customerLoanRepository = customerLoanRepository;
        this.userService = userService;
        this.offerLetterRepository = offerLetterRepository;
        this.approvalRoleHierarchyService = approvalRoleHierarchyService;
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
            logger.error("customer offer letter path is empty {}", customerOfferLetter);
            throw new ServiceValidationException(
                    "Cannot perform task please fill offer letter to save");
        }

        return customerOfferRepository
                .save(customerOfferLetter);


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

        Page<CustomerLoan> customerLoanPage = customerLoanRepository.findAll(loanSpecification, pageable);

        List<CustomerLoan> tempLoan = new ArrayList<>();
        List<CustomerLoan> customerLoanList = customerLoanPage.getContent();
        if (!customerLoanList.isEmpty()) {
            final List<CustomerOfferLetter> customerOfferLetterContent = customerOfferRepository.findCustomerOfferLetterByCustomerLoanIn(customerLoanList);

            customerLoanList.forEach(c -> {
                for (CustomerOfferLetter customerOfferLetter : customerOfferLetterContent) {
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
        }
        Page tempPage = new PageImpl(tempLoan, pageable, customerLoanPage.getTotalElements());
        return tempPage;
    }


    @Override
    public CustomerOfferLetter saveWithMultipartFile(MultipartFile multipartFile,
                                                     Long customerLoanId, Long offerLetterId) {
        final CustomerLoan customerLoan = customerLoanRepository.getOne(customerLoanId);
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
        logger.info("File Upload Path offer letter {} {}", uploadPath, nameBuilder);
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

    @Override
    public CustomerOfferLetter assignOfferLetter(Long customerLoanId, Long userId, Long roleId) {
        OfferLetterStage offerLetterStageAssigned = assignStage(userId, roleId);
        CustomerOfferLetter customerOfferLetter = customerOfferRepository.findByCustomerLoanId(customerLoanId);
        if (ObjectUtils.isEmpty(customerOfferLetter)) {
            customerOfferLetter = new CustomerOfferLetter();
            CustomerLoan customerLoan = new CustomerLoan();
            customerLoan.setId(customerLoanId);
            customerOfferLetter.setCustomerLoan(customerLoan);
        } else {
            final OfferLetterStage offerLetterStage = customerOfferLetter.getOfferLetterStage();

            com.fasterxml.jackson.databind.ObjectMapper objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
            objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
            List previousList = customerOfferLetter.getPreviousList();
            List previousListTemp = new ArrayList();

            if (offerLetterStage != null) {

                Map<String, String> tempLoanStage = objectMapper
                        .convertValue(offerLetterStage, Map.class);
                try {
                    previousList.forEach(p -> {
                        try {
                            Map<String, String> previous = objectMapper.convertValue(p, Map.class);

                            previousListTemp.add(objectMapper.writeValueAsString(previous));
                        } catch (JsonProcessingException e) {
                            logger.error("Failed to handle JSON data {}", e.getMessage());
                            throw new RuntimeException("Failed to handle JSON data");
                        }
                    });
                    String jsonValue = objectMapper.writeValueAsString(tempLoanStage);
                    previousListTemp.add(jsonValue);
                } catch (JsonProcessingException e) {
                    logger.error("Failed to Get Stage data {}", e.getMessage());
                    throw new RuntimeException("Failed to Get Stage data");
                }
            }
            customerOfferLetter.setDocStatus(DocStatus.PENDING);
            customerOfferLetter.setOfferLetterStageList(previousListTemp.toString());

            final User user = userService.getAuthenticatedUser();
            final User toUser = userService.findOne(userId);
            offerLetterStage.setDocAction(DocAction.ASSIGNED);
            offerLetterStage.setFromRole(user.getRole());
            offerLetterStage.setFromUser(user);
            offerLetterStage.setToRole(toUser.getRole());
            offerLetterStage.setToUser(toUser);
            offerLetterStage.setComment("Assigned");
            customerOfferLetter.setOfferLetterStage(offerLetterStage);
        }

        Preconditions
                .checkNotNull(customerOfferLetter.getCustomerLoan(), "Customer Cannot be empty");
        if (customerOfferLetter.getId() == null) {
            customerOfferLetter.setOfferLetterStage(offerLetterStageAssigned);
        }
        return customerOfferRepository
                .save(customerOfferLetter);
    }

    @Override
    public Page<CustomerOfferLetter> getAssignedOfferLetter(Object searchDto, Pageable pageable) {
        final ObjectMapper objectMapper = new ObjectMapper();
        User currentUser = userService.getAuthenticatedUser();
        Map<String, String> s = objectMapper.convertValue(searchDto, Map.class);
        String branchAccess = userService.getRoleAccessFilterByBranch().stream()
                .map(Object::toString).collect(Collectors.joining(","));
        if (s.containsKey("branchIds")) {
            branchAccess = s.get("branchIds");
        }
        s.put("branchIds", branchAccess);
        s.put("toUser", String.valueOf(currentUser.getId()));
        s.put("toRole", String.valueOf(currentUser.getRole().getId()));
        final CustomerLoanOfferSpecBuilder customerLoanOfferSpecBuilder = new CustomerLoanOfferSpecBuilder(
                s);
        final Specification<CustomerOfferLetter> specification = customerLoanOfferSpecBuilder
                .build();

        return customerOfferRepository.findAll(specification, pageable);
    }

    @Override
    public Map<String, Object> userPostApprovalDocStat() {
        Map<String, Object> map = new HashMap<>();
        final User user = userService.getAuthenticatedUser();
        if (user.getRole().getRoleType().equals(RoleType.CAD_ADMIN)) {
            final Long count = customerLoanRepository.countCustomerLoanByDocumentStatus(DocStatus.APPROVED);
            map.put("docCount", count);
            map.put("show", true);
        } else {
            boolean isUserExistInCad = approvalRoleHierarchyService.checkRoleContainInHierarchies(user.getRole().getId(), ApprovalType.CAD, 0l);
            if (isUserExistInCad) {
                final Long count = customerOfferRepository
                        .countCustomerOfferLetterByOfferLetterStageToUserIdAndOfferLetterStageToRoleId
                                (user.getId(), user.getRole().getId());

                map.put("docCount", count);
            }
            map.put("show", isUserExistInCad);

        }
        return map;
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

    private OfferLetterStage assignStage(Long userId, Long roleId) {
        User user = userService.getAuthenticatedUser();
        final OfferLetterStage offerLetterStage = new OfferLetterStage();
        User toUser = userService.findOne(userId);

        offerLetterStage.setToRole(toUser.getRole());
        offerLetterStage.setToUser(toUser);
        offerLetterStage.setFromUser(user);
        offerLetterStage.setFromRole(user.getRole());

        offerLetterStage.setComment("Assigned to " + toUser.getUsername());
        offerLetterStage.setDocAction(DocAction.ASSIGNED);
        return offerLetterStage;
    }


}
