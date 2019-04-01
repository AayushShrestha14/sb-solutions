package com.sb.solutions.web.valuatorController;

import com.sb.solutions.api.valuator.entity.Valuator;
import com.sb.solutions.api.valuator.service.ValuatorService;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.dto.SearchDto;
import com.sb.solutions.core.utils.CustomPageable;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/valuator")
public class ValuatorController {
    private final ValuatorService valuatorService;

    @PostMapping
    public ResponseEntity<?> saveValuator(@RequestBody Valuator valuator) {
        return new RestResponseDto().successModel(valuatorService.save(valuator));
    }
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page.")})
    @PostMapping(value = "/get")
    public ResponseEntity<?> getAll(@RequestBody SearchDto searchDto, @RequestParam("page") int page, @RequestParam("size") int size) {
        return new RestResponseDto().successModel(valuatorService.findAllPageable(searchDto,new CustomPageable().pageable(page, size)));
    }

    @GetMapping("/get/statusCount")
    public ResponseEntity<?> getValuatorStatusCount() {
        return new RestResponseDto().successModel(valuatorService.valuatorStatusCount());
    }
}
