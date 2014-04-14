package com.spiretos.nokia.mynotes.storage;

import java.util.Vector;

import android.database.Cursor;

import com.spiretos.nokia.mynotes.notes.Note;

public class Queries
{
	public static Vector<Note> getAllNotes()
	{
		Vector<Note> notes=new Vector<Note>();
		
		String query="select * from notes";
		Cursor cursor=null;
		try
		{
			cursor = new DBCustomer(query).executeQueryForResult();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		if (cursor!=null && cursor.getCount()>0)
		{
			cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
            	notes.add(new Note(cursor));
           	    
            	cursor.moveToNext();
            }
            cursor.close();
		}
		
		return notes;
	}
	
	public static Note getNotesByID(int id)
	{
		String query="select * from notes where id="+id;
		Cursor cursor=null;
		try
		{
			cursor = new DBCustomer(query).executeQueryForResult();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		if (cursor!=null && cursor.getCount()>0)
		{
			cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
            	Note newNote=new Note(cursor);
            	cursor.close();
            	return newNote;
            }
            cursor.close();
		}
		
		return null;
	}
}
