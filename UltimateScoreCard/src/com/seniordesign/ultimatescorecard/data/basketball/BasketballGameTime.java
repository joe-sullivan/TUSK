package com.seniordesign.ultimatescorecard.data.basketball;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;

import com.seniordesign.ultimatescorecard.data.GameInfo;
import com.seniordesign.ultimatescorecard.data.GameTime;
import com.seniordesign.ultimatescorecard.sqlite.basketball.BasketballDatabaseHelper;
import com.seniordesign.ultimatescorecard.sqlite.helper.Games;
import com.seniordesign.ultimatescorecard.sqlite.helper.Players;
import com.seniordesign.ultimatescorecard.sqlite.helper.Teams;


public class BasketballGameTime extends GameTime {
	private static final long serialVersionUID = -8188939132700576156L;
	private BasketballTeam _homeTeam, _awayTeam;
	private boolean _foulOnPlay = false;
	private boolean _keepPossession = false;
	//databases
	public BasketballDatabaseHelper _basketball_db;
	private long g_id;
	private Context _context;
	private Teams _home, _away;
	private long _home_t_id, _away_t_id;
	private GameInfo _gameInfo;
	private ArrayList<Players> _homeTeamPlayersPull, _awayTeamPlayersPull;
		
	public BasketballGameTime(Teams home, Teams away){
		//databases
		_home = home;
		_away = away;
	}	
	
	public void setContext(Context context){
		_context = context;
	}
	
	public long createTeams(){
		_basketball_db = new BasketballDatabaseHelper(_context);
		_homeTeam = new BasketballTeam(_home.gettname(), true);
		_awayTeam = new BasketballTeam(_away.gettname(), false);

		_home_t_id = _home.gettid();
		_away_t_id = _away.gettid();
		
		String date = DateFormat.getDateTimeInstance().format(new Date());
		g_id = _basketball_db.createGame(new Games(_home_t_id, _away_t_id, date));

		ArrayList<Players> _homeTeamPlayer = (ArrayList<Players>) _basketball_db.getPlayersTeam(_home_t_id);
		ArrayList<Players> _awayTeamPlayer = (ArrayList<Players>) _basketball_db.getPlayersTeam(_away_t_id);
		
		ArrayList<BasketballPlayer> _homeTeamPlayers = new ArrayList<BasketballPlayer>();
		for(Players p: _homeTeamPlayer){
			BasketballPlayer player = new BasketballPlayer();
			player.setpid(p.getpid());
			player.settid(p.gettid());
			player.setpname(p.getpname());
			player.setpnum(p.getpnum());
			player.setdb(_basketball_db);
			_homeTeamPlayers.add(player);
		}
		ArrayList<BasketballPlayer> _awayTeamPlayers = new ArrayList<BasketballPlayer>();
		for(Players p: _awayTeamPlayer){
			BasketballPlayer player = new BasketballPlayer();
			player.setpid(p.getpid());
			player.settid(p.gettid());
			player.setpname(p.getpname());
			player.setpnum(p.getpnum());
			player.setdb(_basketball_db);
			_awayTeamPlayers.add(player);

		}
		
		_homeTeam.setData(g_id, _home, _homeTeamPlayers);
		_awayTeam.setData(g_id, _away, _awayTeamPlayers);
		_homeTeam.setTeamAbbr();
		_awayTeam.setTeamAbbr();
		_homeTeamPlayersPull = (ArrayList<Players>) _basketball_db.getPlayersTeam2(_home_t_id);
		_awayTeamPlayersPull = (ArrayList<Players>) _basketball_db.getPlayersTeam2(_away_t_id);
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
	
	//getting the name of a player given team name and which player 
	public BasketballPlayer getPlayer(String whichTeam, int player){
		if(whichTeam.equals(_homeTeam.getTeamName())){
			return _homeTeam.getPlayer(player);
		}
		else{
			return _awayTeam.getPlayer(player);
		}
	}
	
	//getting the name of a player given only player name
	public BasketballPlayer getPlayer(String player){
		for(int i=0; i<5; i++){
			if(player.equals(_homeTeam.getPlayer(i).getpname())){
				return _homeTeam.getPlayer(i);
			}
		}
		for(int i=0; i<5; i++){
			if(player.equals(_awayTeam.getPlayer(i).getpname())){
				return _awayTeam.getPlayer(i);
			}
		}
		return null;
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
	
	public void scoreChange(boolean team, int point, String player){
		if(team){
			_homeTeam.scoreChange(point, player);
		}
		else{
			_awayTeam.scoreChange(point, player);
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
	
	public boolean foulOccurred(){
		return _foulOnPlay;
	}
	
	public void setFoulVariable(boolean which){
		_foulOnPlay = which;
	}
	
	public boolean keepPossession(){
		return _keepPossession;
	}
	
	public void willKeepPossession(boolean which){
		_keepPossession = which;
	}
	
	public String getTeamPossession(boolean flip){
		if(flip){
			if(_possession)
				return getAwayAbbr();
			else
				return getHomeAbbr();
		}
		else{
			if(_possession)
				return getHomeAbbr();
			else
				return getAwayAbbr();
		}
		
	}
	
	public long getgid(){
		return g_id;
	}
	
	public long gethometid(){
		return _home_t_id;
	}
	
	public long getawaytid(){
		return _away_t_id;
	}
	
	public void addTeamDRebound(){
		if(_possession){
			_basketball_db.addTeamStats(g_id, "home_dreb", 1);
		}
		else{
			_basketball_db.addTeamStats(g_id, "away_dreb", 1);

		}
	}
	
	public void addTeamORebound(){
		if(_possession){
			_basketball_db.addTeamStats(g_id, "home_oreb", 1);
		}
		else{
			_basketball_db.addTeamStats(g_id, "away_oreb", 1);

		}
	}
}
