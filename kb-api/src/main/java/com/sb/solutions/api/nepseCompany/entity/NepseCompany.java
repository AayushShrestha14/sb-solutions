package com.sb.solutions.api.nepseCompany.entity;

import com.sb.solutions.core.enitity.BaseEntity;
import com.sb.solutions.core.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NepseCompany extends BaseEntity<Long> {

    private String companyName;

    private double amountPerUnit;

    private Status status;
}
