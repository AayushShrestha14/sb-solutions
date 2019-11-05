package com.sb.solutions.api.loan.entity;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.sb.solutions.core.enitity.BaseEntity;

/**
 * @author yunish on 11/5/2019
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CustomerDocument extends BaseEntity<Long> {

    private String documentPath;
    @Transient
    private List<String> documentMap;
}
