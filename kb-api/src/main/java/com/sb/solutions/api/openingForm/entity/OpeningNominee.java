package com.sb.solutions.api.openingForm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpeningNominee {
    private String fullName;
    private String imagePath;
    private String relationToMe;
    private Date dateOfBirth;
    private int age;
    private String citizenNumber;
    private String issuedPlace;
    private String temporaryAddress;
    private String permanentAddress;
    private String contactAddress;
    private String contactNumber;
}
