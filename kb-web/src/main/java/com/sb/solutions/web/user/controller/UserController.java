package com.sb.solutions.web.user.controller;


import com.sb.solutions.api.user.entity.User;
import com.sb.solutions.api.user.service.UserService;
import com.sb.solutions.core.dto.RestResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Sunil Babu Shrestha on 12/27/2018
 */
@RestController
@RequestMapping("/v1/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(method = RequestMethod.GET, path = "/authenticated")
    public ResponseEntity<?> getAuthenticated() {
    return new RestResponseDto().successModel(userService.getAuthenticated());
    }


    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@RequestBody User user) {
        userService.save(user);
        return new ResponseEntity<>(userService.getAuthenticated(), HttpStatus.OK);
    }
}
