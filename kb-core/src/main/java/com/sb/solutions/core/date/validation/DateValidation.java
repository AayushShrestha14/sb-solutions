package com.sb.solutions.core.date.validation;

import java.util.Date;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class DateValidation {

    public boolean checkDate(Date date) {
        if (date.before(new Date())) {
            return true;
        }
        return false;
    }
}
