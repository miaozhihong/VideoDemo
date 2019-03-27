package com.jm.utils;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateChangesUtils {

    /*时间戳转换成字符窜*/
    public static String getDateToString(long time, String timetype) {
        Date d = new Date(time);
        SimpleDateFormat sf = new SimpleDateFormat(timetype); //例如 "yyyy-MM-dd"
        return sf.format(d);
    }

    /*将字符串转为时间戳*/
    public static long getStringToDate(String time, String timetype) {
        SimpleDateFormat sdf = new SimpleDateFormat(timetype);
        Date date = new Date();
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    /*将字符串转为日期*/
    public static Date getStringToDateDate(String time, String timetype) {
        SimpleDateFormat sdf = new SimpleDateFormat(timetype);
        Date date = new Date();
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 判断当前日期是星期几<br>
     * <br>
     *
     * @param pTime 修要判断的时间<br>
     * @return dayForWeek 判断结果<br>
     * @Exception 发生异常<br>
     */
    public static int dayForWeek1(String pTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(pTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int dayForWeek = 0;
        if (Integer.parseInt(c.get(Calendar.DAY_OF_WEEK) + "") == 1) {
            dayForWeek = 7;
        } else {
            dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        }
        return dayForWeek;
    }

    //把String类型的值转为日期格式
    public static Date strToDate(String time) {
        //通过日期获得月和日
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    //把String类型的值转为日期格式
    public static String getNowDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());
        String str = formatter.format(curDate);
        return str;
    }


}
