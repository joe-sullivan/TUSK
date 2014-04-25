package com.seniordesign.ultimatescorecard.sqlite.basketball;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.seniordesign.ultimatescorecard.data.StatData;
import com.seniordesign.ultimatescorecard.data.basketball.BasketballPlayer;
import com.seniordesign.ultimatescorecard.networkdatabase.helper.BasketballNetworkHelper;
import com.seniordesign.ultimatescorecard.networkdatabase.helper.NetworkHelper;
import com.seniordesign.ultimatescorecard.sqlite.helper.DatabaseHelper;
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
import android.util.Log;

public class BasketballDatabaseHelper extends DatabaseHelper implements Serializable{

	/**
	 * 
	 */

	protected boolean _local = false;
	protected BasketballNetworkHelper  _net = new BasketballNetworkHelper("fulltest", "fulltest", "fulltest");

	private static final long serialVersionUID = -3785769973811870982L;

	// Logcat tag
    private static final String LOG = "BasketballDatabaseHelper";
 
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "BasketballStats";

    //Table Names
    private static final String TABLE_BASKETBALL_GAME_STATS = "basketball_game_stats";

    //GAMES Table - column names
    private static final String KEY_HOME_ID = "home_id";
    private static final String KEY_AWAY_ID = "away_id";
    private static final String KEY_DATE = "date";
    private static final String KEY_HOME_PTS = "home_pts";
    private static final String KEY_HOME_FGM = "home_fgm";
    private static final String KEY_HOME_FGA = "home_fga";
    private static final String KEY_HOME_FGM3 = "home_fgm3";
    private static final String KEY_HOME_FGA3 = "home_fga3";
    private static final String KEY_HOME_FTM = "home_ftm";
    private static final String KEY_HOME_FTA = "home_fta";
    private static final String KEY_HOME_OREB = "home_oreb";
    private static final String KEY_HOME_DREB = "home_dreb";
    private static final String KEY_HOME_AST = "home_ast";
    private static final String KEY_HOME_STL = "home_stl";
    private static final String KEY_HOME_BLK = "home_blk";
    private static final String KEY_HOME_TO = "home_turnover";
    private static final String KEY_HOME_PF = "home_pf";
    private static final String KEY_HOME_TECH = "home_tech";
    private static final String KEY_HOME_FLAGRANT = "home_flagrant";
    private static final String KEY_AWAY_PTS = "away_pts";
    private static final String KEY_AWAY_FGM = "away_fgm";
    private static final String KEY_AWAY_FGA = "away_fga";
    private static final String KEY_AWAY_FGM3 = "away_fgm3";
    private static final String KEY_AWAY_FGA3 = "away_fga3";
    private static final String KEY_AWAY_FTM = "away_ftm";
    private static final String KEY_AWAY_FTA = "away_fta";
    private static final String KEY_AWAY_OREB = "away_oreb";
    private static final String KEY_AWAY_DREB = "away_dreb";
    private static final String KEY_AWAY_AST = "away_ast";
    private static final String KEY_AWAY_STL = "away_stl";
    private static final String KEY_AWAY_BLK = "away_blk";
    private static final String KEY_AWAY_TO = "away_turnover";
    private static final String KEY_AWAY_PF = "away_pf";
    private static final String KEY_AWAY_TECH = "away_tech";
    private static final String KEY_AWAY_FLAGRANT = "away_flagrant";
    
    
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

    //Table Create Statements
    //GAMES table create statement
    private static final String CREATE_TABLE_GAMES = "CREATE TABLE IF NOT EXISTS " + TABLE_GAMES 
    		+ "(" + KEY_G_ID + " INTEGER PRIMARY KEY," + KEY_HOME_ID + " INTEGER," 
    		+ KEY_AWAY_ID + " INTEGER," + KEY_DATE + " DATE, " 
    		
    		+ KEY_HOME_PTS + " INTEGER, " + KEY_HOME_FGM + " INTEGER, " + KEY_HOME_FGA + " INTEGER, "
    		+ KEY_HOME_FGM3 + " INTEGER, " + KEY_HOME_FGA3 + " INTEGER, " + KEY_HOME_FTM + " INTEGER, "
    		+ KEY_HOME_FTA + " INTEGER, " + KEY_HOME_OREB + " INTEGER, " + KEY_HOME_DREB + " INTEGER, "
    		+ KEY_HOME_AST + " INTEGER, " + KEY_HOME_STL + " INTEGER, " + KEY_HOME_BLK + " INTEGER, "
    		+ KEY_HOME_TO + " INTEGER, " + KEY_HOME_PF + " INTEGER, " + KEY_HOME_TECH + " INTEGER, "
    		+ KEY_HOME_FLAGRANT + " INTEGER, "
    		
