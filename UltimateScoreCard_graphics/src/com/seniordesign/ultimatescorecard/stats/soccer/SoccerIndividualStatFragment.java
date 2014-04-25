package com.seniordesign.ultimatescorecard.stats.soccer;

import java.util.ArrayList;

import com.seniordesign.ultimatescorecard.R;
import com.seniordesign.ultimatescorecard.data.GameInfo;
import com.seniordesign.ultimatescorecard.sqlite.helper.Games;
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
	private boolean _ifGameView, _ifPlayerView, _home, _ifTeamStats, _average;
	private Players _player;
	private GameInfo _gameInfo;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = (View) inflater.inflate(R.layout.fragment_individual_soccer, container, false);
		view.setBackgroundResource(R.drawable.background_green);

		return view;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		_db = ((SoccerIndividualStatActivity) getActivity())._db;
		
		_ifGameView = ((SoccerIndividualStatActivity) getActivity())._ifGameView;
		_ifPlayerView = ((SoccerIndividualStatActivity) getActivity())._ifPlayerView;
		
		((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.playerName)).setTextColor(Color.WHITE);
		((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.teamName)).setTextColor(Color.WHITE);
		((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.goalTotal)).setTextColor(Color.WHITE);
		((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.assistTotal)).setTextColor(Color.WHITE);
		((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.shotOnGoalTotal)).setTextColor(Color.WHITE);
		((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.shotTotal)).setTextColor(Color.WHITE);
		((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.penaltyTotal)).setTextColor(Color.WHITE);
		((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.penaltyTypeTotal)).setTextColor(Color.WHITE);
		((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.goalieTitle)).setTextColor(Color.WHITE);
		((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.goalieStats)).setTextColor(Color.WHITE);
		
		if(_ifGameView){
			_player = ((SoccerIndividualStatActivity) getActivity())._player;
			_gameInfo = ((SoccerIndividualStatActivity) getActivity())._gameInfo;
			_home = ((SoccerIndividualStatActivity) getActivity())._home;
			_ifTeamStats = ((SoccerIndividualStatActivity) getActivity())._ifTeamStats;
			
			if(_ifTeamStats){

				SoccerGames game = ((SoccerIndividualStatActivity) getActivity())._game;
				
				if(_home){
					Teams team = _gameInfo.getHomeTeam();
					((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.playerName)).setText(team.getabbv());
					((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.teamName)).setText(team.gettname());
					((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.goalTotal)).setText("Goals: "+game.gethomegoals());
					((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.assistTotal)).setText("Assists: "+game.gethomeast());
					((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.shotTotal)).setText("Shots: "+game.gethomeshots());
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
					Teams team = _gameInfo.getAwayTeam();

					((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.playerName)).setText(team.getabbv());
					((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.teamName)).setText(team.gettname());
					((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.goalTotal)).setText("Goals: "+game.getawaygoals());
					((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.assistTotal)).setText("Assists: "+game.getawayast());
					((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.shotTotal)).setText("Shots: "+game.getawayshots());
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
				SoccerGameStats stats = _db.getPlayerGameStats(_gameInfo.getgid(), _player.getpid());
				Teams team = null;
				if(_home){
					team = _gameInfo.getHomeTeam();
				}
				else{
					team = _gameInfo.getAwayTeam();
				}		
				
				((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.playerName)).setText(_player.getpname());
				((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.teamName)).setText(team.gettname());
				((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.goalTotal)).setText("Goals: "+stats.getgoals());
				((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.assistTotal)).setText("Assists: "+stats.getast());
				((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.shotTotal)).setText("Shots: "+stats.getshots());
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
		else if(_ifPlayerView){
			_average = ((SoccerIndividualStatActivity) getActivity())._average;
			_player = ((SoccerIndividualStatActivity) getActivity())._player;
			Teams team = ((SoccerIndividualStatActivity) getActivity())._team;

			if(!_average){
				SoccerGames game = ((SoccerIndividualStatActivity) getActivity())._game;
				SoccerGameStats stats = _db.getPlayerGameStats(game.getgid(), _player.getpid());

				((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.playerName)).setText(_player.getpname());
				((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.teamName)).setText(team.gettname());
				((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.goalTotal)).setText("Goals: "+stats.getgoals());
				((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.assistTotal)).setText("Assists: "+stats.getast());
				((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.shotTotal)).setText("Shots: "+stats.getshots());
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
			else{
			
				ArrayList<SoccerGameStats> allStats = (ArrayList<SoccerGameStats>) _db.getPlayerAllGameStats(_player.getpid());
				ArrayList<Games> games = (ArrayList<Games>) _db.getAllGamesTeam(_player.gettid());
				double shots=0,sog=0,goals=0,ast=0,fouls=0,pka=0,pkg=0,offside=0,
						ycard=0,rcard=0,save_opps=0,saves=0,goals_allowed=0;
				int n = 0;
				for(SoccerGameStats s: allStats){
					for(Games g: games){
						if(s.getgid()==g.getgid()){
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
							n++;
						}
					}
				}
				
				shots /= n;
				sog /= n;
				goals /= n;
				ast /= n;
				fouls /= n;
				pka /= n; 
				pkg /= n;
				offside /= n;
				ycard /= n;
				rcard /= n;
				save_opps /= n;
				saves /= n;
				goals_allowed /= n;
				
			    String savepercent;
		    	if((saves+goals_allowed)>0){
		    		savepercent= String.format("%.3f", saves/(saves+goals_allowed));
		    	}
		    	else{
		    		savepercent= "N/A";
		    	}
			    
				
				((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.playerName)).setText(_player.getpname() + " - Average Stats");
				((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.teamName)).setText(team.gettname());
				((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.goalTotal)).setText("Goals: "+String.format("%.2f", goals));
				((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.assistTotal)).setText("Assists: "+String.format("%.2f", ast));
				((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.shotTotal)).setText("Shots: "+String.format("%.2f", shots));
				((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.shotOnGoalTotal)).setText("Shots On Goal: "+String.format("%.2f", sog));
				((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.penaltyTypeTotal)).setText(
						" Yellow Cards:" + String.format("%.2f", ycard) +
						"\n Red Cards: " + String.format("%.2f", rcard));
				//Goalie Stats
				((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.goalieTitle)).setText("Goalie Stats");
				((TextView)((SoccerIndividualStatActivity) getActivity()).findViewById(R.id.goalieStats)).setText(
					" Saves:" + String.format("%.2f", saves) +
					"\n Goals Allowed: " + String.format("%.2f", goals_allowed) +
		
					"\n Save %: " + savepercent);		
			}
		}
	}
}
