package com.sb.solutions.api.customerGroup;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;

import com.sb.solutions.api.customer.entity.Customer;
import com.sb.solutions.core.enitity.BaseEntity;
import com.sb.solutions.core.enitity.EntityValidator;
import com.sb.solutions.core.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@EntityListeners({AuditingEntityListener.class})
public class CustomerGroup extends BaseEntity<Long> {

  @Column(unique = true, nullable = false)
  private String groupCode;

  private BigDecimal groupLimit;

  private Status status = Status.ACTIVE;

}
