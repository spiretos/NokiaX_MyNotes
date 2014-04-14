package com.spiretos.nokia.mynotes.storage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.spiretos.nokia.mynotes.utils.DateUtils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;


@SuppressLint("SimpleDateFormat") public class AppPreferencesManager
{
	
	private static SharedPreferences mPrefs;
	
	public static String LAST_DOWNLOAD_ALL="LAST_DOWNLOAD_ALL";
	public static String MINUTES_AFTER_DATA_IS_OLD="MINUTES_AFTER_DATA_IS_OLD";
	public static String DOWNLOAD_INTERVAL="DOWNLOAD_INTERVAL";
	public static String DELETEOLD_PERIOD="DELETEOLD_PERIOD";
	
	
	public AppPreferencesManager(Context context)
	{
		mPrefs=PreferenceManager.getDefaultSharedPreferences(context);
	}
	
	
	
	public void setString(String tokenName, String tokenValue)
	{
		Editor editor=mPrefs.edit();
		editor.putString(tokenName, tokenValue);
		editor.commit();
	}
	public String getString(String tokenName, String defaultValue)
	{
		return mPrefs.getString(tokenName, defaultValue);
	}
	
	
	public void setInt(String tokenName, int tokenValue)
	{
		Editor editor=mPrefs.edit();
		editor.putInt(tokenName, tokenValue);
		editor.commit();
	}
	public int getInt(String tokenName, int defaultValue)
	{
		return mPrefs.getInt(tokenName, defaultValue);
	}
	
	
	
	
	
	public void setLastDownloadAll()
	{
		try
		{
			SimpleDateFormat sdformat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateStr=sdformat.format(new Date());
			setString(LAST_DOWNLOAD_ALL,dateStr);
		}
		catch (Exception ex)
		{
			
		}
	}
	public Date getLastDownloadAll() throws ParseException
	{
		try
		{
			String dateStr=getString(LAST_DOWNLOAD_ALL,"2000-01-01 00:00:00");
			SimpleDateFormat sdformat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sdformat.parse(dateStr);
		}
		catch (Exception ex)
		{
			return new Date();
		}
	}
	
	
	
	public void setMinutesAfterDataIsOld(int value)
	{
		try
		{
			setInt(MINUTES_AFTER_DATA_IS_OLD,value);
		}
		catch (Exception ex)
		{
			
		}
	}
	public int getMinutesAfterDataIsOld()
	{
		try
		{
			return getInt(MINUTES_AFTER_DATA_IS_OLD,60);
		}
		catch (Exception ex)
		{
			return 60;
		}
	}
	
	
	public boolean timeToRefreshHasPassed()
	{
		GregorianCalendar now=DateUtils.gregFromDate(new Date());
		
		Date lastDate=new Date();
		try
		{
			lastDate=getLastDownloadAll();
		}
		catch (Exception ex)
		{
			return true;
		}
		GregorianCalendar lastTime=DateUtils.gregFromDate(lastDate);
		
		lastTime.add(Calendar.MINUTE,getMinutesAfterDataIsOld());

		if (lastTime.before(now))
			return true;
		else
			return false;	
	}
	
	
	
	
	
	public void setInterval(int millis)
	{
		try
		{
			setInt(DOWNLOAD_INTERVAL,millis);
			if (millis!=-1)
				setMinutesAfterDataIsOld((millis/60)/1000);
			else
				setMinutesAfterDataIsOld(30*24*60);
		}
		catch (Exception ex)
		{
			
		}
	}
	public int getInterval()
	{
		try
		{
			return getInt(DOWNLOAD_INTERVAL,2*60*60*1000);
		}
		catch (Exception ex)
		{
			return 2*60*60*1000;
		}
	}
	
	
	public void setDeleteOld(int days)
	{
		try
		{
			setInt(DELETEOLD_PERIOD,days);
		}
		catch (Exception ex)
		{
			
		}
	}
	public int getDeleteOld()
	{
		try
		{
			return getInt(DELETEOLD_PERIOD,30);
		}
		catch (Exception ex)
		{
			return 30;
		}
	}
}
