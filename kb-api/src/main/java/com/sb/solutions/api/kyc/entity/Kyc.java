package com.sb.solutions.api.kyc.entity;

import com.sb.solutions.api.customer.entity.Customer;
import com.sb.solutions.api.customerRelative.entity.CustomerRelative;
import com.sb.solutions.core.enitity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "kyc")
public class Kyc extends BaseEntity<Long> {
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToMany(mappedBy = "kyc", fetch = FetchType.LAZY)
    private Set<CustomerRelative> customerRelatives;


}
