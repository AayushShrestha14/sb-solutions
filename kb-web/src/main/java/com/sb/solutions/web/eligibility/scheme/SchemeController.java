package com.sb.solutions.web.eligibility.scheme;

import com.sb.solutions.api.eligibility.scheme.entity.Scheme;
import com.sb.solutions.api.eligibility.scheme.service.SchemeService;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.exception.GlobalExceptionHandler;
import com.sb.solutions.core.utils.CustomPageable;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/companies/{companyId}/schemes")
@AllArgsConstructor
public class SchemeController {

    private final Logger logger = LoggerFactory.getLogger(SchemeController.class);

    private final SchemeService schemeService;

    private final GlobalExceptionHandler globalExceptionHandler;

    @PostMapping
    final public ResponseEntity<?> addScheme(@Valid @RequestBody Scheme scheme
            , @PathVariable Long companyId, BindingResult bindingResult) {
        globalExceptionHandler.constraintValidation(bindingResult);
        logger.debug("Request to save new scheme.");
        final Scheme savedScheme = schemeService.save(scheme);
        if (savedScheme == null) return new RestResponseDto().failureModel("Oops! Something went wrong");
        return new RestResponseDto().successModel(scheme);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page.")})
    @GetMapping
    final public ResponseEntity<?> getSchemes(@RequestParam("page") int page, @RequestParam("size") int size
    , @PathVariable Long companyId) {
        logger.debug("Request to get list of schemes.");
        return new RestResponseDto().successModel(schemeService.
                findAllPageable(companyId, new CustomPageable().pageable(page, size)));
    }

    @PutMapping(path = "/{schemeId}")
    final public ResponseEntity<?> updateSchemes(@PathVariable long companyId, @PathVariable long schemeId
            , @RequestBody Scheme scheme
            , BindingResult bindingResult) {
        globalExceptionHandler.constraintValidation(bindingResult);
        logger.debug("Request to update the scheme with id [{}]", schemeId);
        final Scheme updatedScheme = schemeService.save(scheme);
        if (updatedScheme == null) return new RestResponseDto().failureModel("Oops! Something went wrong.");
        return new RestResponseDto().successModel(updatedScheme);
    }

}
