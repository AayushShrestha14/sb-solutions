package com.sb.solutions.web.memo.v1;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sb.solutions.api.memo.entity.MemoType;
import com.sb.solutions.api.memo.service.MemoTypeService;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.exception.GlobalExceptionHandler;
import com.sb.solutions.core.utils.CustomPageable;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RestController
@RequestMapping(MemoTypeController.URL)
public class MemoTypeController {

    static final String URL = "v1/memos/types";

    private final MemoTypeService service;

    private final GlobalExceptionHandler exceptionHandler;

    public MemoTypeController(@Autowired MemoTypeService service,
        @Autowired GlobalExceptionHandler exceptionHandler) {
        this.service = service;
        this.exceptionHandler = exceptionHandler;
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody MemoType type, BindingResult bindingResult) {
        exceptionHandler.constraintValidation(bindingResult);

        final MemoType savedMemo = service.save(type);

        if (null == savedMemo) {
            return new RestResponseDto()
                .failureModel("Error occurred while saving Memo Type " + type);
        }

        return new RestResponseDto().successModel(savedMemo);
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "page",
            dataType = "integer",
            paramType = "query",
            value = "Results page you want to retrieve (0..N)"),
        @ApiImplicitParam(name = "size",
            dataType = "integer",
            paramType = "query",
            value = "Number of records per page.")})
    @GetMapping
    public ResponseEntity<?> getPageableBranch(@RequestBody MemoType type,
        @RequestParam("page") int page, @RequestParam("size") int size) {
        return new RestResponseDto()
            .successModel(service.findAllPageable(type, new CustomPageable().pageable(page, size)));
    }
}
