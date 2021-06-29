package com.sb.solutions.api.collateralSiteVisit.entity;

import com.sb.solutions.api.security.entity.Security;
import com.sb.solutions.core.enitity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by Mohammad Hussain on May, 2021
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CollateralSiteVisit extends BaseEntity<Long> {
    private LocalDate siteVisitDate;
    private String securityName;
    private String siteVisitJsonData;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "security_id")
    @JsonIgnore
    private Security security;

    @OneToMany
    @JsonIgnore
    private List<SiteVisitDocument> siteVisitDocuments;
}
