package com.sb.solutions.api.user.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.sb.solutions.api.address.province.entity.Province;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.domain.Specification;
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
import org.springframework.util.ObjectUtils;

import com.sb.solutions.api.authorization.dto.RoleDto;
import com.sb.solutions.api.authorization.entity.Role;
import com.sb.solutions.api.authorization.repository.RoleRepository;
import com.sb.solutions.api.branch.dto.BranchDto;
import com.sb.solutions.api.branch.entity.Branch;
import com.sb.solutions.api.branch.repository.BranchRepository;
import com.sb.solutions.api.loan.repository.CustomerLoanRepository;
import com.sb.solutions.api.user.dto.UserDto;
import com.sb.solutions.api.user.entity.User;
import com.sb.solutions.api.user.repository.UserRepository;
import com.sb.solutions.api.user.repository.specification.UserSpecBuilder;
import com.sb.solutions.core.config.security.CustomJdbcTokenStore;
import com.sb.solutions.core.constant.UploadDir;
import com.sb.solutions.core.enums.DocStatus;
import com.sb.solutions.core.enums.RoleAccess;
import com.sb.solutions.core.enums.RoleType;
import com.sb.solutions.core.enums.Status;
import com.sb.solutions.core.exception.ServiceValidationException;
import com.sb.solutions.core.utils.FilterJsonUtils;
import com.sb.solutions.core.utils.csv.CsvMaker;

/**
 * @author Sunil Babu Shrestha on 12/31/2018
 */
