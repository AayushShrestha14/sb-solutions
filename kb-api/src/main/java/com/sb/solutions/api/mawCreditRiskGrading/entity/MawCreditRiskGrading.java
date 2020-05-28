package com.sb.solutions.api.mawCreditRiskGrading.entity;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.sb.solutions.core.enitity.BaseEntity;

/**
 * @author Amulya Shrestha on 1/31/2020
 **/

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MawCreditRiskGrading extends BaseEntity<Long> {
    private String data;
}
