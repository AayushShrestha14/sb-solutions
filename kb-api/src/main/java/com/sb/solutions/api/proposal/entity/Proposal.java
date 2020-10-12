package com.sb.solutions.api.proposal.entity;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.sb.solutions.core.enitity.BaseEntity;
import com.sb.solutions.core.utils.NumberToWordsConverter;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@EntityListeners({AuditingEntityListener.class})
@Audited
public class Proposal extends BaseEntity<Long> {

    private String data;
    private BigDecimal proposedLimit;
    private Double tenureDurationInMonths;
    private String checkedData;
    private BigDecimal existingLimit;
    private BigDecimal outStandingLimit;

    @Transient
    private String proposedAmountInWords;

    public String getProposedAmountInWords() {
        try {
            return NumberToWordsConverter
                .calculateAmountInWords(String.valueOf(this.getProposedLimit()));
        } catch (Exception e) {
            return null;
        }
    }

}
