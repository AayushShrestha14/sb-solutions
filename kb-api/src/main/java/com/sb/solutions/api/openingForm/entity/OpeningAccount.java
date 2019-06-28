package com.sb.solutions.api.openingForm.entity;

import java.util.Set;

import com.sb.solutions.api.accountPurpose.entity.AccountPurpose;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpeningAccount {

    private boolean haveExistingAccountNo;
    private String existingAccountNo;
    private AccountPurpose purposeOfAccount;
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
