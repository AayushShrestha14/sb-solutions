package com.sb.solutions.web.accountOpening.v1;

import com.sb.solutions.api.branch.entity.Branch;
import com.sb.solutions.api.openingForm.entity.OpeningForm;
import com.sb.solutions.api.openingForm.service.OpeningFormService;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.exception.GlobalExceptionHandler;
import com.sb.solutions.core.utils.PaginationUtils;
import com.sb.solutions.core.utils.uploadFile.FileUploadUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/accountOpening")
@AllArgsConstructor
public class AccountOpeningController {
    private OpeningFormService openingFormService;
    private GlobalExceptionHandler globalExceptionHandler;

    @PostMapping
    public ResponseEntity<?> saveCustomer(@Valid @RequestBody OpeningForm openingForm, BindingResult bindingResult) {
        globalExceptionHandler.constraintValidation(bindingResult);
        OpeningForm c = openingFormService.save(openingForm);
        if (c == null) {
            return new RestResponseDto().failureModel("Error Occurs");
        } else {
            return new RestResponseDto().successModel(c);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return new RestResponseDto().successModel(openingFormService.findOne(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long id, @RequestBody String status) {
        return new RestResponseDto().successModel(openingFormService.updateOpeningCustomer(id,status));
    }

    @GetMapping(value = "/statusCount")
    public ResponseEntity<?> getStatus(@RequestParam("branchId") Long branchId){
        return new RestResponseDto().successModel(openingFormService.getStatus(branchId));
    }

    @GetMapping(value = "/all")
    public ResponseEntity<?> getCustomer() {
        return new RestResponseDto().successModel(openingFormService.findAll());
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page.")})
    @PostMapping(value = "/list")
    public ResponseEntity<?> getPageableCustomerByBranch(@RequestBody Branch branch, @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("accountStatus") String accountStatus) {
        return new RestResponseDto().successModel(openingFormService.findAllByBranchAndAccountStatus(branch, PaginationUtils.pageable(page, size), accountStatus));
    }

    @PostMapping(value = "/uploadFile")
    public ResponseEntity<?> saveUserFile(@RequestParam("file") MultipartFile multipartFile, @RequestParam("type") String type, @RequestParam("name") String name, @RequestParam("branch") String branch) {
        return FileUploadUtils.uploadAccountOpeningFile(multipartFile, branch, type, name);
    }
}
