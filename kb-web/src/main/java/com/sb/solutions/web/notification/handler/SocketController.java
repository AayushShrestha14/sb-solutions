package com.sb.solutions.web.notification.handler;

import com.sb.solutions.api.userNotification.entity.Message;
import com.sb.solutions.api.userNotification.service.MessageService;
import com.sb.solutions.core.dto.RestResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SocketController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MessageService service;

    public SocketController(SimpMessagingTemplate simpMessagingTemplate, MessageService service) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.service = service;
    }

    @MessageMapping("/send/message")
    public ResponseEntity<?> usingSocketMessaging(@RequestBody Message message) {
        if (message != null) {
            if (message.getToId() != null) {
                simpMessagingTemplate.convertAndSend("/socket-publisher/" + message.getToId(), message);
            }
        }
        return new RestResponseDto().successModel(service.save(message));
    }


}
