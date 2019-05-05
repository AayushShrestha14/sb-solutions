package com.sb.solutions.api.nominee.entity;

import com.sb.solutions.core.enitity.BaseEntity;
import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

//@Entity
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
//@EqualsAndHashCode(callSuper = true)
//@Table(name = "nominee")
//public class Nominee extends BaseEntity<Long> {
public class Nominee{
        private String fullName;
    private String imagePath;
    private String relationToMe;
    private Date dateOfBirth;
    private int age;
    private String citizenNumber;
    private String issuedPlace;
    private String temporaryAddress;
    private String permanentAddress;
    private String contactAddress;
    private String contactNumber;
}
