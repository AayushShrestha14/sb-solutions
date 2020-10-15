package com.sb.solutions.api.customer.entity;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.AuditJoinTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.util.Pair;

import com.sb.solutions.api.address.district.entity.District;
import com.sb.solutions.api.address.municipalityVdc.entity.MunicipalityVdc;
import com.sb.solutions.api.address.province.entity.Province;
import com.sb.solutions.api.customerRelative.entity.CustomerRelative;
import com.sb.solutions.core.enitity.BaseEntity;
import com.sb.solutions.core.enitity.EntityValidator;
import com.sb.solutions.core.enums.Status;
import com.sb.solutions.core.utils.string.NameFormatter;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "customer")
@EntityListeners({AuditingEntityListener.class})
@Audited
public class Customer extends BaseEntity<Long> implements EntityValidator {

    @NotAudited
    private String profilePic;

    @JsonDeserialize(using = NameFormatter.class)
    private String customerName;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date dob;


    @ManyToOne
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Province province;

    @ManyToOne
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private District district;

    @ManyToOne
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private MunicipalityVdc municipalities;

    private String street;
    private String wardNumber;
    private String contactNumber;
    private String occupation;
    private String incomeSource;
    private String email;
    private Date initialRelationDate;
    private String citizenshipNumber;
    private Date citizenshipIssuedDate;
    private String citizenshipIssuedPlace;
    private Status status = Status.ACTIVE;
    private String introduction;

    @Transient
    private String bankingRelationship;

    /*@Transient
    private String customerUniqueId;*/


    @AuditJoinTable
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CustomerRelative> customerRelatives;

    private String nepaliDetail;

    @Override
    public Pair<Boolean, String> valid() {
        final String validationMsg =
            "Customer Info - Name, Date of Birth, citizenship Number, Citizenship Issue date,"
                + "Citizenship Issued Place";
        Pair pair = Pair.of(Boolean.TRUE, "");
        Boolean anyAttributeNull = Stream.of(this.customerName,
            this.dob, this.citizenshipIssuedPlace,
            this.citizenshipNumber, this.citizenshipIssuedDate).anyMatch(Objects::isNull);
        if (anyAttributeNull) {
            pair = Pair.of(Boolean.FALSE,
                validationMsg);
        }
        return pair;
    }
}
