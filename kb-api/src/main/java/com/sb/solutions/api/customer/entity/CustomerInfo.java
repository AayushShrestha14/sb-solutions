package com.sb.solutions.api.customer.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import com.sb.solutions.api.creditChecklist.entity.CreditChecklist;
import com.sb.solutions.api.netTradingAssets.entity.NetTradingAssets;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.sb.solutions.api.branch.entity.Branch;
import com.sb.solutions.api.cicl.entity.Cicl;
import com.sb.solutions.api.creditRiskGrading.entity.CreditRiskGrading;
import com.sb.solutions.api.creditRiskGradingAlpha.entity.CreditRiskGradingAlpha;
import com.sb.solutions.api.crg.entity.CrgGamma;
import com.sb.solutions.api.customer.enums.CustomerIdType;
import com.sb.solutions.api.customer.enums.CustomerType;
import com.sb.solutions.api.customerGroup.CustomerGroup;
import com.sb.solutions.api.financial.entity.Financial;
import com.sb.solutions.api.guarantor.entity.GuarantorDetail;
import com.sb.solutions.api.incomeFromAccount.entity.IncomeFromAccount;
import com.sb.solutions.api.insurance.entity.Insurance;
import com.sb.solutions.api.loanflag.entity.CustomerLoanFlag;
import com.sb.solutions.api.security.entity.Security;
import com.sb.solutions.api.sharesecurity.ShareSecurity;
import com.sb.solutions.api.siteVisit.entity.SiteVisit;
import com.sb.solutions.core.enitity.BaseEntity;
import com.sb.solutions.core.enums.Status;
import com.sb.solutions.core.utils.string.NameFormatter;

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
    @JsonDeserialize(using = NameFormatter.class)
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

    @OneToMany
    private List<Insurance> insurance;

    @OneToOne
    private GuarantorDetail guarantors;

    @OneToOne
    private CreditRiskGradingAlpha creditRiskGradingAlpha;

    @OneToOne
    private CreditRiskGrading creditRiskGrading;

    @OneToOne
    private CrgGamma crgGamma;

    @OneToOne
    private Branch branch;

    @Transient
    private List<CustomerGeneralDocument> customerGeneralDocuments = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "customer_info_customer_group_id")
    private CustomerGroup customerGroup;

    private String profilePic;

    @OneToMany(mappedBy = "customerInfo")
    @JsonManagedReference
    private List<CustomerLoanFlag> loanFlags = new ArrayList<>();


    @OneToOne
    private Cicl cicl;

    @OneToOne
    private IncomeFromAccount incomeFromAccount;

    @OneToOne
    private NetTradingAssets netTradingAssets;

    private String bankingRelationship;

    private String customerCode;

    @OneToOne
    private CreditChecklist creditChecklist;
}
