package com.snapdeal.ims.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;

/**
 * Date format utility class.
 */
@Slf4j
public class DateUtil {

    public static Date dateFromString(String date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setLenient(false);

        if (date == null) {
            return null;
        }

        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            log.debug("date invalid format");
            return null;
        }
    }

    public static String formatDate(Date date, String format) {
        if (null == date)
            return null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setLenient(false);
        return sdf.format(date);
    }
}
