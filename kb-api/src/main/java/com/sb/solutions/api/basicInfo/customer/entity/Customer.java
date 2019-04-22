package com.sb.solutions.api.basicInfo.customer.entity;

import com.sb.solutions.api.basicInfo.customerRelative.entity.CustomerRelative;
import com.sb.solutions.core.enitity.BaseEntity;
import com.sb.solutions.core.enitity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Customer extends BaseEntity<Long> {
    private String title;
    private String customerName;
    private String accountNo;
    private String province;
    private String district;
    private String municipalitiesOrVDC;
    private String telephone;
    private String mobile;
    private String email;
    private Date initialRelationDate;
    private String citizenshipNumber;
    private Date citizenshipIssuedDate;
    private String issuedPlace;
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Set<CustomerRelative> customerRelatives;
}

