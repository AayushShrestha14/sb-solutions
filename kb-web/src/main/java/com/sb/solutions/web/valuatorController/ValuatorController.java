package com.sb.solutions.web.valuatorController;

import com.sb.solutions.api.valuator.entity.Valuator;
import com.sb.solutions.api.valuator.service.ValuatorService;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.exception.GlobalExceptionHandler;
import com.sb.solutions.core.utils.CustomPageable;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/valuator")
public class ValuatorController {
    private final ValuatorService valuatorService;

    @PostMapping
    public ResponseEntity<?> saveUser(@RequestBody Valuator valuator) {
        return new RestResponseDto().successModel(valuatorService.save(valuator));
    }
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page.")})
    @PostMapping(value = "/get")
    public ResponseEntity<?> getAll(@RequestBody Valuator valuator, @RequestParam("page") int page, @RequestParam("size") int size) {
        return new RestResponseDto().successModel(valuatorService.findAllPageable(valuator,new CustomPageable().pageable(page, size)));
    }

    @GetMapping("/get/statusCount")
    public ResponseEntity<?> getBranchStatusCount() {
        return new RestResponseDto().successModel(valuatorService.valuatorStatusCount());
    }
}
