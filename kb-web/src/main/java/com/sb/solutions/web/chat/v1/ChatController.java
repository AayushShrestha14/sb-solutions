package com.sb.solutions.web.chat.v1;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sb.solutions.api.chat.entity.ChatMessage;
import com.sb.solutions.api.chat.service.ChatService;
import com.sb.solutions.api.user.service.UserService;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.utils.PaginationUtils;

/**
 * @author : Rujan Maharjan on  3/19/2020
 **/
@RestController
@RequestMapping(ChatController.URL)
public class ChatController {

    public static final String URL = "v1/chat";

    private final Logger logger = LoggerFactory.getLogger(ChatController.class);


    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private ChatService chatService;


    @PostMapping
    public ResponseEntity<?> chat(@RequestBody ChatMessage message) {
        if (message.getText() != null) {
            //if the toId is present the message will be sent privately else broadcast it to all users

            if (message.getToUserId() != null) {

                this.simpMessagingTemplate
                    .convertAndSend("/socket-publisher/" + message.getFromUserId(), message);
                message.setUnSeenMsg(1);
                this.simpMessagingTemplate
                    .convertAndSend("/socket-publisher/" + message.getToUserId(), message);
                chatService.save(message);

            } else {
                this.simpMessagingTemplate.convertAndSend("/socket-publisher", message);
            }
            return new ResponseEntity<>(message, new HttpHeaders(), HttpStatus.OK);
        }
        return new ResponseEntity<>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }


    @GetMapping("/user-list")
    public ResponseEntity<?> getUsersForChat() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("userList", chatService.getUserListChat());
        map.put("totalUnseenMsg", chatService.getAllUnseenMsgCount());
        return new RestResponseDto().successModel(
            map);
    }

    @PostMapping("/list")
    public ResponseEntity getChatHistoryCurrentUser(@RequestBody String associateId,
        @RequestParam("page") int page, @RequestParam("size") int size) {
        return new RestResponseDto()
            .successModel(chatService.findByAssociateChatMessage(associateId, PaginationUtils
                .pageable(page, size)));

    }

    @PostMapping("/update-seen")
    public void updateSeen(@RequestBody String fromId) {
        chatService.updateSeen(fromId);

    }


}


