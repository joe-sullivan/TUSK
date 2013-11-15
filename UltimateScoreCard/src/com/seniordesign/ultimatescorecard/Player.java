package com.seniordesign.ultimatescorecard;

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
	
	//getting values about a player
	public Player (String name){
		_name = name;
	}
	public String getName(){
		return _name;
	}
	public int getPoints(){
		return (_threePtsMade *3) + (_twoPtsMade *2) + _freeThrowMade;
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
}
