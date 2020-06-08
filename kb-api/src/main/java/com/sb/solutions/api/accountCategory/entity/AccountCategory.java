package com.sb.solutions.api.accountCategory.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.sb.solutions.api.accountType.entity.AccountType;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountCategory {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne
    private AccountType accountType;
}
