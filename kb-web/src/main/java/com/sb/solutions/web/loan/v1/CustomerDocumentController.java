package com.sb.solutions.web.loan.v1;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sb.solutions.api.user.service.UserService;
import com.sb.solutions.core.constant.UploadDir;
import com.sb.solutions.core.utils.PathBuilder;
import com.sb.solutions.core.utils.file.FileUploadUtils;

/**
 * @author yunish on 11/5/2019
 */
@RestController
@RequestMapping(CustomerDocumentController.URL)
public class CustomerDocumentController {

    static final String URL = "/v1/customer-document";

    private static final Logger logger = LoggerFactory.getLogger(CustomerDocumentController.class);

    private final UserService userService;

    public CustomerDocumentController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/uploadFile")
    public ResponseEntity<?> uploadLoanFile(@RequestParam("file") MultipartFile multipartFile,
        @RequestParam("type") String type,
        @RequestParam("citizenNumber") String citizenNumber,
        @RequestParam("customerName") String name,
        @RequestParam("documentName") String documentName,
        @RequestParam(name = "action", required = false, defaultValue = "new") String action) {

        String branchName = userService.getAuthenticated().getBranch().get(0).getName()
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
        return FileUploadUtils
            .uploadFile(multipartFile, uploadPath, documentName);

    }
}
