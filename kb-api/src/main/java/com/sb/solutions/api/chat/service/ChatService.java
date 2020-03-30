package com.sb.solutions.api.chat.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sb.solutions.api.chat.entity.ChatMessage;
import com.sb.solutions.api.user.dto.UserDto;
import com.sb.solutions.core.service.BaseService;

/**
 * @author : Rujan Maharjan on  3/26/2020
 **/

public interface ChatService extends BaseService<ChatMessage> {

    Page<ChatMessage> findByAssociateChatMessage(String associateUser, Pageable pageable);

    void updateSeen(String fromId);

    Integer getAllUnseenMsgCount();

    public List<UserDto> getUserListChat();

}
