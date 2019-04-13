package com.sb.solutions.api.address.municipalityVdc.entity;

import com.sb.solutions.api.address.district.entity.District;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MunicipalityVdc {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "district_id")
    private District district;

}
