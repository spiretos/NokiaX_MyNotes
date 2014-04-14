package com.spiretos.nokia.mynotes;

import com.spiretos.nokia.mynotes.notes.Note;
import com.spiretos.nokia.mynotes.storage.DBAdapter;
import com.spiretos.nokia.mynotes.widgets.MyWidgetProvider;
import com.spiretos.nokia.mynotes.widgets.MyWidgetProvider;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;


public class EditNoteActivity extends Activity
{
	
	Note mNote;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);

		setTitle(R.string.action_new);

		if (DBAdapter.Instance==null)
    		DBAdapter.Instance=new DBAdapter(this);
		
		initializeUI();
	}
	
	private void initializeUI()
	{
		try
		{
			Bundle b = getIntent().getExtras();
			if (b==null)
			{
				mNote=new Note();
				return;
			}
			else
			{
				mNote = (Note)b.getSerializable("note");
			}
			
			EditText title=(EditText)findViewById(R.id.editText1);
			title.setText(mNote.Title);
			
			EditText description=(EditText)findViewById(R.id.editText2);
			description.setText(mNote.Description);
			
			CheckBox check=(CheckBox)findViewById(R.id.checkBox1);
			check.setChecked(mNote.Important);
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
		
		if (DBAdapter.Instance==null)
    		DBAdapter.Instance=new DBAdapter(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_save)
		{
			saveNote();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	private void saveNote()
	{
		EditText title=(EditText)findViewById(R.id.editText1);
		mNote.Title=title.getText().toString();
		
		EditText description=(EditText)findViewById(R.id.editText2);
		mNote.Description=description.getText().toString();
		
		CheckBox check=(CheckBox)findViewById(R.id.checkBox1);
		mNote.Important=check.isChecked();
		
		mNote.save();
		
		MyWidgetProvider.updateWidgetData(this);
		
		finish();
	}
	
}