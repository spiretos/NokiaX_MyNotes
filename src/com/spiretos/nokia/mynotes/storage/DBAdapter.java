package com.spiretos.nokia.mynotes.storage;

import com.spiretos.nokia.mynotes.utils.MLog;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAdapter
{
	public static DBAdapter Instance;
	
	public class DBHelper extends SQLiteOpenHelper
	{

	    private static final int DB_VERSION = 15;
	    private static final String DB_NAME = "rssdb";

	    DBHelper(Context context)
	    {
	        super(context, DB_NAME, null, DB_VERSION);
	    }

	    @Override
	    public void onCreate(SQLiteDatabase db)
	    {
	    	createNotesTable(db);
    	}
	    
	    private void createNotesTable(SQLiteDatabase db)
	    {
	    	String createNotesTableSql="CREATE TABLE notes ("
    			+"id INTEGER, "
    			+"title TEXT, "
    			+"description TEXT, "
    			+"date DATETIME, "
    			+"important INTEGER, "
    			+"PRIMARY KEY ('id'))";
    			
    			db.execSQL(createNotesTableSql);
	    }
	    
	    @Override
	    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	    {
	    	db.execSQL("DROP TABLE IF EXISTS notes");
	    	onCreate(db);
       }
	}
	
	//private Context _context;
	private DBHelper dbHelper;
	private SQLiteDatabase db;
	
	public DBAdapter(Context context)
	{
		//_context=context;
		dbHelper=new DBHelper(context);
	}
	
	private static boolean isOpened=false;
	private void openDB()
	{
		if (isOpened) return;
		try
		{
			db=dbHelper.getWritableDatabase();
			isOpened=true;
		}
		catch (Exception ex)
		{
			//db=dbHelper.getReadableDatabase();
			MLog.e("DBerror1","ex.getMessage()");
			ex.printStackTrace();
		}
	}
	/*private void closeDB()
	{
		try
		{
			db.close();
		}
		catch (Exception ex)
		{
			Log.e("DBerror2",ex.getMessage());
		}
	}*/
	
	public void executeQuery(String query)
	{
		openDB();
		db.execSQL(query);
	}
	public Cursor executeQueryForResult(String query)
	{
		openDB();
		return db.rawQuery(query, null);
	}
	
	
	public boolean tableIsEmpty(String tableName)
	{
		Cursor cursor=null;
		try
		{
			cursor = new DBCustomer("select * from "+tableName).executeQueryForResult();
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return true;
		}
		
		if (cursor==null || cursor.getCount()==0)
			return true;
		else
			return false;
	}
	
	public String getMaxColumnValue(String table, String column)
	{
		Cursor cursor=null;
		try
		{
			cursor = new DBCustomer("select max("+column+") from "+table).executeQueryForResult();
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		if (cursor==null || cursor.getCount()==0)
			return null;
		else
		{
			cursor.moveToFirst();
			return cursor.getString(0);
		}
	}
	

}