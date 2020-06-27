package com.sdjnshq.circle.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class TimeUtils {

    public static Date string2date(String strDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.parse(strDate);
        } catch (Exception ex) {
            return new Date();
        }
    }

    public static String date2String(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(date);
        return dateStr;
    }


    public static Calendar string2Calendar(String strDate) {
        Date date = string2date(strDate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

}
