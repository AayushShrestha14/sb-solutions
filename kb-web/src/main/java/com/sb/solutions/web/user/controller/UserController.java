package com.sb.solutions.web.user.controller;


import com.sb.solutions.api.user.entity.User;
import com.sb.solutions.api.user.entity.UserType;
import com.sb.solutions.api.user.service.UserService;
import com.sb.solutions.api.user.service.UserTypeService;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.utils.CustomPageable;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * @author Sunil Babu Shrestha on 12/27/2018
 */
@AllArgsConstructor
@RestController
@RequestMapping("/v1/user")
public class UserController {
    private UserService userService;
    private UserTypeService userTypeService;

    @GetMapping(path = "/authenticated")
    public ResponseEntity<?> getAuthenticated() {
    return new RestResponseDto().successModel(userService.getAuthenticated());
    }


    @PostMapping
    public ResponseEntity<?> saveUser(@RequestBody User user) {
        return new RestResponseDto().successModel(userService.save(user));
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
    @PostMapping(value = "listByUserType")
    public ResponseEntity<?> getUserByUserType(@RequestBody Collection<UserType> userTypes, @RequestParam("page") int page, @RequestParam("size") int size){
        return new RestResponseDto().successModel(userService.findByUserType(userTypes, new CustomPageable().pageable(page, size)));
    }

    @GetMapping(value = "listUserType")
    public  ResponseEntity<?> getUserTypeList(){
        return new RestResponseDto().successModel(userTypeService.findAll());
    }



}
