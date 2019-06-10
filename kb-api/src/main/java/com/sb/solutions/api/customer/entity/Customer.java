package com.sb.solutions.api.customer.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sb.solutions.api.kyc.entity.Kyc;
import com.sb.solutions.api.address.district.entity.District;
import com.sb.solutions.api.address.municipalityVdc.entity.MunicipalityVdc;
import com.sb.solutions.api.address.province.entity.Province;
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

    @ManyToOne
    private Province province;

    @ManyToOne
    private District district;

    @ManyToOne
    private MunicipalityVdc municipalitiesOrVDC;
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
