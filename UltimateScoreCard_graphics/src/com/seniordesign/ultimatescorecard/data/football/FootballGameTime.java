package com.seniordesign.ultimatescorecard.data.football;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.seniordesign.ultimatescorecard.data.GameInfo;
import com.seniordesign.ultimatescorecard.data.GameTime;
import com.seniordesign.ultimatescorecard.data.football.FootballPlayer;
import com.seniordesign.ultimatescorecard.data.football.FootballTeam;
import com.seniordesign.ultimatescorecard.sqlite.football.FootballDatabaseHelper;
import com.seniordesign.ultimatescorecard.sqlite.helper.Games;
import com.seniordesign.ultimatescorecard.sqlite.helper.Players;
import com.seniordesign.ultimatescorecard.sqlite.helper.Teams;

import android.content.Context;
import android.util.Log;

public class FootballGameTime extends GameTime{
	private static final long serialVersionUID = 3840132882537431776L;
	private FootballTeam _homeTeam;
	private FootballTeam _awayTeam;
	private String[] _lineOfScrimmage = new String[2];
	private int[] _downDistance = new int[2];
	private String _gameState;
	
	//databases
	public FootballDatabaseHelper _football_db;
	private long g_id;
	private Context _context;
	private Teams _home, _away;
	private ArrayList<FootballPlayer> _homeTeamPlayers, _awayTeamPlayers;
	private long _home_t_id, _away_t_id;
	public GameInfo _gameInfo;
	private ArrayList<Players> _homeTeamPlayersPull, _awayTeamPlayersPull;
			
	public FootballGameTime(Teams home, Teams away){
		//databases
		_home = home;
		_away = away;
	}	
	
	public void setContext(Context context){
		_context = context;
	}
	
	public long createTeams(){
		_football_db = new FootballDatabaseHelper(_context);
		_homeTeam = new FootballTeam(_home.gettname(), true);
		_awayTeam = new FootballTeam(_away.gettname(), false);

		_home_t_id = _home.gettid();
		_away_t_id = _away.gettid();
		
		String date = DateFormat.getDateTimeInstance().format(new Date());
		g_id = _football_db.createGame(new Games(_home_t_id, _away_t_id, date));

		ArrayList<Players> _homeTeamPlayer = (ArrayList<Players>) _football_db.getPlayersTeam(_home_t_id);
		ArrayList<Players> _awayTeamPlayer = (ArrayList<Players>) _football_db.getPlayersTeam(_away_t_id);
		
		ArrayList<FootballPlayer> _homeTeamPlayers = new ArrayList<FootballPlayer>();
		for(Players p: _homeTeamPlayer){
			FootballPlayer player = new FootballPlayer();
			player.setpid(p.getpid());
			player.settid(p.gettid());
			player.setpname(p.getpname());
			player.setpnum(p.getpnum());
			player.setdb(_football_db);
			_homeTeamPlayers.add(player);
		}
		ArrayList<FootballPlayer> _awayTeamPlayers = new ArrayList<FootballPlayer>();
		for(Players p: _awayTeamPlayer){
			FootballPlayer player = new FootballPlayer();
			player.setpid(p.getpid());
			player.settid(p.gettid());
			player.setpname(p.getpname());
			player.setpnum(p.getpnum());
			player.setdb(_football_db);
			_awayTeamPlayers.add(player);

		}
		
		_homeTeam.setData(g_id, _home, _homeTeamPlayers);
		_awayTeam.setData(g_id, _away, _awayTeamPlayers);
		_homeTeam.setTeamAbbr();
		_awayTeam.setTeamAbbr();
		_homeTeamPlayersPull = (ArrayList<Players>) _football_db.getPlayersTeam2(_home_t_id);
		_awayTeamPlayersPull = (ArrayList<Players>) _football_db.getPlayersTeam2(_away_t_id);
		_gameInfo = new GameInfo(_home, _away, _homeTeamPlayersPull, _awayTeamPlayersPull, g_id);

		return g_id;
	}
	
	public GameInfo getGameInfo(){
		return _gameInfo;
	}
	
