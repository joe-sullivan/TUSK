package com.seniordesign.ultimatescorecard.networkdatabase.helper;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.seniordesign.ultimatescorecard.data.StatData;
import com.seniordesign.ultimatescorecard.data.basketball.BasketballPlayer;
import com.seniordesign.ultimatescorecard.data.soccer.SoccerPlayer;
import com.seniordesign.ultimatescorecard.sqlite.basketball.BasketballGameStats;
import com.seniordesign.ultimatescorecard.sqlite.helper.DatabaseHelper;
import com.seniordesign.ultimatescorecard.sqlite.helper.Games;
import com.seniordesign.ultimatescorecard.sqlite.helper.PlayByPlay;
import com.seniordesign.ultimatescorecard.sqlite.helper.Players;
import com.seniordesign.ultimatescorecard.sqlite.helper.ShotChartCoords;
import com.seniordesign.ultimatescorecard.sqlite.helper.Teams;
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
	private static final String TABLE_SOCCER_GAME_STATS = "SOCCER_GAME_STATS";
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
	protected static final String TAG_STAT_VALUE = "value";
	protected static final String TAG_SOCCER_GAMES = "soccer_games";
	protected static final String TAG_SOCCER_GAME_STATS = "soccer_game_stats";

	//URL Information
	protected static final String dburl = "http://tusk.zapto.org/php/";
	protected static final String url_delete = dburl + "delete_entry.php";
	protected static final String url_get_stat = dburl + "get_stat.php";

	//protected static final String url_insert_createGame = dburl + "insert_create_game_soccer.php";
	//protected static final String url_get_game = dburl + "get_game_soccer.php";
	//protected static final String url_get_games_by_team = dburl + "get_games_by_team.php";
	//protected static final String url_get_team_game_stat = dburl + "get_team_game_stat.php";
	//protected static final String url_insert_game_stat = dburl + "insert_soccer_game_stat.php";
	//protected static final String url_get_game_stat = dburl + "get_soccer_game_stat.php";
	//protected static final String url_get_stat = dburl + "get_stat.php";


	protected static final String url_insert_game = dburl + "insert_soccer_game.php";
	protected static final String url_get_game = dburl + "get_soccer_games.php";
	protected static final String url_insert_game_stat = dburl + "insert_soccer_game_stats.php";
	protected static final String url_get_game_stat = dburl + "get_soccer_game_stats.php";




	//URL Information
	protected static final String url_update = dburl + "update_table.php";


	protected static final String url_insert_pbp = dburl + "soccer_insert_play_by_play.php";
	protected static final String url_get_pbp = dburl + "soccer_get_play_by_play.php";
	protected static final String url_get_players = dburl + "soccer_get_players.php";
	protected static final String url_update_players = dburl + "soccer_update_player.php";
	protected static final String url_create_shot = dburl + "soccer_insert_shot_chart_coords.php";
	protected static final String url_get_shot = dburl + "soccer_get_shot_chart_coords.php";
	protected static final String url_create_teams = dburl + "soccer_insert_team.php";
	protected static final String url_get_teams = dburl + "soccer_get_teams.php";
	protected static final String url_update_teams = dburl + "soccer_update_team.php";
	protected static final String url_insert_player = dburl + "soccer_insert_player.php";



	//Table Info

	protected static final String TABLE_PLAYERS = "SOCCER_PLAYERS";
	protected static final String TABLE_TEAMS = "SOCCER_TEAMS";
	protected static final String TABLE_PLAY_BY_PLAY = "SOCCER_PLAY_BY_PLAY";
	protected static final String TABLE_SHOT_CHART_COORDS = "SOCCER_SHOT_CHART_COORDS";





	//DB Tags
	protected static final String TAG_PBP = "play_by_play";
	protected static final String TAG_PLAYERS = "players";
	protected static final String TAG_SHOTS = "shot_chart_coords";
	protected static final String TAG_TEAMS = "teams";
	protected static final String TAG_GAME = "soccer_games";
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
		_user = dbuser;
		_password = dbpass;
		_schema = dbschema;
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

	public void createGame(Games game, Long gid){
		List<NameValuePair> params = this.startParams();

		params.add(new BasicNameValuePair(KEY_G_ID, Long.toString(gid)));
		params.add(new BasicNameValuePair(KEY_HOME_ID, Long.toString(game.gethomeid())));
		params.add(new BasicNameValuePair(KEY_AWAY_ID, Long.toString(game.getawayid())));
		params.add(new BasicNameValuePair(KEY_DATE, game.getDate()));
		/*
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
		 */
		HttpParameter parameter = new HttpParameter(url_insert_game,	"POST", params);
		AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
		List<Players> home_players = getPlayersTeam(game.gethomeid());
		List<Players> away_players = getPlayersTeam(game.getawayid());

		for(Players player : home_players){
			createGameStats(player.getpid(), gid);
		}
		for(Players player : away_players){
			createGameStats(player.getpid(), gid);
		}


	}

	//get single game
	public Games getGame(long g_id) {
		try{
			String w = "where g_id = " + Long.toString(g_id);
			SoccerGames game = new SoccerGames();
			List<NameValuePair> params = this.startParams();
			params.add(new BasicNameValuePair(TAG_WHERE, w));
			//Log.i("SOC","Get Game where: " + w);
			HttpParameter parameter = new HttpParameter(url_get_game, "POST", params);
			AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
			JSONObject json = result.get();
			//Log.i("SOC","Get Game Response: " + json.toString());
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
		}catch(Exception ex){
			Log.i("INTEG", "S: getGame");
			Log.i("INTEG", ex.toString());
			return new SoccerGames();
		}
	}

	// Get all games played by a team
	public List<Games> getAllGamesTeam(long t_id) {
		String w = "where " + KEY_HOME_ID + " = " + Long.toString(t_id) + " OR " + KEY_AWAY_ID + " = " + t_id;
		List<Games> games = new ArrayList<Games>();
		try{
			List<NameValuePair> params = this.startParams();
			params.add(new BasicNameValuePair(TAG_WHERE, w));

			HttpParameter parameter = new HttpParameter(url_get_game, "POST", params);
			AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
			JSONObject json = result.get();
			JSONArray gamesObj = json.getJSONArray(TAG_SOCCER_GAMES);

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
		}catch(Exception ex){
			Log.i("INTEG", "S: getAllGamesTeam");
			Log.i("INTEG", ex.toString());
			return games;
		}
	}

	//Get all Games
	public List<Games> getAllGames() {
		String w = "";
		List<Games> games = new ArrayList<Games>();
		try{
			List<NameValuePair> params = this.startParams();
			params.add(new BasicNameValuePair(TAG_WHERE, w));

			HttpParameter parameter = new HttpParameter(url_get_game, "POST", params);
			AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
			JSONObject json = result.get();
			JSONArray gamesObj = json.getJSONArray(TAG_SOCCER_GAMES);

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
		}catch(Exception ex){
			Log.i("INTEG", "S: getAllGamesTeam");
			Log.i("INTEG", ex.toString());
			return games;
		}
	}
	//get single game stat for team
	public int getTeamGameStat(long g_id, String stat) {


		try{
			//create query to select game
			String query = "SELECT " + stat + " FROM " + _schema + "." + TABLE_GAMES + 
					" WHERE " + KEY_G_ID + " = " + g_id; 
			List<NameValuePair> params = this.startParams();
			params.add(new BasicNameValuePair(TAG_QUERY, query));
			params.add(new BasicNameValuePair(TAG_STAT, stat));

			Log.i("INTEG-EX", "start debugging getTeamGameStat");
			Log.i("INTEG-EX", "getstat query: " + query);
			Log.i("INTEG-EX", "getstat stat: " + stat);


			//Log.i("INTEG-EX", "getTeamGameStat info");
			//Log.i("INTEG-EX", "query: " + query);
			HttpParameter parameter = new HttpParameter(url_get_stat, "POST", params);
			AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
			JSONObject json = result.get();

			Log.i("INTEG-EX", "getstat json: " + json.toString());

			//Log.i("INTEG-EX", "json response: " + json.toString());
			JSONArray stats = json.getJSONArray(TAG_STAT);
			JSONObject c = stats.getJSONObject(0);
			return Integer.parseInt(c.getString(TAG_STAT_VALUE));
		}catch(Exception ex){
			Log.i("INTEG-EX", ex.toString());
			Log.i("INTEG-EX", "ex - S getTeamGameStat");
			return 0;
		}
	}

	//Adding value to points category of a player
	public int addTeamStats(ArrayList<StatData> statlist){

		for(StatData statdata: statlist){
			long g_id = statdata.getgid();
			String stat = statdata.getstat();
			int value = statdata.getvalue();
			int old_value = getTeamGameStat(g_id,stat);
			int new_value = old_value + value;

			String w = "WHERE " + KEY_G_ID + " = " + g_id;
			List<NameValuePair> params = this.startParams();
			params.add(new BasicNameValuePair(TAG_TABLE, TABLE_GAMES));
			params.add(new BasicNameValuePair(TAG_COLUMN, stat));
			params.add(new BasicNameValuePair(TAG_VALUE, Integer.toString(new_value)));
			params.add(new BasicNameValuePair(TAG_WHERE, w));

			HttpParameter parameter = new HttpParameter(url_update, "POST", params);
			AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
		}
		return 1;
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
	public SoccerGameStats getPlayerGameStats(long g_id, long p_id) {
		try{
			//create query to select game
			String w = " WHERE " + KEY_G_ID + " = " + g_id + 
					" AND " + KEY_P_ID + " = " + p_id;

			List<NameValuePair> params = this.startParams();
			params.add(new BasicNameValuePair(TAG_WHERE, w));

			HttpParameter parameter = new HttpParameter(url_get_game_stat, "POST", params);
			AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
			JSONObject json = result.get();
			Log.i("SOC:","pid gid Json response: " + json.toString());
			JSONArray stat = json.getJSONArray(TAG_SOCCER_GAME_STATS);

			Log.i("INTEG-EX", "start debugging getPlayerGameStat");
			Log.i("INTEG-EX", "getPlayer where: " + w);
			Log.i("INTEG-EX", "getPlayer json: " + json.toString());


			JSONObject c = stat.getJSONObject(0);


			SoccerGameStats stats = new SoccerGameStats();
			stats.setgid(Long.parseLong(c.getString(KEY_G_ID)));
			stats.setpid(Long.parseLong(c.getString(KEY_P_ID)));
			stats.setshots((Integer.parseInt(c.getString(KEY_SHOTS))));
			stats.setsog((Integer.parseInt(c.getString(KEY_SOG))));
			stats.setgoals(Integer.parseInt(c.getString(KEY_GOALS)));
			stats.setast((Integer.parseInt(c.getString(KEY_AST))));
			stats.setfouls((Integer.parseInt(c.getString(KEY_FOULS))));
			stats.setpka(Integer.parseInt(c.getString(KEY_PKA)));
			stats.setpkg((Integer.parseInt(c.getString(KEY_PKG))));
			stats.setycard(Integer.parseInt(c.getString(KEY_YCARD)));
			stats.setrcard((Integer.parseInt(c.getString(KEY_RCARD))));
			stats.setsaves((Integer.parseInt(c.getString(KEY_SAVES))));
			stats.setgoalsallowed((Integer.parseInt(c.getString(KEY_GOALS_ALLOWED))));

			return stats;
		}catch(Exception ex){
			Log.i("INTEG", "S: getPlayerGameStats g_id p_id");
			Log.i("INTEG", ex.toString());
			return new SoccerGameStats();
		}
	}

	//get single game stats for single player
	public int getPlayerGameStat(long g_id, long p_id, String stat) {
		try{
			//Log.i("SOC","Getplayergamestat start");
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
			/*
			Log.i("SOC","Query gamestat: " + query);
			Log.i("SOC","Stat gamestat: " + stat);
			Log.i("SOC","Get json response gamestat: " + json.toString());
			 */
			JSONArray stats = json.getJSONArray(TAG_STAT);
			JSONObject c = stats.getJSONObject(0);
			return Integer.parseInt(c.getString(TAG_STAT_VALUE));
		}catch(Exception ex){
			Log.i("INTEG", "S: getPlayerGameStat");
			Log.i("INTEG", ex.toString());
			return 0;
		}
	}

	//Get all GameStats for player
	public List<SoccerGameStats> getPlayerAllGameStats(long p_id) {
		List<SoccerGameStats> gameStats = new ArrayList<SoccerGameStats>();
		try{
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
				stats.setgid(Long.getLong(c.getString(KEY_G_ID)));
				stats.setpid(Long.getLong(c.getString(KEY_P_ID)));
				stats.setshots((Integer.parseInt(c.getString(KEY_SHOTS))));
				stats.setsog((Integer.parseInt(c.getString(KEY_SOG))));
				stats.setgoals(Integer.parseInt(c.getString(KEY_GOALS)));
				stats.setast((Integer.parseInt(c.getString(KEY_AST))));
				stats.setfouls((Integer.parseInt(c.getString(KEY_FOULS))));
				stats.setpka(Integer.parseInt(c.getString(KEY_PKA)));
				stats.setpkg((Integer.parseInt(c.getString(KEY_PKG))));
				stats.setycard(Integer.parseInt(c.getString(KEY_YCARD)));
				stats.setrcard((Integer.parseInt(c.getString(KEY_RCARD))));
				stats.setsaves((Integer.parseInt(c.getString(KEY_SAVES))));
				stats.setgoalsallowed((Integer.parseInt(c.getString(KEY_GOALS_ALLOWED))));
				//Insert more stats here

				// adding to gameStats list
				gameStats.add(stats);

			}
			//Insert more stats here

			return gameStats;
		}catch(Exception ex){
			Log.i("INTEG", "S: getPlayerAllGameStats");
			Log.i("INTEG", ex.toString());
			return gameStats;
		}

	}

	//Get all GameStats
	public List<SoccerGameStats> getAllGameStats(){
		List<SoccerGameStats> gameStats = new ArrayList<SoccerGameStats>();
		try{
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
				stats.setgid(Long.parseLong(c.getString(KEY_G_ID)));
				stats.setpid(Long.parseLong(c.getString(KEY_P_ID)));
				stats.setshots((Integer.parseInt(c.getString(KEY_SHOTS))));
				stats.setsog((Integer.parseInt(c.getString(KEY_SOG))));
				stats.setgoals(Integer.parseInt(c.getString(KEY_GOALS)));
				stats.setast((Integer.parseInt(c.getString(KEY_AST))));
				stats.setfouls((Integer.parseInt(c.getString(KEY_FOULS))));
				stats.setpka(Integer.parseInt(c.getString(KEY_PKA)));
				stats.setpkg((Integer.parseInt(c.getString(KEY_PKG))));
				stats.setycard(Integer.parseInt(c.getString(KEY_YCARD)));
				stats.setrcard((Integer.parseInt(c.getString(KEY_RCARD))));
				stats.setsaves((Integer.parseInt(c.getString(KEY_SAVES))));
				stats.setgoalsallowed((Integer.parseInt(c.getString(KEY_GOALS_ALLOWED))));
				//Insert more stats here

				// adding to gameStats list
				gameStats.add(stats);


			}
			//Insert more stats here

			return gameStats;
		}catch(Exception ex){
			Log.i("INTEG", "S: getAllGameStats");
			Log.i("INTEG", ex.toString());
			return gameStats;
		}
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
	public int addStats(ArrayList<StatData> statlist){

		for(StatData statdata: statlist){

			long g_id = statdata.getgid();
			long p_id = statdata.getpid();
			String stat = statdata.getstat();
			int value = statdata.getvalue();

			int old_value = getPlayerGameStat(g_id, p_id, stat);
			int new_value = old_value + value;

			String w = "WHERE " + KEY_P_ID + " = " + p_id + " AND " + KEY_G_ID + " = " + g_id;
			List<NameValuePair> params = this.startParams();
			params.add(new BasicNameValuePair(TAG_TABLE, TABLE_GAME_STATS));
			params.add(new BasicNameValuePair(TAG_COLUMN, stat));
			params.add(new BasicNameValuePair(TAG_VALUE, Integer.toString(new_value)));
			params.add(new BasicNameValuePair(TAG_WHERE, w));


			Log.i("SOC","addStats stat: " + stat);
			Log.i("SOC","addStats value: " + new_value);
			Log.i("SOC","addStats where: " + w);





			HttpParameter parameter = new HttpParameter(url_update, "POST", params);
			AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
			try {
				Log.i("SOC","Get json response gamestat: " + result.get().toString());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return 1;
	}

	// ----------------------- PLAYERS table methods ------------------------- //

	public void createPlayers(SoccerPlayer player, Long p_id){
		Log.i("SOC","Start player creation");
		List<NameValuePair> params = this.startParams();			
		params.add(new BasicNameValuePair(KEY_P_ID, Long.toString(p_id)));
		params.add(new BasicNameValuePair(KEY_T_ID, Long.toString(player.gettid())));
		params.add(new BasicNameValuePair(KEY_P_NAME, player.getpname()));
		params.add(new BasicNameValuePair(KEY_P_NUM, Integer.toString(player.getpnum())));

		Log.i("SOC","Player p id: " + p_id);
		Log.i("SOC","Player t id: " + player.gettid());
		Log.i("SOC","Player p name: " + player.getpname());
		Log.i("SOC","Player p num: " + player.getpnum());
		Log.i("SOC","end player call");


		HttpParameter parameter = new HttpParameter(url_insert_player,"POST", params);
		AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);

		Log.i("SOC","end player call");
	}

	//get single player
	public SoccerPlayer getPlayer(long p_id) {
		try{
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
		}catch(Exception ex){
			Log.i("INTEG", "S:getPlayer ");
			Log.i("INTEG", ex.toString());
			return new SoccerPlayer();
		}
	}



	public void createPlayByPlay(PlayByPlay pbp, long aid){
		List<NameValuePair> params = this.startParams();

		//add data from pbp to parameter list
		//TODO Decide how to handle a_id

		params.add(new BasicNameValuePair(KEY_A_ID, Long.toString(aid)));
		params.add(new BasicNameValuePair(KEY_G_ID, Long.toString(pbp.getgid())));
		params.add(new BasicNameValuePair(KEY_ACTION, pbp.getaction()));
		params.add(new BasicNameValuePair(KEY_TIME, pbp.gettime()));
		params.add(new BasicNameValuePair(KEY_PERIOD, pbp.getperiod()));
		params.add(new BasicNameValuePair(KEY_HOME_SCORE, Integer.toString(pbp.gethomescore())));
		params.add(new BasicNameValuePair(KEY_AWAY_SCORE, Integer.toString(pbp.getawayscore())));
		/*
		Log.i("NH", "NH Before sending request");
		// sending modified data through http request
		// Notice that update product url accepts POST method
		JSONObject json = jsonParser.makeHttpRequest(url_insert_pbp,
				"POST", params);
		 */

		HttpParameter parameter = new HttpParameter(url_insert_pbp,	"POST", params);
		AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
		try {
			JSONObject t = result.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	public List<PlayByPlay> getPlayByPlayGame(long g_id){
		//SQLiteDatabase db = this.getReadableDatabase();
		String w = "where g_id = " + Long.toString(g_id);
		List<PlayByPlay> pbps = new ArrayList<PlayByPlay>();
		List<NameValuePair> params = this.startParams();
		params.add(new BasicNameValuePair(TAG_WHERE, w));

		HttpParameter parameter = new HttpParameter(url_get_pbp, "POST", params);

		try {
			AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
			JSONObject json = result.get();
			JSONArray plays = json.getJSONArray(TAG_PBP);


			for (int i = 0; i < plays.length(); i++) {
				JSONObject c = plays.getJSONObject(i);
				PlayByPlay pbp = new PlayByPlay();

				pbp.setaid(Long.parseLong(c.getString(KEY_A_ID)));
				pbp.setgid(Long.parseLong(c.getString(KEY_G_ID)));
				pbp.setaction(c.getString(KEY_ACTION));
				pbp.settime(c.getString(KEY_TIME));
				pbp.setperiod(c.getString(KEY_PERIOD));
				pbp.sethomescore(Integer.parseInt(c.getString(KEY_HOME_SCORE)));
				pbp.setawayscore(Integer.parseInt(c.getString(KEY_AWAY_SCORE)));
				// adding to playbyplay list
				pbps.add(pbp);	
			}
			return pbps;
		}
		catch(Exception ex){
			Log.i("INTEG-EX", "ex - getPlayByPlay");
			return new ArrayList<PlayByPlay>();
		}
	}
	// Delete PlayByPlay of a game
	public void deletePlayByPlay(long a_id) {
		List<NameValuePair> params = this.startParams();
		params.add(new BasicNameValuePair("table", "PLAY_BY_PLAY"));
		params.add(new BasicNameValuePair("key_id", KEY_A_ID));
		params.add(new BasicNameValuePair("key_value", Long.toString(a_id)));

		HttpParameter parameter = new HttpParameter(url_delete, "POST", params);
		AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
	}

	// Delete PlayByPlay of a game
	public void deletePlayByPlayGame(long g_id) {
		List<NameValuePair> params = this.startParams();
		params.add(new BasicNameValuePair("table", "PLAY_BY_PLAY"));
		params.add(new BasicNameValuePair("key_id", KEY_G_ID));
		params.add(new BasicNameValuePair("key_value", Long.toString(g_id)));

		HttpParameter parameter = new HttpParameter(url_delete, "POST", params);
		AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);

	}

	// -------------------PLAYERS table methods----------------------------- //

	//TODO fix php to flexibly change where clause

	public List<Players> getPlayersTeam(long t_id) {
		try{
			List<Players> players = new ArrayList<Players>();
			//String selectPlayerQuery = "SELECT * FROM " + TABLE_PLAYERS + " WHERE " + KEY_T_ID + " = " + t_id;
			String w = " WHERE " + KEY_T_ID + " = " + t_id;
			List<NameValuePair> params = this.startParams();
			params.add(new BasicNameValuePair(TAG_WHERE, w));


			HttpParameter parameter = new HttpParameter(url_get_players, "POST", params);
			AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
			JSONObject json = result.get();
			JSONArray playerArray = json.getJSONArray(TAG_PLAYERS);

			for (int i = 0; i < playerArray.length(); i++) {
				JSONObject c = playerArray.getJSONObject(i);
				Players player = new Players();
				player.setpid(Long.parseLong(c.getString(KEY_P_ID)));
				player.settid(Long.parseLong(c.getString(KEY_T_ID)));
				player.setpname((c.getString(KEY_P_NAME)));
				player.setpnum((Integer.parseInt(c.getString(KEY_P_NUM))));
				players.add(player);	
			}
			return players;
		}
		catch(Exception ex){
			Log.i("INTEG-EX", "ex - getPlayersTeam");
			return  new ArrayList<Players>();
		}
	}

	public List<Players> getPlayersTeam2(long t_id) {
		try{
			//SQLiteDatabase db = this.getReadableDatabase();
			List<Players> players = new ArrayList<Players>();
			//String selectPlayerQuery = "SELECT * FROM " + TABLE_PLAYERS + " WHERE " + KEY_T_ID + " = " + t_id;
			String w = " WHERE " + KEY_T_ID + " = " + t_id;
			List<NameValuePair> params = this.startParams();
			params.add(new BasicNameValuePair(TAG_WHERE, w));


			HttpParameter parameter = new HttpParameter(url_get_players, "POST", params);
			AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
			JSONObject json = result.get();
			JSONArray playerArray = json.getJSONArray(TAG_PLAYERS);

			for (int i = 0; i < playerArray.length(); i++) {
				JSONObject c = playerArray.getJSONObject(i);
				Players player = new Players();
				player.setpid(Long.parseLong(c.getString(KEY_P_ID)));
				player.settid(Long.parseLong(c.getString(KEY_T_ID)));
				player.setpname((c.getString(KEY_P_NAME)));
				player.setpnum((Integer.parseInt(c.getString(KEY_P_NUM))));
				players.add(player);	
			}
			return players;
		}
		catch (Exception ex){
			Log.i("INTEG-EX", "ex - getPlayersTeam2");
			return  new ArrayList<Players>();
		}
	}

	public void updatePlayer(Players player){
		List<NameValuePair> params = this.startParams();

		//add data from pbp to parameter list
		//TODO Decide how to handle a_id
		params.add(new BasicNameValuePair(KEY_P_ID, Long.toString(player.getpid())));
		params.add(new BasicNameValuePair(KEY_T_ID, Long.toString(player.gettid())));
		params.add(new BasicNameValuePair(KEY_P_NAME, player.getpname()));
		params.add(new BasicNameValuePair(KEY_P_NUM, Integer.toString(player.getpnum())));

		// sending modified data through http request
		// Notice that update product url accepts POST method
		HttpParameter parameter = new HttpParameter(url_update_players, "POST", params);
		AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);

	}

	public List<Players> getAllPlayers() {
		try {
			//SQLiteDatabase db = this.getReadableDatabase();
			List<Players> players = new ArrayList<Players>();
			//String selectPlayerQuery = "SELECT * FROM " + TABLE_PLAYERS + " WHERE " + KEY_T_ID + " = " + t_id;
			String w = "";
			List<NameValuePair> params = this.startParams();
			params.add(new BasicNameValuePair(TAG_WHERE, w));

			HttpParameter parameter = new HttpParameter(url_get_players, "POST", params);
			AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
			JSONObject json = result.get();
			JSONArray playerArray = json.getJSONArray(TAG_PLAYERS);

			for (int i = 0; i < playerArray.length(); i++) {
				JSONObject c = playerArray.getJSONObject(i);
				Players player = new Players();
				player.setpid(Long.parseLong(c.getString(KEY_P_ID)));
				player.settid(Long.parseLong(c.getString(KEY_T_ID)));
				player.setpname((c.getString(KEY_P_NAME)));
				player.setpnum((Integer.parseInt(c.getString(KEY_P_NUM))));
				players.add(player);	
			}
			return players;
		}
		catch (Exception ex){
			Log.i("INTEG-EX", "ex - getAllPlayers");
			return new ArrayList<Players>();
		}
	}
	// Delete a Player
	public void deletePlayer(long p_id) {

		List<NameValuePair> params = this.startParams();
		params.add(new BasicNameValuePair("table", "PLAYERS"));
		params.add(new BasicNameValuePair("key_id", KEY_P_ID));
		params.add(new BasicNameValuePair("key_value", Long.toString(p_id)));

		HttpParameter parameter = new HttpParameter(url_delete, "POST", params);
		AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
	}


	// Delete Players on a team
	public void deletePlayers(long t_id) {

		List<NameValuePair> params = this.startParams();
		params.add(new BasicNameValuePair("table", "SOCCER_PLAYERS"));
		params.add(new BasicNameValuePair("key_id", KEY_T_ID));
		params.add(new BasicNameValuePair("key_value", Long.toString(t_id)));

		HttpParameter parameter = new HttpParameter(url_delete, "POST", params);
		AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
	}


	// -------------------SHOT_CHART_COORDS table methods ------------------ //

	//create a row of shot chart coordinates
	public void createShot(ShotChartCoords shot, Long shot_id){

		List<NameValuePair> params = this.startParams();
		params.add(new BasicNameValuePair(KEY_SHOT_ID, Long.toString(shot_id)));
		params.add(new BasicNameValuePair(KEY_G_ID, Long.toString(shot.getgid())));
		params.add(new BasicNameValuePair(KEY_P_ID, Long.toString(shot.getpid())));
		params.add(new BasicNameValuePair(KEY_T_ID, Long.toString(shot.gettid())));
		params.add(new BasicNameValuePair(KEY_X, Integer.toString(shot.getx())));
		params.add(new BasicNameValuePair(KEY_Y, Integer.toString(shot.gety())));
		params.add(new BasicNameValuePair(KEY_MADE, shot.getmade()));


		// sending modified data through http request
		// Notice that update product url accepts POST method
		HttpParameter parameter = new HttpParameter(url_create_shot, "POST", params);
		AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);

	}

	public List<ShotChartCoords> getAllShots() {

		try{
			List<ShotChartCoords> shots = new ArrayList<ShotChartCoords>();
			//String selectPlayerQuery = "SELECT * FROM " + TABLE_PLAYERS + " WHERE " + KEY_T_ID + " = " + t_id;
			String w = "";
			List<NameValuePair> params = this.startParams();
			params.add(new BasicNameValuePair(TAG_WHERE, w));

			HttpParameter parameter = new HttpParameter(url_get_shot, "POST", params);
			AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
			JSONObject json = result.get();
			JSONArray shotArray = json.getJSONArray(TAG_SHOTS);

			for (int i = 0; i < shotArray.length(); i++) {
				JSONObject c = shotArray.getJSONObject(i);
				ShotChartCoords shot = new ShotChartCoords();

				shot.setshotid(Long.parseLong(c.getString(KEY_SHOT_ID)));
				shot.setgid((Long.parseLong(c.getString(KEY_G_ID))));		    
				shot.setpid((Long.parseLong(c.getString(KEY_P_ID))));
				shot.settid((Long.parseLong(c.getString(KEY_T_ID))));
				shot.setx((Integer.parseInt(c.getString(KEY_X))));
				shot.sety((Integer.parseInt(c.getString(KEY_Y))));
				shot.setmade((c.getString(KEY_MADE)));

				shots.add(shot);
			}
			return shots;
		}
		catch(Exception ex){
			Log.i("INTEG-EX", "ex - getAllShots");
			return new ArrayList<ShotChartCoords>();
		}


	}

	public List<ShotChartCoords> getAllTeamShots(long t_id) {

		try{
			List<ShotChartCoords> shots = new ArrayList<ShotChartCoords>();
			//String selectPlayerQuery = "SELECT * FROM " + TABLE_PLAYERS + " WHERE " + KEY_T_ID + " = " + t_id;
			String w = " WHERE " + KEY_T_ID + " = " + t_id;
			List<NameValuePair> params = this.startParams();
			params.add(new BasicNameValuePair(TAG_WHERE, w));

			HttpParameter parameter = new HttpParameter(url_get_shot, "POST", params);
			AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
			JSONObject json = result.get();
			JSONArray shotArray = json.getJSONArray(TAG_SHOTS);

			for (int i = 0; i < shotArray.length(); i++) {
				JSONObject c = shotArray.getJSONObject(i);
				ShotChartCoords shot = new ShotChartCoords();

				shot.setshotid(Long.parseLong(c.getString(KEY_SHOT_ID)));
				shot.setgid((Long.parseLong(c.getString(KEY_G_ID))));		    
				shot.setpid((Long.parseLong(c.getString(KEY_P_ID))));
				shot.settid((Long.parseLong(c.getString(KEY_T_ID))));
				shot.setx((Integer.parseInt(c.getString(KEY_X))));
				shot.sety((Integer.parseInt(c.getString(KEY_Y))));
				shot.setmade((c.getString(KEY_MADE)));

				shots.add(shot);
			}
			return shots;
		}
		catch(Exception ex){
			Log.i("INTEG-EX", "ex - getAllTeamShots");
			return new ArrayList<ShotChartCoords>();
		}
	}

	public List<ShotChartCoords> getAllPlayerShots(long p_id) {

		try{
			List<ShotChartCoords> shots = new ArrayList<ShotChartCoords>();
			//String selectPlayerQuery = "SELECT * FROM " + TABLE_PLAYERS + " WHERE " + KEY_T_ID + " = " + t_id;
			String w = "WHERE " + KEY_P_ID + " = " + Long.toString(p_id);
			List<NameValuePair> params = this.startParams();
			params.add(new BasicNameValuePair(TAG_WHERE, w));

			HttpParameter parameter = new HttpParameter(url_get_shot, "POST", params);
			AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
			JSONObject json = result.get();
			JSONArray shotArray = json.getJSONArray(TAG_SHOTS);

			for (int i = 0; i < shotArray.length(); i++) {
				JSONObject c = shotArray.getJSONObject(i);
				ShotChartCoords shot = new ShotChartCoords();

				shot.setshotid(Long.parseLong(c.getString(KEY_SHOT_ID)));
				shot.setgid((Long.parseLong(c.getString(KEY_G_ID))));		    
				shot.setpid((Long.parseLong(c.getString(KEY_P_ID))));
				shot.settid((Long.parseLong(c.getString(KEY_T_ID))));
				shot.setx((Integer.parseInt(c.getString(KEY_X))));
				shot.sety((Integer.parseInt(c.getString(KEY_Y))));
				shot.setmade((c.getString(KEY_MADE)));

				shots.add(shot);
			}
			return shots;
		}
		catch(Exception ex){
			Log.i("INTEG-Exc","ex - getALlPlayerShots" );
			return new ArrayList<ShotChartCoords>();
		}
	}

	public List<ShotChartCoords> getAllTeamShotsGame(long t_id, long g_id) {

		List<ShotChartCoords> shots = new ArrayList<ShotChartCoords>();
		try{
			//String selectPlayerQuery = "SELECT * FROM " + TABLE_PLAYERS + " WHERE " + KEY_T_ID + " = " + t_id;
			String w = " WHERE (" + KEY_T_ID + " = " + Long.toString(t_id) + ") AND (" + KEY_G_ID + " = " + Long.toString(g_id) + ")";
			List<NameValuePair> params = this.startParams();
			params.add(new BasicNameValuePair(TAG_WHERE, w));



			HttpParameter parameter = new HttpParameter(url_get_shot, "POST", params);
			AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
			JSONObject json = result.get();

			JSONArray shotArray = json.getJSONArray(TAG_SHOTS);

			for (int i = 0; i < shotArray.length(); i++) {
				JSONObject c = shotArray.getJSONObject(i);
				ShotChartCoords shot = new ShotChartCoords();

				shot.setshotid(Long.parseLong(c.getString(KEY_SHOT_ID)));
				shot.setgid((Long.parseLong(c.getString(KEY_G_ID))));		    
				shot.setpid((Long.parseLong(c.getString(KEY_P_ID))));
				shot.settid((Long.parseLong(c.getString(KEY_T_ID))));
				shot.setx((Integer.parseInt(c.getString(KEY_X))));
				shot.sety((Integer.parseInt(c.getString(KEY_Y))));
				shot.setmade((c.getString(KEY_MADE)));

				shots.add(shot);
			}
			return shots;
		}
		catch(Exception ex){
			Log.i("INTEG-EX", ex.toString());
			Log.i("INTEG-EX", "ex - getAllTeamShotsGame");
			return shots;
		}


	}

	public List<ShotChartCoords> getAllPlayerShotsGame(long p_id, long g_id) {

		List<ShotChartCoords> shots = new ArrayList<ShotChartCoords>();
		try{
			//String selectPlayerQuery = "SELECT * FROM " + TABLE_PLAYERS + " WHERE " + KEY_T_ID + " = " + t_id;
			String w = " WHERE (" + KEY_P_ID + " = " + Long.toString(p_id) + ") AND (" + KEY_G_ID + " = " + Long.toString(g_id) + ")";
			List<NameValuePair> params = this.startParams();
			params.add(new BasicNameValuePair(TAG_WHERE, w));

			HttpParameter parameter = new HttpParameter(url_get_shot, "POST", params);
			AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
			JSONObject json = result.get();
			JSONArray shotArray = json.getJSONArray(TAG_SHOTS);

			for (int i = 0; i < shotArray.length(); i++) {
				JSONObject c = shotArray.getJSONObject(i);
				ShotChartCoords shot = new ShotChartCoords();

				shot.setshotid(Long.parseLong(c.getString(KEY_SHOT_ID)));
				shot.setgid((Long.parseLong(c.getString(KEY_G_ID))));		    
				shot.setpid((Long.parseLong(c.getString(KEY_P_ID))));
				shot.settid((Long.parseLong(c.getString(KEY_T_ID))));
				shot.setx((Integer.parseInt(c.getString(KEY_X))));
				shot.sety((Integer.parseInt(c.getString(KEY_Y))));
				shot.setmade((c.getString(KEY_MADE)));

				shots.add(shot);
			}
			return shots;
		}
		catch(Exception ex){
			Log.i("INTEG-EX", "ex - getAllTeamShots");
			return shots;
		}
	}

	// Delete a Shot
	public void deleteShot(long shot_id) {

		List<NameValuePair> params = this.startParams();
		params.add(new BasicNameValuePair("table", TABLE_SHOT_CHART_COORDS));
		params.add(new BasicNameValuePair("key_id", KEY_SHOT_ID));
		params.add(new BasicNameValuePair("key_value", Long.toString(shot_id)));

		HttpParameter parameter = new HttpParameter(url_delete, "POST", params);
		AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
	}


	// ----------------------- TEAMS table methods ------------------------- //

	public void createTeams(Teams team, Long t_id){
		Log.i("SCR", "Starting Create Team");

		List<NameValuePair> params = this.startParams();
		params.add(new BasicNameValuePair(KEY_T_ID, Long.toString(t_id)));
		params.add(new BasicNameValuePair(KEY_T_NAME, team.gettname()));
		params.add(new BasicNameValuePair(KEY_ABBV, team.getabbv()));
		params.add(new BasicNameValuePair(KEY_C_NAME, team.getcname()));
		params.add(new BasicNameValuePair(KEY_SPORT, team.getSport()));

		Log.i("SCR", "t id: " + t_id);
		Log.i("SCR", "t name: " + team.gettname());
		Log.i("SCR", "abbv: " + team.getabbv());
		Log.i("SCR", "c name: " + team.getcname());
		Log.i("SCR", "sport: " + team.getSport());


		// sending modified data through http request
		// Notice that update product url accepts POST method
		HttpParameter parameter = new HttpParameter(url_create_teams, "POST", params);
		AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
		try {
			Log.i("SCR", "json response: " + result.get().toString());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	//get single team with id
	public Teams getTeam(long t_id) {

		try{
			String w = " WHERE (" + KEY_T_ID + " = " + Long.toString(t_id) + ")";
			List<NameValuePair> params = this.startParams();
			params.add(new BasicNameValuePair(TAG_WHERE, w));

			HttpParameter parameter = new HttpParameter(url_get_teams, "POST", params);
			AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
			JSONObject json = result.get();
			JSONArray teamArray = json.getJSONArray(TAG_TEAMS);


			JSONObject c = teamArray.getJSONObject(0);
			Teams team = new Teams();

			team.settid(Long.parseLong(c.getString(KEY_T_ID)));
			team.settname(c.getString(KEY_T_NAME));
			team.setabbv(c.getString(KEY_ABBV));
			team.setcname(c.getString(KEY_C_NAME));
			team.setsport(c.getString(KEY_SPORT));


			return team;
		}
		catch(Exception ex){
			Log.i("INTEG-EX", "ex - getTeam by t_id");
			return new Teams();
		}

	}

	//TODO check that quotes are properly escaped/needed
	//get single team with name
	public Teams getTeam(String t_name) {

		try{
		String w = " WHERE (" + KEY_T_NAME + " = \"" + t_name + "\")";
		List<NameValuePair> params = this.startParams();
		params.add(new BasicNameValuePair(TAG_WHERE, w));

		HttpParameter parameter = new HttpParameter(url_get_teams, "POST", params);
		AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
		JSONObject json = result.get();
		JSONArray teamArray = json.getJSONArray(TAG_TEAMS);


		JSONObject c = teamArray.getJSONObject(0);
		Teams team = new Teams();

		team.settid(Long.parseLong(c.getString(KEY_T_ID)));
		team.settname(c.getString(KEY_T_NAME));
		team.setabbv(c.getString(KEY_ABBV));
		team.setcname(c.getString(KEY_C_NAME));
		team.setsport(c.getString(KEY_SPORT));


		return team;
		}
		catch(Exception ex){
			Log.i("INTEG-EX", "ex - getTeam by name");
			return new Teams();
		}

	}

	public List<Teams> getAllTeams() {	

		List<Teams> teams = new ArrayList<Teams>();
		try{
		String w = "";
		List<NameValuePair> params = this.startParams();
		params.add(new BasicNameValuePair(TAG_WHERE, w));

		HttpParameter parameter = new HttpParameter(url_get_teams, "POST", params);
		AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
		JSONObject json = result.get();
		JSONArray teamArray = json.getJSONArray(TAG_TEAMS);

		for (int i = 0; i < teamArray.length(); i++) {

			JSONObject c = teamArray.getJSONObject(i);
			Teams team = new Teams();

			team.settid(Long.parseLong(c.getString(KEY_T_ID)));
			team.settname(c.getString(KEY_T_NAME));
			team.setabbv(c.getString(KEY_ABBV));
			team.setcname(c.getString(KEY_C_NAME));
			team.setsport(c.getString(KEY_SPORT));

			teams.add(team);
		}
		return teams;
		}
		catch(Exception ex){
			Log.i("INTEG-EX", "ex - getAllTeams");
			return teams;
		}
	}

	public void updateTeam(Teams team){

		String w = "where " + KEY_T_ID + " = " + Long.toString(team.gettid());
		List<NameValuePair> params = this.startParams();
		params.add(new BasicNameValuePair(TAG_WHERE, w));
		params.add(new BasicNameValuePair(KEY_T_NAME, team.gettname()));
		params.add(new BasicNameValuePair(KEY_ABBV, team.getabbv()));
		params.add(new BasicNameValuePair(KEY_C_NAME, team.getcname()));
		params.add(new BasicNameValuePair(KEY_SPORT, team.getSport()));

		HttpParameter parameter = new HttpParameter(url_update_teams, "POST", params);
		AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);


	}

	// Delete a Team
	public void deleteTeam(long t_id) {

		deletePlayers(t_id);
		List<NameValuePair> params = this.startParams();
		params.add(new BasicNameValuePair("table", TABLE_TEAMS));
		params.add(new BasicNameValuePair("key_id", KEY_T_ID));
		params.add(new BasicNameValuePair("key_value", Long.toString(t_id)));
		Log.i("INTEG", "Delete Info");
		Log.i("INTEG", "TABLE_TEAMS: " + TABLE_TEAMS);
		Log.i("INTEG", "key id: " + KEY_T_ID);
		Log.i("INTEG", "key value: " + t_id);
		HttpParameter parameter = new HttpParameter(url_delete, "POST", params);
		AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
	}



}