package com.sb.solutions.core.date.validation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DateValidation {
    private Date date;

    public boolean checkDate(Date date) {
        if (date.after(new Date())) {
            return true;
        }
        return false;
    }
}
