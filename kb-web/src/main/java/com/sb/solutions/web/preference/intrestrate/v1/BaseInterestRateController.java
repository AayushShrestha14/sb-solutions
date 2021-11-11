package com.sb.solutions.web.preference.intrestrate.v1;

import java.util.List;
import javax.validation.Valid;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sb.solutions.api.preference.interestrate.entity.BaseInterestRate;
import com.sb.solutions.api.preference.interestrate.service.BaseInterestService;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.dto.SearchDto;
import com.sb.solutions.core.utils.PaginationUtils;

@RestController
@RequestMapping("/v1/base-interest")
public class BaseInterestRateController {

    private final BaseInterestService baseInterestService;

    @Autowired
    public BaseInterestRateController(BaseInterestService baseInterestService) {
        this.baseInterestService = baseInterestService;
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page.")})
    @PreAuthorize("hasAuthority('Preference Master')")
    @PostMapping(value = "/list")
    public ResponseEntity<?> getAll(@RequestBody SearchDto searchDto,
                                    @RequestParam("page") int page, @RequestParam("size") int size) {
        return new RestResponseDto()
                .successModel(baseInterestService.findAllPageable(searchDto, PaginationUtils
                        .pageable(page, size)));
    }


    @GetMapping("/all")
    public List<BaseInterestRate> getAllInterests() {
        return baseInterestService.findAll();
    }


    @GetMapping("/{id}")
    public ResponseEntity<BaseInterestRate> getInterestById(@PathVariable("id") Long id) {
        BaseInterestRate base = baseInterestService.findOne(id);
        if (base == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(base);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateIntrest(@PathVariable("id") Long id,
        @Valid @RequestBody BaseInterestRate intrestDetail) {
        BaseInterestRate base = baseInterestService.findOne(id);
        if (base == null) {
            return new RestResponseDto().failureModel("Unable to save value");
        }

        return new RestResponseDto().successModel(baseInterestService.save(intrestDetail));
    }


    @PostMapping
    public ResponseEntity<?> saveInterest(@Valid @RequestBody BaseInterestRate base) {
        return new RestResponseDto().successModel(baseInterestService.save(base));
    }

    @GetMapping("/active")
    public ResponseEntity<?> getActiveBaseRate() {
        return new RestResponseDto().successModel(baseInterestService.getActiveRate());
    }

}
