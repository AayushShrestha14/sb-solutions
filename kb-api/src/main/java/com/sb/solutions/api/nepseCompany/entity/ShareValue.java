package com.sb.solutions.api.nepseCompany.entity;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.sb.solutions.core.enitity.BaseEntity;
import com.sb.solutions.core.enums.Status;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ShareValue extends BaseEntity<Long> {
    private String shareData;
    private Status status;
}
