package kafka;


import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by zhaocw on 2016-7-21.
 *
 * @author zhaocw
 * <p>
 * Modified by lulj on 2017-01-09
 * SimpleDateFormat存在并发缺陷，这里用TLS包装，避免对同一实例的并发访问
 */
public class DateUtils {

    private static final ThreadLocal<DateFormat> TLS_MONTH_FORMAT =
            new ThreadLocal<DateFormat>() {
                @Override
                protected DateFormat initialValue() {
                    return new SimpleDateFormat("yyyyMM");
                }
            };

    private static DateFormat sdfCurMonth() {
        return TLS_MONTH_FORMAT.get();
    }

    private static final ThreadLocal<DateFormat> TLS_MONTH2_FORMAT =
            new ThreadLocal<DateFormat>() {
                @Override
                protected DateFormat initialValue() {
                    return new SimpleDateFormat("yyyy/MM");
                }
            };

    private static DateFormat sdfCurMonth2() {
        return TLS_MONTH2_FORMAT.get();
    }

    private static final ThreadLocal<DateFormat> TLS_DAY_FORMAT =
            new ThreadLocal<DateFormat>() {
                @Override
                protected DateFormat initialValue() {
                    return new SimpleDateFormat("yyyyMMdd");
                }
            };

    private static final ThreadLocal<DateFormat> TLS_HOUR_FORMAT =
            new ThreadLocal<DateFormat>() {
                @Override
                protected DateFormat initialValue() {
                    return new SimpleDateFormat("yyyyMMddHH");
                }
            };

    private static DateFormat sdfCurDay() {
        return TLS_DAY_FORMAT.get();
    }

    private static DateFormat sdfCurDayHour() {
        return TLS_HOUR_FORMAT.get();
    }

    // SimpleDateFormat有线程并发问题，使用ThreadLocal来包装一下
    private static final ThreadLocal<DateFormat> TLS_DATE_FORMAT =
            new ThreadLocal<DateFormat>() {
                @Override
                protected DateFormat initialValue() {
                    return new SimpleDateFormat("yyyy-MM-dd");
                }
            };

    // 缺省日期格式
    public static DateFormat defaultDateFormat() {
        return TLS_DATE_FORMAT.get();
    }

    private static final ThreadLocal<DateFormat> TLS_TIME_FORMAT =
            new ThreadLocal<DateFormat>() {
                @Override
                protected DateFormat initialValue() {
                    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                }
            };

    public final static DateTimeFormatter FORMATTER_DD = DateTimeFormatter.ofPattern("yyyyMMdd");


    public final static DateTimeFormatter FORMATTER_HH = DateTimeFormatter.ofPattern("yyyyMMddHH");

    private static DateFormat sdfCurTime() {
        return TLS_TIME_FORMAT.get();
    }

    // 格式化为缺省的字符串格式
    public static String formatDate(Date date) {
        return defaultDateFormat().format(date);
    }

    // 格式化为缺省的字符串格式
    public static String formatDate(Timestamp ts) {
        return defaultDateFormat().format(ts);
    }

    // 格式化为缺省的字符串格式
    public static Date parseDate(String date) throws ParseException {
        return defaultDateFormat().parse(date);
    }

    public static String formatTime(Date date) {
        return sdfCurTime().format(date);
    }

    public static String formatTime(long timeMillis) {
        return sdfCurTime().format(new Date(timeMillis));
    }

    //格式化为"yyyyMMdd"类型的字符串
    public static String getFormatDay(Date date) {
        return sdfCurDay().format(date);
    }

    public static String getFormatDay(Timestamp ts) {
        return sdfCurDay().format(ts);
    }

    public static String getFormatDayHour(Timestamp ts) {
        return sdfCurDayHour().format(ts);
    }

    public static String getFormatTime(Timestamp ts) {
        return sdfCurTime().format(ts);
    }

    // 解析"yyyyMMdd"格式的日期字符串
    public static Date parseTheDay(String theDay) throws ParseException {
        return sdfCurDay().parse(theDay);
    }

    //格式化为"yyyyMM"类型的字符串
    public static String getFormatMonth1(Date date) {
        return sdfCurMonth().format(date);
    }

    //格式化为"yyyy/MM"类型的字符串
    public static String getFormatMonth2(Date date) {
        return sdfCurMonth2().format(date);
    }

    // 格式化为"yyyyMM"类型的日期格式
    public static Date parseMonth1(String date) throws ParseException {
        return sdfCurMonth().parse(date);
    }

    /**
     * 返回当前月份，例如：201607
     *
     * @return
     */
    public static String getCurMonth() {
        return sdfCurMonth().format(new Date());
    }

    public static String getCurDay() {
        return sdfCurDay().format(new Date());
    }

