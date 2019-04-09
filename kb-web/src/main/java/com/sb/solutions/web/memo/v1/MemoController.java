package com.sb.solutions.web.memo.v1;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sb.solutions.api.memo.entity.Memo;
import com.sb.solutions.api.memo.entity.MemoStage;
import com.sb.solutions.api.memo.service.MemoService;
import com.sb.solutions.api.memo.service.MemoStageService;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.exception.GlobalExceptionHandler;
import com.sb.solutions.core.utils.CustomPageable;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RestController
@RequestMapping(MemoController.URL)
public class MemoController {

    static final String URL = "v1/memos";

    private static final Logger logger = LoggerFactory.getLogger(MemoController.class);

    private final MemoService service;

    private final MemoStageService stageService;

    private final GlobalExceptionHandler exceptionHandler;

    public MemoController(@Autowired MemoService service,
        @Autowired MemoStageService stageService,
        @Autowired GlobalExceptionHandler exceptionHandler) {
        this.service = service;
        this.stageService = stageService;
        this.exceptionHandler = exceptionHandler;
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody Memo memo, BindingResult bindingResult) {
        exceptionHandler.constraintValidation(bindingResult);

        final Memo savedMemo = service.save(memo);

        if (null == savedMemo) {
            logger.error("Error while saving memo {}", memo);
            return new RestResponseDto()
                .failureModel("Error occurred while saving Memo " + memo);
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
    public ResponseEntity<?> getPageable(@RequestBody Memo memo,
        @RequestParam("page") int page, @RequestParam("size") int size) {
        return new RestResponseDto()
            .successModel(service.findAllPageable(memo, new CustomPageable().pageable(page, size)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable long id) {
        final Memo memo = service.findOne(id);

        return new RestResponseDto().successModel(memo);
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody Memo memo,
        BindingResult bindingResult) {
        exceptionHandler.constraintValidation(bindingResult);

        final Memo savedMemo = service.save(memo);

        if (null == savedMemo) {
            return new RestResponseDto()
                .failureModel("Error occurred while saving Memo " + memo);
        }

        return new RestResponseDto().successModel(savedMemo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id) {
        service.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        return new RestResponseDto().successModel(service.findAll());
    }

    @GetMapping("/{id}/stages")
    public ResponseEntity<?> getMemoStagesByMemo(@PathVariable long id) {
        return new RestResponseDto().successModel(service.findOne(id));
    }

    @PostMapping("/{id}/stages")
    public ResponseEntity<?> saveMemoStageForMemo(@PathVariable long id,
        @RequestBody MemoStage stage,
        BindingResult bindingResult) {
        exceptionHandler.constraintValidation(bindingResult);

        final MemoStage saved = stageService.save(stage);

        if (null == saved) {
            return new RestResponseDto()
                .failureModel("Error occurred while saving Memo " + stage);
        }

        return new RestResponseDto().successModel(saved);
    }
}
