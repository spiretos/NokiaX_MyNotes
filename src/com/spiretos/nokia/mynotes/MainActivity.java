package com.spiretos.nokia.mynotes;

import com.spiretos.nokia.mynotes.notes.Note;
import com.spiretos.nokia.mynotes.storage.DBAdapter;
import com.spiretos.nokia.mynotes.utils.MLog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity
{

	TextView		mText;
	ListView		mList;
	NoteListAdapter	mAdapter;

	boolean			updateList	= false;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (DBAdapter.Instance==null)
    		DBAdapter.Instance=new DBAdapter(this);
		
		mText = (TextView) findViewById(R.id.show_titlelabel);
		mList = (ListView) findViewById(R.id.mailList);

		mList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id)
			{
				updateList=true;
				
				Note selectedNote=mAdapter.getItem(position);
				
				Intent intent = new Intent(MainActivity.this, ShowNoteActivity.class);
				Bundle b = new Bundle();
				b.putSerializable("note",selectedNote);
				intent.putExtras(b);
				startActivity(intent);
			}
		});

		fillList();

		MLog.e("test", "onCreate");
	}

	@Override
	protected void onResume()
	{
		super.onResume();

		if (DBAdapter.Instance==null)
    		DBAdapter.Instance=new DBAdapter(this);
		
		MLog.e("test", "onResume");
		
		if (updateList)
		{
			updateList=false;
			fillList();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_new)
		{
			updateList=true;
			Intent intent = new Intent(this, EditNoteActivity.class);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


	private void fillList()
	{
		mAdapter = new NoteListAdapter(MainActivity.this);
		mList.setAdapter(mAdapter);
	}
}
