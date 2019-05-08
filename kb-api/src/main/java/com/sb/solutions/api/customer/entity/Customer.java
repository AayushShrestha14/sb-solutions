package com.sb.solutions.api.customer.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sb.solutions.api.kyc.entity.Kyc;
import com.sb.solutions.core.enitity.BaseEntity;
import com.sb.solutions.core.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "customer")
public class Customer extends BaseEntity<Long> {
    private String title;
    private String customerName;
    private String accountNo;
    private String province;
    private String district;
    @Column(name = "municipalities_or_vdc")
    private String municipalitiesOrVDC;
    private String telephone;
    private String mobile;
    private String email;
    private Date initialRelationDate;
    private String citizenshipNumber;
    private Date citizenshipIssuedDate;
    private String issuedPlace;
    private Status status;
    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "customer")
    private Kyc kyc;
}
