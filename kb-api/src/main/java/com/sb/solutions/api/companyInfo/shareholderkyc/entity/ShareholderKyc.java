package com.sb.solutions.api.companyInfo.shareholderkyc.entity;

import com.sb.solutions.api.address.district.entity.District;
import com.sb.solutions.core.enitity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * @author Mohammad Hussain
 * created on 12/30/2020
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
@Audited
public class ShareholderKyc extends BaseEntity<Long> {

    private String shareholderRelationship;
    private String relationName;
    private String citizenshipNumber;

    @Audited(targetAuditMode =  RelationTargetAuditMode.NOT_AUDITED)
    @ManyToOne
    private District district;

    private Date citizenshipIssuedDate;
    private String mobileNumber;
    private String shareholderKycAddress;
}
