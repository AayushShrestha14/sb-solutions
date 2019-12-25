package com.sb.solutions.api.companyInfo.swot.entity;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.sb.solutions.core.enitity.BaseEntity;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@EntityListeners({AuditingEntityListener.class})
@Audited
public class Swot extends BaseEntity<Long> {

    private String strength;

    private String weakness;

    private String opportunity;

    private String threats;

}
