package com.sb.solutions.web.eligibility.scheme;

import com.sb.solutions.api.eligibility.scheme.entity.Scheme;
import com.sb.solutions.api.eligibility.scheme.service.SchemeService;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.exception.GlobalExceptionHandler;
import com.sb.solutions.core.utils.CustomPageable;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class SchemeController {

    private final SchemeService schemeService;

    private final GlobalExceptionHandler globalExceptionHandler;

    @PostMapping(path = "/v1/admin/schemes")
    final public ResponseEntity<?> addScheme(@Valid @RequestBody Scheme scheme, BindingResult bindingResult) {
        globalExceptionHandler.constraintValidation(bindingResult);
        final Scheme savedScheme = schemeService.save(scheme);
        if (savedScheme == null) return new RestResponseDto().failureModel("Oops! Something went wrong");
        return new RestResponseDto().successModel(scheme);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page.")})
    @GetMapping(path = "/v1/schemes")
    final public ResponseEntity<?> getSchemes(@RequestParam("page") int page, @RequestParam("size") int size) {
        return new RestResponseDto().successModel(schemeService.
                findAllPageable(null, new CustomPageable().pageable(page, size)));
    }

}
