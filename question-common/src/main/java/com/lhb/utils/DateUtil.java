package com.lhb.utils;

import cn.hutool.core.util.ArrayUtil;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;

/**
 * 日期工具类
 * @author LHB
 * @date 2021-9-5 16:06:10
 */
public class DateUtil {

    public static final String YYYY = "yyyy";
    public static final String YYYY_MM = "yyyy-MM";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String HH_MM_SS = "HH:mm:ss";
    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final String YYYYMMDD = "yyyyMMdd";

    /**
     * 周的第一天
     *
     * @return LocalDate
     */
    public static LocalDate weekFirstDay(LocalDate date) {
        return date.with(DayOfWeek.MONDAY);
    }

    /**
     * 周的最后一天
     *
     * @return LocalDate
     */
    public static LocalDate weekLastDay(LocalDate date) {
        return date.with(DayOfWeek.SUNDAY);
    }

    /**
     * 获取当前日期一年中的第几周
     * @param date
     * @return int
     */
    public static int getDateWeek(LocalDate date){
        WeekFields weekFields = WeekFields.of(DayOfWeek.MONDAY,1);
        int week = date.get(weekFields.weekOfYear());
        return week;
    }

    /**
     * 某天 开始时分秒 比如 2020-12-01 00:00:00
     *
     * @return LocalDate
     */
    public static LocalDateTime dayOfStartTime(LocalDate localDate) {
        return LocalDateTime.of(localDate, LocalTime.of(0,0,0));
    }

    /**
     * 某天 结束时分秒 比如 2020-12-01 23:59:59
     *
     * @return LocalDate
     */
    public static LocalDateTime dayOfEndTime(LocalDate localDate) {
        return LocalDateTime.of(localDate, LocalTime.of(23, 59, 59));
    }

    /**
     * 月的第一天
     * @param date
     * @return LocalDate
     */
    public static LocalDate firstDayOfMonth(LocalDate date){
        return date.with(TemporalAdjusters.firstDayOfMonth());
    }

    /**
     * 月的最后一天
     * @param date
     * @return LocalDate
     */
    public static LocalDate lastDayOfMonth(LocalDate date){
        return date.with(TemporalAdjusters.lastDayOfMonth());
    }


    /**
     * 当前年的第一天
     * @param date
     * @return LocalDate
     */
    public static LocalDate firstDayOfYear(LocalDate date){
        return date.with(TemporalAdjusters.firstDayOfYear());
    }

    /**
     * 当前年的最后一天
     * @param date
     * @return LocalDate
     */
    public static LocalDate lastDayOfYear(LocalDate date){
        return date.with(TemporalAdjusters.lastDayOfYear());
    }


    /**
     * 字符串转LocalDate
     * @param date 格式化 yyyy-MM-dd
     * @return LocalDate
     */
    public static LocalDate stringToLocalDate(String date){
        return stringToLocalDate(date,YYYY_MM_DD);
    }

    /**
     * 字符串转LocalDate
     * @param date 格式化
     * @return LocalDate
     */
    public static LocalDate stringToLocalDate(String date, String dateFormat){
        DateTimeFormatter df = DateTimeFormatter.ofPattern(dateFormat);
        return LocalDate.parse(date,df);
    }

