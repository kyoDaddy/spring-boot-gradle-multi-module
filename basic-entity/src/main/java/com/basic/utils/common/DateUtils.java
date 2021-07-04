package com.basic.utils.common;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static String getDate(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, java.util.Locale.KOREA);
        return sdf.format(new Date());
    }

}
