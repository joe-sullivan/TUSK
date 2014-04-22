package com.seniordesign.ultimatescorecard.networkdatabase.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.seniordesign.ultimatescorecard.data.StatData;
import com.seniordesign.ultimatescorecard.data.basketball.BasketballPlayer;
import com.seniordesign.ultimatescorecard.sqlite.basketball.BasketballGameStats;
import com.seniordesign.ultimatescorecard.sqlite.basketball.BasketballGames;
import com.seniordesign.ultimatescorecard.sqlite.helper.Games;
import com.seniordesign.ultimatescorecard.sqlite.helper.PlayByPlay;
import com.seniordesign.ultimatescorecard.sqlite.helper.Players;
import com.seniordesign.ultimatescorecard.sqlite.helper.ShotChartCoords;
import com.seniordesign.ultimatescorecard.sqlite.helper.Teams;

public class BasketballNetworkHelper extends NetworkHelper {
	private String _user;
	private String _password;
	private String _schema;

	// Logcat tag
	private static final String LOG = "BasketballDatabaseHelper";

	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "BasketballStats";

	//Table Names
	private static final String TABLE_BASKETBALL_GAME_STATS = "BASKETBALL_GAME_STATS";
	private static final String TABLE_GAMES = "BASKETBALL_GAMES";
	private static final String TABLE_GAME_STATS = "BASKETBALL_GAME_STATS";

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

	//Log In Tags

	protected static final String user = "user";
	protected static final String password = "password";
	protected static final String schema = "schema";


	//Table Tags
	protected static final String TAG_STAT = "stat";
	protected static final String TAG_STAT_VALUE = "value";
	protected static final String TAG_BASKETBALL_GAMES = "basketball_games";
	protected static final String TAG_BASKETBALL_GAME_STATS = "basketball_game_stats";

	//URL Information
	protected static final String dburl = "http://tusk.zapto.org/php/";
	protected static final String url_delete = dburl + "delete_entry.php";
	protected static final String url_get_stat = dburl + "get_stat.php";

	protected static final String url_insert_game = dburl + "insert_basketball_game.php";
	protected static final String url_get_game = dburl + "get_basketball_games.php";
	protected static final String url_insert_game_stat = dburl + "insert_basketball_game_stats.php";
	protected static final String url_get_game_stat = dburl + "get_basketball_game_stats.php";


	//URL Information
		protected static final String url_update = dburl + "update_table.php";


		protected static final String url_insert_pbp = dburl + "basketball_insert_play_by_play.php";
		protected static final String url_get_pbp = dburl + "basketball_get_play_by_play.php";
		protected static final String url_get_players = dburl + "basketball_get_players.php";
		protected static final String url_update_players = dburl + "basketball_update_player.php";
		protected static final String url_create_shot = dburl + "basketball_insert_shot_chart_coords.php";
		protected static final String url_get_shot = dburl + "basketball_get_shot_chart_coords.php";
		protected static final String url_create_teams = dburl + "basketball_insert_team.php";
		protected static final String url_get_teams = dburl + "basketball_get_teams.php";
		protected static final String url_update_teams = dburl + "basketball_update_team.php";
		protected static final String url_insert_player = dburl + "basketball_insert_player.php";



	//Table Info

		protected static final String TABLE_PLAYERS = "BASKETBALL_PLAYERS";
		protected static final String TABLE_TEAMS = "BASKETBALL_TEAMS";
		protected static final String TABLE_PLAY_BY_PLAY = "BASKETBALL_PLAY_BY_PLAY";
		protected static final String TABLE_SHOT_CHART_COORDS = "BASKETBALL_SHOT_CHART_COORDS";




	public BasketballNetworkHelper(String dbuser, String dbpass, String dbschema) {
		super(dbuser, dbpass, dbschema);
		_user = dbuser;
		_password = dbpass;
		_schema = dbschema;
	}

	// ----------------------- GAMES table methods ------------------------- //

