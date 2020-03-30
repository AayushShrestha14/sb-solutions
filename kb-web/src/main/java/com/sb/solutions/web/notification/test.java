package com.sb.solutions.web.notification;

import java.util.Calendar;
import java.util.Date;

/**
 * @author : Rujan Maharjan on  3/27/2020
 **/
public class test {


    public static void main(String[] args) {

        System.out.println("Hello World");
        Date date = new Date();
        java.sql.Timestamp date1 = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());
        System.out.println(date1);


    }

}
