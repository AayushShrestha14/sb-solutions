package com.sb.solutions.api.customerSpouse.entity;

import com.sb.solutions.core.enitity.AbstractBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerSpouse extends AbstractBaseEntity<Long> {
    private String customerSpouseName;
    private String citizenshipNumber;
    private String citizenshipIssuedPlace;
    private Date citizenshipIssuedDate;
}
