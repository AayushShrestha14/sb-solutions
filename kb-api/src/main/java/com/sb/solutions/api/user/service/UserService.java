package com.sb.solutions.api.user.service;

import com.sb.solutions.api.branch.entity.Branch;
import com.sb.solutions.api.rolePermissionRight.entity.Role;
import com.sb.solutions.api.user.entity.User;
import com.sb.solutions.core.dto.SearchDto;
import com.sb.solutions.core.service.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Sunil Babu Shrestha on 12/31/2018
 */


public interface UserService extends BaseService<User> ,UserDetailsService {

    User getAuthenticated();

    User getByUsername(String username);

    User save(User user);

    Page<User> findByRole(Collection<Role> roles, Pageable pageable);

    Map<Object, Object> userStatusCount();

    List<User>  findByRoleId(Long id);

    List<User> findByRoleAndBranch(Long roleId,List<Long> branchIds);

    String csv(SearchDto searchDto);

    List<Long> getRoleAccessFilterByBranch();



}
