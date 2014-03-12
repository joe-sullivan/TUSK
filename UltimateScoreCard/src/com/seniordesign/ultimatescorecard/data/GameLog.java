package com.seniordesign.ultimatescorecard.data;

import java.io.Serializable;

import android.database.sqlite.SQLiteOpenHelper;

public class GameLog implements Serializable{
	private static final long serialVersionUID = 2437099151521151044L;
	protected String _thePlay;
	protected String _timeStamp;
	protected SQLiteOpenHelper _db;
	protected long g_id;
	
	
	public void setdb(SQLiteOpenHelper db){
		_db = db;
	}
	
	public void setgid(long gid){
		g_id = gid;
	}
}
