package com.sb.solutions.api.vehiclesecurity.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VehicleSecurityValuator> valuatorList;

}
