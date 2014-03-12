package com.seniordesign.ultimatescorecard.data.soccer;

import java.io.Serializable;

import com.seniordesign.ultimatescorecard.sqlite.helper.Players;
import com.seniordesign.ultimatescorecard.sqlite.soccer.SoccerDatabaseHelper;

public class SoccerPlayer extends Players implements Serializable{
	private static final long serialVersionUID = -2038938909025112867L;
	private long g_id;
	private SoccerDatabaseHelper db;
	private boolean home;
	
	public SoccerPlayer(){
		super();
	}
	public SoccerPlayer(long g_id, String p_name, int p_num){
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
	
	public void setdb(SoccerDatabaseHelper db){
		this.db = db;
	}	
	
	public void scoreGoal(){
		db.addStats(g_id, p_id, "shots", 1);
		db.addStats(g_id, p_id, "sog", 1);
		db.addStats(g_id, p_id, "goals", 1);
		
		if(home){
			db.addTeamStats(g_id, "home_shots", 1);
			db.addTeamStats(g_id, "home_sog", 1);
			db.addTeamStats(g_id, "home_goals", 1);
		}
		else{
			db.addTeamStats(g_id, "away_shots", 1);
			db.addTeamStats(g_id, "away_sog", 1);
			db.addTeamStats(g_id, "away_goals", 1);
		}
	}
	
	public void shotOnGoalMissed(){
		db.addStats(g_id, p_id, "shots", 1);
		db.addStats(g_id, p_id, "sog", 1);
		
		if(home){
			db.addTeamStats(g_id, "home_shots", 1);
			db.addTeamStats(g_id, "home_sog", 1);
		}
		else{
			db.addTeamStats(g_id, "away_shots", 1);
			db.addTeamStats(g_id, "away_sog", 1);
		}
	}
	
	public void shotMissed(){		
		if(home){
			db.addTeamStats(g_id, "home_shots", 1);
		}
		else{
			db.addTeamStats(g_id, "away_shots", 1);
		}
	}
	public void assisted(){
		db.addStats(g_id, p_id, "ast", 1);
		
		if(home){
			db.addTeamStats(g_id, "home_ast", 1);
		}
		else{
			db.addTeamStats(g_id, "away_ast", 1);
		}
	}
	public void saved(){
		db.addStats(g_id, p_id, "saves", 1);
		
		if(home){
			db.addTeamStats(g_id, "home_saves", 1);
		}
		else{
			db.addTeamStats(g_id, "away_saves", 1);
		}
	}
	public void goalAllowed(){
		db.addStats(g_id, p_id, "goals_allowed", 1);
		
		if(home){
			db.addTeamStats(g_id, "home_goals_allowed", 1);
		}
		else{
			db.addTeamStats(g_id, "away_goals_allowed", 1);
		}
	}
	public void foul(){
		db.addStats(g_id, p_id, "fouls", 1);
		
		if(home){
			db.addTeamStats(g_id, "home_fouls", 1);
		}
		else{
			db.addTeamStats(g_id, "away_fouls", 1);
		}
	}
	public void penaltyYellow(){
		db.addStats(g_id, p_id, "ycard", 1);
		
		if(home){
			db.addTeamStats(g_id, "home_ycard", 1);
		}
		else{
			db.addTeamStats(g_id, "away_ycard", 1);
		}
	}
	public void penaltyRed(){
		db.addStats(g_id, p_id, "rcard", 1);
		
		if(home){
			db.addTeamStats(g_id, "home_rcard", 1);
		}
		else{
			db.addTeamStats(g_id, "away_rcard", 1);
		}
	}

}