    		+ KEY_AWAY_PTS + " INTEGER, " + KEY_AWAY_FGM + " INTEGER, " + KEY_AWAY_FGA + " INTEGER, "
    		+ KEY_AWAY_FGM3 + " INTEGER, " + KEY_AWAY_FGA3 + " INTEGER, " + KEY_AWAY_FTM + " INTEGER, "
    		+ KEY_AWAY_FTA + " INTEGER, " + KEY_AWAY_OREB + " INTEGER, " + KEY_AWAY_DREB + " INTEGER, "
    		+ KEY_AWAY_AST + " INTEGER, " + KEY_AWAY_STL + " INTEGER, " + KEY_AWAY_BLK + " INTEGER, "
    		+ KEY_AWAY_TO + " INTEGER, " + KEY_AWAY_PF + " INTEGER, " + KEY_AWAY_TECH + " INTEGER, "
    		+ KEY_AWAY_FLAGRANT + " INTEGER " +
    		")"; 

    //BASKETBALL_GAME_STATS table create statement
    private static final String CREATE_TABLE_BASKETBALL_GAME_STATS = "CREATE TABLE IF NOT EXISTS " + TABLE_BASKETBALL_GAME_STATS 
    		+ "(" + KEY_G_ID + " INTEGER" +
    		//		"FOREIGN KEY REFERENCES" + TABLE_GAMES + "(" + KEY_G_ID + "),"
    		", "
    		+ KEY_P_ID + " INTEGER" +
    		//" INTEGER FOREIGN KEY REFERENCES" + TABLE_PLAYERS + "(" + KEY_P_ID + ")," 
    		", "
    		+ KEY_PTS + " INTEGER, " + KEY_FGM + " INTEGER, " + KEY_FGA + " INTEGER, "
    		+ KEY_FGM3 + " INTEGER, " + KEY_FGA3 + " INTEGER, " + KEY_FTM + " INTEGER, "
    		+ KEY_FTA + " INTEGER, " + KEY_OREB + " INTEGER, " + KEY_DREB + " INTEGER, "
    		+ KEY_AST + " INTEGER, " + KEY_STL + " INTEGER, " + KEY_BLK + " INTEGER, "
    		+ KEY_TO + " INTEGER, " + KEY_PF + " INTEGER, " + KEY_TECH + " INTEGER, "
    		+ KEY_FLAGRANT + " INTEGER " + ")"; 
    
    public BasketballDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        
        _local = true;
        //_net = new BasketballNetworkHelper("a","b","c");
        
        String[] login = new String[4];
        login[0] = "";
        login[1] = "";
        login[2] = "";
        login[3] = "";
        try {
       	 
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(
                    context.openFileInput("myfile")));
            String inputString;
            StringBuffer stringBuffer = new StringBuffer();      
            int i = 0;
            while ((inputString = inputReader.readLine()) != null) {
               stringBuffer.append(inputString + "\n");
          	  login[i] = inputString;
          	  i++;
            }

