package com.sb.solutions.api.fiscalyear.entity;

import java.time.LocalDate;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.sb.solutions.core.enitity.BaseEntity;
import com.sb.solutions.core.enums.Status;

@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FiscalYear extends BaseEntity<Long> {

    private String fiscalYear;
    private Boolean isCurrentYear;
    private Status status;
    private LocalDate qOneStartDate;
    private LocalDate qOneEndDate;
    private LocalDate qTwoStartDate;
    private LocalDate qTwoEndDate;
    private LocalDate qThreeStartDate;
    private LocalDate qThreeEndDate;
    private LocalDate qFourStartDate;
    private LocalDate qFourEndDate;

}
