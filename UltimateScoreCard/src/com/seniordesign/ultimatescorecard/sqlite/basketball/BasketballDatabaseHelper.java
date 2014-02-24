package com.seniordesign.ultimatescorecard.sqlite.basketball;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.seniordesign.ultimatescorecard.data.basketball.BasketballPlayer;
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

public class BasketballDatabaseHelper extends DatabaseHelper implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3785769973811870982L;

	// Logcat tag
    private static final String LOG = "BasketballDatabaseHelper";
 
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "BasketballStats";
	
    //Table Names
    private static final String TABLE_GAMES = "games";
    private static final String TABLE_BASKETBALL_GAME_STATS = "basketball_game_stats";
    private static final String TABLE_PLAYERS = "players";
    private static final String TABLE_TEAMS = "teams";
    private static final String TABLE_PLAY_BY_PLAY = "play_by_play";
    private static final String TABLE_SHOT_CHART_COORDS = "shot_chart_coords";


    //Common Column Names
    private static final String KEY_G_ID = "g_id";
    private static final String KEY_P_ID = "p_id";
    private static final String KEY_T_ID = "t_id";
    private static final String KEY_A_ID = "a_id";
    private static final String KEY_PERIOD = "period";

    //GAMES Table - column names
    private static final String KEY_HOME_ID = "home_id";
    private static final String KEY_AWAY_ID = "away_id";
    private static final String KEY_DATE = "date";
    
    //BASKETBALLGAMESTATS Table - column names
    private static final String KEY_PTS = "pts";
    private static final String KEY_FGM = "fgm";
    private static final String KEY_FGA = "fga";
    private static final String KEY_FGM3 = "fgm3";
    private static final String KEY_FGA3 = "fga3";
    private static final String KEY_FTM = "ftm";
    private static final String KEY_FTA = "fta";
    private static final String KEY_OREB = "oreb";
    private static final String KEY_DREB = "dreb";
    private static final String KEY_AST = "ast";
    private static final String KEY_STL = "stl";
    private static final String KEY_BLK = "blk";
    private static final String KEY_TO = "turnover";
    private static final String KEY_PF = "pf";
    private static final String KEY_TECH = "tech";
    private static final String KEY_FLAGRANT = "flagrant";

    
    //PLAYERS Table - column names
    private static final String KEY_P_NAME = "p_name";
    private static final String KEY_P_NUM = "p_num";

    //TEAMS Table - column names
    private static final String KEY_T_NAME = "t_name";
    private static final String KEY_C_NAME = "c_name";
    private static final String KEY_SPORT = "sport";
    private static final String KEY_ABBV = "abbv";

    
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

    //BASKETBALL_GAME_STATS table create statement
    private static final String CREATE_TABLE_BASKETBALL_GAME_STATS = "CREATE TABLE IF NOT EXISTS " + TABLE_BASKETBALL_GAME_STATS 
    		+ "(" + KEY_G_ID + " INTEGER" +
    		//		"FOREIGN KEY REFERENCES" + TABLE_GAMES + "(" + KEY_G_ID + "),"
    		", "
    		+ KEY_P_ID + " INTEGER" +
    		//" INTEGER FOREIGN KEY REFERENCES" + TABLE_PLAYERS + "(" + KEY_P_ID + ")," 
    		", "
    		+ KEY_PTS + " INTEGER, " + KEY_FGM + " INTEGER, " + KEY_FGA + " INTEGER, "
    		//+ "," + "CONSTRAINT pk_player_game PRIMARY KEY (" + KEY_P_ID + "," + KEY_G_ID + ")" 
    		+ KEY_FGM3 + " INTEGER, " + KEY_FGA3 + " INTEGER, " + KEY_FTM + " INTEGER, "
    		+ KEY_FTA + " INTEGER, " + KEY_OREB + " INTEGER, " + KEY_DREB + " INTEGER, "
    		+ KEY_AST + " INTEGER, " + KEY_STL + " INTEGER, " + KEY_BLK + " INTEGER, "
    		+ KEY_TO + " INTEGER, " + KEY_PF + " INTEGER, " + KEY_TECH + " INTEGER, "
    		+ KEY_FLAGRANT + " INTEGER " + ")"; 
    
    //PLAYERS table create statement
    private static final String CREATE_TABLE_PLAYERS = "CREATE TABLE IF NOT EXISTS " + TABLE_PLAYERS 
    		+ "(" + KEY_P_ID + " INTEGER PRIMARY KEY," 
    		+ KEY_T_ID + " INTEGER, "
    		// + FOREIGN KEY REFERENCES " + TABLE_TEAMS + "(" + KEY_T_ID + ")," 
    		+ KEY_P_NAME + " VARCHAR(45)," + KEY_P_NUM + " INTEGER" + ")"; 
    
    //TEAMS table create statement
    private static final String CREATE_TABLE_TEAMS = "CREATE TABLE IF NOT EXISTS " + TABLE_TEAMS 
    		+ "(" + KEY_T_ID + " INTEGER PRIMARY KEY," + KEY_T_NAME + " VARCHAR(45)," 
    		+ KEY_ABBV + " VARCHAR(45),"+ KEY_C_NAME + " VARCHAR(45),"+ KEY_SPORT + " VARCHAR(45)" + ")"; 
    
    //PLAY_BY_PLAY table create statement
    private static final String CREATE_TABLE_PLAY_BY_PLAY = "CREATE TABLE IF NOT EXISTS " + TABLE_PLAY_BY_PLAY 
    		+ "(" + KEY_A_ID + " INTEGER PRIMARY KEY," + KEY_G_ID + " INTEGER," 
    		+ KEY_ACTION + " VARCHAR(45)," + KEY_TIME + " VARCHAR(45)," + KEY_PERIOD + " VARCHAR(10)," + KEY_HOME_SCORE + " INTEGER, " 
    		+ KEY_AWAY_SCORE + " INTEGER" + ")";
    
    //SHOT_CHART_COORDS table create statement
    private static final String CREATE_TABLE_SHOT_CHART_COORDS = "CREATE TABLE IF NOT EXISTS " + TABLE_SHOT_CHART_COORDS 
    		+ "(" + KEY_A_ID + " INTEGER PRIMARY KEY," + KEY_G_ID + " INTEGER," 
    		+ KEY_P_ID + " INTEGER," + KEY_X + " INTEGER," + KEY_Y + " INTEGER," 
    		+ KEY_MADE + " VARCHAR(4)" + ")";
    
    public BasketballDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
	public BasketballDatabaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
        db.execSQL(CREATE_TABLE_GAMES);
        db.execSQL(CREATE_TABLE_BASKETBALL_GAME_STATS);
        db.execSQL(CREATE_TABLE_PLAYERS);
        db.execSQL(CREATE_TABLE_TEAMS);
        db.execSQL(CREATE_TABLE_PLAY_BY_PLAY);
        db.execSQL(CREATE_TABLE_SHOT_CHART_COORDS);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GAMES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BASKETBALL_GAME_STATS);
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
 
        // insert row
        long g_id = db.insert(TABLE_GAMES, null, values);
        
        List<Players> home_players = getPlayersTeam2(game.gethomeid());
        List<Players> away_players = getPlayersTeam2(game.getawayid());

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
		deleteGameStats(g_id);
	    SQLiteDatabase db = this.getWritableDatabase();
	    db.delete(TABLE_GAMES, KEY_G_ID + " = ?",
	            new String[] { String.valueOf(g_id) });
	}
	
	
	// ----------------------- GAME_STATS table methods ------------------------- //
	
	// ----------------------- BASKETBALL_GAME_STATS ---------------------------- //

	public void createGameStats(long p_id, long g_id){
		SQLiteDatabase db = this.getWritableDatabase();
		 
        ContentValues values = new ContentValues();
        values.put(KEY_P_ID, p_id);
        values.put(KEY_G_ID, g_id);
        values.put(KEY_PTS, 0);
        values.put(KEY_FGM, 0);
        values.put(KEY_FGA, 0);
        values.put(KEY_FGM3, 0);
        values.put(KEY_FGA3, 0);
        values.put(KEY_FTM, 0);
        values.put(KEY_FTA, 0);
        values.put(KEY_OREB, 0);
        values.put(KEY_DREB, 0);
        values.put(KEY_AST, 0);
        values.put(KEY_STL, 0);
        values.put(KEY_BLK, 0);
        values.put(KEY_TO, 0);
        values.put(KEY_PF, 0);
        values.put(KEY_TECH, 0);
        values.put(KEY_FLAGRANT, 0);

        //insert more stats here
        
        // insert row
        db.insert(TABLE_BASKETBALL_GAME_STATS, null, values);
	}
	
		//get single game stats for single player
		public BasketballGameStats getPlayerGameStats(long g_id, long p_id) {
		    SQLiteDatabase db = this.getReadableDatabase();
		    //create query to select game
		    String selectQuery = "SELECT  * FROM " + TABLE_BASKETBALL_GAME_STATS + 
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
		    BasketballGameStats stats = new BasketballGameStats();
		    stats.setgid(c.getLong(c.getColumnIndex(KEY_G_ID)));
		    stats.setpid(c.getLong(c.getColumnIndex(KEY_P_ID)));
		    stats.setpts((c.getInt(c.getColumnIndex(KEY_PTS))));
		    stats.setfgm((c.getInt(c.getColumnIndex(KEY_FGM))));
		    stats.setfga(c.getInt(c.getColumnIndex(KEY_FGA)));
		    stats.setfgm3(c.getInt(c.getColumnIndex(KEY_FGM3)));
		    stats.setfga3(c.getInt(c.getColumnIndex(KEY_FGA3)));
		    stats.setftm(c.getInt(c.getColumnIndex(KEY_FTM)));
		    stats.setfta(c.getInt(c.getColumnIndex(KEY_FTA)));
		    stats.setoreb(c.getInt(c.getColumnIndex(KEY_OREB)));
		    stats.setdreb(c.getInt(c.getColumnIndex(KEY_DREB)));
		    stats.setast(c.getInt(c.getColumnIndex(KEY_AST)));
		    stats.setstl(c.getInt(c.getColumnIndex(KEY_STL)));
		    stats.setblk(c.getInt(c.getColumnIndex(KEY_BLK)));
		    stats.setto(c.getInt(c.getColumnIndex(KEY_TO)));
		    stats.setpf(c.getInt(c.getColumnIndex(KEY_PF)));
		    stats.settech(c.getInt(c.getColumnIndex(KEY_TECH)));
		    stats.setflagrant(c.getInt(c.getColumnIndex(KEY_FLAGRANT)));

		    //Insert more stats here
		    
		    return stats;
		}
		
		//get single game stat for single player
		public int getPlayerGameStat(long g_id, long p_id, String stat) {
		    SQLiteDatabase db = this.getReadableDatabase();
		    //create query to select game
		    String selectQuery = "SELECT " + stat + " FROM " + TABLE_BASKETBALL_GAME_STATS + 
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
		public List<BasketballGameStats> getPlayerAllGameStats(long p_id) {
		    List<BasketballGameStats> gameStats = new ArrayList<BasketballGameStats>();
		    SQLiteDatabase db = this.getReadableDatabase();
		    String selectQuery = "SELECT  * FROM " + TABLE_BASKETBALL_GAME_STATS
		    		+ " WHERE " + KEY_P_ID + " = " + p_id ;
		 
		    Log.i(LOG, selectQuery);
		 
		    Cursor c = db.rawQuery(selectQuery, null);
		 
		    // looping through all rows and adding to list
		    if (c.moveToFirst()) {
		        do {
				    //create the instance of Games using cursor information
				    BasketballGameStats stats = new BasketballGameStats();
				    stats.setgid(c.getLong(c.getColumnIndex(KEY_G_ID)));
				    stats.setpid(c.getLong(c.getColumnIndex(KEY_P_ID)));
				    stats.setpts((c.getInt(c.getColumnIndex(KEY_PTS))));
				    stats.setfgm((c.getInt(c.getColumnIndex(KEY_FGM))));
				    stats.setfga(c.getInt(c.getColumnIndex(KEY_FGA)));
				    stats.setfgm3(c.getInt(c.getColumnIndex(KEY_FGM3)));
				    stats.setfga3(c.getInt(c.getColumnIndex(KEY_FGA3)));
				    stats.setftm(c.getInt(c.getColumnIndex(KEY_FTM)));
				    stats.setfta(c.getInt(c.getColumnIndex(KEY_FTA)));
				    stats.setoreb(c.getInt(c.getColumnIndex(KEY_OREB)));
				    stats.setdreb(c.getInt(c.getColumnIndex(KEY_DREB)));
				    stats.setast(c.getInt(c.getColumnIndex(KEY_AST)));
				    stats.setstl(c.getInt(c.getColumnIndex(KEY_STL)));
				    stats.setblk(c.getInt(c.getColumnIndex(KEY_BLK)));
				    stats.setto(c.getInt(c.getColumnIndex(KEY_TO)));
				    stats.setpf(c.getInt(c.getColumnIndex(KEY_PF)));
				    stats.settech(c.getInt(c.getColumnIndex(KEY_TECH)));
				    stats.setflagrant(c.getInt(c.getColumnIndex(KEY_FLAGRANT)));

				    //Insert more stats here
				    
		            // adding to gameStats list
		            gameStats.add(stats);
		        } while (c.moveToNext());
		    }
		 
		    return gameStats;
		}
		
		//Get all GameStats
		public List<BasketballGameStats> getAllGameStats() {
		    List<BasketballGameStats> gameStats = new ArrayList<BasketballGameStats>();
		    SQLiteDatabase db = this.getReadableDatabase();
		    String selectQuery = "SELECT  * FROM " + TABLE_BASKETBALL_GAME_STATS;
		 
		    Log.i(LOG, selectQuery);
		 
		    Cursor c = db.rawQuery(selectQuery, null);
		 
		    // looping through all rows and adding to list
		    if (c.moveToFirst()) {
		        do {
				    //create the instance of Games using cursor information
				    BasketballGameStats stats = new BasketballGameStats();
				    stats.setgid(c.getLong(c.getColumnIndex(KEY_G_ID)));
				    stats.setpid(c.getLong(c.getColumnIndex(KEY_P_ID)));
				    stats.setgid(c.getLong(c.getColumnIndex(KEY_G_ID)));
				    stats.setpid(c.getLong(c.getColumnIndex(KEY_P_ID)));
				    stats.setpts((c.getInt(c.getColumnIndex(KEY_PTS))));
				    stats.setfgm((c.getInt(c.getColumnIndex(KEY_FGM))));
				    stats.setfga(c.getInt(c.getColumnIndex(KEY_FGA)));
				    stats.setfgm3(c.getInt(c.getColumnIndex(KEY_FGM3)));
				    stats.setfga3(c.getInt(c.getColumnIndex(KEY_FGA3)));
				    stats.setftm(c.getInt(c.getColumnIndex(KEY_FTM)));
				    stats.setfta(c.getInt(c.getColumnIndex(KEY_FTA)));
				    stats.setoreb(c.getInt(c.getColumnIndex(KEY_OREB)));
				    stats.setdreb(c.getInt(c.getColumnIndex(KEY_DREB)));
				    stats.setast(c.getInt(c.getColumnIndex(KEY_AST)));
				    stats.setstl(c.getInt(c.getColumnIndex(KEY_STL)));
				    stats.setblk(c.getInt(c.getColumnIndex(KEY_BLK)));
				    stats.setto(c.getInt(c.getColumnIndex(KEY_TO)));
				    stats.setpf(c.getInt(c.getColumnIndex(KEY_PF)));
				    stats.settech(c.getInt(c.getColumnIndex(KEY_TECH)));
				    stats.setflagrant(c.getInt(c.getColumnIndex(KEY_FLAGRANT)));
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
		    db.delete(TABLE_BASKETBALL_GAME_STATS, KEY_G_ID + " = ?",
		            new String[] { String.valueOf(g_id) });
		}
		
		//ADDING STATS
		
		//Adding value to points category of a player
		public int addStats(long g_id, long p_id, String stat, int value){
		    SQLiteDatabase db = this.getWritableDatabase();
		    BasketballGameStats stats = getPlayerGameStats(g_id, p_id);
		    
		    int old_value = getPlayerGameStat(g_id,p_id,stat);
		    int new_value = old_value + value;
		    
		    ContentValues values = new ContentValues();
		    	
		    values.put(KEY_P_ID, p_id);
	        values.put(KEY_G_ID, g_id);
		    if(stat==KEY_PTS)
		    	values.put(KEY_PTS, new_value);
		    else
		    	values.put(KEY_PTS, stats.getpts());
		    if(stat==KEY_FGM)
		    	values.put(KEY_FGM, new_value);
		    else
		    	values.put(KEY_FGM, stats.getfgm());
		    if(stat==KEY_FGA)
		    	values.put(KEY_FGA, new_value);
		    else
		    	values.put(KEY_FGA, stats.getfga());
		    if(stat==KEY_FGM3)
		    	values.put(KEY_FGM3, new_value);
		    else
		    	values.put(KEY_FGM3, stats.getfgm3());
		    if(stat==KEY_FGA3)
		    	values.put(KEY_FGA3, new_value);
		    else
		    	values.put(KEY_FGA3, stats.getfga3());
		    if(stat==KEY_FTM)
		    	values.put(KEY_FTM, new_value);
		    else
		    	values.put(KEY_FTM, stats.getftm());
		    if(stat==KEY_FTA)
		    	values.put(KEY_FTA, new_value);
		    else
		    	values.put(KEY_FTA, stats.getfta());
		    if(stat==KEY_OREB)
		    	values.put(KEY_OREB, new_value);
		    else
		    	values.put(KEY_OREB, stats.getoreb());
		    if(stat==KEY_DREB)
		    	values.put(KEY_DREB, new_value);
		    else
		    	values.put(KEY_DREB, stats.getdreb());
		    if(stat==KEY_AST)
		    	values.put(KEY_AST, new_value);
		    else
		    	values.put(KEY_AST, stats.getast());
		    if(stat==KEY_STL)
		    	values.put(KEY_STL, new_value);
		    else
		    	values.put(KEY_STL, stats.getstl());
		    if(stat==KEY_BLK)
		    	values.put(KEY_BLK, new_value);
		    else
		    	values.put(KEY_BLK, stats.getblk());
		    if(stat==KEY_TO)
		    	values.put(KEY_TO, new_value);
		    else
		    	values.put(KEY_TO, stats.getto());
		    if(stat==KEY_PF)
		    	values.put(KEY_PF, new_value);
		    else
		    	values.put(KEY_PF, stats.getpf());
		    if(stat==KEY_TECH)
		    	values.put(KEY_TECH, new_value);
		    else
		    	values.put(KEY_TECH, stats.gettech());
		    if(stat==KEY_FLAGRANT)
		    	values.put(KEY_FLAGRANT, new_value);
		    else
		    	values.put(KEY_FLAGRANT, stats.getflagrant());
	        
	        //insert more stats here
	        
		    return db.update(TABLE_BASKETBALL_GAME_STATS,  values, KEY_P_ID + " = " + p_id + " AND " + KEY_G_ID + " = " + g_id, null);
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

	public long createPlayers(BasketballPlayer player){
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
	public BasketballPlayer getPlayer(long p_id) {
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
	    BasketballPlayer player = new BasketballPlayer();
	    player.setpid(c.getLong(c.getColumnIndex(KEY_P_ID)));
	    player.settid(c.getLong(c.getColumnIndex(KEY_T_ID)));
	    player.setpname((c.getString(c.getColumnIndex(KEY_P_NAME))));
	    player.setpnum((c.getInt(c.getColumnIndex(KEY_P_NUM))));
	    player.setdb(this);
	 
	    return player;
	}
	
	public List<BasketballPlayer> getPlayersTeam(long t_id){
	    SQLiteDatabase db = this.getReadableDatabase();
		List<BasketballPlayer> players = new ArrayList<BasketballPlayer>();
		String selectPlayerQuery = "SELECT * FROM " + TABLE_PLAYERS + " WHERE " + KEY_T_ID + " = " + t_id;
        
        Log.i(LOG, selectPlayerQuery);
        
        Cursor c = db.rawQuery(selectPlayerQuery, null);
        
        if (c!=null)
        	c.moveToFirst();
        
        do {
        	//create the instance of Players using cursor information
		    BasketballPlayer player = new BasketballPlayer();
		    player.setpid(c.getLong(c.getColumnIndex(KEY_P_ID)));
		    player.settid(c.getLong(c.getColumnIndex(KEY_T_ID)));
		    player.setpname((c.getString(c.getColumnIndex(KEY_P_NAME))));
		    player.setpnum((c.getInt(c.getColumnIndex(KEY_P_NUM))));
		    player.setdb(this);

		    
            // adding to players list
            players.add(player);
        } while(c.moveToNext());
        
        return players;
	}
	
//NOT NECESSARY BECAUSE OF SUPERCLASS
	/*	
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
*/	
	public List<BasketballPlayer> getAllPlayers(){
	    SQLiteDatabase db = this.getReadableDatabase();
		List<BasketballPlayer> players = new ArrayList<BasketballPlayer>();
		String selectPlayerQuery = "SELECT * FROM " + TABLE_PLAYERS;
        
        Log.i(LOG, selectPlayerQuery);
        
        Cursor c = db.rawQuery(selectPlayerQuery, null);
        
        if (c!=null)
        	c.moveToFirst();
        
        do {
        	//create the instance of Players using cursor information
        	BasketballPlayer player = new BasketballPlayer();
		    player.setpid(c.getLong(c.getColumnIndex(KEY_P_ID)));
		    player.settid(c.getLong(c.getColumnIndex(KEY_T_ID)));
		    player.setpname((c.getString(c.getColumnIndex(KEY_P_NAME))));
		    player.setpnum((c.getInt(c.getColumnIndex(KEY_P_NUM))));
		    player.setdb(this);

		   
            // adding to players list
            players.add(player);
        } while(c.moveToNext());

        return players;
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
