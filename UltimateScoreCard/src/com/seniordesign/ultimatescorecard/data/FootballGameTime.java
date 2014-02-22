package com.seniordesign.ultimatescorecard.data;

import com.seniordesign.ultimatescorecard.sqlite.helper.Teams;

import android.util.Log;

public class FootballGameTime extends GameTime{
	private static final long serialVersionUID = 3840132882537431776L;
	private FootballTeam _homeTeam;
	private FootballTeam _awayTeam;
	private String[] _lineOfScrimmage = new String[]{"OWN","0"};
	private int[] _downDistance = new int[]{0,0};
	private boolean _aReturn = false;
		
	public FootballGameTime(Teams home, Teams away){
		_homeTeam = new FootballTeam(home.gettname(), true);
		_awayTeam = new FootballTeam(away.gettname(), false);
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
	
	public void setLineOfScrimmage (int viewNum){
		if (yardsGained(viewNum) >= _downDistance[1] || _downDistance[0] == 0 || isReturned()){
			_downDistance[0] = 1;
			_downDistance[1] = 10;
		}
		else if (_downDistance[0] >= 4){
			changePossession();
			_downDistance[0] = 1;
			_downDistance[1] = 10;
			if(_lineOfScrimmage[0].equals("OWN")) {
				_lineOfScrimmage[0] = "OPP";
			}
			else if(_lineOfScrimmage[0].equals("OPP")){
				_lineOfScrimmage[0] = "OWN";
			}
			else{
				_lineOfScrimmage[0] = "MID";
			}
		}
		else{
			_downDistance[0]++;
			_downDistance[1]-= yardsGained(viewNum);
		}
		
		if(viewNum < 50){
			_lineOfScrimmage[0] = "OPP";
			_lineOfScrimmage[1] = viewNum+"";
		}
		else if (viewNum == 50){
			_lineOfScrimmage[0] = "MID";
			_lineOfScrimmage[1] = "50";
		}
		else{
			_lineOfScrimmage[0] = "OWN";
			_lineOfScrimmage[1] = 100-viewNum+"";
		}
		Log.e("POSSESSION:", getPossession()+"!");
	}
	
	public int getLineOfScrimmage (){
		if(_lineOfScrimmage[0].equals("OPP")){
			return Integer.parseInt(_lineOfScrimmage[1]);
		}
		else if(_lineOfScrimmage[0].equals("MID")){
			return 50;
		}
		else{
			return (Integer.parseInt(_lineOfScrimmage[1])-100)*-1;
		}
	}
	
	public int[] getDownDistance(){
		return _downDistance;
	}
	
	private int yardsGained(int viewNum){
		return getLineOfScrimmage() - viewNum;
	}
	
	public boolean isReturned(){
		return _aReturn;
	}
	
	public void returning (boolean aReturn){
		_aReturn = aReturn;
	}
}
