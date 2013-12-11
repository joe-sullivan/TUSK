package com.seniordesign.ultimatescorecard.data;

import java.io.Serializable;

public class Player implements Serializable{
	private static final long serialVersionUID = 8138078955965364135L;
	private String _name;
	private int _threePtsMade = 0;
	private int _threePtsMiss = 0;
	private int _twoPtsMade = 0;
	private int _twoPtsMiss = 0;
	private int _freeThrowMade = 0;
	private int _freeThrowMiss = 0;
	private int _rebounds = 0;
	private int _assists = 0;
	private int _steals = 0;
	private int _blocks = 0;
	private int _turnovers = 0;
	private int _fouls = 0;
	private int _techFouls = 0;
	private int _flagFouls = 0;
	
	//getting values about a player
	public Player (String name){
		_name = name;
	}
	public String getName(){
		return _name;
	}
	public int getRebounds(){
		return _rebounds;
	}
	public int getAssists(){
		return _assists;
	}
	public int getSteals(){
		return _steals;
	}
	public int getBlocks(){
		return _blocks;
	}
	public int getTurnovers(){
		return _turnovers;
	}
	public int getFTMade(){
		return _freeThrowMade;
	}
	public int getFTMiss(){
		return _freeThrowMiss;
	}
	public int getThreePtsMade(){
		return _threePtsMade;
	}
	public int getThreePtsMiss(){
		return _threePtsMiss;
	}
	public int getTwoPtsMade(){
		return _twoPtsMade;
	}
	public int getTwoPtsMiss(){
		return _twoPtsMiss;
	}
	public int getFouls(){
		return _fouls;
	}
	public int getTechFouls(){
		return _techFouls;
	}
	public int getFlagFouls(){
		return _flagFouls;
	}
	
	//changing the values in a game
	public void madeThree(){
		_threePtsMade++;
	}
	public void madeTwo(){
		_twoPtsMade++;
	}
	public void madeFreeThrow(){
		_freeThrowMade++;
	}	
	public void missThree(){
		_threePtsMiss++;
	}
	public void missTwo(){
		_twoPtsMiss++;
	}	
	public void missFreeThrow(){
		_freeThrowMiss++;
	}	
	public void grabRebound(){
		_rebounds++;
	}
	public void dishAssist(){
		_assists++;
	}
	public void stealsBall(){
		_steals++;
	}
	public void blocksShot(){
		_blocks++;
	}
	public void turnedOver(){
		_turnovers++;
	}
	public void commitFoul(){
		_fouls++;
	}
	public void commitTechFoul(){
		_techFouls++;
	}
	public void commitFlagFoul(){
		_flagFouls++;
	}
	
	//calculated stats
	public int pointScored(){
		return (_threePtsMade*3) + (_twoPtsMade*2) + _freeThrowMade;
	}
	
	public int fieldGoalMade(){
		return _threePtsMade + _twoPtsMade;
	}
	
	public int fieldGoalAttempted(){
		return _threePtsMade + _twoPtsMade + _threePtsMiss + _twoPtsMiss;
	}
	
	public int freeThrowAttempted(){
		return _freeThrowMade + _freeThrowMiss;
	}
	
	public double freeThrowPCT(){
		if(freeThrowAttempted() > 0){
			return (double) _freeThrowMade / freeThrowAttempted();
		}
		else{
			return 0.0;
		}
	}
	
	public double fieldGoalPCT(){
		if(fieldGoalAttempted() > 0){
			return (double) fieldGoalMade() / fieldGoalAttempted();
		}
		else{
			return 0.0;
		}
	}
}
