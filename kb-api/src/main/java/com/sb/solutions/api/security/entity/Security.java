package com.sb.solutions.api.security.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.sb.solutions.api.guarantor.entity.Guarantor;
import com.sb.solutions.core.enitity.BaseEntity;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@EntityListeners({AuditingEntityListener.class})
@Audited
public class Security extends BaseEntity<Long> {

    private String data;

    @NotAudited
    @OneToMany(cascade = CascadeType.ALL)
    private List<Guarantor> guarantor;

}
