package com.sb.solutions.api.branch.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import com.sb.solutions.core.enitity.BaseEntity;
import com.sb.solutions.core.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Rujan Maharjan on 2/13/2019
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Branch extends BaseEntity<Long> {

    @NotNull(message = "Name should not be null")
    private String name;

    @Column(name = "branch_code")
    private String branchCode;
    private String address;
    private Status status;
}
