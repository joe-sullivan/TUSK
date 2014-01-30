package com.seniordesign.ultimatescorecard.data;

import java.io.Serializable;

public class Team implements Serializable{
	private static final long serialVersionUID = -4547516998953968383L;
	protected String _teamName;
	protected String _teamAbbr;
	protected boolean _homeTeam;
	protected int _score = 0; 
	protected int _teamTurnovers = 0;
			
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
	
	public void teamTurnover(){
		_teamTurnovers++;
	}
	
	public int getScore(){
		return _score;
	}
	
	public int getTeamTurnovers(){
		return _teamTurnovers;
	}
}
