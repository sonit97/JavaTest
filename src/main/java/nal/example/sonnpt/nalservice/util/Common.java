package nal.example.sonnpt.nalservice.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import java.text.ParseException;

/**
 * @author Son
 * @version 1.0
 * @since 2020-03-20
 */
public class Common {
    private static final String DATE_PATTERN = "yyyy-MM-dd";

    /**
     * Parse date
     *
     * @param date the date
     * @return String
     */
    public static final Date getDate(String date) throws ParseException {
        if (date == null || "".equals(date)) {
            return null;
        }
        return new SimpleDateFormat(DATE_PATTERN).parse(date);
    }
}