package com.seniordesign.ultimatescorecard.stats.soccer;

import java.util.ArrayList;

import com.seniordesign.ultimatescorecard.R;
import com.seniordesign.ultimatescorecard.data.GameInfo;
import com.seniordesign.ultimatescorecard.sqlite.helper.Games;
import com.seniordesign.ultimatescorecard.sqlite.helper.Players;
import com.seniordesign.ultimatescorecard.sqlite.helper.Teams;
import com.seniordesign.ultimatescorecard.view.StaticFinalVars;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SoccerBoxscoreFragment extends Fragment{ 
	private boolean _lookingAtHome = true;
	private Players _player;
	private ArrayList<Games> _games;
	private ArrayList<Teams> _teams;
	boolean _ifPlayerView, _ifGameView;
	private Teams _team;

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = (View) inflater.inflate(R.layout.fragment_boxscore, container, false);
		view.setBackgroundResource(R.drawable.background_green);
		_ifPlayerView = ((SoccerStatsActivity) getActivity())._ifPlayerView;
		_ifGameView = ((SoccerStatsActivity) getActivity())._ifGameView;
		_player = ((SoccerStatsActivity) getActivity())._player;
        return view;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if(_ifGameView){
			((TextView)getView().findViewById(R.id.homeTeamStatText)).setText(((SoccerStatsActivity) getActivity())._gameInfo.getHomeTeam().gettname());
			((TextView)getView().findViewById(R.id.awayTeamStatText)).setText(((SoccerStatsActivity) getActivity())._gameInfo.getAwayTeam().gettname());
		}
		else if(_ifPlayerView){
			((TextView)getView().findViewById(R.id.homeTeamStatText)).setText(_player.getpname());
			((TextView)getView().findViewById(R.id.awayTeamStatText)).setVisibility(View.GONE);
		}
		if(_lookingAtHome){
			if(_ifGameView)
				getView().findViewById(R.id.homeTeamStatText).setBackgroundColor(getResources().getColor(R.color.robin_egg_blue));
			else if(_ifPlayerView)
				getView().findViewById(R.id.homeTeamStatText).setBackgroundColor(getResources().getColor(R.color.white));
			getView().findViewById(R.id.awayTeamStatText).setBackgroundColor(getResources().getColor(R.color.white));
		}
		else{
			getView().findViewById(R.id.homeTeamStatText).setBackgroundColor(getResources().getColor(R.color.white));
			if(_ifGameView)
				getView().findViewById(R.id.awayTeamStatText).setBackgroundColor(getResources().getColor(R.color.robin_egg_blue));
		}
		if(_ifGameView){
			getView().findViewById(R.id.homeTeamStatText).setOnClickListener(homeTeamListener);
			getView().findViewById(R.id.awayTeamStatText).setOnClickListener(awayTeamListener);
		}
		removeAllViews();
		addTextViews();
	}
	
	private void addTextViews(){
		LinearLayout layout = ((LinearLayout) getView().findViewById(R.id.playerListLayout));

		if(_ifPlayerView){
			_games = ((SoccerStatsActivity) getActivity())._games;
			_player = ((SoccerStatsActivity) getActivity())._player;
			_team = ((SoccerStatsActivity) getActivity())._team;
			_teams = ((SoccerStatsActivity) getActivity())._teams;

			for(Games game: _games){
				Teams _away = null;
				Teams _home = null;
				for(Teams t: _teams){
					if(t.gettid()==game.getawayid()){
						_away = t;
					}
					if(t.gettid()==game.gethomeid()){
						_home = t;
					}
				}
				String gameName = _away.getabbv() + " vs " + _home.getabbv()+":\n"+game.getDate();
				layout.addView(newTextView(gameName));
			}	
			//AVE
			layout.addView(newTextView("Average Stats"));
        }
        //END NEW
        else if(_ifGameView){
    		GameInfo _gameInfo = ((SoccerStatsActivity) getActivity())._gameInfo;

			if(_lookingAtHome){
				for(Players p: _gameInfo.getHomePlayers()){
					layout.addView(newTextView(p.getpname()));	
				}
				layout.addView(newTextView(_gameInfo.getHomeTeam().getabbv() +  " Stats"));
			}
			else{
				for(Players p: _gameInfo.getAwayPlayers()){
					layout.addView(newTextView(p.getpname()));	
				}		
				layout.addView(newTextView(_gameInfo.getAwayTeam().getabbv() +  " Stats"));
			}
        }
	}
	
	private void removeAllViews(){
		LinearLayout layout = ((LinearLayout) getView().findViewById(R.id.playerListLayout));
		layout.removeAllViews();
	}
	
	private TextView newTextView(String teamName){
		final TextView textView = new TextView(getActivity());
		textView.setText(teamName);
		textView.setPadding(5,5,5,5);
		textView.setTextSize(20);
		textView.setOnClickListener(selectPlayerListener);
		return textView;
	}
	
	private OnClickListener homeTeamListener = new OnClickListener(){
		@Override
		public void onClick(View v) {
			getView().findViewById(R.id.homeTeamStatText).setBackgroundColor(getResources().getColor(R.color.robin_egg_blue));
			getView().findViewById(R.id.awayTeamStatText).setBackgroundColor(getResources().getColor(R.color.white));
			if(!_lookingAtHome){
				_lookingAtHome = true;
				removeAllViews();
				addTextViews();
				
			}
		}
	};
	
	private OnClickListener awayTeamListener = new OnClickListener(){
		@Override
		public void onClick(View v) {
			getView().findViewById(R.id.homeTeamStatText).setBackgroundColor(getResources().getColor(R.color.white));
			getView().findViewById(R.id.awayTeamStatText).setBackgroundColor(getResources().getColor(R.color.robin_egg_blue));
			if(_lookingAtHome){
				_lookingAtHome = false;
				removeAllViews();
				addTextViews();			
			}
		}
	};
	
	private OnClickListener selectPlayerListener = new OnClickListener(){
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(getActivity().getApplicationContext(), SoccerIndividualStatActivity.class);
			v.setBackgroundColor(getResources().getColor(R.color.robin_egg_blue));
			String aveTest = ((TextView)v).getText().toString();
			boolean average = aveTest.equals("Average Stats");
			
			if(_ifPlayerView){
				if(average){
		        	intent.putExtra(StaticFinalVars.PLAYER, _player);	        	
		        	intent.putExtra(StaticFinalVars.TEAM, _team);
				}
				else if(!average){
					
					Games game = null;
					String[] lines = ((TextView)v).getText().toString().split("\n");
					for(Games g: _games){
						if(g.getDate().equals(lines[1])){
							game = g;
							break;
						}
					}
					
					intent.putExtra(StaticFinalVars.PLAYER, _player);	        	
		        	intent.putExtra(StaticFinalVars.TEAM, _team);	
					intent.putExtra(StaticFinalVars.GAME, game);	        	
		        }
				intent.putExtra(StaticFinalVars.AVERAGE, average);
			}
	        else if(_ifGameView){
	        	//END NEW
				if(_lookingAtHome){
					intent.putExtra(StaticFinalVars.SHOT_CHART, ((SoccerStatsActivity) getActivity())._homeShots);
				}
				else{
					intent.putExtra(StaticFinalVars.SHOT_CHART, ((SoccerStatsActivity) getActivity())._awayShots);
				}
				intent.putExtra(StaticFinalVars.HOME_OR_AWAY, _lookingAtHome);
				intent.putExtra(StaticFinalVars.PLAYER_NAME, ((TextView)v).getText().toString());
				intent.putExtra(StaticFinalVars.GAME_INFO, ((SoccerStatsActivity) getActivity())._gameInfo);
	        }
			intent.putExtra(StaticFinalVars.IF_PLAYER_VIEW, _ifPlayerView);
			intent.putExtra(StaticFinalVars.IF_GAME_VIEW, _ifGameView);
			intent.putExtra(StaticFinalVars.DISPLAY_TYPE, 0);
			startActivity(intent);
		}
	};
}
