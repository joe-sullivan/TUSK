package com.seniordesign.ultimatescorecard.data;

public class FootballGameTime extends GameTime{
	private static final long serialVersionUID = 3840132882537431776L;
	private FootballTeam _homeTeam;
	private FootballTeam _awayTeam;
	private boolean _possession = true;
	private boolean _sideOfField = true;
	private int _lineOfScrimmage = -1;
	private int _down = 1;
	private int toGo = 10;
	private String _gameStatus = "pre-game";
		
	public FootballGameTime(String away, String home){
		_homeTeam = new FootballTeam(home, true);
		_awayTeam = new FootballTeam(away, false);
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
	
	public void scoreChange(boolean team, int point, String player1, String player2){
		if(team){
			_homeTeam.scoreChange(point, player1, player2);
		}
		else{
			_awayTeam.scoreChange(point, player1, player2);
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
	
	public void setLineOfScrimmage(int value){
		if(value < 0){
			_sideOfField = true;
			
		}
		else{
			_sideOfField = false;
		}
		_lineOfScrimmage = (int)(50-Math.abs(value));
	}
	
	public String getLineOfScrimmage(){
		if(_sideOfField){
			return "Home "+_lineOfScrimmage;
		}
		else{
			return "Away "+_lineOfScrimmage;
		}
	}
	
	public int getLOS_Layout(){
		if(_sideOfField){
			return _lineOfScrimmage;
		}
		else{
			return 100-_lineOfScrimmage;
		}
	}
	public int getToGo(){
		return toGo;
	}
	public void setToGo(int newValue){
		toGo = newValue;
	}
	
	public void setGameStatus(String gameStatus){
		_gameStatus = gameStatus;
	}
	
	public String getGameStatus(){
		return _gameStatus;
	}
	
	public void changePossession(){
		_possession = !_possession;
	}
	
	public boolean getPossession(){
		return _possession;
	}	
}
