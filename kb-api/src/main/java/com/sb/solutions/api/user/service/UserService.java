package com.sb.solutions.api.user.service;

import com.sb.solutions.api.user.entity.User;
import com.sb.solutions.core.service.BaseService;

/**
 * @author Sunil Babu Shrestha on 12/31/2018
 */


public interface UserService extends BaseService<User> {

    User getAuthenticated();

    User getByUsername(String username);

    User save(User user);

}
