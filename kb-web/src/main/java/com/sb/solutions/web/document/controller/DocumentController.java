package com.sb.solutions.web.document.controller;

import com.sb.solutions.api.document.entity.Document;
import com.sb.solutions.api.document.entity.LoanCycle;
import com.sb.solutions.api.document.service.DocumentService;
import com.sb.solutions.api.document.service.LoanCycleService;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.exception.GlobalExceptionHandler;
import com.sb.solutions.core.utils.CustomPageable;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;


@RestController
@AllArgsConstructor
@RequestMapping(value = "v1/document")
public class DocumentController {

    private final DocumentService documentService;
    private final GlobalExceptionHandler globalExceptionHandler;
    private final LoanCycleService loanCycleService;

    @PostMapping
    public ResponseEntity<?> addDocument(@Valid @RequestBody Document document, BindingResult bindingResult) {
        globalExceptionHandler.constraintValidation(bindingResult);

        Document doc = documentService.save(document);
        if(doc != null){

            return new RestResponseDto().successModel(doc);
        }else{
            return new RestResponseDto().failureModel("Error Occurred");
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

    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page.")})
    @PostMapping(value="/list")
    public ResponseEntity<?> getByCycleNotContaining(@RequestBody LoanCycle loanCycleList, @RequestParam("page") int page, @RequestParam("size") int size){
        return  new RestResponseDto().successModel(documentService.getByCycleNotContaining(loanCycleList,new CustomPageable().pageable(page, size)));

    }
    @GetMapping(value="lifeCycle")
    public ResponseEntity<?> getLifeCycle(){
        return new RestResponseDto().successModel(loanCycleService.findAll());
    }

    @PostMapping(value="getCount")
    public ResponseEntity<?> getCount(@RequestBody LoanCycle loanCycle){
        return new RestResponseDto().successModel(documentService.getCount(loanCycle));
    }
}
