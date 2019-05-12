package com.sb.solutions.api.openingForm.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpeningCustomerRelative {
    private String title;
    private String customerRelation;
    private String customerRelativeName;
    private String citizenshipNumber;
    private String citizenshipIssuedPlace;
    private Date citizenshipIssuedDate;
}
