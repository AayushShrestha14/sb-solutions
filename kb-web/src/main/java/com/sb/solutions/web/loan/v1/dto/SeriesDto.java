package com.sb.solutions.web.loan.v1.dto;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class SeriesDto {
    private String name;
    private BigDecimal value;
    private Long fileCount;
}
