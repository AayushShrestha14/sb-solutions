package com.sb.solutions.web.DmsloanFile.controller;

import com.sb.solutions.api.dms.dmsloanfile.entity.DmsLoanFile;
import com.sb.solutions.api.dms.dmsloanfile.repository.DmsLoanFileRepository;
import com.sb.solutions.api.dms.dmsloanfile.service.DmsLoanFileService;
import com.sb.solutions.api.memo.enums.Stage;
import com.sb.solutions.core.constant.FilePath;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.dto.SearchDto;
import com.sb.solutions.core.exception.GlobalExceptionHandler;
import com.sb.solutions.core.utils.PaginationUtils;
import com.sb.solutions.core.utils.uploadFile.UploadFile;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@RestController
@RequestMapping(value = "/v1/dmsLoanFile")
public class DmsLoanFileController {
    @Autowired
    private DmsLoanFileService dmsLoanFileService;
    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;
    @Autowired
    private UploadFile uploadFile;
    @Autowired
    private DmsLoanFileRepository dmsLoanFileRepository;

    private static final Logger logger = LoggerFactory.getLogger(DmsLoanFileController.class);

    @PostMapping
    public ResponseEntity<?> saveLoanFile(@Valid @RequestBody DmsLoanFile dmsLoanFile, BindingResult bindingResult) {
        globalExceptionHandler.constraintValidation(bindingResult);
        DmsLoanFile info = dmsLoanFileService.save(dmsLoanFile);
        if (info == null) {
            return new RestResponseDto().failureModel("Error Occurs");
        } else {
            return new RestResponseDto().successModel(info);
        }
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<?> getDmsLoanFileById(@PathVariable("id") Long id) {
        return new RestResponseDto().successModel(dmsLoanFileService.findOne(id));
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getLoanFiles() {
        return new RestResponseDto().successModel(dmsLoanFileService.findAll());
    }

    @GetMapping("/getList")
    public ResponseEntity<?> getLoanFile() {
        return new RestResponseDto().successModel(dmsLoanFileService.findAll());
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page.")})
    @RequestMapping(method = RequestMethod.POST, path = "/get")
    public ResponseEntity<?> getPageableLoanFile(@RequestBody SearchDto searchDto, @RequestParam("page") int page, @RequestParam("size") int size) {
        return new RestResponseDto().successModel(dmsLoanFileService.findAllPageable(searchDto, PaginationUtils.pageable(page, size)));
    }

    @PostMapping("/uploadFile")
    public ResponseEntity<?> uploadLoanFile(@RequestParam("file") MultipartFile multipartFile, @RequestParam("type") String type, @RequestParam("id") int id, @RequestParam("customerName") String name, @RequestParam("documentName") String documentName) {
        return uploadFile.uploadFile(multipartFile, type, id, name, documentName);

    }

    @GetMapping("/download")
    public ResponseEntity<?> downloadFile(@RequestParam("path") String path, HttpServletResponse response) throws FileNotFoundException {
        FilePath filePath = new FilePath();
        path = filePath.getOSPath() + path;
        File file = new File(path);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        ResponseEntity<Object> responseEntity = ResponseEntity.ok().headers(headers).contentLength(file.length()).contentType(
                MediaType.parseMediaType("application/txt")).body(resource);
        return responseEntity;
    }

    @GetMapping("/getLoan")
    public ResponseEntity<?> getLoanByStage(@RequestParam("stage") Stage stage) {
        return new RestResponseDto().successModel(dmsLoanFileRepository.findAllByStage(stage));
    }
}
