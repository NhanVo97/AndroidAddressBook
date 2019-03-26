package com.example.qlsll.Utils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {
    public static boolean isEmailFormat(String valueToValidate) {
        // Regex
        String regexExpression = "[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?\\.)+[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?";
        Pattern regexPattern = Pattern.compile(regexExpression);
        boolean valid=false;
        if(valueToValidate!=null) {
            Matcher matcher = regexPattern.matcher(valueToValidate);
            valid=matcher.matches();
        }else{ // The case of empty Regex expression must be accepted
            Matcher matcher = regexPattern.matcher("");
            valid=matcher.matches();
        }
        return valid;
    }
    public static boolean isPhoneNumberFormat(String valueToValidate)
    {
        // Regex
        String regexExpression = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}";
        Pattern regexPattern = Pattern.compile(regexExpression);
        boolean valid = valid = false;
        if(valueToValidate!=null)
        {
            Matcher matcher = regexPattern.matcher(valueToValidate);
            valid = matcher.matches();
        }
        else
        {
            Matcher matcher = regexPattern.matcher("");
            valid = matcher.matches();
        }

        return valid;
    }
   public static String getStatus(int key)
   {
       String status = "";
       switch (key)
       {
           case 1:
               status = "Hoạt Động";
           case 2:
               status = "Không Hoạt Động";
           case 0:
               status = "Đã Xóa";
           case 4:
               status = "Đang Chờ";

       }
       return status;
   }
}
