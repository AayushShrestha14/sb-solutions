package com.sb.solutions.api.customer.entity;

import com.sb.solutions.api.customerFather.entity.CustomerFather;
import com.sb.solutions.api.customerGrandFather.entity.CustomerGrandFather;
import com.sb.solutions.api.customerSpouse.entity.CustomerSpouse;
import com.sb.solutions.core.enitity.AbstractBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer extends AbstractBaseEntity<Long> {
    private String title;
    private String customerName;
    private String customerId;
    private String accountNo;
    private String address1;
    private String address2;
    private String telephone;
    private String mobile;
    private String email;
    private Date initialRelationDate;
    private String citizenshipNumber;
    private Date citizenshipIssuedDate;
    private String issuedPlace;
    @OneToOne(cascade = CascadeType.ALL)
    private CustomerFather customerFather;
    @OneToOne(cascade = CascadeType.ALL)
    private CustomerGrandFather customerGrandFather;
    @OneToOne(cascade = CascadeType.ALL)
    private CustomerSpouse customerSpouse;
}
