package com.sb.solutions.entity;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.sb.solutions.core.enitity.BaseEntity;

/**
 * @author : Rujan Maharjan on  12/4/2020
 **/
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CadFile extends BaseEntity<Long> {

    private String cadDocument;

    private String draftPath;

    private String signedPath;

    private String initialInformation;

    private String supportedInformation;
}
