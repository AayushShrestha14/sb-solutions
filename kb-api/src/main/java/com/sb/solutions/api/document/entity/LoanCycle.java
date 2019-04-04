package com.sb.solutions.api.document.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanCycle {
    @Id
    @GeneratedValue
    private long id;
    private String cycle;
    private String level;
    @ManyToMany(mappedBy = "loanCycle",cascade = CascadeType.ALL)
    @JsonIgnore
    private Collection<Document> documents;

}
