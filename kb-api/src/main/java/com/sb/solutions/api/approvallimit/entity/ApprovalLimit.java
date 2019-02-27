package com.sb.solutions.api.approvallimit.entity;

import com.sb.solutions.api.approvallimit.emuns.Authorities;
import com.sb.solutions.api.approvallimit.emuns.LoanApprovalType;
import com.sb.solutions.api.approvallimit.emuns.LoanCategory;
import com.sb.solutions.core.enitity.AbstractBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApprovalLimit extends AbstractBaseEntity<Long> {
    private static final long serialVersionUID = 1L;

    private double amount;
    @Column(nullable = true)
    private LoanApprovalType loanApprovalType;
    private LoanCategory loanCategory;
    private Authorities authorities;


}
