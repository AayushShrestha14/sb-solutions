package com.sb.solutions.api.document.entity;

import java.util.Collection;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sb.solutions.core.enitity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanCycle extends BaseEntity<Long> {

    private String cycle;
    @ManyToMany
    @JoinTable(name = "document_loan_cycle",
            joinColumns = {@JoinColumn(name = "document_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "loan_cycle_id", referencedColumnName = "id")})
    @JsonIgnore
    private Collection<Document> documents;
}
