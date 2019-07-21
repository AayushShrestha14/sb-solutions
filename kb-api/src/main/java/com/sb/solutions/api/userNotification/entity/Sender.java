package com.sb.solutions.api.userNotification.entity;

import lombok.Data;

@Data
public class Sender {
    private Long toId;
    private Long fromId;
    private String message;
    private int notification;
}
