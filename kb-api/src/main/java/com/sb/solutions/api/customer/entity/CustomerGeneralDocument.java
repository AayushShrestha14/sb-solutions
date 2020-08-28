package com.sb.solutions.api.customer.entity;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.sb.solutions.api.document.entity.Document;
import com.sb.solutions.core.enitity.BaseEntity;
import com.sb.solutions.core.validation.constraint.NotEmpty;

/**
 * @author : Rujan Maharjan on  8/25/2020
 **/

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "customer_general_document")
@EntityListeners({AuditingEntityListener.class})
public class CustomerGeneralDocument extends BaseEntity<Long> {

    @OneToOne
    private Document document;

    private String docPath;

    @NotEmpty(message = "customer Id cannot be null")
    private Long customerInfoId;

}
