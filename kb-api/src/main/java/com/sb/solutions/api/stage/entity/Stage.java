package com.sb.solutions.api.stage.entity;

import com.sb.solutions.api.user.entity.User;
import com.sb.solutions.core.enitity.BaseEntity;
import com.sb.solutions.core.enums.DocAction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

/**
 * @author Sunil Babu Shrestha on 5/24/2019
 */
@Data
@MappedSuperclass
@EqualsAndHashCode(callSuper = true)
public class Stage extends BaseEntity<Long> {

    @OneToOne
    private User fromUser;

    @OneToOne
    private User toUser;

    private DocAction docAction;

    private String comment;


}
