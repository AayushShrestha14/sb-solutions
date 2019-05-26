package com.sb.solutions.api.dms.dmsloanfile.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sb.solutions.api.common.entity.Stage;
import com.sb.solutions.core.enitity.BaseEntity;
import com.sb.solutions.core.enums.LoanType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class LoanFileStage extends BaseEntity<Long> {
    @OneToOne
    @JsonIgnore
    private Stage currentStage;
    @ElementCollection
    @JsonIgnore
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Stage> previousStageList;
    private LoanType loanType;

}