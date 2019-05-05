package com.sb.solutions.api.customer.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
@Data
public class CustomerDetails {
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
}
