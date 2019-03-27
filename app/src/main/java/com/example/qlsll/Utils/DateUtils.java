package com.example.qlsll.Utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
public class DateUtils {
    // get UTC time now
    public static Date getUTCNow() {
        int offset = TimeZone.getDefault().getOffset(new Date().getTime());
        return new Date(new Date().getTime() - offset);
    }
    // convert to UTC time
    public static String convertToUTC(String strDate) {
        String strUTCDate = "";
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            Date date = formatter.parse(strDate);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            strUTCDate = sdf.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return strUTCDate;

    }
    public static String formatDateString(String utcDate, boolean isUTC)
    {
        String arrDate[] = utcDate.split("-");
        if(isUTC)
        return arrDate[2].split("T")[0]+"-"+arrDate[1]+"-"+arrDate[0];
        else
            return arrDate[2]+"-"+arrDate[1]+"-"+arrDate[0];
    }
}
