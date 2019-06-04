package com.sb.solutions.api.eligibility.question.entity;

import com.sb.solutions.api.eligibility.criteria.entity.EligibilityCriteria;
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
public class EligibilityQuestion extends BaseEntity<Long> {

    private String description;

    private String operandCharacter;

    @ManyToOne
    @JoinColumn(name = "eligibility_criteria_id")
    private EligibilityCriteria eligibilityCriteria;
}
