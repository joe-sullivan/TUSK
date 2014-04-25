package com.seniordesign.ultimatescorecard.stats.hockey;

import java.util.ArrayList;

import com.seniordesign.ultimatescorecard.R;
import com.seniordesign.ultimatescorecard.data.GameInfo;
import com.seniordesign.ultimatescorecard.sqlite.helper.Games;
import com.seniordesign.ultimatescorecard.sqlite.helper.Players;
import com.seniordesign.ultimatescorecard.sqlite.helper.Teams;
import com.seniordesign.ultimatescorecard.sqlite.hockey.HockeyDatabaseHelper;
import com.seniordesign.ultimatescorecard.sqlite.hockey.HockeyGameStats;
import com.seniordesign.ultimatescorecard.sqlite.hockey.HockeyGames;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class HockeyIndividualStatFragment extends Fragment{
	
	private HockeyDatabaseHelper _db;
	private boolean _ifGameView, _ifPlayerView, _home, _ifTeamStats, _average;
	private Players _player;
	private GameInfo _gameInfo;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = (View) inflater.inflate(R.layout.fragment_individual_hockey, container, false);
		view.setBackgroundResource(R.drawable.background_ice);

		return view;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		_db = ((HockeyIndividualStatActivity) getActivity())._db;
		
		_ifGameView = ((HockeyIndividualStatActivity) getActivity())._ifGameView;
		_ifPlayerView = ((HockeyIndividualStatActivity) getActivity())._ifPlayerView;
		
		if(_ifGameView){
			_player = ((HockeyIndividualStatActivity) getActivity())._player;
			_gameInfo = ((HockeyIndividualStatActivity) getActivity())._gameInfo;
			_home = ((HockeyIndividualStatActivity) getActivity())._home;
			_ifTeamStats = ((HockeyIndividualStatActivity) getActivity())._ifTeamStats;
			
			if(_ifTeamStats){

				HockeyGames game = ((HockeyIndividualStatActivity) getActivity())._game;
				
				if(_home){
					Teams team = _gameInfo.getHomeTeam();
					
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
					Teams team = _gameInfo.getAwayTeam();

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
				HockeyGameStats stats = _db.getPlayerGameStats(_gameInfo.getgid(), _player.getpid());
				Teams team = null;
				if(_home){
					team = _gameInfo.getHomeTeam();
				}
				else{
					team = _gameInfo.getAwayTeam();
				}		
				
				((TextView)((HockeyIndividualStatActivity) getActivity()).findViewById(R.id.playerName)).setText(_player.getpname());
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
		
		
		else if(_ifPlayerView){
			_average = ((HockeyIndividualStatActivity) getActivity())._average;
			_player = ((HockeyIndividualStatActivity) getActivity())._player;
			Teams team = ((HockeyIndividualStatActivity) getActivity())._team;

			if(!_average){
				HockeyGames game = ((HockeyIndividualStatActivity) getActivity())._game;
				HockeyGameStats stats = _db.getPlayerGameStats(game.getgid(), _player.getpid());

				((TextView)((HockeyIndividualStatActivity) getActivity()).findViewById(R.id.playerName)).setText(_player.getpname());
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
			else{
				ArrayList<HockeyGameStats> allStats = (ArrayList<HockeyGameStats>) _db.getPlayerAllGameStats(_player.getpid());
				ArrayList<Games> games = (ArrayList<Games>) _db.getAllGamesTeam(_player.gettid());
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
				
				((TextView)((HockeyIndividualStatActivity) getActivity()).findViewById(R.id.playerName)).setText(_player.getpname() + " - Average Stats");
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
		}
	}
}
