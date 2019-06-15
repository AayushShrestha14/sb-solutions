package com.sb.solutions.web.user;

import com.sb.solutions.api.rolePermissionRight.entity.RoleHierarchy;
import com.sb.solutions.api.rolePermissionRight.service.RoleHierarchyService;
import com.sb.solutions.api.user.entity.User;
import com.sb.solutions.api.user.service.UserService;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.web.user.dto.RoleHierarchyDto;
import com.sb.solutions.web.user.mapper.RoleHierarchyMapper;

/**
 * @author Rujan Maharjan on 5/13/2019
 */

@RestController
@RequestMapping("/v1/role-hierarchy")
public class RoleHierarchyController {


    private final Logger logger = LoggerFactory.getLogger(RoleHierarchyController.class);

    private final RoleHierarchyMapper roleHierarchyMapper;

    private final RoleHierarchyService roleHierarchyService;

    private final UserService userService;

    public RoleHierarchyController(
        @Autowired RoleHierarchyMapper roleHierarchyMapper,
        @Autowired RoleHierarchyService roleHierarchyService,
        @Autowired UserService userService) {
        this.roleHierarchyMapper = roleHierarchyMapper;
        this.roleHierarchyService = roleHierarchyService;
        this.userService = userService;
    }


    @PostMapping
    public ResponseEntity<?> saveRoleHierarchyList(
        @RequestBody List<RoleHierarchyDto> roleHierarchyDtoList) {
        final List<RoleHierarchy> roleHierarchies = roleHierarchyService
            .SaveList(roleHierarchyMapper.mapDtosToEntities(roleHierarchyDtoList));

        if (roleHierarchies.isEmpty()) {
            logger.error("Error while saving Role_Hierarchy {}", roleHierarchyDtoList);
            return new RestResponseDto()
                .failureModel("Error occurred while saving Role_Hierarchy " + roleHierarchyDtoList);
        }
        return new RestResponseDto()
            .successModel(roleHierarchyMapper.mapEntitiesToDtos(roleHierarchies));
    }


    @GetMapping("/all")
    public ResponseEntity<?> getRoleHierarchyList() {
        return new RestResponseDto()
            .successModel(roleHierarchyMapper.mapEntitiesToDtos(roleHierarchyService.findAll()));
    }

    @GetMapping("/getForward")
    public ResponseEntity<?> getRoleHierarchyListPerRoleForward() {
        User u = userService.getAuthenticated();
        return new RestResponseDto().successModel(roleHierarchyService.roleHierarchyByCurrentRoleForward(u.getRole().getId()));
    }

    @GetMapping("/getBackward")
    public ResponseEntity<?> getRoleHierarchyListPerRoleBackward() {
        User u = userService.getAuthenticated();
        return new RestResponseDto().successModel(
            roleHierarchyService.roleHierarchyByCurrentRoleBackward(u.getRole().getId()));
    }
}
