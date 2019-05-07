package com.sb.solutions.web.clientInfo.controller;

import com.sb.solutions.api.clientInfo.entity.ClientInfo;
import com.sb.solutions.api.clientInfo.service.ClientInfoService;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.dto.SearchDto;
import com.sb.solutions.core.exception.GlobalExceptionHandler;
import com.sb.solutions.core.utils.PaginationUtils;
import com.sb.solutions.core.utils.uploadFile.UploadFile;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/v1/clientInfo")
public class ClientInfoController {
    @Autowired
    private ClientInfoService clientInfoService;
    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;
    @Autowired
    private UploadFile uploadFile;

    @PostMapping
    public ResponseEntity<?> saveCustomerInfo(@Valid @RequestBody ClientInfo clientInfo, BindingResult bindingResult) {
        globalExceptionHandler.constraintValidation(bindingResult);
        ClientInfo info = clientInfoService.save(clientInfo);
        if (info == null) {
            return new RestResponseDto().failureModel("Error Occurs");
        } else {
            return new RestResponseDto().successModel(info);
        }
    }

    @GetMapping("/getList")
    public ResponseEntity<?> getCustomerInfo() {
        return new RestResponseDto().successModel(clientInfoService.findAll());
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page.")})
    @RequestMapping(method = RequestMethod.POST, path = "/get")
    public ResponseEntity<?> getPageableCustomerInfo(@RequestBody SearchDto searchDto, @RequestParam("page") int page, @RequestParam("size") int size) {
        return new RestResponseDto().successModel(clientInfoService.findAllPageable(searchDto, PaginationUtils.pageable(page, size)));
    }
    @PostMapping("/uploadFile")
    public ResponseEntity<?> uploadClientDocument(@RequestParam("file")MultipartFile multipartFile, @RequestParam("type")String type, @RequestParam("clientName")String name, @RequestParam("documentName") String documentName){
        return uploadFile.uploadFile(multipartFile,type,name,documentName);
    }

}
