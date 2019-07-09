package com.sb.solutions.api.financial.entity;

import javax.persistence.Entity;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.sb.solutions.core.enitity.BaseEntity;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Financial extends BaseEntity<Long> {

    private String path;

    @Transient
    private String data;
}
