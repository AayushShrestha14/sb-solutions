package com.sb.solutions.api.kyc.entity;

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

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<CustomerRelative> customerRelatives;


}
