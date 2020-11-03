package com.sb.solutions.web.customerInfo.v1;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.google.common.base.Preconditions;
import com.sb.solutions.core.exception.ServiceValidationException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sb.solutions.api.customer.entity.CustomerGeneralDocument;
import com.sb.solutions.api.customer.entity.CustomerInfo;
import com.sb.solutions.api.customer.service.CustomerGeneralDocumentService;
import com.sb.solutions.api.customer.service.CustomerInfoService;
import com.sb.solutions.api.customerActivity.aop.Activity;
import com.sb.solutions.api.customerActivity.aop.CustomerActivityLog;
import com.sb.solutions.api.user.service.UserService;
import com.sb.solutions.core.constant.UploadDir;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.utils.PaginationUtils;
import com.sb.solutions.core.utils.PathBuilder;
import com.sb.solutions.core.utils.file.FileUploadUtils;
import com.sb.solutions.core.utils.string.StringUtil;

/**
 * @author : Rujan Maharjan on  8/10/2020
 **/
@RestController
@RequestMapping(CustomerInfoController.URL)
public class CustomerInfoController {

    public static final String URL = "/v1/customer-info";
    private final Logger logger = LoggerFactory.getLogger(CustomerInfoController.class);
    private final CustomerInfoService customerInfoService;
    private final CustomerGeneralDocumentService customerGeneralDocumentService;
    private final UserService userService;


    @Autowired
    public CustomerInfoController(
            CustomerInfoService customerInfoService,
            CustomerGeneralDocumentService customerGeneralDocumentService,
            UserService userService) {
        this.customerInfoService = customerInfoService;

        this.customerGeneralDocumentService = customerGeneralDocumentService;
        this.userService = userService;

    }

    @PostMapping("/list")
    public ResponseEntity<?> getPageable(@RequestBody Map<String, String> searchDto,
                                         @RequestParam("page") int page, @RequestParam("size") int size) {
        return new RestResponseDto()
                .successModel(customerInfoService.findPageableBySpec(searchDto, PaginationUtils
                        .pageable(page, size)));
    }

    @CustomerActivityLog(Activity.CUSTOMER_UPDATE)
    @PostMapping("/{customerInfoId}/{template}")
    public ResponseEntity<?> saveCustomerLoanInfo(@RequestBody Object loanInfo,
                                                  @PathVariable("customerInfoId") Long customerInfoId,
                                                  @PathVariable("template") String template) {
        return new RestResponseDto()
                .successModel(
                        customerInfoService.saveLoanInformation(loanInfo, customerInfoId, template));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerInfoByID(
            @PathVariable("id") Long id) {
        CustomerInfo customerInfo = customerInfoService.findOne(id).get();
        List<CustomerGeneralDocument> generalDocuments = customerGeneralDocumentService
                .findByCustomerInfoId(id);
        if (!generalDocuments.isEmpty()) {
            customerInfo.setCustomerGeneralDocuments(generalDocuments);
        }
        return new RestResponseDto()
                .successModel(customerInfo);
    }

    @PostMapping("/customer")
    public ResponseEntity getCustomerByTypeIdNumberIdTypeRegDate(
            @RequestBody CustomerInfo customerInfo) {
        final CustomerInfo customerInfo1 = customerInfoService
                .findByCustomerTypeAndIdNumberAndIdRegPlaceAndIdTypeAndIdRegDate(
                        customerInfo.getCustomerType(), customerInfo.getIdNumber(),
                        customerInfo.getIdRegPlace(), customerInfo.getIdType(),
                        customerInfo.getIdRegDate());
        if (ObjectUtils.isEmpty(customerInfo1)) {
            return new RestResponseDto().failureModel("no Customer Found");
        }
        return new RestResponseDto().successModel(
                customerInfo1
        );
    }

    @PostMapping("/upload-photo")
    public ResponseEntity profilePicUploader(
            @RequestParam("file") MultipartFile multipartFile,
            @RequestParam("customerInfoId") Long customerInfoId,
            @RequestParam("name") String name,
            @RequestParam("branch") String branch,
            @RequestParam("customerType") String customerType) {
        Preconditions.checkNotNull(name.equals("null") ? null
                        : (StringUtils.isEmpty(name) ? null : name),
                "Customer Name is required to upload file.");
        Preconditions.checkNotNull(branch.equals("null") ? null
                        : (StringUtils.isEmpty(branch) ? null : branch),
                "Branch Name is required to upload file.");
        LocalDateTime currentDateTime = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm");

        String formattedDateTime = currentDateTime.format(formatter);
        final Optional<CustomerInfo> customerInfo = customerInfoService.findOne(customerInfoId);
        if (!customerInfo.isPresent()) {
            throw new ServiceValidationException("Invalid customer id");
        }
        String uploadPath = new PathBuilder(UploadDir.initialDocument)
                .buildCustomerInfoBasePathWithId(customerInfoId, customerInfo.get().getBranch().getId(),
                        customerType);

        ResponseEntity responseEntity = FileUploadUtils
                .uploadFile(multipartFile, uploadPath,
                        formattedDateTime + "-" + StringUtil
                                .getStringWithoutWhiteSpaceAndWithCapitalize(name)
                                .toLowerCase());
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            Object body = responseEntity.getBody();
            RestResponseDto restResponseDto = new RestResponseDto();
            if (!ObjectUtils.isEmpty(body)) {
                BeanUtils.copyProperties(body, restResponseDto);
                String path = (String) restResponseDto.getDetail();
                if (customerInfo.isPresent()) {
                    logger.info("updating profile picture::{}", customerInfo.get().getName());
                    CustomerInfo c = customerInfo.get();
                    c.setProfilePic(path);
                    customerInfoService.save(c);
                }
            }
        }
        return responseEntity;
    }


    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile multipartFile,
                                    @RequestParam("customerName") String name,
                                    @RequestParam("documentName") String documentName,
                                    @RequestParam("customerInfoId") Long id,
                                    @RequestParam("customerType") String customerType,
                                    @RequestParam("folderName") String folderName
    ) {
        final String branchName = userService.getAuthenticatedUser().getBranch().get(0).getName();
        Preconditions.checkNotNull(name.equals("undefined") || name.equals("null") ? null
                : (StringUtils.isEmpty(name) ? null : name), "Customer Name "
                + "is required to upload file.");
        Preconditions.checkNotNull(documentName.equals("undefined") || documentName.equals("null") ? null
                : (StringUtils.isEmpty(documentName) ? null : documentName), "Doc Name "
                + "is required to upload file.");
        documentName = documentName.replaceAll("\\\\", "-");
        documentName = documentName.replace("/", "-");

        Optional<CustomerInfo> customerInfo = customerInfoService.findOne(id);
        if (!customerInfo.isPresent()) {
            throw new ServiceValidationException("Invalid customer id");
        }
        String basicPath = new PathBuilder(UploadDir.initialDocument)
                .buildCustomerInfoBasePathWithId(id, customerInfo.get().getBranch().getId(),
                        customerType);
        String uploadPath = new StringBuilder(basicPath)
                .append(folderName)
                .append("/")
                .toString();
        logger.info("File Upload Path {}", uploadPath);
        ResponseEntity<?> responseEntity = FileUploadUtils
                .uploadFile(multipartFile, uploadPath, documentName);
        return new RestResponseDto().successModel(((RestResponseDto) responseEntity.getBody()).getDetail().toString());
    }


    @PostMapping("/csv")
    public ResponseEntity<?> getDownload(@RequestBody Object searchDto) {
        return new RestResponseDto()
                .successModel(customerInfoService.csv(searchDto));
    }
}
