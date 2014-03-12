package com.seniordesign.ultimatescorecard.sqlite.football;


import java.util.ArrayList;
import java.util.List;

import com.seniordesign.ultimatescorecard.data.football.FootballPlayer;
import com.seniordesign.ultimatescorecard.sqlite.helper.DatabaseHelper;
import com.seniordesign.ultimatescorecard.sqlite.helper.Games;
import com.seniordesign.ultimatescorecard.sqlite.helper.PlayByPlay;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class FootballDatabaseHelper extends DatabaseHelper{

	// Logcat tag
    private static final String LOG = "FootballDatabaseHelper";
 
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "FootballStats";
	
    //Table Names
    private static final String TABLE_FOOTBALL_GAME_STATS_PASSING = "football_game_stats_passing";
    private static final String TABLE_FOOTBALL_GAME_STATS_RUSHING = "football_game_stats_rushing";
    private static final String TABLE_FOOTBALL_GAME_STATS_RECEIVING = "football_game_stats_receiving";
    private static final String TABLE_FOOTBALL_GAME_STATS_FUMBLES = "football_game_stats_fumbles";
    private static final String TABLE_FOOTBALL_GAME_STATS_DEFENSE = "football_game_stats_defense";
    private static final String TABLE_FOOTBALL_GAME_STATS_KICKING = "football_game_stats_kicking";
    private static final String TABLE_FOOTBALL_GAME_STATS_PUNTING = "football_game_stats_punting";
    private static final String TABLE_FOOTBALL_GAME_STATS_RETURNS= "football_game_stats_returns";

    //GAMES Table - column names
    
    
    //FOOTBALLGAMESTATS - common columns
    private static final String KEY_ATT = "att";
    private static final String KEY_YDS = "yds";
    private static final String KEY_TDS = "tds";
    private static final String KEY_TWOPC = "TWOPC";

    //FOOTBALLGAMESTATSPASSING Table - column names
    private static final String KEY_CMP = "cmp";
    private static final String KEY_INTS = "ints";
    
    //FOOTBALLGAMESTATSRUSHING Table - column names

    //FOOTBALLGAMESTATSRECEIVING Table - column names
    private static final String KEY_TGT = "tgt";
    private static final String KEY_REC = "rec";

    //FOOTBALLGAMESTATSBALLCARRIER Table - column names
    private static final String KEY_FMB = "fmb";
    private static final String KEY_FMBLOST = "fmblost";
    private static final String KEY_FF = "ff";
    private static final String KEY_FR = "fr";
    
    //FOOTBALLGAMESTATSDEFENSE Table - column names
    private static final String KEY_TACKLES = "tackles";
    private static final String KEY_TFL = "tfl";
    private static final String KEY_SACKS = "sacks";
    
    //FOOTBALLGAMESTATSKICKING Table - column names
    private static final String KEY_XPM = "xpm";
    private static final String KEY_XPA = "xpa";
    private static final String KEY_FGM = "fgm";
    private static final String KEY_FGA = "fga";

    //FOOTBALLGAMESTATSPUNTING Table - column names
    private static final String KEY_PUNTS = "punts";
    private static final String KEY_IN20 = "in20";
    private static final String KEY_TBS = "tbs";
    
    //FOOTBALLGAMESTATSRETURNS Table - column names
    private static final String KEY_KRT = "krt";
    private static final String KEY_KYDS = "kyds";
    private static final String KEY_KTDS = "ktds";
    private static final String KEY_PRT = "prt";
    private static final String KEY_PYDS = "pyds";
    private static final String KEY_PTDS = "ptds";

    //Table Create Statements
    //GAMES table create statement
    private static final String CREATE_TABLE_GAMES = "CREATE TABLE IF NOT EXISTS " + TABLE_GAMES 
    		+ "(" + KEY_G_ID + " INTEGER PRIMARY KEY," + KEY_HOME_ID + " INTEGER," 
    		+ KEY_AWAY_ID + " INTEGER," + KEY_DATE + " DATE" + ")"; 

    //FOOTBALL_GAME_STATS_PASSING table create statement
    private static final String CREATE_TABLE_FOOTBALL_GAME_STATS_PASSING = "CREATE TABLE IF NOT EXISTS " + TABLE_FOOTBALL_GAME_STATS_PASSING 
    		+ "(" + KEY_G_ID + " INTEGER" + ", " + KEY_P_ID + " INTEGER" + ", "
    		+ KEY_CMP + " INTEGER, " + KEY_ATT + " INTEGER, " + KEY_YDS + " INTEGER, "
    		+ KEY_TDS + " INTEGER, " + KEY_INTS + " INTEGER, " + KEY_TWOPC + " INTEGER"
    		+ ")"; 
    
    //FOOTBALL_GAME_STATS_RUSHING table create statement
    private static final String CREATE_TABLE_FOOTBALL_GAME_STATS_RUSHING = "CREATE TABLE IF NOT EXISTS " + TABLE_FOOTBALL_GAME_STATS_RUSHING 
    		+ "(" + KEY_G_ID + " INTEGER" + ", " + KEY_P_ID + " INTEGER" + ", "
    		+ KEY_ATT + " INTEGER, " + KEY_YDS + " INTEGER, "
    		+ KEY_TDS + " INTEGER, " + KEY_TWOPC + " INTEGER"
    		+ ")"; 
    
    //FOOTBALL_GAME_STATS_RECEIVING table create statement
    private static final String CREATE_TABLE_FOOTBALL_GAME_STATS_RECEIVING = "CREATE TABLE IF NOT EXISTS " + TABLE_FOOTBALL_GAME_STATS_RECEIVING 
    		+ "(" + KEY_G_ID + " INTEGER" + ", " + KEY_P_ID + " INTEGER" + ", "
    		+ KEY_TGT + " INTEGER, " + KEY_REC + " INTEGER, " + KEY_YDS + " INTEGER, "
    		+ KEY_TDS + " INTEGER, " + KEY_TWOPC + " INTEGER"
    		+ ")"; 
    
    //FOOTBALL_GAME_STATS_FUMBLES table create statement
    private static final String CREATE_TABLE_FOOTBALL_GAME_STATS_FUMBLES = "CREATE TABLE IF NOT EXISTS " + TABLE_FOOTBALL_GAME_STATS_FUMBLES 
    		+ "(" + KEY_G_ID + " INTEGER" + ", " + KEY_P_ID + " INTEGER" + ", "
    		+ KEY_FMB + " INTEGER, " + KEY_FMBLOST + " INTEGER, " + KEY_FF + " INTEGER, "
    		+ KEY_FR + " INTEGER"
    		+ ")"; 
    
    //FOOTBALL_GAME_STATS_DEFENSE table create statement
    private static final String CREATE_TABLE_FOOTBALL_GAME_STATS_DEFENSE = "CREATE TABLE IF NOT EXISTS " + TABLE_FOOTBALL_GAME_STATS_DEFENSE 
    		+ "(" + KEY_G_ID + " INTEGER" + ", " + KEY_P_ID + " INTEGER" + ", "
    		+ KEY_TACKLES + " INTEGER, " + KEY_TFL + " INTEGER, " + KEY_SACKS + " INTEGER, "
    		+ KEY_INTS + " INTEGER, " + KEY_TDS + " INTEGER"
    		+ ")"; 
    
    //FOOTBALL_GAME_STATS_KICKING table create statement
    private static final String CREATE_TABLE_FOOTBALL_GAME_STATS_KICKING = "CREATE TABLE IF NOT EXISTS " + TABLE_FOOTBALL_GAME_STATS_KICKING 
    		+ "(" + KEY_G_ID + " INTEGER" + ", " + KEY_P_ID + " INTEGER" + ", "
    		+ KEY_XPM + " INTEGER, " + KEY_XPA + " INTEGER, " + KEY_FGM + " INTEGER, "
    		+ KEY_FGA + " INTEGER"
    		+ ")"; 
    
    //FOOTBALL_GAME_STATS_PUNTING table create statement
    private static final String CREATE_TABLE_FOOTBALL_GAME_STATS_PUNTING = "CREATE TABLE IF NOT EXISTS " + TABLE_FOOTBALL_GAME_STATS_PUNTING 
    		+ "(" + KEY_G_ID + " INTEGER" + ", " + KEY_P_ID + " INTEGER" + ", "
    		+ KEY_PUNTS + " INTEGER, " + KEY_YDS + " INTEGER, " + KEY_IN20 + " INTEGER, "
    		+ KEY_TBS + " INTEGER"
    		+ ")"; 
    
    //FOOTBALL_GAME_STATS_RETURNS table create statement
    private static final String CREATE_TABLE_FOOTBALL_GAME_STATS_RETURNS = "CREATE TABLE IF NOT EXISTS " + TABLE_FOOTBALL_GAME_STATS_RETURNS 
    		+ "(" + KEY_G_ID + " INTEGER" + ", " + KEY_P_ID + " INTEGER" + ", "
    		+ KEY_KRT + " INTEGER, " + KEY_KYDS + " INTEGER, " + KEY_KTDS + " INTEGER, "
    		+ KEY_PRT + " INTEGER, " + KEY_PYDS + " INTEGER, " + KEY_PTDS + " INTEGER"
    		+ ")"; 
    
    public FootballDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
	public FootballDatabaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
        db.execSQL(CREATE_TABLE_GAMES);
        db.execSQL(CREATE_TABLE_PLAYERS);
        db.execSQL(CREATE_TABLE_TEAMS);
        db.execSQL(CREATE_TABLE_PLAY_BY_PLAY);
        db.execSQL(CREATE_TABLE_SHOT_CHART_COORDS);
        db.execSQL(CREATE_TABLE_FOOTBALL_GAME_STATS_PASSING);
        db.execSQL(CREATE_TABLE_FOOTBALL_GAME_STATS_RUSHING);
        db.execSQL(CREATE_TABLE_FOOTBALL_GAME_STATS_RECEIVING);
        db.execSQL(CREATE_TABLE_FOOTBALL_GAME_STATS_FUMBLES);
        db.execSQL(CREATE_TABLE_FOOTBALL_GAME_STATS_DEFENSE);
        db.execSQL(CREATE_TABLE_FOOTBALL_GAME_STATS_KICKING);
        db.execSQL(CREATE_TABLE_FOOTBALL_GAME_STATS_PUNTING);
        db.execSQL(CREATE_TABLE_FOOTBALL_GAME_STATS_RETURNS);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GAMES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEAMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAY_BY_PLAY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOT_CHART_COORDS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOOTBALL_GAME_STATS_PASSING);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOOTBALL_GAME_STATS_RUSHING);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOOTBALL_GAME_STATS_RECEIVING);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOOTBALL_GAME_STATS_FUMBLES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOOTBALL_GAME_STATS_DEFENSE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOOTBALL_GAME_STATS_KICKING);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOOTBALL_GAME_STATS_PUNTING);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOOTBALL_GAME_STATS_RETURNS);

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
 /*      
        List<Players> home_players = getPlayersTeam(game.gethomeid());
        List<Players> away_players = getPlayersTeam(game.getawayid());

        for(Players player : home_players){
        	createGameStats(player.getpid(), g_id);
        }
        for(Players player : away_players){
        	createGameStats(player.getpid(), g_id);
        }
 */
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
	// Get all games played by a team
	public List<Games> getAllGamesTeam(long t_id) {
	    List<Games> games = new ArrayList<Games>();
	    SQLiteDatabase db = this.getReadableDatabase();
	    String selectQuery = "SELECT  * FROM " + TABLE_GAMES 
	    		+ " WHERE " + KEY_HOME_ID + " = " + t_id 
	    		+ " OR " + KEY_AWAY_ID + " = " + t_id;
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
	
	// ----------------------- FOOTBALL_GAME_STATS_DEFENSE ---------------------------- //

	public void createGameStatsDefense(long p_id, long g_id){
		SQLiteDatabase db = this.getWritableDatabase();
		 
        ContentValues values = new ContentValues();
        values.put(KEY_P_ID, p_id);
        values.put(KEY_G_ID, g_id);
        values.put(KEY_TACKLES, 0);
        values.put(KEY_TFL, 0);
        values.put(KEY_SACKS, 0);
        values.put(KEY_INTS, 0);
        values.put(KEY_TDS, 0);
        //insert more stats here
        
        // insert row
        db.insert(TABLE_FOOTBALL_GAME_STATS_DEFENSE, null, values);
	}
	
	//get single defense game stats for single player
	public FootballGameStatsDefense getPlayerGameStatsDefense(long g_id, long p_id) {
	    SQLiteDatabase db = this.getReadableDatabase();
	    //create query to select game
	    String selectQuery = "SELECT  * FROM " + TABLE_FOOTBALL_GAME_STATS_DEFENSE + 
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
	    FootballGameStatsDefense stats = new FootballGameStatsDefense();
	    stats.setgid(c.getLong(c.getColumnIndex(KEY_G_ID)));
	    stats.setpid(c.getLong(c.getColumnIndex(KEY_P_ID)));
	    stats.settackles((c.getInt(c.getColumnIndex(KEY_TACKLES))));
	    stats.settfl((c.getInt(c.getColumnIndex(KEY_TFL))));
	    stats.setsacks(c.getInt(c.getColumnIndex(KEY_SACKS)));
	    stats.setints(c.getInt(c.getColumnIndex(KEY_INTS)));
	    stats.settds(c.getInt(c.getColumnIndex(KEY_TDS)));
	    //Insert more stats here
	    
	    return stats;
	}
	
	//get single game stats for single player
	public int getPlayerGameStatDefense(long g_id, long p_id, String stat) {
	    SQLiteDatabase db = this.getReadableDatabase();
	    //create query to select game
	    String selectQuery = "SELECT " + stat + " FROM " + TABLE_FOOTBALL_GAME_STATS_DEFENSE + 
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
	public List<FootballGameStatsDefense> getPlayerAllGameStatsDefense(long p_id) {
	    List<FootballGameStatsDefense> gameStats = new ArrayList<FootballGameStatsDefense>();
	    SQLiteDatabase db = this.getReadableDatabase();
	    String selectQuery = "SELECT  * FROM " + TABLE_FOOTBALL_GAME_STATS_DEFENSE
	    		+ " WHERE " + KEY_P_ID + " = " + p_id ;
	 
	    Log.i(LOG, selectQuery);
	 
	    Cursor c = db.rawQuery(selectQuery, null);
	 
	    // looping through all rows and adding to list
	    if (c.moveToFirst()) {
	        do {
			    //create the instance of Games using cursor information
			    FootballGameStatsDefense stats = new FootballGameStatsDefense();
			    stats.setgid(c.getLong(c.getColumnIndex(KEY_G_ID)));
			    stats.setpid(c.getLong(c.getColumnIndex(KEY_P_ID)));
			    stats.settackles((c.getInt(c.getColumnIndex(KEY_TACKLES))));
			    stats.settfl((c.getInt(c.getColumnIndex(KEY_TFL))));
			    stats.setsacks(c.getInt(c.getColumnIndex(KEY_SACKS)));
			    stats.setints(c.getInt(c.getColumnIndex(KEY_INTS)));
			    stats.settds(c.getInt(c.getColumnIndex(KEY_TDS)));
			    //Insert more stats here
			    
	            // adding to gameStats list
	            gameStats.add(stats);
	        } while (c.moveToNext());
	    }
	 
	    return gameStats;
	}
	
	//Get all GameStats
	public List<FootballGameStatsDefense> getAllGameStatsDefense() {
	    List<FootballGameStatsDefense> gameStats = new ArrayList<FootballGameStatsDefense>();
	    SQLiteDatabase db = this.getReadableDatabase();
	    String selectQuery = "SELECT  * FROM " + TABLE_FOOTBALL_GAME_STATS_DEFENSE;
	 
	    Log.i(LOG, selectQuery);
	 
	    Cursor c = db.rawQuery(selectQuery, null);
	 
	    // looping through all rows and adding to list
	    if (c.moveToFirst()) {
	        do {
			    //create the instance of Games using cursor information
			    FootballGameStatsDefense stats = new FootballGameStatsDefense();
			    stats.setgid(c.getLong(c.getColumnIndex(KEY_G_ID)));
			    stats.setpid(c.getLong(c.getColumnIndex(KEY_P_ID)));
			    stats.settackles((c.getInt(c.getColumnIndex(KEY_TACKLES))));
			    stats.settfl((c.getInt(c.getColumnIndex(KEY_TFL))));
			    stats.setsacks(c.getInt(c.getColumnIndex(KEY_SACKS)));
			    stats.setints(c.getInt(c.getColumnIndex(KEY_INTS)));
			    stats.settds(c.getInt(c.getColumnIndex(KEY_TDS)));
			    //Insert more stats here
			    
	            // adding to gameStats list
	            gameStats.add(stats);
	        } while (c.moveToNext());
	    }
	 
	    return gameStats;
	}
	
	// Delete a GameStats
	public void deleteGameStatsDefense(long g_id) {
	    SQLiteDatabase db = this.getWritableDatabase();
	    db.delete(TABLE_FOOTBALL_GAME_STATS_DEFENSE, KEY_G_ID + " = ?",
	            new String[] { String.valueOf(g_id) });
	}
	
	//ADDING STATS
	
	//Adding value to points category of a player
	public int addStatsDefense(long g_id, long p_id, String stat, int value){
	    SQLiteDatabase db = this.getWritableDatabase();
	    FootballGameStatsDefense stats = getPlayerGameStatsDefense(g_id, p_id);
	    
	    int old_value = getPlayerGameStatDefense(g_id,p_id,stat);
	    int new_value = old_value + value;
	    
	    ContentValues values = new ContentValues();
	    	
	    values.put(KEY_P_ID, p_id);
        values.put(KEY_G_ID, g_id);
	    if(stat==KEY_TACKLES)
	    	values.put(KEY_TACKLES, new_value);
	    else
	    	values.put(KEY_TACKLES, stats.gettackles());
	    if(stat==KEY_TFL)
	    	values.put(KEY_TFL, new_value);
	    else
	    	values.put(KEY_TFL, stats.gettfl());
	    if(stat==KEY_SACKS)
	    	values.put(KEY_SACKS, new_value);
	    else
	    	values.put(KEY_SACKS, stats.getsacks());
	    if(stat==KEY_INTS)
	    	values.put(KEY_INTS, new_value);
	    else
	    	values.put(KEY_INTS, stats.getints());
	    if(stat==KEY_TDS)
	    	values.put(KEY_TDS, new_value);
	    else
	    	values.put(KEY_TDS, stats.gettds());
        //insert more stats here
        
	    return db.update(TABLE_FOOTBALL_GAME_STATS_DEFENSE,  values, KEY_P_ID + " = " + p_id + " AND " + KEY_G_ID + " = " + g_id, null);
	}
	
	// ----------------------- FOOTBALL_GAME_STATS_FUMBLES ---------------------------- //

	public void createGameStatsFumbles(long p_id, long g_id){
		SQLiteDatabase db = this.getWritableDatabase();
		 
        ContentValues values = new ContentValues();
        values.put(KEY_P_ID, p_id);
        values.put(KEY_G_ID, g_id);
        values.put(KEY_FMB, 0);
        values.put(KEY_FMBLOST, 0);
        values.put(KEY_FF, 0);
        values.put(KEY_FR, 0);
        //insert more stats here
        
        // insert row
        db.insert(TABLE_FOOTBALL_GAME_STATS_FUMBLES, null, values);
	}
	
	//get single defense game stats for single player
	public FootballGameStatsFumbles getPlayerGameStatsFumbles(long g_id, long p_id) {
	    SQLiteDatabase db = this.getReadableDatabase();
	    //create query to select game
	    String selectQuery = "SELECT  * FROM " + TABLE_FOOTBALL_GAME_STATS_FUMBLES + 
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
	    FootballGameStatsFumbles stats = new FootballGameStatsFumbles();
	    stats.setgid(c.getLong(c.getColumnIndex(KEY_G_ID)));
	    stats.setpid(c.getLong(c.getColumnIndex(KEY_P_ID)));
	    stats.setfmb((c.getInt(c.getColumnIndex(KEY_FMB))));
	    stats.setfmblost((c.getInt(c.getColumnIndex(KEY_FMBLOST))));
	    stats.setff(c.getInt(c.getColumnIndex(KEY_FF)));
	    stats.setfr(c.getInt(c.getColumnIndex(KEY_FR)));
	    //Insert more stats here
	    
	    return stats;
	}
	
	//get single game stats for single player
	public int getPlayerGameStatFumbles(long g_id, long p_id, String stat) {
	    SQLiteDatabase db = this.getReadableDatabase();
	    //create query to select game
	    String selectQuery = "SELECT " + stat + " FROM " + TABLE_FOOTBALL_GAME_STATS_FUMBLES + 
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
	public List<FootballGameStatsFumbles> getPlayerAllGameStats(long p_id) {
	    List<FootballGameStatsFumbles> gameStats = new ArrayList<FootballGameStatsFumbles>();
	    SQLiteDatabase db = this.getReadableDatabase();
	    String selectQuery = "SELECT  * FROM " + TABLE_FOOTBALL_GAME_STATS_FUMBLES
	    		+ " WHERE " + KEY_P_ID + " = " + p_id ;
	 
	    Log.i(LOG, selectQuery);
	 
	    Cursor c = db.rawQuery(selectQuery, null);
	 
	    // looping through all rows and adding to list
	    if (c.moveToFirst()) {
	        do {
			    //create the instance of Games using cursor information
			    FootballGameStatsFumbles stats = new FootballGameStatsFumbles();
			    stats.setgid(c.getLong(c.getColumnIndex(KEY_G_ID)));
			    stats.setpid(c.getLong(c.getColumnIndex(KEY_P_ID)));
			    stats.setfmb((c.getInt(c.getColumnIndex(KEY_FMB))));
			    stats.setfmblost((c.getInt(c.getColumnIndex(KEY_FMBLOST))));
			    stats.setff(c.getInt(c.getColumnIndex(KEY_FF)));
			    stats.setfr(c.getInt(c.getColumnIndex(KEY_FR)));
			    //Insert more stats here
			    
	            // adding to gameStats list
	            gameStats.add(stats);
	        } while (c.moveToNext());
	    }
	 
	    return gameStats;
	}
	
	//Get all GameStats
	public List<FootballGameStatsFumbles> getAllGameStatsFumbles() {
	    List<FootballGameStatsFumbles> gameStats = new ArrayList<FootballGameStatsFumbles>();
	    SQLiteDatabase db = this.getReadableDatabase();
	    String selectQuery = "SELECT  * FROM " + TABLE_FOOTBALL_GAME_STATS_FUMBLES;
	 
	    Log.i(LOG, selectQuery);
	 
	    Cursor c = db.rawQuery(selectQuery, null);
	 
	    // looping through all rows and adding to list
	    if (c.moveToFirst()) {
	        do {
			    //create the instance of Games using cursor information
			    FootballGameStatsFumbles stats = new FootballGameStatsFumbles();
			    stats.setgid(c.getLong(c.getColumnIndex(KEY_G_ID)));
			    stats.setpid(c.getLong(c.getColumnIndex(KEY_P_ID)));
			    stats.setfmb((c.getInt(c.getColumnIndex(KEY_FMB))));
			    stats.setfmblost((c.getInt(c.getColumnIndex(KEY_FMBLOST))));
			    stats.setff(c.getInt(c.getColumnIndex(KEY_FF)));
			    stats.setfr(c.getInt(c.getColumnIndex(KEY_FR)));
			    //Insert more stats here
			    
	            // adding to gameStats list
	            gameStats.add(stats);
	        } while (c.moveToNext());
	    }
	 
	    return gameStats;
	}
	
	// Delete a GameStats
	public void deleteGameStatsFumbles(long g_id) {
	    SQLiteDatabase db = this.getWritableDatabase();
	    db.delete(TABLE_FOOTBALL_GAME_STATS_FUMBLES, KEY_G_ID + " = ?",
	            new String[] { String.valueOf(g_id) });
	}
	
	//ADDING STATS
	
	//Adding value to points category of a player
	public int addStatsFumbles(long g_id, long p_id, String stat, int value){
	    SQLiteDatabase db = this.getWritableDatabase();
	    FootballGameStatsFumbles stats = getPlayerGameStatsFumbles(g_id, p_id);
	    
	    int old_value = getPlayerGameStatFumbles(g_id,p_id,stat);
	    int new_value = old_value + value;
	    
	    ContentValues values = new ContentValues();
	    	
	    values.put(KEY_P_ID, p_id);
        values.put(KEY_G_ID, g_id);
	    if(stat==KEY_FMB)
	    	values.put(KEY_FMB, new_value);
	    else
	    	values.put(KEY_FMB, stats.getfmb());
	    if(stat==KEY_FMBLOST)
	    	values.put(KEY_FMBLOST, new_value);
	    else
	    	values.put(KEY_FMBLOST, stats.getfmblost());
	    if(stat==KEY_FF)
	    	values.put(KEY_FF, new_value);
	    else
	    	values.put(KEY_FF, stats.getff());
	    if(stat==KEY_FR)
	    	values.put(KEY_FR, new_value);
	    else
	    	values.put(KEY_FR, stats.getfr());
        //insert more stats here
        
	    return db.update(TABLE_FOOTBALL_GAME_STATS_FUMBLES,  values, KEY_P_ID + " = " + p_id + " AND " + KEY_G_ID + " = " + g_id, null);
	}

	// ----------------------- FOOTBALL_GAME_STATS_KICKING ---------------------------- //

	public void createGameStatsKicking(long p_id, long g_id){
		SQLiteDatabase db = this.getWritableDatabase();
		 
        ContentValues values = new ContentValues();
        values.put(KEY_P_ID, p_id);
        values.put(KEY_G_ID, g_id);
        values.put(KEY_XPM, 0);
        values.put(KEY_XPA, 0);
        values.put(KEY_FGM, 0);
        values.put(KEY_FGA, 0);
        //insert more stats here
        
        // insert row
        db.insert(TABLE_FOOTBALL_GAME_STATS_KICKING, null, values);
	}
	
	//get single defense game stats for single player
	public FootballGameStatsKicking getPlayerGameStatsKicking(long g_id, long p_id) {
	    SQLiteDatabase db = this.getReadableDatabase();
	    //create query to select game
	    String selectQuery = "SELECT  * FROM " + TABLE_FOOTBALL_GAME_STATS_KICKING + 
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
	    FootballGameStatsKicking stats = new FootballGameStatsKicking();
	    stats.setgid(c.getLong(c.getColumnIndex(KEY_G_ID)));
	    stats.setpid(c.getLong(c.getColumnIndex(KEY_P_ID)));
	    stats.setxpm((c.getInt(c.getColumnIndex(KEY_XPM))));
	    stats.setxpa((c.getInt(c.getColumnIndex(KEY_XPA))));
	    stats.setfgm(c.getInt(c.getColumnIndex(KEY_FGM)));
	    stats.setfga(c.getInt(c.getColumnIndex(KEY_FGA)));
	    //Insert more stats here
	    
	    return stats;
	}
	
	//get single game stats for single player
	public int getPlayerGameStatKicking(long g_id, long p_id, String stat) {
	    SQLiteDatabase db = this.getReadableDatabase();
	    //create query to select game
	    String selectQuery = "SELECT " + stat + " FROM " + TABLE_FOOTBALL_GAME_STATS_KICKING + 
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
	public List<FootballGameStatsKicking> getPlayerAllGameStatsKicking(long p_id) {
	    List<FootballGameStatsKicking> gameStats = new ArrayList<FootballGameStatsKicking>();
	    SQLiteDatabase db = this.getReadableDatabase();
	    String selectQuery = "SELECT  * FROM " + TABLE_FOOTBALL_GAME_STATS_KICKING
	    		+ " WHERE " + KEY_P_ID + " = " + p_id ;
	 
	    Log.i(LOG, selectQuery);
	 
	    Cursor c = db.rawQuery(selectQuery, null);
	 
	    // looping through all rows and adding to list
	    if (c.moveToFirst()) {
	        do {
			    //create the instance of Games using cursor information
			    FootballGameStatsKicking stats = new FootballGameStatsKicking();
			    stats.setgid(c.getLong(c.getColumnIndex(KEY_G_ID)));
			    stats.setpid(c.getLong(c.getColumnIndex(KEY_P_ID)));
			    stats.setxpm((c.getInt(c.getColumnIndex(KEY_XPM))));
			    stats.setxpa((c.getInt(c.getColumnIndex(KEY_XPA))));
			    stats.setfgm(c.getInt(c.getColumnIndex(KEY_FGM)));
			    stats.setfga(c.getInt(c.getColumnIndex(KEY_FGA)));
			    //Insert more stats here
			    
	            // adding to gameStats list
	            gameStats.add(stats);
	        } while (c.moveToNext());
	    }
	 
	    return gameStats;
	}
	
	//Get all GameStats
	public List<FootballGameStatsKicking> getAllGameStatsKicking() {
	    List<FootballGameStatsKicking> gameStats = new ArrayList<FootballGameStatsKicking>();
	    SQLiteDatabase db = this.getReadableDatabase();
	    String selectQuery = "SELECT  * FROM " + TABLE_FOOTBALL_GAME_STATS_KICKING;
	 
	    Log.i(LOG, selectQuery);
	 
	    Cursor c = db.rawQuery(selectQuery, null);
	 
	    // looping through all rows and adding to list
	    if (c.moveToFirst()) {
	        do {
			    //create the instance of Games using cursor information
			    FootballGameStatsKicking stats = new FootballGameStatsKicking();
			    stats.setgid(c.getLong(c.getColumnIndex(KEY_G_ID)));
			    stats.setpid(c.getLong(c.getColumnIndex(KEY_P_ID)));
			    stats.setxpm((c.getInt(c.getColumnIndex(KEY_XPM))));
			    stats.setxpa((c.getInt(c.getColumnIndex(KEY_XPA))));
			    stats.setfgm(c.getInt(c.getColumnIndex(KEY_FGM)));
			    stats.setfga(c.getInt(c.getColumnIndex(KEY_FGA)));
			    //Insert more stats here
			    
	            // adding to gameStats list
	            gameStats.add(stats);
	        } while (c.moveToNext());
	    }
	 
	    return gameStats;
	}
	
	// Delete a GameStats
	public void deleteGameStatsKicking(long g_id) {
	    SQLiteDatabase db = this.getWritableDatabase();
	    db.delete(TABLE_FOOTBALL_GAME_STATS_KICKING, KEY_G_ID + " = ?",
	            new String[] { String.valueOf(g_id) });
	}
	
	//ADDING STATS
	
	//Adding value to points category of a player
	public int addStatsKicking(long g_id, long p_id, String stat, int value){
	    SQLiteDatabase db = this.getWritableDatabase();
	    FootballGameStatsKicking stats = getPlayerGameStatsKicking(g_id, p_id);
	    
	    int old_value = getPlayerGameStatKicking(g_id,p_id,stat);
	    int new_value = old_value + value;
	    
	    ContentValues values = new ContentValues();
	    	
	    values.put(KEY_P_ID, p_id);
        values.put(KEY_G_ID, g_id);
	    if(stat==KEY_XPM)
	    	values.put(KEY_XPM, new_value);
	    else
	    	values.put(KEY_XPM, stats.getxpm());
	    if(stat==KEY_XPA)
	    	values.put(KEY_XPA, new_value);
	    else
	    	values.put(KEY_XPA, stats.getxpa());
	    if(stat==KEY_FGM)
	    	values.put(KEY_FGM, new_value);
	    else
	    	values.put(KEY_FGM, stats.getfgm());
	    if(stat==KEY_FGA)
	    	values.put(KEY_FGA, new_value);
	    else
	    	values.put(KEY_FGA, stats.getfga());
        //insert more stats here
        
	    return db.update(TABLE_FOOTBALL_GAME_STATS_KICKING,  values, KEY_P_ID + " = " + p_id + " AND " + KEY_G_ID + " = " + g_id, null);
	}	
	
	// ----------------------- FOOTBALL_GAME_STATS_PASSING ---------------------------- //

	public void createGameStatsPassing(long p_id, long g_id){
		SQLiteDatabase db = this.getWritableDatabase();
		 
        ContentValues values = new ContentValues();
        values.put(KEY_P_ID, p_id);
        values.put(KEY_G_ID, g_id);
        values.put(KEY_CMP, 0);
        values.put(KEY_ATT, 0);
        values.put(KEY_YDS, 0);
        values.put(KEY_TDS, 0);
        values.put(KEY_INTS, 0);
        values.put(KEY_TWOPC, 0);
        //insert more stats here
        
        // insert row
        db.insert(TABLE_FOOTBALL_GAME_STATS_PASSING, null, values);
	}
	
	//get single defense game stats for single player
	public FootballGameStatsPassing getPlayerGameStatsPassing(long g_id, long p_id) {
	    SQLiteDatabase db = this.getReadableDatabase();
	    //create query to select game
	    String selectQuery = "SELECT  * FROM " + TABLE_FOOTBALL_GAME_STATS_PASSING + 
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
	    FootballGameStatsPassing stats = new FootballGameStatsPassing();
	    stats.setgid(c.getLong(c.getColumnIndex(KEY_G_ID)));
	    stats.setpid(c.getLong(c.getColumnIndex(KEY_P_ID)));
	    stats.setcmp((c.getInt(c.getColumnIndex(KEY_CMP))));
	    stats.setatt((c.getInt(c.getColumnIndex(KEY_ATT))));
	    stats.setyds(c.getInt(c.getColumnIndex(KEY_YDS)));
	    stats.settds(c.getInt(c.getColumnIndex(KEY_TDS)));
	    stats.setints(c.getInt(c.getColumnIndex(KEY_INTS)));
	    stats.settwopc(c.getInt(c.getColumnIndex(KEY_TWOPC)));
	    //Insert more stats here
	    
	    return stats;
	}
	
	//get single game stats for single player
	public int getPlayerGameStatPassing(long g_id, long p_id, String stat) {
	    SQLiteDatabase db = this.getReadableDatabase();
	    //create query to select game
	    String selectQuery = "SELECT " + stat + " FROM " + TABLE_FOOTBALL_GAME_STATS_PASSING + 
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
	public List<FootballGameStatsPassing> getPlayerAllGameStatsPassing(long p_id) {
	    List<FootballGameStatsPassing> gameStats = new ArrayList<FootballGameStatsPassing>();
	    SQLiteDatabase db = this.getReadableDatabase();
	    String selectQuery = "SELECT  * FROM " + TABLE_FOOTBALL_GAME_STATS_PASSING
	    		+ " WHERE " + KEY_P_ID + " = " + p_id ;
	 
	    Log.i(LOG, selectQuery);
	 
	    Cursor c = db.rawQuery(selectQuery, null);
	 
	    // looping through all rows and adding to list
	    if (c.moveToFirst()) {
	        do {
			    //create the instance of Games using cursor information
			    FootballGameStatsPassing stats = new FootballGameStatsPassing();
			    stats.setgid(c.getLong(c.getColumnIndex(KEY_G_ID)));
			    stats.setpid(c.getLong(c.getColumnIndex(KEY_P_ID)));
			    stats.setcmp((c.getInt(c.getColumnIndex(KEY_CMP))));
			    stats.setatt((c.getInt(c.getColumnIndex(KEY_ATT))));
			    stats.setyds(c.getInt(c.getColumnIndex(KEY_YDS)));
			    stats.settds(c.getInt(c.getColumnIndex(KEY_TDS)));
			    stats.setints(c.getInt(c.getColumnIndex(KEY_INTS)));
			    stats.settwopc(c.getInt(c.getColumnIndex(KEY_TWOPC)));
			    //Insert more stats here
			    
	            // adding to gameStats list
	            gameStats.add(stats);
	        } while (c.moveToNext());
	    }
	 
	    return gameStats;
	}
	
	//Get all GameStats
	public List<FootballGameStatsPassing> getAllGameStatsPassing() {
	    List<FootballGameStatsPassing> gameStats = new ArrayList<FootballGameStatsPassing>();
	    SQLiteDatabase db = this.getReadableDatabase();
	    String selectQuery = "SELECT  * FROM " + TABLE_FOOTBALL_GAME_STATS_PASSING;
	 
	    Log.i(LOG, selectQuery);
	 
	    Cursor c = db.rawQuery(selectQuery, null);
	 
	    // looping through all rows and adding to list
	    if (c.moveToFirst()) {
	        do {
			    //create the instance of Games using cursor information
			    FootballGameStatsPassing stats = new FootballGameStatsPassing();
			    stats.setgid(c.getLong(c.getColumnIndex(KEY_G_ID)));
			    stats.setpid(c.getLong(c.getColumnIndex(KEY_P_ID)));
			    stats.setcmp((c.getInt(c.getColumnIndex(KEY_CMP))));
			    stats.setatt((c.getInt(c.getColumnIndex(KEY_ATT))));
			    stats.setyds(c.getInt(c.getColumnIndex(KEY_YDS)));
			    stats.settds(c.getInt(c.getColumnIndex(KEY_TDS)));
			    stats.setints(c.getInt(c.getColumnIndex(KEY_INTS)));
			    stats.settwopc(c.getInt(c.getColumnIndex(KEY_TWOPC)));
			    //Insert more stats here
			    
	            // adding to gameStats list
	            gameStats.add(stats);
	        } while (c.moveToNext());
	    }
	 
	    return gameStats;
	}
	
	// Delete a GameStats
	public void deleteGameStatsPassing(long g_id) {
	    SQLiteDatabase db = this.getWritableDatabase();
	    db.delete(TABLE_FOOTBALL_GAME_STATS_PASSING, KEY_G_ID + " = ?",
	            new String[] { String.valueOf(g_id) });
	}
	
	//ADDING STATS
	
	//Adding value to points category of a player
	public int addStatsPassing(long g_id, long p_id, String stat, int value){
	    SQLiteDatabase db = this.getWritableDatabase();
	    FootballGameStatsPassing stats = getPlayerGameStatsPassing(g_id, p_id);
	    
	    int old_value = getPlayerGameStatPassing(g_id,p_id,stat);
	    int new_value = old_value + value;
	    
	    ContentValues values = new ContentValues();
	    	
	    values.put(KEY_P_ID, p_id);
        values.put(KEY_G_ID, g_id);
	    if(stat==KEY_CMP)
	    	values.put(KEY_CMP, new_value);
	    else
	    	values.put(KEY_CMP, stats.getcmp());
	    if(stat==KEY_ATT)
	    	values.put(KEY_ATT, new_value);
	    else
	    	values.put(KEY_ATT, stats.getatt());
	    if(stat==KEY_YDS)
	    	values.put(KEY_YDS, new_value);
	    else
	    	values.put(KEY_YDS, stats.getyds());
	    if(stat==KEY_TDS)
	    	values.put(KEY_TDS, new_value);
	    else
	    	values.put(KEY_TDS, stats.gettds());
	    if(stat==KEY_INTS)
	    	values.put(KEY_INTS, new_value);
	    else
	    	values.put(KEY_INTS, stats.getints());
	    if(stat==KEY_TWOPC)
	    	values.put(KEY_TWOPC, new_value);
	    else
	    	values.put(KEY_TWOPC, stats.gettwopc());
        //insert more stats here
        
	    return db.update(TABLE_FOOTBALL_GAME_STATS_PASSING,  values, KEY_P_ID + " = " + p_id + " AND " + KEY_G_ID + " = " + g_id, null);
	}
	
	// ----------------------- FOOTBALL_GAME_STATS_PUNTING ---------------------------- //

	public void createGameStatsPunting(long p_id, long g_id){
		SQLiteDatabase db = this.getWritableDatabase();
		 
        ContentValues values = new ContentValues();
        values.put(KEY_P_ID, p_id);
        values.put(KEY_G_ID, g_id);
        values.put(KEY_PUNTS, 0);
        values.put(KEY_YDS, 0);
        values.put(KEY_IN20, 0);
        values.put(KEY_TBS, 0);
        //insert more stats here
        
        // insert row
        db.insert(TABLE_FOOTBALL_GAME_STATS_PUNTING, null, values);
	}
	
	//get single defense game stats for single player
	public FootballGameStatsPunting getPlayerGameStatsPunting(long g_id, long p_id) {
	    SQLiteDatabase db = this.getReadableDatabase();
	    //create query to select game
	    String selectQuery = "SELECT  * FROM " + TABLE_FOOTBALL_GAME_STATS_PUNTING + 
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
	    FootballGameStatsPunting stats = new FootballGameStatsPunting();
	    stats.setgid(c.getLong(c.getColumnIndex(KEY_G_ID)));
	    stats.setpid(c.getLong(c.getColumnIndex(KEY_P_ID)));
	    stats.setpunts((c.getInt(c.getColumnIndex(KEY_PUNTS))));
	    stats.setyds((c.getInt(c.getColumnIndex(KEY_YDS))));
	    stats.setin20(c.getInt(c.getColumnIndex(KEY_IN20)));
	    stats.settbs(c.getInt(c.getColumnIndex(KEY_TBS)));
	    //Insert more stats here
	    
	    return stats;
	}
	
	//get single game stats for single player
	public int getPlayerGameStatPunting(long g_id, long p_id, String stat) {
	    SQLiteDatabase db = this.getReadableDatabase();
	    //create query to select game
	    String selectQuery = "SELECT " + stat + " FROM " + TABLE_FOOTBALL_GAME_STATS_PUNTING + 
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
	public List<FootballGameStatsPunting> getPlayerAllGameStatsPunting(long p_id) {
	    List<FootballGameStatsPunting> gameStats = new ArrayList<FootballGameStatsPunting>();
	    SQLiteDatabase db = this.getReadableDatabase();
	    String selectQuery = "SELECT  * FROM " + TABLE_FOOTBALL_GAME_STATS_PUNTING
	    		+ " WHERE " + KEY_P_ID + " = " + p_id ;
	 
	    Log.i(LOG, selectQuery);
	 
	    Cursor c = db.rawQuery(selectQuery, null);
	 
	    // looping through all rows and adding to list
	    if (c.moveToFirst()) {
	        do {
			    //create the instance of Games using cursor information
			    FootballGameStatsPunting stats = new FootballGameStatsPunting();
			    stats.setgid(c.getLong(c.getColumnIndex(KEY_G_ID)));
			    stats.setpid(c.getLong(c.getColumnIndex(KEY_P_ID)));
			    stats.setpunts((c.getInt(c.getColumnIndex(KEY_PUNTS))));
			    stats.setyds((c.getInt(c.getColumnIndex(KEY_YDS))));
			    stats.setin20(c.getInt(c.getColumnIndex(KEY_IN20)));
			    stats.settbs(c.getInt(c.getColumnIndex(KEY_TBS)));
			    //Insert more stats here
			    
	            // adding to gameStats list
	            gameStats.add(stats);
	        } while (c.moveToNext());
	    }
	 
	    return gameStats;
	}
	
	//Get all GameStats
	public List<FootballGameStatsPunting> getAllGameStatsPunting() {
	    List<FootballGameStatsPunting> gameStats = new ArrayList<FootballGameStatsPunting>();
	    SQLiteDatabase db = this.getReadableDatabase();
	    String selectQuery = "SELECT  * FROM " + TABLE_FOOTBALL_GAME_STATS_PUNTING;
	 
	    Log.i(LOG, selectQuery);
	 
	    Cursor c = db.rawQuery(selectQuery, null);
	 
	    // looping through all rows and adding to list
	    if (c.moveToFirst()) {
	        do {
			    //create the instance of Games using cursor information
			    FootballGameStatsPunting stats = new FootballGameStatsPunting();
			    stats.setgid(c.getLong(c.getColumnIndex(KEY_G_ID)));
			    stats.setpid(c.getLong(c.getColumnIndex(KEY_P_ID)));
			    stats.setpunts((c.getInt(c.getColumnIndex(KEY_PUNTS))));
			    stats.setyds((c.getInt(c.getColumnIndex(KEY_YDS))));
			    stats.setin20(c.getInt(c.getColumnIndex(KEY_IN20)));
			    stats.settbs(c.getInt(c.getColumnIndex(KEY_TBS)));
			    //Insert more stats here
			    
	            // adding to gameStats list
	            gameStats.add(stats);
	        } while (c.moveToNext());
	    }
	 
	    return gameStats;
	}
	
	// Delete a GameStats
	public void deleteGameStatsPunting(long g_id) {
	    SQLiteDatabase db = this.getWritableDatabase();
	    db.delete(TABLE_FOOTBALL_GAME_STATS_PUNTING, KEY_G_ID + " = ?",
	            new String[] { String.valueOf(g_id) });
	}
	
	//ADDING STATS
	
	//Adding value to points category of a player
	public int addStatsPunting(long g_id, long p_id, String stat, int value){
	    SQLiteDatabase db = this.getWritableDatabase();
	    FootballGameStatsPunting stats = getPlayerGameStatsPunting(g_id, p_id);
	    
	    int old_value = getPlayerGameStatPunting(g_id,p_id,stat);
	    int new_value = old_value + value;
	    
	    ContentValues values = new ContentValues();
	    	
	    values.put(KEY_P_ID, p_id);
        values.put(KEY_G_ID, g_id);
	    if(stat==KEY_PUNTS)
	    	values.put(KEY_PUNTS, new_value);
	    else
	    	values.put(KEY_PUNTS, stats.getpunts());
	    if(stat==KEY_YDS)
	    	values.put(KEY_YDS, new_value);
	    else
	    	values.put(KEY_YDS, stats.getyds());
	    if(stat==KEY_IN20)
	    	values.put(KEY_IN20, new_value);
	    else
	    	values.put(KEY_IN20, stats.getin20());
	    if(stat==KEY_TBS)
	    	values.put(KEY_TBS, new_value);
	    else
	    	values.put(KEY_TBS, stats.gettbs());
        //insert more stats here
        
	    return db.update(TABLE_FOOTBALL_GAME_STATS_PUNTING,  values, KEY_P_ID + " = " + p_id + " AND " + KEY_G_ID + " = " + g_id, null);
	}
	
	// ----------------------- FOOTBALL_GAME_STATS_RECEIVING ---------------------------- //

	public void createGameStatsReceiving(long p_id, long g_id){
		SQLiteDatabase db = this.getWritableDatabase();
		 
        ContentValues values = new ContentValues();
        values.put(KEY_P_ID, p_id);
        values.put(KEY_G_ID, g_id);
        values.put(KEY_TGT, 0);
        values.put(KEY_REC, 0);
        values.put(KEY_YDS, 0);
        values.put(KEY_TDS, 0);
        values.put(KEY_TWOPC, 0);
        //insert more stats here
        
        // insert row
        db.insert(TABLE_FOOTBALL_GAME_STATS_RECEIVING, null, values);
	}
	
	//get single defense game stats for single player
	public FootballGameStatsReceiving getPlayerGameStatsReceiving(long g_id, long p_id) {
	    SQLiteDatabase db = this.getReadableDatabase();
	    //create query to select game
	    String selectQuery = "SELECT  * FROM " + TABLE_FOOTBALL_GAME_STATS_RECEIVING + 
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
	    FootballGameStatsReceiving stats = new FootballGameStatsReceiving();
	    stats.setgid(c.getLong(c.getColumnIndex(KEY_G_ID)));
	    stats.setpid(c.getLong(c.getColumnIndex(KEY_P_ID)));
	    stats.settgt((c.getInt(c.getColumnIndex(KEY_TGT))));
	    stats.setrec((c.getInt(c.getColumnIndex(KEY_REC))));
	    stats.setyds(c.getInt(c.getColumnIndex(KEY_YDS)));
	    stats.settds(c.getInt(c.getColumnIndex(KEY_TDS)));
	    stats.settwopc(c.getInt(c.getColumnIndex(KEY_TWOPC)));
	    //Insert more stats here
	    
	    return stats;
	}
	
	//get single game stats for single player
	public int getPlayerGameStatReceiving(long g_id, long p_id, String stat) {
	    SQLiteDatabase db = this.getReadableDatabase();
	    //create query to select game
	    String selectQuery = "SELECT " + stat + " FROM " + TABLE_FOOTBALL_GAME_STATS_RECEIVING + 
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
	public List<FootballGameStatsReceiving> getPlayerAllGameStatsReceiving(long p_id) {
	    List<FootballGameStatsReceiving> gameStats = new ArrayList<FootballGameStatsReceiving>();
	    SQLiteDatabase db = this.getReadableDatabase();
	    String selectQuery = "SELECT  * FROM " + TABLE_FOOTBALL_GAME_STATS_RECEIVING
	    		+ " WHERE " + KEY_P_ID + " = " + p_id ;
	 
	    Log.i(LOG, selectQuery);
	 
	    Cursor c = db.rawQuery(selectQuery, null);
	 
	    // looping through all rows and adding to list
	    if (c.moveToFirst()) {
	        do {
			    //create the instance of Games using cursor information
			    FootballGameStatsReceiving stats = new FootballGameStatsReceiving();
			    stats.setgid(c.getLong(c.getColumnIndex(KEY_G_ID)));
			    stats.setpid(c.getLong(c.getColumnIndex(KEY_P_ID)));
			    stats.settgt((c.getInt(c.getColumnIndex(KEY_TGT))));
			    stats.setrec((c.getInt(c.getColumnIndex(KEY_REC))));
			    stats.setyds(c.getInt(c.getColumnIndex(KEY_YDS)));
			    stats.settds(c.getInt(c.getColumnIndex(KEY_TDS)));
			    stats.settwopc(c.getInt(c.getColumnIndex(KEY_TWOPC)));
			    //Insert more stats here
			    
	            // adding to gameStats list
	            gameStats.add(stats);
	        } while (c.moveToNext());
	    }
	 
	    return gameStats;
	}
	
	//Get all GameStats
	public List<FootballGameStatsReceiving> getAllGameStatsReceiving() {
	    List<FootballGameStatsReceiving> gameStats = new ArrayList<FootballGameStatsReceiving>();
	    SQLiteDatabase db = this.getReadableDatabase();
	    String selectQuery = "SELECT  * FROM " + TABLE_FOOTBALL_GAME_STATS_RECEIVING;
	 
	    Log.i(LOG, selectQuery);
	 
	    Cursor c = db.rawQuery(selectQuery, null);
	 
	    // looping through all rows and adding to list
	    if (c.moveToFirst()) {
	        do {
			    //create the instance of Games using cursor information
			    FootballGameStatsReceiving stats = new FootballGameStatsReceiving();
			    stats.setgid(c.getLong(c.getColumnIndex(KEY_G_ID)));
			    stats.setpid(c.getLong(c.getColumnIndex(KEY_P_ID)));
			    stats.settgt((c.getInt(c.getColumnIndex(KEY_TGT))));
			    stats.setrec((c.getInt(c.getColumnIndex(KEY_REC))));
			    stats.setyds(c.getInt(c.getColumnIndex(KEY_YDS)));
			    stats.settds(c.getInt(c.getColumnIndex(KEY_TDS)));
			    stats.settwopc(c.getInt(c.getColumnIndex(KEY_TWOPC)));
			    //Insert more stats here
			    
	            // adding to gameStats list
	            gameStats.add(stats);
	        } while (c.moveToNext());
	    }
	 
	    return gameStats;
	}
	
	// Delete a GameStats
	public void deleteGameStatsReceiving(long g_id) {
	    SQLiteDatabase db = this.getWritableDatabase();
	    db.delete(TABLE_FOOTBALL_GAME_STATS_RECEIVING, KEY_G_ID + " = ?",
	            new String[] { String.valueOf(g_id) });
	}
	
	//ADDING STATS
	
	//Adding value to points category of a player
	public int addStatsReceiving(long g_id, long p_id, String stat, int value){
	    SQLiteDatabase db = this.getWritableDatabase();
	    FootballGameStatsReceiving stats = getPlayerGameStatsReceiving(g_id, p_id);
	    
	    int old_value = getPlayerGameStatReceiving(g_id,p_id,stat);
	    int new_value = old_value + value;
	    
	    ContentValues values = new ContentValues();
	    	
	    values.put(KEY_P_ID, p_id);
        values.put(KEY_G_ID, g_id);
	    if(stat==KEY_TGT)
	    	values.put(KEY_TGT, new_value);
	    else
	    	values.put(KEY_TGT, stats.gettgt());
	    if(stat==KEY_REC)
	    	values.put(KEY_REC, new_value);
	    else
	    	values.put(KEY_REC, stats.getrec());
	    if(stat==KEY_YDS)
	    	values.put(KEY_YDS, new_value);
	    else
	    	values.put(KEY_YDS, stats.getyds());
	    if(stat==KEY_TDS)
	    	values.put(KEY_TDS, new_value);
	    else
	    	values.put(KEY_TDS, stats.gettds());
	    if(stat==KEY_TWOPC)
	    	values.put(KEY_TWOPC, new_value);
	    else
	    	values.put(KEY_TWOPC, stats.gettwopc());
        //insert more stats here
        
	    return db.update(TABLE_FOOTBALL_GAME_STATS_RECEIVING,  values, KEY_P_ID + " = " + p_id + " AND " + KEY_G_ID + " = " + g_id, null);
	}
	
	// ----------------------- FOOTBALL_GAME_STATS_RETURNS ---------------------------- //

	public void createGameStatsReturns(long p_id, long g_id){
		SQLiteDatabase db = this.getWritableDatabase();
		 
        ContentValues values = new ContentValues();
        values.put(KEY_P_ID, p_id);
        values.put(KEY_G_ID, g_id);
        values.put(KEY_KRT, 0);
        values.put(KEY_KYDS, 0);
        values.put(KEY_KTDS, 0);
        values.put(KEY_PRT, 0);
        values.put(KEY_PYDS, 0);
        values.put(KEY_PTDS, 0);
        //insert more stats here
        
        // insert row
        db.insert(TABLE_FOOTBALL_GAME_STATS_RETURNS, null, values);
	}
	
	//get single defense game stats for single player
	public FootballGameStatsReturns getPlayerGameStatsReturns(long g_id, long p_id) {
	    SQLiteDatabase db = this.getReadableDatabase();
	    //create query to select game
	    String selectQuery = "SELECT  * FROM " + TABLE_FOOTBALL_GAME_STATS_RETURNS + 
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
	    FootballGameStatsReturns stats = new FootballGameStatsReturns();
	    stats.setgid(c.getLong(c.getColumnIndex(KEY_G_ID)));
	    stats.setpid(c.getLong(c.getColumnIndex(KEY_P_ID)));
	    stats.setkrt((c.getInt(c.getColumnIndex(KEY_KRT))));
	    stats.setkyds((c.getInt(c.getColumnIndex(KEY_KYDS))));
	    stats.setktds(c.getInt(c.getColumnIndex(KEY_KTDS)));
	    stats.setprt(c.getInt(c.getColumnIndex(KEY_PRT)));
	    stats.setpyds(c.getInt(c.getColumnIndex(KEY_PYDS)));
	    stats.setptds(c.getInt(c.getColumnIndex(KEY_PTDS)));
	    //Insert more stats here
	    
	    return stats;
	}
	
	//get single game stats for single player
	public int getPlayerGameStatReturns(long g_id, long p_id, String stat) {
	    SQLiteDatabase db = this.getReadableDatabase();
	    //create query to select game
	    String selectQuery = "SELECT " + stat + " FROM " + TABLE_FOOTBALL_GAME_STATS_RETURNS + 
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
	public List<FootballGameStatsReturns> getPlayerAllGameStatsReturns(long p_id) {
	    List<FootballGameStatsReturns> gameStats = new ArrayList<FootballGameStatsReturns>();
	    SQLiteDatabase db = this.getReadableDatabase();
	    String selectQuery = "SELECT  * FROM " + TABLE_FOOTBALL_GAME_STATS_RETURNS
	    		+ " WHERE " + KEY_P_ID + " = " + p_id ;
	 
	    Log.i(LOG, selectQuery);
	 
	    Cursor c = db.rawQuery(selectQuery, null);
	 
	    // looping through all rows and adding to list
	    if (c.moveToFirst()) {
	        do {
			    //create the instance of Games using cursor information
			    FootballGameStatsReturns stats = new FootballGameStatsReturns();
			    stats.setgid(c.getLong(c.getColumnIndex(KEY_G_ID)));
			    stats.setpid(c.getLong(c.getColumnIndex(KEY_P_ID)));
			    stats.setkrt((c.getInt(c.getColumnIndex(KEY_KRT))));
			    stats.setkyds((c.getInt(c.getColumnIndex(KEY_KYDS))));
			    stats.setktds(c.getInt(c.getColumnIndex(KEY_KTDS)));
			    stats.setprt(c.getInt(c.getColumnIndex(KEY_PRT)));
			    stats.setpyds(c.getInt(c.getColumnIndex(KEY_PYDS)));
			    stats.setptds(c.getInt(c.getColumnIndex(KEY_PTDS)));
			    //Insert more stats here
			    
	            // adding to gameStats list
	            gameStats.add(stats);
	        } while (c.moveToNext());
	    }
	 
	    return gameStats;
	}
	
	//Get all GameStats
	public List<FootballGameStatsReturns> getAllGameStatsReturns() {
	    List<FootballGameStatsReturns> gameStats = new ArrayList<FootballGameStatsReturns>();
	    SQLiteDatabase db = this.getReadableDatabase();
	    String selectQuery = "SELECT  * FROM " + TABLE_FOOTBALL_GAME_STATS_RETURNS;
	 
	    Log.i(LOG, selectQuery);
	 
	    Cursor c = db.rawQuery(selectQuery, null);
	 
	    // looping through all rows and adding to list
	    if (c.moveToFirst()) {
	        do {
			    //create the instance of Games using cursor information
			    FootballGameStatsReturns stats = new FootballGameStatsReturns();
			    stats.setgid(c.getLong(c.getColumnIndex(KEY_G_ID)));
			    stats.setpid(c.getLong(c.getColumnIndex(KEY_P_ID)));
			    stats.setkrt((c.getInt(c.getColumnIndex(KEY_KRT))));
			    stats.setkyds((c.getInt(c.getColumnIndex(KEY_KYDS))));
			    stats.setktds(c.getInt(c.getColumnIndex(KEY_KTDS)));
			    stats.setprt(c.getInt(c.getColumnIndex(KEY_PRT)));
			    stats.setpyds(c.getInt(c.getColumnIndex(KEY_PYDS)));
			    stats.setptds(c.getInt(c.getColumnIndex(KEY_PTDS)));
			    //Insert more stats here
			    
	            // adding to gameStats list
	            gameStats.add(stats);
	        } while (c.moveToNext());
	    }
	 
	    return gameStats;
	}
	
	// Delete a GameStats
	public void deleteGameStatsReturns(long g_id) {
	    SQLiteDatabase db = this.getWritableDatabase();
	    db.delete(TABLE_FOOTBALL_GAME_STATS_RETURNS, KEY_G_ID + " = ?",
	            new String[] { String.valueOf(g_id) });
	}
	
	//ADDING STATS
	
	//Adding value to points category of a player
	public int addStatsReturns(long g_id, long p_id, String stat, int value){
	    SQLiteDatabase db = this.getWritableDatabase();
	    FootballGameStatsReturns stats = getPlayerGameStatsReturns(g_id, p_id);
	    
	    int old_value = getPlayerGameStatReturns(g_id,p_id,stat);
	    int new_value = old_value + value;
	    
	    ContentValues values = new ContentValues();
	    	
	    values.put(KEY_P_ID, p_id);
        values.put(KEY_G_ID, g_id);
	    if(stat==KEY_KRT)
	    	values.put(KEY_KRT, new_value);
	    else
	    	values.put(KEY_KRT, stats.getkrt());
	    if(stat==KEY_KYDS)
	    	values.put(KEY_KYDS, new_value);
	    else
	    	values.put(KEY_KYDS, stats.getkyds());
	    if(stat==KEY_KTDS)
	    	values.put(KEY_KTDS, new_value);
	    else
	    	values.put(KEY_KTDS, stats.getktds());
	    if(stat==KEY_PRT)
	    	values.put(KEY_PRT, new_value);
	    else
	    	values.put(KEY_PRT, stats.getprt());
	    if(stat==KEY_PYDS)
	    	values.put(KEY_PYDS, new_value);
	    else
	    	values.put(KEY_PYDS, stats.getpyds());
	    if(stat==KEY_PTDS)
	    	values.put(KEY_PTDS, new_value);
	    else
	    	values.put(KEY_PTDS, stats.getptds());
        //insert more stats here
        
	    return db.update(TABLE_FOOTBALL_GAME_STATS_RETURNS,  values, KEY_P_ID + " = " + p_id + " AND " + KEY_G_ID + " = " + g_id, null);
	}
	
	// ----------------------- FOOTBALL_GAME_STATS_RUSHING ---------------------------- //

	public void createGameStatsRushing(long p_id, long g_id){
		SQLiteDatabase db = this.getWritableDatabase();
		 
        ContentValues values = new ContentValues();
        values.put(KEY_P_ID, p_id);
        values.put(KEY_G_ID, g_id);
        values.put(KEY_ATT, 0);
        values.put(KEY_YDS, 0);
        values.put(KEY_TDS, 0);
        values.put(KEY_TWOPC, 0);
        //insert more stats here
        
        // insert row
        db.insert(TABLE_FOOTBALL_GAME_STATS_RUSHING, null, values);
	}
	
	//get single defense game stats for single player
	public FootballGameStatsRushing getPlayerGameStatsRushing(long g_id, long p_id) {
	    SQLiteDatabase db = this.getReadableDatabase();
	    //create query to select game
	    String selectQuery = "SELECT  * FROM " + TABLE_FOOTBALL_GAME_STATS_RUSHING + 
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
	    FootballGameStatsRushing stats = new FootballGameStatsRushing();
	    stats.setgid(c.getLong(c.getColumnIndex(KEY_G_ID)));
	    stats.setpid(c.getLong(c.getColumnIndex(KEY_P_ID)));
	    stats.setatt((c.getInt(c.getColumnIndex(KEY_ATT))));
	    stats.setyds((c.getInt(c.getColumnIndex(KEY_YDS))));
	    stats.settds(c.getInt(c.getColumnIndex(KEY_TDS)));
	    stats.settwopc(c.getInt(c.getColumnIndex(KEY_TWOPC)));
	    //Insert more stats here
	    
	    return stats;
	}
	
	//get single game stats for single player
	public int getPlayerGameStatRushing(long g_id, long p_id, String stat) {
	    SQLiteDatabase db = this.getReadableDatabase();
	    //create query to select game
	    String selectQuery = "SELECT " + stat + " FROM " + TABLE_FOOTBALL_GAME_STATS_RUSHING + 
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
	public List<FootballGameStatsRushing> getPlayerAllGameStatsRushing(long p_id) {
	    List<FootballGameStatsRushing> gameStats = new ArrayList<FootballGameStatsRushing>();
	    SQLiteDatabase db = this.getReadableDatabase();
	    String selectQuery = "SELECT  * FROM " + TABLE_FOOTBALL_GAME_STATS_RUSHING
	    		+ " WHERE " + KEY_P_ID + " = " + p_id ;
	 
	    Log.i(LOG, selectQuery);
	 
	    Cursor c = db.rawQuery(selectQuery, null);
	 
	    // looping through all rows and adding to list
	    if (c.moveToFirst()) {
	        do {
			    //create the instance of Games using cursor information
			    FootballGameStatsRushing stats = new FootballGameStatsRushing();
			    stats.setgid(c.getLong(c.getColumnIndex(KEY_G_ID)));
			    stats.setpid(c.getLong(c.getColumnIndex(KEY_P_ID)));
			    stats.setatt((c.getInt(c.getColumnIndex(KEY_ATT))));
			    stats.setyds((c.getInt(c.getColumnIndex(KEY_YDS))));
			    stats.settds(c.getInt(c.getColumnIndex(KEY_TDS)));
			    stats.settwopc(c.getInt(c.getColumnIndex(KEY_TWOPC)));
			    //Insert more stats here
			    
	            // adding to gameStats list
	            gameStats.add(stats);
	        } while (c.moveToNext());
	    }
	 
	    return gameStats;
	}
	
	//Get all GameStats
	public List<FootballGameStatsRushing> getAllGameStatsRushing() {
	    List<FootballGameStatsRushing> gameStats = new ArrayList<FootballGameStatsRushing>();
	    SQLiteDatabase db = this.getReadableDatabase();
	    String selectQuery = "SELECT  * FROM " + TABLE_FOOTBALL_GAME_STATS_RUSHING;
	 
	    Log.i(LOG, selectQuery);
	 
	    Cursor c = db.rawQuery(selectQuery, null);
	 
	    // looping through all rows and adding to list
	    if (c.moveToFirst()) {
	        do {
			    //create the instance of Games using cursor information
			    FootballGameStatsRushing stats = new FootballGameStatsRushing();
			    stats.setgid(c.getLong(c.getColumnIndex(KEY_G_ID)));
			    stats.setpid(c.getLong(c.getColumnIndex(KEY_P_ID)));
			    stats.setatt((c.getInt(c.getColumnIndex(KEY_ATT))));
			    stats.setyds((c.getInt(c.getColumnIndex(KEY_YDS))));
			    stats.settds(c.getInt(c.getColumnIndex(KEY_TDS)));
			    stats.settwopc(c.getInt(c.getColumnIndex(KEY_TWOPC)));
			    //Insert more stats here
			    
	            // adding to gameStats list
	            gameStats.add(stats);
	        } while (c.moveToNext());
	    }
	 
	    return gameStats;
	}
	
	// Delete a GameStats
	public void deleteGameStatsRushing(long g_id) {
	    SQLiteDatabase db = this.getWritableDatabase();
	    db.delete(TABLE_FOOTBALL_GAME_STATS_RUSHING, KEY_G_ID + " = ?",
	            new String[] { String.valueOf(g_id) });
	}
	
	//ADDING STATS
	
	//Adding value to points category of a player
	public int addStatsRushing(long g_id, long p_id, String stat, int value){
	    SQLiteDatabase db = this.getWritableDatabase();
	    FootballGameStatsRushing stats = getPlayerGameStatsRushing(g_id, p_id);
	    
	    int old_value = getPlayerGameStatRushing(g_id,p_id,stat);
	    int new_value = old_value + value;
	    
	    ContentValues values = new ContentValues();
	    	
	    values.put(KEY_P_ID, p_id);
        values.put(KEY_G_ID, g_id);
	    if(stat==KEY_ATT)
	    	values.put(KEY_ATT, new_value);
	    else
	    	values.put(KEY_ATT, stats.getatt());
	    if(stat==KEY_YDS)
	    	values.put(KEY_YDS, new_value);
	    else
	    	values.put(KEY_YDS, stats.getyds());
	    if(stat==KEY_TDS)
	    	values.put(KEY_TDS, new_value);
	    else
	    	values.put(KEY_TDS, stats.gettds());
	    if(stat==KEY_TWOPC)
	    	values.put(KEY_TWOPC, new_value);
	    else
	    	values.put(KEY_TWOPC, stats.gettwopc());
        //insert more stats here
        
	    return db.update(TABLE_FOOTBALL_GAME_STATS_RUSHING,  values, KEY_P_ID + " = " + p_id + " AND " + KEY_G_ID + " = " + g_id, null);
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
	        
	        if (c!=null)
	        	c.moveToFirst();
	        
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
	        
	        return pbps;
		}
		
		// Delete PlayByPlay of a game
		public void deletePlayByPlayGame(long g_id) {
		    SQLiteDatabase db = this.getWritableDatabase();
		    db.delete(TABLE_PLAY_BY_PLAY, KEY_G_ID + " = ?",
		            new String[] { String.valueOf(g_id) });
		}
	
	// ----------------------- PLAYERS table methods ------------------------- //

	public long createPlayers(FootballPlayer footballPlayer){
		SQLiteDatabase db = this.getWritableDatabase();
		 
        ContentValues values = new ContentValues();
        values.put(KEY_T_ID, footballPlayer.gettid());
        values.put(KEY_P_NAME, footballPlayer.getpname());
        values.put(KEY_P_NUM, footballPlayer.getpnum());

        // insert row
        long p_id = db.insert(TABLE_PLAYERS, null, values);
 
        return p_id;
	}
	
	//get single player
	public FootballPlayer getPlayer(long p_id) {
	    SQLiteDatabase db = this.getReadableDatabase();
	    //create query to select game
	    String selectQuery = "SELECT  * FROM " + TABLE_PLAYERS + 
	    	" WHERE " + KEY_P_ID + " = " + p_id;
	    //Log the query
	    Log.i(LOG, selectQuery);
	    //perform the query and store data in cursor
	    Cursor c = db.rawQuery(selectQuery, null);
	    //set cursor to beginning
	    if (c != null)
	        c.moveToFirst();
	    //create the instance of Teams using cursor information
	    FootballPlayer player = new FootballPlayer();
	    player.setpid(c.getLong(c.getColumnIndex(KEY_P_ID)));
	    player.settid(c.getLong(c.getColumnIndex(KEY_T_ID)));
	    player.setpname((c.getString(c.getColumnIndex(KEY_P_NAME))));
	    player.setpnum((c.getInt(c.getColumnIndex(KEY_P_NUM))));
	 
	    return player;
	}


	
	// closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
}
