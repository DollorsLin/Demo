package com.yun.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * 时间日期工具类
 */
public class TimeDateUtils {

    public static Logger log = LoggerFactory.getLogger(TimeDateUtils.class);

    public static String shortDate_pattern = "yyyy-MM-dd";

    public static DateFormat format_today = new SimpleDateFormat("HH:mm");

    public static DateFormat format_d = new SimpleDateFormat("yyyy-MM-dd");
    public static DateFormat format_h = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static DateFormat format_short = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public static DateFormat format_h_client = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    public static DateFormat format_h_recharge = new SimpleDateFormat("yyyyMMddHHmmssSSS");



    @SuppressWarnings("deprecation")
    public static Date MIN_DATE = new Date(Date.UTC(1000, 1, 1, 0, 0, 0));
    /**
     * 一周的时间(单位毫秒)
     */
    public static final long ONEWEEK = 7 * 3600 * 24 * 1000;
    /**
     * 一天的时间(单位毫秒)
     */
    public static final long ONEDAY = 3600 * 24 * 1000;
    /**
     * 一小时的时间(单位毫秒)
     */
    public static final long ONEHOUR = 3600 * 1000;

    /**
     * 分钟(单位毫秒)
     */
    public static final long ONEMIN = 60 * 1000;
    /**
     * 秒(单位毫秒)
     */
    public static final long ONESECOND = 1000;
    public static TimeZone timezone = TimeZone.getTimeZone("GMT+08:00");




    /**
     * 判断时间相等
     */
    public static Boolean compareDate(Date d1, Date d2) {
        if (d1 == null || d2 == null)
            return false;
        String s1 = format_h.format(d1);
        String s2 = format_h.format(d2);
        if (s1.equals(s2))
            return true;
        else
            return false;
    }

    /**
     * 判断日期相等
     */
    public static Boolean compareDay(Date d1, Date d2) {
        if (d1 == null || d2 == null)
            return false;
        String s1 = format_d.format(d1);
        String s2 = format_d.format(d2);
        if (s1.equals(s2))
            return true;
        else
            return false;
    }

    /**
     * 判断是否在活动时间范围内
     */
    public static boolean isTodayInThePeriod(Date beginTime, Date endTime) {
        if (beginTime == null || endTime == null)
            return false;
        long nt = new Date().getTime();
        if (beginTime.getTime() - nt <= 0 && endTime.getTime() - nt >= 0)
            return true;
        return false;
    }

    /**
     * 获取当前时间戳(毫秒)
     *
     * @return
     * @name getCurrentTime
     */
    public static long getCurrentTime() {
        return System.currentTimeMillis();
    }

    /**
     * 获取当前时间戳(秒)
     *
     * @return
     * @name getCurrentTime
     */
    public static int getCurrentTimeBySeconds() {
        return (int) (System.currentTimeMillis() / 1000);
    }

    public static String getCurDateByDateFormat(Date date, String format) {
        DateFormat format_d = new SimpleDateFormat(format);
        return format_d.format(date);
    }

    /**
     * 返回长日期格式,例如 “2012-12-20 00:00”格式
     *
     * @param date
     * @return
     */
    public static String getShortDate(Date date, DateFormat format_short) {
        if (date == null) {
            return "";
        }
        return format_short.format(date);
    }

    /**
     * 返回 “2012-12-20”格式
     *
     * @return
     */
    public static String getCurDate() {
        return TimeDateUtils.format_d.format(new Date(System.currentTimeMillis() - 5 * 60 * 1000));
    }

    /**
     * 返回短日期格式,例如 “2012-12-20”格式
     *
     * @param date
     * @return
     */
    public static String getShortDate(Date date) {
        return TimeDateUtils.format_d.format(date);
    }

    /**
     * 返回长日期格式,例如 “2012-12-20 00:00:00”格式
     *
     * @param date
     * @return
     */
    public static String getLongDate(Date date) {
        if (date == null) {
            return "";
        }
        return format_h.format(date);
    }

    /**
     * 时间类型转字符串
     *
     * @param date
     * @return 20180110093210
     */
    public static String getStringTime(Date date) {
        String printData = format_h_recharge.format(date);
        return printData;
    }

