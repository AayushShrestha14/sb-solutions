package com.sb.solutions.core.date.validation;

import java.util.Date;

import lombok.Data;
import org.springframework.stereotype.Component;

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
