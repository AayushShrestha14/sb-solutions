package com.sb.solutions.web.loanConfig;

import com.sb.solutions.api.loanConfig.entity.LoanConfig;
import com.sb.solutions.api.loanConfig.service.LoanConfigService;
import com.sb.solutions.api.loanTemplate.entity.LoanTemplate;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.dto.SearchDto;
import com.sb.solutions.core.exception.GlobalExceptionHandler;
import com.sb.solutions.core.utils.CustomPageable;
import com.sb.solutions.core.utils.uploadFile.UploadFile;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

/**
 * @author Rujan Maharjan on 2/25/2019
 */
@RestController
@RequestMapping("/v1/config")
public class LoanConfigController {

    @Autowired
    GlobalExceptionHandler globalExceptionHandler;
    @Autowired
    LoanConfigService loanConfigService;
    @Autowired
    private UploadFile uploadFile;


    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> saveBranch(@Valid @RequestBody LoanConfig config, BindingResult bindingResult) {
        globalExceptionHandler.constraintValidation(bindingResult);
        LoanConfig loanConfig = loanConfigService.save(config);
        if (loanConfig == null) {
            return new RestResponseDto().failureModel("Error Occurs");
        } else {
            return new RestResponseDto().successModel(loanConfig);
        }
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page.")})
    @RequestMapping(method = RequestMethod.POST, path = "/get")
    public ResponseEntity<?> getPageableLoanConfig(@RequestBody SearchDto searchDto, @RequestParam("page") int page, @RequestParam("size") int size) {
        return new RestResponseDto().successModel(loanConfigService.findAllPageable(searchDto, new CustomPageable().pageable(page, size)));
    }


    @RequestMapping(method = RequestMethod.GET, path = "/get/statusCount")
    public ResponseEntity<?> getLoanStatusCount() {
        return new RestResponseDto().successModel(loanConfigService.loanStatusCount());
    }

    @RequestMapping(method = RequestMethod.GET, path = "/getAll")
    public ResponseEntity<?> getLoanAll() {
        return new RestResponseDto().successModel(loanConfigService.findAll());
    }

    @RequestMapping(method = RequestMethod.GET, path = "/get/{id}")
    public ResponseEntity<?> getLoanOne(@PathVariable Long id) {
        return new RestResponseDto().successModel(loanConfigService.findOne(id));
    }

    @RequestMapping(method = RequestMethod.POST, path = "/uploadFile")
    public ResponseEntity<?> saveDocuments(@RequestParam("file") MultipartFile multipartFile, @RequestParam("type") String type, @RequestParam("cycle") String cycle) {
        System.out.println(type);
        return uploadFile.uploadFile(multipartFile, type, cycle);
    }
}
