package com.seniordesign.ultimatescorecard.data.basketball;

import java.io.Serializable;
import java.util.ArrayList;

import com.seniordesign.ultimatescorecard.data.StatData;
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
		ArrayList<StatData> _pstats = new ArrayList<StatData>();
		ArrayList<StatData> _tstats = new ArrayList<StatData>();			

		_pstats.add(new StatData(g_id, p_id, "pts", 3));
		_pstats.add(new StatData(g_id, p_id, "fgm3", 1));
		_pstats.add(new StatData(g_id, p_id, "fga3", 1));
		_pstats.add(new StatData(g_id, p_id, "fgm", 1));
		_pstats.add(new StatData(g_id, p_id, "fga", 1));
		
		if(home){
			_tstats.add(new StatData(g_id,0, "home_pts", 3));
			_tstats.add(new StatData(g_id,0,"home_fgm3", 1));
			_tstats.add(new StatData(g_id,0, "home_fga3", 1));
			_tstats.add(new StatData(g_id,0, "home_fgm", 1));
			_tstats.add(new StatData(g_id,0, "home_fga", 1));
		}
		else{
			_tstats.add(new StatData(g_id,0, "away_pts", 3));
			_tstats.add(new StatData(g_id,0,"away_fgm3", 1));
			_tstats.add(new StatData(g_id,0, "away_fga3", 1));
			_tstats.add(new StatData(g_id,0, "away_fgm", 1));
			_tstats.add(new StatData(g_id,0, "away_fga", 1));
		}
		
		db.addStats(_pstats);
		db.addTeamStats(_tstats);
	}
	public void madeTwo(){
		ArrayList<StatData> _pstats = new ArrayList<StatData>();
		ArrayList<StatData> _tstats = new ArrayList<StatData>();
		
		_pstats.add(new StatData(g_id, p_id, "pts", 2));
		_pstats.add(new StatData(g_id, p_id, "fgm", 1));
		_pstats.add(new StatData(g_id, p_id, "fga", 1));
		
		if(home){
			_tstats.add(new StatData(g_id,0, "home_pts", 2));
			_tstats.add(new StatData(g_id,0, "home_fgm", 1));
			_tstats.add(new StatData(g_id,0, "home_fga", 1));
		}
		else{
			_tstats.add(new StatData(g_id,0, "away_pts", 2));
			_tstats.add(new StatData(g_id,0, "away_fgm", 1));
			_tstats.add(new StatData(g_id,0, "away_fga", 1));
		}
		
		db.addStats(_pstats);
		db.addTeamStats(_tstats);
	}
	public void madeFreeThrow(){
		ArrayList<StatData> _pstats = new ArrayList<StatData>();
		ArrayList<StatData> _tstats = new ArrayList<StatData>();
		
		_pstats.add(new StatData(g_id, p_id, "pts", 1));
		_pstats.add(new StatData(g_id, p_id, "ftm", 1));
		_pstats.add(new StatData(g_id, p_id, "fta", 1));
		
		if(home){
			_tstats.add(new StatData(g_id,0, "home_pts", 1));
			_tstats.add(new StatData(g_id,0, "home_ftm", 1));
			_tstats.add(new StatData(g_id,0, "home_fta", 1));
		}
		else{
			_tstats.add(new StatData(g_id,0, "away_pts", 1));
			_tstats.add(new StatData(g_id,0, "away_ftm", 1));
			_tstats.add(new StatData(g_id,0, "away_fta", 1));
		}
		
		db.addStats(_pstats);
		db.addTeamStats(_tstats);
	}	
	
	public void missThree(){
		ArrayList<StatData> _pstats = new ArrayList<StatData>();
		ArrayList<StatData> _tstats = new ArrayList<StatData>();
		
		_pstats.add(new StatData(g_id, p_id, "fga3", 1));
		_pstats.add(new StatData(g_id, p_id, "fga", 1));
		
		if(home){
			_tstats.add(new StatData(g_id,0,"home_fga3", 1));
			_tstats.add(new StatData(g_id,0, "home_fga", 1));
		}
		else{
			_tstats.add(new StatData(g_id,0, "away_fga3", 1));
			_tstats.add(new StatData(g_id,0,"away_fga", 1));
		}
		
		db.addStats(_pstats);
		db.addTeamStats(_tstats);
	}
	
	public void missTwo(){
		ArrayList<StatData> _pstats = new ArrayList<StatData>();
		ArrayList<StatData> _tstats = new ArrayList<StatData>();
		
		_pstats.add(new StatData(g_id, p_id, "fga", 1));
		
		if(home){
			_tstats.add(new StatData(g_id,0, "home_fga", 1));
		}
		else{
			_tstats.add(new StatData(g_id,0,"away_fga", 1));
		}
		
		db.addStats(_pstats);
		db.addTeamStats(_tstats);
	}	
	
	public void missFreeThrow(){
		ArrayList<StatData> _pstats = new ArrayList<StatData>();
		ArrayList<StatData> _tstats = new ArrayList<StatData>();
		
		_pstats.add(new StatData(g_id, p_id, "fta", 1));
		
		if(home){
			_tstats.add(new StatData(g_id,0, "home_fta", 1));
		}
		else{
			_tstats.add(new StatData(g_id,0,"away_fta", 1));
		}
		
		db.addStats(_pstats);
		db.addTeamStats(_tstats);
	}	
	
	public void grabDRebound(){
		ArrayList<StatData> _pstats = new ArrayList<StatData>();
		ArrayList<StatData> _tstats = new ArrayList<StatData>();
		
		_pstats.add(new StatData(g_id, p_id, "dreb", 1));
		
		if(home){
			_tstats.add(new StatData(g_id,0,"home_dreb", 1));
		}
		else{
			_tstats.add(new StatData(g_id,0, "away_dreb", 1));
		}
		
		db.addStats(_pstats);
		db.addTeamStats(_tstats);
	}
	
	public void grabORebound(){
		ArrayList<StatData> _pstats = new ArrayList<StatData>();
		ArrayList<StatData> _tstats = new ArrayList<StatData>();
		
		_pstats.add(new StatData(g_id, p_id, "oreb", 1));
		
		if(home){
			_tstats.add(new StatData(g_id,0,"home_oreb", 1));
		}
		else{
			_tstats.add(new StatData(g_id,0, "away_oreb", 1));
		}
		
		db.addStats(_pstats);
		db.addTeamStats(_tstats);
	}
	
	public void dishAssist(){
		ArrayList<StatData> _pstats = new ArrayList<StatData>();
		ArrayList<StatData> _tstats = new ArrayList<StatData>();
		
		_pstats.add(new StatData(g_id, p_id, "ast", 1));
		
		if(home){
			_tstats.add(new StatData(g_id,0,"home_ast", 1));
		}
		else{
			_tstats.add(new StatData(g_id,0, "away_ast", 1));
		}
		
		db.addStats(_pstats);
		db.addTeamStats(_tstats);
	}
	
	public void stealsBall(){
		ArrayList<StatData> _pstats = new ArrayList<StatData>();
		ArrayList<StatData> _tstats = new ArrayList<StatData>();
		
		_pstats.add(new StatData(g_id, p_id, "stl", 1));
		
		if(home){
			_tstats.add(new StatData(g_id,0,"home_stl", 1));
		}
		else{
			_tstats.add(new StatData(g_id,0, "away_stl", 1));
		}
		
		db.addStats(_pstats);
		db.addTeamStats(_tstats);
	}
	
	public void blocksShot(){
		ArrayList<StatData> _pstats = new ArrayList<StatData>();
		ArrayList<StatData> _tstats = new ArrayList<StatData>();
		
		_pstats.add(new StatData(g_id, p_id, "blk", 1));
		
		if(home){
			_tstats.add(new StatData(g_id,0,"home_blk", 1));
		}
		else{
			_tstats.add(new StatData(g_id,0, "away_blk", 1));
		}
		
		db.addStats(_pstats);
		db.addTeamStats(_tstats);
	}
	
	public void turnedOver(){
		ArrayList<StatData> _pstats = new ArrayList<StatData>();
		ArrayList<StatData> _tstats = new ArrayList<StatData>();
		
		_pstats.add(new StatData(g_id, p_id, "turnover", 1));
		
		if(home){
			_tstats.add(new StatData(g_id,0,"home_turnover", 1));
		}
		else{
			_tstats.add(new StatData(g_id,0, "away_turnover", 1));
		}
		
		db.addStats(_pstats);
		db.addTeamStats(_tstats);
	}
	
	public void commitFoul(){
		ArrayList<StatData> _pstats = new ArrayList<StatData>();
		ArrayList<StatData> _tstats = new ArrayList<StatData>();
		
		_pstats.add(new StatData(g_id, p_id, "pf", 1));
		
		if(home){
			_tstats.add(new StatData(g_id,0,"home_pf", 1));
		}
		else{
			_tstats.add(new StatData(g_id,0, "away_pf", 1));
		}
		
		db.addStats(_pstats);
		db.addTeamStats(_tstats);
	}
	
	public void commitTechFoul(){
		ArrayList<StatData> _pstats = new ArrayList<StatData>();
		ArrayList<StatData> _tstats = new ArrayList<StatData>();
		
		_pstats.add(new StatData(g_id, p_id, "tech", 1));
		
		if(home){
			_tstats.add(new StatData(g_id,0,"home_tech", 1));
		}
		else{
			_tstats.add(new StatData(g_id,0, "away_tech", 1));
		}
		
		db.addStats(_pstats);
		db.addTeamStats(_tstats);
	}
	
	public void commitFlagFoul(){
		ArrayList<StatData> _pstats = new ArrayList<StatData>();
		ArrayList<StatData> _tstats = new ArrayList<StatData>();
		
		_pstats.add(new StatData(g_id, p_id, "flagrant", 1));
		
		if(home){
			_tstats.add(new StatData(g_id,0,"home_flagrant", 1));
		}
		else{
			_tstats.add(new StatData(g_id,0, "away_flagrant", 1));
		}
		
		db.addStats(_pstats);
		db.addTeamStats(_tstats);
	}
	
	//calculated stats
	
	public int pointScored(){
		return 	db.getPlayerGameStat(g_id, p_id, "pts");
	}
}
