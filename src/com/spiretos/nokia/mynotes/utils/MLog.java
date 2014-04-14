package com.spiretos.nokia.mynotes.utils;

import android.util.Log;

public class MLog
{
	static boolean showMessages=false;
	
	public static void i(String tag, String message)
	{
		if (showMessages)
			Log.i(tag,message);
	}
	
	public static void e(String tag, String message)
	{
		if (showMessages)
			Log.e(tag,message);
	}
	
	public static void v(String tag, String message)
	{
		if (showMessages)
			Log.v(tag,message);
	}
}
