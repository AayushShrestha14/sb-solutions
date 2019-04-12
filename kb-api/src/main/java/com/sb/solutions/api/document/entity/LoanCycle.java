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
    private String level;
    @ManyToMany(mappedBy = "loanCycle",cascade = CascadeType.ALL)
    @JsonIgnore
    private Collection<Document> documents;

}
