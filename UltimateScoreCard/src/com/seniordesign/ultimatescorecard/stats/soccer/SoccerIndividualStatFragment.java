package com.seniordesign.ultimatescorecard.stats.soccer;

import java.util.ArrayList;

import com.seniordesign.ultimatescorecard.R;
import com.seniordesign.ultimatescorecard.sqlite.helper.Players;
import com.seniordesign.ultimatescorecard.sqlite.helper.Teams;
import com.seniordesign.ultimatescorecard.sqlite.soccer.SoccerDatabaseHelper;
import com.seniordesign.ultimatescorecard.sqlite.soccer.SoccerGameStats;
import com.seniordesign.ultimatescorecard.sqlite.soccer.SoccerGames;

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
