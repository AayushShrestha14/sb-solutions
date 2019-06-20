package com.sb.solutions.api.openingForm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpeningKyc {
    private Set<OpeningOccupationalDetails> occupationalDetails;
    private Set<OpeningCustomerRelative> customerRelatives;
}
