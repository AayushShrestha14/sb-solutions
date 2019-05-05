package com.sb.solutions.api.customerRelative.entity;


import com.sb.solutions.api.kyc.entity.Kyc;
import com.sb.solutions.core.enitity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

//@Entity
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
//@EqualsAndHashCode(callSuper = true)
//@Table(name = "customer_relative")
//public class CustomerRelative extends BaseEntity<Long> {
public class CustomerRelative{
        private String title;
    private String customerRelation;
    private String customerRelativeName;
    private String citizenshipNumber;
    private String citizenshipIssuedPlace;
    private Date citizenshipIssuedDate;
    /*@JsonIgnore
    @ManyToOne
    @JoinColumn(name = "kyc_id")
    private Kyc kyc;*/
}
