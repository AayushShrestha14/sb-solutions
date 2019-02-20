package com.sb.solutions.web.document.controller;

import com.sb.solutions.api.document.entity.Document;
import com.sb.solutions.api.document.service.DocumentService;
import com.sb.solutions.api.document.validator.DocumentValidator;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.utils.CustomPageable;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;



@RestController
@AllArgsConstructor
@RequestMapping(value = "v1/document")
public class DocumentController {

    private final DocumentService documentService;
    private final DocumentValidator documentValidator;

    @PostMapping(value = "/add")
    public ResponseEntity<?> addDocument(@RequestBody Document document) {
    if(StringUtils.isEmpty(documentValidator.isValid(document))) {
        return new RestResponseDto().successModel(documentService.save(document));
    }else{
        return new RestResponseDto().failureModel(documentValidator.isValid(document));
    }
    }

    @PostMapping(value = "/edit")
    public ResponseEntity<?> editDocument(@RequestBody Document document) {
            if (documentService.findOne(document.getId())!=null) {
                return new RestResponseDto().successModel(documentService.save(document));
            } else {
                return new RestResponseDto().failureModel("Document doesn't exit");
            }
    }

    @PostMapping(value = "/changeStatus")
    public ResponseEntity<?> changeStatus(@RequestBody Document document) {
        if (documentService.findOne(document.getId())!=null) {
            return new RestResponseDto().successModel(documentService.save(document));
        } else {
            return new RestResponseDto().failureModel("Document doesn't exit");
        }

    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page.")})
    @PostMapping(value = "/get")
    public ResponseEntity<?> getAll(@RequestBody Document document, @RequestParam("page") int page, @RequestParam("size") int size) {
        return new RestResponseDto().successModel(documentService.findAllPageable(document,new CustomPageable().pageable(page, size)));
    }
}
