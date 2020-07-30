package com.tdkj.System.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

/**
 * @Description 时间工具类
 * @ClassName DateUtil
 * @Author Chang
 * @date 2020.05.28 08:50
 */
public class DateUtil {
    public static final String FORMAT_LONOGRAM = "yyyy-MM-dd";

    public static final String FULL_TIME_PATTERN = "yyyyMMddHHmmss";

    public static final String FULL_TIME_SPLIT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static final String CST_TIME_PATTERN = "EEE MMM dd HH:mm:ss zzz yyyy";

    public static String formatFullTime(LocalDateTime localDateTime) {
        return formatFullTime(localDateTime, FULL_TIME_PATTERN);
    }

    public static String formatFullTime(LocalDateTime localDateTime, String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return localDateTime.format(dateTimeFormatter);
    }

    public static String getDateFormat(Date date, String dateFormatType) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormatType, Locale.CHINA);
        return simpleDateFormat.format(date);
    }

    public static String formatCstTime(String date, String format) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(CST_TIME_PATTERN, Locale.US);
        Date usDate = simpleDateFormat.parse(date);
        return DateUtil.getDateFormat(usDate, format);
    }

    public static String formatInstant(Instant instant, String format) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return localDateTime.format(DateTimeFormatter.ofPattern(format));
    }


    /*传入String类型的日期，转成yyyy-MM-dd HH:mm:ss 的时间返回*/
    public static Date formatDate(String date) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FULL_TIME_SPLIT_PATTERN, Locale.CHINESE);
        Date usDate = simpleDateFormat.parse(date);
        return usDate;
    }

    public static Date getformatDate(String date) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT_LONOGRAM);
        Date usDate = simpleDateFormat.parse(date);
        return usDate;
    }

    public static String getformatDate(Date date) throws ParseException {
       return  new SimpleDateFormat(FULL_TIME_PATTERN).format(new Date());
    }

    public static String getToday(){
        return new SimpleDateFormat(FORMAT_LONOGRAM).format(new Date());
    }

    public static Boolean isBeforeAndAfter(String data1,String data2) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat(FULL_TIME_SPLIT_PATTERN);
        Date date1 = sdf.parse(data1);
        Date date2 = sdf.parse(data2);
        if(date1.before(new Date())&&date2.after(new Date())){
            return true;
        }else{
            return false;
        }
    }

    public static Boolean isbefore(String data1) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat(FULL_TIME_SPLIT_PATTERN);
        Date date1 = sdf.parse(data1);
        if(date1.before(new Date())){
            return true;
        }else{
            return false;
        }
    }




}
