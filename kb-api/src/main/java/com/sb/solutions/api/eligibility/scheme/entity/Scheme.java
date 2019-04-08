package com.sb.solutions.api.eligibility.scheme.entity;

import com.sb.solutions.api.eligibility.company.entity.Company;
import com.sb.solutions.core.enitity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Scheme extends BaseEntity<Long> {

    private String name;

    private long totalPoints;

    private long eligibilityPoints;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

}
