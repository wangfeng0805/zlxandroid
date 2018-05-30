package com.example.ylf019.zlxandroid.utils;




import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import org.ocpsoft.prettytime.PrettyTime;
/**
 * Author:    guozhangyu
 * Description: 日期帮助工具类

 */
public class DateHelper {

    /**
     * 获取当前日期
     *
     * @return date 当前日期
     */
    public static Date getCurrentDate() {
        return Calendar.getInstance().getTime();
    }

    /**
     * 获取当前日期 yyyy-MM-dd
     *
     * @return 当前日期 2015-08-16
     */
    public static String getCurrentDateFormat() {
        return getCurrentDateFormat(getCurrentDate());
    }

    /**
     * 获取当前日期 yyyy-MM-dd
     *
     * @return 当前日期 2015-08-16
     */
    public static String getCurrentDateFormat(Date date) {
        return formatDate(date);
    }

    /**
     * 格式化日期 格式yyyy-MM-dd
     *
     * @param date 需要格式化的日期
     * @return 日期 2015-08-16
     */
    public static String formatDate(Date date) {
        return formatDate(date, "yyyy-MM-dd");
    }

    /**
     * 格式化日期
     *
     * @param date           需要格式化的日期
     * @param formatTemplate 格式yyyy-MM-dd
     * @return 日期 2015-08-16
     */
    public static String formatDate(Date date, String formatTemplate) {
        SimpleDateFormat sDateFormat = new SimpleDateFormat(formatTemplate, Locale.CHINA);
        return sDateFormat.format(date);
    }

    /**
     * 获取当前日期的上一天
     *
     * @param currentDate 当前日期
     * @return 日期 2015-08-16
     */
    public static String getCurrentPreDateFormat(Date currentDate) {
        return formatDate(getCurrentPreDate(currentDate));
    }

    /**
     * 获取当前日期的上一天
     *
     * @param currentDate 当前日期
     * @return 日期 Date
     */
    public static Date getCurrentPreDate(Date currentDate) {
        Calendar ca = Calendar.getInstance();//得到一个Calendar的实例
        ca.setTime(currentDate); //设置时间为当前时间
        ca.add(Calendar.DAY_OF_MONTH, -1); //日期减1
        return ca.getTime(); //结果
    }

    /**
     * 获取当前日期的下一天
     *
     * @param currentDate 当前日期
     * @return 日期 2015-08-16
     */
    public static String getCurrentNextDateFormat(Date currentDate) {
        return formatDate(getCurrentNextDate(currentDate));
    }

    /**
     * 获取当前日期的下一天
     *
     * @param currentDate 当前日期
     * @return 日期 Date
     */
    public static Date getCurrentNextDate(Date currentDate) {
        Calendar ca = Calendar.getInstance();//得到一个Calendar的实例
        ca.setTime(currentDate); //设置时间为当前时间
        ca.add(Calendar.DAY_OF_MONTH, 1); //日期减1
        return ca.getTime(); //结果
    }

    private static PrettyTime prettyTime = new PrettyTime();

    /**
     * 获取时间显示 （几小时前、几天前）
     *
     * @param dateStr 需要转化的时间
     * @return 用于显示的时间
     */
    public static String getWeiBoDataTime(String dateStr) {
        String dateTime = "1小时前";
        if (StringHelper.notEmpty(dateStr)) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINESE);
            try {
                Date date = df.parse(dateStr);
                dateTime = prettyTime.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            return "";
        }
        return dateTime;
    }

    /**
     * 将字符串转化为Date
     * @param date_str Date字符串
     * @return
     * @throws ParseException
     */
    public static Date parseStringtoDate(String date_str) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINESE);
        return sdf.parse(date_str);
    }

    /**
     * 返回当前日期前一日格式化后Date
     * @return
     */
    public static String getCurrentPreDateFormat() {
        return getCurrentPreDateFormat(getCurrentDate());
    }
}
