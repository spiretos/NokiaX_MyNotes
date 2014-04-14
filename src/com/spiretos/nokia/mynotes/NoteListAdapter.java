package com.spiretos.nokia.mynotes;

import java.util.Vector;

import com.spiretos.nokia.mynotes.notes.Note;
import com.spiretos.nokia.mynotes.notes.NoteView;
import com.spiretos.nokia.mynotes.storage.Queries;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


public class NoteListAdapter extends BaseAdapter
{

	Context mContext;
	public Vector<Note> mNotes;
	
	
	public NoteListAdapter(Context context)
	{
		mContext=context;
		
		keepTheNotes();
	}
	
	
	private void keepTheNotes()
	{
		mNotes=new Vector<Note>();
		
		Vector<Note> notes=Queries.getAllNotes();
		for (Note n : notes)
			mNotes.add(new Note(n));
	}
	
	
	@Override
	public int getCount()
	{
		return mNotes.size();
	}

	@Override
	public Note getItem(int position)
	{
		return mNotes.elementAt(position);
	}

	@Override
	public long getItemId(int position)
	{
		return mNotes.elementAt(position).ID;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		NoteView view;
		
		if (convertView==null)
			view = new NoteView(mContext,mNotes.elementAt(position));
		else
			view=(NoteView)convertView;
		
		view.updateView(mNotes.elementAt(position));
		
		return view;
	}

}
