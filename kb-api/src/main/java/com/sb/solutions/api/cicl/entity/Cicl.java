package com.sb.solutions.api.cicl.entity;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.sb.solutions.core.enitity.BaseEntity;

/**
 * @author : Rujan Maharjan on  9/30/2020
 **/
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Cicl extends BaseEntity<Long> {

    private String data;

    private String remarks;

    private Double cibCharge;

}
