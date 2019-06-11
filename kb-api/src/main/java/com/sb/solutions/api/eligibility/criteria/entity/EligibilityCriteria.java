package com.sb.solutions.api.eligibility.criteria.entity;

import com.sb.solutions.api.eligibility.question.entity.EligibilityQuestion;
import com.sb.solutions.core.enitity.BaseEntity;
import com.sb.solutions.core.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
public class EligibilityCriteria extends BaseEntity<Long> {

    private double thresholdAmount;

    private double percentageOfAmount;

    @OneToMany(mappedBy = "eligibilityCriteria")
    @Where(clause = "status != 'DELETED'")
    @Cascade({CascadeType.PERSIST, CascadeType.MERGE})
    private List<EligibilityQuestion> questions = new ArrayList<>();

    private String formula;

    private Status status;

}
