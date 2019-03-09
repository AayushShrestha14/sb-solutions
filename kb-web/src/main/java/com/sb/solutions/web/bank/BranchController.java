package com.sb.solutions.web.bank;

import com.sb.solutions.api.branch.entity.Branch;
import com.sb.solutions.api.branch.service.BranchService;
import com.sb.solutions.core.dto.RestResponseDto;
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

    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page.")})
    @RequestMapping(method = RequestMethod.POST, path = "/get")
    public ResponseEntity<?> getPageableBranch(@RequestBody Branch branch, @RequestParam("page") int page, @RequestParam("size") int size) {
        return new RestResponseDto().successModel(branchService.findAllPageable(branch, new CustomPageable().pageable(page, size)));
    }

    @RequestMapping(method = RequestMethod.GET, path = "/get/statusCount")
    public ResponseEntity<?> getBranchStatusCount() {
        return new RestResponseDto().successModel(branchService.branchStatusCount());
    }

}
