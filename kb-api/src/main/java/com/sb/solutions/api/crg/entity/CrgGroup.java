package com.sb.solutions.api.crg.entity;

import com.sb.solutions.core.enitity.BaseEntity;
import com.sb.solutions.core.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

/**
 * @author Sunil Babu Shrestha on 9/10/2020
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrgGroup extends BaseEntity<Long> {
    private String label;
    private String description;
    private int weightage;
    private Status status;
}
