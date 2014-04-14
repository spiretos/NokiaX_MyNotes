package com.spiretos.nokia.mynotes.utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@SuppressLint("SimpleDateFormat") public class DateUtils
{
	
	@SuppressWarnings("deprecation")
	public static GregorianCalendar gregFromDate(Date date)
	{
		GregorianCalendar greg=new GregorianCalendar();
		greg.set(date.getYear()+1900,date.getMonth(),date.getDate(),date.getHours(),date.getMinutes(),date.getSeconds());
		return greg;
	}
	
	
	public static String getDateForQuery(String column)
	{
		return getDateForQuery(column,0,">=");
	}
	public static String getDateForQuery(String column, int offset, String operator)
	{
		//SimpleDateFormat outSDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date now=new Date();
		GregorianCalendar greg=DateUtils.gregFromDate(now);
		greg.add(Calendar.DATE, offset);
		String nowStr=sdf.format(greg.getTime());
		
		//String res=column+">='"+nowStr+" 00:00:00' and "+column+"<='"+nowStr+" 23:59:59'";
		String res=column+operator+"'"+nowStr+" 00:00:00'";
		//Log.i("getDateForQuery","* * * "+nowStr);
		return res;
	}
	
	public static String getHourForQuery(String column)
	{
		return getHourForQuery(column,0);
	}
	public static String getHourForQuery(String column, int offset)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now=new Date();
		GregorianCalendar greg=DateUtils.gregFromDate(now);
		greg.add(Calendar.HOUR, offset);
		String nowStr=sdf.format(greg.getTime());
		
		String res=column+">='"+nowStr+" 00:00:00'";
		//Log.i("getHourForQuery","* * * "+nowStr);
		return res;
	}
	
	
	public static String getDateTimeNow()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now=new Date();
		GregorianCalendar greg=DateUtils.gregFromDate(now);
		String nowStr=sdf.format(greg.getTime());
		
		return nowStr;
	}
}