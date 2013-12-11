package com.seniordesign.ultimatescorecard.data;


import java.io.Serializable;


public class GameTime implements Serializable {
	private static final long serialVersionUID = 3390144989945164475L;
	private Team _homeTeam, _awayTeam;
	private boolean _gameStarted = false;
	private boolean _possession = false;
	private boolean _foulOnPlay = false;
	private boolean _keepPossession = false;
		
	public GameTime(String away, String home){
		_homeTeam = new Team(home, true);
		_awayTeam = new Team(away, false);
	}
	
	//getting the name of a player given team name and which player 
	public Player getPlayer(String whichTeam, int player){
		if(whichTeam.equals(_homeTeam.getTeamName())){
			return _homeTeam.getPlayer(player);
		}
		else{
			return _awayTeam.getPlayer(player);
		}
	}
	
	//getting the name of a player given only player name
	public Player getPlayer(String player){
		for(int i=0; i<5; i++){
			if(player.equals(_homeTeam.getPlayer(i).getName())){
				return _homeTeam.getPlayer(i);
			}
		}
		for(int i=0; i<5; i++){
			if(player.equals(_awayTeam.getPlayer(i).getName())){
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
	
	public boolean isGameStarted(){
		return _gameStarted;
	}
	
	public void gameStarted(){
		_gameStarted = true;
	}
	
	public void gameStopped(){
		_gameStarted = false;
	}
	
	public void setPossession(boolean initial){
		_possession = initial;
	}
	
	public void changePossession(){
		_possession = !_possession;
	}
	
	public boolean getPossession(){
		return _possession;
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
	
	public Team getTheHomeTeam(){
		return _homeTeam;
	}
	
	public Team getTheAwayTeam(){
		return _awayTeam;
	}
	
}
