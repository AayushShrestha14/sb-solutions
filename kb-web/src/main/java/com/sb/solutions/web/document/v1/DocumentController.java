package com.sb.solutions.web.document.v1;

import com.sb.solutions.api.document.entity.Document;
import com.sb.solutions.api.document.entity.LoanCycle;
import com.sb.solutions.api.document.service.DocumentService;
import com.sb.solutions.api.document.service.LoanCycleService;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.dto.SearchDto;
import com.sb.solutions.core.utils.PaginationUtils;
import com.sb.solutions.web.document.v1.dto.DocumentDto;
import com.sb.solutions.web.document.v1.mapper.DocumentMapper;
import edu.emory.mathcs.backport.java.util.Arrays;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(value = "/v1/document")
public class DocumentController {

    private final DocumentService documentService;
    private final LoanCycleService loanCycleService;
    private final DocumentMapper documentMapper;

    public DocumentController(
        @Autowired DocumentService documentService,
        @Autowired LoanCycleService loanCycleService,
        @Autowired DocumentMapper documentMapper
    ) {
        this.documentService = documentService;
        this.loanCycleService = loanCycleService;
        this.documentMapper = documentMapper;
    }

    @PostMapping
    public ResponseEntity<?> addDocument(@Valid @RequestBody DocumentDto documentDto) {

        final Document doc = documentService.save(documentMapper.mapDtoToEntity(documentDto));

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


    @GetMapping(value = "/cycle/{cycleId}/status/{statusValue}")
    public ResponseEntity<?> getByCycleContaining(@PathVariable Long cycleId,
        @PathVariable String statusValue) {
        return new RestResponseDto()
            .successModel(documentService.getByCycleContainingAndStatus(cycleId, statusValue));
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
        LoanCycle loanCycle = loanCycleService.findOne(loanCycleId);
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

    @PostMapping(value = "/downloadDoc")
    public ResponseEntity<?> downloadDoc(@RequestBody String path){
        String finalPath = String.join("/",
                (Arrays.asList(path.split("/"))
                        .subList(0, Arrays.asList(path.split("/"))
                                .size() - 1)));
        return new RestResponseDto().successModel(documentService.downloadAllDoc(finalPath));
    }

}
