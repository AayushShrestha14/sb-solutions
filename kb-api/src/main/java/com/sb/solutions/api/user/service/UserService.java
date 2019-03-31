package com.sb.solutions.api.user.service;

import com.sb.solutions.api.user.entity.Role;
import com.sb.solutions.api.user.entity.User;
import com.sb.solutions.core.service.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

/**
 * @author Sunil Babu Shrestha on 12/31/2018
 */


public interface UserService extends BaseService<User> {

    User getAuthenticated();

    User getByUsername(String username);

    User save(User user);
    Page<User> findByRole(Collection<Role> roles, Pageable pageable);

    User getUserByFingerPrint(MultipartFile file);

}
