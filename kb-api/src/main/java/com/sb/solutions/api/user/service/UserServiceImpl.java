package com.sb.solutions.api.user.service;

import com.sb.solutions.api.user.entity.User;
import com.sb.solutions.api.user.entity.UserType;
import com.sb.solutions.api.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * @author Sunil Babu Shrestha on 12/31/2018
 */
@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;


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
        return userRepository.save(user);
    }

    @Override
    public Page<User> findAllPageable(User user,Pageable pageable) {
        return userRepository.userFilter(user.getName()==null?"":user.getName(),pageable);
    }

    @Override
    public Page<User> findByUserType(Collection<UserType> userTypes, Pageable pageable){
        return  userRepository.findByUserTypeIn(userTypes,pageable);
    }

}
