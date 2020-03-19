package com.sb.solutions.web.chat.v1;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : Rujan Maharjan on  3/19/2020
 **/
@RestController
@RequestMapping(ChatController.URL)
public class ChatController {

    public static final String URL = "v1/chat";

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;


    public ResponseEntity<?> chat(@RequestBody Map<String, String> message) {
        if (message.containsKey("message")) {
            //if the toId is present the message will be sent privately else broadcast it to all users

            if (message.containsKey("toUserId") && message.get("toUserId") != null && !message
                .get("toUserId")
                .equals("")) {
                this.simpMessagingTemplate
                    .convertAndSend("/socket-publisher/" + message.get("toUserId"), message);

                this.simpMessagingTemplate
                    .convertAndSend("/socket-publisher/" + message.get("fromUserId"), message);
            } else {
                this.simpMessagingTemplate.convertAndSend("/socket-publisher", message);
            }
            return new ResponseEntity<>(message, new HttpHeaders(), HttpStatus.OK);
        }
        return new ResponseEntity<>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

}


