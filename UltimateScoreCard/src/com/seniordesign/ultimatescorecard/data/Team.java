package com.seniordesign.ultimatescorecard.data;

import java.io.Serializable;

public class Team implements Serializable{
	private static final long serialVersionUID = -4547516998953968383L;
	protected String _teamName;
	protected String _teamAbbr;
	protected boolean _homeTeam;
	protected int _score = 0; 
	
	public Team (String teamName, boolean homeTeam){
		_homeTeam = homeTeam;
		_teamName = teamName;
	}
			
	public String getTeamAbbr(){
		return _teamAbbr;
	}
	
	public String getTeamName(){
		return _teamName;
	}
	
	public boolean isHomeTeam(){
		return _homeTeam;
	}
	
	public void increaseScore(int points){
		_score += points;
	}	
		
	public int getScore(){
		return _score;
	}
}
