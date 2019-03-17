package com.sb.solutions.api.customerGrandFather.entity;

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
public class CustomerGrandFather extends AbstractBaseEntity<Long> {
    private String customerGrandFatherName;
    private String citizenshipNumber;
    private String citizenshipIssuedPlace;
    private Date citizenshipIssuedDate;
}
