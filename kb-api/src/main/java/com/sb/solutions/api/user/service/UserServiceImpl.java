package com.sb.solutions.api.user.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sb.solutions.api.basehttp.BaseHttpService;
import com.sb.solutions.api.branch.entity.Branch;
import com.sb.solutions.api.rolePermissionRight.entity.Role;
import com.sb.solutions.api.user.entity.User;
import com.sb.solutions.api.user.repository.UserRepository;
import com.sb.solutions.core.constant.UploadDir;
import com.sb.solutions.core.dto.SearchDto;
import com.sb.solutions.core.enums.Status;
import com.sb.solutions.core.utils.csv.CsvMaker;
import lombok.AllArgsConstructor;

/**
 * @author Sunil Babu Shrestha on 12/31/2018
 */
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    BaseHttpService baseHttpService;
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

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
        if (authentication.getPrincipal() instanceof UserDetails) {
            User user = (User) authentication.getPrincipal();
            user = this.getByUsername(user.getUsername());
            return user;
        } else {
            throw new UsernameNotFoundException(
                "User is not authenticated; Found " + " of type " + authentication.getPrincipal()
                    .getClass() + "; Expected type User");
        }
    }


    @Override
    public User getByUsername(String username) {
        return userRepository.getUsersByUsername(username);

    }

    @Override
    public User save(User user) {
        user.setLastModifiedAt(new Date());
        if (user.getId() == null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setStatus(Status.ACTIVE);
        } else {
            user.setPassword(userRepository.getOne(user.getId()).getPassword());
        }
        User u = userRepository.findByRoleIdAndBranch(user.getRole().getId(), user.getBranch());
        if (u == null) {
            return userRepository.save(user);
        } else {
            throw new RuntimeException(
                "USER OF ROLE " + u.getRole().getRoleName() + " ALREADY EXIST IN BRANCH " + u
                    .getBranch().getName());
        }

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
    public User findByRoleAndBranch(Long roleId, Branch branchId) {
        return userRepository.findByRoleIdAndBranch(roleId, branchId);
    }

    @Override
    public String csv(SearchDto searchDto) {
        CsvMaker csvMaker = new CsvMaker();
        List branchList = userRepository
            .userCsvFilter(searchDto.getName() == null ? "" : searchDto.getName());
        Map<String, String> header = new LinkedHashMap<>();
        header.put("name", " Name");
        header.put("email", "Email");
        header.put("branch,name", "Branch name");
        header.put("branch,address", "Branch Address");
        header.put("role,roleName", "Role");
        header.put("status", "Status");
        String url = csvMaker.csv("user", header, branchList, UploadDir.userCsv);
        return baseHttpService.getBaseUrl() + url;
    }

    @Override
    public Page<User> findAllPageable(Object object, Pageable pageable) {
        ObjectMapper objectMapper = new ObjectMapper();
        SearchDto s = objectMapper.convertValue(object, SearchDto.class);
        return userRepository.userFilter(s.getName() == null ? "" : s.getName(), pageable);

    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = userRepository.getUsersByUsername(username);
        if (u != null) {
            List<String> authorityList = userRepository
                .userApiAuthorities(u.getRole().getId(), u.getUsername()).stream()
                .map(object -> Objects.toString(object, null))
                .collect(Collectors.toList());
            Collection<GrantedAuthority> oldAuthorities = (Collection<GrantedAuthority>) SecurityContextHolder
                .getContext().getAuthentication().getAuthorities();
            List<GrantedAuthority> updatedAuthorities = new ArrayList<GrantedAuthority>();
            for (String a : authorityList) {
                updatedAuthorities.add(new SimpleGrantedAuthority("a"));
            }
            updatedAuthorities.addAll(oldAuthorities);
            SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                    SecurityContextHolder.getContext().getAuthentication().getPrincipal(),
                    u.getPassword(),
                    updatedAuthorities)
            );

            u.setAuthorityList(authorityList);

        }
        return u;
    }
}
