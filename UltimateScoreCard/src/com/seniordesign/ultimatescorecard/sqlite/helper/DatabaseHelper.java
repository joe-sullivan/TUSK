package com.seniordesign.ultimatescorecard.sqlite.helper;

import java.util.ArrayList;
import java.util.List;
import com.seniordesign.ultimatescorecard.sqlite.helper.*;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	// Logcat tag
    protected static final String LOG = "DatabaseHelper";
	
    //Table Names
    protected static final String TABLE_GAMES = "games";
    protected static final String TABLE_PLAYERS = "players";
    protected static final String TABLE_TEAMS = "teams";
    protected static final String TABLE_PLAY_BY_PLAY = "play_by_play";
    protected static final String TABLE_SHOT_CHART_COORDS = "shot_chart_coords";
    
    //Common Column Names
    protected static final String KEY_G_ID = "g_id";
    protected static final String KEY_P_ID = "p_id";
    protected static final String KEY_T_ID = "t_id";
    protected static final String KEY_A_ID = "a_id";
    protected static final String KEY_PERIOD = "period";
    

    //GAMES Table - column names
    protected static final String KEY_HOME_ID = "home_id";
    protected static final String KEY_AWAY_ID = "away_id";
    protected static final String KEY_DATE = "date";
    
    //PLAYERS Table - column names
    protected static final String KEY_P_NAME = "p_name";
    protected static final String KEY_P_NUM = "p_num";

    //TEAMS Table - column names
    protected static final String KEY_T_NAME = "t_name";
    protected static final String KEY_C_NAME = "c_name";
    protected static final String KEY_SPORT = "sport";
    protected static final String KEY_ABBV = "abbv";
    
    //PLAY_BY_PLAY Table - column names
    protected static final String KEY_ACTION = "action";
    protected static final String KEY_TIME = "time";
    protected static final String KEY_HOME_SCORE = "home_score";
    protected static final String KEY_AWAY_SCORE = "away_score";

    //SHOT_CHART_COORDS Table - column names
    protected static final String KEY_X = "x";
    protected static final String KEY_Y = "y";
    protected static final String KEY_MADE = "made";
    protected static final String KEY_SHOT_ID = "shot_id";

  //PLAYERS table create statement
    protected static final String CREATE_TABLE_PLAYERS = "CREATE TABLE IF NOT EXISTS " + TABLE_PLAYERS 
    		+ "(" + KEY_P_ID + " INTEGER PRIMARY KEY," 
    		+ KEY_T_ID + " INTEGER, "
    		// + FOREIGN KEY REFERENCES " + TABLE_TEAMS + "(" + KEY_T_ID + ")," 
    		+ KEY_P_NAME + " VARCHAR(45)," + KEY_P_NUM + " INTEGER" + ")"; 
    
    //TEAMS table create statement
    protected static final String CREATE_TABLE_TEAMS = "CREATE TABLE IF NOT EXISTS " + TABLE_TEAMS 
    		+ "(" + KEY_T_ID + " INTEGER PRIMARY KEY," + KEY_T_NAME + " VARCHAR(45)," 
    		+ KEY_ABBV + " VARCHAR(45),"+ KEY_C_NAME + " VARCHAR(45),"+ KEY_SPORT + " VARCHAR(45)" + ")"; 
    
    //PLAY_BY_PLAY table create statement
    protected static final String CREATE_TABLE_PLAY_BY_PLAY = "CREATE TABLE IF NOT EXISTS " + TABLE_PLAY_BY_PLAY 
    		+ "(" + KEY_A_ID + " INTEGER PRIMARY KEY," + KEY_G_ID + " INTEGER," 
    		+ KEY_ACTION + " VARCHAR(45)," + KEY_TIME + " VARCHAR(45)," + KEY_PERIOD + " VARCHAR(10)," + KEY_HOME_SCORE + " INTEGER, " 
    		+ KEY_AWAY_SCORE + " INTEGER" + ")";
    
    //SHOT_CHART_COORDS table create statement
    protected static final String CREATE_TABLE_SHOT_CHART_COORDS = "CREATE TABLE IF NOT EXISTS " + TABLE_SHOT_CHART_COORDS 
    		+ "(" + KEY_SHOT_ID + " INTEGER PRIMARY KEY," + KEY_G_ID + " INTEGER," 
    		+ KEY_P_ID + " INTEGER," + KEY_T_ID + " INTEGER,"
    		+ KEY_X + " INTEGER," + KEY_Y + " INTEGER," 
    		+ KEY_MADE + " VARCHAR(4)" + ")";
    

	public DatabaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
	// ----------------------- PLAY_BY_PLAY table method --------------------- //
	
	public long createPlayByPlay(PlayByPlay pbp){
		SQLiteDatabase db = this.getWritableDatabase();
		 
        ContentValues values = new ContentValues();
        values.put(KEY_G_ID, pbp.getgid());
        values.put(KEY_ACTION, pbp.getaction());
        values.put(KEY_TIME, pbp.gettime());
        values.put(KEY_PERIOD, pbp.getperiod());
        values.put(KEY_HOME_SCORE, pbp.gethomescore());
        values.put(KEY_AWAY_SCORE, pbp.getawayscore());

        // insert row
        long a_id = db.insert(TABLE_PLAY_BY_PLAY, null, values);
 
        return a_id;
	}
	
	public List<PlayByPlay> getPlayByPlayGame(long g_id){
	    SQLiteDatabase db = this.getReadableDatabase();
		List<PlayByPlay> pbps = new ArrayList<PlayByPlay>();
		String selectPlayByPlayQuery = "SELECT * FROM " + TABLE_PLAY_BY_PLAY + " WHERE " + KEY_G_ID + " = " + g_id;
        
        Log.i(LOG, selectPlayByPlayQuery);
        
        Cursor c = db.rawQuery(selectPlayByPlayQuery, null);
        
        if (c!=null && c.moveToFirst()){        
	        do {
	        	//create the instance of Players using cursor information
			    PlayByPlay pbp = new PlayByPlay();
			    pbp.setaid(c.getLong(c.getColumnIndex(KEY_A_ID)));
			    pbp.setgid(c.getLong(c.getColumnIndex(KEY_G_ID)));
			    pbp.setaction(c.getString(c.getColumnIndex(KEY_ACTION)));
			    pbp.settime(c.getString(c.getColumnIndex(KEY_TIME)));
			    pbp.setperiod(c.getString(c.getColumnIndex(KEY_PERIOD)));
			    pbp.sethomescore(c.getInt(c.getColumnIndex(KEY_HOME_SCORE)));
			    pbp.setawayscore(c.getInt(c.getColumnIndex(KEY_AWAY_SCORE)));
	            // adding to playbyplay list
	            pbps.add(pbp);
	        } while(c.moveToNext());
        }
        return pbps;
	}
	
	// Delete PlayByPlay of a game
	public void deletePlayByPlayGame(long g_id) {
	    SQLiteDatabase db = this.getWritableDatabase();
	    db.delete(TABLE_PLAY_BY_PLAY, KEY_G_ID + " = ?",
	            new String[] { String.valueOf(g_id) });
	}
	
	// -------------------PLAYERS table methods----------------------------- //
	
	public List<Players> getPlayersTeam(long t_id){
	    SQLiteDatabase db = this.getReadableDatabase();
		List<Players> players = new ArrayList<Players>();
		String selectPlayerQuery = "SELECT * FROM " + TABLE_PLAYERS + " WHERE " + KEY_T_ID + " = " + t_id;
        
        Log.i(LOG, selectPlayerQuery);
        
        Cursor c = db.rawQuery(selectPlayerQuery, null);
        
        if (c!=null)
        	c.moveToFirst();
        
        do {
        	//create the instance of Players using cursor information
		    Players player = new Players();
		    player.setpid(c.getLong(c.getColumnIndex(KEY_P_ID)));
		    player.settid(c.getLong(c.getColumnIndex(KEY_T_ID)));
		    player.setpname((c.getString(c.getColumnIndex(KEY_P_NAME))));
		    player.setpnum((c.getInt(c.getColumnIndex(KEY_P_NUM))));

		    
            // adding to players list
            players.add(player);
        } while(c.moveToNext());
        
        return players;
	}

	public List<Players> getPlayersTeam2(long t_id){
	    SQLiteDatabase db = this.getReadableDatabase();
		List<Players> players = new ArrayList<Players>();
		String selectPlayerQuery = "SELECT * FROM " + TABLE_PLAYERS + " WHERE " + KEY_T_ID + " = " + t_id;
        
        Log.i(LOG, selectPlayerQuery);
        
        Cursor c = db.rawQuery(selectPlayerQuery, null);
        
        if (c!=null)
        	c.moveToFirst();
        
        do {
        	//create the instance of Players using cursor information
		    Players player = new Players();
		    player.setpid(c.getLong(c.getColumnIndex(KEY_P_ID)));
		    player.settid(c.getLong(c.getColumnIndex(KEY_T_ID)));
		    player.setpname((c.getString(c.getColumnIndex(KEY_P_NAME))));
		    player.setpnum((c.getInt(c.getColumnIndex(KEY_P_NUM))));
		    
            // adding to players list
            players.add(player);
        } while(c.moveToNext());
        
        return players;
	}
	
	public int updatePlayer(Players player){
	    SQLiteDatabase db = this.getWritableDatabase();
	    
        ContentValues values = new ContentValues();
        values.put(KEY_T_ID, player.gettid());
        values.put(KEY_P_NAME, player.getpname());
        values.put(KEY_P_NUM, player.getpnum());
        
        return db.update(TABLE_PLAYERS, values, KEY_P_ID + " = ?", new String[] {String.valueOf(player.getpid())});
	}
	
	public List<Players> getAllPlayers(){
	    SQLiteDatabase db = this.getReadableDatabase();
		List<Players> players = new ArrayList<Players>();
		String selectPlayerQuery = "SELECT * FROM " + TABLE_PLAYERS;
        
        Log.i(LOG, selectPlayerQuery);
        
        Cursor c = db.rawQuery(selectPlayerQuery, null);
        
        if (c!=null)
        	c.moveToFirst();
        
        do {
        	//create the instance of Players using cursor information
        	Players player = new Players();
		    player.setpid(c.getLong(c.getColumnIndex(KEY_P_ID)));
		    player.settid(c.getLong(c.getColumnIndex(KEY_T_ID)));
		    player.setpname((c.getString(c.getColumnIndex(KEY_P_NAME))));
		    player.setpnum((c.getInt(c.getColumnIndex(KEY_P_NUM))));
		   
            // adding to players list
            players.add(player);
        } while(c.moveToNext());

        return players;
	}
	
	// Delete a Player
	public void deletePlayer(long p_id) {
	    SQLiteDatabase db = this.getWritableDatabase();
	    db.delete(TABLE_PLAYERS, KEY_P_ID + " = " + p_id, null);
	}
	
	
	// Delete Players on a team
	public void deletePlayers(long t_id) {
	    SQLiteDatabase db = this.getWritableDatabase();
	    db.delete(TABLE_PLAYERS, KEY_T_ID + " = ?",
	            new String[] { String.valueOf(t_id) });
	}

	
	// -------------------SHOT_CHART_COORDS table methods ------------------ //
	
	//create a row of shot chart coordinates
	public long createShot(ShotChartCoords shot){
		SQLiteDatabase db = this.getWritableDatabase();
		 
        ContentValues values = new ContentValues();
        values.put(KEY_G_ID, shot.getgid());
        values.put(KEY_P_ID, shot.getpid());
        values.put(KEY_T_ID, shot.gettid());
        values.put(KEY_X, shot.getx());
        values.put(KEY_Y, shot.gety());
        values.put(KEY_MADE, shot.getmade());

        // insert row
        long row = db.insert(TABLE_SHOT_CHART_COORDS, null, values);
 
        return row;
	}
	
	public List<ShotChartCoords> getAllShots(){
	    SQLiteDatabase db = this.getReadableDatabase();
		List<ShotChartCoords> shots = new ArrayList<ShotChartCoords>();
		String selectQuery = "SELECT * FROM " + TABLE_SHOT_CHART_COORDS;
        
        Log.i(LOG, selectQuery);
        
        Cursor c = db.rawQuery(selectQuery, null);
        
	    if (c != null && c.moveToFirst()){
        
	        do {
	        	//create the instance of Players using cursor information
			    ShotChartCoords shot = new ShotChartCoords();
			    shot.setshotid(c.getLong(c.getColumnIndex(KEY_SHOT_ID)));
			    shot.setgid((c.getLong(c.getColumnIndex(KEY_G_ID))));		    
			    shot.setpid((c.getLong(c.getColumnIndex(KEY_P_ID))));
			    shot.settid((c.getLong(c.getColumnIndex(KEY_T_ID))));
			    shot.setx((c.getInt(c.getColumnIndex(KEY_X))));
			    shot.sety((c.getInt(c.getColumnIndex(KEY_Y))));
			    shot.setmade((c.getString(c.getColumnIndex(KEY_MADE))));
	
	            // adding to players list
			    shots.add(shot);
	        } while(c.moveToNext());
	    }
        return shots;
	}
	
	public List<ShotChartCoords> getAllTeamShots(long t_id){
	    SQLiteDatabase db = this.getReadableDatabase();
		List<ShotChartCoords> shots = new ArrayList<ShotChartCoords>();
		String selectQuery = "SELECT * FROM " + TABLE_SHOT_CHART_COORDS 
				+ " WHERE " + KEY_T_ID + " = " + t_id;
        
        Log.i(LOG, selectQuery);
        
        Cursor c = db.rawQuery(selectQuery, null);
        
        if (c!=null)
        	c.moveToFirst();
        
        do {
        	//create the instance of Players using cursor information
		    ShotChartCoords shot = new ShotChartCoords();
		    shot.setshotid(c.getLong(c.getColumnIndex(KEY_SHOT_ID)));
		    shot.setgid((c.getLong(c.getColumnIndex(KEY_G_ID))));		    
		    shot.setpid((c.getLong(c.getColumnIndex(KEY_P_ID))));
		    shot.settid((c.getLong(c.getColumnIndex(KEY_T_ID))));
		    shot.setx((c.getInt(c.getColumnIndex(KEY_X))));
		    shot.sety((c.getInt(c.getColumnIndex(KEY_Y))));
		    shot.setmade((c.getString(c.getColumnIndex(KEY_MADE))));
		    
            // adding to players list
		    shots.add(shot);
        } while(c.moveToNext());
        
        return shots;
	}
	
	public List<ShotChartCoords> getAllPlayerShots(long p_id){
	    SQLiteDatabase db = this.getReadableDatabase();
		List<ShotChartCoords> shots = new ArrayList<ShotChartCoords>();
		String selectQuery = "SELECT * FROM " + TABLE_SHOT_CHART_COORDS + "WHERE " + KEY_P_ID + " = " + p_id;
        
        Log.i(LOG, selectQuery);
        
        Cursor c = db.rawQuery(selectQuery, null);
        
        if (c!=null)
        	c.moveToFirst();
        
        do {
        	//create the instance of Players using cursor information
		    ShotChartCoords shot = new ShotChartCoords();
		    shot.setshotid(c.getLong(c.getColumnIndex(KEY_SHOT_ID)));
		    shot.setgid((c.getLong(c.getColumnIndex(KEY_G_ID))));		    
		    shot.setpid((c.getLong(c.getColumnIndex(KEY_P_ID))));
		    shot.settid((c.getLong(c.getColumnIndex(KEY_T_ID))));
		    shot.setx((c.getInt(c.getColumnIndex(KEY_X))));
		    shot.sety((c.getInt(c.getColumnIndex(KEY_Y))));
		    shot.setmade((c.getString(c.getColumnIndex(KEY_MADE))));

            // adding to players list
		    shots.add(shot);
        } while(c.moveToNext());
        
        return shots;
	}
	
	public List<ShotChartCoords> getAllTeamShotsGame(long t_id, long g_id){
	    SQLiteDatabase db = this.getReadableDatabase();
		List<ShotChartCoords> shots = new ArrayList<ShotChartCoords>();
		String selectQuery = "SELECT * FROM " + TABLE_SHOT_CHART_COORDS
				+ " WHERE (" + KEY_T_ID + " = " + t_id + ") AND (" + KEY_G_ID + " = " + g_id + ")";
        
        Log.i(LOG, selectQuery);
        
        Cursor c = db.rawQuery(selectQuery, null);
        
        if (c!=null && c.moveToFirst()){
	        do {
	        	//create the instance of Players using cursor information
			    ShotChartCoords shot = new ShotChartCoords();
			    shot.setshotid(c.getLong(c.getColumnIndex(KEY_SHOT_ID)));
			    shot.setgid((c.getLong(c.getColumnIndex(KEY_G_ID))));		    
			    shot.setpid((c.getLong(c.getColumnIndex(KEY_P_ID))));
			    shot.settid((c.getLong(c.getColumnIndex(KEY_T_ID))));
			    shot.setx((c.getInt(c.getColumnIndex(KEY_X))));
			    shot.sety((c.getInt(c.getColumnIndex(KEY_Y))));
			    shot.setmade((c.getString(c.getColumnIndex(KEY_MADE))));
	
	            // adding to players list
			    shots.add(shot);
	        } while(c.moveToNext());
        
        }
        
        return shots;
	}
	
	public List<ShotChartCoords> getAllPlayerShotsGame(long p_id, long g_id){
	    SQLiteDatabase db = this.getReadableDatabase();
		List<ShotChartCoords> shots = new ArrayList<ShotChartCoords>();
		String selectQuery = "SELECT * FROM " + TABLE_SHOT_CHART_COORDS +  
				" WHERE " + KEY_P_ID + " = " + p_id + " AND " + KEY_G_ID + " = " + g_id;
        
        Log.i(LOG, selectQuery);
        
        Cursor c = db.rawQuery(selectQuery, null);
        
        if (c!=null)
        	c.moveToFirst();
        
        do {
        	//create the instance of Players using cursor information
		    ShotChartCoords shot = new ShotChartCoords();
		    shot.setshotid(c.getLong(c.getColumnIndex(KEY_SHOT_ID)));
		    shot.setgid((c.getLong(c.getColumnIndex(KEY_G_ID))));		    
		    shot.setpid((c.getLong(c.getColumnIndex(KEY_P_ID))));
		    shot.settid((c.getLong(c.getColumnIndex(KEY_T_ID))));
		    shot.setx((c.getInt(c.getColumnIndex(KEY_X))));
		    shot.sety((c.getInt(c.getColumnIndex(KEY_Y))));
		    shot.setmade((c.getString(c.getColumnIndex(KEY_MADE))));

            // adding to players list
		    shots.add(shot);
        } while(c.moveToNext());
        
        return shots;
	}
	
	// Delete a Shot
	public void deleteShot(long shot_id) {
	    SQLiteDatabase db = this.getWritableDatabase();
	    db.delete(TABLE_SHOT_CHART_COORDS, KEY_SHOT_ID + " = " + shot_id, null);
	}
	
		
	// ----------------------- TEAMS table methods ------------------------- //

	public long createTeams(Teams team){
		SQLiteDatabase db = this.getWritableDatabase();
		 
        ContentValues values = new ContentValues();
        //values.put(KEY_T_ID, team.gettid());
        values.put(KEY_T_NAME, team.gettname());
        values.put(KEY_ABBV, team.getabbv());
        values.put(KEY_C_NAME, team.getcname());
        values.put(KEY_SPORT, team.getSport());


        // insert row
        long p_id = db.insert(TABLE_TEAMS, null, values);
 
        return p_id;
	}
	
	//get single team with id
	public Teams getTeam(long t_id) {
	    SQLiteDatabase db = this.getReadableDatabase();
	    //create query to select game
	    String selectQuery = "SELECT  * FROM " + TABLE_TEAMS + 
	    	" WHERE " + KEY_T_ID + " = " + t_id;
	    //Log the query
	    Log.i(LOG, selectQuery);
	    //perform the query and store data in cursor
	    Cursor c = db.rawQuery(selectQuery, null);
	    //set cursor to beginning
	    if (c != null)
	        c.moveToFirst();
	    //create the instance of Teams using cursor information
	    Teams team = new Teams();
	    team.settid(c.getLong(c.getColumnIndex(KEY_T_ID)));
	    team.settname((c.getString(c.getColumnIndex(KEY_T_NAME))));
	    team.setabbv((c.getString(c.getColumnIndex(KEY_ABBV))));
	    team.setcname((c.getString(c.getColumnIndex(KEY_C_NAME))));
	    team.setsport((c.getString(c.getColumnIndex(KEY_SPORT))));
	 
	    return team;
	}
	
	//get single team with name
	public Teams getTeam(String t_name) {
	    SQLiteDatabase db = this.getReadableDatabase();
	    //create query to select game
	    String selectQuery = "SELECT  * FROM " + TABLE_TEAMS + 
	    	" WHERE " + KEY_T_NAME + " = " + t_name;
	    //Log the query
	    Log.i(LOG, selectQuery);
	    //perform the query and store data in cursor
	    Cursor c = db.rawQuery(selectQuery, null);
	    //set cursor to beginning
	    if (c != null)
	        c.moveToFirst();
	    //create the instance of Teams using cursor information
	    Teams team = new Teams();
	    team.settid(c.getLong(c.getColumnIndex(KEY_T_ID)));
	    team.settname((c.getString(c.getColumnIndex(KEY_T_NAME))));
	    team.setabbv((c.getString(c.getColumnIndex(KEY_ABBV))));
	    team.setcname((c.getString(c.getColumnIndex(KEY_C_NAME))));
	    team.setsport((c.getString(c.getColumnIndex(KEY_SPORT))));
	 
	    return team;
	}
	
	public List<Teams> getAllTeams(){
	    SQLiteDatabase db = this.getReadableDatabase();
		List<Teams> teams = new ArrayList<Teams>();
		String selectQuery = "SELECT * FROM " + TABLE_TEAMS;
        
        Log.i(LOG, selectQuery);
        
        Cursor c = db.rawQuery(selectQuery, null);
        
        if (c!=null)
        	c.moveToFirst();
        
        do {
        	//create the instance of Players using cursor information
		    Teams team = new Teams();
		    team.settid(c.getLong(c.getColumnIndex(KEY_T_ID)));
		    team.settname((c.getString(c.getColumnIndex(KEY_T_NAME))));
		    team.setabbv((c.getString(c.getColumnIndex(KEY_ABBV))));
		    team.setcname((c.getString(c.getColumnIndex(KEY_C_NAME))));
		    team.setsport((c.getString(c.getColumnIndex(KEY_SPORT))));
		   
            // adding to players list
		    teams.add(team);
        } while(c.moveToNext());
        
        return teams;
	}
	
	public int updateTeam(Teams team){
	    SQLiteDatabase db = this.getWritableDatabase();
	    
        ContentValues values = new ContentValues();
        values.put(KEY_T_NAME, team.gettname());
        values.put(KEY_ABBV, team.getabbv());
        values.put(KEY_C_NAME, team.getcname());
        values.put(KEY_SPORT, team.getSport());
        
        return db.update(TABLE_TEAMS, values, KEY_T_ID + " = ?", new String[] {String.valueOf(team.gettid())});
	}
	
	// Delete a Team
	public void deleteTeam(long t_id) {
		deletePlayers(t_id);
	    SQLiteDatabase db = this.getWritableDatabase();
	    db.delete(TABLE_TEAMS, KEY_T_ID + " = ?",
	            new String[] { String.valueOf(t_id) });
	}

}
