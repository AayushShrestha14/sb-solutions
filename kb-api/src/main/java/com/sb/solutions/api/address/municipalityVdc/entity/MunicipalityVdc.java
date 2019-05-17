package com.sb.solutions.api.address.municipalityVdc.entity;

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
public class MunicipalityVdc {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "district_id")
    private District district;

    @JsonIgnore
    @OneToMany(mappedBy = "municipalityVdc", fetch = FetchType.LAZY)
    private Set<Branch> branch;

    @JsonIgnore
    @OneToMany(mappedBy = "municipalityVdc", fetch = FetchType.LAZY)
    private Set<Valuator> valuator;
    @JsonIgnore
    @OneToMany(mappedBy = "siteMunicipalityVdc", fetch = FetchType.LAZY)
    private Set<Valuator> valuatorSite;

}
