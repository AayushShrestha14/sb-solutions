package com.sb.solutions.web.user.controller;


import com.sb.solutions.api.rolePermissionRight.entity.Role;
import com.sb.solutions.api.rolePermissionRight.service.RoleService;
import com.sb.solutions.api.user.entity.User;
import com.sb.solutions.api.user.service.UserService;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.dto.SearchDto;
import com.sb.solutions.core.utils.CustomPageable;
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
    private UserService userService;
    private RoleService roleService;
    @Autowired
    public UserController (UserService userService,RoleService roleService){
        this.userService= userService;
        this.roleService= roleService;
    }
    private String signaturePath;
    private String profiePath;
    @GetMapping(path = "/authenticated")
    public ResponseEntity<?> getAuthenticated() {
    return new RestResponseDto().successModel(userService.getAuthenticated());
    }


    @PostMapping
    public ResponseEntity<?> saveUser(@RequestBody User user) {
        System.out.println("here");
        if(!profiePath.equals(null)) {
            user.setProfilePicture(profiePath);
        }
        if(!signaturePath.equals(null)) {
            user.setSignatureImage(signaturePath);
        }
        return new RestResponseDto().successModel(userService.save(user));
    }
    @PostMapping(value = "/uploadSignature")
    public ResponseEntity<?> saveUserSignature(@RequestBody MultipartFile multipartFile) {

        if (multipartFile.isEmpty()) {
            return new RestResponseDto().failureModel("Select Signature Image");
        }

        try {
            byte[] bytes = multipartFile.getBytes();
            Path path = Paths.get("C:\\Users\\GOAT\\Desktop\\Images\\signature" + multipartFile.getOriginalFilename());
            signaturePath = path.toString();
            Files.write(path, bytes);
            return new RestResponseDto().successModel("Uploaded");
        } catch (IOException e) {
            e.printStackTrace();
            return new RestResponseDto().failureModel("Upload Unsuccessful");
        }
    }

    @PostMapping(value = "/uploadProfile")
    public ResponseEntity<?> saveUserProfile(@RequestBody MultipartFile multipartFile) {

        if (multipartFile.isEmpty()) {
            return new RestResponseDto().failureModel("Select Profile Image");
        }

        try {
            byte[] bytes = multipartFile.getBytes();
            Path path = Paths.get("C:\\Users\\GOAT\\Desktop\\Images\\profile" + multipartFile.getOriginalFilename());
            profiePath = path.toString();
            Files.write(path, bytes);
            return new RestResponseDto().successModel("Uploaded");
        } catch (IOException e) {
            e.printStackTrace();
            return new RestResponseDto().failureModel("Upload Unsuccessful");
        }
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page.")})
    @PostMapping(value = "/get")
    public ResponseEntity<?> getAll(@RequestBody SearchDto searchDto, @RequestParam("page") int page, @RequestParam("size") int size) {
        return new RestResponseDto().successModel(userService.findAllPageable(searchDto,new CustomPageable().pageable(page, size)));
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
    @PostMapping(value = "checkFingerPrint")
    public  ResponseEntity<?> checkFingerPrint(@RequestBody MultipartFile file){
        return new RestResponseDto().successModel(userService.getUserByFingerPrint(file));
    }



}
