package com.sb.solutions.api.document.entity;

import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

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
    @ManyToMany(mappedBy = "loanCycle", cascade = CascadeType.ALL)
    @JsonIgnore
    private Collection<Document> documents;
}
