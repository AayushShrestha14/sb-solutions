package com.sb.solutions.api.document.entity;

import com.sb.solutions.core.enitity.AbstractBaseEntity;
import com.sb.solutions.core.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Document extends AbstractBaseEntity<Long> {

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String url;

    @ManyToMany
    private Collection<LoanCycle> loanCycle;

    private Status status;

}
