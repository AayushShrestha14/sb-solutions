package com.sb.solutions.api.chat.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.sb.solutions.core.enitity.BaseEntity;

/**
 * @author : Rujan Maharjan on  3/19/2020
 **/
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ChatMessage extends BaseEntity<Long> {

    private String toUserId;
    private String fromUserId;
    @Column(name = "message")
    private String text;
    private Boolean seenFlag;
    @Transient
    private Boolean reply = false;
    private String senderUser;

    private Date sendDate;

    @Transient
    private Integer unSeenMsg;


}
