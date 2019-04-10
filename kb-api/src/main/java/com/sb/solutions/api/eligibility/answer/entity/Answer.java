package com.sb.solutions.api.eligibility.answer.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sb.solutions.api.eligibility.question.entity.Question;
import com.sb.solutions.core.enitity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Answer extends BaseEntity<Long> {

    private String description;

    private long points;

    @ManyToOne
    @JoinColumn(name = "question_id")
    @JsonIgnore
    private Question question;

}
