package com.seniordesign.ultimatescorecard.data.soccer;

import java.io.Serializable;

public class SoccerPlayer implements Serializable{
	private static final long serialVersionUID = -2038938909025112867L;
	private String _name;
	private int _goals;
	private int _assist;
	private int _yellowCard;
	private int _redCard;
	
	public SoccerPlayer (String name){
		_name = name;
	}
	public String getName(){
		return _name;
	}
	public void scoreGoal(){
		_goals++;
	}
	public int getGoals(){
		return _goals;
	}
	public void assisted(){
		_assist++;
	}
	public int getAssists(){
		return _assist;
	}
	public void penaltyYellow(){
		_yellowCard++;
	}
	public int getYellow(){
		return _yellowCard;
	}
	public void penaltyRed(){
		_redCard++;
	}
	public int getRed(){
		return _redCard;
	}
}
