package com.sb.solutions.api.userNotification.service;

import com.sb.solutions.api.userNotification.entity.Message;
import com.sb.solutions.api.userNotification.repository.MessageRepository;
import com.sb.solutions.api.userNotification.repository.specification.NotificationSpecBuilder;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageServiceImpl(
            MessageRepository messageRepository
    ) {
        this.messageRepository = messageRepository;
    }

    @Override
    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    @Override
    public Message findOne(Long id) {
        return messageRepository.getOne(id);
    }

    @Override
    public Message save(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public Page<Message> findAllPageable(Object t, Pageable pageable) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> s = objectMapper.convertValue(t, Map.class);
        final NotificationSpecBuilder notificationSpecBuilder = new NotificationSpecBuilder(s);
        final Specification<Message> specification = notificationSpecBuilder.build();
        return messageRepository.findAll(specification, pageable);
    }

}
