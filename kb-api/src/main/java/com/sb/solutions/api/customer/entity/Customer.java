package com.sb.solutions.api.customer.entity;

import com.sb.solutions.api.beneficiary.entity.Beneficiary;
import com.sb.solutions.api.branch.entity.Branch;
import com.sb.solutions.api.kyc.entity.Kyc;
import com.sb.solutions.api.nominee.entity.Nominee;
import com.sb.solutions.core.enitity.BaseEntity;
import com.sb.solutions.core.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "ac_customer")
public class Customer extends BaseEntity<Long> {
    @ManyToOne
    private Branch branch;
    private String accountType;
    private String accountNo;
    private String currency;
    private String imagePath;
    private boolean isJoint;
    private boolean haveNominee;
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Nominee> nominees;
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Beneficiary> beneficiaries;
    @OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL,
            mappedBy = "customer")
    private Kyc kyc;
    @Column(columnDefinition = "text")
    private String customerDetailsJson;
    @Transient
    private Set<CustomerDetails> customerDetails;
    private Status status;
    private String internetBanking;
    private String mobileBanking;
    private String debitCard;
//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name="joint_customer_id")
//    private Customer customer;
//    @OneToMany(mappedBy="customer")
//    private Set<Customer> jointApplicants;
   /*
    private String title;
    private String firstName;
    private String middleName;
    private String lastName;
    private String proposeOfAccount;
    private String maritalStatus;
    private String gender;
    private String permanentAddress;
    private String presentAddress;
    private String landLordName;
    private String landLordContactNo;
    private String email;
    private String residentialContactNo;
    private String officeContactNo;
    private String mobileContactNo;
    private Date dateOfBirthBS;
    private Date dateOfBirthAD;
    private String nationality;
    private String citizenNumber;
    private String citizenIssuedPlace;
    private Date citizenIssuedDate;
    private String passwordNumber;
    private String passwordIssuedPlace;
    private Date passwordIssuedDate;
    private Date passwordExpireDate;
    private String IdCardNumber;
    private String IdCardIssueAuthority;
    private Date IdCardIssuedDate;
    private Date IdCardExpireDate;
    private Date visaIssueDate;
    private Date visaValidity;
    private String panNo;
    private String salariedEmployedWith;
    private String selfEmployedWith;
    private String annualTurnover;
    private String annualTransaction;
    private boolean isAccountInAnotherBank;
    private String bankName;
    private String education;
    private String map;
    private Status status;
    private String internetBanking;
    private String mobileBanking;
    private String debitCard;*/

}
