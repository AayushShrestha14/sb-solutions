package com.sb.solutions.api.user.repository;

import com.sb.solutions.api.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Rujan Maharjan on 12/31/2018
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User getUsersByUserName(String username);
}
