package com.sb.solutions.api.accountType.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sb.solutions.api.accountPurpose.entity.AccountPurpose;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountType {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "account_purpose_id")
    private AccountPurpose accountPurpose;
}
