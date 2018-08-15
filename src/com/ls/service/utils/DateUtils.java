package com.ls.service.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Title: DateUtils.java
 * @Package com.ls.test
 * @Description:
 * @author linsheng
 * @email hi.linsheng@foxmail.com
 * @date 2015年11月11日 上午9:13:18
 * @version V1.0
 */
public class DateUtils {

	public static String FORMAT_YMDHMS = "yyyy-MM-dd HH:mm:ss";

	public static String FORMAT_YMDHMSS = "yyyy-MM-dd HH:mm:ss.S";

	public static String FORMAT_YMD = "yyyy-MM-dd";

	public static String FORMAT_MD = "MM-dd";

	public static String FORMAT_HMS = "HH:mm:ss";

	public static String FORMAT_HM = "HH:mm";

	/**
	 * @Title:getCalendar
	 * @Description:获取calendar对象
	 * @param @param date
	 * @param @return
	 * @return Calendar
	 */
	public static Calendar getCalendar(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}
	
	public static String getNow(){
		return format(new Date(), FORMAT_YMDHMSS);
	}
	

	/**
	 * @Title:format
	 * @Description:日期格式化为String
	 * @param date
	 * @param pattern
	 * @return String
	 */
	public static String format(Date date, String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		return df.format(date);
	}

	/**
	 * @Title:pase
	 * @Description:字符传转化日期格式
	 * @param @param date
	 * @param @param pattern
	 * @param @throws ParseException
	 * @return Date
	 */
	public static Date parse(String date, String pattern) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		return df.parse(date);
	}

	/**
	 * @Title:getDateByDifferDay
	 * @Description:获得相差天数的日期
	 * @param @param date 目标日期
	 * @param @param differDay 相差的天数
	 * @param @return
	 * @return Date
	 */
	public static Date getDateByDifferDay(Date date, int differDay) {
		Calendar cal = getCalendar(date);
		cal.add(Calendar.DAY_OF_YEAR, differDay);
		return cal.getTime();
	}

	/**
	 * getDateByDifferMonth
	 * 
	 * @Description:获得相差月份的日期
	 * @param @param date 目标日期
	 * @param @param differMonth 相差的月份
	 * @param @return
	 * @return Date
	 */
	public static Date getDateByDifferMonth(Date date, int differMonth) {
		Calendar cal = getCalendar(date);
		cal.add(Calendar.MONTH, differMonth);
		return cal.getTime();
	}

	/**
	 * getDateByDifferMonth
	 * 
	 * @Description:获得相差年份的日期
	 * @param @param date 目标日期
	 * @param @param differYear 相差的年份
	 * @param @return
	 * @return Date
	 */
	public static Date getDateByDifferYear(Date date, int differYear) {
		Calendar cal = getCalendar(date);
		cal.add(Calendar.YEAR, differYear);
		return cal.getTime();
	}

	/**
	 * @Title:getYear
	 * @Description:
	 * @param @param date
	 * @return int
	 */
	public static int getYear(Date date) {
		Calendar cal = getCalendar(date);
		return cal.get(Calendar.YEAR);
	}

	/**
	 * @Title:getMonth
	 * @Description:
	 * @param @param date
	 * @return int
	 */
	public static int getMonth(Date date) {
		Calendar calendar = getCalendar(date);
		return calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * @Title:getDay
	 * @Description:
	 * @param @param date
	 * @return int
	 */
	public static int getDay(Date date) {
		Calendar calendar = getCalendar(date);
		return calendar.get(Calendar.DAY_OF_MONTH) + 1;
	}

	/**
	 * @Title:getHour
	 * @Description:
	 * @param @param date
	 * @return int
	 */
	public static int getHour(Date date) {
		Calendar calendar = getCalendar(date);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * @Title:getMinute
	 * @Description:
	 * @param @param date
	 * @return int
	 */
	public static int getMinute(Date date) {
		Calendar calendar = getCalendar(date);
		return calendar.get(Calendar.MINUTE);
	}

	/**
	 * @Title:getSecond
	 * @Description:
	 * @param @param date
	 * @return int
	 */
	public static int getSecond(Date date) {
		Calendar calendar = getCalendar(date);
		return calendar.get(Calendar.SECOND);
	}

	/**
	 * @Title:isDateBefore
	 * @Description:目标日期是否在源日期之前
	 * @param @param sourceDate 源日期
	 * @param @param targetDate 目标日期
	 * @return boolean
	 */
	public static boolean isDateBefore(Date sourceDate, Date targetDate) {
		return sourceDate.getTime() > targetDate.getTime() ? true : false;
	}

	/**
	 * @Title:countMinuteBetweenDates
	 * @Description:获取两个日期的分钟差
	 * @param @param date1
	 * @param @param date2
	 * @param @return
	 * @return long
	 */
	public static long countMinuteBetweenDates(Date date1, Date date2) {
		long time1 = date1.getTime();
		long time2 = date2.getTime();
		long between_days = (time2 - time1) / (1000 * 60);
		return (between_days < 0) ? -between_days : between_days;
	}

	/**
	 * @Title:countHourBetweenDates
	 * @Description:获取两个日期的小时差
	 * @param @param date1
	 * @param @param date2
	 * @param @return
	 * @return long
	 */
	public static long countHourBetweenDates(Date date1, Date date2) {
		long time1 = date1.getTime();
		long time2 = date2.getTime();
		long between_days = (time2 - time1) / (1000 * 60 * 60);
		return (between_days < 0) ? -between_days : between_days;
	}

	/**
	 * @Title:countDaysBetweenDates
	 * @Description:获取两个日期间的天数
	 * @param @param date1
	 * @param @param date2
	 * @param @return
	 * @return long
	 */
	public static long countDaysBetweenDates(Date date1, Date date2) {
		long time1 = date1.getTime();
		long time2 = date2.getTime();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);
		return (between_days < 0) ? -between_days : between_days;
	}

	/**
	 * @Title:countMonthsBetweenDates
	 * @Description:获取两个日期间的月份
	 * @param @param date1
	 * @param @param date2
	 * @param @return
	 * @return long
	 */
	public static long countMonthsBetweenDates(Date date1, Date date2) {
		Calendar calendar1 = getCalendar(date1);
		Calendar calendar2 = getCalendar(date2);
		int diffYear = calendar1.get(Calendar.YEAR) - calendar2.get(Calendar.YEAR);
		int diffMonth = calendar1.get(Calendar.MONTH) - calendar2.get(Calendar.MONTH);
		int month = diffYear * 12 + diffMonth;
		return month > 0 ? month : -month;
	}

	/**
	 * @Title:countMonthsBetweenDates
	 * @Description:获取两个日期间的年份
	 * @param @param date1
	 * @param @param date2
	 * @param @return
	 * @return long
	 */
	public static long countYearsBetweenDates(Date date1, Date date2) {
		Calendar calendar1 = getCalendar(date1);
		Calendar calendar2 = getCalendar(date2);
		int diffYear = calendar1.get(Calendar.YEAR) - calendar2.get(Calendar.YEAR);
		return diffYear > 0 ? diffYear : -diffYear;
	}

	public static void main(String[] args) {
		Date date1 = getDateByDifferMonth(new Date(), -11);
		System.out.println(countMonthsBetweenDates(date1, new Date()));
	}
}
