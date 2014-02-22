package com.seniordesign.ultimatescorecard.data;

import java.io.Serializable;

import com.seniordesign.ultimatescorecard.sqlite.basketball.BasketballDatabaseHelper;
import com.seniordesign.ultimatescorecard.sqlite.helper.Players;

public class BasketballPlayer extends Players implements Serializable{
	private static final long serialVersionUID = 8138078955965364135L;
	private long g_id;
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
	private BasketballDatabaseHelper db;
	
	public BasketballPlayer(){
		super();
	}
	
	public BasketballPlayer(long g_id, String p_name, int p_num){
		super(g_id, p_name, p_num);
	}
	
	public void setgid(long g_id){
		this.g_id = g_id;
	}
	
	public void setdb(BasketballDatabaseHelper db){
		this.db = db;
	}
	
	//getting values about a player
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
		((BasketballDatabaseHelper) db).addStats(g_id, p_id, "pts", 3);
		((BasketballDatabaseHelper) db).addStats(g_id, p_id, "fgm3", 1);
		((BasketballDatabaseHelper) db).addStats(g_id, p_id, "fga3", 1);
		((BasketballDatabaseHelper) db).addStats(g_id, p_id, "fgm", 1);
		((BasketballDatabaseHelper) db).addStats(g_id, p_id, "fga", 1);
	}
	public void madeTwo(){
		((BasketballDatabaseHelper) db).addStats(g_id, p_id, "pts", 2);
		((BasketballDatabaseHelper) db).addStats(g_id, p_id, "fgm", 1);
		((BasketballDatabaseHelper) db).addStats(g_id, p_id, "fga", 1);
	}
	public void madeFreeThrow(){
		((BasketballDatabaseHelper) db).addStats(g_id, p_id, "pts", 1);
		((BasketballDatabaseHelper) db).addStats(g_id, p_id, "ftm", 1);
		((BasketballDatabaseHelper) db).addStats(g_id, p_id, "fta", 1);
	}	
	public void missThree(){
		((BasketballDatabaseHelper) db).addStats(g_id, p_id, "fga3", 1);
		((BasketballDatabaseHelper) db).addStats(g_id, p_id, "fga", 1);
	}
	public void missTwo(){
		((BasketballDatabaseHelper) db).addStats(g_id, p_id, "fga", 1);
	}	
	public void missFreeThrow(){
		((BasketballDatabaseHelper) db).addStats(g_id, p_id, "fta", 1);
	}	
	public void grabDRebound(){
		((BasketballDatabaseHelper) db).addStats(g_id, p_id, "dreb", 1);
	}
	
	public void grabORebound(){
		((BasketballDatabaseHelper) db).addStats(g_id, p_id, "oreb", 1);
	}
	
	public void dishAssist(){
		((BasketballDatabaseHelper) db).addStats(g_id, p_id, "ast", 1);
	}
	public void stealsBall(){
		((BasketballDatabaseHelper) db).addStats(g_id, p_id, "stl", 1);
	}
	public void blocksShot(){
		((BasketballDatabaseHelper) db).addStats(g_id, p_id, "blk", 1);
	}
	public void turnedOver(){
		((BasketballDatabaseHelper) db).addStats(g_id, p_id, "turnover", 1);
	}
	public void commitFoul(){
		((BasketballDatabaseHelper) db).addStats(g_id, p_id, "pf", 1);
	}
	public void commitTechFoul(){
		((BasketballDatabaseHelper) db).addStats(g_id, p_id, "tech", 1);
	}
	public void commitFlagFoul(){
		((BasketballDatabaseHelper) db).addStats(g_id, p_id, "flagrant", 1);
	}
	
	//calculated stats
	
	public int pointScored(){
		return 	((BasketballDatabaseHelper) db).getPlayerGameStat(g_id, p_id, "pts");
	}
	
	
	
	
/*
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
	*/
}
