package com.sb.solutions.core.dateValidation;

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
        if(date.after(new Date())){
            System.out.println("Invalid date");
            return false;
        }else {
            return true;
        }
    }


}
