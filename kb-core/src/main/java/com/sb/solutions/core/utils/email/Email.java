package com.sb.solutions.core.utils.email;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Email {

    private List<String> bcc;
    private String body;
    private List<String> attachment;
    private String subject;
    private String to;
    private String toName;
    private String bankName;
    private String bankBranch;
    private String bankWebsite;
    private String accountType;
    private String resetPasswordLink;
    private String expiry;
    private String loanType;

}
