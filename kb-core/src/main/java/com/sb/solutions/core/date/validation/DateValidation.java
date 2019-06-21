package com.sb.solutions.core.date.validation;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

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
