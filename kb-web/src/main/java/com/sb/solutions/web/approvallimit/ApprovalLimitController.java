package com.sb.solutions.web.approvallimit;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sb.solutions.api.approvallimit.entity.ApprovalLimit;
import com.sb.solutions.api.approvallimit.service.ApprovalLimitService;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.dto.SearchDto;
import com.sb.solutions.core.exception.GlobalExceptionHandler;
import com.sb.solutions.core.utils.PaginationUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/v1/approvallimit")
public class ApprovalLimitController {

    private final ApprovalLimitService approvalLimitService;
    private final GlobalExceptionHandler globalExceptionHandler;

    @PostMapping
    public ResponseEntity<?> addApprovalLimit(@RequestBody ApprovalLimit approvalLimit,
        BindingResult bindingResult) {
        System.out.println("tesds");
        globalExceptionHandler.constraintValidation(bindingResult);
        ApprovalLimit aproLimit = approvalLimitService.save(approvalLimit);
        if (aproLimit != null) {
            return new RestResponseDto().successModel(aproLimit);
        } else {
            return new RestResponseDto().failureModel("Error Occurred");
        }
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
            value = "Results page you want to retrieve (0..N)"),
        @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
            value = "Number of records per page.")})
    @PostMapping(value = "/get")
    public ResponseEntity<?> getAll(@RequestBody SearchDto searchDto,
        @RequestParam("page") int page, @RequestParam("size") int size) {
        return new RestResponseDto().successModel(approvalLimitService
            .findAllPageable(searchDto, PaginationUtils.pageable(page, size)));
    }

}
