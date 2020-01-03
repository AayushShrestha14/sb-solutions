package com.sb.solutions.web.loan.v1;

import java.text.ParseException;
import javax.validation.Valid;

import com.google.common.base.Preconditions;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sb.solutions.api.document.entity.Document;
import com.sb.solutions.api.loan.entity.CustomerDocument;
import com.sb.solutions.api.loan.entity.CustomerLoan;
import com.sb.solutions.api.loan.service.CustomerLoanService;
import com.sb.solutions.api.user.service.UserService;
import com.sb.solutions.core.constant.UploadDir;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.utils.PaginationUtils;
import com.sb.solutions.core.utils.PathBuilder;
import com.sb.solutions.core.utils.file.FileUploadUtils;
import com.sb.solutions.web.common.stage.dto.StageDto;
import com.sb.solutions.web.loan.v1.mapper.Mapper;

/**
 * @author Rujan Maharjan on 5/10/2019
 */

@RestController
@RequestMapping(CustomerLoanController.URL)
public class CustomerLoanController {

    static final String URL = "/v1/Loan-customer";

    private static final Logger logger = LoggerFactory.getLogger(CustomerLoanController.class);

    private final CustomerLoanService service;

    private final UserService userService;

    private final Mapper mapper;

    public CustomerLoanController(
        @Autowired CustomerLoanService service,
        @Autowired Mapper mapper,
        @Autowired UserService userService) {

        this.service = service;
        this.mapper = mapper;
        this.userService = userService;
    }

    @PostMapping(value = "/action")
    public ResponseEntity<?> loanAction(@Valid @RequestBody StageDto actionDto) {
        final CustomerLoan c = mapper
            .actionMapper(actionDto, service.findOne(actionDto.getCustomerLoanId()),
                userService.getAuthenticatedUser());
        service.sendForwardBackwardLoan(c);
        return new RestResponseDto().successModel(actionDto);
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody CustomerLoan customerLoan,
        BindingResult bindingResult) {

        logger.debug("saving Customer Loan {}", customerLoan);

        return new RestResponseDto().successModel(service.save(customerLoan));
    }

    @PostMapping("/close-renew-customer-loan")
    public ResponseEntity<?> closeRenew(@Valid @RequestBody CustomerLoan customerLoan) {

        logger.debug("saving Customer Loan {}", customerLoan);

        return new RestResponseDto().successModel(service.renewCloseEntity(customerLoan));
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        return new RestResponseDto().successModel(service.findOne(id));
    }


    @GetMapping("/{id}/delete")
    public ResponseEntity<?> delByIdRoleMaker(@PathVariable("id") Long id) {
        logger.info("deleting Customer Loan {}", id);
        return new RestResponseDto()
            .successModel(service.delCustomerLoan(id));
    }

