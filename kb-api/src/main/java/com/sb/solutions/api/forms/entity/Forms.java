package com.sb.solutions.api.forms.entity;

import com.sb.solutions.core.enitity.BaseEntity;
import com.sb.solutions.core.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.validation.constraints.NotNull;

/**
 * @author : Rujan Maharjan on  11/18/2020
 **/

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@EntityListeners({AuditingEntityListener.class})
public class Forms extends BaseEntity<Long> {

    @NotNull(message = "title cannot be empty")
    private String title;

    @NotNull(message = "Form configuration cannot be empty")
    private String config;

    private Status status = Status.ACTIVE;
}
