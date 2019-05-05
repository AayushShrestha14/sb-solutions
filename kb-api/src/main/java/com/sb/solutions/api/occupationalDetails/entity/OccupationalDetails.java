package com.sb.solutions.api.occupationalDetails.entity;

import com.sb.solutions.api.kyc.entity.Kyc;
import com.sb.solutions.core.enitity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

//@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
/*@EqualsAndHashCode(callSuper = true)
@Table(name = "occupational_details")*/
public class OccupationalDetails{
    private String nameOfOrganization;
    private String address;
    private String telNo;
    private String natureOfBusiness;
    private String designation;
    private String estimatedAnnualIncome;
    /*@JsonIgnore
    @ManyToOne
    @JoinColumn(name = "kyc_id")
    private Kyc kyc;*/

}