    @PostMapping("/status")
    public ResponseEntity<?> getfirst5ByDocStatus(@RequestBody CustomerLoan customerLoan) {
        logger.debug("getByDocStatus Customer Loan {}", customerLoan);
        return new RestResponseDto().successModel(
            service.getFirst5CustomerLoanByDocumentStatus(customerLoan.getDocumentStatus()));
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
            value = "Results page you want to retrieve (0..N)"),
        @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
            value = "Number of records per page.")})
    @PostMapping(value = "/list")
    public ResponseEntity<?> getAllByPagination(@RequestBody Object searchDto,
        @RequestParam("page") int page, @RequestParam("size") int size) {
        return new RestResponseDto()
            .successModel(service.findAllPageable(searchDto, PaginationUtils.pageable(page, size)));
    }

    @GetMapping(value = "/statusCount")
    public ResponseEntity<?> countLoanStatus() {
        return new RestResponseDto().successModel(service.statusCount());
    }

    @GetMapping(value = "/proposed-amount")
    public ResponseEntity<?> getProposedAmount(@RequestParam(required = false) String startDate,
        @RequestParam(required = false) String endDate) throws ParseException {
        return new RestResponseDto().successModel(service.proposedAmount(startDate, endDate));
    }

    @GetMapping(value = "/loan-amount/{id}")
    public ResponseEntity<?> getProposedAmountByBranch(@PathVariable Long id,
        @RequestParam(required = false) String startDate,
        @RequestParam(required = false) String endDate) throws ParseException {
        return new RestResponseDto().successModel(service.proposedAmountByBranch(id, startDate,
            endDate));
    }

    @GetMapping(value = "/searchByCitizenship/{number}")
    public ResponseEntity<?> getLoansByCitizenship(
        @PathVariable("number") String citizenshipNumber) {
        logger.info("GET:/searchByCitizenship/{}", citizenshipNumber);
        return new RestResponseDto()
            .successModel(service.getByCitizenshipNumber(citizenshipNumber));
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
            value = "Results page you want to retrieve (0..N)"),
        @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
            value = "Number of records per page.")})
    @PostMapping(value = "/catalogue")
    public ResponseEntity<?> getCatalogues(@RequestBody Object searchDto,
        @RequestParam("page") int page, @RequestParam("size") int size) {
        return new RestResponseDto()
            .successModel(service.getCatalogues(searchDto, PaginationUtils.pageable(page, size)));
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
            value = "Results page you want to retrieve (0..N)"),
        @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
            value = "Number of records per page.")})
    @PostMapping(value = "/committee-pull")
    public ResponseEntity<?> getCommitteePull(@RequestBody Object searchDto,
        @RequestParam("page") int page, @RequestParam("size") int size) {
        return new RestResponseDto()
            .successModel(
                service.getCommitteePull(searchDto, PaginationUtils.pageable(page, size)));
    }

    @GetMapping(path = "/stats")
    public final ResponseEntity<?> getStats(@RequestParam(value = "branchId") Long branchId,
        @RequestParam(required = false) String startDate,
        @RequestParam(required = false) String endDate) throws ParseException {
        logger.debug("REST request to get the statistical data about the loans.");
        return new RestResponseDto().successModel(mapper.toBarchartDto(service.getStats(branchId,
            startDate, endDate)));
    }

    @GetMapping(path = "/check-user-customer-loan/{id}")
    public ResponseEntity<?> chkUserContainCustomerLoan(@PathVariable Long id) {
        logger.debug("REST request to get the check data about the user.");
        return new RestResponseDto().successModel(service.chkUserContainCustomerLoan(id));
    }

    @RequestMapping(method = RequestMethod.POST, path = "/csv")
    public ResponseEntity<?> csv(@RequestBody Object searchDto) {
        return new RestResponseDto().successModel(service.csv(searchDto));
    }

    @PostMapping("/uploadFile")
    public ResponseEntity<?> uploadLoanFile(@RequestParam("file") MultipartFile multipartFile,
        @RequestParam("type") String type,
        @RequestParam("citizenNumber") String citizenNumber,
        @RequestParam("customerName") String name,
        @RequestParam("documentName") String documentName,
        @RequestParam("documentId") Long documentId,
        @RequestParam(name = "action", required = false, defaultValue = "new") String action) {

        CustomerDocument customerDocument = new CustomerDocument();
        Document document = new Document();
        document.setId(documentId);
        customerDocument.setDocument(document);
        String branchName = userService.getAuthenticatedUser().getBranch().get(0).getName()
            .replace(" ", "_");
        Preconditions.checkNotNull(citizenNumber.equals("null") ? null
                : (StringUtils.isEmpty(citizenNumber) ? null : citizenNumber),
            "Citizenship Number is required to upload file.");
        Preconditions.checkNotNull(name.equals("undefined") || name.equals("null") ? null
            : (StringUtils.isEmpty(name) ? null : name), "Customer Name "
            + "is required to upload file.");
        String uploadPath = new PathBuilder(UploadDir.initialDocument).withAction(action)
            .isJsonPath(false).withBranch(branchName).withCitizenship(citizenNumber)
            .withCustomerName(name).withLoanType(type).build();
        logger.info("File Upload Path {}", uploadPath);
        ResponseEntity<?> responseEntity = FileUploadUtils
            .uploadFile(multipartFile, uploadPath, documentName);
        customerDocument
            .setDocumentPath(((RestResponseDto) responseEntity.getBody()).getDetail().toString());
        return new RestResponseDto().successModel(customerDocument);
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
            value = "Results page you want to retrieve (0..N)"),
        @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
            value = "Number of records per page.")})
    @PostMapping(value = "/issue-offer-letter")
    public ResponseEntity<?> getIssuedOfferLetter(@RequestBody Object searchDto,
        @RequestParam("page") int page, @RequestParam("size") int size) {
        return new RestResponseDto()
            .successModel(
                service.getIssuedOfferLetter(searchDto, PaginationUtils.pageable(page, size)));
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<?> getLoanByCustomerId(@PathVariable("id") Long id) {
        logger.info("getting Customer Loan {}", id);
        return new RestResponseDto()
            .successModel(service.getLoanByCustomerId(id));
    }

}
