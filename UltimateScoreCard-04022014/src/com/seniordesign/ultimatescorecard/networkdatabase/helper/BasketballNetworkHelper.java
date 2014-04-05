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

import com.seniordesign.ultimatescorecard.data.basketball.BasketballPlayer;
import com.seniordesign.ultimatescorecard.sqlite.basketball.BasketballGameStats;
import com.seniordesign.ultimatescorecard.sqlite.basketball.BasketballGames;
import com.seniordesign.ultimatescorecard.sqlite.helper.Games;
import com.seniordesign.ultimatescorecard.sqlite.helper.PlayByPlay;
import com.seniordesign.ultimatescorecard.sqlite.helper.Players;

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
    private static final String TABLE_BASKETBALL_GAME_STATS = "basketball_game_stats";
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
	protected static final String TAG_STAT_VALUE = "stat_value";
	protected static final String TAG_BASKETBALL_GAMES = "basketball_games";
	protected static final String TAG_BASKETBALL_GAME_STATS = "basketball_games_stats";

	//URL Information
	protected static final String dburl = "http://tusk.zapto.org/php/";
	protected static final String url_delete = dburl + "delete_entry.php";
	protected static final String url_get_stat = dburl + "get_stat.php";
	
	protected static final String url_insert_game = dburl + "insert_basketball_game.php";
	protected static final String url_get_game = dburl + "get_basketball_game.php";
	protected static final String url_insert_game_stat = dburl + "insert_basketball_game_stat.php";
	protected static final String url_get_game_stat = dburl + "get_basketball_game_stat.php";
    
    
	public BasketballNetworkHelper(String dbuser, String dbpass, String dbschema) {
		super(dbuser, dbpass, dbschema);
	}
	
	// ----------------------- GAMES table methods ------------------------- //

		public void createGame(Games game, long g_id) throws JSONException, InterruptedException, ExecutionException{
			
			
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
			JSONArray plays = json.getJSONArray(TAG_BASKETBALL_GAMES);
			

			JSONObject c = plays.getJSONObject(0);
			BasketballGames game = new BasketballGames();

		  
		    game.setgid(c.getLong(c.getString(KEY_G_ID)));
		    game.sethomeid((c.getLong(c.getString(KEY_HOME_ID))));
		    game.setawayid((c.getLong(c.getString(KEY_AWAY_ID))));
		    game.setDate(c.getString(c.getString(KEY_DATE)));
		    
		    game.sethomepts((c.getInt(c.getString(KEY_HOME_PTS))));
		    game.sethomefgm((c.getInt(c.getString(KEY_HOME_FGM))));
		    game.sethomefga(c.getInt(c.getString(KEY_HOME_FGA)));
		    game.sethomefgm3(c.getInt(c.getString(KEY_HOME_FGM3)));
		    game.sethomefga3(c.getInt(c.getString(KEY_HOME_FGA3)));
		    game.sethomeftm(c.getInt(c.getString(KEY_HOME_FTM)));
		    game.sethomefta(c.getInt(c.getString(KEY_HOME_FTA)));
		    game.sethomeoreb(c.getInt(c.getString(KEY_HOME_OREB)));
		    game.sethomedreb(c.getInt(c.getString(KEY_HOME_DREB)));
		    game.sethomeast(c.getInt(c.getString(KEY_HOME_AST)));
		    game.sethomestl(c.getInt(c.getString(KEY_HOME_STL)));
		    game.sethomeblk(c.getInt(c.getString(KEY_HOME_BLK)));
		    game.sethometo(c.getInt(c.getString(KEY_HOME_TO)));
		    game.sethomepf(c.getInt(c.getString(KEY_HOME_PF)));
		    game.sethometech(c.getInt(c.getString(KEY_HOME_TECH)));
		    game.sethomeflagrant(c.getInt(c.getString(KEY_HOME_FLAGRANT)));

		    game.setawaypts((c.getInt(c.getString(KEY_AWAY_PTS))));
		    game.setawayfgm((c.getInt(c.getString(KEY_AWAY_FGM))));
		    game.setawayfga(c.getInt(c.getString(KEY_AWAY_FGA)));
		    game.setawayfgm3(c.getInt(c.getString(KEY_AWAY_FGM3)));
		    game.setawayfga3(c.getInt(c.getString(KEY_AWAY_FGA3)));
		    game.setawayftm(c.getInt(c.getString(KEY_AWAY_FTM)));
		    game.setawayfta(c.getInt(c.getString(KEY_AWAY_FTA)));
		    game.setawayoreb(c.getInt(c.getString(KEY_AWAY_OREB)));
		    game.setawaydreb(c.getInt(c.getString(KEY_AWAY_DREB)));
		    game.setawayast(c.getInt(c.getString(KEY_AWAY_AST)));
		    game.setawaystl(c.getInt(c.getString(KEY_AWAY_STL)));
		    game.setawayblk(c.getInt(c.getString(KEY_AWAY_BLK)));
		    game.setawayto(c.getInt(c.getString(KEY_AWAY_TO)));
		    game.setawaypf(c.getInt(c.getString(KEY_AWAY_PF)));
		    game.setawaytech(c.getInt(c.getString(KEY_AWAY_TECH)));
		    game.setawayflagrant(c.getInt(c.getString(KEY_AWAY_FLAGRANT)));
		    
		    return game;
		}
		
		//Get all Games
		public List<Games> getAllGames() throws JSONException, InterruptedException, ExecutionException {
			String w = "";
			List<Games> games  = new ArrayList<Games>();
			List<NameValuePair> params = this.startParams();
			params.add(new BasicNameValuePair(TAG_WHERE, w));
			
		
			
			HttpParameter parameter = new HttpParameter(url_get_game, "POST", params);
			AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
			JSONObject json = result.get();
			JSONArray plays = json.getJSONArray(TAG_BASKETBALL_GAMES);
			
			for (int i = 0; i < plays.length(); i++) {
			JSONObject c = plays.getJSONObject(i);
			BasketballGames game = new BasketballGames();

		  
		    game.setgid(c.getLong(c.getString(KEY_G_ID)));
		    game.sethomeid((c.getLong(c.getString(KEY_HOME_ID))));
		    game.setawayid((c.getLong(c.getString(KEY_AWAY_ID))));
		    game.setDate(c.getString(c.getString(KEY_DATE)));
		    
		    game.sethomepts((c.getInt(c.getString(KEY_HOME_PTS))));
		    game.sethomefgm((c.getInt(c.getString(KEY_HOME_FGM))));
		    game.sethomefga(c.getInt(c.getString(KEY_HOME_FGA)));
		    game.sethomefgm3(c.getInt(c.getString(KEY_HOME_FGM3)));
		    game.sethomefga3(c.getInt(c.getString(KEY_HOME_FGA3)));
		    game.sethomeftm(c.getInt(c.getString(KEY_HOME_FTM)));
		    game.sethomefta(c.getInt(c.getString(KEY_HOME_FTA)));
		    game.sethomeoreb(c.getInt(c.getString(KEY_HOME_OREB)));
		    game.sethomedreb(c.getInt(c.getString(KEY_HOME_DREB)));
		    game.sethomeast(c.getInt(c.getString(KEY_HOME_AST)));
		    game.sethomestl(c.getInt(c.getString(KEY_HOME_STL)));
		    game.sethomeblk(c.getInt(c.getString(KEY_HOME_BLK)));
		    game.sethometo(c.getInt(c.getString(KEY_HOME_TO)));
		    game.sethomepf(c.getInt(c.getString(KEY_HOME_PF)));
		    game.sethometech(c.getInt(c.getString(KEY_HOME_TECH)));
		    game.sethomeflagrant(c.getInt(c.getString(KEY_HOME_FLAGRANT)));

		    game.setawaypts((c.getInt(c.getString(KEY_AWAY_PTS))));
		    game.setawayfgm((c.getInt(c.getString(KEY_AWAY_FGM))));
		    game.setawayfga(c.getInt(c.getString(KEY_AWAY_FGA)));
		    game.setawayfgm3(c.getInt(c.getString(KEY_AWAY_FGM3)));
		    game.setawayfga3(c.getInt(c.getString(KEY_AWAY_FGA3)));
		    game.setawayftm(c.getInt(c.getString(KEY_AWAY_FTM)));
		    game.setawayfta(c.getInt(c.getString(KEY_AWAY_FTA)));
		    game.setawayoreb(c.getInt(c.getString(KEY_AWAY_OREB)));
		    game.setawaydreb(c.getInt(c.getString(KEY_AWAY_DREB)));
		    game.setawayast(c.getInt(c.getString(KEY_AWAY_AST)));
		    game.setawaystl(c.getInt(c.getString(KEY_AWAY_STL)));
		    game.setawayblk(c.getInt(c.getString(KEY_AWAY_BLK)));
		    game.setawayto(c.getInt(c.getString(KEY_AWAY_TO)));
		    game.setawaypf(c.getInt(c.getString(KEY_AWAY_PF)));
		    game.setawaytech(c.getInt(c.getString(KEY_AWAY_TECH)));
		    game.setawayflagrant(c.getInt(c.getString(KEY_AWAY_FLAGRANT)));
		    games.add(game);
			}
		    return games;
		}
		
		//Get all Games played by a team
		public List<Games> getAllGamesTeam(long t_id) throws JSONException, InterruptedException, ExecutionException {
			String w = "WHERE " + KEY_HOME_ID + " = " + Long.toString(t_id) + " OR " + KEY_AWAY_ID + " = " + Long.toString(t_id);
			List<Games> games  = new ArrayList<Games>();
			List<NameValuePair> params = this.startParams();
			params.add(new BasicNameValuePair(TAG_WHERE, w));
			
		
			
			HttpParameter parameter = new HttpParameter(url_get_game, "POST", params);
			AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
			JSONObject json = result.get();
			JSONArray plays = json.getJSONArray(TAG_BASKETBALL_GAMES);
			
			for (int i = 0; i < plays.length(); i++) {
			JSONObject c = plays.getJSONObject(i);
			BasketballGames game = new BasketballGames();

		  
		    game.setgid(c.getLong(c.getString(KEY_G_ID)));
		    game.sethomeid((c.getLong(c.getString(KEY_HOME_ID))));
		    game.setawayid((c.getLong(c.getString(KEY_AWAY_ID))));
		    game.setDate(c.getString(c.getString(KEY_DATE)));
		    
		    game.sethomepts((c.getInt(c.getString(KEY_HOME_PTS))));
		    game.sethomefgm((c.getInt(c.getString(KEY_HOME_FGM))));
		    game.sethomefga(c.getInt(c.getString(KEY_HOME_FGA)));
		    game.sethomefgm3(c.getInt(c.getString(KEY_HOME_FGM3)));
		    game.sethomefga3(c.getInt(c.getString(KEY_HOME_FGA3)));
		    game.sethomeftm(c.getInt(c.getString(KEY_HOME_FTM)));
		    game.sethomefta(c.getInt(c.getString(KEY_HOME_FTA)));
		    game.sethomeoreb(c.getInt(c.getString(KEY_HOME_OREB)));
		    game.sethomedreb(c.getInt(c.getString(KEY_HOME_DREB)));
		    game.sethomeast(c.getInt(c.getString(KEY_HOME_AST)));
		    game.sethomestl(c.getInt(c.getString(KEY_HOME_STL)));
		    game.sethomeblk(c.getInt(c.getString(KEY_HOME_BLK)));
		    game.sethometo(c.getInt(c.getString(KEY_HOME_TO)));
		    game.sethomepf(c.getInt(c.getString(KEY_HOME_PF)));
		    game.sethometech(c.getInt(c.getString(KEY_HOME_TECH)));
		    game.sethomeflagrant(c.getInt(c.getString(KEY_HOME_FLAGRANT)));

		    game.setawaypts((c.getInt(c.getString(KEY_AWAY_PTS))));
		    game.setawayfgm((c.getInt(c.getString(KEY_AWAY_FGM))));
		    game.setawayfga(c.getInt(c.getString(KEY_AWAY_FGA)));
		    game.setawayfgm3(c.getInt(c.getString(KEY_AWAY_FGM3)));
		    game.setawayfga3(c.getInt(c.getString(KEY_AWAY_FGA3)));
		    game.setawayftm(c.getInt(c.getString(KEY_AWAY_FTM)));
		    game.setawayfta(c.getInt(c.getString(KEY_AWAY_FTA)));
		    game.setawayoreb(c.getInt(c.getString(KEY_AWAY_OREB)));
		    game.setawaydreb(c.getInt(c.getString(KEY_AWAY_DREB)));
		    game.setawayast(c.getInt(c.getString(KEY_AWAY_AST)));
		    game.setawaystl(c.getInt(c.getString(KEY_AWAY_STL)));
		    game.setawayblk(c.getInt(c.getString(KEY_AWAY_BLK)));
		    game.setawayto(c.getInt(c.getString(KEY_AWAY_TO)));
		    game.setawaypf(c.getInt(c.getString(KEY_AWAY_PF)));
		    game.setawaytech(c.getInt(c.getString(KEY_AWAY_TECH)));
		    game.setawayflagrant(c.getInt(c.getString(KEY_AWAY_FLAGRANT)));
		    games.add(game);
			}
		    return games;
		}
		
		//get single game stat for team
		public int getTeamGameStat(long g_id, String stat) throws NumberFormatException, JSONException, InterruptedException, ExecutionException {
		 
		    //create query to select game
		    String query = "SELECT " + stat + " FROM " + _schema + TABLE_GAMES + 
		    	" WHERE " + KEY_G_ID + " = " + g_id; 
			List<NameValuePair> params = this.startParams();
			params.add(new BasicNameValuePair(TAG_QUERY, query));
			
		
			
			HttpParameter parameter = new HttpParameter(url_get_stat, "POST", params);
			AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
			JSONObject json = result.get();
			JSONArray stats = json.getJSONArray(TAG_STAT);
			JSONObject c = stats.getJSONObject(0);
			return Integer.parseInt(c.getString(TAG_STAT_VALUE));
		}
		
		//Adding value to points category of a player
		public void addTeamStats(long g_id, String stat, int value) throws NumberFormatException, JSONException, InterruptedException, ExecutionException{
			
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
		
		
		// ----------------------- BASKETBALL_GAME_STATS ---------------------------- //

		public void createGameStats(long p_id, long g_id){
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
			public BasketballGameStats getPlayerGameStats(long g_id, long p_id) throws JSONException, InterruptedException, ExecutionException {
			    //create query to select game
			    String w = " WHERE " + KEY_G_ID + " = " + g_id + 
			    	" AND " + KEY_P_ID + " = " + p_id;
			    
			    List<NameValuePair> params = this.startParams();
				params.add(new BasicNameValuePair(TAG_WHERE, w));
				
				HttpParameter parameter = new HttpParameter(url_get_game_stat, "POST", params);
				AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
				JSONObject json = result.get();
				JSONArray stat = json.getJSONArray(TAG_BASKETBALL_GAME_STATS);
				

				JSONObject c = stat.getJSONObject(0);
			    
			    
			    //create the instance of Games using cursor information
			    BasketballGameStats stats = new BasketballGameStats();
			    stats.setgid(c.getLong(c.getString(KEY_G_ID)));
			    stats.setpid(c.getLong(c.getString(KEY_P_ID)));
			    stats.setpts((c.getInt(c.getString(KEY_PTS))));
			    stats.setfgm((c.getInt(c.getString(KEY_FGM))));
			    stats.setfga(c.getInt(c.getString(KEY_FGA)));
			    stats.setfgm3(c.getInt(c.getString(KEY_FGM3)));
			    stats.setfga3(c.getInt(c.getString(KEY_FGA3)));
			    stats.setftm(c.getInt(c.getString(KEY_FTM)));
			    stats.setfta(c.getInt(c.getString(KEY_FTA)));
			    stats.setoreb(c.getInt(c.getString(KEY_OREB)));
			    stats.setdreb(c.getInt(c.getString(KEY_DREB)));
			    stats.setast(c.getInt(c.getString(KEY_AST)));
			    stats.setstl(c.getInt(c.getString(KEY_STL)));
			    stats.setblk(c.getInt(c.getString(KEY_BLK)));
			    stats.setto(c.getInt(c.getString(KEY_TO)));
			    stats.setpf(c.getInt(c.getString(KEY_PF)));
			    stats.settech(c.getInt(c.getString(KEY_TECH)));
			    stats.setflagrant(c.getInt(c.getString(KEY_FLAGRANT)));

			    //Insert more stats here
			    
			    return stats;
			}
			
			//get single game stat for single player
			public int getPlayerGameStat(long g_id, long p_id, String stat) throws JSONException, InterruptedException, ExecutionException {
				
				
				
			    //create query to select game
			    String query = "SELECT " + stat + " FROM " + TABLE_BASKETBALL_GAME_STATS + 
				    	" WHERE " + KEY_G_ID + " = " + g_id + 
				    	" AND " + KEY_P_ID + " = " + p_id;
				List<NameValuePair> params = this.startParams();
				params.add(new BasicNameValuePair(TAG_QUERY, query));
				
			
				
				HttpParameter parameter = new HttpParameter(url_get_stat, "POST", params);
				AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
				JSONObject json = result.get();
				JSONArray stats = json.getJSONArray(TAG_STAT);
				JSONObject c = stats.getJSONObject(0);
				return Integer.parseInt(c.getString(TAG_STAT_VALUE));

			}
			
			//Get all GameStats for player
			public List<BasketballGameStats> getPlayerAllGameStats(long p_id) throws JSONException, InterruptedException, ExecutionException {
			    List<BasketballGameStats> gameStats = new ArrayList<BasketballGameStats>();
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
			    stats.setgid(c.getLong(c.getString(KEY_G_ID)));
			    stats.setpid(c.getLong(c.getString(KEY_P_ID)));
			    stats.setpts((c.getInt(c.getString(KEY_PTS))));
			    stats.setfgm((c.getInt(c.getString(KEY_FGM))));
			    stats.setfga(c.getInt(c.getString(KEY_FGA)));
			    stats.setfgm3(c.getInt(c.getString(KEY_FGM3)));
			    stats.setfga3(c.getInt(c.getString(KEY_FGA3)));
			    stats.setftm(c.getInt(c.getString(KEY_FTM)));
			    stats.setfta(c.getInt(c.getString(KEY_FTA)));
			    stats.setoreb(c.getInt(c.getString(KEY_OREB)));
			    stats.setdreb(c.getInt(c.getString(KEY_DREB)));
			    stats.setast(c.getInt(c.getString(KEY_AST)));
			    stats.setstl(c.getInt(c.getString(KEY_STL)));
			    stats.setblk(c.getInt(c.getString(KEY_BLK)));
			    stats.setto(c.getInt(c.getString(KEY_TO)));
			    stats.setpf(c.getInt(c.getString(KEY_PF)));
			    stats.settech(c.getInt(c.getString(KEY_TECH)));
			    stats.setflagrant(c.getInt(c.getString(KEY_FLAGRANT)));
			    gameStats.add(stats);
				}
			    //Insert more stats here
			    
			    return gameStats;
			}
			
			//Get all GameStats
			public List<BasketballGameStats> getAllGameStats() throws InterruptedException, ExecutionException, JSONException {
				 List<BasketballGameStats> gameStats = new ArrayList<BasketballGameStats>();
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
				    stats.setgid(c.getLong(c.getString(KEY_G_ID)));
				    stats.setpid(c.getLong(c.getString(KEY_P_ID)));
				    stats.setpts((c.getInt(c.getString(KEY_PTS))));
				    stats.setfgm((c.getInt(c.getString(KEY_FGM))));
				    stats.setfga(c.getInt(c.getString(KEY_FGA)));
				    stats.setfgm3(c.getInt(c.getString(KEY_FGM3)));
				    stats.setfga3(c.getInt(c.getString(KEY_FGA3)));
				    stats.setftm(c.getInt(c.getString(KEY_FTM)));
				    stats.setfta(c.getInt(c.getString(KEY_FTA)));
				    stats.setoreb(c.getInt(c.getString(KEY_OREB)));
				    stats.setdreb(c.getInt(c.getString(KEY_DREB)));
				    stats.setast(c.getInt(c.getString(KEY_AST)));
				    stats.setstl(c.getInt(c.getString(KEY_STL)));
				    stats.setblk(c.getInt(c.getString(KEY_BLK)));
				    stats.setto(c.getInt(c.getString(KEY_TO)));
				    stats.setpf(c.getInt(c.getString(KEY_PF)));
				    stats.settech(c.getInt(c.getString(KEY_TECH)));
				    stats.setflagrant(c.getInt(c.getString(KEY_FLAGRANT)));
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
			public void addStats(long g_id, long p_id, String stat, int value) throws NumberFormatException, JSONException, InterruptedException, ExecutionException{
				
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

		public void createPlayers(BasketballPlayer player, Long p_id){

			List<NameValuePair> params = this.startParams();			

			params.add(new BasicNameValuePair(KEY_P_ID, Long.toString(p_id)));
			params.add(new BasicNameValuePair(KEY_T_ID, Long.toString(player.gettid())));
		    params.add(new BasicNameValuePair(KEY_P_NAME, player.getpname()));
		    params.add(new BasicNameValuePair(KEY_P_NUM, Integer.toString(player.getpnum())));
			 
			
			HttpParameter parameter = new HttpParameter(url_insert_game,"POST", params);
			AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
		}
		
		//get single player
		public BasketballPlayer getPlayer(long p_id) throws JSONException, InterruptedException, ExecutionException {
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
		}
			

}