	public void createGame(Games game, long g_id) {

		Log.i("INTEG", "bas: create game");
		List<NameValuePair> params = this.startParams();


		params.add(new BasicNameValuePair(KEY_G_ID, Long.toString(g_id)));
		params.add(new BasicNameValuePair(KEY_HOME_ID, Long.toString(game.gethomeid())));
		params.add(new BasicNameValuePair(KEY_AWAY_ID, Long.toString(game.getawayid())));
		params.add(new BasicNameValuePair(KEY_DATE, game.getDate()));

		// Change in PHP to automatically add the 0's
		/*
	        params.add(new BasicNameValuePair(KEY_HOME_PTS, "0"));
	        params.add(new BasicNameValuePair(KEY_HOME_FGM, "0"));
	        params.add(new BasicNameValuePair(KEY_HOME_FGA, "0"));
	        params.add(new BasicNameValuePair(KEY_HOME_FGM3, "0"));
	        params.add(new BasicNameValuePair(KEY_HOME_FGA3, "0"));
	        params.add(new BasicNameValuePair(KEY_HOME_FTM, "0"));
	        params.add(new BasicNameValuePair(KEY_HOME_FTA, "0"));
	        params.add(new BasicNameValuePair(KEY_HOME_OREB, "0"));
	        params.add(new BasicNameValuePair(KEY_HOME_DREB, "0"));
	        params.add(new BasicNameValuePair(KEY_HOME_AST, "0"));
	        params.add(new BasicNameValuePair(KEY_HOME_STL, "0"));
	        params.add(new BasicNameValuePair(KEY_HOME_BLK, "0"));
	        params.add(new BasicNameValuePair(KEY_HOME_TO, "0"));
	        params.add(new BasicNameValuePair(KEY_HOME_PF, "0"));
	        params.add(new BasicNameValuePair(KEY_HOME_TECH, "0"));
	        params.add(new BasicNameValuePair(KEY_HOME_FLAGRANT, "0"));

	        params.add(new BasicNameValuePair(KEY_AWAY_PTS, "0"));
	        params.add(new BasicNameValuePair(KEY_AWAY_FGM, "0"));
	        params.add(new BasicNameValuePair(KEY_AWAY_FGA, "0"));
	        params.add(new BasicNameValuePair(KEY_AWAY_FGM3, "0"));
	        params.add(new BasicNameValuePair(KEY_AWAY_FGA3, "0"));
	        params.add(new BasicNameValuePair(KEY_AWAY_FTM, "0"));
	        params.add(new BasicNameValuePair(KEY_AWAY_FTA, "0"));
	        params.add(new BasicNameValuePair(KEY_AWAY_OREB, "0"));
	        params.add(new BasicNameValuePair(KEY_AWAY_DREB, "0"));
	        params.add(new BasicNameValuePair(KEY_AWAY_AST, "0"));
	        params.add(new BasicNameValuePair(KEY_AWAY_STL, "0"));
	        params.add(new BasicNameValuePair(KEY_AWAY_BLK, "0"));
	        params.add(new BasicNameValuePair(KEY_AWAY_TO, "0"));
	        params.add(new BasicNameValuePair(KEY_AWAY_PF, "0"));
	        params.add(new BasicNameValuePair(KEY_AWAY_TECH, "0"));
	        params.add(new BasicNameValuePair(KEY_AWAY_FLAGRANT, "0"));
		 */

		HttpParameter parameter = new HttpParameter(url_insert_game,"POST", params);
		AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);

		List<Players> home_players = getPlayersTeam2(game.gethomeid());
		List<Players> away_players = getPlayersTeam2(game.getawayid());

