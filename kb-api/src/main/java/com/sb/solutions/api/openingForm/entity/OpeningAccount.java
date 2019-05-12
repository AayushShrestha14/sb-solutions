package com.sb.solutions.api.openingForm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpeningAccount {
    private String accountType;
    private String accountNo;
    private String proposeOfAccount;
    private String currency; private boolean isJoint;
    private boolean haveNominee;
    private boolean haveBeneficiary;
    private OpeningNominee nominee;
    private OpeningBeneficiary beneficiary;
    private Set<OpeningCustomer> openingCustomers;
}
