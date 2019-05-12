package com.sb.solutions.api.openingForm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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
    private OpeningKyc kyc;
    private String permanentProvince;
    private String permanentDistrict;
    private String permanentMunicipality;
    private String permanentWard;
    private String permanentStreet;
    private String permanentHouseNumber;
    private String presentProvince;
    private String presentDistrict;
    private String presentMunicipality;
    private String presentWard;
    private String presentStreet;
    private String presentHouseNumber;
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
    private String citizenImagePath;
    private String passportNumber;
    private String passportIssuedPlace;
    private Date passportIssuedDate;
    private Date passportExpireDate;
    private String passportImagePath;
    private String idCardNumber;
    private String idCardIssueAuthority;
    private Date idCardIssuedDate;
    private Date idCardExpireDate;
    private String idImagePath;
    private Date visaIssueDate;
    private Date visaValidity;
    private String panNo;
    private String salariedEmployedWith;
    private String selfEmployedWith;
    private String otherSourceOfIncome;
    private String education;
    private String map;
    private boolean isUsResident;
    private boolean isUsCitizen;
    private boolean isGreenCardHolder;
    private boolean isExposeToPep;
    private String pepName;
    private String pepRelationToYou;
    private boolean isConvictedOfCrime;
    private String convictedCrime;
    private boolean residentialPermitOfForeign;
    private String residentialPermitOfForeignType;
    private boolean isAccountInAnotherBank;
    private String bankName;
}
