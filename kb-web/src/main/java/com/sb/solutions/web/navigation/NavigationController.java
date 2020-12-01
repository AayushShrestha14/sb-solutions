package com.sb.solutions.web.navigation;

import static com.sb.solutions.core.constant.AppConstant.ELIGIBILITY_PERMISSION;
import static com.sb.solutions.core.constant.AppConstant.ELIGIBILITY_PERMISSION_SUBNAV_GENERAL_QUESTION;
import static com.sb.solutions.core.constant.AppConstant.ELIGIBILITY_PERMISSION_SUBNAV_QUESTION;

import java.util.List;

import com.sb.solutions.api.authorization.approval.ApprovalRoleHierarchyService;
import com.sb.solutions.core.enums.RoleType;
import com.sb.solutions.core.utils.ApprovalType;
import com.sb.solutions.core.utils.ProductUtils;
import com.sb.solutions.web.navigation.dto.MenuDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sb.solutions.api.authorization.entity.RolePermissionRights;
import com.sb.solutions.api.authorization.service.RolePermissionRightService;
import com.sb.solutions.api.user.entity.User;
import com.sb.solutions.api.user.service.UserService;
import com.sb.solutions.core.constant.AppConstant;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.web.navigation.mapper.MenuMapper;

/**
 * @author Rujan Maharjan on 3/28/2019
 */
@RestController
@RequestMapping("/v1")
public class NavigationController {

    private final RolePermissionRightService rolePermissionRightService;

    private final ApprovalRoleHierarchyService approvalRoleHierarchyService;

    private final UserService userService;

    private final MenuMapper menuMapper;


    public NavigationController(
            RolePermissionRightService rolePermissionRightService,
            ApprovalRoleHierarchyService approvalRoleHierarchyService, UserService userService, MenuMapper menuMapper) {
        this.rolePermissionRightService = rolePermissionRightService;
        this.approvalRoleHierarchyService = approvalRoleHierarchyService;
        this.userService = userService;
        this.menuMapper = menuMapper;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/nav")
    public ResponseEntity<?> getNav() {
        User u = userService.getAuthenticatedUser();
        return new RestResponseDto()
                .successModel(rolePermissionRightService.getByRoleId(u.getRole().getId()));
    }

    @GetMapping("/menu")
    public ResponseEntity<?> getMenu() {
        User u = userService.getAuthenticatedUser();
        List<RolePermissionRights> rolePermissionRights = rolePermissionRightService
                .getByRoleId(u.getRole().getId());
          /*
        Beside admin user, no other user can set question for eligibility
        below logic is to guard
         */
        boolean hasEligibilityPermission = rolePermissionRights.stream()
                .anyMatch(r -> r.getPermission().getId() == ELIGIBILITY_PERMISSION);
        if (!u.getRole().getRoleName().equals(AppConstant.ADMIN_ROLE) && hasEligibilityPermission) {
            rolePermissionRights.stream().forEach(role -> {
                if (role.getPermission().getId() == ELIGIBILITY_PERMISSION) {
                    role.getPermission().getSubNavs()
                            .removeIf(subNav -> subNav.getId() == ELIGIBILITY_PERMISSION_SUBNAV_QUESTION
                                    || subNav.getId() == ELIGIBILITY_PERMISSION_SUBNAV_GENERAL_QUESTION);
                }
            });

        }
        List<MenuDto> menuList = menuMapper.menuDtoList(rolePermissionRights);
        if (ProductUtils.OFFER_LETTER) {
            boolean isPresentInCadHierarchy = approvalRoleHierarchyService.checkRoleContainInHierarchies(u.getRole().getId(), ApprovalType.CAD, 0l);
            if ((isPresentInCadHierarchy || u.getRole().getRoleType().equals(RoleType.CAD_ADMIN)) && (!ProductUtils.FULL_CAD)) {
                MenuDto menuDto = new MenuDto();
                menuDto.setTitle("Post Approval Documentation");
                menuDto.setLink("/home/loan/loan-offer-letter");
                menuDto.setIcon("arrowhead-down-outline");
                menuList.add(menuDto);
            }
        }
        return new RestResponseDto().successModel(menuList);
    }
}
