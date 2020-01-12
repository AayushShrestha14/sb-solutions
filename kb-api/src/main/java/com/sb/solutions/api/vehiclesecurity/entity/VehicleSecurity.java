package com.sb.solutions.api.vehiclesecurity.entity;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.sb.solutions.core.enitity.BaseEntity;

/**
 * @author Elvin Shrestha on 1/12/20
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class VehicleSecurity extends BaseEntity<Long> {

    private String data;

}
