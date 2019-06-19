package com.sb.solutions.api.eligibility.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchCriteria {

    private String key;

    private Object value;

    private String operation;

}
