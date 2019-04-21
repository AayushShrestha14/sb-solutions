package com.sb.solutions.api.user.service;

import com.sb.solutions.api.basehttp.BaseHttpService;
import com.sb.solutions.api.rolePermissionRight.entity.Role;
import com.sb.solutions.api.user.entity.User;
import com.sb.solutions.api.user.repository.UserRepository;
import com.sb.solutions.core.constant.UploadDir;
import com.sb.solutions.core.dto.SearchDto;
import com.sb.solutions.core.enums.Status;
import lombok.AllArgsConstructor;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Sunil Babu Shrestha on 12/31/2018
 */
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;
    BaseHttpService baseHttpService;



    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findOne(Long id) {
        return userRepository.getOne(id);
    }

    @Override
    public User getAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof org.springframework.security.core.userdetails.User) {
            org.springframework.security.core.userdetails.User userDetail = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
            User u = this.getByUsername(userDetail.getUsername());
            return u;
        } else {
            throw new UsernameNotFoundException("User is not authenticated; Found " + authentication.getPrincipal() + " of type " + authentication.getPrincipal().getClass() + "; Expected type User");
        }
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.getUsersByUserName(username);
    }

    @Override
    public User save(User user) {
        System.out.println(user.getPassword());
        user.setLastModifiedAt(new Date());
        if (user.getId() == null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setStatus(Status.ACTIVE);
        }else{
            user.setPassword(userRepository.getOne(user.getId()).getPassword());
        }
        return userRepository.save(user);

    }

    @Override
    public Page<User> findByRole(Collection<Role> roles, Pageable pageable) {
        return userRepository.findByRoleIn(roles, pageable);
    }


    @Override
    public Map<Object, Object> userStatusCount() {
        return userRepository.userStatusCount();
    }



    @Override
    public Page<User> findAllPageable(User object, Pageable pageable) {
        ObjectMapper objectMapper = new ObjectMapper();
        SearchDto s = objectMapper.convertValue(object, SearchDto.class);
        return userRepository.userFilter(s.getName() == null ? "" : s.getName(), pageable);

    }

}
