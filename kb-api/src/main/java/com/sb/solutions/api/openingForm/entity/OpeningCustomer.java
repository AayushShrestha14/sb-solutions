package com.sb.solutions.api.openingForm.entity;

import com.sb.solutions.api.address.district.entity.District;
import com.sb.solutions.api.address.municipalityVdc.entity.MunicipalityVdc;
import com.sb.solutions.api.address.province.entity.Province;
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
    private OpeningNominee nominee;
    private Set<OpeningBeneficiary> beneficiaries;
    private OpeningKyc kyc;
    private Province permanentProvince;
    private District permanentDistrict;
    private MunicipalityVdc permanentMunicipality;
    private Province presentProvince;
    private District presentDistrict;
    private MunicipalityVdc presentMunicipality;
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
    private String IdCardNumber;
    private String IdCardIssueAuthority;
    private Date IdCardIssuedDate;
    private Date IdCardExpireDate;
    private String idImagePath;
    private Date visaIssueDate;
    private Date visaValidity;
    private String panNo;
    private String salariedEmployedWith;
    private String selfEmployedWith;
    private int annualTurnover;
    private int annualTransaction;
    private boolean isAccountInAnotherBank;
    private String bankName;
    private String education;
    private String map;
    private boolean internetBanking;
    private boolean mobileBanking;
    private boolean debitCard;
    private boolean checkBook;

}
