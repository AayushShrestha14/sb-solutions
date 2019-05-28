package com.sb.solutions.core.date.validation;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Date;
@Component
@Data
@NoArgsConstructor
public class DateValidation {
    public boolean checkDate(Date date) {
        if(date.before(new Date())){
            return true;
        }else {
            return false;
        }

    }
}
