package com.spiretos.nokia.mynotes.storage;

import java.util.LinkedList;
import java.util.Queue;

import com.spiretos.nokia.mynotes.utils.MLog;

import android.database.Cursor;

public class DBCustomer
{
	private static Queue<String> queue=new LinkedList<String>();
	private static int ticketNumber=0;
	private static int ticketInterval=1000000;
	
	private String myQuery;
	private int myTicketNumber;
	
	public DBCustomer(String query)
	{
		this.myQuery=query;
		this.myTicketNumber=getTicket();
	}
	
	public void executeQuery() throws Exception
	{
		queue.add(myTicketNumber+"");
		MLog.v("db",myTicketNumber+" - new ("+myQuery+")");
		while (true)
		{
			if (queue.peek().equals(myTicketNumber+""))
			{
				try
				{
					DBAdapter.Instance.executeQuery(myQuery);
					queue.remove();
					MLog.v("db",myTicketNumber+" - done");
					break;
				}
				catch (Exception ex)
				{
					queue.remove();
					MLog.e("db",myTicketNumber+" - done ("+ex.toString()+"\n"+ex.getStackTrace().toString()+")");
					ex.printStackTrace();
					throw ex;
				}
			}
		}
	}
	public Cursor executeQueryForResult() throws Exception
	{
		queue.add(myTicketNumber+"");
		MLog.v("db",myTicketNumber+" - new ("+myQuery+")");
		while (true)
		{
			if (queue.peek().equals(myTicketNumber+""))
			{
				try
				{
					Cursor result=DBAdapter.Instance.executeQueryForResult(myQuery);
					queue.remove();
					MLog.v("db",myTicketNumber+" - done");
					return result;
				}
				catch (Exception ex)
				{
					queue.remove();
					MLog.e("db",myTicketNumber+" - done ("+ex.getMessage()+")");
					ex.printStackTrace();
					throw ex;
				}
			}
		}
	}
	
	public int executeQueryForCount() throws Exception
	{
		queue.add(myTicketNumber+"");
		MLog.v("db",myTicketNumber+" - new ("+myQuery+")");
		while (true)
		{
			if (queue.peek().equals(myTicketNumber+""))
			{
				try
				{
					Cursor result=DBAdapter.Instance.executeQueryForResult(myQuery);
					queue.remove();
					MLog.v("db",myTicketNumber+" - done");
					return result.getCount();
				}
				catch (Exception ex)
				{
					queue.remove();
					MLog.e("db",myTicketNumber+" - done ("+ex.getMessage()+")");
					ex.printStackTrace();
					throw ex;
				}
			}
		}
	}
	
	private static int getTicket()
	{
		ticketNumber++;
		if (ticketNumber==ticketInterval)
			ticketNumber=1;
		return ticketNumber;
	}
}
