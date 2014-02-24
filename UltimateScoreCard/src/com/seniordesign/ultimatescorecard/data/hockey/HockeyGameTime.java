package com.seniordesign.ultimatescorecard.data.hockey;

import com.seniordesign.ultimatescorecard.data.GameTime;

public class HockeyGameTime extends GameTime{
	private static final long serialVersionUID = -7763761249318969512L;
	private HockeyTeam _homeTeam, _awayTeam;
	
	public HockeyGameTime (String home, String away){
		_homeTeam = new HockeyTeam(home, true);
		_awayTeam = new HockeyTeam(away, false);
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
			if(player.equals(_homeTeam.getPlayer(i).getName())){
				return _homeTeam.getPlayer(i);
			}
		}
		for(int i=0; i<_awayTeam.numberPlayers(); i++){
			if(player.equals(_awayTeam.getPlayer(i).getName())){
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
			return "0"+ _homeTeam.getScore();
		}
		else {
			return ""+ _homeTeam.getScore();
		}
	}
	public String getAwayScoreText(){
		if(_awayTeam.getScore() < 10){
			return "0"+ _awayTeam.getScore();
		}
		else {
			return ""+ _awayTeam.getScore();
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
}
