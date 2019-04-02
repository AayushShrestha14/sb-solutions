package com.sb.solutions.web.memo.v1;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sb.solutions.api.memo.entity.MemoType;
import com.sb.solutions.api.memo.service.MemoTypeService;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.exception.GlobalExceptionHandler;

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

    @GetMapping
    public ResponseEntity<?> getAll() {
        return new RestResponseDto()
            .successModel(service.findAll());
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        service.deleteById(id);

        return  ResponseEntity.ok().build();
    }
}
