package com.song.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * // TODO description
 *
 * @author zhangsong
 * @version 2017-07-24
 */
public class DateUtil {
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public static Date parseStrToDate(String dateStr) throws ParseException {
        Date date = null;
        try {
            date = format.parse(dateStr);
        } catch (ParseException e) {
            throw e;
        }
        return date;
    }

    public static String parseDateToStr(Date date) {
        return format.format(date);
    }
}