@Service("userDetailService")
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final BranchRepository branchRepository;
    private final RoleRepository roleRepository;
    private final CustomerLoanRepository customerLoanRepository;
    private final CustomJdbcTokenStore customJdbcTokenStore;

    public UserServiceImpl(
        @Autowired UserRepository userRepository,
        @Autowired BranchRepository branchRepository,
        @Autowired RoleRepository roleRepository,
        @Autowired CustomJdbcTokenStore customJdbcTokenStore,
        @Autowired BCryptPasswordEncoder passwordEncoder,
        @Autowired CustomerLoanRepository customerLoanRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.branchRepository = branchRepository;
        this.roleRepository = roleRepository;
        this.customJdbcTokenStore = customJdbcTokenStore;
        this.customerLoanRepository = customerLoanRepository;
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
    public User getAuthenticatedUser() {
        final Authentication authentication = SecurityContextHolder.getContext()
            .getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetails) {
            User user = (User) authentication.getPrincipal();
            return this.getByUsername(user.getUsername());
        } else {
            logger.error("User not authenticated or invalid {}", authentication);
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

        if (user.getRole().getRoleAccess().equals(RoleAccess.SPECIFIC)
                && user.getRole().getRoleType() != RoleType.CAD_SUPERVISOR) {
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
        if (r.getRoleAccess().equals(RoleAccess.ALL) && (!r.getRoleType()
            .equals(RoleType.COMMITTEE))) {
            return userRepository.findByRoleRoleAccessAndRoleNotAndRoleId(RoleAccess.ALL,
                roleRepository.getOne(Long.valueOf(1)), roleId);
        }
        if (r.getRoleType().equals(RoleType.COMMITTEE)) {
            return userRepository.findByRoleIdAndIsDefaultCommittee(r.getId(), true);
        }
        return userRepository.findByRoleIdAndBranch(roleId, this.getRoleAccessFilterByBranch());
    }

    @Override
    public List<User> findByRoleIdAndIsDefaultCommittee(Long roleId, Boolean isTrue) {
        return userRepository.findByRoleIdAndIsDefaultCommittee(roleId, true);
    }

    @Override
    public List<User> findByRoleAndBranchId(Long roleId, Long branchId) {
        return userRepository.findByRoleIdAndBranchId(roleId, branchId);
    }

    @Override
    public List<User> findByRoleIdAndBranch(Long role, List<Long> branch) {
        return userRepository.findByRoleIdAndBranch(role, branch);
    }

    @Override
    public String csv(Object searchDto) {
        final CsvMaker csvMaker = new CsvMaker();
        final ObjectMapper objectMapper = new ObjectMapper();
        final Map<String, String> s = objectMapper.convertValue(searchDto, Map.class);
        final UserSpecBuilder userSpecBuilder = new UserSpecBuilder(s);
        final Specification<User> specification = userSpecBuilder.build();
        final List userList = userRepository.findAll(specification);
        Map<String, String> header = new LinkedHashMap<>();
        header.put("name", " Name");
        header.put("email", "Email");
        header.put("branch,name", "Branch name");
        header.put("role,roleName", "Role");
        header.put("status", "Status");
        return csvMaker.csv("user", header, userList, UploadDir.userCsv);

    }

    @Override
    public Page<User> findAllPageable(Object searchDto, Pageable pageable) {
        final ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> s = objectMapper.convertValue(searchDto, Map.class);
        final UserSpecBuilder userSpecBuilder = new UserSpecBuilder(s);
        final Specification<User> specification = userSpecBuilder.build();
        return userRepository.findAll(specification, pageable);

    }

    @Override
    public List<User> saveAll(List<User> list) {
        return userRepository.saveAll(list);
    }

    @Override
    public List<Long> getRoleAccessFilterByBranch() {
        User u = this.getAuthenticatedUser();
        List<Long> branchIdList = new ArrayList<>();
        String branchIdListTOString = null;
        if (u.getRole().getRoleAccess() != null) {
            if (u.getRole().getRoleAccess().equals(RoleAccess.SPECIFIC) || u.getRole()
                .getRoleAccess().equals(RoleAccess.OWN)) {
                if(u.getRole().getRoleType().equals(RoleType.CAD_SUPERVISOR)) {
                    for(Province p: u.getProvinces()) {
                        List<Branch> branches = branchRepository.getAllByProvinceIdAndStatus(p.getId(), Status.ACTIVE);
                        branchIdList.addAll(branches.stream().map(Branch::getId).collect(Collectors.toList()));
                    }
                } else {
                    for (Branch b : u.getBranch()) {
                        branchIdList.add(b.getId());
                    }
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
        Integer i = customerLoanRepository
            .chkUserContainCustomerLoan(user.getId(), user.getRole().getId(),
                DocStatus.PENDING);
        if (i > 0) {
            throw new ServiceValidationException("This user have " + i
                + " Customer Loan pending  Please transfer the loan before dismiss.");
        }
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
                .userApiAuthorities(u.getRole().getId()).stream()
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


    @Override
    public List<RoleDto> getRoleWiseBranchWiseUserList(Long roleId, Long branchId, Long userId) {
        User currentUser = getAuthenticatedUser();
        String roleName = "CAD";
        List<User> finalUserList = new ArrayList<>();
        List<User> users = userRepository.findUserNotDisMissAndActive(Status.ACTIVE);
        List<User> userWithAllAccess = users.stream()
            .filter(user ->(!user.getRole().getRoleName().equalsIgnoreCase(roleName)) && (!user.getRole().getRoleType().equals(RoleType.CAD_ADMIN)) && (user
                .getRole().getRoleAccess().equals(RoleAccess.ALL)) && (!currentUser
                .equals(user))).collect(
                Collectors.toList());
        finalUserList.addAll(userWithAllAccess);
        List<User> userWithOwnAndSpecificAccess = users.stream()
            .filter(
                user ->(!user.getRole().getRoleName().equalsIgnoreCase(roleName)) &&  (!user.getRole().getRoleType().equals(RoleType.CAD_ADMIN)) && (!user
                    .getRole().getRoleAccess().equals(RoleAccess.ALL)) && (!currentUser
                    .equals(user))).collect(
                Collectors.toList());
        userWithOwnAndSpecificAccess.forEach(user -> {
            List<Branch> branchList = user.getBranch().stream()
                .filter(branch -> Objects.equals(branch.getId(), branchId)).collect(
                    Collectors.toList());
            if (!branchList.isEmpty()) {
                finalUserList.add(user);
            }

        });
        List<RoleDto> roleDtoList = roleRepository.getRoleDto();
        return userAndRoleDtoMap(roleDtoList,
            finalUserList.stream().filter(FilterJsonUtils.distinctByKey(User::getId))
                .collect(Collectors.toList()), userId);

    }

    @Override
    public boolean checkIfValidOldPassword(final User user, final String oldPassword) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }

    @Override
    public List<UserDto> getUserByRoleCad() {
        List<User> userList = userRepository.findByRoleRoleNameAndStatus("CAD", Status.ACTIVE);
        return userToUserDtoMap(userList);

    }

    @Override
    public List<User> findByRoleTypeAndBranchIdAndStatusActive(RoleType roleType, Long branchId) {
        return userRepository
            .findByRoleRoleTypeAndBranchIdAndStatus(roleType, branchId, Status.ACTIVE);
    }

    @Override
    public List<UserDto> findByRoleIdAndBranchIdForDocumentAction(Long roleId, Long branchId) {
        final Role role = roleRepository.getOne(roleId);
        List<Branch> branchIdList = new ArrayList<>();
        final Branch branch = branchRepository.getOne(branchId);
        List<User> userList;
        if (!ObjectUtils.isEmpty(role)) {
            switch (role.getRoleAccess()) {
                case ALL:
                    userList = userRepository.findByRoleId(roleId);
                    return userToUserDtoMap(
                        userList.stream().filter(user -> user.getStatus().equals(Status.ACTIVE))
                            .collect(Collectors.toList()));
                case OWN:
                case SPECIFIC:
                    branchIdList.add(branch);
                    break;
            }
            userList = userRepository
                .findByRoleIdAndBranchInAndStatus(roleId, branchIdList, Status.ACTIVE);
            return userToUserDtoMap(userList);
        }
        return new ArrayList<>();
    }

    @Override
    public List<RoleDto> findByRoleInAndStatus(List<Role> roleList, Status status) {
        List<User> userList = userRepository.findByRoleInAndStatus(roleList, status);
        List<RoleDto> roleDtoList = new ArrayList<>();
        roleList.forEach(r -> {
            RoleDto roleDto = new RoleDto();
            roleDto.setId(r.getId());
            roleDto.setName(r.getRoleName());
            roleDtoList.add(roleDto);
        });

        return userAndRoleDtoMap(roleDtoList, userList, 0L);
    }

    @Override
    public List<UserDto> findUserListForSolByRoleIdInAndBranchId(List<Long> roleIds,
        Long branchId) {
        List<UserDto> finalList = new ArrayList<>();
        List<User> userList = userRepository.findByRoleIdInAndStatus(roleIds, Status.ACTIVE);
        userList.forEach(user -> {
            UserDto userDto = new UserDto();
            List<Branch> branchList = user.getBranch();
            if (user.getRole().getRoleAccess().equals(RoleAccess.ALL)) {
                BeanUtils.copyProperties(user, userDto);
                userDto.setId(user.getId());
                finalList.add(userDto);
            } else {
                List<Branch> checkList = branchList.stream()
                    .filter(branch -> branch.getId().equals(branchId)).collect(
                        Collectors.toList());
                if (checkList.size() > 0) {
                    BeanUtils.copyProperties(user, userDto);
                    userDto.setId(user.getId());
                    finalList.add(userDto);
                }
            }
        });
        return finalList;
    }

    private List<UserDto> userToUserDtoMap(List<User> userList) {
        List<UserDto> userDtoList = new ArrayList<>();
        for (User u : userList) {
            UserDto userDto = new UserDto();
            RoleDto roleDto = new RoleDto();

            BeanUtils.copyProperties(u, userDto);
            BeanUtils.copyProperties(u.getRole(), roleDto);
            roleDto.setId(u.getRole().getId());
            userDto.setId(u.getId());
            userDto.setRole(roleDto);
            userDtoList.add(userDto);
        }
        return userDtoList;
    }

    private List<RoleDto> userAndRoleDtoMap(List<RoleDto> roleDtoList, List<User> users,
        Long userId) {
        List<RoleDto> finalRoleDtoList = new ArrayList<>();
        for (RoleDto r : roleDtoList) {
            List<UserDto> userDtoList = new ArrayList<>();
            for (User u : users) {
                UserDto userDto = new UserDto();
                if (u.getRole().getId() == r.getId() && u.getRole().getId() != 1L
                    && u.getId() != userId) {
                    List<BranchDto> branchDto = new ArrayList<>();

                    userDto.setId(u.getId());
                    userDto.setUsername(u.getUsername());
                    userDto.setName(u.getName());
                    for (Branch b : u.getBranch()) {
                        BranchDto branchDto1 = new BranchDto();
                        branchDto1.setId(b.getId());
                        branchDto1.setName(b.getName());
                        branchDto.add(branchDto1);
                    }
                    userDto.setBranchDtoList(branchDto);
                    userDtoList.add(userDto);
                }
                r.setUserDtoList(userDtoList);

            }
            if (r.getId() != 1L) {
                finalRoleDtoList.add(r);
            }

        }
        return finalRoleDtoList.stream().filter(roleDto -> !roleDto.getUserDtoList().isEmpty())
            .collect(
                Collectors.toList());

    }

}
