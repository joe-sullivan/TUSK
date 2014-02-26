package com.seniordesign.ultimatescorecard.data.basketball;

import java.io.Serializable;

import android.util.Log;

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
	private boolean home;
	
	public BasketballPlayer(){
		super();
	}
	
	public BasketballPlayer(long g_id, String p_name, int p_num){
		super(g_id, p_name, p_num);
		Log.d("hi", db.getGame(g_id).gethomeid() + " == " +t_id);

	}
	
	public void setgid(long g_id){
		this.g_id = g_id;
		
		if(db.getGame(g_id).gethomeid()==t_id){
			home=true;
		}
		else{
			home=false;
		}
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
		db.addStats(g_id, p_id, "pts", 3);
		db.addStats(g_id, p_id, "fgm3", 1);
		db.addStats(g_id, p_id, "fga3", 1);
		db.addStats(g_id, p_id, "fgm", 1);
		db.addStats(g_id, p_id, "fga", 1);
		
		if(home){
			db.addTeamStats(g_id, "home_pts", 3);
			db.addTeamStats(g_id,"home_fgm3", 1);
			db.addTeamStats(g_id, "home_fga3", 1);
			db.addTeamStats(g_id, "home_fgm", 1);
			db.addTeamStats(g_id, "home_fga", 1);
		}
		else{
			db.addTeamStats(g_id, "away_pts", 3);
			db.addTeamStats(g_id,"away_fgm3", 1);
			db.addTeamStats(g_id, "away_fga3", 1);
			db.addTeamStats(g_id, "away_fgm", 1);
			db.addTeamStats(g_id, "away_fga", 1);
		}
	}
	public void madeTwo(){
		db.addStats(g_id, p_id, "pts", 2);
		db.addStats(g_id, p_id, "fgm", 1);
		db.addStats(g_id, p_id, "fga", 1);
		
		if(home){
			db.addTeamStats(g_id, "home_pts", 2);
			db.addTeamStats(g_id, "home_fgm", 1);
			db.addTeamStats(g_id, "home_fga", 1);
		}
		else{
			db.addTeamStats(g_id, "away_pts", 2);
			db.addTeamStats(g_id, "away_fgm", 1);
			db.addTeamStats(g_id, "away_fga", 1);
		}
	}
	public void madeFreeThrow(){
		db.addStats(g_id, p_id, "pts", 1);
		db.addStats(g_id, p_id, "ftm", 1);
		db.addStats(g_id, p_id, "fta", 1);
		
		if(home){
			db.addTeamStats(g_id, "home_pts", 1);
			db.addTeamStats(g_id, "home_ftm", 1);
			db.addTeamStats(g_id, "home_fta", 1);
		}
		else{
			db.addTeamStats(g_id, "away_pts", 1);
			db.addTeamStats(g_id, "away_ftm", 1);
			db.addTeamStats(g_id, "away_fta", 1);
		}
	}	
	public void missThree(){
		db.addStats(g_id, p_id, "fga3", 1);
		db.addStats(g_id, p_id, "fga", 1);
		
		if(home){
			db.addTeamStats(g_id,"home_fga3", 1);
			db.addTeamStats(g_id, "home_fga", 1);
		}
		else{
			db.addTeamStats(g_id, "away_fga3", 1);
			db.addTeamStats(g_id,"away_fga", 1);
		}
	}
	public void missTwo(){
		db.addStats(g_id, p_id, "fga", 1);
		
		if(home){
			db.addTeamStats(g_id, "home_fga", 1);
		}
		else{
			db.addTeamStats(g_id,"away_fga", 1);
		}
		
	}	
	public void missFreeThrow(){
		db.addStats(g_id, p_id, "fta", 1);
		
		if(home){
			db.addTeamStats(g_id, "home_fta", 1);
		}
		else{
			db.addTeamStats(g_id,"away_fta", 1);
		}
	}	
	public void grabDRebound(){
		db.addStats(g_id, p_id, "dreb", 1);
		
		if(home){
			db.addTeamStats(g_id,"home_dreb", 1);
		}
		else{
			db.addTeamStats(g_id, "away_dreb", 1);
		}
	}
	
	public void grabORebound(){
		db.addStats(g_id, p_id, "oreb", 1);
		
		if(home){
			db.addTeamStats(g_id,"home_oreb", 1);
		}
		else{
			db.addTeamStats(g_id, "away_oreb", 1);
		}
	}
	
	public void dishAssist(){
		db.addStats(g_id, p_id, "ast", 1);
		
		if(home){
			db.addTeamStats(g_id,"home_ast", 1);
		}
		else{
			db.addTeamStats(g_id, "away_ast", 1);
		}
	}
	public void stealsBall(){
		db.addStats(g_id, p_id, "stl", 1);
		
		if(home){
			db.addTeamStats(g_id,"home_stl", 1);
		}
		else{
			db.addTeamStats(g_id, "away_stl", 1);
		}
	}
	public void blocksShot(){
		db.addStats(g_id, p_id, "blk", 1);
		
		if(home){
			db.addTeamStats(g_id,"home_blk", 1);
		}
		else{
			db.addTeamStats(g_id, "away_blk", 1);
		}
	}
	public void turnedOver(){
		db.addStats(g_id, p_id, "turnover", 1);
		
		if(home){
			db.addTeamStats(g_id,"home_turnover", 1);
		}
		else{
			db.addTeamStats(g_id, "away_turnover", 1);
		}
	}
	public void commitFoul(){
		db.addStats(g_id, p_id, "pf", 1);
		
		if(home){
			db.addTeamStats(g_id,"home_pf", 1);
		}
		else{
			db.addTeamStats(g_id, "away_pf", 1);
		}
	}
	public void commitTechFoul(){
		db.addStats(g_id, p_id, "tech", 1);
		
		if(home){
			db.addTeamStats(g_id,"home_tech", 1);
		}
		else{
			db.addTeamStats(g_id, "away_tech", 1);
		}
	}
	public void commitFlagFoul(){
		db.addStats(g_id, p_id, "flagrant", 1);
		
		if(home){
			db.addTeamStats(g_id,"home_flagrant", 1);
		}
		else{
			db.addTeamStats(g_id, "away_flagrant", 1);
		}
	}
	
	//calculated stats
	
	public int pointScored(){
		return 	db.getPlayerGameStat(g_id, p_id, "pts");
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
