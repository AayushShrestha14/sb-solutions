package com.sb.solutions.api.openingForm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpeningBeneficiary {
    private String fullName;
    private String imagePath;
    private String relationToMe;
    private Date dateOfBirth;
    private String citizenNumber;
    private String issuedPlace;
    private String citizenImagePath;
    private String temporaryAddress;
    private String permanentAddress;
    private String contactNumber;
}
