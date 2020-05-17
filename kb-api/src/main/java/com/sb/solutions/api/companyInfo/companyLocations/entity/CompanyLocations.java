package com.sb.solutions.api.companyInfo.companyLocations.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.sb.solutions.core.enitity.BaseEntity;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
//@EntityListeners({AuditingEntityListener.class})
@Table(name = "company_locations")
public class CompanyLocations extends BaseEntity<Long> {

    private String houseNumber;

    private String streetName;

    private String address;

}
