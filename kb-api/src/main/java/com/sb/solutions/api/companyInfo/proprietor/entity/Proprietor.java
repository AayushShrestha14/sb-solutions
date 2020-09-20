package com.sb.solutions.api.companyInfo.proprietor.entity;

import java.util.Objects;
import java.util.stream.Stream;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.util.Pair;

import com.sb.solutions.api.address.district.entity.District;
import com.sb.solutions.api.address.municipalityVdc.entity.MunicipalityVdc;
import com.sb.solutions.api.address.province.entity.Province;
import com.sb.solutions.core.enitity.BaseEntity;
import com.sb.solutions.core.enitity.EntityValidator;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@EntityListeners({AuditingEntityListener.class})
@Audited
public class Proprietor extends BaseEntity<Long> implements EntityValidator {

    private String name;

    private String contactNo;

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @ManyToOne
    private Province province;

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @ManyToOne
    private District district;

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @ManyToOne
    private MunicipalityVdc municipalityVdc;

    private double share;
    private String type;

    @Override
    public Pair<Boolean, String> valid() {
        final String validationMsg = "Company Info - Proprietor Information Section Name or contactNo cannot be left empty.";
        Pair pair = Pair.of(Boolean.TRUE, "");
        Boolean anyAttributeNull = Stream.of(this.name,
            this.contactNo).anyMatch(Objects::isNull);
        if (anyAttributeNull) {
            pair = Pair.of(Boolean.FALSE,
                validationMsg);
        }
        return pair;
    }
}
