package com.sb.solutions.api.openingForm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpeningAccount {
    private boolean haveExistingAccountNo;
    private String existingAccountNo;
    private String purposeOfAccount;
    private String currency;
    private boolean haveJoint;
    private boolean haveNominee;
    private boolean haveBeneficiary;
    private OpeningNominee nominee;
    private OpeningBeneficiary beneficiary;
    private Set<OpeningCustomer> openingCustomers;
    private double annualTurnover;
    private double annualTransaction;
    private boolean internetBanking;
    private boolean mobileBanking;
    private boolean debitCard;
    private boolean statement;
    private String statementFrequency;
    private String statementMode;

}
