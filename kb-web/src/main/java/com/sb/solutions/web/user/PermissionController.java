package com.sb.solutions.web.user;

import com.sb.solutions.api.rolePermissionRight.entity.Permission;
import com.sb.solutions.api.rolePermissionRight.service.PermissionService;
import com.sb.solutions.api.user.entity.User;
import com.sb.solutions.api.user.service.UserService;
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

/**
 * @author Rujan Maharjan on 3/28/2019
 */
@RestController
@RequestMapping("/v1/permission")
public class PermissionController {

    @Autowired
    PermissionService permissionService;

    @Autowired
    UserService userService;

    @Autowired
    GlobalExceptionHandler globalExceptionHandler;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> savePermission(@Valid @RequestBody Permission permission, BindingResult bindingResult) {
        globalExceptionHandler.constraintValidation(bindingResult);
        Permission r = permissionService.save(permission);
        if (r == null) {
            return new RestResponseDto().failureModel("Error Occurs");
        } else {
            return new RestResponseDto().successModel(r.getPermissionName());
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getPermission() {
        return new RestResponseDto().successModel(permissionService.findAll());
    }

    @RequestMapping(method = RequestMethod.POST, path = "/chkPerm")
    public ResponseEntity<?> getPermChk(@RequestBody String name) {
        User u = userService.getAuthenticated();
        return new RestResponseDto().successModel(permissionService.permsRight(name, u.getRole().getRoleName()));
    }

}
