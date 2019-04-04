package com.sb.solutions.api.loanConfig.entity;

import com.sb.solutions.api.loanTemplate.entity.LoanTemplate;
import com.sb.solutions.core.enitity.AbstractBaseEntity;
import com.sb.solutions.core.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanConfig extends AbstractBaseEntity<Long> {

    @NotNull(message="name should not be null")
    private String name;

    @ManyToMany
    private List<LoanTemplate> templateList;

    private Status status;

}
