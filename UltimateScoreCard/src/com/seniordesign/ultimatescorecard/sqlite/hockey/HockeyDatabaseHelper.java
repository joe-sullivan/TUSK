package com.seniordesign.ultimatescorecard.sqlite.hockey;


import java.util.ArrayList;
import java.util.List;

import com.seniordesign.ultimatescorecard.data.hockey.HockeyPlayer;
import com.seniordesign.ultimatescorecard.sqlite.helper.DatabaseHelper;
import com.seniordesign.ultimatescorecard.sqlite.helper.Games;
import com.seniordesign.ultimatescorecard.sqlite.helper.Players;
import com.seniordesign.ultimatescorecard.sqlite.helper.Teams;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class HockeyDatabaseHelper extends DatabaseHelper{

	// Logcat tag
    private static final String LOG = "HockeyDatabaseHelper";
 
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "HockeyStats";
	
    //Table Names
    private static final String TABLE_HOCKEY_GAME_STATS = "hockey_game_stats";

    //GAMES Table - column names  
    private static final String KEY_HOME_SOG = "home_sog";
    private static final String KEY_HOME_GOALS = "home_goals";
    private static final String KEY_HOME_SAVES = "home_saves";
    private static final String KEY_HOME_GOALS_ALLOWED = "home_goals_allowed";
    private static final String KEY_HOME_SHOTS = "home_shots";
    private static final String KEY_HOME_AST = "home_ast";
    private static final String KEY_HOME_PEN_MINOR = "home_pen_minor";
    private static final String KEY_HOME_PEN_MAJOR = "home_pen_major";
    private static final String KEY_HOME_PEN_MISCONDUCT = "home_pen_misconduct";
    
    private static final String KEY_AWAY_SOG = "away_sog";
    private static final String KEY_AWAY_GOALS = "away_goals";
    private static final String KEY_AWAY_SAVES = "away_saves";
    private static final String KEY_AWAY_GOALS_ALLOWED = "away_goals_allowed";
    private static final String KEY_AWAY_SHOTS = "away_shots";
    private static final String KEY_AWAY_AST = "away_ast";
    private static final String KEY_AWAY_PEN_MINOR = "away_pen_minor";
    private static final String KEY_AWAY_PEN_MAJOR = "away_pen_major";
    private static final String KEY_AWAY_PEN_MISCONDUCT = "away_pen_misconduct";
    
    //HOCKEYGAMESTATS - common column names
    private static final String KEY_SOG = "sog";
    private static final String KEY_GOALS = "goals";
    private static final String KEY_SAVES = "saves";
    private static final String KEY_GOALS_ALLOWED = "goals_allowed";
    private static final String KEY_SHOTS = "shots";
    private static final String KEY_AST = "ast";
    private static final String KEY_PEN_MINOR = "pen_minor";
    private static final String KEY_PEN_MAJOR = "pen_major";
    private static final String KEY_PEN_MISCONDUCT = "pen_misconduct";

    //Table Create Statements
    //GAMES table create statement
    private static final String CREATE_TABLE_GAMES = "CREATE TABLE IF NOT EXISTS " + TABLE_GAMES 
    		+ "(" + KEY_G_ID + " INTEGER PRIMARY KEY," + KEY_HOME_ID + " INTEGER," 
    		+ KEY_AWAY_ID + " INTEGER," + KEY_DATE + " DATE, "
    		
    		+ KEY_HOME_SHOTS + " INTEGER, " + KEY_HOME_SOG + " INTEGER, " + KEY_HOME_GOALS + " INTEGER, "
    		+ KEY_HOME_AST + " INTEGER, " + KEY_HOME_PEN_MINOR + " INTEGER, " + KEY_HOME_PEN_MAJOR + " INTEGER, "
    		+ KEY_HOME_PEN_MISCONDUCT + " INTEGER, "+ KEY_HOME_SAVES + " INTEGER, " + KEY_HOME_GOALS_ALLOWED + " INTEGER, "
    		
    		+ KEY_AWAY_SHOTS + " INTEGER, " + KEY_AWAY_SOG + " INTEGER, " + KEY_AWAY_GOALS + " INTEGER, "
    		+ KEY_AWAY_AST + " INTEGER, " + KEY_AWAY_PEN_MINOR + " INTEGER, " + KEY_AWAY_PEN_MAJOR + " INTEGER, "
    		+ KEY_AWAY_PEN_MISCONDUCT + " INTEGER, "+ KEY_AWAY_SAVES + " INTEGER, " + KEY_AWAY_GOALS_ALLOWED + " INTEGER"
    	
    		+ ")"; 
    //Doesn't include SPORT yet...
    
    //HOCKEY_GAME_STATS table create statement
    private static final String CREATE_TABLE_HOCKEY_GAME_STATS = "CREATE TABLE IF NOT EXISTS " + TABLE_HOCKEY_GAME_STATS 
    		+ "(" + KEY_G_ID + " INTEGER" + ", " + KEY_P_ID + " INTEGER" + ", "
    		+ KEY_SHOTS + " INTEGER, " + KEY_SOG + " INTEGER, " + KEY_GOALS + " INTEGER, "
    		+ KEY_AST + " INTEGER, " + KEY_PEN_MINOR + " INTEGER, " + KEY_PEN_MAJOR + " INTEGER, "
    		+ KEY_PEN_MISCONDUCT + " INTEGER, "+ KEY_SAVES + " INTEGER, " + KEY_GOALS_ALLOWED + " INTEGER"
    		+ ")"; 
    
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
        db.execSQL(CREATE_TABLE_PLAYERS);
        db.execSQL(CREATE_TABLE_TEAMS);
        db.execSQL(CREATE_TABLE_PLAY_BY_PLAY);
        db.execSQL(CREATE_TABLE_SHOT_CHART_COORDS);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GAMES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HOCKEY_GAME_STATS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYERS);
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
        
        values.put(KEY_HOME_SHOTS, 0);
        values.put(KEY_HOME_SOG, 0);
        values.put(KEY_HOME_GOALS, 0);
        values.put(KEY_HOME_AST, 0);
        values.put(KEY_HOME_PEN_MINOR, 0);
        values.put(KEY_HOME_PEN_MAJOR, 0);
        values.put(KEY_HOME_PEN_MISCONDUCT, 0);
        values.put(KEY_HOME_SAVES,0);
        values.put(KEY_HOME_GOALS_ALLOWED, 0);
        
        values.put(KEY_AWAY_SHOTS, 0);
        values.put(KEY_AWAY_SOG, 0);
        values.put(KEY_AWAY_GOALS, 0);
        values.put(KEY_AWAY_AST, 0);
        values.put(KEY_AWAY_PEN_MINOR, 0);
        values.put(KEY_AWAY_PEN_MAJOR, 0);
        values.put(KEY_AWAY_PEN_MISCONDUCT, 0);
        values.put(KEY_AWAY_SAVES,0);
        values.put(KEY_AWAY_GOALS_ALLOWED, 0);
 
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
	    HockeyGames game = new HockeyGames();

	    //set cursor to beginning
	    if (c != null && c.moveToFirst()){
	    //create the instance of Games using cursor information
		    game.setgid(c.getLong(c.getColumnIndex(KEY_G_ID)));
		    game.sethomeid((c.getLong(c.getColumnIndex(KEY_HOME_ID))));
		    game.setawayid((c.getLong(c.getColumnIndex(KEY_AWAY_ID))));
		    game.setDate(c.getString(c.getColumnIndex(KEY_DATE)));
		 
		    game.sethomeshots((c.getInt(c.getColumnIndex(KEY_HOME_SHOTS))));
		    game.sethomesog((c.getInt(c.getColumnIndex(KEY_HOME_SOG))));
		    game.sethomegoals(c.getInt(c.getColumnIndex(KEY_HOME_GOALS)));
		    game.sethomeast((c.getInt(c.getColumnIndex(KEY_HOME_AST))));
		    game.sethomepenminor((c.getInt(c.getColumnIndex(KEY_HOME_PEN_MINOR))));
		    game.sethomepenmajor(c.getInt(c.getColumnIndex(KEY_HOME_PEN_MAJOR)));
		    game.sethomepenmisconduct(c.getInt(c.getColumnIndex(KEY_HOME_PEN_MISCONDUCT)));
		    game.sethomesaves((c.getInt(c.getColumnIndex(KEY_HOME_SAVES))));
		    game.sethomegoalsallowed((c.getInt(c.getColumnIndex(KEY_HOME_GOALS_ALLOWED))));
		    
		    game.setawayshots((c.getInt(c.getColumnIndex(KEY_AWAY_SHOTS))));
		    game.setawaysog((c.getInt(c.getColumnIndex(KEY_AWAY_SOG))));
		    game.setawaygoals(c.getInt(c.getColumnIndex(KEY_AWAY_GOALS)));
		    game.setawayast((c.getInt(c.getColumnIndex(KEY_AWAY_AST))));
		    game.setawaypenminor((c.getInt(c.getColumnIndex(KEY_AWAY_PEN_MINOR))));
		    game.setawaypenmajor(c.getInt(c.getColumnIndex(KEY_AWAY_PEN_MAJOR)));
		    game.setawaypenmisconduct(c.getInt(c.getColumnIndex(KEY_AWAY_PEN_MISCONDUCT)));
		    game.setawaysaves((c.getInt(c.getColumnIndex(KEY_AWAY_SAVES))));
		    game.setawaygoalsallowed((c.getInt(c.getColumnIndex(KEY_AWAY_GOALS_ALLOWED))));
	    }
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
		        	HockeyGames game = new HockeyGames();
		    	    game.setgid(c.getLong(c.getColumnIndex(KEY_G_ID)));
		    	    game.sethomeid((c.getLong(c.getColumnIndex(KEY_HOME_ID))));
		    	    game.setawayid((c.getLong(c.getColumnIndex(KEY_AWAY_ID))));
		    	    game.setDate(c.getString(c.getColumnIndex(KEY_DATE)));
		    		 
		    	    game.sethomeshots((c.getInt(c.getColumnIndex(KEY_HOME_SHOTS))));
		    	    game.sethomesog((c.getInt(c.getColumnIndex(KEY_HOME_SOG))));
		    	    game.sethomegoals(c.getInt(c.getColumnIndex(KEY_HOME_GOALS)));
		    	    game.sethomeast((c.getInt(c.getColumnIndex(KEY_HOME_AST))));
		    	    game.sethomepenminor((c.getInt(c.getColumnIndex(KEY_HOME_PEN_MINOR))));
		    	    game.sethomepenmajor(c.getInt(c.getColumnIndex(KEY_HOME_PEN_MAJOR)));
		    	    game.sethomepenmisconduct(c.getInt(c.getColumnIndex(KEY_HOME_PEN_MISCONDUCT)));
		    	    game.sethomesaves((c.getInt(c.getColumnIndex(KEY_HOME_SAVES))));
		    	    game.sethomegoalsallowed((c.getInt(c.getColumnIndex(KEY_HOME_GOALS_ALLOWED))));
		    	    
		    	    game.setawayshots((c.getInt(c.getColumnIndex(KEY_AWAY_SHOTS))));
		    	    game.setawaysog((c.getInt(c.getColumnIndex(KEY_AWAY_SOG))));
		    	    game.setawaygoals(c.getInt(c.getColumnIndex(KEY_AWAY_GOALS)));
		    	    game.setawayast((c.getInt(c.getColumnIndex(KEY_AWAY_AST))));
		    	    game.setawaypenminor((c.getInt(c.getColumnIndex(KEY_AWAY_PEN_MINOR))));
		    	    game.setawaypenmajor(c.getInt(c.getColumnIndex(KEY_AWAY_PEN_MAJOR)));
		    	    game.setawaypenmisconduct(c.getInt(c.getColumnIndex(KEY_AWAY_PEN_MISCONDUCT)));
		    	    game.setawaysaves((c.getInt(c.getColumnIndex(KEY_AWAY_SAVES))));
		    	    game.setawaygoalsallowed((c.getInt(c.getColumnIndex(KEY_AWAY_GOALS_ALLOWED))));
		    	
		    	    
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
	        	HockeyGames game = new HockeyGames();
	    	    game.setgid(c.getLong(c.getColumnIndex(KEY_G_ID)));
	    	    game.sethomeid((c.getLong(c.getColumnIndex(KEY_HOME_ID))));
	    	    game.setawayid((c.getLong(c.getColumnIndex(KEY_AWAY_ID))));
	    	    game.setDate(c.getString(c.getColumnIndex(KEY_DATE)));
	    		 
	    	    game.sethomeshots((c.getInt(c.getColumnIndex(KEY_HOME_SHOTS))));
	    	    game.sethomesog((c.getInt(c.getColumnIndex(KEY_HOME_SOG))));
	    	    game.sethomegoals(c.getInt(c.getColumnIndex(KEY_HOME_GOALS)));
	    	    game.sethomeast((c.getInt(c.getColumnIndex(KEY_HOME_AST))));
	    	    game.sethomepenminor((c.getInt(c.getColumnIndex(KEY_HOME_PEN_MINOR))));
	    	    game.sethomepenmajor(c.getInt(c.getColumnIndex(KEY_HOME_PEN_MAJOR)));
	    	    game.sethomepenmisconduct(c.getInt(c.getColumnIndex(KEY_HOME_PEN_MISCONDUCT)));
	    	    game.sethomesaves((c.getInt(c.getColumnIndex(KEY_HOME_SAVES))));
	    	    game.sethomegoalsallowed((c.getInt(c.getColumnIndex(KEY_HOME_GOALS_ALLOWED))));
	    	    
	    	    game.setawayshots((c.getInt(c.getColumnIndex(KEY_AWAY_SHOTS))));
	    	    game.setawaysog((c.getInt(c.getColumnIndex(KEY_AWAY_SOG))));
	    	    game.setawaygoals(c.getInt(c.getColumnIndex(KEY_AWAY_GOALS)));
	    	    game.setawayast((c.getInt(c.getColumnIndex(KEY_AWAY_AST))));
	    	    game.setawaypenminor((c.getInt(c.getColumnIndex(KEY_AWAY_PEN_MINOR))));
	    	    game.setawaypenmajor(c.getInt(c.getColumnIndex(KEY_AWAY_PEN_MAJOR)));
	    	    game.setawaypenmisconduct(c.getInt(c.getColumnIndex(KEY_AWAY_PEN_MISCONDUCT)));
	    	    game.setawaysaves((c.getInt(c.getColumnIndex(KEY_AWAY_SAVES))));
	    	    game.setawaygoalsallowed((c.getInt(c.getColumnIndex(KEY_AWAY_GOALS_ALLOWED))));
	    	
	    	    
	    	    // adding to games list
	            games.add(game);
	        } while (c.moveToNext());
	    }
	 
	    return games;
	}
	
	//get single game stat for team
	public int getTeamGameStat(long g_id, String stat) {
	    SQLiteDatabase db = this.getReadableDatabase();
	    //create query to select game
	    String selectQuery = "SELECT " + stat + " FROM " + TABLE_GAMES + 
	    	" WHERE " + KEY_G_ID + " = " + g_id;
	    
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
	
	//Adding value to points category of a player
	public int addTeamStats(long g_id, String stat, int value){
	    SQLiteDatabase db = this.getWritableDatabase();
	    HockeyGames game = (HockeyGames) getGame(g_id);
	    
	    int old_value = getTeamGameStat(g_id,stat);
	    int new_value = old_value + value;
	    
	    ContentValues values = new ContentValues();
	    	
        values.put(KEY_G_ID, g_id);
        values.put(KEY_HOME_ID, game.gethomeid());
        values.put(KEY_AWAY_ID, game.getawayid());
        values.put(KEY_DATE, game.getDate());

	    if(stat==KEY_HOME_SHOTS)
	    	values.put(KEY_HOME_SHOTS, new_value);
	    else
	    	values.put(KEY_HOME_SHOTS, game.gethomeshots());
	    if(stat==KEY_HOME_SOG)
	    	values.put(KEY_HOME_SOG, new_value);
	    else
	    	values.put(KEY_HOME_SOG, game.gethomesog());
	    if(stat==KEY_HOME_GOALS)
	    	values.put(KEY_HOME_GOALS, new_value);
	    else
	    	values.put(KEY_HOME_GOALS, game.gethomegoals());
	    if(stat==KEY_HOME_AST)
	    	values.put(KEY_HOME_AST, new_value);
	    else
	    	values.put(KEY_HOME_AST, game.gethomeast());
	    if(stat==KEY_HOME_PEN_MINOR)
	    	values.put(KEY_HOME_PEN_MINOR, new_value);
	    else
	    	values.put(KEY_HOME_PEN_MINOR, game.gethomepenminor());
	    if(stat==KEY_HOME_PEN_MAJOR)
	    	values.put(KEY_HOME_PEN_MAJOR, new_value);
	    else
	    	values.put(KEY_HOME_PEN_MAJOR, game.gethomepenminor());
	    if(stat==KEY_HOME_PEN_MISCONDUCT)
	    	values.put(KEY_HOME_PEN_MISCONDUCT, new_value);
	    else
	    	values.put(KEY_HOME_PEN_MISCONDUCT, game.gethomepenmisconduct());
	    if(stat==KEY_HOME_SAVES)
	    	values.put(KEY_HOME_SAVES, new_value);
	    else
	    	values.put(KEY_HOME_SAVES, game.gethomesaves());
	    if(stat==KEY_HOME_GOALS_ALLOWED)
	    	values.put(KEY_HOME_GOALS_ALLOWED, new_value);
	    else
	    	values.put(KEY_HOME_GOALS_ALLOWED, game.gethomegoalsallowed());
        
	    if(stat==KEY_AWAY_SHOTS)
	    	values.put(KEY_AWAY_SHOTS, new_value);
	    else
	    	values.put(KEY_AWAY_SHOTS, game.getawayshots());
	    if(stat==KEY_AWAY_SOG)
	    	values.put(KEY_AWAY_SOG, new_value);
	    else
	    	values.put(KEY_AWAY_SOG, game.getawaysog());
	    if(stat==KEY_AWAY_GOALS)
	    	values.put(KEY_AWAY_GOALS, new_value);
	    else
	    	values.put(KEY_AWAY_GOALS, game.getawaygoals());
	    if(stat==KEY_AWAY_AST)
	    	values.put(KEY_AWAY_AST, new_value);
	    else
	    	values.put(KEY_AWAY_AST, game.getawayast());
	    if(stat==KEY_AWAY_PEN_MINOR)
	    	values.put(KEY_AWAY_PEN_MINOR, new_value);
	    else
	    	values.put(KEY_AWAY_PEN_MINOR, game.getawaypenminor());
	    if(stat==KEY_AWAY_PEN_MAJOR)
	    	values.put(KEY_AWAY_PEN_MAJOR, new_value);
	    else
	    	values.put(KEY_AWAY_PEN_MAJOR, game.getawaypenminor());
	    if(stat==KEY_AWAY_PEN_MISCONDUCT)
	    	values.put(KEY_AWAY_PEN_MISCONDUCT, new_value);
	    else
	    	values.put(KEY_AWAY_PEN_MISCONDUCT, game.getawaypenmisconduct());
	    if(stat==KEY_AWAY_SAVES)
	    	values.put(KEY_AWAY_SAVES, new_value);
	    else
	    	values.put(KEY_AWAY_SAVES, game.getawaysaves());
	    if(stat==KEY_AWAY_GOALS_ALLOWED)
	    	values.put(KEY_AWAY_GOALS_ALLOWED, new_value);
	    else
	    	values.put(KEY_AWAY_GOALS_ALLOWED, game.getawaygoalsallowed());
	    //insert more stats here
        
	    return db.update(TABLE_GAMES,  values, KEY_G_ID + " = " + g_id, null);
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
        values.put(KEY_PEN_MISCONDUCT, 0);
        values.put(KEY_SAVES,0);
        values.put(KEY_GOALS_ALLOWED, 0);
        //insert more stats here
        
        // insert row
        db.insert(TABLE_HOCKEY_GAME_STATS, null, values);
	}
	
	//get single game stats for single player
	public HockeyGameStats getPlayerGameStats(long g_id, long p_id) {
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
	    stats.setpenmisconduct(c.getInt(c.getColumnIndex(KEY_PEN_MISCONDUCT)));
	    stats.setsaves((c.getInt(c.getColumnIndex(KEY_SAVES))));
	    stats.setgoalsallowed((c.getInt(c.getColumnIndex(KEY_GOALS_ALLOWED))));

	    //Insert more stats here
	    
	    return stats;
	}
	
	//get single game stats for single player
	public int getPlayerGameStat(long g_id, long p_id, String stat) {
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
	public List<HockeyGameStats> getPlayerAllGameStats(long p_id) {
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
	    	    stats.setpenmisconduct(c.getInt(c.getColumnIndex(KEY_PEN_MISCONDUCT)));
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
	public List<HockeyGameStats> getAllGameStats() {
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
	    	    stats.setpenmisconduct(c.getInt(c.getColumnIndex(KEY_PEN_MISCONDUCT)));
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
	public void deleteGameStats(long g_id) {
	    SQLiteDatabase db = this.getWritableDatabase();
	    db.delete(TABLE_HOCKEY_GAME_STATS, KEY_G_ID + " = ?",
	            new String[] { String.valueOf(g_id) });
	}
	
	//ADDING STATS
	
	//Adding value to points category of a player
	public int addStats(long g_id, long p_id, String stat, int value){
	    SQLiteDatabase db = this.getWritableDatabase();
	    HockeyGameStats stats = getPlayerGameStats(g_id, p_id);
	    
	    int old_value = getPlayerGameStat(g_id,p_id,stat);
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
	    if(stat==KEY_PEN_MISCONDUCT)
	    	values.put(KEY_PEN_MISCONDUCT, new_value);
	    else
	    	values.put(KEY_PEN_MISCONDUCT, stats.getpenmisconduct());
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
	
/*	
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
*/
	// ----------------------- PLAYERS table methods ------------------------- //

	public long createPlayers(HockeyPlayer player){
		SQLiteDatabase db = this.getWritableDatabase();
		 
        ContentValues values = new ContentValues();
        values.put(KEY_T_ID, player.gettid());
        values.put(KEY_P_NAME, player.getpname());
        values.put(KEY_P_NUM, player.getpnum());

        // insert row
        long p_id = db.insert(TABLE_PLAYERS, null, values);
 
        return p_id;
	}
	
	//get single player
	public HockeyPlayer getPlayer(long p_id) {
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
	    HockeyPlayer player = new HockeyPlayer();
	    player.setpid(c.getLong(c.getColumnIndex(KEY_P_ID)));
	    player.settid(c.getLong(c.getColumnIndex(KEY_T_ID)));
	    player.setpname((c.getString(c.getColumnIndex(KEY_P_NAME))));
	    player.setpnum((c.getInt(c.getColumnIndex(KEY_P_NUM))));
	    player.setdb(this);
	    
	    return player;
	}
/*	
	public List<Players> getPlayersTeam(long t_id){
	    SQLiteDatabase db = this.getReadableDatabase();
		List<Players> players = new ArrayList<Players>();
		String selectPlayerQuery = "SELECT * FROM " + TABLE_PLAYERS + " WHERE " + KEY_T_ID + " = " + t_id;
        
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
		String selectPlayerQuery = "SELECT * FROM " + TABLE_PLAYERS;
        
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
	    db.delete(TABLE_PLAYERS, KEY_P_ID + " = " + p_id, null);
	}
		
	
	// Delete  on a team
	public void deletePlayers(long t_id) {
	    SQLiteDatabase db = this.getWritableDatabase();
	    db.delete(TABLE_PLAYERS, KEY_T_ID + " = ?",
	            new String[] { String.valueOf(t_id) });
	}
	
	*/
	
	// -------------------SHOT_CHART_COORDS table methods ------------------ //
/*	
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

	// Delete a Shot
	public void deleteShot(long a_id) {
	    SQLiteDatabase db = this.getWritableDatabase();
	    db.delete(TABLE_SHOT_CHART_COORDS, KEY_A_ID + " = " + a_id, null);
	}
*/	
/*		
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
	
	*/
	
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
