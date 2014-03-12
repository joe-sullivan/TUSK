package com.seniordesign.ultimatescorecard.data.hockey;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;

import com.seniordesign.ultimatescorecard.data.GameInfo;
import com.seniordesign.ultimatescorecard.data.GameTime;
import com.seniordesign.ultimatescorecard.sqlite.helper.Games;
import com.seniordesign.ultimatescorecard.sqlite.helper.Players;
import com.seniordesign.ultimatescorecard.sqlite.helper.Teams;
import com.seniordesign.ultimatescorecard.sqlite.hockey.HockeyDatabaseHelper;

public class HockeyGameTime extends GameTime{
	private static final long serialVersionUID = -7763761249318969512L;
	private HockeyTeam _homeTeam, _awayTeam;
	
	//databases
	public HockeyDatabaseHelper _hockey_db;
	private long g_id;
	private Context _context;
	private Teams _home, _away;
	private long _home_t_id, _away_t_id;
	public GameInfo _gameInfo;
	private ArrayList<Players> _homeTeamPlayersPull, _awayTeamPlayersPull;
	
	public HockeyGameTime (Teams home, Teams away){
		_home = home;
		_away = away;
	}
	
	public void setContext(Context context){
		_context = context;
	}
	
	public long createTeams(){
		_hockey_db = new HockeyDatabaseHelper(_context);
		_homeTeam = new HockeyTeam(_home.gettname(), true);
		_awayTeam = new HockeyTeam(_away.gettname(), false);

		_home_t_id = _home.gettid();
		_away_t_id = _away.gettid();
		
		String date = DateFormat.getDateTimeInstance().format(new Date());
		g_id = _hockey_db.createGame(new Games(_home_t_id, _away_t_id, date));

		ArrayList<Players> _homeTeamPlayer = (ArrayList<Players>) _hockey_db.getPlayersTeam(_home_t_id);
		ArrayList<Players> _awayTeamPlayer = (ArrayList<Players>) _hockey_db.getPlayersTeam(_away_t_id);
		
		ArrayList<HockeyPlayer> _homeTeamPlayers = new ArrayList<HockeyPlayer>();
		for(Players p: _homeTeamPlayer){
			HockeyPlayer player = new HockeyPlayer();
			player.setpid(p.getpid());
			player.settid(p.gettid());
			player.setpname(p.getpname());
			player.setpnum(p.getpnum());
			player.setdb(_hockey_db);
			_homeTeamPlayers.add(player);

		}
		ArrayList<HockeyPlayer> _awayTeamPlayers = new ArrayList<HockeyPlayer>();
		for(Players p: _awayTeamPlayer){
			HockeyPlayer player = new HockeyPlayer();
			player.setpid(p.getpid());
			player.settid(p.gettid());
			player.setpname(p.getpname());
			player.setpnum(p.getpnum());
			player.setdb(_hockey_db);
			_awayTeamPlayers.add(player);

		}
		
		_homeTeam.setData(g_id, _home, _homeTeamPlayers);
		_awayTeam.setData(g_id, _away, _awayTeamPlayers);
		_homeTeam.setTeamAbbr();
		_awayTeam.setTeamAbbr();
		_homeTeamPlayersPull = (ArrayList<Players>) _hockey_db.getPlayersTeam2(_home_t_id);
		_awayTeamPlayersPull = (ArrayList<Players>) _hockey_db.getPlayersTeam2(_away_t_id);
		_gameInfo = new GameInfo(_home, _away, _homeTeamPlayersPull, _awayTeamPlayersPull, g_id);

		return g_id;
	}
	
	public GameInfo getGameInfo(){
		return _gameInfo;
	}
	
	public void setGameInfo(GameInfo gameInfo){
		_gameInfo = gameInfo;
	}
	
	public HockeyPlayer getPlayer(String whichTeam, int player){
		if(whichTeam.equals(_homeTeam.getTeamName())){
			return _homeTeam.getPlayer(player);
		}
		else{
			return _awayTeam.getPlayer(player);
		}
	}
	
	public HockeyPlayer getPlayer(String player){
		for(int i=0; i<_homeTeam.numberPlayers(); i++){
			if(player.equals(_homeTeam.getPlayer(i).getpname())){
				return _homeTeam.getPlayer(i);
			}
		}
		for(int i=0; i<_awayTeam.numberPlayers(); i++){
			if(player.equals(_awayTeam.getPlayer(i).getpname())){
				return _awayTeam.getPlayer(i);
			}
		}
		return null;
	}
	
	public String getHomeTeam(){
		return _homeTeam.getTeamName();
	}
	public String getAwayTeam(){
		return _awayTeam.getTeamName();
	}
	
	public String getHomeAbbr(){
		return _homeTeam.getTeamAbbr();
	}
	public String getAwayAbbr(){
		return _awayTeam.getTeamAbbr();
	}
	
	public String getHomeScoreText(){
		if(_homeTeam.getScore() < 10){
			return "00"+ _homeTeam.getScore();
		}
		else {
			return "0"+ _homeTeam.getScore();
		}
	}
	public String getAwayScoreText(){
		if(_awayTeam.getScore() < 10){
			return "00"+ _awayTeam.getScore();
		}
		else {
			return "0"+ _awayTeam.getScore();
		}
	}
	
	public HockeyTeam getTheHomeTeam(){
		return _homeTeam;
	}
	public HockeyTeam getTheAwayTeam(){
		return _awayTeam;
	}
	
	public HockeyTeam getTeam(){
		if(_possession){
			return _homeTeam;
		}
		else{
			return _awayTeam;
		}
	}
	
	public HockeyTeam getOppoTeam(){
		if(_possession){
			return _awayTeam;
		}
		else{
			return _homeTeam;
		}
	}
	
	public long gethometid(){
		return _home_t_id;
	}
	
	public long getawaytid(){
		return _away_t_id;
	}
}
