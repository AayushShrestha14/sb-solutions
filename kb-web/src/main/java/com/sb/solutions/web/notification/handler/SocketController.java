package com.sb.solutions.web.notification.handler;

import com.sb.solutions.api.userNotification.entity.Sender;
import com.sb.solutions.api.userNotification.service.SenderService;
import com.sb.solutions.core.dto.RestResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SocketController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final SenderService service;

    public SocketController(SimpMessagingTemplate simpMessagingTemplate, SenderService service) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.service = service;
    }

    @MessageMapping("/send/message")
    public ResponseEntity<?> usingSocketMessaging(@RequestBody Sender sender) {
        if (sender != null) {
            if (sender.getToId() != null) {
                simpMessagingTemplate.convertAndSend("/socket-publisher/" + sender.getToId(), sender);
            }
        }
        return new RestResponseDto().successModel(service.save(sender));
    }


}