    /**
     * 判断当前时间是否在活动期间内
     *
     * @param beginTime
     * @param endTime
     * @return
     */
    public static boolean isTodayInThePeriod(String beginTime, String endTime) {
        if (beginTime == null || beginTime.equals("")) {
            log.error("beginTime is null");
            return false;
        }
        if (endTime == null || endTime.equals("")) {
            log.error("endTime is null");
            return false;
        }
        // 在活动期间内获可得积分
        Date begin = TimeDateUtils.string2Date(beginTime, format_h);
        Date end = TimeDateUtils.string2Date(endTime, format_h);
        return isTodayInThePeriod(begin, end);
    }

    /**
     * 获取今天零时的时间
     *
     * @return 零时以long类型返回的数值
     */
    public static long getTodayZeroTime() {
        long now = System.currentTimeMillis();
        long more = now % ONEHOUR;
        int nowHour = getHour();
        long zeroTime = now - (nowHour * ONEHOUR + more);
        return zeroTime;
    }

    /**
     * 获取本周零时时间(周日)
     *
     * @return
     */
    public static long getWeekZeroTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.DAY_OF_WEEK, 1);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取明天零时的时间
     *
     * @return 零时以long类型返回的数值
     */
    public static long getTomorrowZeroTime() {
        long zeroTime = getTodayZeroTime();
        zeroTime += ONEDAY;
        return zeroTime;
    }

    /**
     * 获取明天中午的时间
     *
     * @return 零时以long类型返回的数值
     */
    public static long getTomorrowMiddayTime() {
        long time = getTodayZeroTime() + ONEDAY / 2;

        if (System.currentTimeMillis() > time) {
            time += ONEDAY;
        }

        return time;
    }

    /**
     * 获取距离当前时间最近的时间
     *
     * @param hour [1,24]
     * @return
     * @createTime 2015年5月14日下午7:43:33
     */
    public static long getTomorrowHourTime(int hour) {
        long time = getTodayZeroTime() + ONEHOUR * hour;

        if (System.currentTimeMillis() > time) {
            time += ONEDAY;
        }

        return time;
    }

    /**
     * 获取任意一个时间点当天的零时时间
     *
     * @param theDay
     * @return
     */
    public static long getSomedayZeroTime(long theDay) {
        int nowHour = getHour();
        long more = theDay % ONEHOUR;
        long zeroTime = theDay - (nowHour * ONEHOUR + more);
        return zeroTime;
    }

    /**
     * 返回任意一天的最后一秒
     *
     * @param theDay
     * @return
     */
    public static long getSomedayMaximumTime(long theDay) {
        int nowHour = getHour();
        long more = theDay % ONEHOUR;
        long zeroTime = theDay - (nowHour * ONEHOUR + more) + ONEDAY - 1000;
        return zeroTime;
    }

    /**
     * 默认最小时间
     *
     * @return
     * @throws Exception
     */
    public static Date getSmallDayTime() {
        Date date = null;
        try {
            date = format_h.parse("1000-01-01 00:00:00");
        } catch (ParseException e) {
            log.error("时间转换出错", e);
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 返回指定日期的第2天时间
     *
     * @param date
     * @return
     */
    public static Date getSomeDayTomorrowTime(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, 1);
        date = calendar.getTime();
        return date;
    }

    /**
     * 获取今天零时的时间
     *
     * @return 零时以int类型返回的数值
     */
    public static int getTodayZeroTimeReturnInt() {
        long todayZeroTime = getTodayZeroTime();
        return (int) (todayZeroTime / 1000);
    }

    /**
     * 获取明天零时的时间
     *
     * @return 零时以int类型返回的数值
     */
    public static int getTomorrowZeroTimeReturnInt() {
        long tomorrowZeroTime = getTomorrowZeroTime();
        return (int) (tomorrowZeroTime / 1000);
    }

    /**
     * 获取距离明天零点剩余的秒数
     *
     * @return 以int类型返回的数值
     */
    public static int getTomorrowZeroLeftInt() {
        long timeLeft = TimeDateUtils.getTomorrowZeroTime() - System.currentTimeMillis();
        return (int) (timeLeft / 1000);
    }

    /**
     * 获取距离明天中午剩余的秒数
     *
     * @return 以int类型返回的数值
     */
    public static int getTomorrowMiddayLeftInt() {
        long timeLeft = TimeDateUtils.getTomorrowMiddayTime() - System.currentTimeMillis();
        return (int) (timeLeft / 1000);
    }

    /**
     * 获取距离第2天凌晨5点剩余的秒数
     *
     * @return 以int类型返回的数值
     */
    public static int getTomorrowFiveHourLeftInt() {
        long timeLeft = TimeDateUtils.getTomorrowHourTime(5) - System.currentTimeMillis();
        return (int) (timeLeft / 1000);
    }

    /**
     * 返回24小时的小时数
     *
     * @return
     * @createTime 2015年8月13日下午4:59:41
     */
    public static int getHour() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(timezone);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static String printTime(int time) {
        long arg = time * 1000l;
        Date date = new Date(arg);
        format_h.setTimeZone(timezone);
        String printData = format_h.format(date);
        return printData;
    }

    public static String printTime(long time) {
        Date date = new Date(time);
        String printData = format_h.format(date);
        return printData;
    }

    /**
     * 字符串转换成日期格式
     *
     * @param dateString  待转换的日期字符串
     * @param datePattern 日期格式
     * @return 转换后的日期
     */
    public static Date string2Date(String dateString, DateFormat format) {
        if (dateString == null || dateString.trim().length() == 0) {
            return null;
        }
        try {
            return format.parse(dateString);
        } catch (ParseException e) {
            log.error("ParseException in Converting String to date: " + e.getMessage());
        }
        return null;
    }

    /**
     * 字符串转换成短日期格式(例如2015-01-01)
     *
     * @param dateString  待转换的日期字符串
     * @param datePattern 日期格式
     * @return 转换后的日期
     */
    public static Date parseShortDate(String dateString) {
        if (dateString == null || dateString.trim().length() == 0) {
            return null;
        }
        try {
            return format_d.parse(dateString);
        } catch (ParseException e) {
            log.error("ParseException in Converting String to date: " + e.getMessage());
        }
        return null;
    }

    /**
     * 字符串转换成短日期格式(例如11/24/2016 14:01:41)
     *
     * @param dateString  待转换的日期字符串
     * @param datePattern 日期格式
     * @return 转换后的日期
     */
    public static Date parseLongDateClient(String dateString) {
        if (dateString == null || dateString.trim().length() == 0) {
            return null;
        }
        try {
            return format_h_client.parse(dateString);
        } catch (ParseException e) {
            log.error("ParseException in Converting String to date: " + e.getMessage());
        }
        return null;
    }

    /**
     * 字符串转换成短日期格式(例如2015-01-01 12:00:00)
     *
     * @param dateString  待转换的日期字符串
     * @param datePattern 日期格式
     * @return 转换后的日期
     */
    public static Date parseLongDate(String dateString) {
        if (dateString == null || dateString.trim().length() == 0) {
            return null;
        }
        try {
            return format_h.parse(dateString);
        } catch (ParseException e) {
            log.error("ParseException in Converting String to date: " + e.getMessage());
        }
        return null;
    }

    /**
     * 仅仅返回时间
     *
     * @param time
     * @return 时分秒
     * @name printOnlyTime
     * @condition 这里描述这个方法适用条件
     * @author lobbyer
     * @date：2012-8-30 下午07:52:08
     */
    public static String printOnlyTime(int time) {
        long arg = time * 1000l;
        Date date = new Date(arg);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        format.setTimeZone(timezone);
        String printData = format.format(date);
        return printData;
    }

    /**
     * 获取下个小时的整点时间
     *
     * @return
     */
    public static long getNextHourTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.HOUR, 1);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取指定时间的时间部分,从当天0点开始的秒数
     *
     * @return
     */
    public static int getTimeOfDay(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        int sec = calendar.get(Calendar.SECOND);

        int t = hour * 3600 + min * 60 + sec;
        return t;
    }

    /**
     * Sets the values for the fields YEAR, MONTH, DAY_OF_MONTH, HOUR, MINUTE,
     * and SECOND. Previous values of other fields are retained. If this is not
     * desired, call clear() first.
     *
     * @param year      the value used to set the YEAR calendar field.
     * @param month     the value used to set the MONTH calendar field. Month value is
     *                  0-based. e.g., 1 for January.
     * @param date      the value used to set the DAY_OF_MONTH calendar field.
     * @param hourOfDay the value used to set the HOUR_OF_DAY calendar field.(24
     *                  hours)
     * @param minute    the value used to set the MINUTE calendar field.
     * @param second    the value used to set the SECOND calendar field.
     * @return
     * @createTime 2015年7月21日下午3:23:33
     */
    public static Date getDateTime(int year, int month, int date, int hourOfDay, int minute, int second) {
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("month is [1,12]");
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, date, hourOfDay, minute, second);
        return calendar.getTime();
    }

    /**
     * Sets the values for the fields YEAR, MONTH, DAY_OF_MONTH, HOUR, MINUTE,
     * and SECOND. Previous values of other fields are retained. If this is not
     * desired, call clear() first.
     *
     * @param year  the value used to set the YEAR calendar field.
     * @param month the value used to set the MONTH calendar field. Month value is
     *              0-based. e.g., 1 for January.
     * @param date  the value used to set the DAY_OF_MONTH calendar field.
     * @return
     * @createTime 2015年7月21日下午3:23:33
     */
    public static Date getDateTime(int year, int month, int date) {
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("month is [1,12]");
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, date);
        return calendar.getTime();
    }

    /**
     * 获取指定时间的月份
     *
     * @return
     */
    public static int getMonthOfYear(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return calendar.get(Calendar.MONTH);
    }

    /**
     * 获取指定时间是当月的第几天
     *
     * @return
     */
    public static int getDayOfMonth(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取指定时间是当周的第几天，周日返回1
     *
     * @return
     */
    public static int getDayOfWeek(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 判断两个日期相差的天数
     *
     * @param arg
     */
    public static int getDays(Date fDate, Date tDate) {
        DateFormat sdf = format_d;
        try {
            fDate = sdf.parse(sdf.format(fDate));
            tDate = sdf.parse(sdf.format(tDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar f = Calendar.getInstance();
        Calendar t = Calendar.getInstance();
        f.setTime(fDate);
        t.setTime(tDate);

        return (int) ((Math.abs(t.getTimeInMillis() - f.getTimeInMillis())) / 1000 / 60 / 60 / 24);
    }

    /**
     * 判断两个日期相差的秒数
     *
     * @param arg
     */
    public static int getTime(Date fDate, Date tDate) {
        try {
            fDate = format_h.parse(format_h.format(fDate));
            tDate = format_h.parse(format_h.format(tDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar f = Calendar.getInstance();
        Calendar t = Calendar.getInstance();
        f.setTime(fDate);
        t.setTime(tDate);
        return (int) ((Math.abs(t.getTimeInMillis() - f.getTimeInMillis())) / 1000);
    }

    /**
     * 获取前天的日期
     *
     * @return
     */
    public static String getYesDate() {
        Date as = new Date(new Date().getTime() - (24 * 60 * 60 * 1000) * 2);
        String time = format_d.format(as);
        return time;
    }

    /**
     * 获取昨天的日期
     *
     * @return
     */
    public static String getTureYesDate() {
        Date as = new Date(new Date().getTime() - 24 * 60 * 60 * 1000);
        String time = format_d.format(as);
        return time;
    }

    /**
     * 获取七天前的日期
     *
     * @return
     */
    public static String getSevenYesDate() {
        Date as = new Date(new Date().getTime() - 24 * 60 * 60 * 1000 * 7);
        String time = format_d.format(as);
        return time;
    }

    /**
     * 获取当天的日期
     */
    public static String getTodayDate() {
        String time = format_d.format(new Date());
        return time;
    }

    /**
     * 返回 N天后的实现
     *
     * @param date
     * @param expireddays
     * @return
     * @author hsn <br/>
     * <p>
     * createTime 2015-12-12下午6:08:57
     */
    public static Date getSameDays(Date date, int expireddays) {
        Date nDate = new Date(date.getTime() + ONEDAY * expireddays);
        return nDate;
    }

    public static int dayBetween(Date beginDate, Date endDate) throws Exception {
        beginDate = format_h.parse(format_h.format(beginDate));
        endDate = format_h.parse(format_h.format(endDate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(beginDate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(endDate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time1 - time2) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    public static int getAgeByBirth(String birthday) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date bithday = null;
        int age = 0;
        try {
            bithday = format.parse(birthday);
            Calendar now = Calendar.getInstance();
            now.setTime(new Date());// 当前时间

            Calendar birth = Calendar.getInstance();
            birth.setTime(bithday);

            if (birth.after(now)) {//如果传入的时间，在当前时间的后面，返回0岁
                age = 0;
            } else {
                age = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
                if (now.get(Calendar.DAY_OF_YEAR) > birth.get(Calendar.DAY_OF_YEAR)) {
                    age += 1;
                }
            }
            return age;
        } catch (Exception e) {//兼容性更强,异常后返回数据
            return 0;
        }
    }
}