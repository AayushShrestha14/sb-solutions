package com.sb.solutions.api.chat.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sb.solutions.api.authorization.dto.RoleDto;
import com.sb.solutions.api.chat.entity.ChatMessage;
import com.sb.solutions.api.chat.repository.ChatRepository;
import com.sb.solutions.api.user.dto.UserDto;
import com.sb.solutions.api.user.entity.User;
import com.sb.solutions.api.user.repository.UserRepository;
import com.sb.solutions.api.user.service.UserService;
import com.sb.solutions.core.enums.Status;

/**
 * @author : Rujan Maharjan on  3/26/2020
 **/

@Service
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;

    private final UserService userService;

    private final UserRepository userRepository;

    private final Logger logger = LoggerFactory.getLogger(ChatServiceImpl.class);

    public ChatServiceImpl(@Autowired ChatRepository chatRepository,
        UserService userService,
        UserRepository userRepository) {
        this.chatRepository = chatRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    public List<ChatMessage> findAll() {
        return null;
    }

    @Override
    public ChatMessage findOne(Long id) {
        return null;
    }

    @Override
    public ChatMessage save(ChatMessage chatMessage) {
        this.updateSeen(chatMessage.getToUserId());
        return chatRepository.save(chatMessage);
    }

    @Override
    public Page<ChatMessage> findAllPageable(Object t, Pageable pageable) {
        return null;
    }

    @Override
    public List<ChatMessage> saveAll(List<ChatMessage> list) {
        return null;
    }

    @Override
    public Page<ChatMessage> findByAssociateChatMessage(String associateUser, Pageable pageable) {
        String uId = String.valueOf(userService.getAuthenticatedUser().getId());
        Page<ChatMessage> chatPage = chatRepository
            .findChatMessageByFromUserIdAndToUserIdOrToUserIdAndFromUserIdOrderBySendDateDesc(uId,
                associateUser, uId, associateUser, pageable);
        return chatPage;

    }

    @Override
    public void updateSeen(String fromId) {
        String uId = String.valueOf(userService.getAuthenticatedUser().getId());
        chatRepository.updateSeen(fromId, uId);
        logger.info("updated chat {}", fromId);

    }

    @Override
    public Integer getAllUnseenMsgCount() {
        String uId = String.valueOf(userService.getAuthenticatedUser().getId());
        return chatRepository.getUnSeenMsg(uId);
    }

    @Override
    public List<UserDto> getUserListChat() {
        User currentUser = userService.getAuthenticatedUser();
        List<User> userList = userRepository.findUserNotDisMissAndActive(Status.ACTIVE);
        userList.remove(currentUser);
        List<UserDto> userDtoList = new ArrayList<>();
        for (User u : userList) {
            UserDto userDto = new UserDto();
            userDto.setId(u.getId());
            userDto.setName(u.getName());
            userDto.setRole(new RoleDto(u.getRole().getId(), u.getRole().getRoleName()));
            userDto.setIsUnseenMsg(chatRepository
                .getUnSeenMsgWithRespectToSender(currentUser.getId().toString(),
                    u.getId().toString()));
            userDtoList.add(userDto);
        }
        return userDtoList;
    }
}
