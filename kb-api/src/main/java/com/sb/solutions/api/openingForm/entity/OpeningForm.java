package com.sb.solutions.api.openingForm.entity;

import com.sb.solutions.api.accountType.entity.AccountType;
import com.sb.solutions.api.branch.entity.Branch;
import com.sb.solutions.core.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "opening_form")
public class OpeningForm {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Branch branch;
    private Date requestedDate;
    private String fullName;
    @ManyToOne
    private AccountType accountType;
    private AccountStatus status;
    @Column(columnDefinition = "text")
    private String customerDetailsJson;
    @Transient
    private OpeningAccount openingAccount;
}
