package com.sb.solutions.api.branch.entity;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import com.sb.solutions.core.enitity.BaseEntity;
import com.sb.solutions.core.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Rujan Maharjan on 2/13/2019
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Branch extends BaseEntity<Long> {

    @NotNull(message = "Name should not be null")
    private String name;
    private String branchCode;
    private String address;
    private Status status;

}
