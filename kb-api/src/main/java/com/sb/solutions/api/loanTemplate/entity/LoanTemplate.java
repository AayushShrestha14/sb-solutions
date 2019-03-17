package com.sb.solutions.api.loanTemplate.entity;

import com.sb.solutions.core.enitity.AbstractBaseEntity;
import com.sb.solutions.core.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;

/**
 * @author Rujan Maharjan on 2/25/2019
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanTemplate extends AbstractBaseEntity<Long> {

    @NotNull
    private String name;
    @NotNull
    private String templateUrl;
    private Integer orderUrl;
    @Lob
    private String templateView;
    private Status status;
}
