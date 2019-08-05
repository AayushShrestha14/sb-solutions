package com.sb.solutions.api.userNotification.entity;

import com.sb.solutions.core.enums.DocAction;
import com.sb.solutions.core.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    @Id
    @GeneratedValue
    private Long id;

    private Long fromRole;
    private Long toRole;
    private Long toId;
    private Long fromId;
    private Long customerId;
    private Long loanConfigId;
    private String message;
    private Status status;

    @Transient
    private DocAction docAction;
}
