package com.sb.solutions.web.navigation;

import com.sb.solutions.api.rolePermissionRight.entity.RolePermissionRights;
import com.sb.solutions.api.rolePermissionRight.service.RolePermissionRightService;
import com.sb.solutions.api.user.entity.User;
import com.sb.solutions.api.user.service.UserService;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.web.navigation.mapper.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Rujan Maharjan on 3/28/2019
 */
@RestController
@RequestMapping("/v1")
public class NavigationController {

    @Autowired
    RolePermissionRightService rolePermissionRightService;

    @Autowired
    UserService userService;

    @Autowired
    MenuMapper menuMapper;

    @RequestMapping(method = RequestMethod.GET, path = "/nav")
    public ResponseEntity<?> getNav() {
        User u = userService.getAuthenticated();
        return new RestResponseDto().successModel(rolePermissionRightService.getByRoleId(u.getRole().getId()));
    }

    @GetMapping("/menu")
    public ResponseEntity<?> getMenu() {
        User u = userService.getAuthenticated();
        List<RolePermissionRights> rolePermissionRights = rolePermissionRightService.getByRoleId(u.getRole().getId());
        return new RestResponseDto().successModel(menuMapper.menuDtoList(rolePermissionRights));
    }
}
