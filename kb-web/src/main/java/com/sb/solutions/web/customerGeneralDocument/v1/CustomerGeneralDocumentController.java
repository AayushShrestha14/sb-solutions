package com.sb.solutions.web.customerGeneralDocument.v1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sb.solutions.api.customer.entity.CustomerGeneralDocument;
import com.sb.solutions.api.customer.service.CustomerGeneralDocumentService;
import com.sb.solutions.core.dto.RestResponseDto;
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

    public CustomerGeneralDocumentController(
        CustomerGeneralDocumentService customerGeneralDocumentService) {
        this.customerGeneralDocumentService = customerGeneralDocumentService;
    }

    @PostMapping
    public ResponseEntity save(@RequestBody CustomerGeneralDocument customerGeneralDocument) {
        return new RestResponseDto()
            .successModel(customerGeneralDocumentService.save(customerGeneralDocument));
    }
}
