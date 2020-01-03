package com.sb.solutions.web.valuator;

import java.util.List;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sb.solutions.api.branch.entity.Branch;
import com.sb.solutions.api.user.entity.User;
import com.sb.solutions.api.user.service.UserService;
import com.sb.solutions.api.valuator.entity.Valuator;
import com.sb.solutions.api.valuator.service.ValuatorService;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.dto.SearchDto;
import com.sb.solutions.core.utils.PaginationUtils;

@RestController
@RequestMapping("/v1/valuator")
public class ValuatorController {

    private final ValuatorService valuatorService;
    private final UserService userService;

    @Autowired
    public ValuatorController(ValuatorService valuatorService,
        UserService userService) {
        this.valuatorService = valuatorService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> saveValuator(@RequestBody Valuator valuator) {
        return new RestResponseDto().successModel(valuatorService.save(valuator));
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
            value = "Results page you want to retrieve (0..N)"),
        @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
            value = "Number of records per page.")})
    @PostMapping(value = "/list")
    public ResponseEntity<?> getAll(@RequestBody SearchDto searchDto,
        @RequestParam("page") int page, @RequestParam("size") int size) {
        return new RestResponseDto()
            .successModel(valuatorService.findAllPageable(searchDto, PaginationUtils
                .pageable(page, size)));
    }

    @GetMapping("/statusCount")
    public ResponseEntity<?> getValuatorStatusCount() {
        return new RestResponseDto().successModel(valuatorService.valuatorStatusCount());
    }

    @PostMapping("/valuator-branch")
    public ResponseEntity<?> getValuatorByBranch(
        @RequestBody(required = false) List<Branch> branches) {
        User authenticatedUser = userService.getAuthenticated();
        return new RestResponseDto().successModel(
            valuatorService.findByBranchIn(branches != null && !branches.isEmpty() ? branches
                : authenticatedUser.getBranch()));
    }
}
