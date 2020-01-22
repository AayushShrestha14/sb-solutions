package com.sb.solutions.api.sharesecurity.entity;

import com.sb.solutions.core.enitity.BaseEntity;
import com.sb.solutions.core.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

/**
 * @author Sunil Babu Shrestha on 1/17/2020
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class ShareMaster extends BaseEntity<Long> {
    private double promotor;
    private double ordinary;
    private Status status;
}
