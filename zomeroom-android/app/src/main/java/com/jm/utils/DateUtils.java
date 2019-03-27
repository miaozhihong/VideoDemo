package com.jm.utils;


import com.jm.base.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


/**
 * @change author: wangdeshun
 * @change time: 2017/11/27 11:15
 * @change description:  添加获取当前日期
 */
public class DateUtils {
    /**
     * 计算指定月份的天数
     *
     * @param year
     * @param month
     * @return
     */
    public static int getMonthDays(int year, int month) {
        month++;
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) {
                    return 29;
                } else {
                    return 28;
                }
            default:
                return -1;
        }
    }

    /**
     * 返回当前月份1号位于周几
     *
     * @param year  年份
     * @param month 月份，传入系统获取的，不需要正常的
     * @return 日：1		一：2		二：3		三：4		四：5		五：6		六：7
     */
    public static int getFirstDayWeek(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获得当前日期-天
     *
     * @return
     */
    public static String getCurrentDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date currentTime = new Date();// 当前时间
        return formatter.format(currentTime);
    }

    /**
     * 获得当前日期-分
     *
     * @return
     */
    public static String getCurrentTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentTime = new Date();// 当前时间
        return formatter.format(currentTime);
    }

    /**
     * 返回对应日期的long值
     */
    public static long getCurrentDayLong(String date) {
        java.text.DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date parse = null;
        long time = -1;
        try {
            parse = df.parse(date);
            time = parse.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * 获取当前日期是周几<br>
     *
     * @param dt
     * @return 当前日期是周几
     */
    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekDays[w];
    }

    /**
     * 转换日期为对应格式string
     */
    public static String fromDateToString(Date date, String type) {
        SimpleDateFormat sf = new SimpleDateFormat(type);
        return sf.format(date);
    }

    /**
     * 从网络获取百度网站时间
     */
    public static Date getBaiduDate() {
        /*URL url;
        Date date = null;
        try{
            url=new URL("http://www.baidu.com");
            URLConnection uc=url.openConnection();
            uc.connect();
            long ld = uc.getDate();
            date =new Date(ld);
        }catch (Exception e){

        }*/
//        return date;
        return new Date();
    }

    /**
     * 获取当前日期
     *
     * @return 返回格式  xxxx-xx-xx
     */
    public static String getAriNowTime() {
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        c.add(Calendar.DAY_OF_MONTH, 0);
        int year = c.get(Calendar.YEAR); // 获取当前年份
        int month = c.get(Calendar.MONTH) + 1;// 获取当前月份
        int day = c.get(Calendar.DAY_OF_MONTH);// 获取当前月份的日期号码
        return year + "-" + month + "-" + day;
    }

    public static String getDateAndWeek(long date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd ");
        Date currentTime = new Date(date);
        String d = formatter.format(currentTime);
        String weekOfDate = getWeekOfDate(currentTime);
        return d + " " + weekOfDate;
    }

    /**
     * 获取日
     *
     * @param timeStamp
     * @return
     */
    public static String getDayByTimeStamp(long timeStamp) {
        String date = timeStampToDate(timeStamp);
        String day = date.substring(8, 10);
        if (day.length() < 1) {
            day = "0" + day;
        }
        return day;
    }

    /**
     * 获取月
     *
     * @param timeStamp
     * @return
     */
    public static String getMonthByTimeStamp(long timeStamp) {
        String date = timeStampToDate(timeStamp);
        String month = date.substring(5, 7);
        if (month.length() < 1) {
            month = "0" + month;
        }
        return month;
    }

    /**
     * 获取年
     *
     * @param timeStamp
     * @return
     */
    public static String getYearByTimeStamp(long timeStamp) {
        String date = timeStampToDate(timeStamp);
        String year = date.substring(0, 4);
        return year;
    }

    /**
     * 时间戳转换日期
     *
     * @param timeStamp
     * @return
     */
    public static String timeStampToDate(long timeStamp) {
        Date date = new Date(timeStamp);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = simpleDateFormat.format(date);
        return dateStr;
    }

    /**
     * 时间戳转换日期
     *
     * @param timeStamp
     * @return
     */
    public static String timeStampToYearMonthDay(long timeStamp) {
        Date date = new Date(timeStamp);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = simpleDateFormat.format(date);
        return dateStr;
    }
    /**
     * 时间戳转换时间 时分秒
     *
     * @param timeStamp
     * @return
     */
    public static int[] timeStampToSFM(long timeStamp) {
        long sfm = 0;
        if (Constants.TWO_HOURS > timeStamp) {
            sfm = Constants.TWO_HOURS - timeStamp;
        }
        Date date = new Date(sfm);
        int[] time = {date.getHours()-8,date.getMinutes(),date.getSeconds()};
        return time;
    }
}