		Log.i("INTEG", "Length of player list: " + home_players.size() );
		for(Players player : home_players){
			createGameStats(player.getpid(), g_id);
		}
		for(Players player : away_players){
			createGameStats(player.getpid(), g_id);
		}

	}

	//get single game
	public Games getGame(long g_id) {
		Log.i("INTEG", "bas: get game");
		try{
			String w = "where g_id = " + g_id;
			List<NameValuePair> params = this.startParams();
			params.add(new BasicNameValuePair(TAG_WHERE, w));



			HttpParameter parameter = new HttpParameter(url_get_game, "POST", params);
			AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
			JSONObject json = result.get();
			JSONArray plays = json.getJSONArray(TAG_BASKETBALL_GAMES);


			JSONObject c = plays.getJSONObject(0);
			BasketballGames game = new BasketballGames();

			Log.i("INTEG-EX", "works up to getting json");
			game.setgid(g_id);
			game.sethomeid((Long.parseLong(c.getString(KEY_HOME_ID))));
			game.setawayid((Long.parseLong(c.getString(KEY_AWAY_ID))));
			game.setDate(c.getString(KEY_DATE));

			game.sethomepts((Integer.parseInt(c.getString(KEY_HOME_PTS))));
			game.sethomefgm((Integer.parseInt(c.getString(KEY_HOME_FGM))));
			game.sethomefga(Integer.parseInt(c.getString(KEY_HOME_FGA)));
			game.sethomefgm3(Integer.parseInt(c.getString(KEY_HOME_FGM3)));
			game.sethomefga3(Integer.parseInt(c.getString(KEY_HOME_FGA3)));
			game.sethomeftm(Integer.parseInt(c.getString(KEY_HOME_FTM)));
			game.sethomefta(Integer.parseInt(c.getString(KEY_HOME_FTA)));
			game.sethomeoreb(Integer.parseInt(c.getString(KEY_HOME_OREB)));
			game.sethomedreb(Integer.parseInt(c.getString(KEY_HOME_DREB)));
			game.sethomeast(Integer.parseInt(c.getString(KEY_HOME_AST)));
			game.sethomestl(Integer.parseInt(c.getString(KEY_HOME_STL)));
			game.sethomeblk(Integer.parseInt(c.getString(KEY_HOME_BLK)));
			game.sethometo(Integer.parseInt(c.getString(KEY_HOME_TO)));
			game.sethomepf(Integer.parseInt(c.getString(KEY_HOME_PF)));
			game.sethometech(Integer.parseInt(c.getString(KEY_HOME_TECH)));
			game.sethomeflagrant(Integer.parseInt(c.getString(KEY_HOME_FLAGRANT)));

			game.setawaypts((Integer.parseInt(c.getString(KEY_AWAY_PTS))));
			game.setawayfgm((Integer.parseInt(c.getString(KEY_AWAY_FGM))));
			game.setawayfga(Integer.parseInt(c.getString(KEY_AWAY_FGA)));
			game.setawayfgm3(Integer.parseInt(c.getString(KEY_AWAY_FGM3)));
			game.setawayfga3(Integer.parseInt(c.getString(KEY_AWAY_FGA3)));
			game.setawayftm(Integer.parseInt(c.getString(KEY_AWAY_FTM)));
			game.setawayfta(Integer.parseInt(c.getString(KEY_AWAY_FTA)));
			game.setawayoreb(Integer.parseInt(c.getString(KEY_AWAY_OREB)));
			game.setawaydreb(Integer.parseInt(c.getString(KEY_AWAY_DREB)));
			game.setawayast(Integer.parseInt(c.getString(KEY_AWAY_AST)));
			game.setawaystl(Integer.parseInt(c.getString(KEY_AWAY_STL)));
			game.setawayblk(Integer.parseInt(c.getString(KEY_AWAY_BLK)));
			game.setawayto(Integer.parseInt(c.getString(KEY_AWAY_TO)));
			game.setawaypf(Integer.parseInt(c.getString(KEY_AWAY_PF)));
			game.setawaytech(Integer.parseInt(c.getString(KEY_AWAY_TECH)));
			game.setawayflagrant(Integer.parseInt(c.getString(KEY_AWAY_FLAGRANT)));
			Log.i("INTEG-EX", "after sets");
			return game;
		}
		catch(Exception ex){
			Log.i("INTEG-EX", ex.toString());
			Log.i("INTEG-EX", "ex - bas getGame");
			return new BasketballGames();
		}
	}

	//Get all Games
	public List<Games> getAllGames()  {
		Log.i("INTEG", "bas: get all games");
		List<Games> games  = new ArrayList<Games>();
		try{
			String w = "";

			List<NameValuePair> params = this.startParams();
			params.add(new BasicNameValuePair(TAG_WHERE, w));



			HttpParameter parameter = new HttpParameter(url_get_game, "POST", params);
			AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
			JSONObject json = result.get();
			JSONArray plays = json.getJSONArray(TAG_BASKETBALL_GAMES);

			for (int i = 0; i < plays.length(); i++) {
				JSONObject c = plays.getJSONObject(i);
				BasketballGames game = new BasketballGames();


				game.setgid(Long.parseLong(c.getString(KEY_G_ID)));
				game.sethomeid((Long.parseLong(c.getString(KEY_HOME_ID))));
				game.setawayid((Long.parseLong(c.getString(KEY_AWAY_ID))));
				game.setDate(c.getString(KEY_DATE));

				game.sethomepts((Integer.parseInt(c.getString(KEY_HOME_PTS))));
				game.sethomefgm((Integer.parseInt(c.getString(KEY_HOME_FGM))));
				game.sethomefga(Integer.parseInt(c.getString(KEY_HOME_FGA)));
				game.sethomefgm3(Integer.parseInt(c.getString(KEY_HOME_FGM3)));
				game.sethomefga3(Integer.parseInt(c.getString(KEY_HOME_FGA3)));
				game.sethomeftm(Integer.parseInt(c.getString(KEY_HOME_FTM)));
				game.sethomefta(Integer.parseInt(c.getString(KEY_HOME_FTA)));
				game.sethomeoreb(Integer.parseInt(c.getString(KEY_HOME_OREB)));
				game.sethomedreb(Integer.parseInt(c.getString(KEY_HOME_DREB)));
				game.sethomeast(Integer.parseInt(c.getString(KEY_HOME_AST)));
				game.sethomestl(Integer.parseInt(c.getString(KEY_HOME_STL)));
				game.sethomeblk(Integer.parseInt(c.getString(KEY_HOME_BLK)));
				game.sethometo(Integer.parseInt(c.getString(KEY_HOME_TO)));
				game.sethomepf(Integer.parseInt(c.getString(KEY_HOME_PF)));
				game.sethometech(Integer.parseInt(c.getString(KEY_HOME_TECH)));
				game.sethomeflagrant(Integer.parseInt(c.getString(KEY_HOME_FLAGRANT)));

				game.setawaypts((Integer.parseInt(c.getString(KEY_AWAY_PTS))));
				game.setawayfgm((Integer.parseInt(c.getString(KEY_AWAY_FGM))));
				game.setawayfga(Integer.parseInt(c.getString(KEY_AWAY_FGA)));
				game.setawayfgm3(Integer.parseInt(c.getString(KEY_AWAY_FGM3)));
				game.setawayfga3(Integer.parseInt(c.getString(KEY_AWAY_FGA3)));
				game.setawayftm(Integer.parseInt(c.getString(KEY_AWAY_FTM)));
				game.setawayfta(Integer.parseInt(c.getString(KEY_AWAY_FTA)));
				game.setawayoreb(Integer.parseInt(c.getString(KEY_AWAY_OREB)));
				game.setawaydreb(Integer.parseInt(c.getString(KEY_AWAY_DREB)));
				game.setawayast(Integer.parseInt(c.getString(KEY_AWAY_AST)));
				game.setawaystl(Integer.parseInt(c.getString(KEY_AWAY_STL)));
				game.setawayblk(Integer.parseInt(c.getString(KEY_AWAY_BLK)));
				game.setawayto(Integer.parseInt(c.getString(KEY_AWAY_TO)));
				game.setawaypf(Integer.parseInt(c.getString(KEY_AWAY_PF)));
				game.setawaytech(Integer.parseInt(c.getString(KEY_AWAY_TECH)));
			}
			return games;
		}catch(Exception ex){
			Log.i("INTEG-EX", "ex - bas getallGames");
			return games;
		}
	}

	//Get all Games played by a team
	public List<Games> getAllGamesTeam(long t_id) {
		Log.i("INTEG", "bas: get all games team");
		List<Games> games  = new ArrayList<Games>();
		try{
			String w = "WHERE " + KEY_HOME_ID + " = " + Long.toString(t_id) + " OR " + KEY_AWAY_ID + " = " + Long.toString(t_id);
			List<NameValuePair> params = this.startParams();
			params.add(new BasicNameValuePair(TAG_WHERE, w));



			HttpParameter parameter = new HttpParameter(url_get_game, "POST", params);
			AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
			JSONObject json = result.get();
			JSONArray plays = json.getJSONArray(TAG_BASKETBALL_GAMES);

			for (int i = 0; i < plays.length(); i++) {
				JSONObject c = plays.getJSONObject(i);
				BasketballGames game = new BasketballGames();


				game.setgid(Long.parseLong(c.getString(KEY_G_ID)));
				game.sethomeid((Long.parseLong(c.getString(KEY_HOME_ID))));
				game.setawayid((Long.parseLong(c.getString(KEY_AWAY_ID))));
				game.setDate(c.getString(KEY_DATE));

				game.sethomepts((Integer.parseInt(c.getString(KEY_HOME_PTS))));
				game.sethomefgm((Integer.parseInt(c.getString(KEY_HOME_FGM))));
				game.sethomefga(Integer.parseInt(c.getString(KEY_HOME_FGA)));
				game.sethomefgm3(Integer.parseInt(c.getString(KEY_HOME_FGM3)));
				game.sethomefga3(Integer.parseInt(c.getString(KEY_HOME_FGA3)));
				game.sethomeftm(Integer.parseInt(c.getString(KEY_HOME_FTM)));
				game.sethomefta(Integer.parseInt(c.getString(KEY_HOME_FTA)));
				game.sethomeoreb(Integer.parseInt(c.getString(KEY_HOME_OREB)));
				game.sethomedreb(Integer.parseInt(c.getString(KEY_HOME_DREB)));
				game.sethomeast(Integer.parseInt(c.getString(KEY_HOME_AST)));
				game.sethomestl(Integer.parseInt(c.getString(KEY_HOME_STL)));
				game.sethomeblk(Integer.parseInt(c.getString(KEY_HOME_BLK)));
				game.sethometo(Integer.parseInt(c.getString(KEY_HOME_TO)));
				game.sethomepf(Integer.parseInt(c.getString(KEY_HOME_PF)));
				game.sethometech(Integer.parseInt(c.getString(KEY_HOME_TECH)));
				game.sethomeflagrant(Integer.parseInt(c.getString(KEY_HOME_FLAGRANT)));

				game.setawaypts((Integer.parseInt(c.getString(KEY_AWAY_PTS))));
				game.setawayfgm((Integer.parseInt(c.getString(KEY_AWAY_FGM))));
				game.setawayfga(Integer.parseInt(c.getString(KEY_AWAY_FGA)));
				game.setawayfgm3(Integer.parseInt(c.getString(KEY_AWAY_FGM3)));
				game.setawayfga3(Integer.parseInt(c.getString(KEY_AWAY_FGA3)));
				game.setawayftm(Integer.parseInt(c.getString(KEY_AWAY_FTM)));
				game.setawayfta(Integer.parseInt(c.getString(KEY_AWAY_FTA)));
				game.setawayoreb(Integer.parseInt(c.getString(KEY_AWAY_OREB)));
				game.setawaydreb(Integer.parseInt(c.getString(KEY_AWAY_DREB)));
				game.setawayast(Integer.parseInt(c.getString(KEY_AWAY_AST)));
				game.setawaystl(Integer.parseInt(c.getString(KEY_AWAY_STL)));
				game.setawayblk(Integer.parseInt(c.getString(KEY_AWAY_BLK)));
				game.setawayto(Integer.parseInt(c.getString(KEY_AWAY_TO)));
				game.setawaypf(Integer.parseInt(c.getString(KEY_AWAY_PF)));
				game.setawaytech(Integer.parseInt(c.getString(KEY_AWAY_TECH)));
				game.setawayflagrant(Integer.parseInt(c.getString(KEY_AWAY_FLAGRANT)));
				games.add(game);
			}
			return games;
		}catch(Exception ex){
			Log.i("INTEG-EX", "ex - bas getallGamesTeam");
			return games;
		}
	}

	//get single game stat for team
	public int getTeamGameStat(long g_id, String stat) {
		Log.i("INTEG", "bas: get team game stat");
		Log.i("INTEG", stat);
		try{
			//create query to select game
			String query = "SELECT " + stat + " FROM " + _schema + "." + TABLE_GAMES + 
					" WHERE " + KEY_G_ID + " = " + g_id; 
			List<NameValuePair> params = this.startParams();
			params.add(new BasicNameValuePair(TAG_QUERY, query));
			params.add(new BasicNameValuePair(TAG_STAT, stat));

			Log.i("INTEG-EX", "query: " + query);
			HttpParameter parameter = new HttpParameter(url_get_stat, "POST", params);
			AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
			JSONObject json = result.get();
			Log.i("INTEG-EX", "json response: " + json.toString());
			JSONArray stats = json.getJSONArray(TAG_STAT);
			JSONObject c = stats.getJSONObject(0);
			return Integer.parseInt(c.getString(TAG_STAT_VALUE));
		}catch(Exception ex){
			Log.i("INTEG-EX", ex.toString());
			Log.i("INTEG-EX", "ex - bas getTeamGameStat");
			return 0;
		}
	}

	//Adding value to points category of a player
	public int addTeamStats(ArrayList<StatData> statlist) {
		Log.i("INTEG", "bas: add team stats");
		for(StatData statdata: statlist){

			long g_id = statdata.getgid();
			String stat = statdata.getstat();
			int value = statdata.getvalue();
			 Log.i("INTEG","network stat test: " + stat);
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
		Log.i("INTEG", "bas: delete game");
		List<NameValuePair> params = this.startParams();
		params.add(new BasicNameValuePair("table", TABLE_GAMES));
		params.add(new BasicNameValuePair("key_id", KEY_G_ID));
		params.add(new BasicNameValuePair("key_value", Long.toString(g_id)));

		HttpParameter parameter = new HttpParameter(url_delete, "POST", params);
		AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
	}


	// ----------------------- BASKETBALL_GAME_STATS ---------------------------- //

	public void createGameStats(long p_id, long g_id){
		Log.i("INTEG", "bas: create game stats");
		List<NameValuePair> params = this.startParams(); 
		params.add(new BasicNameValuePair(KEY_G_ID, Long.toString(g_id)));
		params.add(new BasicNameValuePair(KEY_P_ID, Long.toString(p_id)));

		//Have basic 0 values added in PHP file
		/*
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
		 */

		//insert more stats here

		// insert row
		HttpParameter parameter = new HttpParameter(url_insert_game_stat,"POST", params);
		AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
	}

	//get single game stats for single player
	public BasketballGameStats getPlayerGameStats(long g_id, long p_id) {
		Log.i("INTEG", "bas: get player game stats");
		//create query to select game
		try{
			String w = " WHERE " + KEY_G_ID + " = " + g_id + 
					" AND " + KEY_P_ID + " = " + p_id;

			List<NameValuePair> params = this.startParams();
			params.add(new BasicNameValuePair(TAG_WHERE, w));

			HttpParameter parameter = new HttpParameter(url_get_game_stat, "POST", params);
			AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
			JSONObject json = result.get();
			Log.i("INTEG","get player game stats debug");
			Log.i("INTEG","where: " + w);
			Log.i("INTEG","json response: " + json.toString());
			JSONArray stat = json.getJSONArray(TAG_BASKETBALL_GAME_STATS);


			JSONObject c = stat.getJSONObject(0);


			//create the instance of Games using cursor information
			BasketballGameStats stats = new BasketballGameStats();
			stats.setgid(Long.parseLong(c.getString(KEY_G_ID)));
			stats.setpid(Long.parseLong(c.getString(KEY_P_ID)));
			stats.setpts((Integer.parseInt(c.getString(KEY_PTS))));
			stats.setfgm((Integer.parseInt(c.getString(KEY_FGM))));
			stats.setfga(Integer.parseInt(c.getString(KEY_FGA)));
			stats.setfgm3(Integer.parseInt(c.getString(KEY_FGM3)));
			stats.setfga3(Integer.parseInt(c.getString(KEY_FGA3)));
			stats.setftm(Integer.parseInt(c.getString(KEY_FTM)));
			stats.setfta(Integer.parseInt(c.getString(KEY_FTA)));
			stats.setoreb(Integer.parseInt(c.getString(KEY_OREB)));
			stats.setdreb(Integer.parseInt(c.getString(KEY_DREB)));
			stats.setast(Integer.parseInt(c.getString(KEY_AST)));
			stats.setstl(Integer.parseInt(c.getString(KEY_STL)));
			stats.setblk(Integer.parseInt(c.getString(KEY_BLK)));
			stats.setto(Integer.parseInt(c.getString(KEY_TO)));
			stats.setpf(Integer.parseInt(c.getString(KEY_PF)));
			stats.settech(Integer.parseInt(c.getString(KEY_TECH)));
			stats.setflagrant(Integer.parseInt(c.getString(KEY_FLAGRANT)));

			//Insert more stats here

			return stats;
		}catch(Exception ex){
			Log.i("INTEG-EX", ex.toString());
			Log.i("INTEG-EX", "ex - bas getPlayerGameStats");
			return new BasketballGameStats();
		}
	}

	//get single game stat for single player
	public int getPlayerGameStat(long g_id, long p_id, String stat) {
		Log.i("INTEG", "bas: get player game stat");
		try{
			//create query to select game
			String query = "SELECT " + stat + " FROM " + _schema + "." + TABLE_BASKETBALL_GAME_STATS + 
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
		}catch(Exception ex){
			Log.i("INTEG-EX", "ex - bas getPlayerGameStat");
			return 0;
		}

	}

	//Get all GameStats for player
	public List<BasketballGameStats> getPlayerAllGameStats(long p_id) {
		Log.i("INTEG", "bas: get player all game stats");
		List<BasketballGameStats> gameStats = new ArrayList<BasketballGameStats>();
		try{
			String w = " WHERE " + KEY_P_ID + " = " + p_id ;

			List<NameValuePair> params = this.startParams();
			params.add(new BasicNameValuePair(TAG_WHERE, w));

			HttpParameter parameter = new HttpParameter(url_get_game_stat, "POST", params);
			AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
			JSONObject json = result.get();
			JSONArray stat = json.getJSONArray(TAG_BASKETBALL_GAME_STATS);

			for (int i = 0; i < stat.length(); i++) {
				JSONObject c = stat.getJSONObject(i);

				//create the instance of Games using cursor information
				BasketballGameStats stats = new BasketballGameStats();
				stats.setgid(Long.parseLong(c.getString(KEY_G_ID)));
				stats.setpid(Long.parseLong(c.getString(KEY_P_ID)));
				stats.setpts((Integer.parseInt(c.getString(KEY_PTS))));
				stats.setfgm((Integer.parseInt(c.getString(KEY_FGM))));
				stats.setfga(Integer.parseInt(c.getString(KEY_FGA)));
				stats.setfgm3(Integer.parseInt(c.getString(KEY_FGM3)));
				stats.setfga3(Integer.parseInt(c.getString(KEY_FGA3)));
				stats.setftm(Integer.parseInt(c.getString(KEY_FTM)));
				stats.setfta(Integer.parseInt(c.getString(KEY_FTA)));
				stats.setoreb(Integer.parseInt(c.getString(KEY_OREB)));
				stats.setdreb(Integer.parseInt(c.getString(KEY_DREB)));
				stats.setast(Integer.parseInt(c.getString(KEY_AST)));
				stats.setstl(Integer.parseInt(c.getString(KEY_STL)));
				stats.setblk(Integer.parseInt(c.getString(KEY_BLK)));
				stats.setto(Integer.parseInt(c.getString(KEY_TO)));
				stats.setpf(Integer.parseInt(c.getString(KEY_PF)));
				stats.settech(Integer.parseInt(c.getString(KEY_TECH)));
				stats.setflagrant(Integer.parseInt(c.getString(KEY_FLAGRANT)));
				gameStats.add(stats);
			}
			//Insert more stats here

			return gameStats;
		}catch(Exception ex){
			Log.i("INTEG-EX", "ex - bas getPlayerAllGameStats");
			return gameStats;
		}
	}

	//Get all GameStats
	public List<BasketballGameStats> getAllGameStats() {
		Log.i("INTEG", "bas: get all game stats");
		List<BasketballGameStats> gameStats = new ArrayList<BasketballGameStats>();
		try{
			String w = "";

			List<NameValuePair> params = this.startParams();
			params.add(new BasicNameValuePair(TAG_WHERE, w));

			HttpParameter parameter = new HttpParameter(url_get_game_stat, "POST", params);
			AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
			JSONObject json = result.get();
			JSONArray stat = json.getJSONArray(TAG_BASKETBALL_GAME_STATS);

			for (int i = 0; i < stat.length(); i++) {
				JSONObject c = stat.getJSONObject(i);

				//create the instance of Games using cursor information
				BasketballGameStats stats = new BasketballGameStats();
				stats.setgid(Long.parseLong(c.getString(KEY_G_ID)));
				stats.setpid(Long.parseLong(c.getString(KEY_P_ID)));
				stats.setpts((Integer.parseInt(c.getString(KEY_PTS))));
				stats.setfgm((Integer.parseInt(c.getString(KEY_FGM))));
				stats.setfga(Integer.parseInt(c.getString(KEY_FGA)));
				stats.setfgm3(Integer.parseInt(c.getString(KEY_FGM3)));
				stats.setfga3(Integer.parseInt(c.getString(KEY_FGA3)));
				stats.setftm(Integer.parseInt(c.getString(KEY_FTM)));
				stats.setfta(Integer.parseInt(c.getString(KEY_FTA)));
				stats.setoreb(Integer.parseInt(c.getString(KEY_OREB)));
				stats.setdreb(Integer.parseInt(c.getString(KEY_DREB)));
				stats.setast(Integer.parseInt(c.getString(KEY_AST)));
				stats.setstl(Integer.parseInt(c.getString(KEY_STL)));
				stats.setblk(Integer.parseInt(c.getString(KEY_BLK)));
				stats.setto(Integer.parseInt(c.getString(KEY_TO)));
				stats.setpf(Integer.parseInt(c.getString(KEY_PF)));
				stats.settech(Integer.parseInt(c.getString(KEY_TECH)));
				stats.setflagrant(Integer.parseInt(c.getString(KEY_FLAGRANT)));
				gameStats.add(stats);
			}
			//Insert more stats here

			return gameStats;
		}catch(Exception ex){
			Log.i("INTEG-EX", "ex - bas getAllGameStats");
			return gameStats;
		}
	}

	// Delete a GameStats
	public void deleteGameStats(long g_id) {
		Log.i("INTEG", "bas: delete game stats");
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

		Log.i("INTEG", "bas: add stats");
		for(StatData statdata: statlist){

			long g_id = statdata.getgid();
			long p_id = statdata.getpid();
			String stat = statdata.getstat();
			int value = statdata.getvalue();


			int old_value = getPlayerGameStat(g_id, p_id, stat);
			int new_value = old_value + value;

			String w = "WHERE " + KEY_P_ID + " = " + p_id + " AND " + KEY_G_ID + " = " + g_id;
			//Log.i("INTEG", "bas: creating parameters");
			List<NameValuePair> params = this.startParams();
			params.add(new BasicNameValuePair(TAG_TABLE, TABLE_GAME_STATS));
			params.add(new BasicNameValuePair(TAG_COLUMN, stat));
			params.add(new BasicNameValuePair(TAG_VALUE, Integer.toString(new_value)));
			params.add(new BasicNameValuePair(TAG_WHERE, w));

			HttpParameter parameter = new HttpParameter(url_update, "POST", params);
			AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
			//Log.i("INTEG", "should be complete");
		}
		return 1;
	}


	// ----------------------- PLAYERS table methods ------------------------- //

	public void createPlayers(BasketballPlayer player, Long p_id){
		Log.i("INTEG", "bas: create players");

		List<NameValuePair> params = this.startParams();			
		Log.i("INTEG-EX", "starting basketball network create player");
		params.add(new BasicNameValuePair(KEY_P_ID, Long.toString(p_id)));
		params.add(new BasicNameValuePair(KEY_T_ID, Long.toString(player.gettid())));
		params.add(new BasicNameValuePair(KEY_P_NAME, player.getpname()));
		params.add(new BasicNameValuePair(KEY_P_NUM, Integer.toString(player.getpnum())));


		HttpParameter parameter = new HttpParameter(url_insert_player,"POST", params);
		AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
	}

	//get single player
	public BasketballPlayer getPlayer(long p_id) {
		Log.i("INTEG", "bas: get player");
		try{
			String w = " WHERE " + KEY_P_ID + " = " + p_id;;
			List<NameValuePair> params = this.startParams();
			params.add(new BasicNameValuePair(TAG_WHERE, w));

			HttpParameter parameter = new HttpParameter(url_get_players, "POST", params);
			AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
			JSONObject json = result.get();
			JSONArray playerArray = json.getJSONArray(TAG_PLAYERS);


			JSONObject c = playerArray.getJSONObject(0);
			BasketballPlayer player = new BasketballPlayer();
			player.setpid(Long.parseLong(c.getString(KEY_P_ID)));
			player.settid(Long.parseLong(c.getString(KEY_T_ID)));
			player.setpname((c.getString(KEY_P_NAME)));
			player.setpnum((Integer.parseInt(c.getString(KEY_P_NUM))));

			return player;
		}catch(Exception ex){
			return new BasketballPlayer();
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
		params.add(new BasicNameValuePair("table", "BASKETBALL_PLAYERS"));
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
		Log.i("BASKET", "Starting Create Team Basketball");

		List<NameValuePair> params = this.startParams();
		params.add(new BasicNameValuePair(KEY_T_ID, Long.toString(t_id)));
		params.add(new BasicNameValuePair(KEY_T_NAME, team.gettname()));
		params.add(new BasicNameValuePair(KEY_ABBV, team.getabbv()));
		params.add(new BasicNameValuePair(KEY_C_NAME, team.getcname()));
		params.add(new BasicNameValuePair(KEY_SPORT, team.getSport()));

		Log.i("BASKET", "t id: " + t_id);
		Log.i("BASKET", "t name: " + team.gettname());
		Log.i("BASKET", "abbv: " + team.getabbv());
		Log.i("BASKET", "c name: " + team.getcname());
		Log.i("BASKET", "sport: " + team.getSport());


		// sending modified data through http request
		// Notice that update product url accepts POST method
		HttpParameter parameter = new HttpParameter(url_create_teams, "POST", params);
		AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
		try {
			Log.i("BASKET", "json response: " + result.get().toString());
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