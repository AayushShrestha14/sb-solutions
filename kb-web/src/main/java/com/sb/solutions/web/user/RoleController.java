package com.sb.solutions.web.user;

import java.util.List;
import javax.validation.Valid;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sb.solutions.api.rolePermissionRight.entity.Role;
import com.sb.solutions.api.rolePermissionRight.entity.RoleHierarchy;
import com.sb.solutions.api.rolePermissionRight.service.RoleHierarchyService;
import com.sb.solutions.api.rolePermissionRight.service.RoleService;
import com.sb.solutions.core.dto.RestResponseDto;

/**
 * @author Rujan Maharjan on 3/28/2019
 */

@RestController
@RequestMapping("/v1/role")
public class RoleController {

    private final RoleService roleService;

    private final RoleHierarchyService roleHierarchyService;

    public RoleController(
            @Autowired RoleService roleService,
            @Autowired RoleHierarchyService roleHierarchyService) {
        this.roleService = roleService;
        this.roleHierarchyService = roleHierarchyService;
    }

    @RequestMapping(method = RequestMethod.POST)

    public ResponseEntity<?> saveRole(@Valid @RequestBody Role role) {
        final Role r = roleService.save(role);

        if (r == null) {
            return new RestResponseDto().failureModel("Error Occurs");
        } else {
            if (role.getId() != null) {
                List<RoleHierarchy> roleHierarchies = roleHierarchyService.findAll();
                RoleHierarchy roleHierarchy = new RoleHierarchy();
                roleHierarchy.setRole(r);
                roleHierarchy.setRoleOrder((roleHierarchies.size()) + 1L);
                roleHierarchyService.save(roleHierarchy);
            }
            return new RestResponseDto().successModel(r.getRoleName());
        }
    }

    @GetMapping("/all")
    @Produces("application/json")
    public ResponseEntity<?> getRole() {
        return new RestResponseDto().successModel(roleService.findAll());
    }

    @RequestMapping(method = RequestMethod.GET, path = "/statusCount")
    public ResponseEntity<?> getRoleStatusCount() {
        return new RestResponseDto().successModel(roleService.roleStatusCount());
    }

    @GetMapping("/{id}")
    @Produces("application/json")
    public ResponseEntity<?> getByRoleId(@PathVariable("id") Long id) {
        return new RestResponseDto().successModel(roleService.findOne(id));
    }

    @RequestMapping(method = RequestMethod.GET, path = "/active")
    public ResponseEntity<?> getActiveRole() {
        return new RestResponseDto().successModel(roleService.activeRole());
    }

    @GetMapping(value = "maker")
    public ResponseEntity<?> chkMaker() {
        return new RestResponseDto().successModel(roleService.isMaker());
    }

    @GetMapping(value = "getApproval")
    public ResponseEntity<?> getApproval() {
        return new RestResponseDto().successModel(roleService.getApproval());
    }
}
