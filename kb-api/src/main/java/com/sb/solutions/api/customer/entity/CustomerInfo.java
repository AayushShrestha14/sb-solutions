package com.sb.solutions.api.customer.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.sb.solutions.api.branch.entity.Branch;
import com.sb.solutions.api.customer.enums.CustomerIdType;
import com.sb.solutions.api.customer.enums.CustomerType;
import com.sb.solutions.api.financial.entity.Financial;
import com.sb.solutions.api.guarantor.entity.GuarantorDetail;
import com.sb.solutions.api.insurance.entity.Insurance;
import com.sb.solutions.api.security.entity.Security;
import com.sb.solutions.api.sharesecurity.ShareSecurity;
import com.sb.solutions.api.siteVisit.entity.SiteVisit;
import com.sb.solutions.core.enitity.BaseEntity;
import com.sb.solutions.core.enums.Status;

/**
 * @author : Rujan Maharjan on  8/9/2020
 **/

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "customer_info")
@EntityListeners({AuditingEntityListener.class})
public class CustomerInfo extends BaseEntity<Long> {

    @NotNull(message = "name cannot be null")
    private String name;

    private CustomerIdType idType;

    @NotNull(message = "idNumber cannot be null")
    private String idNumber;

    private Date idRegDate;

    private CustomerType customerType;


    private String contactNo;

    private boolean isBlacklist = false;

    @Email
    private String email;


    private String idRegPlace;

    private Long associateId;

    private Status status = Status.ACTIVE;

    @OneToOne
    private SiteVisit siteVisit;

    @OneToOne
    private Financial financial;
    @OneToOne
    private Security security;

    @OneToOne
    private ShareSecurity shareSecurity;

    @OneToOne
    private Insurance insurance;

    @OneToOne
    private GuarantorDetail guarantors;

    @OneToOne
    private Branch branch;

    @Transient
    private List<CustomerGeneralDocument> customerGeneralDocuments = new ArrayList<>();
}
