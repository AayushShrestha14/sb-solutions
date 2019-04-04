package com.sb.solutions.api.branch.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sb.solutions.api.user.entity.User;
import com.sb.solutions.core.enitity.AbstractBaseEntity;
import com.sb.solutions.core.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Rujan Maharjan on 2/13/2019
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "status <> 2")
public class Branch extends AbstractBaseEntity<Long> {
    @NotNull(message = "Name should not be null")
    private String name;
    private String branchCode;
    private String address;
    private Status status;
    @JsonIgnore
    @OneToMany(mappedBy = "branch")
    private List<User> users;

}
