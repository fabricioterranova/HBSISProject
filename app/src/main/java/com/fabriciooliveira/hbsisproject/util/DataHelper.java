package com.fabriciooliveira.hbsisproject.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by fabriciooliveira on 1/23/16.
 */
public class DataHelper {

    public static String getLongDate(long longValue) {
        Date date=new Date(longValue);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        return simpleDateFormat.format(date);
    }
}
