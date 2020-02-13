package com.sb.solutions.api.loan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class contains specific attributes required to log the comment for particular loan.
 *
 * @author Elvin Shrestha on 2/13/2020
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanRemarkDto {

    private String limitExceed; // exceed share considered value
}
