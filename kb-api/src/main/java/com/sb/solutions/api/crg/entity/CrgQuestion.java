package com.sb.solutions.api.crg.entity;

import com.sb.solutions.api.eligibility.question.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

/**
 * @author Sunil Babu Shrestha on 9/10/2020
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrgQuestion extends Question {
    private String group;
}
