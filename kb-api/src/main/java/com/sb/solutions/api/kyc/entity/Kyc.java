
package com.sb.solutions.api.kyc.entity;

import com.sb.solutions.api.customer.entity.Customer;
import com.sb.solutions.api.customerRelative.entity.CustomerRelative;
import com.sb.solutions.api.occupationalDetails.entity.OccupationalDetails;
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
@Table(name = "ac_kyc")
public class Kyc extends BaseEntity<Long> {
    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "kyc", fetch = FetchType.LAZY)
    private Set<OccupationalDetails> occupationalDetails;

    @OneToMany(mappedBy = "kyc", fetch = FetchType.LAZY)
    private Set<CustomerRelative> customerRelatives;
    /*private String maritalStatus;
    private String gender;
    private String permanentAddress;
    private String presentAddress;
    private String email;
    private String contactNo;
    private Date dateOfBirth;
    private String nationality;
    private String panNo;
    private String occupationType;
    private String occupation;
    private String annualTurnover;
    private String annualTransaction;
    private boolean haveAccountInAnotherBank;
    private String bankName;
    private String education;
    private String map;*/


}
