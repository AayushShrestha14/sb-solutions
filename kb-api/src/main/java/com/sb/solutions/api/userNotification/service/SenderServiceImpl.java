package com.sb.solutions.api.userNotification.service;

import com.sb.solutions.api.userNotification.entity.Sender;
import com.sb.solutions.api.userNotification.repository.SenderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SenderServiceImpl implements SenderService {

    @Autowired
    SenderRepository senderRepository;


    @Override
    public List<Sender> findAll() {
        return null;
    }

    @Override
    public Sender findOne(Long id) {
        return null;
    }

    @Override
    public Sender save(Sender sender) {
        return senderRepository.save(sender);
    }

    @Override
    public Page<Sender> findAllPageable(Object t, Pageable pageable) {
        return null;
    }
}
