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
import com.seniordesign.ultimatescorecard.data.soccer.SoccerPlayer;
import com.seniordesign.ultimatescorecard.sqlite.basketball.BasketballGameStats;
import com.seniordesign.ultimatescorecard.sqlite.helper.DatabaseHelper;
import com.seniordesign.ultimatescorecard.sqlite.helper.Games;
import com.seniordesign.ultimatescorecard.sqlite.helper.PlayByPlay;
import com.seniordesign.ultimatescorecard.sqlite.helper.Players;
import com.seniordesign.ultimatescorecard.sqlite.soccer.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.os.AsyncTask;
import android.util.Log;

public class SoccerNetworkHelper extends NetworkHelper{

	// Logcat tag
	private static final String LOG = "SoccerDatabaseHelper";

	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "SoccerStats";

	//Table Names
	private static final String TABLE_SOCCER_GAME_STATS = "soccer_game_stats";
	private static final String TABLE_GAMES = "SOCCER_GAMES";
	private static final String TABLE_GAME_STATS = "SOCCER_GAME_STATS";

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

	//Log In Tags

	protected static final String user = "user";
	protected static final String password = "password";
	protected static final String schema = "schema";

	//Table Tags
	protected static final String TAG_STAT = "stat";
	protected static final String TAG_STAT_VALUE = "stat_value";
	protected static final String TAG_SOCCER_GAMES = "soccer_games";
	protected static final String TAG_SOCCER_GAME_STATS = "soccer_games_stats";

	//URL Information
	protected static final String dburl = "http://tusk.zapto.org/php/";
	protected static final String url_delete = dburl + "delete_entry.php";

	protected static final String url_insert_createGame = dburl + "insert_create_game_soccer.php";
	protected static final String url_get_game = dburl + "get_game_soccer.php";
	protected static final String url_get_games_by_team = dburl + "get_games_by_team.php";
	protected static final String url_get_team_game_stat = dburl + "get_team_game_stat.php";
	protected static final String url_insert_game_stat = dburl + "insert_soccer_game_stat.php";
	protected static final String url_get_game_stat = dburl + "get_soccer_game_stat.php";
	protected static final String url_get_stat = dburl + "get_stat.php";

	//DB Tags
	protected static final String TAG_PBP = "play_by_play";
	protected static final String TAG_PLAYERS = "players";
	protected static final String TAG_SHOTS = "shot_chart_coords";
	protected static final String TAG_TEAMS = "teams";
	protected static final String TAG_GAME = "game";
	protected static final String TAG_GAMES_BY_TEAM = "games_by_team";
	protected static final String TAG_TEAM_GAME_STAT = "team_game_stat";


	protected static final String TAG_WHERE = "where";
	// JSON parser class
	//JSONParser jsonParser = new JSONParser();


	private String _user;
	private String _password;
	private String _schema;


	//	public SoccerNetworkHelper(Context context) {
	//		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	//	}
	//
	//	public SoccerNetworkHelper(Context context, String name, CursorFactory factory,
	//			int version) {
	//		super(context, name, factory, version);
	//	}

	public SoccerNetworkHelper (String dbuser, String dbpass, String dbschema) {
		super(dbuser, dbpass, dbschema);
	}
	/*

	private List<NameValuePair> startParams() {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(user, _user));
		params.add(new BasicNameValuePair(password, _password));
		params.add(new BasicNameValuePair(schema, _schema));
		return params;
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
	 */
	// ----------------------- GAMES table methods ------------------------- //

