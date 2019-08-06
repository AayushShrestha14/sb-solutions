package com.sb.solutions.api.userNotification.service;

import com.sb.solutions.api.rolePermissionRight.entity.Role;
import com.sb.solutions.api.rolePermissionRight.service.RoleService;
import com.sb.solutions.api.user.entity.User;
import com.sb.solutions.api.user.service.UserService;
import com.sb.solutions.api.userNotification.entity.Message;
import com.sb.solutions.api.userNotification.repository.MessageRepository;
import com.sb.solutions.api.userNotification.repository.specification.NotificationSpecBuilder;
import com.sb.solutions.core.enums.Status;
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
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public MessageServiceImpl(
            MessageRepository messageRepository,
            UserService userService,
            RoleService roleService
    ) {
        this.messageRepository = messageRepository;
        this.userService = userService;
        this.roleService = roleService;
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
        User fromUser = userService.findOne(message.getFromId());
        Role fromRole = roleService.findOne(message.getFromRole());

        if (message.getId() == null) {
            message.setStatus(Status.ACTIVE);
            message.setMessage(String.format("%s (%s) has %s you a loan document.",
                    fromUser.getUsername(), fromRole.getRoleName(),
                    message.getDocAction().toString().toLowerCase()));
        }
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
