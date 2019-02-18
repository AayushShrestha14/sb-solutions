package com.sb.solutions.core.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Sunil Babu Shrestha on 1/13/2019
 */
@Data
@AllArgsConstructor
public class ApiException extends RuntimeException {
    private String message;
}
