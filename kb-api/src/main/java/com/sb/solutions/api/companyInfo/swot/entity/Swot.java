package com.sb.solutions.api.companyInfo.swot.entity;

import com.sb.solutions.core.enitity.AbstractBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Swot extends AbstractBaseEntity<Long> {
    private String strength;

    private String weakness;

    private String opportunity;

    private String threats;
}