           Log.i("READ","value from file: " + stringBuffer.toString());

        } catch (IOException e) {

            e.printStackTrace();

        }
        
        if(login[0].equalsIgnoreCase("false")){
        _local = false;
        _net = new BasketballNetworkHelper(login[1], login[2], login[3]);
        }
        
        
    }
    
	public BasketballDatabaseHelper(Context context, String name, CursorFactory factory,
			int version, String s) {
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
        
        values.put(KEY_HOME_PTS, 0);
        values.put(KEY_HOME_FGM, 0);
        values.put(KEY_HOME_FGA, 0);
        values.put(KEY_HOME_FGM3, 0);
        values.put(KEY_HOME_FGA3, 0);
        values.put(KEY_HOME_FTM, 0);
        values.put(KEY_HOME_FTA, 0);
        values.put(KEY_HOME_OREB, 0);
        values.put(KEY_HOME_DREB, 0);
        values.put(KEY_HOME_AST, 0);
        values.put(KEY_HOME_STL, 0);
        values.put(KEY_HOME_BLK, 0);
        values.put(KEY_HOME_TO, 0);
        values.put(KEY_HOME_PF, 0);
        values.put(KEY_HOME_TECH, 0);
        values.put(KEY_HOME_FLAGRANT, 0);
        
        values.put(KEY_AWAY_PTS, 0);
        values.put(KEY_AWAY_FGM, 0);
        values.put(KEY_AWAY_FGA, 0);
        values.put(KEY_AWAY_FGM3, 0);
        values.put(KEY_AWAY_FGA3, 0);
        values.put(KEY_AWAY_FTM, 0);
        values.put(KEY_AWAY_FTA, 0);
        values.put(KEY_AWAY_OREB, 0);
        values.put(KEY_AWAY_DREB, 0);
        values.put(KEY_AWAY_AST, 0);
        values.put(KEY_AWAY_STL, 0);
        values.put(KEY_AWAY_BLK, 0);
        values.put(KEY_AWAY_TO, 0);
        values.put(KEY_AWAY_PF, 0);
        values.put(KEY_AWAY_TECH, 0);
        values.put(KEY_AWAY_FLAGRANT, 0);
 
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
        if (!_local){
        	_net.createGame(game, g_id);
        }
        return g_id;
	}

	//get single game
	public Games getGame(long g_id) {
		if(_local){
	    SQLiteDatabase db = this.getReadableDatabase();
	    //create query to select game
	    String selectQuery = "SELECT  * FROM " + TABLE_GAMES + 
	    	" WHERE " + KEY_G_ID + " = " + g_id;
	    //Log the query
	    Log.i(LOG, selectQuery);
	    //perform the query and store data in cursor
	    Cursor c = db.rawQuery(selectQuery, null);

	    BasketballGames game = new BasketballGames();
	    //set cursor to beginning
	    if (c != null && c.moveToFirst()){
	    //create the instance of Games using cursor information
	    game.setgid(c.getLong(c.getColumnIndex(KEY_G_ID)));
	    game.sethomeid((c.getLong(c.getColumnIndex(KEY_HOME_ID))));
	    game.setawayid((c.getLong(c.getColumnIndex(KEY_AWAY_ID))));
	    game.setDate(c.getString(c.getColumnIndex(KEY_DATE)));

	    game.sethomepts((c.getInt(c.getColumnIndex(KEY_HOME_PTS))));
	    game.sethomefgm((c.getInt(c.getColumnIndex(KEY_HOME_FGM))));
	    game.sethomefga(c.getInt(c.getColumnIndex(KEY_HOME_FGA)));
	    game.sethomefgm3(c.getInt(c.getColumnIndex(KEY_HOME_FGM3)));
	    game.sethomefga3(c.getInt(c.getColumnIndex(KEY_HOME_FGA3)));
	    game.sethomeftm(c.getInt(c.getColumnIndex(KEY_HOME_FTM)));
	    game.sethomefta(c.getInt(c.getColumnIndex(KEY_HOME_FTA)));
	    game.sethomeoreb(c.getInt(c.getColumnIndex(KEY_HOME_OREB)));
	    game.sethomedreb(c.getInt(c.getColumnIndex(KEY_HOME_DREB)));
	    game.sethomeast(c.getInt(c.getColumnIndex(KEY_HOME_AST)));
	    game.sethomestl(c.getInt(c.getColumnIndex(KEY_HOME_STL)));
	    game.sethomeblk(c.getInt(c.getColumnIndex(KEY_HOME_BLK)));
	    game.sethometo(c.getInt(c.getColumnIndex(KEY_HOME_TO)));
	    game.sethomepf(c.getInt(c.getColumnIndex(KEY_HOME_PF)));
	    game.sethometech(c.getInt(c.getColumnIndex(KEY_HOME_TECH)));
	    game.sethomeflagrant(c.getInt(c.getColumnIndex(KEY_HOME_FLAGRANT)));

	    game.setawaypts((c.getInt(c.getColumnIndex(KEY_AWAY_PTS))));
	    game.setawayfgm((c.getInt(c.getColumnIndex(KEY_AWAY_FGM))));
	    game.setawayfga(c.getInt(c.getColumnIndex(KEY_AWAY_FGA)));
	    game.setawayfgm3(c.getInt(c.getColumnIndex(KEY_AWAY_FGM3)));
	    game.setawayfga3(c.getInt(c.getColumnIndex(KEY_AWAY_FGA3)));
	    game.setawayftm(c.getInt(c.getColumnIndex(KEY_AWAY_FTM)));
	    game.setawayfta(c.getInt(c.getColumnIndex(KEY_AWAY_FTA)));
	    game.setawayoreb(c.getInt(c.getColumnIndex(KEY_AWAY_OREB)));
	    game.setawaydreb(c.getInt(c.getColumnIndex(KEY_AWAY_DREB)));
	    game.setawayast(c.getInt(c.getColumnIndex(KEY_AWAY_AST)));
	    game.setawaystl(c.getInt(c.getColumnIndex(KEY_AWAY_STL)));
	    game.setawayblk(c.getInt(c.getColumnIndex(KEY_AWAY_BLK)));
	    game.setawayto(c.getInt(c.getColumnIndex(KEY_AWAY_TO)));
	    game.setawaypf(c.getInt(c.getColumnIndex(KEY_AWAY_PF)));
	    game.setawaytech(c.getInt(c.getColumnIndex(KEY_AWAY_TECH)));
	    game.setawayflagrant(c.getInt(c.getColumnIndex(KEY_AWAY_FLAGRANT)));
	    }
	    return game;
		}
		else {
			return _net.getGame(g_id);
		}
	}

	//Get all Games
	public List<Games> getAllGames() {
		if(_local){
	    List<Games> games = new ArrayList<Games>();
	    SQLiteDatabase db = this.getReadableDatabase();
	    String selectQuery = "SELECT  * FROM " + TABLE_GAMES;

	    Log.i(LOG, selectQuery);

	    Cursor c = db.rawQuery(selectQuery, null);

	    // looping through all rows and adding to list
	    if (c.moveToFirst()) {
	        do {
	        	BasketballGames game = new BasketballGames();
	    	    game.setgid(c.getLong(c.getColumnIndex(KEY_G_ID)));
	    	    game.sethomeid((c.getLong(c.getColumnIndex(KEY_HOME_ID))));
	    	    game.setawayid((c.getLong(c.getColumnIndex(KEY_AWAY_ID))));
	    	    game.setDate(c.getString(c.getColumnIndex(KEY_DATE)));

	    	    game.sethomepts((c.getInt(c.getColumnIndex(KEY_HOME_PTS))));
	    	    game.sethomefgm((c.getInt(c.getColumnIndex(KEY_HOME_FGM))));
	    	    game.sethomefga(c.getInt(c.getColumnIndex(KEY_HOME_FGA)));
	    	    game.sethomefgm3(c.getInt(c.getColumnIndex(KEY_HOME_FGM3)));
	    	    game.sethomefga3(c.getInt(c.getColumnIndex(KEY_HOME_FGA3)));
	    	    game.sethomeftm(c.getInt(c.getColumnIndex(KEY_HOME_FTM)));
	    	    game.sethomefta(c.getInt(c.getColumnIndex(KEY_HOME_FTA)));
	    	    game.sethomeoreb(c.getInt(c.getColumnIndex(KEY_HOME_OREB)));
	    	    game.sethomedreb(c.getInt(c.getColumnIndex(KEY_HOME_DREB)));
	    	    game.sethomeast(c.getInt(c.getColumnIndex(KEY_HOME_AST)));
	    	    game.sethomestl(c.getInt(c.getColumnIndex(KEY_HOME_STL)));
	    	    game.sethomeblk(c.getInt(c.getColumnIndex(KEY_HOME_BLK)));
	    	    game.sethometo(c.getInt(c.getColumnIndex(KEY_HOME_TO)));
	    	    game.sethomepf(c.getInt(c.getColumnIndex(KEY_HOME_PF)));
	    	    game.sethometech(c.getInt(c.getColumnIndex(KEY_HOME_TECH)));
	    	    game.sethomeflagrant(c.getInt(c.getColumnIndex(KEY_HOME_FLAGRANT)));

	    	    game.setawaypts((c.getInt(c.getColumnIndex(KEY_AWAY_PTS))));
	    	    game.setawayfgm((c.getInt(c.getColumnIndex(KEY_AWAY_FGM))));
	    	    game.setawayfga(c.getInt(c.getColumnIndex(KEY_AWAY_FGA)));
	    	    game.setawayfgm3(c.getInt(c.getColumnIndex(KEY_AWAY_FGM3)));
	    	    game.setawayfga3(c.getInt(c.getColumnIndex(KEY_AWAY_FGA3)));
	    	    game.setawayftm(c.getInt(c.getColumnIndex(KEY_AWAY_FTM)));
	    	    game.setawayfta(c.getInt(c.getColumnIndex(KEY_AWAY_FTA)));
	    	    game.setawayoreb(c.getInt(c.getColumnIndex(KEY_AWAY_OREB)));
	    	    game.setawaydreb(c.getInt(c.getColumnIndex(KEY_AWAY_DREB)));
	    	    game.setawayast(c.getInt(c.getColumnIndex(KEY_AWAY_AST)));
	    	    game.setawaystl(c.getInt(c.getColumnIndex(KEY_AWAY_STL)));
	    	    game.setawayblk(c.getInt(c.getColumnIndex(KEY_AWAY_BLK)));
	    	    game.setawayto(c.getInt(c.getColumnIndex(KEY_AWAY_TO)));
	    	    game.setawaypf(c.getInt(c.getColumnIndex(KEY_AWAY_PF)));
	    	    game.setawaytech(c.getInt(c.getColumnIndex(KEY_AWAY_TECH)));
	    	    game.setawayflagrant(c.getInt(c.getColumnIndex(KEY_AWAY_FLAGRANT)));

	            // adding to games list
	            games.add(game);
	        } while (c.moveToNext());
	    }

	    return games;
		}
		else {
			return _net.getAllGames();
		}
	}

	//Get all Games played by a team
	public List<Games> getAllGamesTeam(long t_id) {
		if(_local){
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
	        	BasketballGames game = new BasketballGames();
	    	    game.setgid(c.getLong(c.getColumnIndex(KEY_G_ID)));
	    	    game.sethomeid((c.getLong(c.getColumnIndex(KEY_HOME_ID))));
	    	    game.setawayid((c.getLong(c.getColumnIndex(KEY_AWAY_ID))));
	    	    game.setDate(c.getString(c.getColumnIndex(KEY_DATE)));

	    	    game.sethomepts((c.getInt(c.getColumnIndex(KEY_HOME_PTS))));
	    	    game.sethomefgm((c.getInt(c.getColumnIndex(KEY_HOME_FGM))));
	    	    game.sethomefga(c.getInt(c.getColumnIndex(KEY_HOME_FGA)));
	    	    game.sethomefgm3(c.getInt(c.getColumnIndex(KEY_HOME_FGM3)));
	    	    game.sethomefga3(c.getInt(c.getColumnIndex(KEY_HOME_FGA3)));
	    	    game.sethomeftm(c.getInt(c.getColumnIndex(KEY_HOME_FTM)));
	    	    game.sethomefta(c.getInt(c.getColumnIndex(KEY_HOME_FTA)));
	    	    game.sethomeoreb(c.getInt(c.getColumnIndex(KEY_HOME_OREB)));
	    	    game.sethomedreb(c.getInt(c.getColumnIndex(KEY_HOME_DREB)));
	    	    game.sethomeast(c.getInt(c.getColumnIndex(KEY_HOME_AST)));
	    	    game.sethomestl(c.getInt(c.getColumnIndex(KEY_HOME_STL)));
	    	    game.sethomeblk(c.getInt(c.getColumnIndex(KEY_HOME_BLK)));
	    	    game.sethometo(c.getInt(c.getColumnIndex(KEY_HOME_TO)));
	    	    game.sethomepf(c.getInt(c.getColumnIndex(KEY_HOME_PF)));
	    	    game.sethometech(c.getInt(c.getColumnIndex(KEY_HOME_TECH)));
	    	    game.sethomeflagrant(c.getInt(c.getColumnIndex(KEY_HOME_FLAGRANT)));

	    	    game.setawaypts((c.getInt(c.getColumnIndex(KEY_AWAY_PTS))));
	    	    game.setawayfgm((c.getInt(c.getColumnIndex(KEY_AWAY_FGM))));
	    	    game.setawayfga(c.getInt(c.getColumnIndex(KEY_AWAY_FGA)));
	    	    game.setawayfgm3(c.getInt(c.getColumnIndex(KEY_AWAY_FGM3)));
	    	    game.setawayfga3(c.getInt(c.getColumnIndex(KEY_AWAY_FGA3)));
	    	    game.setawayftm(c.getInt(c.getColumnIndex(KEY_AWAY_FTM)));
	    	    game.setawayfta(c.getInt(c.getColumnIndex(KEY_AWAY_FTA)));
	    	    game.setawayoreb(c.getInt(c.getColumnIndex(KEY_AWAY_OREB)));
	    	    game.setawaydreb(c.getInt(c.getColumnIndex(KEY_AWAY_DREB)));
	    	    game.setawayast(c.getInt(c.getColumnIndex(KEY_AWAY_AST)));
	    	    game.setawaystl(c.getInt(c.getColumnIndex(KEY_AWAY_STL)));
	    	    game.setawayblk(c.getInt(c.getColumnIndex(KEY_AWAY_BLK)));
	    	    game.setawayto(c.getInt(c.getColumnIndex(KEY_AWAY_TO)));
	    	    game.setawaypf(c.getInt(c.getColumnIndex(KEY_AWAY_PF)));
	    	    game.setawaytech(c.getInt(c.getColumnIndex(KEY_AWAY_TECH)));
	    	    game.setawayflagrant(c.getInt(c.getColumnIndex(KEY_AWAY_FLAGRANT)));

	            // adding to games list
	            games.add(game);
	        } while (c.moveToNext());
	    }
	    return games;
		}else{
			return _net.getAllGamesTeam(t_id);
		}
	}

	//get single game stat for team
	public int getTeamGameStat(long g_id, String stat) {
		 Log.i("INTEG","Call to getTeamGameStat true");
		if(_local){
	    SQLiteDatabase db = this.getReadableDatabase();
	    //create query to select game
	    String selectQuery = "SELECT " + stat + " FROM " + TABLE_GAMES + 
	    	" WHERE " + KEY_G_ID + " = " + g_id;

	    //Log the query
	    Log.i("INTEG","getTeam test");
	    Log.i(LOG, selectQuery);
	    //perform the query and store data in cursor
	    Cursor c = db.rawQuery(selectQuery, null);
	    //set cursor to beginning
	    if (c != null)
	        c.moveToFirst();
	    //create the instance of Games using cursor information
	    int stat_value = c.getInt(c.getColumnIndex(stat));

	    return stat_value;
		}else {
			return _net.getTeamGameStat(g_id, stat);
		}
	}

	//Adding value to points category of a player
	public int addTeamStats(ArrayList<StatData> statlist){
		if(_local){
	    SQLiteDatabase db = this.getWritableDatabase();

	    _undoInstance.addtstats(statlist);

	    for(StatData statdata: statlist){

	    	long g_id = statdata.getgid();
	    	String stat = statdata.getstat();
	    	int value = statdata.getvalue();

		    BasketballGames game = (BasketballGames) getGame(g_id);
		    Log.i("INTEG","local stat value: " + stat);

		    int old_value = getTeamGameStat(g_id,stat);
		    int new_value = old_value + value;

		    ContentValues values = new ContentValues();

	        values.put(KEY_G_ID, g_id);
	        values.put(KEY_HOME_ID, game.gethomeid());
	        values.put(KEY_AWAY_ID, game.getawayid());
	        values.put(KEY_DATE, game.getDate());

		    if(stat==KEY_HOME_PTS)
		    	values.put(KEY_HOME_PTS, new_value);
		    else
		    	values.put(KEY_HOME_PTS, game.gethomepts());
		    if(stat==KEY_HOME_FGM)
		    	values.put(KEY_HOME_FGM, new_value);
		    else
		    	values.put(KEY_HOME_FGM, game.gethomefgm());
		    if(stat==KEY_HOME_FGA)
		    	values.put(KEY_HOME_FGA, new_value);
		    else
		    	values.put(KEY_HOME_FGA, game.gethomefga());
		    if(stat==KEY_HOME_FGM3)
		    	values.put(KEY_HOME_FGM3, new_value);
		    else
		    	values.put(KEY_HOME_FGM3, game.gethomefgm3());
		    if(stat==KEY_HOME_FGA3)
		    	values.put(KEY_HOME_FGA3, new_value);
		    else
		    	values.put(KEY_HOME_FGA3, game.gethomefga3());
		    if(stat==KEY_HOME_FTM)
		    	values.put(KEY_HOME_FTM, new_value);
		    else
		    	values.put(KEY_HOME_FTM, game.gethomeftm());
		    if(stat==KEY_HOME_FTA)
		    	values.put(KEY_HOME_FTA, new_value);
		    else
		    	values.put(KEY_HOME_FTA, game.gethomefta());
		    if(stat==KEY_HOME_OREB)
		    	values.put(KEY_HOME_OREB, new_value);
		    else
		    	values.put(KEY_HOME_OREB, game.gethomeoreb());
		    if(stat==KEY_HOME_DREB)
		    	values.put(KEY_HOME_DREB, new_value);
		    else
		    	values.put(KEY_HOME_DREB, game.gethomedreb());
		    if(stat==KEY_HOME_AST)
		    	values.put(KEY_HOME_AST, new_value);
		    else
		    	values.put(KEY_HOME_AST, game.gethomeast());
		    if(stat==KEY_HOME_STL)
		    	values.put(KEY_HOME_STL, new_value);
		    else
		    	values.put(KEY_HOME_STL, game.gethomestl());
		    if(stat==KEY_HOME_BLK)
		    	values.put(KEY_HOME_BLK, new_value);
		    else
		    	values.put(KEY_HOME_BLK, game.gethomeblk());
		    if(stat==KEY_HOME_TO)
		    	values.put(KEY_HOME_TO, new_value);
		    else
		    	values.put(KEY_HOME_TO, game.gethometo());
		    if(stat==KEY_HOME_PF)
		    	values.put(KEY_HOME_PF, new_value);
		    else
		    	values.put(KEY_HOME_PF, game.gethomepf());
		    if(stat==KEY_HOME_TECH)
		    	values.put(KEY_HOME_TECH, new_value);
		    else
		    	values.put(KEY_HOME_TECH, game.gethometech());
		    if(stat==KEY_HOME_FLAGRANT)
		    	values.put(KEY_HOME_FLAGRANT, new_value);
		    else
		    	values.put(KEY_HOME_FLAGRANT, game.gethomeflagrant());
		    if(stat==KEY_AWAY_PTS)
		    	values.put(KEY_AWAY_PTS, new_value);
		    else
		    	values.put(KEY_AWAY_PTS, game.getawaypts());
		    if(stat==KEY_AWAY_FGM)
		    	values.put(KEY_AWAY_FGM, new_value);
		    else
		    	values.put(KEY_AWAY_FGM, game.getawayfgm());
		    if(stat==KEY_AWAY_FGA)
		    	values.put(KEY_AWAY_FGA, new_value);
		    else
		    	values.put(KEY_AWAY_FGA, game.getawayfga());
		    if(stat==KEY_AWAY_FGM3)
		    	values.put(KEY_AWAY_FGM3, new_value);
		    else
		    	values.put(KEY_AWAY_FGM3, game.getawayfgm3());
		    if(stat==KEY_AWAY_FGA3)
		    	values.put(KEY_AWAY_FGA3, new_value);
		    else
		    	values.put(KEY_AWAY_FGA3, game.getawayfga3());
		    if(stat==KEY_AWAY_FTM)
		    	values.put(KEY_AWAY_FTM, new_value);
		    else
		    	values.put(KEY_AWAY_FTM, game.getawayftm());
		    if(stat==KEY_AWAY_FTA)
		    	values.put(KEY_AWAY_FTA, new_value);
		    else
		    	values.put(KEY_AWAY_FTA, game.getawayfta());
		    if(stat==KEY_AWAY_OREB)
		    	values.put(KEY_AWAY_OREB, new_value);
		    else
		    	values.put(KEY_AWAY_OREB, game.getawayoreb());
		    if(stat==KEY_AWAY_DREB)
		    	values.put(KEY_AWAY_DREB, new_value);
		    else
		    	values.put(KEY_AWAY_DREB, game.getawaydreb());
		    if(stat==KEY_AWAY_AST)
		    	values.put(KEY_AWAY_AST, new_value);
		    else
		    	values.put(KEY_AWAY_AST, game.getawayast());
		    if(stat==KEY_AWAY_STL)
		    	values.put(KEY_AWAY_STL, new_value);
		    else
		    	values.put(KEY_AWAY_STL, game.getawaystl());
		    if(stat==KEY_AWAY_BLK)
		    	values.put(KEY_AWAY_BLK, new_value);
		    else
		    	values.put(KEY_AWAY_BLK, game.getawayblk());
		    if(stat==KEY_AWAY_TO)
		    	values.put(KEY_AWAY_TO, new_value);
		    else
		    	values.put(KEY_AWAY_TO, game.getawayto());
		    if(stat==KEY_AWAY_PF)
		    	values.put(KEY_AWAY_PF, new_value);
		    else
		    	values.put(KEY_AWAY_PF, game.getawaypf());
		    if(stat==KEY_AWAY_TECH)
		    	values.put(KEY_AWAY_TECH, new_value);
		    else
		    	values.put(KEY_AWAY_TECH, game.getawaytech());
		    if(stat==KEY_AWAY_FLAGRANT)
		    	values.put(KEY_AWAY_FLAGRANT, new_value);
		    else
		    	values.put(KEY_AWAY_FLAGRANT, game.getawayflagrant());

	        //insert more stats here
		    db.update(TABLE_GAMES,  values, KEY_G_ID + " = " + g_id, null);
	    }
	    return 1;
		}
		else{
			return _net.addTeamStats(statlist);
		}
	}

	// Delete a Game
	public void deleteGame(long g_id) {
		deleteGameStats(g_id);
	    SQLiteDatabase db = this.getWritableDatabase();
	    db.delete(TABLE_GAMES, KEY_G_ID + " = ?",
	            new String[] { String.valueOf(g_id) });
	    deleteGameStats(g_id);
	    if(!_local){
	    	_net.deleteGame(g_id);
	    }
	}


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
        
        if(!_local){
        	_net.createGameStats(p_id, g_id);
        }
	}

		//get single game stats for single player
		public BasketballGameStats getPlayerGameStats(long g_id, long p_id) {
			 Log.i("INTEG","getPlayerGameStats true");
			if (_local){
		    SQLiteDatabase db = this.getReadableDatabase();
		    //create query to select game
		    String selectQuery = "SELECT  * FROM " + TABLE_BASKETBALL_GAME_STATS + 
		    	" WHERE " + KEY_G_ID + " = " + g_id + 
		    	" AND " + KEY_P_ID + " = " + p_id;

		    //Log the query
		    Log.i(LOG, selectQuery);
		    //perform the query and store data in cursor
		    Cursor c = db.rawQuery(selectQuery, null);
		  //create the instance of Games using cursor information
		    BasketballGameStats stats = new BasketballGameStats();

		    //set cursor to beginning
		    if (c != null && c.moveToFirst()){
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
		    }
		    return stats;
			}
			else{
				return _net.getPlayerGameStats(g_id, p_id);
			}
		}

		//get single game stat for single player
		public int getPlayerGameStat(long g_id, long p_id, String stat) {
			if(_local){
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
			else{
				return _net.getPlayerGameStat(g_id, p_id, stat);
			}
		}

		//Get all GameStats for player
		public List<BasketballGameStats> getPlayerAllGameStats(long p_id) {
			if(_local){
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
			}else{
				return _net.getPlayerAllGameStats(p_id);
			}
		}

		//Get all GameStats
		public List<BasketballGameStats> getAllGameStats() {
			if(_local){
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
			}else{
				return _net.getAllGameStats();
			}
		}

		// Delete a GameStats
		public void deleteGameStats(long g_id) {
		    SQLiteDatabase db = this.getWritableDatabase();
		    db.delete(TABLE_BASKETBALL_GAME_STATS, KEY_G_ID + " = ?",
		            new String[] { String.valueOf(g_id) });
		    if(!_local){
		    	_net.deleteGameStats(g_id);
		    }
		}

		//ADDING STATS

		//Adding value to points category of a player
		public int addStats(ArrayList<StatData> statlist){
			if(_local){
		    SQLiteDatabase db = this.getWritableDatabase();

		    _undoInstance.addpstats(statlist);

		    for(StatData statdata: statlist){

			    long g_id = statdata.getgid();
			    long p_id = statdata.getpid();
			    String stat = statdata.getstat();
			    int value = statdata.getvalue();

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

			    db.update(TABLE_BASKETBALL_GAME_STATS,  values, KEY_P_ID + " = " + p_id + " AND " + KEY_G_ID + " = " + g_id, null);
		    }
		    return 1;
			}else{
				return _net.addStats(statlist);
			}
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
        
        if(!_local){
        	_net.createPlayers(player, p_id);
        }
 
        return p_id;
	}

	//get single player
	public BasketballPlayer getPlayer(long p_id) {
		if (_local){
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
		}else{
			return _net.getPlayer(p_id);
		}
	}


	// ----------------------- PLAY_BY_PLAY table method --------------------- //

		public long createPlayByPlay(PlayByPlay pbp){
			long a_id = super.createPlayByPlay(pbp);
			if (!_local){
				Log.i("INTEG", "entering network pbp creation");
				_net.createPlayByPlay(pbp, a_id);
			}
			return a_id;
		}

		public List<PlayByPlay> getPlayByPlayGame(long g_id){
			if(_local){
				return super.getPlayByPlayGame(g_id);
			}
			else{
				return _net.getPlayByPlayGame(g_id);
			}
		}

		// Delete PlayByPlay
		public void deletePlayByPlay(long a_id) {
			super.deletePlayByPlay(a_id);
			if(!_local) {
				_net.deletePlayByPlay(a_id);
			}
		}

		// Delete PlayByPlay of a game
		public void deletePlayByPlayGame(long g_id) {
			super.deletePlayByPlayGame(g_id);
			if(!_local){
				_net.deletePlayByPlayGame(g_id);
			}
		}

		// -------------------PLAYERS table methods----------------------------- //

		public List<Players> getPlayersTeam(long t_id) {
			if (_local){
				return super.getPlayersTeam(t_id);
			}
			else {
				return _net.getPlayersTeam(t_id);
			}
		}

		public List<Players> getPlayersTeam2(long t_id)  {
			if(_local){
				return super.getPlayersTeam2(t_id);
			}
			else {
				return _net.getPlayersTeam2(t_id);
			}
		}

		public int updatePlayer(Players player){

			if (!_local){
				_net.updatePlayer(player);
			}

			return super.updatePlayer(player);
		}

		public List<Players> getAllPlayers() {
			if(_local){
				return super.getAllPlayers();
			}
			else {
				return _net.getAllPlayers();
			}
		}

		// Delete a Player
		public void deletePlayer(long p_id) {
			super.deletePlayer(p_id);
			if(!_local){
				_net.deletePlayer(p_id);
			}
		}


		// Delete Players on a team
		public void deletePlayers(long t_id) {
			super.deletePlayers(t_id);
			if(!_local){
				_net.deletePlayer(t_id);
			}
		}


		// -------------------SHOT_CHART_COORDS table methods ------------------ //

		//create a row of shot chart coordinates
		public long createShot(ShotChartCoords shot){
			long shot_id = super.createShot(shot);
			if(!_local){
				_net.createShot(shot, shot_id);
			}
			return shot_id;
		}

		public List<ShotChartCoords> getAllShots(){
			if(_local){
				return super.getAllShots();
			}
			else {
				return _net.getAllShots();
			}
		}

		public List<ShotChartCoords> getAllTeamShots(long t_id){
			if (_local){
				return super.getAllTeamShots(t_id);
			}
			else {
				return _net.getAllTeamShots(t_id);
			}
		}

		public List<ShotChartCoords> getAllPlayerShots(long p_id){
			if (_local){
				return super.getAllPlayerShots(p_id);
			}
			else {
				return _net.getAllPlayerShots(p_id);
			}
		}


		public List<ShotChartCoords> getAllTeamShotsGame(long t_id, long g_id){
			if(_local){
				return super.getAllTeamShotsGame(t_id, g_id);
			}
			else{
				return _net.getAllTeamShotsGame(t_id, g_id);
			}
		}

		public List<ShotChartCoords> getAllPlayerShotsGame(long g_id, long p_id){
			if(_local){
				return super.getAllPlayerShotsGame(g_id, p_id);
			}
			else{
				return _net.getAllPlayerShotsGame(p_id, g_id);
			}
		}

		// Delete a Shot
		public void deleteShot(long shot_id) {
			super.deleteShot(shot_id);
			if(!_local){
				_net.deleteShot(shot_id);
			}
		}


		// ----------------------- TEAMS table methods ------------------------- //

		public long createTeams(Teams team){
			long p_id = super.createTeams(team);
			if(!_local){
			_net.createTeams(team, p_id);
			}

			return p_id;
		}

		//get single team with id
		public Teams getTeam(long t_id) {
			if (_local){
				return super.getTeam(t_id);
			}
			else{
				return _net.getTeam(t_id);
			}
		}

		//get single team with name
		public Teams getTeam(String t_name) {
			if(_local){
				return super.getTeam(t_name);
			}
			else {
				return _net.getTeam(t_name);
			}
		}

		public List<Teams> getAllTeams(){
			if(_local){
				return super.getAllTeams();
			}
			else{
				return _net.getAllTeams();
			}
		}

		public int updateTeam(Teams team){
			if(!_local){
				_net.updateTeam(team);
			}
			return super.updateTeam(team);
		}

		// Delete a Team
		public void deleteTeam(long t_id) {
			super.deleteTeam(t_id);
			if(!_local){
				_net.deleteTeam(t_id);
			}
		}


	// closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
}