package com.seniordesign.ultimatescorecard.sqlite.hockey;


import java.util.ArrayList;
import java.util.List;

import com.seniordesign.ultimatescorecard.sqlite.DatabaseHelper;
import com.seniordesign.ultimatescorecard.sqlite.helper.Games;
import com.seniordesign.ultimatescorecard.sqlite.helper.PlayByPlay;
import com.seniordesign.ultimatescorecard.sqlite.helper.Players;
import com.seniordesign.ultimatescorecard.sqlite.helper.ShotChartCoords;
import com.seniordesign.ultimatescorecard.sqlite.helper.Teams;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class HockeyDatabaseHelper extends DatabaseHelper{

	// Logcat tag
    private static final String LOG = "HockeyDatabaseHelper";
 
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "HockeyStats";
	
    //Table Names
    private static final String TABLE_GAMES = "games";
    private static final String TABLE_HOCKEY_GAME_STATS = "hockey_game_stats";
    private static final String TABLE = "players";
    private static final String TABLE_TEAMS = "teams";
    private static final String TABLE_PLAY_BY_PLAY = "play_by_play";
    private static final String TABLE_SHOT_CHART_COORDS = "shot_chart_coords";


    //Common Column Names
    private static final String KEY_G_ID = "g_id";
    private static final String KEY_P_ID = "p_id";
    private static final String KEY_T_ID = "t_id";
    private static final String KEY_A_ID = "a_id";
    
    //GAMES Table - column names
    private static final String KEY_HOME_ID = "home_id";
    private static final String KEY_AWAY_ID = "away_id";
    private static final String KEY_DATE = "date";
    
    //HOCKEYGAMESTATS - common column names
    private static final String KEY_SOG = "sog";
    private static final String KEY_GOALS = "goals";
    
    //HOCKEYGAMESTATSGOALIE Table - column names
    private static final String KEY_SAVES = "saves";
    private static final String KEY_GOALS_ALLOWED = "goals_allowed";
    
    //HOCKEYGAMESTATSPLAYERS Table - column names
    private static final String KEY_SHOTS = "shots";
    private static final String KEY_AST = "ast";
    private static final String KEY_PEN_MINOR = "pen_minor";
    private static final String KEY_PEN_MAJOR = "pen_major";
    
    //PLAYERS Table - column names
    private static final String KEY_P_NAME = "p_name";
    private static final String KEY_P_NUM = "p_num";

    //TEAMS Table - column names
    private static final String KEY_T_NAME = "t_name";
    private static final String KEY_C_NAME = "c_name";
    private static final String KEY_SPORT = "sport";
    
    //PLAY_BY_PLAY Table - column names
    private static final String KEY_ACTION = "action";
    private static final String KEY_TIME = "time";
    private static final String KEY_HOME_SCORE = "home_score";
    private static final String KEY_AWAY_SCORE = "away_score";

    //SHOT_CHART_COORDS Table - column names
    private static final String KEY_X = "x";
    private static final String KEY_Y = "y";
    private static final String KEY_MADE = "made";

    //Table Create Statements
    //GAMES table create statement
    private static final String CREATE_TABLE_GAMES = "CREATE TABLE IF NOT EXISTS " + TABLE_GAMES 
    		+ "(" + KEY_G_ID + " INTEGER PRIMARY KEY," + KEY_HOME_ID + " INTEGER," 
    		+ KEY_AWAY_ID + " INTEGER," + KEY_DATE + " DATE" + ")"; 
    //Doesn't include SPORT yet...
    
    //HOCKEY_GAME_STATS table create statement
    private static final String CREATE_TABLE_HOCKEY_GAME_STATS = "CREATE TABLE IF NOT EXISTS " + TABLE_HOCKEY_GAME_STATS 
    		+ "(" + KEY_G_ID + " INTEGER" + ", " + KEY_P_ID + " INTEGER" + ", "
    		+ KEY_SHOTS + " INTEGER, " + KEY_SOG + " INTEGER, " + KEY_GOALS + " INTEGER, "
    		+ KEY_AST + " INTEGER, " + KEY_PEN_MINOR + " INTEGER, " + KEY_PEN_MAJOR + " INTEGER, "
    		+ KEY_SAVES + " INTEGER, " + KEY_GOALS_ALLOWED + " INTEGER"
    		+ ")"; 
    
    //PLAYERS table create statement
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE 
    		+ "(" + KEY_P_ID + " INTEGER PRIMARY KEY," 
    		+ KEY_T_ID + " INTEGER, "
    		// + FOREIGN KEY REFERENCES " + TABLE_TEAMS + "(" + KEY_T_ID + ")," 
    		+ KEY_P_NAME + " VARCHAR(45)," + KEY_P_NUM + " INTEGER" + ")"; 
    
    //TEAMS table create statement
    private static final String CREATE_TABLE_TEAMS = "CREATE TABLE IF NOT EXISTS " + TABLE_TEAMS 
    		+ "(" + KEY_T_ID + " INTEGER PRIMARY KEY," + KEY_T_NAME + " VARCHAR(45)," 
    		+ KEY_C_NAME + " VARCHAR(45),"+ KEY_SPORT + " VARCHAR(45)" + ")"; 
    
    //PLAY_BY_PLAY table create statement
    private static final String CREATE_TABLE_PLAY_BY_PLAY = "CREATE TABLE IF NOT EXISTS " + TABLE_PLAY_BY_PLAY 
    		+ "(" + KEY_A_ID + " INTEGER PRIMARY KEY," + KEY_G_ID + " INTEGER," 
    		+ KEY_ACTION + " VARCHAR(45)," + KEY_TIME + " VARCHAR(45)," + KEY_HOME_SCORE + " INTEGER, " 
    		+ KEY_AWAY_SCORE + " INTEGER" + ")";
    
    //SHOT_CHART_COORDS table create statement
    private static final String CREATE_TABLE_SHOT_CHART_COORDS = "CREATE TABLE IF NOT EXISTS " + TABLE_SHOT_CHART_COORDS 
    		+ "(" + KEY_SHOT_ID + " INTEGER PRIMARY KEY," + KEY_G_ID + " INTEGER," 
    		+ KEY_P_ID + " INTEGER," + KEY_X + " INTEGER," + KEY_Y + " INTEGER," 
    		+ KEY_MADE + " VARCHAR(4)" + ")";
    
    public HockeyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
	public HockeyDatabaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
        db.execSQL(CREATE_TABLE_GAMES);
        db.execSQL(CREATE_TABLE_HOCKEY_GAME_STATS);
        db.execSQL(CREATE_TABLE);
        db.execSQL(CREATE_TABLE_TEAMS);
        db.execSQL(CREATE_TABLE_PLAY_BY_PLAY);
        db.execSQL(CREATE_TABLE_SHOT_CHART_COORDS);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GAMES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HOCKEY_GAME_STATS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEAMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAY_BY_PLAY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOT_CHART_COORDS);

        // create new tables
        onCreate(db);
	}
	
	// ----------------------- GAMES table methods ------------------------- //

	public long createGame(Games game){
		SQLiteDatabase db = this.getWritableDatabase();
		 
        ContentValues values = new ContentValues();
        values.put(KEY_HOME_ID, game.gethomeid());
        values.put(KEY_AWAY_ID, game.getawayid());
        values.put(KEY_DATE, game.getDate());
 
        // insert row
        long g_id = db.insert(TABLE_GAMES, null, values);
        
        List<Players> home_players = getPlayersTeam(game.gethomeid());
        List<Players> away_players = getPlayersTeam(game.getawayid());

        for(Players player : home_players){
        	createGameStats(player.getpid(), g_id);
        }
        for(Players player : away_players){
        	createGameStats(player.getpid(), g_id);
        }
 
        return g_id;
	}
	
	//get single game
	public Games getGame(long g_id) {
	    SQLiteDatabase db = this.getReadableDatabase();
	    //create query to select game
	    String selectQuery = "SELECT  * FROM " + TABLE_GAMES + 
	    	" WHERE " + KEY_G_ID + " = " + g_id;
	    //Log the query
	    Log.i(LOG, selectQuery);
	    //perform the query and store data in cursor
	    Cursor c = db.rawQuery(selectQuery, null);
	    //set cursor to beginning
	    if (c != null)
	        c.moveToFirst();
	    //create the instance of Games using cursor information
	    Games game = new Games();
	    game.setgid(c.getLong(c.getColumnIndex(KEY_G_ID)));
	    game.sethomeid((c.getLong(c.getColumnIndex(KEY_HOME_ID))));
	    game.setawayid((c.getLong(c.getColumnIndex(KEY_AWAY_ID))));
	    game.setDate(c.getString(c.getColumnIndex(KEY_DATE)));
	 
	    return game;
	}
	
	//Get all Games
	public List<Games> getAllGames() {
	    List<Games> games = new ArrayList<Games>();
	    SQLiteDatabase db = this.getReadableDatabase();
	    String selectQuery = "SELECT  * FROM " + TABLE_GAMES;
	 
	    Log.i(LOG, selectQuery);
	 
	    Cursor c = db.rawQuery(selectQuery, null);
	 
	    // looping through all rows and adding to list
	    if (c.moveToFirst()) {
	        do {
	        	Games game = new Games();
	    	    game.setgid(c.getLong(c.getColumnIndex(KEY_G_ID)));
	    	    game.sethomeid((c.getLong(c.getColumnIndex(KEY_HOME_ID))));
	    	    game.setawayid((c.getLong(c.getColumnIndex(KEY_AWAY_ID))));
	    	    game.setDate(c.getString(c.getColumnIndex(KEY_DATE)));
	            // adding to games list
	            games.add(game);
	        } while (c.moveToNext());
	    }
	 
	    return games;
	}
	
	// Delete a Game
	public void deleteGame(long g_id) {
		//deleteGameStats(g_id);
	    SQLiteDatabase db = this.getWritableDatabase();
	    db.delete(TABLE_GAMES, KEY_G_ID + " = ?",
	            new String[] { String.valueOf(g_id) });
	}
	
	
	// ----------------------- GAME_STATS table methods ------------------------- //
	
	public void createGameStats(long p_id, long g_id){
		SQLiteDatabase db = this.getWritableDatabase();
		 
        ContentValues values = new ContentValues();
        values.put(KEY_P_ID, p_id);
        values.put(KEY_G_ID, g_id);
        values.put(KEY_SHOTS, 0);
        values.put(KEY_SOG, 0);
        values.put(KEY_GOALS, 0);
        values.put(KEY_AST, 0);
        values.put(KEY_PEN_MINOR, 0);
        values.put(KEY_PEN_MAJOR, 0);
        values.put(KEY_SAVES,0);
        values.put(KEY_GOALS_ALLOWED, 0);
        //insert more stats here
        
        // insert row
        db.insert(TABLE_HOCKEY_GAME_STATS, null, values);
	}
	
	//get single game stats for single player
	public HockeyGameStats getPlayerGameStatsPlayers(long g_id, long p_id) {
	    SQLiteDatabase db = this.getReadableDatabase();
	    //create query to select game
	    String selectQuery = "SELECT  * FROM " + TABLE_HOCKEY_GAME_STATS + 
	    	" WHERE " + KEY_G_ID + " = " + g_id + 
	    	" AND " + KEY_P_ID + " = " + p_id;
	    
	    //Log the query
	    Log.i(LOG, selectQuery);
	    //perform the query and store data in cursor
	    Cursor c = db.rawQuery(selectQuery, null);
	    //set cursor to beginning
	    if (c != null)
	        c.moveToFirst();
	    //create the instance of Games using cursor information
	    HockeyGameStats stats = new HockeyGameStats();
	    stats.setgid(c.getLong(c.getColumnIndex(KEY_G_ID)));
	    stats.setpid(c.getLong(c.getColumnIndex(KEY_P_ID)));
	    stats.setshots((c.getInt(c.getColumnIndex(KEY_SHOTS))));
	    stats.setsog((c.getInt(c.getColumnIndex(KEY_SOG))));
	    stats.setgoals(c.getInt(c.getColumnIndex(KEY_GOALS)));
	    stats.setast((c.getInt(c.getColumnIndex(KEY_AST))));
	    stats.setpenminor((c.getInt(c.getColumnIndex(KEY_PEN_MINOR))));
	    stats.setpenmajor(c.getInt(c.getColumnIndex(KEY_PEN_MAJOR)));
	    stats.setsaves((c.getInt(c.getColumnIndex(KEY_SAVES))));
	    stats.setgoalsallowed((c.getInt(c.getColumnIndex(KEY_GOALS_ALLOWED))));

	    //Insert more stats here
	    
	    return stats;
	}
	
	//get single game stats for single player
	public int getPlayerGameStatPlayers(long g_id, long p_id, String stat) {
	    SQLiteDatabase db = this.getReadableDatabase();
	    //create query to select game
	    String selectQuery = "SELECT " + stat + " FROM " + TABLE_HOCKEY_GAME_STATS + 
	    	" WHERE " + KEY_G_ID + " = " + g_id + 
	    	" AND " + KEY_P_ID + " = " + p_id;
	    
	    //Log the query
	    Log.i(LOG, selectQuery);
	    //perform the query and store data in cursor
	    Cursor c = db.rawQuery(selectQuery, null);
	    //set cursor to beginning
	    if (c != null)
	        c.moveToFirst();
	    //create the instance of Games using cursor information
	    int stat_value = c.getInt(c.getColumnIndex(stat));

	    return stat_value;
	}
	
	//Get all GameStats for player
	public List<HockeyGameStats> getPlayerAllGameStatsPlayers(long p_id) {
	    List<HockeyGameStats> gameStats = new ArrayList<HockeyGameStats>();
	    SQLiteDatabase db = this.getReadableDatabase();
	    String selectQuery = "SELECT  * FROM " + TABLE_HOCKEY_GAME_STATS
	    		+ " WHERE " + KEY_P_ID + " = " + p_id ;
	 
	    Log.i(LOG, selectQuery);
	 
	    Cursor c = db.rawQuery(selectQuery, null);
	 
	    // looping through all rows and adding to list
	    if (c.moveToFirst()) {
	        do {
			    //create the instance of Games using cursor information
	    	    HockeyGameStats stats = new HockeyGameStats();
	    	    stats.setgid(c.getLong(c.getColumnIndex(KEY_G_ID)));
	    	    stats.setpid(c.getLong(c.getColumnIndex(KEY_P_ID)));
	    	    stats.setshots((c.getInt(c.getColumnIndex(KEY_SHOTS))));
	    	    stats.setsog((c.getInt(c.getColumnIndex(KEY_SOG))));
	    	    stats.setgoals(c.getInt(c.getColumnIndex(KEY_GOALS)));
	    	    stats.setast((c.getInt(c.getColumnIndex(KEY_AST))));
	    	    stats.setpenminor((c.getInt(c.getColumnIndex(KEY_PEN_MINOR))));
	    	    stats.setpenmajor(c.getInt(c.getColumnIndex(KEY_PEN_MAJOR)));
	    	    stats.setsaves((c.getInt(c.getColumnIndex(KEY_SAVES))));
	    	    stats.setgoalsallowed((c.getInt(c.getColumnIndex(KEY_GOALS_ALLOWED))));
			    //Insert more stats here
			    
	            // adding to gameStats list
	            gameStats.add(stats);
	        } while (c.moveToNext());
	    }
	 
	    return gameStats;
	}
	
	//Get all GameStats
	public List<HockeyGameStats> getAllGameStatsPlayers() {
	    List<HockeyGameStats> gameStats = new ArrayList<HockeyGameStats>();
	    SQLiteDatabase db = this.getReadableDatabase();
	    String selectQuery = "SELECT  * FROM " + TABLE_HOCKEY_GAME_STATS;
	 
	    Log.i(LOG, selectQuery);
	 
	    Cursor c = db.rawQuery(selectQuery, null);
	 
	    // looping through all rows and adding to list
	    if (c.moveToFirst()) {
	        do {
			    //create the instance of Games using cursor information
	    	    HockeyGameStats stats = new HockeyGameStats();
	    	    stats.setgid(c.getLong(c.getColumnIndex(KEY_G_ID)));
	    	    stats.setpid(c.getLong(c.getColumnIndex(KEY_P_ID)));
	    	    stats.setshots((c.getInt(c.getColumnIndex(KEY_SHOTS))));
	    	    stats.setsog((c.getInt(c.getColumnIndex(KEY_SOG))));
	    	    stats.setgoals(c.getInt(c.getColumnIndex(KEY_GOALS)));
	    	    stats.setast((c.getInt(c.getColumnIndex(KEY_AST))));
	    	    stats.setpenminor((c.getInt(c.getColumnIndex(KEY_PEN_MINOR))));
	    	    stats.setpenmajor(c.getInt(c.getColumnIndex(KEY_PEN_MAJOR)));
	    	    stats.setsaves((c.getInt(c.getColumnIndex(KEY_SAVES))));
	    	    stats.setgoalsallowed((c.getInt(c.getColumnIndex(KEY_GOALS_ALLOWED))));
			    //Insert more stats here
			    
	            // adding to gameStats list
	            gameStats.add(stats);
	        } while (c.moveToNext());
	    }
	 
	    return gameStats;
	}
	
	// Delete a GameStats
	public void deleteGameStatsPlayers(long g_id) {
	    SQLiteDatabase db = this.getWritableDatabase();
	    db.delete(TABLE_HOCKEY_GAME_STATS, KEY_G_ID + " = ?",
	            new String[] { String.valueOf(g_id) });
	}
	
	//ADDING STATS
	
	//Adding value to points category of a player
	public int addStatsPlayers(long g_id, long p_id, String stat, int value){
	    SQLiteDatabase db = this.getWritableDatabase();
	    HockeyGameStats stats = getPlayerGameStatsPlayers(g_id, p_id);
	    
	    int old_value = getPlayerGameStatPlayers(g_id,p_id,stat);
	    int new_value = old_value + value;
	    
	    ContentValues values = new ContentValues();
	    	
	    values.put(KEY_P_ID, p_id);
        values.put(KEY_G_ID, g_id);
	    if(stat==KEY_SHOTS)
	    	values.put(KEY_SHOTS, new_value);
	    else
	    	values.put(KEY_SHOTS, stats.getshots());
	    if(stat==KEY_SOG)
	    	values.put(KEY_SOG, new_value);
	    else
	    	values.put(KEY_SOG, stats.getsog());
	    if(stat==KEY_GOALS)
	    	values.put(KEY_GOALS, new_value);
	    else
	    	values.put(KEY_GOALS, stats.getgoals());
	    if(stat==KEY_AST)
	    	values.put(KEY_AST, new_value);
	    else
	    	values.put(KEY_AST, stats.getast());
	    if(stat==KEY_PEN_MINOR)
	    	values.put(KEY_PEN_MINOR, new_value);
	    else
	    	values.put(KEY_PEN_MINOR, stats.getpenminor());
	    if(stat==KEY_PEN_MAJOR)
	    	values.put(KEY_PEN_MAJOR, new_value);
	    else
	    	values.put(KEY_PEN_MAJOR, stats.getpenminor());
	    if(stat==KEY_SAVES)
	    	values.put(KEY_SAVES, new_value);
	    else
	    	values.put(KEY_SAVES, stats.getsaves());
	    if(stat==KEY_GOALS_ALLOWED)
	    	values.put(KEY_GOALS_ALLOWED, new_value);
	    else
	    	values.put(KEY_GOALS_ALLOWED, stats.getgoalsallowed());
        //insert more stats here
        
	    return db.update(TABLE_HOCKEY_GAME_STATS,  values, KEY_P_ID + " = " + p_id + " AND " + KEY_G_ID + " = " + g_id, null);
	}
	
	
