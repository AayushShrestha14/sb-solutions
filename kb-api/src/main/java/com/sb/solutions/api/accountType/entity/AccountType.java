package com.sb.solutions.api.accountType.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sb.solutions.api.accountPurpose.entity.AccountPurpose;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

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
    @ManyToMany
    @JoinTable(name = "account_type_account_purpose",
            joinColumns = {@JoinColumn(name = "account_type_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "account_purpose_id", referencedColumnName = "id")}
    )
    private Set<AccountPurpose> accountPurpose;
}
