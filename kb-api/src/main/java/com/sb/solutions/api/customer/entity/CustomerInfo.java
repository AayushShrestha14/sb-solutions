package com.sb.solutions.api.customer.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.sb.solutions.api.customer.enums.CustomerIdType;
import com.sb.solutions.api.customer.enums.CustomerType;
import com.sb.solutions.api.siteVisit.entity.SiteVisit;
import com.sb.solutions.core.enitity.BaseEntity;
import com.sb.solutions.core.enums.Status;

/**
 * @author : Rujan Maharjan on  8/9/2020
 **/

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "customer_info")
@EntityListeners({AuditingEntityListener.class})
public class CustomerInfo extends BaseEntity<Long> {

    @NotNull(message = "name cannot be null")
    private String name;

    private CustomerIdType idType;

    @NotNull(message = "idNumber cannot be null")
    private String idNumber;

    private Date idRegDate;

    private CustomerType customerType;


    private String contactNo;

    private boolean isBlacklist = false;

    @Email
    private String email;


    private String idRegPlace;

    private Long associateId;

    private Status status = Status.ACTIVE;

    @OneToOne
    private SiteVisit siteVisit;

}
