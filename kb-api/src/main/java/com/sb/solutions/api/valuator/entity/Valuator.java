package com.sb.solutions.api.valuator.entity;

import com.sb.solutions.core.enitity.BaseEntity;
import com.sb.solutions.core.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Valuator extends BaseEntity<Long> {

    private String name;

    private String address;

    private String contactNo;

    private String siteLocation;

    private Status status;

}
