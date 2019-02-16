package com.sb.solutions.web.document.controller;

import com.sb.solutions.api.document.entity.Document;
import com.sb.solutions.api.document.service.DocumentService;
import com.sb.solutions.core.dto.RestResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
@RequestMapping(value = "v1/document")
public class DocumentController {

    private final DocumentService documentService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> addDocument(@RequestBody Document document) {
        if(documentService.exists(document)){
            return new RestResponseDto().failureModel("Document of name "+document.getName()+" already exists");
        }
        else {
            return new RestResponseDto().successModel(documentService.save(document));
        }
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResponseEntity<?> editDocument(@RequestBody Document document) {
        if(documentService.exists(document)){
            return new RestResponseDto().successModel(documentService.update(document));
        }
        else{
            return new RestResponseDto().failureModel("Document doesn't exit");
        }
    }

    @RequestMapping(value = "/changeStatus", method = RequestMethod.POST)
    public ResponseEntity<?> changeStatus(@RequestBody Document document) {
        if(documentService.exists(document)){
            return new RestResponseDto().successModel(documentService.changeStatus(document));
        }
        else{
            return new RestResponseDto().failureModel("Document doesn't exit");
        }

    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity<?> getAll() {
        return new RestResponseDto().successModel(documentService.findAll());
    }
}
