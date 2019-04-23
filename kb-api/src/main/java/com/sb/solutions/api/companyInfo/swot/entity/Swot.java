package com.sb.solutions.api.companyInfo.swot.entity;

import com.sb.solutions.core.enitity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Swot extends BaseEntity<Long> {
    private String strength;

    private String weakness;

    private String opportunity;

    private String threats;
}
