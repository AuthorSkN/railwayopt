package com.railwayopt.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateManager {

    private static SimpleDateFormat dateFormater = new SimpleDateFormat("dd.MM.yyyy");

    public static String getNowDate(){
        Date nowDate = new Date();
        return dateFormater.format(nowDate);
    }

}
