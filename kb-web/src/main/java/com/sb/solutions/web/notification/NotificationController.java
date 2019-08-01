package com.sb.solutions.web.notification;

import com.sb.solutions.api.userNotification.service.MessageService;
import com.sb.solutions.core.dto.RestResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(NotificationController.API)
public class NotificationController {

    static final String API = "/v1/notification";
    private final MessageService service;

    @Autowired
    public NotificationController(
            MessageService messageService
    ) {
        this.service = messageService;
    }

    @GetMapping(value = "/count/{toId}")
    public ResponseEntity<?> notificationCount(@PathVariable Long toId) {
        return new RestResponseDto().successModel(service.count(toId));
    }
}
