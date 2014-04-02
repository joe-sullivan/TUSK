package com.seniordesign.ultimatescorecard.data.hockey;

import java.io.Serializable;
import java.util.ArrayList;

import com.seniordesign.ultimatescorecard.data.StatData;
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
	public void minorPenalty(){
		ArrayList<StatData> _pstats = new ArrayList<StatData>();
		ArrayList<StatData> _tstats = new ArrayList<StatData>();
		
		_pstats.add(new StatData(g_id, p_id, "pen_minor", 1));
		
		if(home){
			_tstats.add(new StatData(g_id, 0, "home_pen_minor", 1));
		}
		else{
			_tstats.add(new StatData(g_id, 0, "away_pen_minor", 1));
		}
		
		db.addStats(_pstats);
		db.addTeamStats(_tstats);
	}
	public void majorPenalty(){
		ArrayList<StatData> _pstats = new ArrayList<StatData>();
		ArrayList<StatData> _tstats = new ArrayList<StatData>();
		
		_pstats.add(new StatData(g_id, p_id, "pen_major", 1));
		
		if(home){
			_tstats.add(new StatData(g_id, 0, "home_pen_major", 1));
		}
		else{
			_tstats.add(new StatData(g_id, 0, "away_pen_major", 1));
		}
		
		db.addStats(_pstats);
		db.addTeamStats(_tstats);
	}
	public void misconductPenalty(){
		ArrayList<StatData> _pstats = new ArrayList<StatData>();
		ArrayList<StatData> _tstats = new ArrayList<StatData>();
		
		_pstats.add(new StatData(g_id, p_id, "pen_misconduct", 1));
		
		if(home){
			_tstats.add(new StatData(g_id, 0, "home_pen_misconduct", 1));
		}
		else{
			_tstats.add(new StatData(g_id, 0, "away_pen_misconduct", 1));
		}
		
		db.addStats(_pstats);
		db.addTeamStats(_tstats);
	}
	
}
