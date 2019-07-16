package com.sb.solutions.web.document.v1;

import java.util.List;
import javax.validation.Valid;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sb.solutions.api.document.entity.Document;
import com.sb.solutions.api.document.entity.LoanCycle;
import com.sb.solutions.api.document.service.DocumentService;
import com.sb.solutions.api.document.service.LoanCycleService;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.dto.SearchDto;
import com.sb.solutions.core.utils.PaginationUtils;


@RestController
@RequestMapping(value = "/v1/document")
public class DocumentController {

    private final DocumentService documentService;
    private final LoanCycleService loanCycleService;

    public DocumentController(@Autowired DocumentService documentService,
                              @Autowired LoanCycleService loanCycleService) {
        this.documentService = documentService;
        this.loanCycleService = loanCycleService;
    }

    @PostMapping
    public ResponseEntity<?> addDocument(@Valid @RequestBody Document document) {

        final Document doc = documentService.save(document);

        if (doc != null) {
            return new RestResponseDto().successModel(doc);
        } else {
            return new RestResponseDto().failureModel("Error Occurred");
        }
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page.")})
    @PostMapping(value = "/list")
    public ResponseEntity<?> getAllByPagination(@RequestBody SearchDto searchDto,
                                                @RequestParam("page") int page, @RequestParam("size") int size) {
        return new RestResponseDto().successModel(
                documentService.findAllPageable(searchDto, PaginationUtils.pageable(page, size)));
    }


    @GetMapping(value = "/byCycle/{loanCycleId}/status/{status}")
    public ResponseEntity<?> getByCycleContaining(@PathVariable Long loanCycleId,
                                                  @PathVariable String status) {
        return new RestResponseDto()
                .successModel(documentService.getByCycleContainingAndStatus(loanCycleId, status));
    }

    @GetMapping(value = "/lifeCycle")
    public ResponseEntity<?> getLifeCycle() {
        return new RestResponseDto().successModel(loanCycleService.findAll());
    }

    @GetMapping(value = "/statusCount")
    public ResponseEntity<?> getCount() {
        return new RestResponseDto().successModel(documentService.documentStatusCount());
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "loanCycleId", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)")})
    @PostMapping(value = "/saveList")
    public ResponseEntity<?> saveList(@RequestBody List<Long> integers,
                                      @RequestParam("loanCycleId") long loanCycleId) {
        LoanCycle loanCycle = loanCycleService.findOne(Long.valueOf(loanCycleId));
        return new RestResponseDto().successModel(documentService.saveList(integers, loanCycle));
    }

    @GetMapping(value = "/all")
    public ResponseEntity<?> getAll() {
        return new RestResponseDto().successModel(documentService.findAll());
    }

    @GetMapping(value = "/status/{statusValue}")
    public ResponseEntity<?> getAllByStatus(@PathVariable String statusValue) {
        return new RestResponseDto().successModel(documentService.getByStatus(statusValue));
    }

}