    /**
     * 字符串转LocalDateTime
     * @param dateTime
     * @return LocalDateTime
     */
    public static LocalDateTime stringToLocalDateTime(String dateTime){
        return stringToLocalDateTime(dateTime,YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 字符串转LocalDateTime
     * @param dateTime
     * @return LocalDateTime
     */
    public static LocalDateTime stringToLocalDateTime(String dateTime, String dateFormat){
        DateTimeFormatter df = DateTimeFormatter.ofPattern(dateFormat);
        return LocalDateTime.parse(dateTime,df);
    }

    /**
     * LocalDate转字符串
     * @param date LocalDate
     * @return String 返回格式 yyyy-MM-dd
     */
    public static String localDateToString(LocalDate date){
        return localDateToString(date,YYYY_MM_DD);
    }

    /**
     * LocalDate转字符串
     * @param date LocalDate
     * @param dateFormat
     * @return String
     */
    public static String localDateToString(LocalDate date, String dateFormat){
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(dateFormat);
        return date.format(fmt);
    }

    /**
     * 字符串转日期 LocalDateTime
     * @param date LocalDateTime
     * @return String 返回格式 yyyy-MM-dd HH:mm:ss
     */
    public static String localDateTimeToString(LocalDateTime date){
        return localDateTimeToString(date,YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 字符串转日期 LocalDateTime
     * @param date 字符串
     * @param dateFormat 格式化
     * @return String
     */
    public static String localDateTimeToString(LocalDateTime date, String dateFormat){
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(dateFormat);
        return date.format(fmt);
    }

    /**
     * 字符串转年月
     * @param date 字符串日期
     * @return YearMonth 返回格式 yyyy-MM
     */
    public static YearMonth stringToYearMonth(String date){
        return stringToYearMonth(date,YYYY_MM);
    }

    /**
     * 字符串转年月
     * @param date 字符串日期
     * @param dateFormat 格式化
     * @return YearMonth
     */
    public static YearMonth stringToYearMonth(String date, String dateFormat){
        DateTimeFormatter df = DateTimeFormatter.ofPattern(dateFormat);
        return YearMonth.parse(date, df);
    }

    /**
     * 年月转LocalDate
     * @param date 字符串日期
     * @param dateFormat 格式化
     * @param dayOfMonth 1 to 31
     * @return LocalDate
     */
    public static LocalDate yearMonthToLocalDate(String date, String dateFormat, int dayOfMonth){
        DateTimeFormatter df = DateTimeFormatter.ofPattern(dateFormat);
        return YearMonth.parse(date, df).atDay(dayOfMonth);
    }

    /**
     * 字符串转年
     * @param date 例如：'2019'
     * @return Year
     */
    public static Year stringToYear(String date){
        return stringToYear(date,YYYY);
    }

    /**
     * 字符串转年
     * @param date 例如：'2019'
     * @return Year
     */
    public static Year stringToYear(String date, String dateFormat){
        DateTimeFormatter df = DateTimeFormatter.ofPattern(dateFormat);
        return Year.parse(date,df);
    }

    /**
     * 年转 LocalDate
     * @param date 年份
     * @param dateFormat 格式化 yyyy
     * @param month 月份
     * @param dayOfMonth 1 to 31
     * @return LocalDate
     */
    public static LocalDate yearToLocalDate(String date, String dateFormat, int month, int dayOfMonth){
        DateTimeFormatter df = DateTimeFormatter.ofPattern(dateFormat);
        return Year.parse(date,df).atMonth(month).atDay(dayOfMonth);
    }

    /**
     * 统计月的天数
     * @param date 日期
     * @param month 传入的月份
     * @return 指定月份的天数
     */
    public static int countDaysInMonth(LocalDate date, int month) {
        return date.withMonth(month).lengthOfMonth();
    }

    /**
     * 指定月份的总天数
     * @param date 日期
     * @return 指定月份的天数
     */
    public static int countDaysInMonth(LocalDate date) {
        return countDaysInMonth(date,date.getMonthValue());
    }

    /**
     * 计算两个日期间的天数
     * @param start 开始日期
     * @param end 结束日期
     * @return 开始日期->结束日期 总天数
     */
    public static long calcDateDays(LocalDate start, LocalDate end){
        return ChronoUnit.DAYS.between(start,end);
    }

    /**
     * 获取毫秒数
     * @return String
     */
    public static String getNowMill(){
        LocalDateTime loc = LocalDateTime.now();
        Instant instant = loc.atZone(ZoneId.systemDefault()).toInstant();
        return String.valueOf(instant.toEpochMilli());
    }

}