package com.sb.solutions.web.user;

import com.sb.solutions.api.rolePermissionRight.entity.Role;
import com.sb.solutions.api.rolePermissionRight.entity.RoleHierarchy;
import com.sb.solutions.api.rolePermissionRight.service.RoleHierarchyService;
import com.sb.solutions.api.rolePermissionRight.service.RoleService;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.exception.GlobalExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.ws.rs.Produces;
import java.util.List;

/**
 * @author Rujan Maharjan on 3/28/2019
 */

@RestController
@RequestMapping("/v1/role")
public class RoleController {

    @Autowired
    RoleService roleService;

    @Autowired
    RoleHierarchyService roleHierarchyService;

    @Autowired
    GlobalExceptionHandler globalExceptionHandler;

    @RequestMapping(method = RequestMethod.POST)

    public ResponseEntity<?> saveRole(@Valid @RequestBody Role role, BindingResult bindingResult) {
        globalExceptionHandler.constraintValidation(bindingResult);
        Role r = roleService.save(role);
        if (r == null) {
            return new RestResponseDto().failureModel("Error Occurs");
        } else {
            if (role.getId() != null) {
                List<RoleHierarchy> roleHierarchies = roleHierarchyService.findAll();
                RoleHierarchy roleHierarchy = new RoleHierarchy();
                roleHierarchy.setRole(r);
                roleHierarchy.setRoleOrder((roleHierarchies.size())+1L);
                roleHierarchyService.save(roleHierarchy);
            }
            return new RestResponseDto().successModel(r.getRoleName());
        }
    }

    @RequestMapping(method = RequestMethod.GET)
        @Produces("application/json")
    public ResponseEntity<?> getRole() {
        return new RestResponseDto().successModel(roleService.findAll());
    }


    @RequestMapping(method = RequestMethod.GET, path = "/get/statusCount")
    public ResponseEntity<?> getRoleStatusCount() {
        return new RestResponseDto().successModel(roleService.roleStatusCount());
    }

    @RequestMapping(method = RequestMethod.GET, path = "/active")
    public ResponseEntity<?> getActiveRole() {
        return new RestResponseDto().successModel(roleService.activeRole());
    }


}
