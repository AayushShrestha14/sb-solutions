package com.sb.solutions.api.openingForm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpeningKyc {
    private Set<OpeningOccupationalDetails> occupationalDetails;
    private Set<OpeningCustomerRelative> customerRelatives;
}
