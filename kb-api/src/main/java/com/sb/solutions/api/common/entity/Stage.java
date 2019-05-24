package com.sb.solutions.api.common.entity;

import com.sb.solutions.api.user.entity.User;
import com.sb.solutions.core.enums.DocAction;
import lombok.Data;

import javax.persistence.OneToOne;

/**
 * @author Sunil Babu Shrestha on 5/24/2019
 */
@Data
public class Stage {

    @OneToOne
    private User fromUser;

    @OneToOne
    private User toUser;

    private DocAction docAction;

    private String comment;


}
