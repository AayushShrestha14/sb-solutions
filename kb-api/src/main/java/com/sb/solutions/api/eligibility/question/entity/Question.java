package com.sb.solutions.api.eligibility.question.entity;

import com.sb.solutions.api.eligibility.answer.entity.Answer;
import com.sb.solutions.api.eligibility.scheme.entity.Scheme;
import com.sb.solutions.core.enitity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question extends BaseEntity<Long> {

    private String description;

    private long maximumPoints;

    private long appearanceOrder;

    @ManyToOne
    @JoinColumn(name = "scheme_id")
    private Scheme scheme;

    @OneToMany(mappedBy = "question")
    private List<Answer> answers = new ArrayList<>();

}
