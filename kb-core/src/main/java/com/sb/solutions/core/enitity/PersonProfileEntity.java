package com.sb.solutions.core.enitity;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.Email;

/**
 * @author Sunil Babu Shrestha on 12/27/2018
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class PersonProfileEntity extends BaseEntity<Long> {

    private String firstName;
    private String middleName;
    private String lastName;

    @Email
    @Column(unique = true)
    private String email;
    private String phoneNo;
}
