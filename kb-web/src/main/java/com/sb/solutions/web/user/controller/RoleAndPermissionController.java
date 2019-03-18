package com.sb.solutions.web.user.controller;

import com.sb.solutions.api.roleAndPermission.entity.Role;
import com.sb.solutions.api.roleAndPermission.service.RoleAndPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Rujan Maharjan on 3/18/2019.
 */

@RestController
@RequestMapping("/v1/role")
public class RoleAndPermissionController {
    @Autowired
    RoleAndPermissionService roleAndPermissionService;

    @PostMapping
    public void save(@RequestBody Role role){
        System.out.println(role);
    }

    @GetMapping
    public List test(){
     return   roleAndPermissionService.findAll();
    }
}
