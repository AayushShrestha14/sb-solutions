package com.sb.solutions.api.companyInfo.swot.entity;

import java.util.Objects;
import java.util.stream.Stream;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.util.Pair;

import com.sb.solutions.core.enitity.BaseEntity;
import com.sb.solutions.core.enitity.EntityValidator;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@EntityListeners({AuditingEntityListener.class})
@Audited
public class Swot extends BaseEntity<Long> implements EntityValidator {

    private String strength;

    private String weakness;

    private String opportunity;

    private String threats;

    @Override
    public Pair<Boolean, String> valid() {
        final String validationMsg = "Company Info -None of SWOT Analysis Section Field can be left empty.";
        Pair pair = Pair.of(Boolean.TRUE, "");
        Boolean anyAttributeNull = Stream.of(this.strength,
            this.weakness, this.opportunity,
            this.threats).anyMatch(Objects::isNull);
        if (anyAttributeNull) {
            pair = Pair.of(Boolean.FALSE,
                validationMsg);
        }
        return pair;
    }
}
