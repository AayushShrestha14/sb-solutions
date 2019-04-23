package com.sb.solutions.api.address.province.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sb.solutions.api.address.district.entity.District;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Province {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    @JsonIgnore
    @OneToMany(mappedBy = "province")
    private Set<District> districts;
}
