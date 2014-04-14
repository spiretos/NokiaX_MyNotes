package com.spiretos.nokia.mynotes.notes;

import java.io.Serializable;

import com.spiretos.nokia.mynotes.storage.DBCustomer;
import com.spiretos.nokia.mynotes.utils.DateUtils;
import com.spiretos.nokia.mynotes.utils.MLog;

import android.database.Cursor;

public class Note implements Serializable
{

	private static final long	serialVersionUID	= 5858563499540856575L;

	public int					ID;
	public String				Title;
	public String				Description;
	public boolean				Important;


	public Note()
	{
		ID = -1;
	}

	public Note(Cursor cursor)
	{
		ID = cursor.getInt(cursor.getColumnIndex("id"));
		Title = cursor.getString(cursor.getColumnIndex("title"));
		Description = cursor.getString(cursor.getColumnIndex("description"));

		int important = cursor.getInt(cursor.getColumnIndex("important"));
		if (important != 0)
			Important = true;


	}

	public Note(Note note)
	{
		ID = note.ID;
		Title = note.Title;
		Description = note.Description;
		Important = note.Important;
	}


	public void save()
	{
		String query;
		if (ID == -1)
		{
			query = "insert into notes (title,description,important,date) values ("
					+ "'"
					+ Title.replace("'", "''")
					+ "',"
					+ "'"
					+ Description.replace("'", "''")
					+ "',"
					+ "'"
					+ importantToString()
					+ "',"
					+ "'"
					+ DateUtils.getDateTimeNow() + "')";
		}
		else
		{
			query = "update notes set " + "title='" + Title.replace("'", "''")
					+ "'," + "description='" + Description.replace("'", "''")
					+ "'," + "important='" + importantToString() + "'"
					+ " where id='" + ID + "'";
		}

		try
		{
			MLog.e("query", query);
			new DBCustomer(query).executeQuery();
			MLog.e("query", "ok");
		}
		catch (Exception e)
		{
			MLog.e("query-ex", e.toString());
			e.printStackTrace();
		}
	}

	public void delete()
	{
		String query = "delete from notes where id='" + ID + "'";
		
		try
		{
			MLog.e("query", query);
			new DBCustomer(query).executeQuery();
			MLog.e("query", "ok");
		}
		catch (Exception e)
		{
			MLog.e("query-ex", e.toString());
			e.printStackTrace();
		}
	}

	private String importantToString()
	{
		if (Important)
			return "1";
		else
			return "0";
	}

}
