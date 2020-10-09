package com.sb.solutions.api.fiscalyear.entity;

import java.util.Date;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    private String year;
    private Boolean isCurrentYear;
    private Status status;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date qOneStartDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date qOneEndDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date qTwoStartDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date qTwoEndDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date qThreeStartDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date qThreeEndDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date qFourStartDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date qFourEndDate;

}
