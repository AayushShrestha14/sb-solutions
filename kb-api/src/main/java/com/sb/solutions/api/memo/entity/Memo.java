package com.sb.solutions.api.memo.entity;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import com.sb.solutions.api.user.entity.User;
import com.sb.solutions.core.enitity.BaseEntity;
import com.sb.solutions.core.enums.Stage;
import com.sb.solutions.core.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Memo extends BaseEntity<Long> {

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false)
    @NotNull
    @OneToOne
    private User sentBy;

    @Column(nullable = false)
    @NotNull
    @OneToMany
    private Set<User> sentTo;

    @Column(nullable = false)
    @NotNull
    @OneToMany()
    private Set<User> cc;

    @Column(nullable = false)
    @NotNull
    private String content;

    @Column(nullable = false)
    private Status status = Status.ACTIVE;

    @Column(nullable = false)
    private Stage stage = Stage.UNDER_REVIEW;

    @Column(nullable = false)
    @OneToOne
    private MemoType type;
}
