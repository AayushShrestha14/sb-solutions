package com.sb.solutions.web.branch.v1;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sb.solutions.api.branch.entity.Branch;
import com.sb.solutions.api.branch.service.BranchService;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.dto.SearchDto;
import com.sb.solutions.core.utils.PaginationUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

/**
 * @author Rujan Maharjan on 2/13/2019
 */

@RestController
@RequestMapping("/v1/branch")
public class BranchController {

    private final BranchService branchService;

    public BranchController(@Autowired BranchService branchService) {
        this.branchService = branchService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> saveBranch(@Valid @RequestBody Branch branch) {
        final Branch b = branchService.save(branch);

        if (b == null) {
            return new RestResponseDto().failureModel("Error Occurs");
        } else {
            return new RestResponseDto().successModel(b);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/upload")
    public ResponseEntity<?> uploadBranchExcel(@RequestParam("file") MultipartFile multipartFile) {
        branchService.saveExcel(multipartFile);

        return new RestResponseDto().successModel(null);
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
            value = "Results page you want to retrieve (0..N)"),
        @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
            value = "Number of records per page.")})
    @RequestMapping(method = RequestMethod.POST, path = "/list")
    public ResponseEntity<?> getPageableBranch(@RequestBody Object searchDto,
        @RequestParam("page") int page, @RequestParam("size") int size) {
        return new RestResponseDto().successModel(
            branchService.findAllPageable(searchDto, PaginationUtils.pageable(page, size)));
    }

    @RequestMapping(method = RequestMethod.GET, path = "/statusCount")
    public ResponseEntity<?> getBranchStatusCount() {
        return new RestResponseDto().successModel(branchService.branchStatusCount());
    }

    @GetMapping(value = "/all")
    public ResponseEntity<?> getBranch() {
        return new RestResponseDto().successModel(branchService.findAll());
    }

    @RequestMapping(method = RequestMethod.POST, path = "/csv")
    public ResponseEntity<?> csv(@RequestBody SearchDto searchDto) {
        return new RestResponseDto().successModel((branchService.csv(searchDto)));
    }
}