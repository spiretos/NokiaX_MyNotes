package com.spiretos.nokia.mynotes.widgets;

import com.spiretos.nokia.mynotes.EditNoteActivity;
import com.spiretos.nokia.mynotes.R;
import com.spiretos.nokia.mynotes.storage.DBCustomer;
import com.spiretos.nokia.mynotes.utils.MLog;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;


public class MyWidgetProvider extends AppWidgetProvider
{
	private static final String	REFRESH_ACTION	= "com.example.widget.REFRESH";

	
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
	{
		for (int i = 0; i < appWidgetIds.length; i++)
		{
			int widgetId = appWidgetIds[i];

			RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.my_widget_layout);
			
			int totalNotes = 10;
			int importantNotes = 3;
			
			remoteViews.setTextViewText(R.id.widget_badge, String.valueOf(totalNotes));
			remoteViews.setTextViewText(R.id.widget_sumtext, totalNotes+" notes\n("+importantNotes+" important)");
			
			final Intent refreshIntent = new Intent(context, MyWidgetProvider.class);
			refreshIntent.setAction(MyWidgetProvider.REFRESH_ACTION);
			refreshIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
			final PendingIntent refreshPendingIntent = PendingIntent.getBroadcast(context, 0, refreshIntent, PendingIntent.FLAG_UPDATE_CURRENT);

			remoteViews.setOnClickPendingIntent(R.id.viewFlipper1, refreshPendingIntent);
			
			appWidgetManager.updateAppWidget(widgetId, remoteViews);
		}
	}


	@Override
	public void onReceive(Context context, Intent intent)
	{
		String action = intent.getAction();
		if (action.equals(REFRESH_ACTION))
		{
			RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.my_widget_layout);
			remoteViews.showNext(R.id.viewFlipper1);
			
			int id = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, -1);
			AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
			appWidgetManager.updateAppWidget(id, remoteViews);
		}
		super.onReceive(context, intent);
	}
	
	
	public static void updateWidgetData(Context context)
	{
		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
	    int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, MyWidgetProvider.class));

	    new MyWidgetProvider().onUpdate(context, appWidgetManager, appWidgetIds);
	}

}
