package com.sb.solutions.api.openingForm.entity;

import com.sb.solutions.api.branch.entity.Branch;
import com.sb.solutions.core.enitity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "opening_form")
public class OpeningForm extends BaseEntity<Long> {
    @ManyToOne
    private Branch branch;
    private Date requestedDate;
    private String fullName;
    @Column(columnDefinition = "text")
    private String customerDetailsJson;
    @Transient
    private Set<OpeningCustomer> openingCustomers;
}
