package com.sb.solutions.web.user.controller;


import com.sb.solutions.api.rolePermissionRight.entity.Role;
import com.sb.solutions.api.rolePermissionRight.service.RoleService;
import com.sb.solutions.api.user.entity.User;
import com.sb.solutions.api.user.service.UserService;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.dto.SearchDto;
import com.sb.solutions.core.utils.CustomPageable;
import com.sb.solutions.core.utils.uploadFile.UploadFile;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;


/**
 * @author Sunil Babu Shrestha on 12/27/2018
 */
@RestController
@RequestMapping("/v1/user")
public class UserController {

    private final UserService userService;
    private RoleService roleService;
    private UploadFile uploadFile;
    @Autowired
    public UserController (UserService userService,RoleService roleService, UploadFile uploadFile){
        this.userService = userService;
        this.roleService = roleService;
        this.uploadFile = uploadFile;
    }
    private String signaturePath=null;
    private String profiePath=null;
    @GetMapping(path = "/authenticated")
    public ResponseEntity<?> getAuthenticated() {
    return new RestResponseDto().successModel(userService.getAuthenticated());
    }


    @PostMapping
    public ResponseEntity<?> saveUser(@RequestBody User user) {
        System.out.println("here");
        if(profiePath != null) {
            user.setProfilePicture(profiePath);
            profiePath=null;
        }
        if(signaturePath != null) {
            user.setSignatureImage(signaturePath);
            signaturePath=null;
        }
        user.toString();
        return new RestResponseDto().successModel(userService.save(user));
    }
    @PostMapping(value = "/uploadFile")
    public ResponseEntity<?> saveUserFile(@RequestParam("file") MultipartFile multipartFile, @RequestParam("type") String type) {
        return uploadFile.uploadFile(multipartFile,type);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page.")})
    @PostMapping(value = "/get")
    public ResponseEntity<?> getAll(@RequestBody User user, @RequestParam("page") int page, @RequestParam("size") int size) {
        return new RestResponseDto().successModel(userService.findAllPageable(user,new CustomPageable().pageable(page, size)));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page.")})
    @PostMapping(value = "listByRole")
    public ResponseEntity<?> getUserByRole(@RequestBody Collection<Role> roles, @RequestParam("page") int page, @RequestParam("size") int size){
        return new RestResponseDto().successModel(userService.findByRole(roles, new CustomPageable().pageable(page, size)));
    }

    @GetMapping(value = "listRole")
    public  ResponseEntity<?> getRoleList(){
        return new RestResponseDto().successModel(roleService.findAll());
    }


    @RequestMapping(method = RequestMethod.GET, path = "/get/statusCount")
    public ResponseEntity<?> getUserStatusCount() {
        return new RestResponseDto().successModel(userService.userStatusCount());
    }


}
