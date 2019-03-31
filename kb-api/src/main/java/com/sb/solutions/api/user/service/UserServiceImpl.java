package com.sb.solutions.api.user.service;

import com.machinezoo.sourceafis.FingerprintMatcher;
import com.machinezoo.sourceafis.FingerprintTemplate;
import com.sb.solutions.api.rolePermissionRight.entity.Role;
import com.sb.solutions.api.user.entity.User;
import com.sb.solutions.api.user.repository.FingerPrintRepository;
import com.sb.solutions.api.user.repository.UserRepository;
import com.sb.solutions.core.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author Sunil Babu Shrestha on 12/31/2018
 */
@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private FingerPrintRepository fingerPrintRepository;
    private BCryptPasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setLastModified(new Date());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if(user.getId()==null){
            user.setStatus(Status.ACTIVE);
        }
        return userRepository.save(user);

    }

    @Override
    public Page<User> findByRole(Collection<Role> roles, Pageable pageable) {
        return  userRepository.findByRoleIn(roles,pageable);
    }

    @Override
    public User getUserByFingerPrint(MultipartFile file) {
        byte[] correctImage;
        byte[] candidateImage;
        Set<String> fingersPrint = fingerPrintRepository.getAllPath();
        long id = 0;
        for (String path : fingersPrint) {
            try {
                correctImage = Files.readAllBytes(Paths.get(path));
                candidateImage = file.getBytes();
                FingerprintTemplate correct = new FingerprintTemplate().dpi(500).create(correctImage);
                FingerprintTemplate candidate = new FingerprintTemplate().dpi(500).create(candidateImage);
                double score = new FingerprintMatcher().index(correct).match(candidate);
                double threshold = 100;
                if (score >= threshold) {
                    id = fingerPrintRepository.findByPath(path).getUser_id();
                    break;
                }
            } catch (IOException e) {
                System.out.println("File not found");
            }
        }
        return userRepository.findById(id).get();

    }

    @Override
    public Page<User> findAllPageable(Object user, Pageable pageable) {
        User userMapped = (User) user;
        return userRepository.userFilter(userMapped.getName()==null?"":userMapped.getName(),pageable);

    }

}
