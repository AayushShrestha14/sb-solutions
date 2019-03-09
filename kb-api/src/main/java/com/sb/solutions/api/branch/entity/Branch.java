package com.sb.solutions.api.branch.entity;

import com.sb.solutions.core.enitity.AbstractBaseEntity;
import com.sb.solutions.core.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

/**
 * @author Rujan Maharjan on 2/13/2019
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Branch extends AbstractBaseEntity<Long> {
    @NotNull(message = "Name should not be null")
    private String name;
    private String branchCode;
    private String address;
    private Status status;

}
