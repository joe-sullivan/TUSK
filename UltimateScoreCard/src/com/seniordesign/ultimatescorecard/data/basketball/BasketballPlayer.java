package com.seniordesign.ultimatescorecard.data.basketball;

import java.io.Serializable;
import com.seniordesign.ultimatescorecard.sqlite.basketball.BasketballDatabaseHelper;
import com.seniordesign.ultimatescorecard.sqlite.helper.Players;

public class BasketballPlayer extends Players implements Serializable{
	private static final long serialVersionUID = 8138078955965364135L;
	private long g_id;
	private BasketballDatabaseHelper db;
	private boolean home;
	
	public BasketballPlayer(){
		super();
	}
	
	public BasketballPlayer(long g_id, String p_name, int p_num){
		super(g_id, p_name, p_num);
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
}
