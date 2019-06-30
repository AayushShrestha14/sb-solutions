package com.sb.solutions.api.eligibility.document.entity;

import javax.persistence.Entity;

import com.sb.solutions.core.enitity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class SubmissionDocument extends BaseEntity<Long> {

    private String type;

    private String name;

    private String url;
}
