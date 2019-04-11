package com.sb.solutions.web.memo.v1;

import java.util.Date;
import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sb.solutions.api.memo.entity.MemoType;
import com.sb.solutions.api.memo.service.MemoTypeService;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.exception.GlobalExceptionHandler;
import com.sb.solutions.web.memo.v1.dto.MemoTypeDto;
import com.sb.solutions.web.memo.v1.mapper.MemoTypeMapper;

@RestController
@RequestMapping(MemoTypeController.URL)
public class MemoTypeController {

    static final String URL = "v1/memos/types";

    private final MemoTypeService service;

    private final GlobalExceptionHandler exceptionHandler;

    private final MemoTypeMapper mapper;

    public MemoTypeController(@Autowired MemoTypeService service,
        @Autowired GlobalExceptionHandler exceptionHandler,
        @Autowired MemoTypeMapper mapper) {
        this.service = service;
        this.exceptionHandler = exceptionHandler;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody MemoTypeDto type,
        BindingResult bindingResult) {
        exceptionHandler.constraintValidation(bindingResult);

        final MemoType memoType = mapper.mapDtoToEntity(type);

        final MemoType savedMemo = service.save(memoType);

        if (null == savedMemo) {
            return new RestResponseDto()
                .failureModel("Error occurred while saving Memo Type " + type);
        }

        return new RestResponseDto().successModel(savedMemo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable long id) {
        return new RestResponseDto().successModel(service.findOne(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @Valid @RequestBody MemoTypeDto dto,
        BindingResult bindingResult) {
        exceptionHandler.constraintValidation(bindingResult);

        final MemoType type = mapper.mapDtoToEntity(dto);
        type.setLastModifiedAt(new Date());

        final MemoType savedMemo = service.save(type);

        if (null == savedMemo) {
            return new RestResponseDto()
                .failureModel("Error occurred while saving Memo Type " + type);
        }

        return new RestResponseDto().successModel(savedMemo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        service.deleteById(id);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        final List<MemoType> types = service.findAll();
        return new RestResponseDto()
            .successModel(types);
    }
}
