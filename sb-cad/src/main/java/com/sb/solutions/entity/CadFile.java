package com.sb.solutions.entity;

import com.sb.solutions.core.enitity.BaseEntity;
import com.sb.solutions.enums.CADDocument;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

/**
 * @author : Rujan Maharjan on  12/4/2020
 **/
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CadFile extends BaseEntity<Long> {

    private CADDocument cadDocument;

    private String draftPath;

    private String signedPath;

    private String initialInformation;

    private String supportedInformation;
}
