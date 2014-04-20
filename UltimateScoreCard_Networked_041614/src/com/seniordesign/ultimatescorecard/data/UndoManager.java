package com.seniordesign.ultimatescorecard.data;

import java.util.ArrayList;
import java.util.Stack;

import android.widget.RelativeLayout;

import com.seniordesign.ultimatescorecard.sqlite.helper.DatabaseHelper;

public class UndoManager {
	private Stack<UndoInstance> _undos, _redos;
	private DatabaseHelper _db;
	private RelativeLayout _homeLayout, _awayLayout;
	private Team _homeTeam, _awayTeam;
	
	public UndoManager(DatabaseHelper db, Team home, Team away){
		_undos = new Stack<UndoInstance>();
		_redos = new Stack<UndoInstance>();
		_db = db;
		_homeTeam = home;
		_awayTeam = away;
	}
	
	public void addInstance(UndoInstance instance){
		_undos.push(instance);
		_redos.removeAllElements();
	}
	
	public void setLayouts(RelativeLayout homeLayout, RelativeLayout awayLayout){
		_homeLayout = homeLayout;
		_awayLayout = awayLayout;
	}
	
	public void undo(){
		if(!_undos.isEmpty()){
			UndoInstance instance = _undos.pop();
			
			//shots
			if(instance.getiv()!=null){
				_db.deleteShot(instance.getshot().getshotid());
				if(instance.gethome()){
					_homeLayout.removeView(instance.getiv());
				}
				else{
					_awayLayout.removeView(instance.getiv());
				}
			}
			
			//playbyplay
			_db.deletePlayByPlay(instance.getpbp().getaid());
			
			//stats
			for(StatData stat: instance.getpstats()){
				stat.setvalue(-1*stat.getvalue());
			}
			_db.addStats(instance.getpstats());
			for(StatData stat: instance.gettstats()){
				if(stat.getstat().equals("home_pts") || stat.getstat().equals("home_goals")){
					_homeTeam.increaseScore(-1*stat.getvalue());
				}
				else if(stat.getstat().equals("away_pts") || stat.getstat().equals("away_goals")){
					_awayTeam.increaseScore(-1*stat.getvalue());
				}
				stat.setvalue(-1*stat.getvalue());
			}	
			_db.addTeamStats(instance.gettstats());		
			_redos.push(instance);
		}
	}
	
	public void redo(){
		if(!_redos.isEmpty()){
			UndoInstance instance = _redos.pop();
			
			//shots
			if(instance.getiv()!=null){
				_db.createShot(instance.getshot());
				if(instance.gethome()){
					_homeLayout.addView(instance.getiv());
				}
				else{
					_awayLayout.addView(instance.getiv());
				}
			}
			
			//playbyplay
			_db.createPlayByPlay(instance.getpbp());
			
			//stats
			for(StatData stat: instance.getpstats()){
				stat.setvalue(-1*stat.getvalue());
			}
			_db.addStats(instance.getpstats());
			for(StatData stat: instance.gettstats()){
				if(stat.getstat().equals("home_pts") || stat.getstat().equals("home_goals")){
					_homeTeam.increaseScore(-1*stat.getvalue());
				}
				else if(stat.getstat().equals("away_pts") || stat.getstat().equals("away_goals")){
					_awayTeam.increaseScore(-1*stat.getvalue());
				}
				stat.setvalue(-1*stat.getvalue());
			}	
			_db.addTeamStats(instance.gettstats());		

			_undos.push(instance);
		}
	}
}
