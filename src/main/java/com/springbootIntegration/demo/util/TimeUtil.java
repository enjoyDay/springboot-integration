package com.springbootIntegration.demo.util;

import java.time.*;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class TimeUtil {
    private static final ZoneId ZONE = ZoneId.systemDefault();

    public TimeUtil() {
    }

    public static Instant getInstant(Object d) {
        if (d instanceof Date) {
            return ((Date)d).toInstant();
        } else {
            return d instanceof Long ? (new Date((Long)d)).toInstant() : null;
        }
    }

    public static LocalDateTime date2LocalDateTime(Object d) {
        Instant instant = getInstant(d);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZONE);
        return localDateTime;
    }

    public static LocalDate date2LocalDate(Object d) {
        Instant instant = getInstant(d);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZONE);
        return localDateTime.toLocalDate();
    }

    public static LocalTime date2LocalTime(Object d) {
        Instant instant = getInstant(d);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZONE);
        return localDateTime.toLocalTime();
    }

    public static Date localDate2Date(LocalDate localDate) {
        Instant instant = localDate.atStartOfDay().atZone(ZONE).toInstant();
        return Date.from(instant);
    }

    public static Date localDateTime2Date(LocalDateTime localDateTime) {
        Instant instant = localDateTime.atZone(ZONE).toInstant();
        return Date.from(instant);
    }

    public static Period localDateDiff(LocalDate lt, LocalDate gt) {
        Period p = Period.between(lt, gt);
        return p;
    }

    public static Duration localTimeDiff(LocalTime lt, LocalTime gt) {
        Duration d = Duration.between(lt, gt);
        return d;
    }

    public static long millisDiff(LocalTime lt, LocalTime gt) {
        Duration d = Duration.between(lt, gt);
        return d.toMillis();
    }

    public static long secondDiff(LocalTime lt, LocalTime gt) {
        Duration d = Duration.between(lt, gt);
        return d.getSeconds();
    }

    public static long daysDiff(LocalDate lt, LocalDate gt) {
        long daysDiff = ChronoUnit.DAYS.between(lt, gt);
        return daysDiff;
    }

    public static LocalDate getFirstDayOfLastMonth(LocalDate date) {
        return date.with((temporal) -> {
            return temporal.with(ChronoField.DAY_OF_MONTH, 1L).plus(-1L, ChronoUnit.MONTHS);
        });
    }

    public static LocalDate getLastDayOfLastMonth(LocalDate date) {
        return date.with((temporal) -> {
            return temporal.plus(-1L, ChronoUnit.MONTHS).with(ChronoField.DAY_OF_MONTH, temporal.range(ChronoField.DAY_OF_MONTH).getMaximum());
        });
    }

    public static LocalDate getFirstDayOfMonth(LocalDate date) {
        return date.with((temporal) -> {
            return temporal.with(ChronoField.DAY_OF_MONTH, 1L);
        });
    }

    public static LocalDate getLastDayOfMonth(LocalDate date) {
        return date.with((temporal) -> {
            return temporal.with(ChronoField.DAY_OF_MONTH, temporal.range(ChronoField.DAY_OF_MONTH).getMaximum());
        });
    }

    public static LocalDate getFirstDayOfNextMonth(LocalDate date) {
        return date.with((temporal) -> {
            return temporal.plus(1L, ChronoUnit.MONTHS).with(ChronoField.DAY_OF_MONTH, 1L);
        });
    }

    public static LocalDate getLastDayOfNextMonth(LocalDate date) {
        return date.with((temporal) -> {
            return temporal.plus(1L, ChronoUnit.MONTHS).with(ChronoField.DAY_OF_MONTH, temporal.range(ChronoField.DAY_OF_MONTH).getMaximum());
        });
    }

    public static LocalDate getFirstDayOfLastYear(LocalDate date) {
        return date.with((temporal) -> {
            return temporal.with(ChronoField.DAY_OF_YEAR, 1L).plus(-1L, ChronoUnit.YEARS);
        });
    }

    public static LocalDate getLastDayOfLastYear(LocalDate date) {
        return date.with((temporal) -> {
            return temporal.with(ChronoField.DAY_OF_YEAR, temporal.range(ChronoField.DAY_OF_YEAR).getMaximum()).plus(-1L, ChronoUnit.YEARS);
        });
    }

    public static LocalDate getFirstDayOfYear(LocalDate date) {
        return date.with((temporal) -> {
            return temporal.with(ChronoField.DAY_OF_YEAR, 1L);
        });
    }

    public static LocalDate getLastDayOfYear(LocalDate date) {
        return date.with((temporal) -> {
            return temporal.with(ChronoField.DAY_OF_YEAR, temporal.range(ChronoField.DAY_OF_YEAR).getMaximum());
        });
    }

    public static LocalDate getFirstDayOfNextYear(LocalDate date) {
        return date.with((temporal) -> {
            return temporal.with(ChronoField.DAY_OF_YEAR, 1L).plus(1L, ChronoUnit.YEARS);
        });
    }

    public static LocalDate getLastDayOfNextYear(LocalDate date) {
        return date.with((temporal) -> {
            return temporal.with(ChronoField.DAY_OF_YEAR, temporal.range(ChronoField.DAY_OF_YEAR).getMaximum()).plus(1L, ChronoUnit.YEARS);
        });
    }

    public static LocalDateTime getCurrentLocalDateTime() {
        return LocalDateTime.now(ZONE);
    }

    public static boolean isInRange(Date startTime, Date endTime) throws Exception {
        LocalDateTime now = getCurrentLocalDateTime();
        LocalDateTime start = date2LocalDateTime(startTime);
        LocalDateTime end = date2LocalDateTime(endTime);
        return start.isBefore(now) && end.isAfter(now) || start.isEqual(now) || end.isEqual(now);
    }
}