    public static String getYesterDay() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        String yesterday = sdfCurDay().format(cal.getTime());
        return yesterday;
    }

    //获取前30天时间
    public static String getThirTyDay() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -30);
        String last = sdfCurDay().format(cal.getTime());
        return last;
    }

    /**
     * <p>〈获取给定值前n天时间〉
     * day:yyyyMMdd
     *
     * @throws ParseException
     * @author weizc 2017年8月28日
     */
    public static String getlastDay(String day, int n) throws ParseException {
        Date date = sdfCurDay().parse(day);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, -n);
        String last = sdfCurDay().format(cal.getTime());
        return last;
    }

    public static Date getlastDay(Date day, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(day);
        cal.add(Calendar.DATE, -n);
        ;
        return cal.getTime();
    }

    /**
     * 获取月份字符串
     * monthcount  ，负数为给定月份startDate的前monthcount月，
     * 正数为给定月份startDate的后monthcount月
     *
     * @throws ParseException
     */
    public static String getMonthByParam(int monthcount, String startDate) throws ParseException {
        Date date = sdfCurDay().parse(startDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, monthcount);
        String thirdMonthBeforeNow = sdfCurMonth().format(cal.getTime());
        return thirdMonthBeforeNow;
    }

    /**
     * 根据时间字符串获取开始时间
     * startDate 开始时间，格式为20161001
     */
    public static String getStartTime(String startDate) {
        String collectStartTime =
                startDate.substring(0, 4) + "-" +
                        startDate.substring(4, 6) + "-" + startDate.substring(6, 8) + " 00:00:00";
        return collectStartTime;
    }

    /**
     * 根据时间字符串获取结束时间
     * startDate 开始时间，格式为20161001
     */
    public static String getEndTime(String startDate) {
        String collectEndTime =
                startDate.substring(0, 4) + "-" + startDate.substring(4, 6)
                        + "-" + startDate.substring(6, 8) + " 23:59:59";
        return collectEndTime;
    }

    /**
     * 根据时间字符串获取月份
     * startDate 开始时间，格式为20161001
     */
    public static int getMonth(String startDate) {
        int month = Integer.parseInt(startDate.substring(4, 6));
        return month;
    }

    /**
     * 将长时间格式时间转换为字符串 format为格式
     *
     * @param dateDate
     * @return
     */
    public static String dateToStrLong(Date dateDate, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String dateString = formatter.format(dateDate);
        return dateString;
    }

    /**
     * 获得当前时间后一天
     *
     * @param specifiedDay
     * @return
     * @description
     */
    public static String getSpecifiedDayAfter(String specifiedDay) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String dayAfter = "";
        if (date != null) {
            c.setTime(date);
            int day = c.get(Calendar.DATE);
            c.set(Calendar.DATE, day + 1);
            dayAfter = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        }
        return dayAfter;
    }

    /**
     * 获得下个月的第一天
     *
     * @param date
     * @return
     * @description
     */
    public static Date nextMonthFirstDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 获得下个月的最后一天
     *
     * @param date
     * @return
     * @description
     */
    public static Date nextMouthLastDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        return calendar.getTime();
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param early
     * @param late
     * @return
     */
    public static final int daysBetween(Date early, Date late) {

        Calendar calst = Calendar.getInstance();
        Calendar caled = Calendar.getInstance();
        calst.setTime(early);
        caled.setTime(late);
        //设置时间为0时
        calst.set(Calendar.HOUR_OF_DAY, 0);
        calst.set(Calendar.MINUTE, 0);
        calst.set(Calendar.SECOND, 0);
        caled.set(Calendar.HOUR_OF_DAY, 0);
        caled.set(Calendar.MINUTE, 0);
        caled.set(Calendar.SECOND, 0);
        //得到两个日期相差的天数
        int days = ((int) (caled.getTime().getTime() / 1000) - (int) (calst
                .getTime().getTime() / 1000)) / 3600 / 24;

        return days;
    }

    /**
     * 获取前一天时间
     *
     * @return
     */
    public static String getLastDay() {
        Date dNow = new Date();   //当前时间
        Date dBefore = new Date();
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(dNow);//把当前时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
        dBefore = calendar.getTime();   //得到前一天的时间
        String defaultStartDate = sdfCurDay().format(dBefore);    //格式化前一天
        return defaultStartDate;
    }


    public static String getTheHour() {
        Calendar c = Calendar.getInstance();
        int result = c.get(Calendar.HOUR_OF_DAY);
        return result < 10 ? ("0" + result) : (String.valueOf(result));
    }

    //返回前一个小时，00~23
    public static String getPrevHour() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR_OF_DAY, -1);  //设置为前一小时
        int result = cal.get(Calendar.HOUR_OF_DAY);
        return result < 10 ? ("0" + result) : (String.valueOf(result));
    	/*Calendar cal = Calendar.getInstance();
    	int hour = cal.get(Calendar.HOUR_OF_DAY)-1;
    	return hour<10?("0"+hour):(String.valueO(hour));*/
    }

    //返回 YYYYMMDDHH格式的timeamp
    public static Timestamp getDateHourTs(String date, String hour) {
        try {
            Date d = parseTheDay(date);
            Calendar c = Calendar.getInstance();
            c.setTime(d);
            c.set(Calendar.HOUR, Integer.parseInt(hour));
            return new Timestamp(c.getTimeInMillis());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;

    }


}
