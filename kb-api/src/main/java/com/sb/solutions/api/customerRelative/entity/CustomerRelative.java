package com.sb.solutions.api.customerRelative.entity;

import java.util.Date;
import javax.persistence.Entity;

import com.sb.solutions.core.enitity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CustomerRelative extends BaseEntity<Long> {

    private String title;
    private String customerRelation;
    private String customerRelativeName;
    private String citizenshipNumber;
    private String citizenshipIssuedPlace;
    private Date citizenshipIssuedDate;
}
