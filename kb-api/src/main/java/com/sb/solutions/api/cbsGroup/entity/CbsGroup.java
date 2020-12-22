package com.sb.solutions.api.cbsGroup.entity;

import java.util.Date;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 * @author : Rujan Maharjan on  12/21/2020
 **/
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CbsGroup extends AbstractPersistable<Long> {

    private String obligor;

    private String jsonData;

    private Date lastModifiedAt = new Date();

}
