package com.seniordesign.ultimatescorecard.networkdatabase.helper;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import com.seniordesign.ultimatescorecard.sqlite.helper.*;

/*
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
*/

public class NetworkHelper {

	
	// Logcat tag
	protected static final String LOG = "DatabaseHelper";

	//Table Names
	//protected static final String TABLE_GAMES = "games";
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

	//Log In Tags

	protected static final String user = "user";
	protected static final String password = "password";
	protected static final String schema = "schema";

	//URL Information
	protected static final String dburl = "http://tusk.zapto.org/php/";
	protected static final String url_delete = dburl + "delete_entry.php";
	protected static final String url_update = dburl + "update_table.php";
	
	
	protected static final String url_insert_pbp = dburl + "insert_play_by_play.php";
	protected static final String url_get_pbp = dburl + "get_play_by_play.php";
	protected static final String url_get_players = dburl + "get_players.php";
	protected static final String url_update_players = dburl + "update_players.php";
	protected static final String url_create_shot = dburl + "create_shot_chart_coords.php";
	protected static final String url_get_shot = dburl + "get_shot_chart_coords.php";
	protected static final String url_create_teams = dburl + "create_teams.php";
	protected static final String url_get_teams = dburl + "get_teams.php";
	protected static final String url_update_teams = dburl + "update_teams.php";
	
	//private static final String TAG_SUCCESS = "success";

	
	
	//DB Tags
	protected static final String TAG_PBP = "play_by_play";
	protected static final String TAG_PLAYERS = "players";
	protected static final String TAG_SHOTS = "shot_chart_coords";
	protected static final String TAG_TEAMS = "teams";
	
	
	//Tags for Update/Specific Stat Retrieval
	protected static final String TAG_WHERE = "where";
	protected static final String TAG_QUERY = "query";
	protected static final String TAG_TABLE = "table";
	protected static final String TAG_COLUMN = "column";
	protected static final String TAG_VALUE = "value";
	// JSON parser class
	//JSONParser jsonParser = new JSONParser();


	private String _user;
	private String _password;
	private String _schema;


	public NetworkHelper (String dbuser, String dbpass, String dbschema) {  
		_user = dbuser;
		_password = dbpass;
		_schema = dbschema;
	};

	protected List<NameValuePair> startParams() {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(user, _user));
		params.add(new BasicNameValuePair(password, _password));
		params.add(new BasicNameValuePair(schema, _schema));
		return params;
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

	}


	public List<PlayByPlay> getPlayByPlayGame(long g_id) throws JSONException, InterruptedException, ExecutionException{
		//SQLiteDatabase db = this.getReadableDatabase();
		String w = "where g_id = " + Long.toString(g_id);
		List<PlayByPlay> pbps = new ArrayList<PlayByPlay>();
		List<NameValuePair> params = this.startParams();
		params.add(new BasicNameValuePair(TAG_WHERE, w));
		
	
		
		HttpParameter parameter = new HttpParameter(url_get_pbp, "POST", params);
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
			pbp.sethomescore(Integer.getInteger(c.getString(KEY_HOME_SCORE)));
			pbp.setawayscore(Integer.getInteger(c.getString(KEY_AWAY_SCORE)));
			// adding to playbyplay list
			pbps.add(pbp);	
		}
		return pbps;
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

	public List<Players> getPlayersTeam(long t_id) throws JSONException, InterruptedException, ExecutionException{
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

	public List<Players> getPlayersTeam2(long t_id) throws JSONException, InterruptedException, ExecutionException{
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

	public void updatePlayer(Players player){
		
		List<NameValuePair> params = this.startParams();

		//add data from pbp to parameter list
		//TODO Decide how to handle a_id
		params.add(new BasicNameValuePair(KEY_T_ID, Long.toString(player.gettid())));
		params.add(new BasicNameValuePair(KEY_P_NAME, player.getpname()));
		params.add(new BasicNameValuePair(KEY_P_NUM, Integer.toString(player.getpnum())));

		// sending modified data through http request
		// Notice that update product url accepts POST method
		HttpParameter parameter = new HttpParameter(url_update_players, "POST", params);
		AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
		
	}

	public List<Players> getAllPlayers() throws JSONException, InterruptedException, ExecutionException{
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
		params.add(new BasicNameValuePair("table", "PLAYERS"));
		params.add(new BasicNameValuePair("key_id", KEY_T_ID));
		params.add(new BasicNameValuePair("key_value", Long.toString(t_id)));
		
		HttpParameter parameter = new HttpParameter(url_delete, "POST", params);
		AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
	}


	// -------------------SHOT_CHART_COORDS table methods ------------------ //

	//create a row of shot chart coordinates
	public void createShot(ShotChartCoords shot){
		List<NameValuePair> params = this.startParams();
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

	public List<ShotChartCoords> getAllShots() throws NumberFormatException, JSONException, InterruptedException, ExecutionException{
		
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

	public List<ShotChartCoords> getAllTeamShots(long t_id) throws JSONException, InterruptedException, ExecutionException{

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

	public List<ShotChartCoords> getAllPlayerShots(long p_id) throws JSONException, InterruptedException, ExecutionException{

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

	public List<ShotChartCoords> getAllTeamShotsGame(long t_id, long g_id) throws JSONException, InterruptedException, ExecutionException{
		List<ShotChartCoords> shots = new ArrayList<ShotChartCoords>();
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

	public List<ShotChartCoords> getAllPlayerShotsGame(long p_id, long g_id) throws NumberFormatException, JSONException, InterruptedException, ExecutionException{

		List<ShotChartCoords> shots = new ArrayList<ShotChartCoords>();
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
		
		
		List<NameValuePair> params = this.startParams();
		params.add(new BasicNameValuePair(KEY_T_ID, Long.toString(t_id)));
		params.add(new BasicNameValuePair(KEY_T_NAME, team.gettname()));
		params.add(new BasicNameValuePair(KEY_ABBV, team.getabbv()));
		params.add(new BasicNameValuePair(KEY_C_NAME, team.getcname()));
		params.add(new BasicNameValuePair(KEY_SPORT, team.getSport()));



		// sending modified data through http request
		// Notice that update product url accepts POST method
		HttpParameter parameter = new HttpParameter(url_create_teams, "POST", params);
		AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
	}

	//get single team with id
	public Teams getTeam(long t_id) throws JSONException, InterruptedException, ExecutionException {

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

	//TODO check that quotes are properly escaped/needed
	//get single team with name
	public Teams getTeam(String t_name) throws JSONException, InterruptedException, ExecutionException {
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

	public List<Teams> getAllTeams() throws JSONException, InterruptedException, ExecutionException{	
		List<Teams> teams = new ArrayList<Teams>();
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
		
		HttpParameter parameter = new HttpParameter(url_delete, "POST", params);
		AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
	}


}
