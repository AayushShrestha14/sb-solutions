package com.sb.solutions.api.loanConfig.entity;

import com.sb.solutions.api.document.entity.Document;
import com.sb.solutions.api.loanTemplate.entity.LoanTemplate;
import com.sb.solutions.core.enitity.BaseEntity;
import com.sb.solutions.core.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class LoanConfig extends BaseEntity<Long> {

    @NotNull(message = "name should not be null")
    private String name;
    @ManyToMany
    private List<LoanTemplate> templateList;
    private boolean isFundable;
    private Status status;
    private boolean isRenewable;
    @ManyToMany
    private List<Document> initial;
    @ManyToMany
    private List<Document> renew;


}
