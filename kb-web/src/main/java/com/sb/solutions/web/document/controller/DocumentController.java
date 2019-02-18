package com.sb.solutions.web.document.controller;

import com.sb.solutions.api.document.entity.Document;
import com.sb.solutions.api.document.service.DocumentService;
import com.sb.solutions.api.document.validator.DocumentValidator;
import com.sb.solutions.core.dto.RestResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping(value = "v1/document")
public class DocumentController {

    private final DocumentService documentService;
    private final DocumentValidator documentValidator;

    @PostMapping(value = "/add")
    public ResponseEntity<?> addDocument(@RequestBody Document document) {
    if(documentValidator.isValid(document).equals("Valid")) {
        return new RestResponseDto().successModel(documentService.save(document));
    }else{
        return new RestResponseDto().failureModel(documentValidator.isValid(document));
    }
    }

    @PostMapping(value = "/edit")
    public ResponseEntity<?> editDocument(@RequestBody Document document) {
        if (documentService.findOne(document.getId())!=null) {
            return new RestResponseDto().successModel(documentService.update(document));
        } else {
            return new RestResponseDto().failureModel("Document doesn't exit");
        }
    }

    @PostMapping(value = "/changeStatus")
    public ResponseEntity<?> changeStatus(@RequestBody Document document) {
        if (documentService.findOne(document.getId())!=null) {
            return new RestResponseDto().successModel(documentService.changeStatus(document));
        } else {
            return new RestResponseDto().failureModel("Document doesn't exit");
        }

    }

    @GetMapping(value = "/all")
    public ResponseEntity<?> getAll() {
        return new RestResponseDto().successModel(documentService.findAll());
    }
}
