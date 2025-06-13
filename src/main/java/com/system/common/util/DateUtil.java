package com.system.common.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class DateUtil {
    public static String addOrSubtractDate(String ymd, int addYear, int addMonth, int addDays) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        LocalDate date = LocalDate.parse(ymd, formatter);

        // 연도와 월을 더함
        LocalDate newDate = date.plusYears(addYear).plusMonths(addMonth);

        // 일수를 더하거나 뺌
        if (addDays != 0) {
            newDate = newDate.plusDays(addDays); // 양수이면 더하고, 음수이면 뺌
        }

        return newDate.format(formatter);
    }
    public static String LocalDate(String pattern){
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

        return currentDate.format(formatter);
    }
    public static Map<String, String> getServerTime() {
        Map<String, String> response = new HashMap<>();
        LocalDateTime now = LocalDateTime.now();
        String formattedTime = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String[] a;
        a = formattedTime.split(" ");
        response.put("serverDate", a[0]);
        response.put("serverTime", a[1]);
        return response;
    }
}
