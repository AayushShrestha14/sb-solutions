package com.sb.solutions.api.memo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.sb.solutions.api.memo.enums.Stage;
import com.sb.solutions.api.user.entity.User;
import com.sb.solutions.core.enitity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MemoStage extends BaseEntity<Long> {

    @OneToOne
    @NotNull
    private Memo memo;

    @OneToOne
    @NotNull
    private User sentBy;

    @OneToOne
    @NotNull
    private User sentTo;

    @NotNull
    @NotEmpty
    @Column(name = "stage", nullable = false)
    private Stage stage;

    @Column(name = "note", nullable = false)
    @NotNull
    @NotEmpty
    private String note;
}
