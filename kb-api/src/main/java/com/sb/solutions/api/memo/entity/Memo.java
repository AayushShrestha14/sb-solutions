package com.sb.solutions.api.memo.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import com.sb.solutions.api.memo.enums.Stage;
import com.sb.solutions.api.user.entity.User;
import com.sb.solutions.core.enitity.BaseEntity;
import com.sb.solutions.core.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Memo extends BaseEntity<Long> {

    @Column(nullable = false)
    @NotNull
    private String subject;

    @NotNull
    @OneToOne
    private User sentBy;

    @NotNull
    @OneToOne
    private User sentTo;

    @ManyToMany
    @JoinTable(name = "memo_cc_user")
    private Set<User> cc;

    @ManyToMany
    @JoinTable(name = "memo_bcc_user")
    private Set<User> bcc;

    @Column(nullable = false)
    @NotNull
    private String content;

    @Column(nullable = false)
    private Status status = Status.ACTIVE;

    @Column(nullable = false)
    private Stage stage = Stage.DRAFT;

    @OneToOne
    private MemoType type;

    @OneToMany
    @NotNull
    private Set<MemoStage> memoStage = new HashSet<>();
}
