package com.sb.solutions.core.utils.date;


import java.util.Arrays;

import org.joda.time.DateTime;

/**
 * @author Rujan Maharjan on 6/7/2019
 */
public class EnglishDate {

    public static final String WEEK_DAYS[] = {"Sunday", "Monday", "Tuesday",
        "Wednesday", "Thursday", "Friday", "Saturday"};

    private DateTime englishDate;

    public EnglishDate(DateTime englishDate) {
        this.englishDate = englishDate;
    }


    public int getYear() {
        return englishDate.getYear();
    }

    public String getMonthAsText() {
        return englishDate.monthOfYear().getAsText();
    }

    public int getMonth() {
        return englishDate.getMonthOfYear();
    }

    public int getDate() {
        return englishDate.getDayOfMonth();
    }

    public String toString() {
        return englishDate.getYear() + " / " + englishDate.getMonthOfYear() + " / " +
            englishDate.getDayOfMonth() + " " + WEEK_DAYS[englishDate.getDayOfWeek()];
    }

    public static int getWeekIndex(String weekDay) {
        return Arrays.asList(WEEK_DAYS).indexOf(weekDay) + 1;
    }
}
