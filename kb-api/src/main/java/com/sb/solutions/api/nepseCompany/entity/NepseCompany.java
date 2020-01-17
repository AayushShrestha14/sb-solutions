package com.sb.solutions.api.nepseCompany.entity;

import com.sb.solutions.core.enitity.BaseEntity;
import com.sb.solutions.core.enums.ShareType;
import com.sb.solutions.core.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class NepseCompany extends BaseEntity<Long> {

    private String companyName;

    private Double amountPerUnit;

    private Status status = Status.ACTIVE;

    private String companyCode;

    private ShareType shareType;
}
