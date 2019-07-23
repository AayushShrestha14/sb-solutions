package com.sb.solutions.web.notification.handler;

import com.sb.solutions.api.userNotification.entity.Sender;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SocketController {
    private final SimpMessagingTemplate simpMessagingTemplate;

    public SocketController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/send/message")
    public Sender usingSocketMessaging(@RequestBody Sender sender) {
        if (sender != null) {
            if (sender.getToId() != null) {
                simpMessagingTemplate.convertAndSend("/socket-publisher/" + sender.getToId(), sender);
            }
        }
        return sender;
    }


}
