package com.sb.solutions.api.accountType.entity;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.sb.solutions.api.accountPurpose.entity.AccountPurpose;

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
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "account_type_account_purpose",
        joinColumns = {@JoinColumn(name = "account_type_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "account_purpose_id", referencedColumnName = "id")}
    )
    private Set<AccountPurpose> accountPurpose;
}
