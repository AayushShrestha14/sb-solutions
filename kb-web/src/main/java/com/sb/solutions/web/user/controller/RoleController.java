package com.sb.solutions.web.user.controller;

import com.sb.solutions.api.rolePermissionRight.entity.Role;
import com.sb.solutions.api.rolePermissionRight.service.RoleService;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.exception.GlobalExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Rujan Maharjan on 3/28/2019
 */

@RestController
@RequestMapping("/v1/role")
public class RoleController {

    @Autowired
    RoleService roleService;

    @Autowired
    GlobalExceptionHandler globalExceptionHandler;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> saveRole(@Valid @RequestBody Role role, BindingResult bindingResult) {
        globalExceptionHandler.constraintValidation(bindingResult);
        Role r = roleService.save(role);
        if (r == null) {
            return new RestResponseDto().failureModel("Error Occurs");
        } else {
            return new RestResponseDto().successModel(r.getRoleName());
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getRole() {
        return new RestResponseDto().successModel(roleService.findAll());
    }



}
