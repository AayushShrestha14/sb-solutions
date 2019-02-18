package com.sb.solutions.web.bank;

import com.sb.solutions.api.branch.entity.Branch;
import com.sb.solutions.api.branch.service.BranchService;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.exception.GlobalExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author Rujan Maharjan on 2/13/2019
 */

@RestController
@RequestMapping("/v1/branch")
public class BranchController {

    @Autowired
    BranchService branchService;
    @Autowired
    GlobalExceptionHandler globalExceptionHandler;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> saveBranch(@Valid @RequestBody Branch branch, BindingResult bindingResult) {
        globalExceptionHandler.constraintValidation(bindingResult);
        Branch b = branchService.save(branch);
        if (b == null) {
            return new RestResponseDto().failureModel("Error Occurs");
        } else {
            return new RestResponseDto().successModel(b);
        }
    }


    @RequestMapping(method = RequestMethod.POST,path="/get")
    public ResponseEntity<?> getPageableBranch(@RequestBody Branch branch, Pageable pageable) {
        return new RestResponseDto().successModel(branchService.findAllPageable(branch,pageable));
    }

}