	public void setGameInfo(GameInfo gameInfo){
		_gameInfo = gameInfo;
		_homeTeam.setTeamOrder(_gameInfo.getHomePlayers());
		_awayTeam.setTeamOrder(_gameInfo.getAwayPlayers());
	}
		
	//Getter and setter for team names
	public String getHomeTeam(){
		return _homeTeam.getTeamName();
	}
	public String getAwayTeam(){
		return _awayTeam.getTeamName();
	}
	
	//Getters for team abbreviations
	public String getHomeAbbr(){
		return _homeTeam.getTeamAbbr();
	}
	public String getAwayAbbr(){
		return _awayTeam.getTeamAbbr();
	}
	
	public void scoreChange(boolean team, String type, int point, String player1, String player2){
		if(team){
			_homeTeam.scoreChange(type, point, player1, player2);
		}
		else{
			_awayTeam.scoreChange(type, point, player1, player2);
		}
	}
	
	public String getHomeScoreText(){
		if(_homeTeam.getScore() < 10){
			return "00"+ _homeTeam.getScore();
		}
		else if (_homeTeam.getScore() < 100){
			return "0"+ _homeTeam.getScore();
		}
		else {
			return ""+ _homeTeam.getScore();
		}
	}
	public String getAwayScoreText(){
		if(_awayTeam.getScore() < 10){
			return "00"+ _awayTeam.getScore();
		}
		else if (_awayTeam.getScore() < 100){
			return "0"+ _awayTeam.getScore();
		}
		else {
			return ""+ _awayTeam.getScore();
			}
		}
	
	public FootballTeam getTheHomeTeam(){
		return _homeTeam;
	}
	
	public FootballTeam getTheAwayTeam(){
		return _awayTeam;
	}	
	
	public String getGameState(){
		return _gameState;
	}
	
	public void setGameState(String gameState){
		_gameState = gameState;
	}
	
	public void setLineOfScrimmage(int value){
		if(value < 50){
			_lineOfScrimmage[0] = "OPP";
			_lineOfScrimmage[1] = Integer.toString(value);
		}
		else if (value > 50){
			_lineOfScrimmage[0] = "OWN";
			_lineOfScrimmage[1] = Integer.toString(100-value);
		}
		else{
			_lineOfScrimmage[0] = "MID";
			_lineOfScrimmage[1] = "50";
		}
	}
	
	public int getLineOfScrimmageAsValue(){
		if(_lineOfScrimmage[0].equals("OWN")){
			return (100-Integer.parseInt(_lineOfScrimmage[1]));
		}
		else if (_lineOfScrimmage[0].equals("OPP")){
			return Integer.parseInt(_lineOfScrimmage[1]);
		}
		else {
			return 50;
		}
	}
	
	public void setFirstDown(){
		_downDistance[0] = 1;
		_downDistance[1] = 10;
	}
	
	public int getDowns(){
		return _downDistance[0];
	}
	public int increaseDownsByOne(){
		return _downDistance[0]++;
	}
	
	public int getDistance(){
		return _downDistance[1];
	}
	
	public void switchField(){
		if(_lineOfScrimmage[0].equals("OWN")){
			_lineOfScrimmage[0] = "OPP";
		}
		else if (_lineOfScrimmage[0].equals("OPP")){
			_lineOfScrimmage[0] = "OWN";
		}
	}
	
	//increase the down by one and change the distance
	//WARNING: DOWNS CAN BECOME LARGER THAN 4 IF USED INCORRECTLY, ALWAYS SURROUND METHOD CALL WITH CONDITION STATEMENT
	public void advanceDownAndDistance(int oldLOS, int newLOS){
		_downDistance[0]++;
		if(oldLOS-newLOS < _downDistance[1]){
			_downDistance[1] -= (oldLOS-newLOS);
		}
		else{
			setFirstDown();
		}
	}
	
	//check if yardage gain if enough for first down
	public boolean checkFirstDown(int oldLOS, int newLOS){
		return oldLOS-newLOS > _downDistance[1];
	}
}
