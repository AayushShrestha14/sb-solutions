package com.sb.solutions.api.guarantor.entity;

import com.sb.solutions.core.enitity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Guarantor extends BaseEntity <Long> {
    private String name;
    private String address;
    private String citizenNumber;
    private Date citizenIssuedYear;
    private String citizenIssuedPlace;
    private String contactNumber;
    private String fatherName;
    private String grandFatherName;
    private String relationship;
}
