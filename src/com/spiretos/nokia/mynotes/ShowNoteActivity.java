package com.spiretos.nokia.mynotes;

import com.spiretos.nokia.mynotes.notes.Note;
import com.spiretos.nokia.mynotes.storage.DBAdapter;
import com.spiretos.nokia.mynotes.storage.Queries;
import com.spiretos.nokia.mynotes.utils.MLog;
import com.spiretos.nokia.mynotes.widgets.MyWidgetProvider;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


public class ShowNoteActivity extends Activity
{
	
	Note mNote;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show);

		setTitle(R.string.note);

		if (DBAdapter.Instance==null)
    		DBAdapter.Instance=new DBAdapter(this);
		
		initializeUI();
	}
	
	
	private void initializeUI()
	{
		try
		{
			if (mNote==null)
			{
				Bundle b = getIntent().getExtras();
				mNote = (Note)b.getSerializable("note");
			}
			
			TextView title=(TextView)findViewById(R.id.show_title);
			title.setText(mNote.Title);
			
			TextView description=(TextView)findViewById(R.id.show_description);
			description.setText(mNote.Description);
			
			LinearLayout importantLayout=(LinearLayout)findViewById(R.id.important_linearlayout);
			if (!mNote.Important)
				importantLayout.setVisibility(View.GONE);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	
	@Override
	protected void onResume()
	{
		super.onResume();
		
		MLog.e("resume", "bika");
		
		if (DBAdapter.Instance==null)
    		DBAdapter.Instance=new DBAdapter(this);
		
		Note tempNote=Queries.getNotesByID(mNote.ID);
		if (tempNote!=null)
			mNote=tempNote;
		
		initializeUI();
	}
	
	
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.show_edit)
		{
			goEditNote();
			return true;
		}
		else if (id == R.id.show_delete)
		{
			deleteNote();
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	private void goEditNote()
	{
		Intent intent = new Intent(this, EditNoteActivity.class);
		Bundle b = new Bundle();
		b.putSerializable("note",mNote);
		intent.putExtras(b);
		startActivity(intent);
	}
	
	private void deleteNote()
	{
		AlertDialog deleteAlert = new AlertDialog.Builder(this).create();
		deleteAlert.setTitle("Delete this note?");
		//deleteAlert.setMessage("Are you sure you want to delete this list?");
		deleteAlert.setButton(AlertDialog.BUTTON_POSITIVE,"Yes",new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				mNote.delete();
				MyWidgetProvider.updateWidgetData(ShowNoteActivity.this);
				finish();
			}
		});
		deleteAlert.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				
			}
		});
		deleteAlert.show();
	}
	
}