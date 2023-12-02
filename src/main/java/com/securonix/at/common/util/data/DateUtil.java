package com.securonix.at.common.util.data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateUtil {
    private DateUtil() {
        super();
    }

    public static String getDatePlusDays(int increment, String pattern){
        LocalDateTime dateTime = LocalDateTime.now();
        return formatDate((ChronoUnit.DAYS.addTo(dateTime,increment)),pattern);
    }

    public static String getDatePlusMonths(int increment, String pattern){
        LocalDateTime dateTime = LocalDateTime.now();
        return formatDate((ChronoUnit.MONTHS.addTo(dateTime,increment)),pattern);
    }

    public static String getDatePlusWeeks(int increment, String pattern){
        LocalDateTime dateTime = LocalDateTime.now();
        return formatDate((ChronoUnit.WEEKS.addTo(dateTime,increment)),pattern);
    }

    public static String getDatePlusYears(int increment, String pattern){
        LocalDateTime dateTime = LocalDateTime.now();
        return formatDate((ChronoUnit.YEARS.addTo(dateTime,increment)),pattern);
    }

    public static String formatDate(LocalDateTime dateTime,String pattern){
        return dateTime.format(DateTimeFormatter.ofPattern(pattern));
    }
}
