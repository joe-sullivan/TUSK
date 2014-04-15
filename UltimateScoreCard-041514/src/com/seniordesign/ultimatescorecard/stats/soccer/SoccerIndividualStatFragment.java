package com.seniordesign.ultimatescorecard.stats.soccer;

import java.util.ArrayList;

import com.seniordesign.ultimatescorecard.R;
import com.seniordesign.ultimatescorecard.sqlite.helper.Players;
import com.seniordesign.ultimatescorecard.sqlite.helper.Teams;
import com.seniordesign.ultimatescorecard.sqlite.soccer.SoccerDatabaseHelper;
import com.seniordesign.ultimatescorecard.sqlite.soccer.SoccerGameStats;
import com.seniordesign.ultimatescorecard.sqlite.soccer.SoccerGames;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SoccerIndividualStatFragment extends Fragment{
	
	private SoccerDatabaseHelper _db;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = (View) inflater.inflate(R.layout.fragment_individual_soccer, container, false);
		view.setBackgroundResource(R.drawable.background_green);

		return view;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		_db = ((SoccerIndividualStatActivity) getActivity())._soccer_db;
		SoccerGames game = ((SoccerIndividualStatActivity) getActivity())._game;
		String name = ((SoccerIndividualStatActivity) getActivity())._name;
		Teams team = ((SoccerIndividualStatActivity) getActivity())._team;
		boolean home = ((SoccerIndividualStatActivity) getActivity())._home;
		ArrayList<Players> players = ((SoccerIndividualStatActivity) getActivity())._players;
		long g_id = ((SoccerIndividualStatActivity) getActivity()).g_id;
		//NEW
		String _player = ((SoccerIndividualStatActivity) getActivity())._player;
        if(_player!=null){
			if(!_player.equals("All Players")){
	        	name = _player;
	        }
        }
		//END NEW
        //AVE
        boolean average = ((SoccerIndividualStatActivity) getActivity())._average;
		
		((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.playerName)).setTextColor(Color.WHITE);
		((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.teamName)).setTextColor(Color.WHITE);
		((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.goalTotal)).setTextColor(Color.WHITE);
		((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.assistTotal)).setTextColor(Color.WHITE);
		((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.shotOnGoalTotal)).setTextColor(Color.WHITE);
		((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.penaltyTotal)).setTextColor(Color.WHITE);
		((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.penaltyTypeTotal)).setTextColor(Color.WHITE);
		((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.goalieTitle)).setTextColor(Color.WHITE);
		((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.goalieStats)).setTextColor(Color.WHITE);

		
		if(name.equals(team.getabbv() + " Stats")){
			if(home){
				
			((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.playerName)).setText(team.getabbv());
			((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.teamName)).setText(team.gettname());
			((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.goalTotal)).setText("Goals: "+game.gethomegoals());
			((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.assistTotal)).setText("Assists: "+game.gethomeast());
			((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.shotOnGoalTotal)).setText("Shots On Goal: "+game.gethomesog());
			((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.penaltyTypeTotal)).setText(
					" Yellow Cards:" + game.gethomeycard() +
					"\n Red Cards: " + game.gethomercard());
			//Goalie Stats
			((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.goalieTitle)).setText("Goalie Stats");
			((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.goalieStats)).setText(
				" Saves:" + game.gethomesaves() +
				"\n Goals Allowed: " + game.gethomegoalsallowed() +
	
				"\n Save %: " + game.gethomesavepercent());
			}
			else{
				((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.playerName)).setText(team.getabbv());
				((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.teamName)).setText(team.gettname());
				((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.goalTotal)).setText("Goals: "+game.getawaygoals());
				((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.assistTotal)).setText("Assists: "+game.getawayast());
				((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.shotOnGoalTotal)).setText("Shots On Goal: "+game.getawaysog());
				((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.penaltyTypeTotal)).setText(
						" Yellow Cards:" + game.getawayycard() +
						"\n Red Cards: " + game.getawayrcard());
				//Goalie Stats
				((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.goalieTitle)).setText("Goalie Stats");
				((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.goalieStats)).setText(
					" Saves:" + game.getawaysaves() +
					"\n Goals Allowed: " + game.getawaygoalsallowed() +
		
					"\n Save %: " + game.getawaysavepercent());
			}
		}
			
		else{
			Players player = null;
			for(Players p: players){
				if(p.getpname().equals(name)){
					player = p;
				}
			}
			
			//AVE
			if(average){
				ArrayList<SoccerGameStats> allStats = (ArrayList<SoccerGameStats>) _db.getPlayerAllGameStats(player.getpid());
				double shots=0,sog=0,goals=0,ast=0,fouls=0,pka=0,pkg=0,offside=0,
						ycard=0,rcard=0,save_opps=0,saves=0,goals_allowed=0;
				for(SoccerGameStats s: allStats){
					shots += s.getshots();
					sog += s.getsog();
					goals += s.getgoals();
					ast += s.getast();
					fouls += s.getfouls();
					pka += s.getpka(); 
					pkg += s.getpkg();
					offside += s.getoffside();
					ycard += s.getycard();
					rcard += s.getrcard();
					save_opps += s.getsaveopps();
					saves += s.getsaves();
					goals_allowed += s.getgoalsallowed();
				}
				
				shots /= allStats.size();
				sog /= allStats.size();
				goals /= allStats.size();
				ast /= allStats.size();
				fouls /= allStats.size();
				pka /= allStats.size(); 
				pkg /= allStats.size();
				offside /= allStats.size();
				ycard /= allStats.size();
				rcard /= allStats.size();
				save_opps /= allStats.size();
				saves /= allStats.size();
				goals_allowed /= allStats.size();
				
			    String savepercent;
		    	if((saves+goals_allowed)>0){
		    		savepercent= String.format("%.3f", saves/(saves+goals_allowed));
		    	}
		    	else{
		    		savepercent= "N/A";
		    	}
			    
				
				((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.playerName)).setText(player.getpname() + " - Average Stats");
				((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.teamName)).setText(team.gettname());
				((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.goalTotal)).setText("Goals: "+String.format("%.3f", goals));
				((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.assistTotal)).setText("Assists: "+String.format("%.3f", ast));
				((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.shotOnGoalTotal)).setText("Shots On Goal: "+String.format("%.3f", sog));
				((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.penaltyTypeTotal)).setText(
						" Yellow Cards:" + String.format("%.3f", ycard) +
						"\n Red Cards: " + String.format("%.3f", rcard));
				//Goalie Stats
				((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.goalieTitle)).setText("Goalie Stats");
				((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.goalieStats)).setText(
					" Saves:" + String.format("%.3f", saves) +
					"\n Goals Allowed: " + String.format("%.3f", goals_allowed) +
		
					"\n Save %: " + savepercent);		
			}
			else{
				
				SoccerGameStats stats = _db.getPlayerGameStats(g_id, player.getpid());
					
				((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.playerName)).setText(player.getpname());
				((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.teamName)).setText(team.gettname());
				((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.goalTotal)).setText("Goals: "+stats.getgoals());
				((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.assistTotal)).setText("Assists: "+stats.getast());
				((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.shotOnGoalTotal)).setText("Shots On Goal: "+stats.getsog());
				((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.penaltyTypeTotal)).setText(
						" Yellow Cards:" + stats.getycard() +
						"\n Red Cards: " + stats.getrcard());
				//Goalie Stats
				((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.goalieTitle)).setText("Goalie Stats");
				((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.goalieStats)).setText(
					" Saves:" + stats.getsaves() +
					"\n Goals Allowed: " + stats.getgoalsallowed() +
		
					"\n Save %: " + stats.getsavepercent());
			}
		}
	}
}
