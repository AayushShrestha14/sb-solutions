package com.sb.solutions.web.emailConfig.v1;

import com.sb.solutions.api.emailConfig.entity.EmailConfig;
import com.sb.solutions.api.emailConfig.service.EmailConfigService;
import com.sb.solutions.core.dto.RestResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    public EmailConfigController(@Autowired EmailConfigService emailConfigService) {
        this.emailConfigService = emailConfigService;
    }


    @PostMapping
    public ResponseEntity<?> saveUpdateEmailConfig(@Valid @RequestBody EmailConfig emailConfig) {
        EmailConfig config = emailConfigService.save(emailConfig);
        if (null == config) {
            logger.error("Error while saving memo {}", emailConfig);
            return new RestResponseDto()
                    .failureModel("Error occurred while saving Memo " + emailConfig);
        }

        return new RestResponseDto().successModel(config);
    }
}
