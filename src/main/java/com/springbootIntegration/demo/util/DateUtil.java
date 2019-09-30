package com.springbootIntegration.demo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * @author liukun
 * @description 日期工具类，依赖TimeUtil
 * @date 2019/9/14
 */
public final class DateUtil {
    private static final Map<String, DateTimeFormatter> FORMATS = InstanceUtil.newConcurrentHashMap();
    private static final int[] DAYS = new int[]{20, 19, 21, 20, 21, 22, 23, 23, 23, 24, 23, 22};
    private static final String[] STARSIGNS = new String[]{"摩羯座", "水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座"};

    private DateUtil() {
    }

    public static final DateTimeFormatter getFormat(String pattern) {
        if (!FORMATS.containsKey(pattern)) {
            FORMATS.put(pattern, DateTimeFormatter.ofPattern(pattern));
        }

        return (DateTimeFormatter)FORMATS.get(pattern);
    }

    public static final String format(Object date) {
        return format(date, "yyyy-MM-dd");
    }

    public static final String format(Object date, String pattern) {
        if (date == null) {
            return null;
        } else {
            return pattern == null ? format(date) : getFormat(pattern).format(TimeUtil.date2LocalDateTime(date));
        }
    }

    public static final String getDate() {
        return format(new Date());
    }

    public static final String getDateTime() {
        return format(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    public static final String getDateTime(String pattern) {
        return format(new Date(), pattern);
    }

    public static final Date addDate(Date date, int field, int amount) {
        if (date == null) {
            return null;
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(field, amount);
            return calendar.getTime();
        }
    }

    public static final Date stringToDate(String date) {
        if (date == null) {
            return null;
        } else {
            String separator = String.valueOf(date.charAt(4));
            String pattern = "yyyyMMdd";
            if (!separator.matches("\\d*")) {
                pattern = "yyyy" + separator + "MM" + separator + "dd";
                if (date.length() < 10) {
                    pattern = "yyyy" + separator + "M" + separator + "d";
                }

                pattern = pattern + " HH:mm:ss.SSS";
            } else if (date.length() < 8) {
                pattern = "yyyyMd";
            } else {
                pattern = pattern + "HHmmss.SSS";
            }

            pattern = pattern.substring(0, Math.min(pattern.length(), date.length()));

            try {
                return (new SimpleDateFormat(pattern)).parse(date);
            } catch (ParseException var4) {
                return null;
            }
        }
    }

    public static final Integer getBetween(Date startDate, Date endDate) {
        Calendar start = Calendar.getInstance();
        start.setTime(startDate);
        Calendar end = Calendar.getInstance();
        end.setTime(endDate);
        long n = end.getTimeInMillis() - start.getTimeInMillis();
        return (int)(n / 1000L);
    }

    public static final Integer getDayBetween(Date startDate, Date endDate) {
        Calendar start = Calendar.getInstance();
        start.setTime(startDate);
        start.set(11, 0);
        start.set(12, 0);
        start.set(13, 0);
        start.set(14, 0);
        Calendar end = Calendar.getInstance();
        end.setTime(endDate);
        end.set(11, 0);
        end.set(12, 0);
        end.set(13, 0);
        end.set(14, 0);
        long n = end.getTimeInMillis() - start.getTimeInMillis();
        return (int)(n / 86400000L);
    }

    public static final Integer getMonthBetween(Date startDate, Date endDate) {
        if (startDate != null && endDate != null && startDate.before(endDate)) {
            Calendar start = Calendar.getInstance();
            start.setTime(startDate);
            Calendar end = Calendar.getInstance();
            end.setTime(endDate);
            int year1 = start.get(1);
            int year2 = end.get(1);
            int month1 = start.get(2);
            int month2 = end.get(2);
            int n = (year2 - year1) * 12;
            n = n + month2 - month1;
            return n;
        } else {
            return null;
        }
    }

    public static final Integer getMonthBetweenWithDay(Date startDate, Date endDate) {
        if (startDate != null && endDate != null && startDate.before(endDate)) {
            Calendar start = Calendar.getInstance();
            start.setTime(startDate);
            Calendar end = Calendar.getInstance();
            end.setTime(endDate);
            int year1 = start.get(1);
            int year2 = end.get(1);
            int month1 = start.get(2);
            int month2 = end.get(2);
            int n = (year2 - year1) * 12;
            n = n + month2 - month1;
            int day1 = start.get(5);
            int day2 = end.get(5);
            if (day1 <= day2) {
                ++n;
            }

            return n;
        } else {
            return null;
        }
    }

    public static String getFriendly(long millis) {
        long now = System.currentTimeMillis();
        long span = now - millis;
        if (span < 0L) {
            return String.format("%tF %tT", millis, millis);
        } else if (span < 1000L) {
            return "刚刚";
        } else if (span < 60000L) {
            return String.format("%d秒前", span / 1000L);
        } else if (span < 3600000L) {
            return String.format("%d分钟前", span / 60000L);
        } else {
            long wee = now / 86400000L * 86400000L;
            if (millis >= wee) {
                return String.format("今天%tR", millis);
            } else if (millis >= wee - 86400000L) {
                return String.format("昨天%tR", millis);
            } else {
                wee = now / 31536000000L * 31536000000L;
                return millis >= wee ? String.format("%tm-%te", millis, millis) : String.format("%tF", millis);
            }
        }
    }

    public static Calendar getCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static String getStarSign(Date date) {
        Calendar calendar = getCalendar(date);
        int month = calendar.get(2);
        int day = calendar.get(5);
        return day < DAYS[month] ? STARSIGNS[month] : STARSIGNS[month + 1];
    }

    public interface DatePattern {
        String HHMMSS = "HHmmss";
        String HH_MM_SS = "HH:mm:ss";
        String YYYYMMDD = "yyyyMMdd";
        String YYYY_MM_DD = "yyyy-MM-dd";
        String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
        String YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";
        String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
        String YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";
    }
}

