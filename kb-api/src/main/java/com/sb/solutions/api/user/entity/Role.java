package com.sb.solutions.api.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue
    private long id;
    private String userType;
    @JsonIgnore
    @ManyToMany(mappedBy = "role",cascade = CascadeType.ALL)
    private Collection<User> users;

}
