package com.sb.solutions.api.address.province.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sb.solutions.api.address.district.entity.District;
import com.sb.solutions.api.branch.entity.Branch;
import com.sb.solutions.api.valuator.entity.Valuator;
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
    private Long id;
    private String name;
    @JsonIgnore
    @OneToMany(mappedBy = "province")
    private Set<District> districts;
    @JsonIgnore
    @OneToMany(mappedBy = "province", fetch = FetchType.LAZY)
    private Set<Branch> branch;
    @JsonIgnore
    @OneToMany(mappedBy = "province", fetch = FetchType.LAZY)
    private Set<Valuator> valuator;
    @JsonIgnore
    @OneToMany(mappedBy = "siteProvince", fetch = FetchType.LAZY)
    private Set<Valuator> valuatorSite;
}
