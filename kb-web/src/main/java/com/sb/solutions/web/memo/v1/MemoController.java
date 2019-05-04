package com.sb.solutions.web.memo.v1;

import java.util.Map;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sb.solutions.api.memo.entity.Memo;
import com.sb.solutions.api.memo.service.MemoService;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.exception.GlobalExceptionHandler;
import com.sb.solutions.core.utils.PaginationUtils;
import com.sb.solutions.web.memo.v1.dto.MemoDto;
import com.sb.solutions.web.memo.v1.mapper.MemoMapper;

@RestController
@RequestMapping(MemoController.URL)
public class MemoController {

    static final String URL = "/v1/memos";

    private static final Logger logger = LoggerFactory.getLogger(MemoController.class);

    private final MemoService service;

    private final MemoMapper mapper;

    private final GlobalExceptionHandler exceptionHandler;

    public MemoController(@Autowired MemoService service,
        @Autowired GlobalExceptionHandler exceptionHandler,
        @Autowired MemoMapper mapper) {
        this.service = service;
        this.exceptionHandler = exceptionHandler;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody MemoDto dto, BindingResult bindingResult) {
        exceptionHandler.constraintValidation(bindingResult);

        final Memo savedMemo = service.save(mapper.mapDtoToEntity(dto));

        if (null == savedMemo) {
            logger.error("Error while saving memo {}", dto);
            return new RestResponseDto()
                .failureModel("Error occurred while saving Memo " + dto);
        }

        return new RestResponseDto().successModel(mapper.mapEntityToDto(savedMemo));
    }

    @GetMapping
    public ResponseEntity<?> getPageable(
        @RequestParam(required = false) Map<String, String> requestParams,
        @RequestParam("page") int page, @RequestParam("size") int size) {

        final Pageable pageable = PaginationUtils.pageable(page, size);
        final Map<String, String> filters = PaginationUtils
            .excludePageableProperties(requestParams);

        final Page<Memo> memos = service.findPageable(filters, pageable);
        final Page<MemoDto> dtos = new PageImpl<>(mapper.mapEntitiesToDtos(memos.getContent()),
            pageable, memos.getTotalElements());

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable long id) {
        return new RestResponseDto().successModel(mapper.mapEntityToDto(service.findOne(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody MemoDto dto,
        BindingResult bindingResult) {
        exceptionHandler.constraintValidation(bindingResult);

        final Memo savedMemo = service.save(mapper.mapDtoToEntity(dto));

        if (null == savedMemo) {
            return new RestResponseDto()
                .failureModel("Error occurred while saving Memo " + dto);
        }

        return new RestResponseDto().successModel(mapper.mapEntityToDto(savedMemo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id) {
        service.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        return new RestResponseDto().successModel(mapper.mapEntitiesToDtos(service.findAll()));
    }
}
