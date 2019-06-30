package com.sb.solutions.api.nepseCompany.entity;

import javax.persistence.Entity;

import com.sb.solutions.core.enitity.BaseEntity;
import com.sb.solutions.core.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class NepseCompany extends BaseEntity<Long> {

    private String companyName;

    private double amountPerUnit;

    private Status status;
}
