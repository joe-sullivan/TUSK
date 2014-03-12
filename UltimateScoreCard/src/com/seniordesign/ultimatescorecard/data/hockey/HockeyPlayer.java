package com.seniordesign.ultimatescorecard.data.hockey;

import java.io.Serializable;
import com.seniordesign.ultimatescorecard.sqlite.helper.Players;
import com.seniordesign.ultimatescorecard.sqlite.hockey.HockeyDatabaseHelper;

public class HockeyPlayer extends Players implements Serializable{
	private static final long serialVersionUID = 5690367601028394271L;
	private long g_id;
	private HockeyDatabaseHelper db;
	private boolean home;

	public HockeyPlayer (){
		super();
	}
	public HockeyPlayer(long g_id, String p_name, int p_num){
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
	
	public void setdb(HockeyDatabaseHelper db){
		this.db = db;
	}	
	
	//databases
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
		db.addStats(g_id, p_id, "shots", 1);
		
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
	public void minorPenalty(){
		db.addStats(g_id, p_id, "pen_minor", 1);
		
		if(home){
			db.addTeamStats(g_id, "home_pen_minor", 1);
		}
		else{
			db.addTeamStats(g_id, "away_pen_minor", 1);
		}
	}
	public void majorPenalty(){
		db.addStats(g_id, p_id, "pen_major", 1);
		
		if(home){
			db.addTeamStats(g_id, "home_pen_major", 1);
		}
		else{
			db.addTeamStats(g_id, "away_pen_major", 1);
		}
	}
	public void misconductPenalty(){
		db.addStats(g_id, p_id, "pen_misconduct", 1);
		
		if(home){
			db.addTeamStats(g_id, "home_pen_misconduct", 1);
		}
		else{
			db.addTeamStats(g_id, "away_pen_misconduct", 1);
		}
	}
	
}
