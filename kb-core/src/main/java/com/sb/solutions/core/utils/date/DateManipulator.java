package com.sb.solutions.core.utils.date;

import java.util.Calendar;
import java.util.Date;

public class DateManipulator {

    private Date date;

    public DateManipulator(Date date) {
        this.date = date;
    }

    public static void main(String[] args) {
        // Test
        // Add
        Date date = new Date();
        DateManipulator dateManipulator = new DateManipulator(date);
        System.out.println("Date: " + date);
        System.out.println("Add Days: " + dateManipulator.addDays(1));
        System.out.println("Add Hours: " + dateManipulator.addHours(1));
        System.out.println("Add Minutes: " + dateManipulator.addMinutes(1));
        System.out.println("Add Seconds: " + dateManipulator.addSeconds(1));
        // Minus
        System.out.println("Add Days: " + dateManipulator.addDays(-1));
        System.out.println("Add Hours: " + dateManipulator.addHours(-1));
        System.out.println("Add Minutes: " + dateManipulator.addMinutes(-1));
        System.out.println("Add Seconds: " + dateManipulator.addSeconds(-1));
    }

    public Date addDays(int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(this.date);
        c.add(Calendar.DATE, days);
        return c.getTime();
    }

    public Date addHours(int hours) {
        Calendar c = Calendar.getInstance();
        c.setTime(this.date);
        c.add(Calendar.HOUR_OF_DAY, hours);
        return c.getTime();
    }

    public Date addMinutes(int minutes) {
        Calendar c = Calendar.getInstance();
        c.setTime(this.date);
        c.add(Calendar.MINUTE, minutes);
        return c.getTime();
    }

    public Date addSeconds(int seconds) {
        Calendar c = Calendar.getInstance();
        c.setTime(this.date);
        c.add(Calendar.SECOND, seconds);
        return c.getTime();
    }

}
