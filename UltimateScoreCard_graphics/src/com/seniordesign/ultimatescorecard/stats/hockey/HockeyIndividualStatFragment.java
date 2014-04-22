package com.seniordesign.ultimatescorecard.stats.hockey;

import java.util.ArrayList;

import com.seniordesign.ultimatescorecard.R;
import com.seniordesign.ultimatescorecard.sqlite.basketball.BasketballGameStats;
import com.seniordesign.ultimatescorecard.sqlite.helper.Games;
import com.seniordesign.ultimatescorecard.sqlite.helper.Players;
import com.seniordesign.ultimatescorecard.sqlite.helper.Teams;
import com.seniordesign.ultimatescorecard.sqlite.hockey.HockeyDatabaseHelper;
import com.seniordesign.ultimatescorecard.sqlite.hockey.HockeyGameStats;
import com.seniordesign.ultimatescorecard.sqlite.hockey.HockeyGames;
import com.seniordesign.ultimatescorecard.stats.basketball.BasketballIndividualStatActivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class HockeyIndividualStatFragment extends Fragment{
	
	private HockeyDatabaseHelper _db;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = (View) inflater.inflate(R.layout.fragment_individual_hockey, container, false);
		view.setBackgroundResource(R.drawable.background_ice);

		return view;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		_db = ((HockeyIndividualStatActivity) getActivity())._hockey_db;
		HockeyGames game = ((HockeyIndividualStatActivity) getActivity())._game;
		String name = ((HockeyIndividualStatActivity) getActivity())._name;
		Teams team = ((HockeyIndividualStatActivity) getActivity())._team;
		boolean home = ((HockeyIndividualStatActivity) getActivity())._home;
		ArrayList<Players> players = ((HockeyIndividualStatActivity) getActivity())._players;
		long g_id = ((HockeyIndividualStatActivity) getActivity()).g_id;
		//NEW
		String _player = ((HockeyIndividualStatActivity) getActivity())._player;
        if(_player!=null){
			if(!_player.equals("All Players")){
	        	name = _player;
	        }
        }
		//END NEW
      //AVE
        boolean average = ((HockeyIndividualStatActivity) getActivity())._average;
		if(name.equals(team.getabbv() + " Stats")){
			if(home){
				
				((TextView)((HockeyIndividualStatActivity) getActivity()).findViewById(R.id.playerName)).setText(team.getabbv());
				((TextView)((HockeyIndividualStatActivity) getActivity()).findViewById(R.id.teamName)).setText(team.gettname());
				((TextView)((HockeyIndividualStatActivity) getActivity()).findViewById(R.id.goalTotal)).setText("Goals: "+game.gethomegoals());
				((TextView)((HockeyIndividualStatActivity) getActivity()).findViewById(R.id.assistTotal)).setText("Assists: "+game.gethomeast());
				((TextView)((HockeyIndividualStatActivity) getActivity()).findViewById(R.id.shotOnGoalTotal)).setText("Shots On Goal: "+game.gethomesog());
				((TextView)((HockeyIndividualStatActivity) getActivity()).findViewById(R.id.penaltyTotal)).setText("Penalties: "+(game.gethomepenmajor()+game.gethomepenminor()+game.gethomepenmisconduct()));
				((TextView)((HockeyIndividualStatActivity) getActivity()).findViewById(R.id.penaltyTypeTotal)).setText(
						" Minors:" + game.gethomepenminor() +
						"\n Majors: " + game.gethomepenmajor() +
						"\n Misconduct: " + game.gethomepenmisconduct());
				((TextView)((HockeyIndividualStatActivity) getActivity()).findViewById(R.id.penaltyMinsTotal)).setText("Penalty Minutes: "+game.gethomepenminutes());
				//Goalie Stats
				((TextView)((HockeyIndividualStatActivity) getActivity()).findViewById(R.id.goalieTitle)).setText("Goalie Stats");
				((TextView)((HockeyIndividualStatActivity) getActivity()).findViewById(R.id.goalieStats)).setText(
					" Saves:" + game.gethomesaves() +
					"\n Goals Allowed: " + game.gethomegoalsallowed() +
					"\n Save %: " + game.gethomesavepercent());

			}
			else{
				((TextView)((HockeyIndividualStatActivity) getActivity()).findViewById(R.id.playerName)).setText(team.getabbv());
				((TextView)((HockeyIndividualStatActivity) getActivity()).findViewById(R.id.teamName)).setText(team.gettname());
				((TextView)((HockeyIndividualStatActivity) getActivity()).findViewById(R.id.goalTotal)).setText("Goals: "+game.getawaygoals());
				((TextView)((HockeyIndividualStatActivity) getActivity()).findViewById(R.id.assistTotal)).setText("Assists: "+game.getawayast());
				((TextView)((HockeyIndividualStatActivity) getActivity()).findViewById(R.id.shotOnGoalTotal)).setText("Shots On Goal: "+game.getawaysog());
				((TextView)((HockeyIndividualStatActivity) getActivity()).findViewById(R.id.penaltyTotal)).setText("Penalties: "+(game.getawaypenmajor()+game.getawaypenminor()+game.getawaypenmisconduct()));
				((TextView)((HockeyIndividualStatActivity) getActivity()).findViewById(R.id.penaltyTypeTotal)).setText(
						" Minors:" + game.getawaypenminor() +
						"\n Majors: " + game.getawaypenmajor() +
						"\n Misconduct: " + game.getawaypenmisconduct());
				((TextView)((HockeyIndividualStatActivity) getActivity()).findViewById(R.id.penaltyMinsTotal)).setText("Penalty Minutes: "+game.getawaypenminutes());
				//Goalie Stats
				((TextView)((HockeyIndividualStatActivity) getActivity()).findViewById(R.id.goalieTitle)).setText("Goalie Stats");
				((TextView)((HockeyIndividualStatActivity) getActivity()).findViewById(R.id.goalieStats)).setText(
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
				ArrayList<HockeyGameStats> allStats = (ArrayList<HockeyGameStats>) _db.getPlayerAllGameStats(player.getpid());
				ArrayList<Games> games = (ArrayList<Games>) _db.getAllGamesTeam(player.gettid());
				double shots=0,sog=0,goals=0,ast=0,penminor=0,penmajor=0,penmisconduct=0,
						saves=0,goals_allowed=0;
				int n = 0;
				for(HockeyGameStats s: allStats){
					for(Games g: games){
						if(s.getgid()==g.getgid()){
							shots += s.getshots();
							sog+= s.getsog();
							goals += s.getgoals();
							ast =+ s.getast();
							penminor += s.getpenminor();
							penmajor += s.getpenmajor();
							penmisconduct += s.getpenmisconduct();
							saves += s.getsaves();
							goals_allowed += s.getgoalsallowed();
							n++;
						}
					}
					
				}
				shots /= n;
				sog /= n;
				goals /= n;
				ast /= n;
				penminor /= n;
				penmajor /= n;
				penmisconduct /= n;
				saves /= n;
				goals_allowed /= n;
				
			    String savepercent;
				if((saves+goals_allowed)>0){
					savepercent= String.format("%.3f", saves/(saves+goals_allowed));
				}
				else{
					savepercent= "N/A";
				}
				
				((TextView)((HockeyIndividualStatActivity) getActivity()).findViewById(R.id.playerName)).setText(player.getpname() + " - Average Stats");
				((TextView)((HockeyIndividualStatActivity) getActivity()).findViewById(R.id.teamName)).setText(team.gettname());
				((TextView)((HockeyIndividualStatActivity) getActivity()).findViewById(R.id.goalTotal)).setText("Goals: "+String.format("%.2f",goals));
				((TextView)((HockeyIndividualStatActivity) getActivity()).findViewById(R.id.assistTotal)).setText("Assists: "+String.format("%.2f",ast));
				((TextView)((HockeyIndividualStatActivity) getActivity()).findViewById(R.id.shotOnGoalTotal)).setText("Shots On Goal: "+String.format("%.2f",sog));
				((TextView)((HockeyIndividualStatActivity) getActivity()).findViewById(R.id.penaltyTotal)).setText("Penalties: "+String.format("%.2f",(penmajor+penminor+penmisconduct)));
				((TextView)((HockeyIndividualStatActivity) getActivity()).findViewById(R.id.penaltyTypeTotal)).setText(
						" Minors:" + String.format("%.2f",penminor) +
						"\n Majors: " + String.format("%.2f",penmajor) +
						"\n Misconduct: " + String.format("%.2f",penmisconduct));
				((TextView)((HockeyIndividualStatActivity) getActivity()).findViewById(R.id.penaltyMinsTotal)).setText("Penalty Minutes: "+String.format("%.2f",(2*penminor + 5*penmajor + 10*penmisconduct)));
				//Goalie Stats
				((TextView)((HockeyIndividualStatActivity) getActivity()).findViewById(R.id.goalieTitle)).setText("Goalie Stats");
				((TextView)((HockeyIndividualStatActivity) getActivity()).findViewById(R.id.goalieStats)).setText(
					" Saves:" + String.format("%.2f",saves) +
					"\n Goals Allowed: " + String.format("%.2f",goals_allowed) +
	
					"\n Save %: " + savepercent);
				
			}
			else{
				HockeyGameStats stats = _db.getPlayerGameStats(g_id, player.getpid());
					
				((TextView)((HockeyIndividualStatActivity) getActivity()).findViewById(R.id.playerName)).setText(player.getpname());
				((TextView)((HockeyIndividualStatActivity) getActivity()).findViewById(R.id.teamName)).setText(team.gettname());
				((TextView)((HockeyIndividualStatActivity) getActivity()).findViewById(R.id.goalTotal)).setText("Goals: "+stats.getgoals());
				((TextView)((HockeyIndividualStatActivity) getActivity()).findViewById(R.id.assistTotal)).setText("Assists: "+stats.getast());
				((TextView)((HockeyIndividualStatActivity) getActivity()).findViewById(R.id.shotOnGoalTotal)).setText("Shots On Goal: "+stats.getsog());
				((TextView)((HockeyIndividualStatActivity) getActivity()).findViewById(R.id.penaltyTotal)).setText("Penalties: "+(stats.getpenmajor()+stats.getpenminor()+stats.getpenmisconduct()));
				((TextView)((HockeyIndividualStatActivity) getActivity()).findViewById(R.id.penaltyTypeTotal)).setText(
						" Minors:" + stats.getpenminor() +
						"\n Majors: " + stats.getpenmajor() +
						"\n Misconduct: " + stats.getpenmisconduct());
				((TextView)((HockeyIndividualStatActivity) getActivity()).findViewById(R.id.penaltyMinsTotal)).setText("Penalty Minutes: "+stats.getpenminutes());
				//Goalie Stats
				((TextView)((HockeyIndividualStatActivity) getActivity()).findViewById(R.id.goalieTitle)).setText("Goalie Stats");
				((TextView)((HockeyIndividualStatActivity) getActivity()).findViewById(R.id.goalieStats)).setText(
					" Saves:" + stats.getsaves() +
					"\n Goals Allowed: " + stats.getgoalsallowed() +
	
					"\n Save %: " + stats.getsavepercent());
			}
		}
	}

}
