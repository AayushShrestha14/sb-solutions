package com.sb.solutions.api.user.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.sb.solutions.api.rolePermissionRight.dto.RoleDto;
import com.sb.solutions.api.rolePermissionRight.entity.Role;
import com.sb.solutions.api.user.PieChartDto;
import com.sb.solutions.api.user.entity.User;
import com.sb.solutions.core.service.BaseService;

/**
 * @author Sunil Babu Shrestha on 12/31/2018
 */


public interface UserService extends BaseService<User>, UserDetailsService {

    User getAuthenticated();

    User getByUsername(String username);

    User save(User user);

    Page<User> findByRole(Collection<Role> roles, Pageable pageable);

    Map<Object, Object> userStatusCount();

    List<User> findByRoleId(Long id);

    List<User> findByRoleAndBranch(Long roleId, List<Long> branchIds);
    List<User> findByRoleIdAndIsDefaultCommittee(Long roleId, Boolean isTrue);

    List<User> findByRoleAndBranchId(Long roleId, Long branchId);

    String csv(Object searchDto);

    List<Long> getRoleAccessFilterByBranch();

    String dismissAllBranchAndRole(User user);

    User updatePassword(String username, String password);

    List<PieChartDto> getStatisticsForBranchVsUsers();

    List<PieChartDto> getStatisticsForRolesVsUsers();

    List<RoleDto> getRoleWiseBranchWiseUserList(Long roleId, Long branchId, Long userId);

    boolean checkIfValidOldPassword(User user, String password);
}
