package com.seniordesign.ultimatescorecard.data.soccer;

import java.io.Serializable;
import java.util.ArrayList;

import com.seniordesign.ultimatescorecard.data.StatData;
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
		ArrayList<StatData> _pstats = new ArrayList<StatData>();
		ArrayList<StatData> _tstats = new ArrayList<StatData>();
		
		_pstats.add(new StatData(g_id, p_id, "shots", 1));
		_pstats.add(new StatData(g_id, p_id, "sog", 1));
		_pstats.add(new StatData(g_id, p_id, "goals", 1));
		
		if(home){
			_tstats.add(new StatData(g_id, 0, "home_shots", 1));
			_tstats.add(new StatData(g_id, 0, "home_sog", 1));
			_tstats.add(new StatData(g_id, 0, "home_goals", 1));
		}
		else{
			_tstats.add(new StatData(g_id, 0, "away_shots", 1));
			_tstats.add(new StatData(g_id, 0, "away_sog", 1));
			_tstats.add(new StatData(g_id, 0, "away_goals", 1));
		}
		
		db.addStats(_pstats);
		db.addTeamStats(_tstats);
	}
	
	public void shotOnGoalMissed(){
		ArrayList<StatData> _pstats = new ArrayList<StatData>();
		ArrayList<StatData> _tstats = new ArrayList<StatData>();
		
		_pstats.add(new StatData(g_id, p_id, "shots", 1));
		_pstats.add(new StatData(g_id, p_id, "sog", 1));
		
		if(home){
			_tstats.add(new StatData(g_id, 0, "home_shots", 1));
			_tstats.add(new StatData(g_id, 0, "home_sog", 1));
		}
		else{
			_tstats.add(new StatData(g_id, 0, "away_shots", 1));
			_tstats.add(new StatData(g_id, 0, "away_sog", 1));
		}
		
		db.addStats(_pstats);
		db.addTeamStats(_tstats);
	}
	
	public void shotMissed(){	
		ArrayList<StatData> _pstats = new ArrayList<StatData>();
		ArrayList<StatData> _tstats = new ArrayList<StatData>();
		
		_pstats.add(new StatData(g_id, p_id, "shots", 1));

		if(home){
			_tstats.add(new StatData(g_id, 0, "home_shots", 1));
		}
		else{
			_tstats.add(new StatData(g_id, 0, "away_shots", 1));
		}
		
		db.addStats(_pstats);
		db.addTeamStats(_tstats);
	}
	public void assisted(){
		ArrayList<StatData> _pstats = new ArrayList<StatData>();
		ArrayList<StatData> _tstats = new ArrayList<StatData>();
		
		_pstats.add(new StatData(g_id, p_id, "ast", 1));
		
		if(home){
			_tstats.add(new StatData(g_id, 0, "home_ast", 1));
		}
		else{
			_tstats.add(new StatData(g_id, 0, "away_ast", 1));
		}
		
		db.addStats(_pstats);
		db.addTeamStats(_tstats);
	}
	public void saved(){
		ArrayList<StatData> _pstats = new ArrayList<StatData>();
		ArrayList<StatData> _tstats = new ArrayList<StatData>();
		
		_pstats.add(new StatData(g_id, p_id, "saves", 1));
		
		if(home){
			_tstats.add(new StatData(g_id, 0, "home_saves", 1));
		}
		else{
			_tstats.add(new StatData(g_id, 0, "away_saves", 1));
		}
		
		db.addStats(_pstats);
		db.addTeamStats(_tstats);
	}
	public void goalAllowed(){
		ArrayList<StatData> _pstats = new ArrayList<StatData>();
		ArrayList<StatData> _tstats = new ArrayList<StatData>();
		
		_pstats.add(new StatData(g_id, p_id, "goals_allowed", 1));
		
		if(home){
			_tstats.add(new StatData(g_id, 0, "home_goals_allowed", 1));
		}
		else{
			_tstats.add(new StatData(g_id, 0, "away_goals_allowed", 1));
		}
		
		db.addStats(_pstats);
		db.addTeamStats(_tstats);
	}
	public void foul(){
		ArrayList<StatData> _pstats = new ArrayList<StatData>();
		ArrayList<StatData> _tstats = new ArrayList<StatData>();
		
		_pstats.add(new StatData(g_id, p_id, "fouls", 1));
		
		if(home){
			_tstats.add(new StatData(g_id, 0, "home_fouls", 1));
		}
		else{
			_tstats.add(new StatData(g_id, 0, "away_fouls", 1));
		}
		
		db.addStats(_pstats);
		db.addTeamStats(_tstats);
	}
	public void penaltyYellow(){
		ArrayList<StatData> _pstats = new ArrayList<StatData>();
		ArrayList<StatData> _tstats = new ArrayList<StatData>();
		
		_pstats.add(new StatData(g_id, p_id, "ycard", 1));
		
		if(home){
			_tstats.add(new StatData(g_id, 0, "home_ycard", 1));
		}
		else{
			_tstats.add(new StatData(g_id, 0, "away_ycard", 1));
		}
		
		db.addStats(_pstats);
		db.addTeamStats(_tstats);
	}
	public void penaltyRed(){
		ArrayList<StatData> _pstats = new ArrayList<StatData>();
		ArrayList<StatData> _tstats = new ArrayList<StatData>();
		
		_pstats.add(new StatData(g_id, p_id, "rcard", 1));
		
		if(home){
			_tstats.add(new StatData(g_id, 0, "home_rcard", 1));
		}
		else{
			_tstats.add(new StatData(g_id, 0, "away_rcard", 1));
		}
		
		db.addStats(_pstats);
		db.addTeamStats(_tstats);
	}

}
