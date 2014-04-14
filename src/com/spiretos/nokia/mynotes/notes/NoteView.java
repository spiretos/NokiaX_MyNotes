package com.spiretos.nokia.mynotes.notes;

import com.spiretos.nokia.mynotes.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class NoteView extends LinearLayout
{
	
	Context mContext;
	Note mNote;
	
	
	public NoteView(Context context, Note note)
	{
		super(context);

		mContext=context;
		mNote=note;
		
		LayoutInflater.from(context).inflate(R.layout.noteview, this, true);
		
		updateView(note);
	}
	
	public void updateView(Note n)
	{
		TextView title=(TextView)findViewById(R.id.noteview__title);
		title.setText(n.Title);
		
		TextView description=(TextView)findViewById(R.id.noteview__description);
		description.setText(n.Description);
		
		ImageView image=(ImageView)findViewById(R.id.noteview__image);
		if (n.Important)
			image.setVisibility(View.VISIBLE);
		else
			image.setVisibility(View.INVISIBLE);
	}

}
