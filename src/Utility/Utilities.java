package Utility;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utilities {

    public static double truncateDouble(double value, int decimalsNumber) {
        return (double) ((int) (value * Math.pow(10, decimalsNumber))) / Math.pow(10, decimalsNumber);
    }

    public static double truncate (double decimal) {
        return (double) Math.round(decimal*100.0)/100.0;
    }


    public static String simpleDateFormat(Date date) {
    String pattern = "yyyy-MM-dd";
    SimpleDateFormat sd = new SimpleDateFormat(pattern);
    return sd.format(date);
    }
}
