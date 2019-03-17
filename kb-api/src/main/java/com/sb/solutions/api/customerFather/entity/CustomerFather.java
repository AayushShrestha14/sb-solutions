package com.sb.solutions.api.customerFather.entity;

import com.sb.solutions.core.enitity.AbstractBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerFather extends AbstractBaseEntity<Long> {
    private String customerFatherName;
    private String citizenshipNumber;
    private String citizenshipIssuedPlace;
    private Date citizenshipIssuedDate;

}
