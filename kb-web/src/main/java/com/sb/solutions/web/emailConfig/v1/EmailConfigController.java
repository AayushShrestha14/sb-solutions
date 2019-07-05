package com.sb.solutions.web.emailConfig.v1;

import com.sb.solutions.api.emailConfig.entity.EmailConfig;
import com.sb.solutions.api.emailConfig.service.EmailConfigService;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.utils.email.Email;
import com.sb.solutions.core.utils.email.MailThreadService;
import com.sb.solutions.core.utils.email.template.SampleTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Rujan Maharjan on 7/1/2019
 */

@RestController
@RequestMapping(EmailConfigController.URL)
public class EmailConfigController {

    static final String URL = "/v1/email-config";

    private static final Logger logger = LoggerFactory.getLogger(EmailConfigController.class);

    private final EmailConfigService emailConfigService;

    private final MailThreadService mailThreadService;

    public EmailConfigController(@Autowired EmailConfigService emailConfigService,
                                 @Autowired MailThreadService mailThreadService) {
        this.emailConfigService = emailConfigService;
        this.mailThreadService = mailThreadService;
    }


    @PostMapping
    public ResponseEntity<?> saveUpdateEmailConfig(@Valid @RequestBody EmailConfig emailConfig) {
        logger.info(" saving Email config {}", emailConfig);
        EmailConfig config = emailConfigService.save(emailConfig);

        if (null == config) {
            logger.error("Error while saving Email config {}", emailConfig);
            return new RestResponseDto()
                    .failureModel("Error occurred while saving  Email config " + emailConfig);
        }

        return new RestResponseDto().successModel(config);
    }


    @GetMapping("/all")
    public ResponseEntity<?> getEmailConfig() {
        return new RestResponseDto().successModel(emailConfigService.findAll());
    }

    @PostMapping("/test")
    public ResponseEntity<?> testConfiguration(@RequestBody EmailConfig emailConfig) {
        Email email = new Email();
        email.setBody(SampleTemplate.sampleTemplate());
        email.setSubject("No reply");
        email.setTo(emailConfig.getTestMail());
        try {
            mailThreadService.testMail(email);
            logger.info(" sending Email config {}", emailConfig);
        } catch (Exception e) {
            logger.error("Error while sending Email config {}", emailConfig);
            return new RestResponseDto()
                    .failureModel("Error occurred while Sending  Email config " + emailConfig);
        }
        return null;
    }


}
