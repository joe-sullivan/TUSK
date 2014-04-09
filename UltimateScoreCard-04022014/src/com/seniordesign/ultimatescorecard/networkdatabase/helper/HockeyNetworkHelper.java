package com.seniordesign.ultimatescorecard.networkdatabase.helper;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.seniordesign.ultimatescorecard.data.basketball.BasketballPlayer;
import com.seniordesign.ultimatescorecard.data.hockey.HockeyPlayer;
import com.seniordesign.ultimatescorecard.sqlite.basketball.BasketballGameStats;
import com.seniordesign.ultimatescorecard.sqlite.basketball.BasketballGames;
import com.seniordesign.ultimatescorecard.sqlite.helper.DatabaseHelper;
import com.seniordesign.ultimatescorecard.sqlite.helper.Games;
import com.seniordesign.ultimatescorecard.sqlite.helper.Players;
import com.seniordesign.ultimatescorecard.sqlite.helper.Teams;
import com.seniordesign.ultimatescorecard.sqlite.hockey.HockeyGameStats;
import com.seniordesign.ultimatescorecard.sqlite.hockey.HockeyGames;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.os.AsyncTask;
import android.util.Log;

public class HockeyNetworkHelper extends NetworkHelper{

	private String _user;
	private String _password;
	private String _schema;

	// Logcat tag
	private static final String LOG = "HockeyDatabaseHelper";

	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "HockeyStats";

	//Table Names
	private static final String TABLE_HOCKEY_GAME_STATS = "hockey_game_stats";
	private static final String TABLE_GAMES = "HOCKEY_GAMES";
	private static final String TABLE_GAME_STATS = "HOCKEY_GAME_STATS";

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

	//Log In Tags

	protected static final String user = "user";
	protected static final String password = "password";
	protected static final String schema = "schema";

	//Table Tags
	protected static final String TAG_STAT = "stat";
	protected static final String TAG_STAT_VALUE = "stat_value";
	protected static final String TAG_HOCKEY_GAMES = "hockey_games";
	protected static final String TAG_HOCKEY_GAME_STATS = "hockey_games_stats";

	//URL Information
	protected static final String dburl = "http://tusk.zapto.org/php/";
	protected static final String url_delete = dburl + "delete_entry.php";
	protected static final String url_get_stat = dburl + "get_stat.php";

	protected static final String url_insert_game = dburl + "insert_hockey_game.php";
	protected static final String url_get_game = dburl + "get_hockey_game.php";
	protected static final String url_insert_game_stat = dburl + "insert_hockey_game_stat.php";
	protected static final String url_get_game_stat = dburl + "get_hockey_game_stat.php";




	/*
    public HockeyNetworkHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

	public HockeyNetworkHelper(Context context, String name, CursorFactory factory,
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
	 */

	public HockeyNetworkHelper(String dbuser, String dbpass, String dbschema) {
		super(dbuser, dbpass, dbschema);
	}

	// ----------------------- GAMES table methods ------------------------- //

	public void createGame(Games game, Long g_id) throws JSONException, InterruptedException, ExecutionException{


		List<NameValuePair> params = this.startParams();


		params.add(new BasicNameValuePair(KEY_G_ID, Long.toString(g_id)));
		params.add(new BasicNameValuePair(KEY_HOME_ID, Long.toString(game.gethomeid())));
		params.add(new BasicNameValuePair(KEY_AWAY_ID, Long.toString(game.getawayid())));
		params.add(new BasicNameValuePair(KEY_DATE, game.getDate()));

		//Have base values added in by php file
		/*

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
		 */


		HttpParameter parameter = new HttpParameter(url_insert_game,"POST", params);
		AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);

		List<Players> home_players = getPlayersTeam(game.gethomeid());
		List<Players> away_players = getPlayersTeam(game.getawayid());

