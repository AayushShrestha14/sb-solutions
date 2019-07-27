package com.sb.solutions.api.userNotification.entity;

import com.sb.solutions.core.enitity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Sender extends BaseEntity<Long> {
    private Long toId;
    private Long fromId;
    private String message;
    private int notification;
}
