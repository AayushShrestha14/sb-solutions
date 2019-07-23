package com.sb.solutions.web.accountOpening.v1;

import javax.validation.Valid;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sb.solutions.api.branch.entity.Branch;
import com.sb.solutions.api.openingForm.entity.OpeningForm;
import com.sb.solutions.api.openingForm.service.OpeningFormService;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.utils.PaginationUtils;
import com.sb.solutions.core.utils.file.FileUploadUtils;

@RestController
@RequestMapping("/v1/accountOpening")
@AllArgsConstructor
public class AccountOpeningController {

    private OpeningFormService openingFormService;

    @PostMapping
    public ResponseEntity<?> saveCustomer(@Valid @RequestBody OpeningForm openingForm,
        BindingResult bindingResult) {
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
    public ResponseEntity<?> updateCustomer(@PathVariable Long id, @RequestBody OpeningForm openingForm) {
        return new RestResponseDto()
            .successModel(openingFormService.save(openingForm));
    }

    @GetMapping(value = "/statusCount")
    public ResponseEntity<?> getStatus(@RequestParam("branchId") Long branchId) {
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
    public ResponseEntity<?> getPageableCustomerByBranch(@RequestBody Branch branch,
        @RequestParam("page") int page, @RequestParam("size") int size,
        @RequestParam("accountStatus") String accountStatus) {
        return new RestResponseDto().successModel(openingFormService
            .findAllByBranchAndAccountStatus(branch, PaginationUtils.pageable(page, size),
                accountStatus));
    }

    @PostMapping(value = "/uploadFile")
    public ResponseEntity<?> saveUserFile(@RequestParam("file") MultipartFile multipartFile,
        @RequestParam("type") String type, @RequestParam("name") String name,
        @RequestParam("branch") String branch) {
        return FileUploadUtils.uploadAccountOpeningFile(multipartFile, branch, type, name);
    }
}
