package com.sb.solutions.api.memo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

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
@Table(name = "memo_type")
@EqualsAndHashCode(callSuper = true)
public class MemoType extends BaseEntity<Long> {

    @Column(nullable = false, unique = true)
    @NotNull
    private String name;

    @Column(nullable = false)
    private Status status = Status.ACTIVE;

    @Override
    protected void setId(Long id) {
        super.setId(id);
    }
}
