package com.sb.solutions.web.customerGeneralDocument.v1;

import com.google.common.base.Preconditions;
import com.sb.solutions.api.document.service.DocumentService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sb.solutions.api.customer.entity.CustomerGeneralDocument;
import com.sb.solutions.api.customer.service.CustomerGeneralDocumentService;
import com.sb.solutions.api.document.entity.Document;
import com.sb.solutions.api.user.service.UserService;
import com.sb.solutions.core.constant.UploadDir;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.utils.PathBuilder;
import com.sb.solutions.core.utils.file.FileUploadUtils;
import com.sb.solutions.web.customerInfo.v1.CustomerInfoController;

/**
 * @author : Rujan Maharjan on  8/25/2020
 **/

@RestController
@RequestMapping(CustomerGeneralDocumentController.URL)
public class CustomerGeneralDocumentController {

    public static final String URL = "/v1/customer-general-document";
    private final Logger logger = LoggerFactory.getLogger(CustomerInfoController.class);

    private final CustomerGeneralDocumentService customerGeneralDocumentService;
    private final UserService userService;
    private final DocumentService documentService;

    public CustomerGeneralDocumentController(
            CustomerGeneralDocumentService customerGeneralDocumentService,
            UserService userService, DocumentService documentService) {
        this.customerGeneralDocumentService = customerGeneralDocumentService;
        this.userService = userService;
        this.documentService = documentService;
    }

    @PostMapping
    public ResponseEntity save(@RequestBody CustomerGeneralDocument customerGeneralDocument) {
        return new RestResponseDto()
            .successModel(customerGeneralDocumentService.save(customerGeneralDocument));
    }

    @PostMapping("/upload-document")
    public ResponseEntity<?> uploadPhoto(@RequestParam("file") MultipartFile multipartFile,
        @RequestParam("customerName") String name,
        @RequestParam("documentName") String documentName,
        @RequestParam("documentId") String documentId,
        @RequestParam("customerInfoId") String id,
        @RequestParam("customerType") String customerType
    ) {
        Long customerInfoId = Long.valueOf(id);
        Long docId = Long.valueOf(documentId);
        CustomerGeneralDocument customerGeneralDocument = customerGeneralDocumentService.findByCustomerInfoIdAndDocumentId(customerInfoId, docId);
        if (customerGeneralDocument == null) {
            customerGeneralDocument = new CustomerGeneralDocument();
            Document document = documentService.findOne(docId);
            customerGeneralDocument.setDocument(document);
            customerGeneralDocument.setCustomerInfoId(customerInfoId);
        }
        else {
            int version = customerGeneralDocument.getVersion();
            customerGeneralDocument.setVersion(version + 1);
        }
        Long branchId = userService.getAuthenticatedUser().getBranch().get(0).getId();
        Preconditions.checkNotNull(name.equals("undefined") || name.equals("null") ? null
            : (StringUtils.isEmpty(name) ? null : name), "Customer Name "
            + "is required to upload file.");

        String basePath = new PathBuilder(UploadDir.initialDocument)
            .buildCustomerInfoBasePathWithId(customerInfoId, branchId, customerType);
        String uploadPath = new StringBuilder(basePath)
            .append("generalDoc")
            .append("/")
            .toString();
        logger.info("File Upload Path {}", uploadPath);
        ResponseEntity<?> responseEntity = FileUploadUtils
            .uploadFile(multipartFile, uploadPath, documentName);
        customerGeneralDocument
            .setDocPath(((RestResponseDto) responseEntity.getBody()).getDetail().toString());
        customerGeneralDocumentService.save(customerGeneralDocument);
        return new RestResponseDto().successModel(customerGeneralDocument);
    }

    @GetMapping(value = "/{customerInfoID}")
    public ResponseEntity save(@PathVariable Long customerInfoID) {
        return new RestResponseDto()
            .successModel(customerGeneralDocumentService.findByCustomerInfoId(customerInfoID));
    }

    @PostMapping("/delete-document/{id}/{customerInfoId}")
    public ResponseEntity deleteDocument(@RequestBody String path, @PathVariable Long id,
        @PathVariable Long customerInfoId) {
        return new RestResponseDto()
            .successModel(customerGeneralDocumentService.deleteByDocId(id, customerInfoId, path));
    }
}
