package com.sb.solutions.api.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FingerPrint {
    @Id
    @GeneratedValue
    private long id;
    private String path;
    @Column(name = "user_id")
    private long user_id;
}
