package com.sb.solutions.api.logindetail.service;

import com.sb.solutions.api.user.entity.User;
import com.sb.solutions.api.user.repository.UserRepository;
import com.sb.solutions.core.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.locks.ReentrantReadWriteLock;

@Service
public class LoginDetailServiceImpl implements LoginDetailService{

    @Autowired
    private UserRepository userRepository;

   @Autowired
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    @Override
    public void incrementLoginAttempt(String userName, int increment, int allowedAttempts) {
        lock.writeLock().lock();
        try {
            User user = userRepository.getUsersByUsername(userName);
            if (user!=null){
                if(!user.getStatus().equals(Status.LOCKED)){
                    user.setNumOfAttempts(user.getNumOfAttempts()+increment);
                    if(user.getNumOfAttempts()>allowedAttempts){
                        user.setStatus(Status.LOCKED);
                    }
                    userRepository.save(user);
                }
            }
        }finally {
           lock.writeLock().unlock();
        }

    }

    @Override
    public void resetDetailForUser(String userName) {
        lock.writeLock().lock();
         try {
             User user = userRepository.getUsersByUsername(userName);
             if (user != null) {
                 user.setStatus(Status.ACTIVE);
                 user.setNumOfAttempts(0);
                 userRepository.save(user);
             }
         }finally {
            lock.writeLock().unlock();
         }
    }

}
