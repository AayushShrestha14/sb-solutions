package com.sb.solutions.api.eligibility.answer.entity;

import com.sb.solutions.api.eligibility.question.entity.EligibilityQuestion;
import com.sb.solutions.core.enitity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
public class EligibilityAnswer extends BaseEntity<Long> {

    @ManyToOne
    @JoinColumn(name = "eligibility_question_id")
    private EligibilityQuestion eligibilityQuestion;

    private double value;

}