// ----------------------- PLAY_BY_PLAY table method --------------------- //
	
	public long createPlayByPlay(PlayByPlay pbp){
		SQLiteDatabase db = this.getWritableDatabase();
		 
        ContentValues values = new ContentValues();
        values.put(KEY_G_ID, pbp.getgid());
        values.put(KEY_ACTION, pbp.getaction());
        values.put(KEY_TIME, pbp.gettime());
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
        
        if (c!=null)
        	c.moveToFirst();
        
        do {
        	//create the instance of  using cursor information
		    PlayByPlay pbp = new PlayByPlay();
		    pbp.setaid(c.getLong(c.getColumnIndex(KEY_A_ID)));
		    pbp.setgid(c.getLong(c.getColumnIndex(KEY_G_ID)));
		    pbp.setaction(c.getString(c.getColumnIndex(KEY_ACTION)));
		    pbp.settime(c.getString(c.getColumnIndex(KEY_TIME)));
		    pbp.sethomescore(c.getInt(c.getColumnIndex(KEY_HOME_SCORE)));
		    pbp.setawayscore(c.getInt(c.getColumnIndex(KEY_AWAY_SCORE)));
            // adding to playbyplay list
            pbps.add(pbp);
        } while(c.moveToNext());
        
        return pbps;
	}
	
	// Delete PlayByPlay of a game
	public void deletePlayByPlayGame(long g_id) {
	    SQLiteDatabase db = this.getWritableDatabase();
	    db.delete(TABLE_PLAY_BY_PLAY, KEY_G_ID + " = ?",
	            new String[] { String.valueOf(g_id) });
	}
	
	// ----------------------- PLAYERS table methods ------------------------- //

	public long createPlayers(Players player){
		SQLiteDatabase db = this.getWritableDatabase();
		 
        ContentValues values = new ContentValues();
        values.put(KEY_T_ID, player.gettid());
        values.put(KEY_P_NAME, player.getpname());
        values.put(KEY_P_NUM, player.getpnum());

        // insert row
        long p_id = db.insert(TABLE, null, values);
 
        return p_id;
	}
	
	//get single player
	public Players getPlayer(long p_id) {
	    SQLiteDatabase db = this.getReadableDatabase();
	    //create query to select game
	    String selectQuery = "SELECT  * FROM " + TABLE + 
	    	" WHERE " + KEY_P_ID + " = " + p_id;
	    //Log the query
	    Log.i(LOG, selectQuery);
	    //perform the query and store data in cursor
	    Cursor c = db.rawQuery(selectQuery, null);
	    //set cursor to beginning
	    if (c != null)
	        c.moveToFirst();
	    //create the instance of Teams using cursor information
	    Players player = new Players();
	    player.setpid(c.getLong(c.getColumnIndex(KEY_P_ID)));
	    player.settid(c.getLong(c.getColumnIndex(KEY_T_ID)));
	    player.setpname((c.getString(c.getColumnIndex(KEY_P_NAME))));
	    player.setpnum((c.getInt(c.getColumnIndex(KEY_P_NUM))));
	 
	    return player;
	}
	
	public List<Players> getPlayersTeam(long t_id){
	    SQLiteDatabase db = this.getReadableDatabase();
		List<Players> players = new ArrayList<Players>();
		String selectPlayerQuery = "SELECT * FROM " + TABLE + " WHERE " + KEY_T_ID + " = " + t_id;
        
        Log.i(LOG, selectPlayerQuery);
        
        Cursor c = db.rawQuery(selectPlayerQuery, null);
        
        if (c!=null)
        	c.moveToFirst();
        
        do {
        	//create the instance of  using cursor information
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
	
	public List<Players> getAllPlayers(){
	    SQLiteDatabase db = this.getReadableDatabase();
		List<Players> players = new ArrayList<Players>();
		String selectPlayerQuery = "SELECT * FROM " + TABLE;
        
        Log.i(LOG, selectPlayerQuery);
        
        Cursor c = db.rawQuery(selectPlayerQuery, null);
        
        if (c!=null)
        	c.moveToFirst();
        
        
        do {
        	//create the instance of  using cursor information
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
	    db.delete(TABLE, KEY_P_ID + " = " + p_id, null);
	}
		
	
	// Delete  on a team
	public void deletePlayers(long t_id) {
	    SQLiteDatabase db = this.getWritableDatabase();
	    db.delete(TABLE, KEY_T_ID + " = ?",
	            new String[] { String.valueOf(t_id) });
	}
	
	// -------------------SHOT_CHART_COORDS table methods ------------------ //
	
	//create a row of shot chart coordinates
	public long createShot(ShotChartCoords shot){
		SQLiteDatabase db = this.getWritableDatabase();
		 
        ContentValues values = new ContentValues();
        values.put(KEY_SHOT_ID, shot.getshotid());
        values.put(KEY_G_ID, shot.getgid());
        values.put(KEY_P_ID, shot.getpid());
        values.put(KEY_X, shot.getx());
        values.put(KEY_Y, shot.gety());
        values.put(KEY_MADE, shot.getmade());

        // insert row
        long row = db.insert(TABLE_SHOT_CHART_COORDS, null, values);
 
        return row;
	}
/*	
	public List<ShotChartCoords> getAllShots(){
	    SQLiteDatabase db = this.getReadableDatabase();
		List<ShotChartCoords> shots = new ArrayList<ShotChartCoords>();
		String selectQuery = "SELECT * FROM " + TABLE_SHOT_CHART_COORDS;
        
        Log.i(LOG, selectQuery);
        
        Cursor c = db.rawQuery(selectQuery, null);
        
        if (c!=null)
        	c.moveToFirst();
        
        do {
        	//create the instance of  using cursor information
		    ShotChartCoords shot = new ShotChartCoords();
		    shot.setaid(c.getLong(c.getColumnIndex(KEY_A_ID)));
		    shot.setgid((c.getLong(c.getColumnIndex(KEY_G_ID))));		    
		    shot.setpid((c.getLong(c.getColumnIndex(KEY_P_ID))));
		    shot.setx((c.getInt(c.getColumnIndex(KEY_X))));
		    shot.sety((c.getInt(c.getColumnIndex(KEY_Y))));
		    shot.setmade((c.getString(c.getColumnIndex(KEY_MADE))));

            // adding to players list
		    shots.add(shot);
        } while(c.moveToNext());
        
        return shots;
	}
	
	public List<ShotChartCoords> getAllTeamShots(long t_id){
	    SQLiteDatabase db = this.getReadableDatabase();
		List<ShotChartCoords> shots = new ArrayList<ShotChartCoords>();
		String selectQuery = "SELECT * FROM " + TABLE_SHOT_CHART_COORDS + " NATURAL JOIN " + TABLE 
				+ " WHERE " + KEY_T_ID + " = " + t_id;
        
        Log.i(LOG, selectQuery);
        
        Cursor c = db.rawQuery(selectQuery, null);
        
        if (c!=null)
        	c.moveToFirst();
        
        do {
        	//create the instance of  using cursor information
		    ShotChartCoords shot = new ShotChartCoords();
		    shot.setaid(c.getLong(c.getColumnIndex(KEY_A_ID)));
		    shot.setgid((c.getLong(c.getColumnIndex(KEY_G_ID))));		    
		    shot.setpid((c.getLong(c.getColumnIndex(KEY_P_ID))));
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
        	//create the instance of  using cursor information
		    ShotChartCoords shot = new ShotChartCoords();
		    shot.setaid(c.getLong(c.getColumnIndex(KEY_A_ID)));
		    shot.setgid((c.getLong(c.getColumnIndex(KEY_G_ID))));		    
		    shot.setpid((c.getLong(c.getColumnIndex(KEY_P_ID))));
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
		String selectQuery = "SELECT * FROM " + TABLE_SHOT_CHART_COORDS + " NATURAL JOIN " + TABLE  
				+ " WHERE " + KEY_T_ID + " = " + t_id + " AND " + KEY_G_ID + " = " + g_id;
        
        Log.i(LOG, selectQuery);
        
        Cursor c = db.rawQuery(selectQuery, null);
        
        if (c!=null)
        	c.moveToFirst();
        
        do {
        	//create the instance of  using cursor information
		    ShotChartCoords shot = new ShotChartCoords();
		    shot.setaid(c.getLong(c.getColumnIndex(KEY_A_ID)));
		    shot.setgid((c.getLong(c.getColumnIndex(KEY_G_ID))));		    
		    shot.setpid((c.getLong(c.getColumnIndex(KEY_P_ID))));
		    shot.setx((c.getInt(c.getColumnIndex(KEY_X))));
		    shot.sety((c.getInt(c.getColumnIndex(KEY_Y))));
		    shot.setmade((c.getString(c.getColumnIndex(KEY_MADE))));

            // adding to players list
		    shots.add(shot);
        } while(c.moveToNext());
        
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
        	//create the instance of  using cursor information
		    ShotChartCoords shot = new ShotChartCoords();
		    shot.setaid(c.getLong(c.getColumnIndex(KEY_A_ID)));
		    shot.setgid((c.getLong(c.getColumnIndex(KEY_G_ID))));		    
		    shot.setpid((c.getLong(c.getColumnIndex(KEY_P_ID))));
		    shot.setx((c.getInt(c.getColumnIndex(KEY_X))));
		    shot.sety((c.getInt(c.getColumnIndex(KEY_Y))));
		    shot.setmade((c.getString(c.getColumnIndex(KEY_MADE))));

            // adding to players list
		    shots.add(shot);
        } while(c.moveToNext());
        
        return shots;
	}
*/	
	// Delete a Shot
	public void deleteShot(long a_id) {
	    SQLiteDatabase db = this.getWritableDatabase();
	    db.delete(TABLE_SHOT_CHART_COORDS, KEY_A_ID + " = " + a_id, null);
	}
	
		
	// ----------------------- TEAMS table methods ------------------------- //

	public long createTeams(Teams team){
		SQLiteDatabase db = this.getWritableDatabase();
		 
        ContentValues values = new ContentValues();
        //values.put(KEY_T_ID, team.gettid());
        values.put(KEY_T_NAME, team.gettname());
        values.put(KEY_C_NAME, team.getcname());
        values.put(KEY_SPORT, team.getSport());

        // insert row
        long p_id = db.insert(TABLE_TEAMS, null, values);
 
        return p_id;
	}
	
	//get single team
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
        	//create the instance of  using cursor information
		    Teams team = new Teams();
		    team.settid(c.getLong(c.getColumnIndex(KEY_T_ID)));
		    team.settname((c.getString(c.getColumnIndex(KEY_T_NAME))));
		    team.setcname((c.getString(c.getColumnIndex(KEY_C_NAME))));
		    team.setsport((c.getString(c.getColumnIndex(KEY_SPORT))));
		   
            // adding to players list
		    teams.add(team);
        } while(c.moveToNext());
        
        return teams;
	}
	
	// Delete a Team
	public void deleteTeam(long t_id) {
		deletePlayers(t_id);
	    SQLiteDatabase db = this.getWritableDatabase();
	    db.delete(TABLE_TEAMS, KEY_T_ID + " = ?",
	            new String[] { String.valueOf(t_id) });
	}
	
	
	
	public void deleteAll(){
	    List<Games> games = getAllGames();
	    for(Games g: games){
	    	deleteGame(g.getgid());
	    	deletePlayByPlayGame(g.getgid());
	    }
	    
	    List<Teams> teams = getAllTeams();
	    for(Teams t: teams){
	    	deleteTeam(t.gettid());
	    }

	}
	
	
	
	
	// closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
}
