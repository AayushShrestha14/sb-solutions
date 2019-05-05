package com.sb.solutions.api.openingForm.entity;

import com.sb.solutions.core.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpeningCustomer {
    private String title;
    private String firstName;
    private String middleName;
    private String lastName;
    private String proposeOfAccount;
    private String maritalStatus;
    private String gender;
    private String accountType;
    private String accountNo;
    private String currency;
    private boolean isJoint;
    private boolean haveNominee;
    private Set<OpeningNominee> nominees;
    private Set<OpeningBeneficiary> beneficiaries;
    private OpeningKyc kyc;
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
    private String debitCard;

}
