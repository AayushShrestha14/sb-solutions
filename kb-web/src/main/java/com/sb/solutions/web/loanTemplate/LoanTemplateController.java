package com.sb.solutions.web.loanTemplate;

import com.sb.solutions.api.loanTemplate.entity.LoanTemplate;
import com.sb.solutions.api.loanTemplate.service.LoanTemplateService;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.dto.SearchDto;
import com.sb.solutions.core.exception.GlobalExceptionHandler;
import com.sb.solutions.core.utils.CustomPageable;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Rujan Maharjan on 2/25/2019
 */

@RestController
@RequestMapping("/v1/loanTemplate")
public class LoanTemplateController {

    @Autowired
    LoanTemplateService loanTemplateService;

    @Autowired
    GlobalExceptionHandler globalExceptionHandler;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> saveBranch(@Valid @RequestBody LoanTemplate loanTemplate, BindingResult bindingResult) {
        globalExceptionHandler.constraintValidation(bindingResult);
        LoanTemplate template = loanTemplateService.save(loanTemplate);
        if (template == null) {
            return new RestResponseDto().failureModel("Error Occurs");
        } else {
            return new RestResponseDto().successModel(template);
        }
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page.")})
    @RequestMapping(method = RequestMethod.POST, path = "/get")
    public ResponseEntity<?> getPageableLoanTemplate(@RequestBody SearchDto searchDto, @RequestParam("page") int page, @RequestParam("size") int size) {
return new RestResponseDto().successModel(loanTemplateService.findAllPageable(searchDto, new CustomPageable().pageable(page, size)));
    }


    @RequestMapping(method = RequestMethod.GET, path = "/getAll")
    public ResponseEntity<?> getLoanTemplate() {
        return new RestResponseDto().successModel(loanTemplateService.findAll());
    }

}