		for(Players player : home_players){
			createGameStats(player.getpid(), g_id);
		}
		for(Players player : away_players){
			createGameStats(player.getpid(), g_id);
		}

	}

	//get single game
	public Games getGame(long g_id) throws JSONException, InterruptedException, ExecutionException {

		String w = "where g_id = " + Long.toString(g_id);
		List<NameValuePair> params = this.startParams();
		params.add(new BasicNameValuePair(TAG_WHERE, w));



		HttpParameter parameter = new HttpParameter(url_get_game, "POST", params);
		AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
		JSONObject json = result.get();
		JSONArray plays = json.getJSONArray(TAG_HOCKEY_GAMES);


		JSONObject c = plays.getJSONObject(0);
		HockeyGames game = new HockeyGames();



		//create the instance of Games using cursor information
		game.setgid(c.getLong(c.getString(KEY_G_ID)));
		game.sethomeid((c.getLong(c.getString(KEY_HOME_ID))));
		game.setawayid((c.getLong(c.getString(KEY_AWAY_ID))));
		game.setDate(c.getString(c.getString(KEY_DATE)));

		game.sethomeshots((c.getInt(c.getString(KEY_HOME_SHOTS))));
		game.sethomesog((c.getInt(c.getString(KEY_HOME_SOG))));
		game.sethomegoals(c.getInt(c.getString(KEY_HOME_GOALS)));
		game.sethomeast((c.getInt(c.getString(KEY_HOME_AST))));
		game.sethomepenminor((c.getInt(c.getString(KEY_HOME_PEN_MINOR))));
		game.sethomepenmajor(c.getInt(c.getString(KEY_HOME_PEN_MAJOR)));
		game.sethomepenmisconduct(c.getInt(c.getString(KEY_HOME_PEN_MISCONDUCT)));
		game.sethomesaves((c.getInt(c.getString(KEY_HOME_SAVES))));
		game.sethomegoalsallowed((c.getInt(c.getString(KEY_HOME_GOALS_ALLOWED))));

		game.setawayshots((c.getInt(c.getString(KEY_AWAY_SHOTS))));
		game.setawaysog((c.getInt(c.getString(KEY_AWAY_SOG))));
		game.setawaygoals(c.getInt(c.getString(KEY_AWAY_GOALS)));
		game.setawayast((c.getInt(c.getString(KEY_AWAY_AST))));
		game.setawaypenminor((c.getInt(c.getString(KEY_AWAY_PEN_MINOR))));
		game.setawaypenmajor(c.getInt(c.getString(KEY_AWAY_PEN_MAJOR)));
		game.setawaypenmisconduct(c.getInt(c.getString(KEY_AWAY_PEN_MISCONDUCT)));
		game.setawaysaves((c.getInt(c.getString(KEY_AWAY_SAVES))));
		game.setawaygoalsallowed((c.getInt(c.getString(KEY_AWAY_GOALS_ALLOWED))));

		return game;
	}

	// Get all games played by a team
	public List<Games> getAllGamesTeam(long t_id) throws JSONException, InterruptedException, ExecutionException {
		String w = "WHERE " + KEY_HOME_ID + " = " + Long.toString(t_id) + " OR " + KEY_AWAY_ID + " = " + Long.toString(t_id);
		List<Games> games  = new ArrayList<Games>();
		List<NameValuePair> params = this.startParams();
		params.add(new BasicNameValuePair(TAG_WHERE, w));



		HttpParameter parameter = new HttpParameter(url_get_game, "POST", params);
		AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
		JSONObject json = result.get();
		JSONArray plays = json.getJSONArray(TAG_HOCKEY_GAMES);

		// looping through all rows and adding to list
		for (int i = 0; i < plays.length(); i++) {
			JSONObject c = plays.getJSONObject(i);
			HockeyGames game = new HockeyGames();

			game.setgid(c.getLong(c.getString(KEY_G_ID)));
			game.sethomeid((c.getLong(c.getString(KEY_HOME_ID))));
			game.setawayid((c.getLong(c.getString(KEY_AWAY_ID))));
			game.setDate(c.getString(c.getString(KEY_DATE)));

			game.sethomeshots((c.getInt(c.getString(KEY_HOME_SHOTS))));
			game.sethomesog((c.getInt(c.getString(KEY_HOME_SOG))));
			game.sethomegoals(c.getInt(c.getString(KEY_HOME_GOALS)));
			game.sethomeast((c.getInt(c.getString(KEY_HOME_AST))));
			game.sethomepenminor((c.getInt(c.getString(KEY_HOME_PEN_MINOR))));
			game.sethomepenmajor(c.getInt(c.getString(KEY_HOME_PEN_MAJOR)));
			game.sethomepenmisconduct(c.getInt(c.getString(KEY_HOME_PEN_MISCONDUCT)));
			game.sethomesaves((c.getInt(c.getString(KEY_HOME_SAVES))));
			game.sethomegoalsallowed((c.getInt(c.getString(KEY_HOME_GOALS_ALLOWED))));

			game.setawayshots((c.getInt(c.getString(KEY_AWAY_SHOTS))));
			game.setawaysog((c.getInt(c.getString(KEY_AWAY_SOG))));
			game.setawaygoals(c.getInt(c.getString(KEY_AWAY_GOALS)));
			game.setawayast((c.getInt(c.getString(KEY_AWAY_AST))));
			game.setawaypenminor((c.getInt(c.getString(KEY_AWAY_PEN_MINOR))));
			game.setawaypenmajor(c.getInt(c.getString(KEY_AWAY_PEN_MAJOR)));
			game.setawaypenmisconduct(c.getInt(c.getString(KEY_AWAY_PEN_MISCONDUCT)));
			game.setawaysaves((c.getInt(c.getString(KEY_AWAY_SAVES))));
			game.setawaygoalsallowed((c.getInt(c.getString(KEY_AWAY_GOALS_ALLOWED))));

			// adding to games list
			games.add(game);

		}

		return games;
	}

	//Get all Games
	public List<Games> getAllGames() throws InterruptedException, ExecutionException, JSONException {
		String w = "";
		List<Games> games  = new ArrayList<Games>();
		List<NameValuePair> params = this.startParams();
		params.add(new BasicNameValuePair(TAG_WHERE, w));



		HttpParameter parameter = new HttpParameter(url_get_game, "POST", params);
		AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
		JSONObject json = result.get();
		JSONArray plays = json.getJSONArray(TAG_HOCKEY_GAMES);

		// looping through all rows and adding to list
		for (int i = 0; i < plays.length(); i++) {
			JSONObject c = plays.getJSONObject(i);
			HockeyGames game = new HockeyGames();

			game.setgid(c.getLong(c.getString(KEY_G_ID)));
			game.sethomeid((c.getLong(c.getString(KEY_HOME_ID))));
			game.setawayid((c.getLong(c.getString(KEY_AWAY_ID))));
			game.setDate(c.getString(c.getString(KEY_DATE)));

			game.sethomeshots((c.getInt(c.getString(KEY_HOME_SHOTS))));
			game.sethomesog((c.getInt(c.getString(KEY_HOME_SOG))));
			game.sethomegoals(c.getInt(c.getString(KEY_HOME_GOALS)));
			game.sethomeast((c.getInt(c.getString(KEY_HOME_AST))));
			game.sethomepenminor((c.getInt(c.getString(KEY_HOME_PEN_MINOR))));
			game.sethomepenmajor(c.getInt(c.getString(KEY_HOME_PEN_MAJOR)));
			game.sethomepenmisconduct(c.getInt(c.getString(KEY_HOME_PEN_MISCONDUCT)));
			game.sethomesaves((c.getInt(c.getString(KEY_HOME_SAVES))));
			game.sethomegoalsallowed((c.getInt(c.getString(KEY_HOME_GOALS_ALLOWED))));

			game.setawayshots((c.getInt(c.getString(KEY_AWAY_SHOTS))));
			game.setawaysog((c.getInt(c.getString(KEY_AWAY_SOG))));
			game.setawaygoals(c.getInt(c.getString(KEY_AWAY_GOALS)));
			game.setawayast((c.getInt(c.getString(KEY_AWAY_AST))));
			game.setawaypenminor((c.getInt(c.getString(KEY_AWAY_PEN_MINOR))));
			game.setawaypenmajor(c.getInt(c.getString(KEY_AWAY_PEN_MAJOR)));
			game.setawaypenmisconduct(c.getInt(c.getString(KEY_AWAY_PEN_MISCONDUCT)));
			game.setawaysaves((c.getInt(c.getString(KEY_AWAY_SAVES))));
			game.setawaygoalsallowed((c.getInt(c.getString(KEY_AWAY_GOALS_ALLOWED))));

			// adding to games list
			games.add(game);

		}

		return games;
	}

	//get single game stat for team
	public int getTeamGameStat(long g_id, String stat) throws InterruptedException, ExecutionException, JSONException {
		//create query to select game
		String query = "SELECT " + stat + " FROM " + _schema + "." + TABLE_GAMES + 
				" WHERE " + KEY_G_ID + " = " + g_id; 
		List<NameValuePair> params = this.startParams();
		params.add(new BasicNameValuePair(TAG_QUERY, query));
		params.add(new BasicNameValuePair(TAG_STAT, stat));


		HttpParameter parameter = new HttpParameter(url_get_stat, "POST", params);
		AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
		JSONObject json = result.get();
		JSONArray stats = json.getJSONArray(TAG_STAT);
		JSONObject c = stats.getJSONObject(0);
		return Integer.parseInt(c.getString(TAG_STAT_VALUE));
	}

	//Adding value to points category of a player
	public void addTeamStats(long g_id, String stat, int value) throws InterruptedException, ExecutionException, JSONException{
		int old_value = getTeamGameStat(g_id,stat);
		int new_value = old_value + value;

		String w = KEY_G_ID + " = " + g_id;
		List<NameValuePair> params = this.startParams();
		params.add(new BasicNameValuePair(TAG_TABLE, TABLE_GAMES));
		params.add(new BasicNameValuePair(TAG_COLUMN, stat));
		params.add(new BasicNameValuePair(TAG_VALUE, Integer.toString(new_value)));
		params.add(new BasicNameValuePair(TAG_WHERE, w));

		HttpParameter parameter = new HttpParameter(url_update, "POST", params);
		AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
	}

	// Delete a Game
	public void deleteGame(long g_id) {
		List<NameValuePair> params = this.startParams();
		params.add(new BasicNameValuePair("table", TABLE_GAMES));
		params.add(new BasicNameValuePair("key_id", KEY_G_ID));
		params.add(new BasicNameValuePair("key_value", Long.toString(g_id)));

		HttpParameter parameter = new HttpParameter(url_delete, "POST", params);
		AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
	}


	// ----------------------- GAME_STATS table methods ------------------------- //

	public void createGameStats(long p_id, long g_id){

		List<NameValuePair> params = this.startParams();

		params.add(new BasicNameValuePair(KEY_G_ID, Long.toString(g_id)));
		params.add(new BasicNameValuePair(KEY_P_ID, Long.toString(p_id)));

		//add default values in php
		/*
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
		 */
		// insert row
		HttpParameter parameter = new HttpParameter(url_insert_game_stat,"POST", params);
		AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
	}

	//get single game stats for single player
	public HockeyGameStats getPlayerGameStats(long g_id, long p_id) throws JSONException, InterruptedException, ExecutionException {

		//create query to select game
		String w = " WHERE " + KEY_G_ID + " = " + g_id + 
				" AND " + KEY_P_ID + " = " + p_id;
		List<NameValuePair> params = this.startParams();
		params.add(new BasicNameValuePair(TAG_WHERE, w));

		HttpParameter parameter = new HttpParameter(url_get_game_stat, "POST", params);
		AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
		JSONObject json = result.get();
		JSONArray stat = json.getJSONArray(TAG_HOCKEY_GAME_STATS);


		JSONObject c = stat.getJSONObject(0);

		HockeyGameStats stats = new HockeyGameStats();
		stats.setgid(c.getLong(c.getString(KEY_G_ID)));
		stats.setpid(c.getLong(c.getString(KEY_P_ID)));
		stats.setshots((c.getInt(c.getString(KEY_SHOTS))));
		stats.setsog((c.getInt(c.getString(KEY_SOG))));
		stats.setgoals(c.getInt(c.getString(KEY_GOALS)));
		stats.setast((c.getInt(c.getString(KEY_AST))));
		stats.setpenminor((c.getInt(c.getString(KEY_PEN_MINOR))));
		stats.setpenmajor(c.getInt(c.getString(KEY_PEN_MAJOR)));
		stats.setpenmisconduct(c.getInt(c.getString(KEY_PEN_MISCONDUCT)));
		stats.setsaves((c.getInt(c.getString(KEY_SAVES))));
		stats.setgoalsallowed((c.getInt(c.getString(KEY_GOALS_ALLOWED))));

		//Insert more stats here

		return stats;
	}

	//get single game stats for single player
	public int getPlayerGameStat(long g_id, long p_id, String stat) throws InterruptedException, ExecutionException, JSONException {
		 //create query to select game
	    String query = "SELECT " + stat + " FROM " + _schema + "." + TABLE_HOCKEY_GAME_STATS + 
		    	" WHERE " + KEY_G_ID + " = " + g_id + 
		    	" AND " + KEY_P_ID + " = " + p_id;
		List<NameValuePair> params = this.startParams();
		params.add(new BasicNameValuePair(TAG_QUERY, query));
		params.add(new BasicNameValuePair(TAG_STAT, stat));
	
		
		HttpParameter parameter = new HttpParameter(url_get_stat, "POST", params);
		AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
		JSONObject json = result.get();
		JSONArray stats = json.getJSONArray(TAG_STAT);
		JSONObject c = stats.getJSONObject(0);
		return Integer.parseInt(c.getString(TAG_STAT_VALUE));
	}

	//Get all GameStats for player
	public List<HockeyGameStats> getPlayerAllGameStats(long p_id) throws JSONException, InterruptedException, ExecutionException {
		
		 List<HockeyGameStats> gameStats = new ArrayList<HockeyGameStats>();
		    String w = " WHERE " + KEY_P_ID + " = " + p_id ;
		    
		    List<NameValuePair> params = this.startParams();
			params.add(new BasicNameValuePair(TAG_WHERE, w));
			
			HttpParameter parameter = new HttpParameter(url_get_game_stat, "POST", params);
			AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
			JSONObject json = result.get();
			JSONArray stat = json.getJSONArray(TAG_HOCKEY_GAME_STATS);
			
			for (int i = 0; i < stat.length(); i++) {
			JSONObject c = stat.getJSONObject(i);

		    HockeyGameStats stats = new HockeyGameStats();
			stats.setgid(c.getLong(c.getString(KEY_G_ID)));
			stats.setpid(c.getLong(c.getString(KEY_P_ID)));
			stats.setshots((c.getInt(c.getString(KEY_SHOTS))));
			stats.setsog((c.getInt(c.getString(KEY_SOG))));
			stats.setgoals(c.getInt(c.getString(KEY_GOALS)));
			stats.setast((c.getInt(c.getString(KEY_AST))));
			stats.setpenminor((c.getInt(c.getString(KEY_PEN_MINOR))));
			stats.setpenmajor(c.getInt(c.getString(KEY_PEN_MAJOR)));
			stats.setpenmisconduct(c.getInt(c.getString(KEY_PEN_MISCONDUCT)));
			stats.setsaves((c.getInt(c.getString(KEY_SAVES))));
			stats.setgoalsallowed((c.getInt(c.getString(KEY_GOALS_ALLOWED))));
		    gameStats.add(stats);
			}
		    //Insert more stats here
		    
		    return gameStats;

	}

	//Get all GameStats
	public List<HockeyGameStats> getAllGameStats() throws InterruptedException, ExecutionException, JSONException {
		List<HockeyGameStats> gameStats = new ArrayList<HockeyGameStats>();
	    String w = "";
	    
	    List<NameValuePair> params = this.startParams();
		params.add(new BasicNameValuePair(TAG_WHERE, w));
		
		HttpParameter parameter = new HttpParameter(url_get_game_stat, "POST", params);
		AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
		JSONObject json = result.get();
		JSONArray stat = json.getJSONArray(TAG_HOCKEY_GAME_STATS);
		
		for (int i = 0; i < stat.length(); i++) {
		JSONObject c = stat.getJSONObject(i);

	    HockeyGameStats stats = new HockeyGameStats();
		stats.setgid(c.getLong(c.getString(KEY_G_ID)));
		stats.setpid(c.getLong(c.getString(KEY_P_ID)));
		stats.setshots((c.getInt(c.getString(KEY_SHOTS))));
		stats.setsog((c.getInt(c.getString(KEY_SOG))));
		stats.setgoals(c.getInt(c.getString(KEY_GOALS)));
		stats.setast((c.getInt(c.getString(KEY_AST))));
		stats.setpenminor((c.getInt(c.getString(KEY_PEN_MINOR))));
		stats.setpenmajor(c.getInt(c.getString(KEY_PEN_MAJOR)));
		stats.setpenmisconduct(c.getInt(c.getString(KEY_PEN_MISCONDUCT)));
		stats.setsaves((c.getInt(c.getString(KEY_SAVES))));
		stats.setgoalsallowed((c.getInt(c.getString(KEY_GOALS_ALLOWED))));
	    gameStats.add(stats);
		}
	    //Insert more stats here
	    
	    return gameStats;
	}

	// Delete a GameStats
	public void deleteGameStats(long g_id) {
		List<NameValuePair> params = this.startParams();
		params.add(new BasicNameValuePair("table", TABLE_GAME_STATS));
		params.add(new BasicNameValuePair("key_id", KEY_G_ID));
		params.add(new BasicNameValuePair("key_value", Long.toString(g_id)));
		
		HttpParameter parameter = new HttpParameter(url_delete, "POST", params);
		AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
	}

	//ADDING STATS

	//Adding value to points category of a player
	public void addStats(long g_id, long p_id, String stat, int value) throws InterruptedException, ExecutionException, JSONException{
		
		int old_value = getTeamGameStat(g_id,stat);
		int new_value = old_value + value;
		
		String w = KEY_P_ID + " = " + p_id + " AND " + KEY_G_ID + " = " + g_id;
		List<NameValuePair> params = this.startParams();
		params.add(new BasicNameValuePair(TAG_TABLE, TABLE_GAME_STATS));
		params.add(new BasicNameValuePair(TAG_COLUMN, stat));
		params.add(new BasicNameValuePair(TAG_VALUE, Integer.toString(new_value)));
		params.add(new BasicNameValuePair(TAG_WHERE, w));

		HttpParameter parameter = new HttpParameter(url_update, "POST", params);
		AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
		
		
		
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

	public void createPlayers(HockeyPlayer player, Long p_id){
		List<NameValuePair> params = this.startParams();			

		params.add(new BasicNameValuePair(KEY_P_ID, Long.toString(p_id)));
		params.add(new BasicNameValuePair(KEY_T_ID, Long.toString(player.gettid())));
	    params.add(new BasicNameValuePair(KEY_P_NAME, player.getpname()));
	    params.add(new BasicNameValuePair(KEY_P_NUM, Integer.toString(player.getpnum())));
		 
		
		HttpParameter parameter = new HttpParameter(url_insert_player,"POST", params);
		AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
	}

	//get single player
	public HockeyPlayer getPlayer(long p_id) throws InterruptedException, ExecutionException, JSONException {
		String w = " WHERE " + KEY_P_ID + " = " + p_id;;
		List<NameValuePair> params = this.startParams();
		params.add(new BasicNameValuePair(TAG_WHERE, w));
		
		HttpParameter parameter = new HttpParameter(url_get_players, "POST", params);
		AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
		JSONObject json = result.get();
		JSONArray playerArray = json.getJSONArray(TAG_PLAYERS);
		
		
			JSONObject c = playerArray.getJSONObject(0);
			HockeyPlayer player = new HockeyPlayer();
			player.setpid(Long.parseLong(c.getString(KEY_P_ID)));
			player.settid(Long.parseLong(c.getString(KEY_T_ID)));
			player.setpname((c.getString(KEY_P_NAME)));
			player.setpnum((Integer.parseInt(c.getString(KEY_P_NUM))));
	
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

	public void deleteAll() throws InterruptedException, ExecutionException, JSONException{
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
}
