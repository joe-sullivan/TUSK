package com.seniordesign.ultimatescorecard.data.soccer;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;

import com.seniordesign.ultimatescorecard.data.GameInfo;
import com.seniordesign.ultimatescorecard.data.GameTime;
import com.seniordesign.ultimatescorecard.sqlite.helper.Games;
import com.seniordesign.ultimatescorecard.sqlite.helper.Players;
import com.seniordesign.ultimatescorecard.sqlite.helper.Teams;
import com.seniordesign.ultimatescorecard.sqlite.soccer.SoccerDatabaseHelper;

public class SoccerGameTime extends GameTime {
	private static final long serialVersionUID = 7528809796107161199L;
	private SoccerTeam _homeTeam, _awayTeam;
	
	//databases
	public SoccerDatabaseHelper _soccer_db;
	private long g_id;
	private Context _context;
	private Teams _home, _away;
	private long _home_t_id, _away_t_id;
	public GameInfo _gameInfo;
	private ArrayList<Players> _homeTeamPlayersPull, _awayTeamPlayersPull;
	
	
	public SoccerGameTime (Teams home, Teams away){
		_home = home;
		_away = away;
	}
	
	public void setContext(Context context){
		_context = context;
	}
	
	public long createTeams(){
		_soccer_db = new SoccerDatabaseHelper(_context);
		_homeTeam = new SoccerTeam(_home.gettname(), true);
		_awayTeam = new SoccerTeam(_away.gettname(), false);

		_home_t_id = _home.gettid();
		_away_t_id = _away.gettid();
		
		String date = DateFormat.getDateTimeInstance().format(new Date());
		g_id = _soccer_db.createGame(new Games(_home_t_id, _away_t_id, date));

		ArrayList<Players> _homeTeamPlayer = (ArrayList<Players>) _soccer_db.getPlayersTeam(_home_t_id);
		ArrayList<Players> _awayTeamPlayer = (ArrayList<Players>) _soccer_db.getPlayersTeam(_away_t_id);
		
		ArrayList<SoccerPlayer> _homeTeamPlayers = new ArrayList<SoccerPlayer>();
		for(Players p: _homeTeamPlayer){
			SoccerPlayer player = new SoccerPlayer();
			player.setpid(p.getpid());
			player.settid(p.gettid());
			player.setpname(p.getpname());
			player.setpnum(p.getpnum());
			player.setdb(_soccer_db);
			_homeTeamPlayers.add(player);

		}
		ArrayList<SoccerPlayer> _awayTeamPlayers = new ArrayList<SoccerPlayer>();
		for(Players p: _awayTeamPlayer){
			SoccerPlayer player = new SoccerPlayer();
			player.setpid(p.getpid());
			player.settid(p.gettid());
			player.setpname(p.getpname());
			player.setpnum(p.getpnum());
			player.setdb(_soccer_db);
			_awayTeamPlayers.add(player);

		}
		
		_homeTeam.setData(g_id, _home, _homeTeamPlayers);
		_awayTeam.setData(g_id, _away, _awayTeamPlayers);
		_homeTeam.setTeamAbbr();
		_awayTeam.setTeamAbbr();
		_homeTeamPlayersPull = (ArrayList<Players>) _soccer_db.getPlayersTeam2(_home_t_id);
		_awayTeamPlayersPull = (ArrayList<Players>) _soccer_db.getPlayersTeam2(_away_t_id);
		_gameInfo = new GameInfo(_home, _away, _homeTeamPlayersPull, _awayTeamPlayersPull, g_id);

		return g_id;
	}
	
	public GameInfo getGameInfo(){
		return _gameInfo;
	}
	
	public void setGameInfo(GameInfo gameInfo){
		_gameInfo = gameInfo;
	}
	
	public SoccerPlayer getPlayer(String whichTeam, int player){
		if(whichTeam.equals(_homeTeam.getTeamName())){
			return _homeTeam.getPlayer(player);
		}
		else{
			return _awayTeam.getPlayer(player);
		}
	}
	
	public SoccerPlayer getPlayer(String player){
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
	
	public SoccerTeam getTheHomeTeam(){
		return _homeTeam;
	}
	public SoccerTeam getTheAwayTeam(){
		return _awayTeam;
	}
	
	public SoccerTeam getTeam(){
		if(_possession){
			return _homeTeam;
		}
		else{
			return _awayTeam;
		}
	}
	
	public SoccerTeam getOppoTeam(){
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
