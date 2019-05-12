package com.sb.solutions.core.dateValidation;

import com.sb.solutions.core.exception.ApiException;
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
    private Data date;
    public boolean checkDate(Date date){
        if(date.before(new Date())){
            return true;
        }else {
            return false;
        }
    }
   /* public boolean checkDateAfter(Date date){
        if(date.before(new Date())){
            throw new ApiException("Invalid Date");
        }else {
            return true;
        }
    }*/


}
