package com.sb.solutions.api.memo.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

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
@EqualsAndHashCode(callSuper = false)
public class Memo extends BaseEntity<Long> {

    @Column(nullable = false)
    @NotNull
    private String subject;

    @NotNull
    @OneToOne
    @JoinColumn(name = "sent_by")
    private User sentBy;

    @NotNull
    @OneToOne
    @JoinColumn(name = "sent_to")
    private User sentTo;

    @ManyToMany
    @JoinTable(name = "memo_cc_user",
        joinColumns = {@JoinColumn(name = "memo_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")}
    )
    private Set<User> cc;

    @ManyToMany
    @JoinTable(name = "memo_bcc_user",
        joinColumns = {@JoinColumn(name = "memo_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")})
    private Set<User> bcc;

    @Column(nullable = false)
    @NotNull
    private String content;

    @Column(nullable = false)
    private Status status = Status.ACTIVE;

    @Column(nullable = false)
    private Stage stage = Stage.DRAFT;

    @OneToOne
    @JoinColumn(name = "memo_type_id")
    @NotNull
    private MemoType type;

    @OneToMany
    @NotNull
    @Cascade(value = CascadeType.ALL)
    @JoinColumn(name = "memo_id")
    private Set<MemoStage> stages = new HashSet<>();
}
