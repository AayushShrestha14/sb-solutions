package com.sb.solutions.api.guarantor.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.NotAudited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.sb.solutions.api.address.district.entity.District;
import com.sb.solutions.api.address.municipalityVdc.entity.MunicipalityVdc;
import com.sb.solutions.api.address.province.entity.Province;
import com.sb.solutions.core.enitity.BaseEntity;
import com.sb.solutions.core.utils.string.NameFormatter;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@EntityListeners({AuditingEntityListener.class})
public class Guarantor extends BaseEntity<Long> {

    @JsonDeserialize(using = NameFormatter.class)
    private String name;

    @NotAudited
    @ManyToOne
    private Province province;

    @NotAudited
    @ManyToOne
    private District district;

    @NotAudited
    @ManyToOne
    private MunicipalityVdc municipalities;

    private String citizenNumber;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date issuedYear;

    private String issuedPlace;
    private String contactNumber;
    @JsonDeserialize(using = NameFormatter.class)
    private String fatherName;
    @JsonDeserialize(using = NameFormatter.class)
    private String grandFatherName;

    private String relationship;

    private String docPath;

    private Double netWorth;

    private Boolean consentOfLegalHeirs;


}
