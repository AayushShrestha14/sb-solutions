package com.sb.solutions.api.user.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;

import com.sb.solutions.api.basehttp.BaseHttpService;
import com.sb.solutions.api.branch.entity.Branch;
import com.sb.solutions.api.branch.repository.BranchRepository;
import com.sb.solutions.api.rolePermissionRight.entity.Role;
import com.sb.solutions.api.rolePermissionRight.repository.RoleRepository;
import com.sb.solutions.api.user.entity.User;
import com.sb.solutions.api.user.repository.UserRepository;
import com.sb.solutions.core.config.security.CustomJdbcTokenStore;
import com.sb.solutions.core.constant.UploadDir;
import com.sb.solutions.core.dto.SearchDto;
import com.sb.solutions.core.enums.RoleAccess;
import com.sb.solutions.core.enums.Status;
import com.sb.solutions.core.utils.csv.CsvMaker;

/**
 * @author Sunil Babu Shrestha on 12/31/2018
 */
@Service("userDetailService")
public class UserServiceImpl implements UserService {

    private final BaseHttpService baseHttpService;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final BranchRepository branchRepository;
    private final RoleRepository roleRepository;
    private final CustomJdbcTokenStore customJdbcTokenStore;

    public UserServiceImpl(@Autowired BaseHttpService baseHttpService,
        @Autowired UserRepository userRepository,
        @Autowired BranchRepository branchRepository,
        @Autowired RoleRepository roleRepository,
        @Autowired CustomJdbcTokenStore customJdbcTokenStore,
        @Autowired BCryptPasswordEncoder passwordEncoder) {
        this.baseHttpService = baseHttpService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.branchRepository = branchRepository;
        this.roleRepository = roleRepository;
        this.customJdbcTokenStore = customJdbcTokenStore;

    }

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
        if (user.getId() == null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setStatus(Status.ACTIVE);
        } else {
            user.setPassword(userRepository.getOne(user.getId()).getPassword());
        }
        if (user.getRole().getRoleAccess().equals(RoleAccess.OWN)) {
            if (user.getBranch().isEmpty() || (user.getBranch().size() > 1)) {
                throw new InvalidPropertyException(User.class, "Branch",
                    "Branch can not be null or multi selected");
            }
        }

        if (user.getRole().getRoleAccess().equals(RoleAccess.SPECIFIC)) {
            if (user.getBranch().isEmpty()) {
                throw new InvalidPropertyException(User.class, "Branch", "Branch can not be null");
            }
        }

        if (user.getRole().getRoleAccess().equals(RoleAccess.ALL)) {
            if (!user.getBranch().isEmpty()) {

                throw new InvalidPropertyException(User.class, "Branch",
                    "Branch can not be selected For role");
            }
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
    public List<User> findByRoleId(Long id) {
        return userRepository.findByRoleId(id);
    }

    @Override
    public List<User> findByRoleAndBranch(Long roleId, List<Long> branchIds) {
        Role r = roleRepository.getOne(roleId);
        if (r.getRoleAccess().equals(RoleAccess.ALL)) {
            return userRepository.findByRoleRoleAccessAndRoleNotAndRoleId(RoleAccess.ALL,
                roleRepository.getOne(Long.valueOf(1)), roleId);
        }
        return userRepository.findByRoleIdAndBranch(roleId, this.getRoleAccessFilterByBranch());
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
    public List<Long> getRoleAccessFilterByBranch() {
        User u = this.getAuthenticated();
        List<Long> branchIdList = new ArrayList<>();
        String branchIdListTOString = null;
        if (u.getRole().getRoleAccess() != null) {
            if (u.getRole().getRoleAccess().equals(RoleAccess.SPECIFIC) || u.getRole()
                .getRoleAccess().equals(RoleAccess.OWN)) {
                for (Branch b : u.getBranch()) {
                    branchIdList.add(b.getId());
                }
            }

            if (u.getRole().getRoleAccess().equals(RoleAccess.ALL)) {
                for (Branch b : this.branchRepository.findAll()) {
                    branchIdList.add(b.getId());
                }
            }

        }

        if (branchIdList.isEmpty()) {
            branchIdList.add(0L);
        }

        return branchIdList;
    }

    @Override
    public String dismissAllBranchAndRole(User user) {
        user.setBranch(new ArrayList<Branch>());
        user.setStatus(Status.INACTIVE);
        user.setRole(null);
        userRepository.save(user);
        Collection<OAuth2AccessToken> token = customJdbcTokenStore
            .findTokensByUserName(user.getUsername());
        for (OAuth2AccessToken tempToken : token) {
            customJdbcTokenStore.removeAccessToken(tempToken);
            customJdbcTokenStore.removeRefreshToken(tempToken.getRefreshToken());
        }
        return "SUCCESS";
    }

    @Override
    public User updatePassword(String username, String password) {
        User user = userRepository.getUsersByUsername(username);
        user.setResetPasswordToken(null);
        user.setResetPasswordTokenExpiry(null);
        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = userRepository.getUsersByUsernameAndStatus(username, Status.ACTIVE);
        if (u != null) {
            List<String> authorityList = userRepository
                .userApiAuthorities(u.getRole().getId(), u.getUsername()).stream()
                .map(object -> Objects.toString(object, null))
                .collect(Collectors.toList());
            Collection<GrantedAuthority> oldAuthorities = (Collection<GrantedAuthority>) SecurityContextHolder
                .getContext().getAuthentication().getAuthorities();
            List<GrantedAuthority> updatedAuthorities = new ArrayList<>();
            for (String a : authorityList) {
                updatedAuthorities.add(new SimpleGrantedAuthority(a));
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
