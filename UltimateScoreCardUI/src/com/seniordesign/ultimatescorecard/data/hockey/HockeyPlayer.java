package com.seniordesign.ultimatescorecard.data.hockey;

import java.io.Serializable;

public class HockeyPlayer implements Serializable{
	private static final long serialVersionUID = 5690367601028394271L;
	private String _name;
	private int _goals;
	private int _shotMissed;
	private int _assist;
	private int _saves;
	private int _minors;
	private int _majors;
	private int _misconduct;
	private int _penaltyMins;

	public HockeyPlayer (String name){
		_name = name;
	}
	public String getName(){
		return _name;
	}
	public int getGoals(){
		return _goals;
	}
	public void scoreGoal(){
		_goals++;
	}
	public int getShotMissed(){
		return _shotMissed;
	}
	public void shotMissed(){
		_shotMissed++;
	}
	public int getAssist(){
		return _assist;
	}
	public void assisted(){
		_assist++;
	}
	public int getSaves(){
		return _saves;
	}
	public void saved(){
		_saves++;
	}
	public int getMinors(){
		return _minors;
	}
	public void minorPenalty(){
		_minors++;
	}
	public int getMajors(){
		return _majors;
	}
	public void majorPenalty(){
		_majors++;
	}
	public int getMisconduct(){
		return _misconduct;
	}
	public void misconductPenalty(){
		_misconduct++;
	}
	public int getPenaltyMins(){
		return _penaltyMins;
	}
	public void addPenaltyMins(int minutes){
		_penaltyMins+=minutes;
	}
}