	public void createGame(Games game, Long aid){
		List<NameValuePair> params = this.startParams();

		params.add(new BasicNameValuePair(KEY_HOME_ID, Long.toString(aid)));
		params.add(new BasicNameValuePair(KEY_AWAY_ID, Long.toString(game.getawayid())));
		params.add(new BasicNameValuePair(KEY_DATE, game.getDate()));

		params.add(new BasicNameValuePair(KEY_HOME_SHOTS, "0"));
		params.add(new BasicNameValuePair(KEY_HOME_SOG, "0"));
		params.add(new BasicNameValuePair(KEY_HOME_GOALS, "0"));
		params.add(new BasicNameValuePair(KEY_HOME_AST, "0"));
		params.add(new BasicNameValuePair(KEY_HOME_FOULS, "0"));
		params.add(new BasicNameValuePair(KEY_HOME_PKA, "0"));
		params.add(new BasicNameValuePair(KEY_HOME_PKG, "0"));
		params.add(new BasicNameValuePair(KEY_HOME_YCARD, "0"));
		params.add(new BasicNameValuePair(KEY_HOME_RCARD, "0"));
		params.add(new BasicNameValuePair(KEY_HOME_SAVES,"0"));
		params.add(new BasicNameValuePair(KEY_HOME_GOALS_ALLOWED, "0"));


		params.add(new BasicNameValuePair(KEY_AWAY_SHOTS, "0"));
		params.add(new BasicNameValuePair(KEY_AWAY_SOG, "0"));
		params.add(new BasicNameValuePair(KEY_AWAY_GOALS, "0"));
		params.add(new BasicNameValuePair(KEY_AWAY_AST, "0"));
		params.add(new BasicNameValuePair(KEY_AWAY_FOULS, "0"));
		params.add(new BasicNameValuePair(KEY_AWAY_PKA, "0"));
		params.add(new BasicNameValuePair(KEY_AWAY_PKG, "0"));
		params.add(new BasicNameValuePair(KEY_AWAY_YCARD, "0"));
		params.add(new BasicNameValuePair(KEY_AWAY_RCARD, "0"));
		params.add(new BasicNameValuePair(KEY_AWAY_SAVES,"0"));
		params.add(new BasicNameValuePair(KEY_AWAY_GOALS_ALLOWED, "0"));

		HttpParameter parameter = new HttpParameter(url_insert_createGame,	"POST", params);
		AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);

	}

	//get single game
	public Games getGame(long g_id) throws JSONException, InterruptedException, ExecutionException{
		String w = "where g_id = " + Long.toString(g_id);
		SoccerGames game = new SoccerGames();
		List<NameValuePair> params = this.startParams();
		params.add(new BasicNameValuePair(TAG_WHERE, w));

		HttpParameter parameter = new HttpParameter(url_get_game, "POST", params);
		AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
		JSONObject json = result.get();
		JSONArray gameStats = json.getJSONArray(TAG_GAME);

		for (int i = 0; i < gameStats.length(); i++){
			JSONObject c = gameStats.getJSONObject(i);

			//create the instance of Games using cursor information
			game.setgid(Long.parseLong(c.getString(KEY_G_ID)));
			game.sethomeid(Long.parseLong(c.getString(KEY_HOME_ID)));
			game.setawayid(Long.parseLong(c.getString(KEY_AWAY_ID)));
			game.setDate(c.getString(KEY_DATE));

			game.sethomeshots(Integer.parseInt(c.getString(KEY_HOME_SHOTS)));
			game.sethomesog(Integer.parseInt(c.getString(KEY_HOME_SOG)));
			game.sethomegoals(Integer.parseInt(c.getString(KEY_HOME_GOALS)));
			game.sethomeast(Integer.parseInt(c.getString(KEY_HOME_AST)));
			game.sethomefouls(Integer.parseInt(c.getString(KEY_HOME_FOULS)));
			game.sethomepka(Integer.parseInt(c.getString(KEY_HOME_PKA)));
			game.sethomepkg(Integer.parseInt(c.getString(KEY_HOME_PKG)));
			game.sethomeycard(Integer.parseInt(c.getString(KEY_HOME_YCARD)));
			game.sethomercard(Integer.parseInt(c.getString(KEY_HOME_RCARD)));
			game.sethomesaves(Integer.parseInt(c.getString(KEY_HOME_SAVES)));
			game.sethomegoalsallowed(Integer.parseInt(c.getString(KEY_HOME_GOALS_ALLOWED)));

			game.setawayshots(Integer.parseInt(c.getString(KEY_AWAY_SHOTS)));
			game.setawaysog(Integer.parseInt(c.getString(KEY_AWAY_SOG)));
			game.setawaygoals(Integer.parseInt(c.getString(KEY_AWAY_GOALS)));
			game.setawayast(Integer.parseInt(c.getString(KEY_AWAY_AST)));
			game.setawayfouls(Integer.parseInt(c.getString(KEY_AWAY_FOULS)));
			game.setawaypka(Integer.parseInt(c.getString(KEY_AWAY_PKA)));
			game.setawaypkg(Integer.parseInt(c.getString(KEY_AWAY_PKG)));
			game.setawayycard(Integer.parseInt(c.getString(KEY_AWAY_YCARD)));
			game.setawayrcard(Integer.parseInt(c.getString(KEY_AWAY_RCARD)));
			game.setawaysaves(Integer.parseInt(c.getString(KEY_AWAY_SAVES)));
			game.setawaygoalsallowed(Integer.parseInt(c.getString(KEY_AWAY_GOALS_ALLOWED)));
		}

		return game;
	}

	// Get all games played by a team
	public List<Games> getAllGamesTeam(long t_id) throws NumberFormatException, JSONException, InterruptedException, ExecutionException {
		String w = "where " + KEY_HOME_ID + " = " + Long.toString(t_id) + " OR " + KEY_AWAY_ID + " = " + t_id;
		List<Games> games = new ArrayList<Games>();
		List<NameValuePair> params = this.startParams();
		params.add(new BasicNameValuePair(TAG_WHERE, w));

		HttpParameter parameter = new HttpParameter(url_get_games_by_team, "POST", params);
		AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
		JSONObject json = result.get();
		JSONArray gamesObj = json.getJSONArray(TAG_GAMES_BY_TEAM);

		// looping through all rows and adding to list
		for (int i = 0; i < gamesObj.length(); i++) {
			JSONObject c = gamesObj.getJSONObject(i);
			SoccerGames game = new SoccerGames();
			game.setgid(Long.parseLong(c.getString(KEY_G_ID)));
			game.sethomeid(Long.parseLong(c.getString(KEY_HOME_ID)));
			game.setawayid(Long.parseLong(c.getString(KEY_AWAY_ID)));
			game.setDate(c.getString(KEY_DATE));

			game.sethomeshots(Integer.parseInt(c.getString(KEY_HOME_SHOTS)));
			game.sethomesog(Integer.parseInt(c.getString(KEY_HOME_SOG)));
			game.sethomegoals(Integer.parseInt(c.getString(KEY_HOME_GOALS)));
			game.sethomeast(Integer.parseInt(c.getString(KEY_HOME_AST)));
			game.sethomefouls(Integer.parseInt(c.getString(KEY_HOME_FOULS)));
			game.sethomepka(Integer.parseInt(c.getString(KEY_HOME_PKA)));
			game.sethomepkg(Integer.parseInt(c.getString(KEY_HOME_PKG)));
			game.sethomeycard(Integer.parseInt(c.getString(KEY_HOME_YCARD)));
			game.sethomercard(Integer.parseInt(c.getString(KEY_HOME_RCARD)));
			game.sethomesaves(Integer.parseInt(c.getString(KEY_HOME_SAVES)));
			game.sethomegoalsallowed(Integer.parseInt(c.getString(KEY_HOME_GOALS_ALLOWED)));

			game.setawayshots(Integer.parseInt(c.getString(KEY_AWAY_SHOTS)));
			game.setawaysog(Integer.parseInt(c.getString(KEY_AWAY_SOG)));
			game.setawaygoals(Integer.parseInt(c.getString(KEY_AWAY_GOALS)));
			game.setawayast(Integer.parseInt(c.getString(KEY_AWAY_AST)));
			game.setawayfouls(Integer.parseInt(c.getString(KEY_AWAY_FOULS)));
			game.setawaypka(Integer.parseInt(c.getString(KEY_AWAY_PKA)));
			game.setawaypkg(Integer.parseInt(c.getString(KEY_AWAY_PKG)));
			game.setawayycard(Integer.parseInt(c.getString(KEY_AWAY_YCARD)));
			game.setawayrcard(Integer.parseInt(c.getString(KEY_AWAY_RCARD)));
			game.setawaysaves(Integer.parseInt(c.getString(KEY_AWAY_SAVES)));
			game.setawaygoalsallowed(Integer.parseInt(c.getString(KEY_AWAY_GOALS_ALLOWED)));

			// adding to games list
			games.add(game);
		}

		return games;
	}

	//Get all Games
	public List<Games> getAllGames() throws InterruptedException, ExecutionException, JSONException {
		String w = "";
		List<Games> games = new ArrayList<Games>();
		List<NameValuePair> params = this.startParams();
		params.add(new BasicNameValuePair(TAG_WHERE, w));

		HttpParameter parameter = new HttpParameter(url_get_games_by_team, "POST", params);
		AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
		JSONObject json = result.get();
		JSONArray gamesObj = json.getJSONArray(TAG_GAMES_BY_TEAM);

		// looping through all rows and adding to list
		for (int i = 0; i < gamesObj.length(); i++) {
			JSONObject c = gamesObj.getJSONObject(i);
			SoccerGames game = new SoccerGames();
			game.setgid(Long.parseLong(c.getString(KEY_G_ID)));
			game.sethomeid(Long.parseLong(c.getString(KEY_HOME_ID)));
			game.setawayid(Long.parseLong(c.getString(KEY_AWAY_ID)));
			game.setDate(c.getString(KEY_DATE));

			game.sethomeshots(Integer.parseInt(c.getString(KEY_HOME_SHOTS)));
			game.sethomesog(Integer.parseInt(c.getString(KEY_HOME_SOG)));
			game.sethomegoals(Integer.parseInt(c.getString(KEY_HOME_GOALS)));
			game.sethomeast(Integer.parseInt(c.getString(KEY_HOME_AST)));
			game.sethomefouls(Integer.parseInt(c.getString(KEY_HOME_FOULS)));
			game.sethomepka(Integer.parseInt(c.getString(KEY_HOME_PKA)));
			game.sethomepkg(Integer.parseInt(c.getString(KEY_HOME_PKG)));
			game.sethomeycard(Integer.parseInt(c.getString(KEY_HOME_YCARD)));
			game.sethomercard(Integer.parseInt(c.getString(KEY_HOME_RCARD)));
			game.sethomesaves(Integer.parseInt(c.getString(KEY_HOME_SAVES)));
			game.sethomegoalsallowed(Integer.parseInt(c.getString(KEY_HOME_GOALS_ALLOWED)));

			game.setawayshots(Integer.parseInt(c.getString(KEY_AWAY_SHOTS)));
			game.setawaysog(Integer.parseInt(c.getString(KEY_AWAY_SOG)));
			game.setawaygoals(Integer.parseInt(c.getString(KEY_AWAY_GOALS)));
			game.setawayast(Integer.parseInt(c.getString(KEY_AWAY_AST)));
			game.setawayfouls(Integer.parseInt(c.getString(KEY_AWAY_FOULS)));
			game.setawaypka(Integer.parseInt(c.getString(KEY_AWAY_PKA)));
			game.setawaypkg(Integer.parseInt(c.getString(KEY_AWAY_PKG)));
			game.setawayycard(Integer.parseInt(c.getString(KEY_AWAY_YCARD)));
			game.setawayrcard(Integer.parseInt(c.getString(KEY_AWAY_RCARD)));
			game.setawaysaves(Integer.parseInt(c.getString(KEY_AWAY_SAVES)));
			game.setawaygoalsallowed(Integer.parseInt(c.getString(KEY_AWAY_GOALS_ALLOWED)));

			// adding to games list
			games.add(game);
		}

		return games;
	}
	//get single game stat for team
	public int getTeamGameStat(long g_id, String stat) throws JSONException, InterruptedException, ExecutionException {
		String w = "where " + KEY_G_ID + " = " + Long.toString(g_id);
		List<NameValuePair> params = this.startParams();
		params.add(new BasicNameValuePair(TAG_WHERE, w));

		HttpParameter parameter = new HttpParameter(url_get_team_game_stat, "POST", params);
		AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
		JSONObject json = result.get();
		JSONArray gamesObj = json.getJSONArray(TAG_TEAM_GAME_STAT);

		JSONObject c = gamesObj.getJSONObject(0);
		int stat_value = c.getInt(stat);

		return stat_value;
	}

	//Adding value to points category of a player
	public void addTeamStats(long g_id, String stat, int value) throws JSONException, InterruptedException, ExecutionException{
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
		/*
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
		 */

		HttpParameter parameter = new HttpParameter(url_insert_game_stat,"POST", params);
		AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
	}

	//get single game stats for single player
	public SoccerGameStats getPlayerGameStats(long g_id, long p_id) throws JSONException, InterruptedException, ExecutionException {
		//create query to select game
		String w = " WHERE " + KEY_G_ID + " = " + g_id + 
				" AND " + KEY_P_ID + " = " + p_id;

		List<NameValuePair> params = this.startParams();
		params.add(new BasicNameValuePair(TAG_WHERE, w));

		HttpParameter parameter = new HttpParameter(url_get_game_stat, "POST", params);
		AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
		JSONObject json = result.get();
		JSONArray stat = json.getJSONArray(TAG_SOCCER_GAME_STATS);


		JSONObject c = stat.getJSONObject(0);


		SoccerGameStats stats = new SoccerGameStats();
		stats.setgid(c.getLong(c.getString(KEY_G_ID)));
		stats.setpid(c.getLong(c.getString(KEY_P_ID)));
		stats.setshots((c.getInt(c.getString(KEY_SHOTS))));
		stats.setsog((c.getInt(c.getString(KEY_SOG))));
		stats.setgoals(c.getInt(c.getString(KEY_GOALS)));
		stats.setast((c.getInt(c.getString(KEY_AST))));
		stats.setfouls((c.getInt(c.getString(KEY_FOULS))));
		stats.setpka(c.getInt(c.getString(KEY_PKA)));
		stats.setpkg((c.getInt(c.getString(KEY_PKG))));
		stats.setycard(c.getInt(c.getString(KEY_YCARD)));
		stats.setrcard((c.getInt(c.getString(KEY_RCARD))));
		stats.setsaves((c.getInt(c.getString(KEY_SAVES))));
		stats.setgoalsallowed((c.getInt(c.getString(KEY_GOALS_ALLOWED))));

		return stats;
	}

	//get single game stats for single player
	public int getPlayerGameStat(long g_id, long p_id, String stat) throws InterruptedException, ExecutionException, JSONException {
		//create query to select game
		String query = "SELECT " + stat + " FROM " + _schema + "." + TABLE_SOCCER_GAME_STATS + 
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
	public List<SoccerGameStats> getPlayerAllGameStats(long p_id) throws JSONException, InterruptedException, ExecutionException {
		List<SoccerGameStats> gameStats = new ArrayList<SoccerGameStats>();
		String w = " WHERE " + KEY_P_ID + " = " + p_id ;

		List<NameValuePair> params = this.startParams();
		params.add(new BasicNameValuePair(TAG_WHERE, w));

		HttpParameter parameter = new HttpParameter(url_get_game_stat, "POST", params);
		AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
		JSONObject json = result.get();
		JSONArray stat = json.getJSONArray(TAG_SOCCER_GAME_STATS);

		for (int i = 0; i < stat.length(); i++) {
			JSONObject c = stat.getJSONObject(i);
			SoccerGameStats stats = new SoccerGameStats();
			stats.setgid(c.getLong(c.getString(KEY_G_ID)));
			stats.setpid(c.getLong(c.getString(KEY_P_ID)));
			stats.setshots((c.getInt(c.getString(KEY_SHOTS))));
			stats.setsog((c.getInt(c.getString(KEY_SOG))));
			stats.setgoals(c.getInt(c.getString(KEY_GOALS)));
			stats.setast((c.getInt(c.getString(KEY_AST))));
			stats.setfouls((c.getInt(c.getString(KEY_FOULS))));
			stats.setpka(c.getInt(c.getString(KEY_PKA)));
			stats.setpkg((c.getInt(c.getString(KEY_PKG))));
			stats.setycard(c.getInt(c.getString(KEY_YCARD)));
			stats.setrcard((c.getInt(c.getString(KEY_RCARD))));
			stats.setsaves((c.getInt(c.getString(KEY_SAVES))));
			stats.setgoalsallowed((c.getInt(c.getString(KEY_GOALS_ALLOWED))));
			//Insert more stats here

			// adding to gameStats list
			gameStats.add(stats);

		}
		//Insert more stats here

		return gameStats;

	}

	//Get all GameStats
	public List<SoccerGameStats> getAllGameStats() throws JSONException, InterruptedException, ExecutionException {
		List<SoccerGameStats> gameStats = new ArrayList<SoccerGameStats>();
		String w = "";

		List<NameValuePair> params = this.startParams();
		params.add(new BasicNameValuePair(TAG_WHERE, w));

		HttpParameter parameter = new HttpParameter(url_get_game_stat, "POST", params);
		AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
		JSONObject json = result.get();
		JSONArray stat = json.getJSONArray(TAG_SOCCER_GAME_STATS);

		for (int i = 0; i < stat.length(); i++) {
			JSONObject c = stat.getJSONObject(i);

			SoccerGameStats stats = new SoccerGameStats();
			stats.setgid(c.getLong(c.getString(KEY_G_ID)));
			stats.setpid(c.getLong(c.getString(KEY_P_ID)));
			stats.setshots((c.getInt(c.getString(KEY_SHOTS))));
			stats.setsog((c.getInt(c.getString(KEY_SOG))));
			stats.setgoals(c.getInt(c.getString(KEY_GOALS)));
			stats.setast((c.getInt(c.getString(KEY_AST))));
			stats.setfouls((c.getInt(c.getString(KEY_FOULS))));
			stats.setpka(c.getInt(c.getString(KEY_PKA)));
			stats.setpkg((c.getInt(c.getString(KEY_PKG))));
			stats.setycard(c.getInt(c.getString(KEY_YCARD)));
			stats.setrcard((c.getInt(c.getString(KEY_RCARD))));
			stats.setsaves((c.getInt(c.getString(KEY_SAVES))));
			stats.setgoalsallowed((c.getInt(c.getString(KEY_GOALS_ALLOWED))));
			//Insert more stats here

			// adding to gameStats list
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
	public void addStats(long g_id, long p_id, String stat, int value) throws JSONException, InterruptedException, ExecutionException{

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

	// ----------------------- PLAYERS table methods ------------------------- //

	public void createPlayers(SoccerPlayer player, Long p_id){

		List<NameValuePair> params = this.startParams();			
		params.add(new BasicNameValuePair(KEY_P_ID, Long.toString(p_id)));
		params.add(new BasicNameValuePair(KEY_T_ID, Long.toString(player.gettid())));
	    params.add(new BasicNameValuePair(KEY_P_NAME, player.getpname()));
	    params.add(new BasicNameValuePair(KEY_P_NUM, Integer.toString(player.getpnum())));
		 
		
		HttpParameter parameter = new HttpParameter(url_insert_player,"POST", params);
		AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
	}

	//get single player
	public SoccerPlayer getPlayer(long p_id) throws NumberFormatException, JSONException, InterruptedException, ExecutionException {
		String w = " WHERE " + KEY_P_ID + " = " + p_id;;
		List<NameValuePair> params = this.startParams();
		params.add(new BasicNameValuePair(TAG_WHERE, w));
		
		HttpParameter parameter = new HttpParameter(url_get_players, "POST", params);
		AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
		JSONObject json = result.get();
		JSONArray playerArray = json.getJSONArray(TAG_PLAYERS);
		
		
			JSONObject c = playerArray.getJSONObject(0);
			SoccerPlayer player = new SoccerPlayer();
			player.setpid(Long.parseLong(c.getString(KEY_P_ID)));
			player.settid(Long.parseLong(c.getString(KEY_T_ID)));
			player.setpname((c.getString(KEY_P_NAME)));
			player.setpnum((Integer.parseInt(c.getString(KEY_P_NUM))));
	
		return player;
	}

}
