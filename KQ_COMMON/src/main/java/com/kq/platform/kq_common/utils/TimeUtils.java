package com.kq.platform.kq_common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Zhou jiaqi on 2018/4/2.
 */

public class TimeUtils {
    public static final String formatDefault = "yyyy-MM-dd HH:mm";
    public static final String format1 = "yyyy-MM-dd HH:mm:ss";
    public static final String format2 = "yyyyMMddHHmmss";
    public static final String format3 = "yyyy-MM-dd";
    public static final SimpleDateFormat MDFormat = new SimpleDateFormat("MM月dd日", Locale.ENGLISH);
    public static final SimpleDateFormat HMFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
    public static final SimpleDateFormat YMDFormat = new SimpleDateFormat("yyyy年MM月dd日", Locale.ENGLISH);

    public TimeUtils() {
    }

    /**
     * 时间（毫秒）更换格式成分：秒
     * @param time
     * @return
     */
    public static String toTime(int time) {
        time /= 1000;
        int min = time / 60;
        if(min >= 60) {
            min %= 60;
        }

        int sec = time % 60;
        return String.format("%02d:%02d", new Object[]{Integer.valueOf(min), Integer.valueOf(sec)});
    }

    /**
     * Date 转换成Long
     * @param date
     * @return
     */
    public static long dateToLong(Date date) {
        if(date == null)
            return 0L;
        return date.getTime();
    }

    /**
     * Long 转换成Date
     * @param time
     * @return
     */
    public static Date longToDate(Long time) {
        if(time == null)
            return null;
        try {
            return new Date(time);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取当前时间
     * @return
     */
    public static Long getCurrentTime(){
        return System.currentTimeMillis();
    }

    /**
     * 时间戳转str
     * @param timestamp
     * @return
     */
    public static String timestampToStr(long timestamp){
        SimpleDateFormat df = new SimpleDateFormat(format1);//定义格式，不显示毫秒
        return df.format(timestamp);
    }

    /**
     * @Title:
     * @Description: 时间显示规则：当天的显示时间点；前一天的显示“昨天”； 超过昨天用星期几，如星期二；超过当前星期显示几月几日，
     *               如11月9日，当前星期用自然周（周一至周日）； 超过今年，显示年月日，如2014年12月01日；
     * @param newDate
     * @return 转换后的时间
     */
    public static String dateConvert(Date newDate) {
        if (newDate == null)
            return "";
        try {
            Calendar newCalendar = Calendar.getInstance();
            newCalendar.setTime(newDate);

            Calendar current = Calendar.getInstance();
            // 当前年月
            if (newCalendar.get(Calendar.YEAR) == current.get(Calendar.YEAR)
                    && newCalendar.get(Calendar.MONTH) == current
                    .get(Calendar.MONTH)) {
                // 定义昨天
                Calendar yesterday = Calendar.getInstance();
                yesterday.add(Calendar.DAY_OF_WEEK, -1);
                // 当天
                if (newCalendar.get(Calendar.DAY_OF_MONTH) == current
                        .get(Calendar.DAY_OF_MONTH)) {
                    return HMFormat.format(newDate);
                }
                // 昨天
                else if (newCalendar.get(Calendar.DAY_OF_MONTH) == yesterday
                        .get(Calendar.DAY_OF_MONTH)) {
                    return "昨天";
                }
                // 本周
                else if (newCalendar.get(Calendar.WEEK_OF_MONTH) == current
                        .get(Calendar.WEEK_OF_MONTH)) {
                    // 返回星期几
                    return "星期"
                            + figure2Chinese(newCalendar
                            .get(Calendar.DAY_OF_WEEK));
                } else {
                    return MDFormat.format(newDate);
                }
            } else if (newCalendar.get(Calendar.YEAR) == current
                    .get(Calendar.YEAR)
                    && newCalendar.get(Calendar.MONTH) != current
                    .get(Calendar.MONTH)) {
                return MDFormat.format(newDate);
            }
            // 不是当年
            else if (newCalendar.get(Calendar.YEAR) != current
                    .get(Calendar.YEAR)) {
                return YMDFormat.format(newDate);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return "";
    }

    /**
     * @Description: 将阿拉伯数字转换成星期汉字
     */
    public static String figure2Chinese(int figure) {
        String[] str = { "","日", "一", "二", "三", "四", "五", "六" };
        return str[figure];
    }

    /**
     * 获取当前日期
     * @return
     */
    public static Date getCurrentDate(){
        Date date = new Date();
        return date;
    }

    /**
     * 时间字符串转Date
     * @param dateStr 字符串
     * @param format 字符串格式
     * @return
     */
    public static Date dateToStr(String dateStr, String format){
        SimpleDateFormat sf = new SimpleDateFormat(format);
        if(dateStr != null && dateStr.length()>0){
            try {
                return sf.parse(dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    /**
     * 时间Date转字符串
     * @param date  Date时间
     * @param format 字符串格式
     * @return
     */
    public static String dateToStr(Date date, String format){
        try {
            SimpleDateFormat sf = new SimpleDateFormat(format);
            if(date != null){
                return sf.format(date);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return "";
    }

    /**
     * 将long型转成string
     * */
    public static String timestampToString(long time, String format) {
        try {
            if(time <= 0)
                return "";
            return dateToStr(new Date(String.valueOf(time).length()==10?time*1000:time), format);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return "";
    }

}
