package com.sb.solutions.api.address.district.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sb.solutions.api.address.municipalityVdc.entity.MunicipalityVdc;
import com.sb.solutions.api.address.province.entity.Province;
import com.sb.solutions.api.branch.entity.Branch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class District {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "province_id")
    private Province province;
    @JsonIgnore
    @OneToMany(mappedBy = "district")
    private Set<MunicipalityVdc> municipalityVdcs;
    @JsonIgnore
    @OneToMany(mappedBy = "district", fetch = FetchType.LAZY)
    private Set<Branch> branch;
}
