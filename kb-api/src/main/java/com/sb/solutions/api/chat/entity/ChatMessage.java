package com.sb.solutions.api.chat.entity;

import lombok.Data;

import com.sb.solutions.core.enitity.BaseEntity;

/**
 * @author : Rujan Maharjan on  3/19/2020
 **/
@Data
public class ChatMessage extends BaseEntity<Long> {

    private String toUserId;
    private String fromUserId;
    private String message;
    private Boolean seenFlag = false;

}
