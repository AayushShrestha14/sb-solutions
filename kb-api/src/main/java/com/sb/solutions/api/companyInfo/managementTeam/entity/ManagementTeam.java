package com.sb.solutions.api.companyInfo.managementTeam.entity;

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
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@EntityListeners({AuditingEntityListener.class})
@Audited
public class ManagementTeam extends BaseEntity<Long> implements EntityValidator {

    private String name;
    private String description;
    private String designation;

    @Override
    public Pair<Boolean, String> valid() {
        final String validationMsg = "Company Info - Management Team Name or Designation cannot be left empty.";
        Pair pair = Pair.of(Boolean.TRUE, "");
        Boolean anyAttributeNull = Stream.of(this.name,
            this.designation).anyMatch(Objects::isNull);
        if (anyAttributeNull) {
            pair = Pair.of(Boolean.FALSE,
                validationMsg);
        }
        return pair;
    }

}
