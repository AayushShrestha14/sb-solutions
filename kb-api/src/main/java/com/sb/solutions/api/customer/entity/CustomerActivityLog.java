package com.sb.solutions.api.customer.entity;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.sb.solutions.core.enitity.BaseEntity;
import com.sb.solutions.core.enums.CustomerActivityLogType;

/**
 * @author Elvin Shrestha on 9/19/2020
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CustomerActivityLog extends BaseEntity<Long> {

    private Long customerInfoId;
    private String detail;
    private CustomerActivityLogType logType;

}
