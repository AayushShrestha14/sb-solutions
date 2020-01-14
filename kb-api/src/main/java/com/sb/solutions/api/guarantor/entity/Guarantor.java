package com.sb.solutions.api.guarantor.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.sb.solutions.api.address.district.entity.District;
import com.sb.solutions.api.address.municipalityVdc.entity.MunicipalityVdc;
import com.sb.solutions.api.address.province.entity.Province;
import com.sb.solutions.core.enitity.BaseEntity;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@EntityListeners({AuditingEntityListener.class})
public class Guarantor extends BaseEntity<Long> {

    private String name;
    private String citizenNumber;
    private Date issuedYear;

    private String issuedPlace;
    private String contactNumber;
    private String fatherName;
    private String grandFatherName;
    private String relationship;

    @ManyToOne
    private Province province;

    @ManyToOne
    private District district;

    @ManyToOne
    private MunicipalityVdc municipalities;
}
