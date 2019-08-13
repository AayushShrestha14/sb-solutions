package com.sb.solutions.web.accountOpening.v1;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sb.solutions.api.openingForm.entity.OpeningCustomer;
import com.sb.solutions.api.openingForm.entity.OpeningForm;
import com.sb.solutions.api.openingForm.service.OpeningFormService;
import com.sb.solutions.core.constant.EmailConstant.Template;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.enums.AccountStatus;
import com.sb.solutions.core.utils.PaginationUtils;
import com.sb.solutions.core.utils.email.Email;
import com.sb.solutions.core.utils.email.MailSenderService;
import com.sb.solutions.core.utils.file.FileUploadUtils;

@RestController
@RequestMapping("/v1/accountOpening")
public class AccountOpeningController {

    private final OpeningFormService openingFormService;
    private final MailSenderService mailSenderService;
    private final Logger logger = LoggerFactory.getLogger(AccountOpeningController.class);
    @Value("${bank.name}")
    private String bankName;
    @Value("${bank.website}")
    private String bankWebsite;

    public AccountOpeningController(
        @Autowired OpeningFormService openingFormService,
        @Autowired MailSenderService mailSenderService
    ) {
        this.openingFormService = openingFormService;
        this.mailSenderService = mailSenderService;
    }

    @PostMapping
    public ResponseEntity<?> saveCustomer(@Valid @RequestBody OpeningForm openingForm) {
        OpeningForm c = openingFormService.save(openingForm);
        if (c == null) {
            return new RestResponseDto().failureModel("Error Occurs");
        } else {
            logger.debug("Account Opening Request Saved");
            Email email = new Email();
            email.setBankName(this.bankName);
            email.setBankWebsite(this.bankWebsite);
            email.setBankBranch(c.getBranch().getName());
            for (OpeningCustomer customer : c.getOpeningAccount().getOpeningCustomers()) {
                email.setTo(customer.getEmail());
                email.setToName(customer.getFirstName() + ' ' + customer.getLastName());
                mailSenderService.send(Template.ACCOUNT_OPENING_THANK_YOU, email);
            }
            logger.debug("Email sent for Account Opening Request");
            return new RestResponseDto().successModel(c);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return new RestResponseDto().successModel(openingFormService.findOne(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long id,
        @RequestBody OpeningForm openingForm) {
        OpeningForm oldOpeningForm = openingFormService.findOne(id);
        AccountStatus previousStatus = oldOpeningForm.getStatus();
        OpeningForm newOpeningForm = openingFormService.save(openingForm);
        if (newOpeningForm == null) {
            return new RestResponseDto().failureModel("Couldn't update customer");
        } else {
            if (!previousStatus.equals(newOpeningForm.getStatus())) {
                Email email = new Email();
                email.setBankName(this.bankName);
                email.setBankWebsite(this.bankWebsite);
                email.setBankBranch(newOpeningForm.getBranch().getName());
                email.setAccountType(newOpeningForm.getAccountType().getName());
                for (OpeningCustomer customer : newOpeningForm.getOpeningAccount()
                    .getOpeningCustomers()) {
                    email.setTo(customer.getEmail());
                    email.setToName(customer.getFirstName() + ' ' + customer.getLastName());
                    if (newOpeningForm.getStatus().equals(AccountStatus.APPROVAL)) {
                        mailSenderService.send(Template.ACCOUNT_OPENING_ACCEPT, email);
                    }
                }
            }
            return new RestResponseDto().successModel(newOpeningForm);
        }
    }

    @GetMapping(value = "/statusCount")
    public ResponseEntity<?> getStatus(@RequestParam("branchId") Long branchId) {
        return new RestResponseDto().successModel(openingFormService.getStatus(branchId));
    }

    @GetMapping(value = "/all")
    public ResponseEntity<?> getAll() {
        return new RestResponseDto().successModel(openingFormService.findAll());
    }

    @PostMapping(value = "/list")
    public ResponseEntity<?> getPageable(@RequestBody Object searchObject,
        @RequestParam("page") int page, @RequestParam("size") int size) {
        return new RestResponseDto().successModel(
            openingFormService.findAllPageable(searchObject, PaginationUtils.pageable(page, size)));
    }

    @PostMapping(value = "/uploadFile")
    public ResponseEntity<?> saveUserFile(@RequestParam("file") MultipartFile multipartFile,
        @RequestParam("type") String type, @RequestParam("name") String name,
        @RequestParam("branch") String branch) {
        return FileUploadUtils.uploadAccountOpeningFile(multipartFile, branch, type, name);
    }
}
