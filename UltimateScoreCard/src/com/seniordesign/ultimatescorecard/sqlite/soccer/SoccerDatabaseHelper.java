package com.seniordesign.ultimatescorecard.sqlite.soccer;


import java.util.ArrayList;
import java.util.List;

import com.seniordesign.ultimatescorecard.data.soccer.SoccerPlayer;
import com.seniordesign.ultimatescorecard.sqlite.helper.DatabaseHelper;
import com.seniordesign.ultimatescorecard.sqlite.helper.Games;
import com.seniordesign.ultimatescorecard.sqlite.helper.Players;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class SoccerDatabaseHelper extends DatabaseHelper{

	// Logcat tag
    private static final String LOG = "SoccerDatabaseHelper";
 
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "SoccerStats";
	
    //Table Names
    private static final String TABLE_SOCCER_GAME_STATS = "soccer_game_stats";
    
    //GAMES Table - column names
    private static final String KEY_HOME_SHOTS = "home_shots";
    private static final String KEY_HOME_SOG = "home_sog";
    private static final String KEY_HOME_GOALS = "home_goals";
    private static final String KEY_HOME_SAVES = "home_saves";
    private static final String KEY_HOME_GOALS_ALLOWED = "home_goals_allowed";
    private static final String KEY_HOME_AST = "home_ast";
    private static final String KEY_HOME_FOULS = "home_fouls";
    private static final String KEY_HOME_PKA = "home_pka";
    private static final String KEY_HOME_PKG = "home_pkg";
    private static final String KEY_HOME_YCARD = "home_ycard";
    private static final String KEY_HOME_RCARD = "home_rcard";
    
    private static final String KEY_AWAY_SHOTS = "away_shots";
    private static final String KEY_AWAY_SOG = "away_sog";
    private static final String KEY_AWAY_GOALS = "away_goals";
    private static final String KEY_AWAY_SAVES = "away_saves";
    private static final String KEY_AWAY_GOALS_ALLOWED = "away_goals_allowed";
    private static final String KEY_AWAY_AST = "away_ast";
    private static final String KEY_AWAY_FOULS = "away_fouls";
    private static final String KEY_AWAY_PKA = "away_pka";
    private static final String KEY_AWAY_PKG = "away_pkg";
    private static final String KEY_AWAY_YCARD = "away_ycard";
    private static final String KEY_AWAY_RCARD = "away_rcard";
    
    //SOCCERGAMESTATS - common column names
    private static final String KEY_SHOTS = "shots";
    private static final String KEY_SOG = "sog";
    private static final String KEY_GOALS = "goals";
    private static final String KEY_SAVES = "saves";
    private static final String KEY_GOALS_ALLOWED = "goals_allowed";
    private static final String KEY_AST = "ast";
    private static final String KEY_FOULS = "fouls";
    private static final String KEY_PKA = "pka";
    private static final String KEY_PKG = "pkg";
    private static final String KEY_YCARD = "ycard";
    private static final String KEY_RCARD = "rcard";
 
    //Table Create Statements
    //GAMES table create statement
    private static final String CREATE_TABLE_GAMES = "CREATE TABLE IF NOT EXISTS " + TABLE_GAMES 
    		+ "(" + KEY_G_ID + " INTEGER PRIMARY KEY," + KEY_HOME_ID + " INTEGER," 
    		+ KEY_AWAY_ID + " INTEGER," + KEY_DATE + " DATE, "  
			
    		+ KEY_HOME_SHOTS + " INTEGER, " + KEY_HOME_SOG + " INTEGER, " + KEY_HOME_GOALS + " INTEGER, "
			+ KEY_HOME_AST + " INTEGER, " + KEY_HOME_FOULS + " INTEGER, " + KEY_HOME_PKA + " INTEGER, "
			+ KEY_HOME_PKG + " INTEGER, " + KEY_HOME_YCARD + " INTEGER, " + KEY_HOME_RCARD + " INTEGER, "
			+ KEY_HOME_SAVES + " INTEGER, " + KEY_HOME_GOALS_ALLOWED + " INTEGER, "
    
			+ KEY_AWAY_SHOTS + " INTEGER, " + KEY_AWAY_SOG + " INTEGER, " + KEY_AWAY_GOALS + " INTEGER, "
			+ KEY_AWAY_AST + " INTEGER, " + KEY_AWAY_FOULS + " INTEGER, " + KEY_AWAY_PKA + " INTEGER, "
			+ KEY_AWAY_PKG + " INTEGER, " + KEY_AWAY_YCARD + " INTEGER, " + KEY_AWAY_RCARD + " INTEGER, "
			+ KEY_AWAY_SAVES + " INTEGER, " + KEY_AWAY_GOALS_ALLOWED + " INTEGER"
			+ ")";
    //Doesn't include SPORT yet...
    
    //SOCCER_GAME_STATS table create statement
    private static final String CREATE_TABLE_SOCCER_GAME_STATS = "CREATE TABLE IF NOT EXISTS " + TABLE_SOCCER_GAME_STATS 
    		+ "(" + KEY_G_ID + " INTEGER" + ", " + KEY_P_ID + " INTEGER" + ", "
    		+ KEY_SHOTS + " INTEGER, " + KEY_SOG + " INTEGER, " + KEY_GOALS + " INTEGER, "
    		+ KEY_AST + " INTEGER, " + KEY_FOULS + " INTEGER, " + KEY_PKA + " INTEGER, "
    		+ KEY_PKG + " INTEGER, " + KEY_YCARD + " INTEGER, " + KEY_RCARD + " INTEGER, "
    		+ KEY_SAVES + " INTEGER, " + KEY_GOALS_ALLOWED + " INTEGER"
    		+ ")"; 
    
    public SoccerDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
	public SoccerDatabaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
        db.execSQL(CREATE_TABLE_GAMES);
        db.execSQL(CREATE_TABLE_SOCCER_GAME_STATS);
        db.execSQL(CREATE_TABLE_PLAYERS);
        db.execSQL(CREATE_TABLE_TEAMS);
        db.execSQL(CREATE_TABLE_PLAY_BY_PLAY);
        db.execSQL(CREATE_TABLE_SHOT_CHART_COORDS);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GAMES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SOCCER_GAME_STATS);
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYERS);
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEAMS);
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
        values.put(KEY_HOME_FOULS, 0);
        values.put(KEY_HOME_PKA, 0);
        values.put(KEY_HOME_PKG, 0);
        values.put(KEY_HOME_YCARD, 0);
        values.put(KEY_HOME_RCARD, 0);
        values.put(KEY_HOME_SAVES,0);
        values.put(KEY_HOME_GOALS_ALLOWED, 0);
        
        
        values.put(KEY_AWAY_SHOTS, 0);
        values.put(KEY_AWAY_SOG, 0);
        values.put(KEY_AWAY_GOALS, 0);
        values.put(KEY_AWAY_AST, 0);
        values.put(KEY_AWAY_FOULS, 0);
        values.put(KEY_AWAY_PKA, 0);
        values.put(KEY_AWAY_PKG, 0);
        values.put(KEY_AWAY_YCARD, 0);
        values.put(KEY_AWAY_RCARD, 0);
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
	    SoccerGames game = new SoccerGames();
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
		    game.sethomefouls((c.getInt(c.getColumnIndex(KEY_HOME_FOULS))));
		    game.sethomepka(c.getInt(c.getColumnIndex(KEY_HOME_PKA)));
		    game.sethomepkg((c.getInt(c.getColumnIndex(KEY_HOME_PKG))));
		    game.sethomeycard(c.getInt(c.getColumnIndex(KEY_HOME_YCARD)));
		    game.sethomercard((c.getInt(c.getColumnIndex(KEY_HOME_RCARD))));
		    game.sethomesaves((c.getInt(c.getColumnIndex(KEY_HOME_SAVES))));
		    game.sethomegoalsallowed((c.getInt(c.getColumnIndex(KEY_HOME_GOALS_ALLOWED))));
	
		    game.setawayshots((c.getInt(c.getColumnIndex(KEY_AWAY_SHOTS))));
		    game.setawaysog((c.getInt(c.getColumnIndex(KEY_AWAY_SOG))));
		    game.setawaygoals(c.getInt(c.getColumnIndex(KEY_AWAY_GOALS)));
		    game.setawayast((c.getInt(c.getColumnIndex(KEY_AWAY_AST))));
		    game.setawayfouls((c.getInt(c.getColumnIndex(KEY_AWAY_FOULS))));
		    game.setawaypka(c.getInt(c.getColumnIndex(KEY_AWAY_PKA)));
		    game.setawaypkg((c.getInt(c.getColumnIndex(KEY_AWAY_PKG))));
		    game.setawayycard(c.getInt(c.getColumnIndex(KEY_AWAY_YCARD)));
		    game.setawayrcard((c.getInt(c.getColumnIndex(KEY_AWAY_RCARD))));
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
	        	SoccerGames game = new SoccerGames();
	    	    game.setgid(c.getLong(c.getColumnIndex(KEY_G_ID)));
	    	    game.sethomeid((c.getLong(c.getColumnIndex(KEY_HOME_ID))));
	    	    game.setawayid((c.getLong(c.getColumnIndex(KEY_AWAY_ID))));
	    	    game.setDate(c.getString(c.getColumnIndex(KEY_DATE)));
	            
	    	    game.sethomeshots((c.getInt(c.getColumnIndex(KEY_HOME_SHOTS))));
	    	    game.sethomesog((c.getInt(c.getColumnIndex(KEY_HOME_SOG))));
	    	    game.sethomegoals(c.getInt(c.getColumnIndex(KEY_HOME_GOALS)));
	    	    game.sethomeast((c.getInt(c.getColumnIndex(KEY_HOME_AST))));
	    	    game.sethomefouls((c.getInt(c.getColumnIndex(KEY_HOME_FOULS))));
	    	    game.sethomepka(c.getInt(c.getColumnIndex(KEY_HOME_PKA)));
	    	    game.sethomepkg((c.getInt(c.getColumnIndex(KEY_HOME_PKG))));
	    	    game.sethomeycard(c.getInt(c.getColumnIndex(KEY_HOME_YCARD)));
	    	    game.sethomercard((c.getInt(c.getColumnIndex(KEY_HOME_RCARD))));
	    	    game.sethomesaves((c.getInt(c.getColumnIndex(KEY_HOME_SAVES))));
	    	    game.sethomegoalsallowed((c.getInt(c.getColumnIndex(KEY_HOME_GOALS_ALLOWED))));

	    	    game.setawayshots((c.getInt(c.getColumnIndex(KEY_AWAY_SHOTS))));
	    	    game.setawaysog((c.getInt(c.getColumnIndex(KEY_AWAY_SOG))));
	    	    game.setawaygoals(c.getInt(c.getColumnIndex(KEY_AWAY_GOALS)));
	    	    game.setawayast((c.getInt(c.getColumnIndex(KEY_AWAY_AST))));
	    	    game.setawayfouls((c.getInt(c.getColumnIndex(KEY_AWAY_FOULS))));
	    	    game.setawaypka(c.getInt(c.getColumnIndex(KEY_AWAY_PKA)));
	    	    game.setawaypkg((c.getInt(c.getColumnIndex(KEY_AWAY_PKG))));
	    	    game.setawayycard(c.getInt(c.getColumnIndex(KEY_AWAY_YCARD)));
	    	    game.setawayrcard((c.getInt(c.getColumnIndex(KEY_AWAY_RCARD))));
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
	        	SoccerGames game = new SoccerGames();
	    	    game.setgid(c.getLong(c.getColumnIndex(KEY_G_ID)));
	    	    game.sethomeid((c.getLong(c.getColumnIndex(KEY_HOME_ID))));
	    	    game.setawayid((c.getLong(c.getColumnIndex(KEY_AWAY_ID))));
	    	    game.setDate(c.getString(c.getColumnIndex(KEY_DATE)));
	            
	    	    game.sethomeshots((c.getInt(c.getColumnIndex(KEY_HOME_SHOTS))));
	    	    game.sethomesog((c.getInt(c.getColumnIndex(KEY_HOME_SOG))));
	    	    game.sethomegoals(c.getInt(c.getColumnIndex(KEY_HOME_GOALS)));
	    	    game.sethomeast((c.getInt(c.getColumnIndex(KEY_HOME_AST))));
	    	    game.sethomefouls((c.getInt(c.getColumnIndex(KEY_HOME_FOULS))));
	    	    game.sethomepka(c.getInt(c.getColumnIndex(KEY_HOME_PKA)));
	    	    game.sethomepkg((c.getInt(c.getColumnIndex(KEY_HOME_PKG))));
	    	    game.sethomeycard(c.getInt(c.getColumnIndex(KEY_HOME_YCARD)));
	    	    game.sethomercard((c.getInt(c.getColumnIndex(KEY_HOME_RCARD))));
	    	    game.sethomesaves((c.getInt(c.getColumnIndex(KEY_HOME_SAVES))));
	    	    game.sethomegoalsallowed((c.getInt(c.getColumnIndex(KEY_HOME_GOALS_ALLOWED))));

	    	    game.setawayshots((c.getInt(c.getColumnIndex(KEY_AWAY_SHOTS))));
	    	    game.setawaysog((c.getInt(c.getColumnIndex(KEY_AWAY_SOG))));
	    	    game.setawaygoals(c.getInt(c.getColumnIndex(KEY_AWAY_GOALS)));
	    	    game.setawayast((c.getInt(c.getColumnIndex(KEY_AWAY_AST))));
	    	    game.setawayfouls((c.getInt(c.getColumnIndex(KEY_AWAY_FOULS))));
	    	    game.setawaypka(c.getInt(c.getColumnIndex(KEY_AWAY_PKA)));
	    	    game.setawaypkg((c.getInt(c.getColumnIndex(KEY_AWAY_PKG))));
	    	    game.setawayycard(c.getInt(c.getColumnIndex(KEY_AWAY_YCARD)));
	    	    game.setawayrcard((c.getInt(c.getColumnIndex(KEY_AWAY_RCARD))));
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
		    SoccerGames game = (SoccerGames) getGame(g_id);
		    
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
		    if(stat==KEY_HOME_FOULS)
		    	values.put(KEY_HOME_FOULS, new_value);
		    else
		    	values.put(KEY_HOME_FOULS, game.gethomefouls());
		    if(stat==KEY_HOME_PKA)
		    	values.put(KEY_HOME_PKA, new_value);
		    else
		    	values.put(KEY_HOME_PKA, game.gethomepka());
		    if(stat==KEY_HOME_PKG)
		    	values.put(KEY_HOME_PKG, new_value);
		    else
		    	values.put(KEY_HOME_PKG, game.gethomepkg());
		    if(stat==KEY_HOME_YCARD)
		    	values.put(KEY_HOME_YCARD, new_value);
		    else
		    	values.put(KEY_HOME_YCARD, game.gethomeycard());
		    if(stat==KEY_HOME_RCARD)
		    	values.put(KEY_HOME_RCARD, new_value);
		    else
		    	values.put(KEY_HOME_RCARD, game.gethomercard());
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
		    if(stat==KEY_AWAY_FOULS)
		    	values.put(KEY_AWAY_FOULS, new_value);
		    else
		    	values.put(KEY_AWAY_FOULS, game.getawayfouls());
		    if(stat==KEY_AWAY_PKA)
		    	values.put(KEY_AWAY_PKA, new_value);
		    else
		    	values.put(KEY_AWAY_PKA, game.getawaypka());
		    if(stat==KEY_AWAY_PKG)
		    	values.put(KEY_AWAY_PKG, new_value);
		    else
		    	values.put(KEY_AWAY_PKG, game.getawaypkg());
		    if(stat==KEY_AWAY_YCARD)
		    	values.put(KEY_AWAY_YCARD, new_value);
		    else
		    	values.put(KEY_AWAY_YCARD, game.getawayycard());
		    if(stat==KEY_AWAY_RCARD)
		    	values.put(KEY_AWAY_RCARD, new_value);
		    else
		    	values.put(KEY_AWAY_RCARD, game.getawayrcard());
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
        values.put(KEY_FOULS, 0);
        values.put(KEY_PKA, 0);
        values.put(KEY_PKG, 0);
        values.put(KEY_YCARD, 0);
        values.put(KEY_RCARD, 0);
        values.put(KEY_SAVES,0);
        values.put(KEY_GOALS_ALLOWED, 0);
        //insert more stats here
        
        // insert row
        db.insert(TABLE_SOCCER_GAME_STATS, null, values);
	}
	
	//get single game stats for single player
	public SoccerGameStats getPlayerGameStats(long g_id, long p_id) {
	    SQLiteDatabase db = this.getReadableDatabase();
	    //create query to select game
	    String selectQuery = "SELECT  * FROM " + TABLE_SOCCER_GAME_STATS + 
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
	    SoccerGameStats stats = new SoccerGameStats();
	    stats.setgid(c.getLong(c.getColumnIndex(KEY_G_ID)));
	    stats.setpid(c.getLong(c.getColumnIndex(KEY_P_ID)));
	    stats.setshots((c.getInt(c.getColumnIndex(KEY_SHOTS))));
	    stats.setsog((c.getInt(c.getColumnIndex(KEY_SOG))));
	    stats.setgoals(c.getInt(c.getColumnIndex(KEY_GOALS)));
	    stats.setast((c.getInt(c.getColumnIndex(KEY_AST))));
	    stats.setfouls((c.getInt(c.getColumnIndex(KEY_FOULS))));
	    stats.setpka(c.getInt(c.getColumnIndex(KEY_PKA)));
	    stats.setpkg((c.getInt(c.getColumnIndex(KEY_PKG))));
	    stats.setycard(c.getInt(c.getColumnIndex(KEY_YCARD)));
	    stats.setrcard((c.getInt(c.getColumnIndex(KEY_RCARD))));
	    stats.setsaves((c.getInt(c.getColumnIndex(KEY_SAVES))));
	    stats.setgoalsallowed((c.getInt(c.getColumnIndex(KEY_GOALS_ALLOWED))));

	    //Insert more stats here
	    
	    return stats;
	}
	
	//get single game stats for single player
	public int getPlayerGameStat(long g_id, long p_id, String stat) {
	    SQLiteDatabase db = this.getReadableDatabase();
	    //create query to select game
	    String selectQuery = "SELECT " + stat + " FROM " + TABLE_SOCCER_GAME_STATS + 
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
	public List<SoccerGameStats> getPlayerAllGameStats(long p_id) {
	    List<SoccerGameStats> gameStats = new ArrayList<SoccerGameStats>();
	    SQLiteDatabase db = this.getReadableDatabase();
	    String selectQuery = "SELECT  * FROM " + TABLE_SOCCER_GAME_STATS
	    		+ " WHERE " + KEY_P_ID + " = " + p_id ;
	 
	    Log.i(LOG, selectQuery);
	 
	    Cursor c = db.rawQuery(selectQuery, null);
	 
	    // looping through all rows and adding to list
	    if (c.moveToFirst()) {
	        do {
			    //create the instance of Games using cursor information
	    	    SoccerGameStats stats = new SoccerGameStats();
	    	    stats.setgid(c.getLong(c.getColumnIndex(KEY_G_ID)));
	    	    stats.setpid(c.getLong(c.getColumnIndex(KEY_P_ID)));
	    	    stats.setshots((c.getInt(c.getColumnIndex(KEY_SHOTS))));
	    	    stats.setsog((c.getInt(c.getColumnIndex(KEY_SOG))));
	    	    stats.setgoals(c.getInt(c.getColumnIndex(KEY_GOALS)));
	    	    stats.setast((c.getInt(c.getColumnIndex(KEY_AST))));
	    	    stats.setfouls((c.getInt(c.getColumnIndex(KEY_FOULS))));
	    	    stats.setpka(c.getInt(c.getColumnIndex(KEY_PKA)));
	    	    stats.setpkg((c.getInt(c.getColumnIndex(KEY_PKG))));
	    	    stats.setycard(c.getInt(c.getColumnIndex(KEY_YCARD)));
	    	    stats.setrcard((c.getInt(c.getColumnIndex(KEY_RCARD))));
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
	public List<SoccerGameStats> getAllGameStats() {
	    List<SoccerGameStats> gameStats = new ArrayList<SoccerGameStats>();
	    SQLiteDatabase db = this.getReadableDatabase();
	    String selectQuery = "SELECT  * FROM " + TABLE_SOCCER_GAME_STATS;
	 
	    Log.i(LOG, selectQuery);
	 
	    Cursor c = db.rawQuery(selectQuery, null);
	 
	    // looping through all rows and adding to list
	    if (c.moveToFirst()) {
	        do {
			    //create the instance of Games using cursor information
	    	    SoccerGameStats stats = new SoccerGameStats();
	    	    stats.setgid(c.getLong(c.getColumnIndex(KEY_G_ID)));
	    	    stats.setpid(c.getLong(c.getColumnIndex(KEY_P_ID)));
	    	    stats.setshots((c.getInt(c.getColumnIndex(KEY_SHOTS))));
	    	    stats.setsog((c.getInt(c.getColumnIndex(KEY_SOG))));
	    	    stats.setgoals(c.getInt(c.getColumnIndex(KEY_GOALS)));
	    	    stats.setast((c.getInt(c.getColumnIndex(KEY_AST))));
	    	    stats.setfouls((c.getInt(c.getColumnIndex(KEY_FOULS))));
	    	    stats.setpka(c.getInt(c.getColumnIndex(KEY_PKA)));
	    	    stats.setpkg((c.getInt(c.getColumnIndex(KEY_PKG))));
	    	    stats.setycard(c.getInt(c.getColumnIndex(KEY_YCARD)));
	    	    stats.setrcard((c.getInt(c.getColumnIndex(KEY_RCARD))));
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
	    db.delete(TABLE_SOCCER_GAME_STATS, KEY_G_ID + " = ?",
	            new String[] { String.valueOf(g_id) });
	}
	
	//ADDING STATS
	
	//Adding value to points category of a player
	public int addStats(long g_id, long p_id, String stat, int value){
	    SQLiteDatabase db = this.getWritableDatabase();
	    SoccerGameStats stats = getPlayerGameStats(g_id, p_id);
	    
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
	    if(stat==KEY_FOULS)
	    	values.put(KEY_FOULS, new_value);
	    else
	    	values.put(KEY_FOULS, stats.getfouls());
	    if(stat==KEY_PKA)
	    	values.put(KEY_PKA, new_value);
	    else
	    	values.put(KEY_PKA, stats.getpka());
	    if(stat==KEY_PKG)
	    	values.put(KEY_PKG, new_value);
	    else
	    	values.put(KEY_PKG, stats.getpkg());
	    if(stat==KEY_YCARD)
	    	values.put(KEY_YCARD, new_value);
	    else
	    	values.put(KEY_YCARD, stats.getycard());
	    if(stat==KEY_RCARD)
	    	values.put(KEY_RCARD, new_value);
	    else
	    	values.put(KEY_RCARD, stats.getrcard());
	    if(stat==KEY_SAVES)
	    	values.put(KEY_SAVES, new_value);
	    else
	    	values.put(KEY_SAVES, stats.getsaves());
	    if(stat==KEY_GOALS_ALLOWED)
	    	values.put(KEY_GOALS_ALLOWED, new_value);
	    else
	    	values.put(KEY_GOALS_ALLOWED, stats.getgoalsallowed());
        //insert more stats here
        
	    return db.update(TABLE_SOCCER_GAME_STATS,  values, KEY_P_ID + " = " + p_id + " AND " + KEY_G_ID + " = " + g_id, null);
	}
	
	// ----------------------- PLAYERS table methods ------------------------- //

	public long createPlayers(SoccerPlayer player){
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
	public SoccerPlayer getPlayer(long p_id) {
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
	    SoccerPlayer player = new SoccerPlayer();
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
