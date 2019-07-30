package com.sb.solutions.api.userNotification.service;

import com.sb.solutions.api.userNotification.entity.Message;
import com.sb.solutions.api.userNotification.repository.MessageRepository;
import com.sb.solutions.core.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageRepository messageRepository;


    @Override
    public List<Message> findAll() {
        return null;
    }

    @Override
    public Message findOne(Long id) {
        return null;
    }

    @Override
    public Message save(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public Page<Message> findAllPageable(Object t, Pageable pageable) {
        return null;
    }

    @Override
    public long count(long toId) {
        return messageRepository.countCurrentUserNotification(Status.ACTIVE, toId);
    }
}
