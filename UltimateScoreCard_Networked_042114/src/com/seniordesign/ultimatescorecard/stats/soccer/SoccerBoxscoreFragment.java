package com.seniordesign.ultimatescorecard.stats.soccer;

import java.util.ArrayList;

import com.seniordesign.ultimatescorecard.R;
import com.seniordesign.ultimatescorecard.data.GameInfo;
import com.seniordesign.ultimatescorecard.sqlite.helper.Games;
import com.seniordesign.ultimatescorecard.sqlite.helper.Players;
import com.seniordesign.ultimatescorecard.sqlite.helper.ShotChartCoords;
import com.seniordesign.ultimatescorecard.sqlite.helper.Teams;
import com.seniordesign.ultimatescorecard.view.StaticFinalVars;

import android.content.Intent;
import android.graphics.Color;
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
	private String _player;
	private ArrayList<Games> _games;
	boolean _playerView = false;
	private ArrayList<Teams> _teams;

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = (View) inflater.inflate(R.layout.fragment_boxscore, container, false);
		view.setBackgroundResource(R.drawable.background_green);
		_player = ((SoccerStatsActivity) getActivity()).getPlayer();
		//NEW
		if(_player!=null){
			//END NEW
			if(_player.equals("All Players")){
				_playerView=false;
			}
			else{
				_playerView = true;
			}
		}
        return view;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if(!_playerView){
			((TextView)getView().findViewById(R.id.homeTeamStatText)).setText(((SoccerStatsActivity) getActivity()).getGameInfo().getHomeTeam().gettname());
			((TextView)getView().findViewById(R.id.awayTeamStatText)).setText(((SoccerStatsActivity) getActivity()).getGameInfo().getAwayTeam().gettname());
		}
		else{
			((TextView)getView().findViewById(R.id.homeTeamStatText)).setText(_player);
			((TextView)getView().findViewById(R.id.awayTeamStatText)).setVisibility(View.GONE);
		}
		((TextView)getView().findViewById(R.id.statisticTitle)).setTextColor(Color.WHITE);

		if(_lookingAtHome){
			if(!_playerView)
				getView().findViewById(R.id.homeTeamStatText).setBackgroundColor(getResources().getColor(R.color.robin_egg_blue));
			else
				getView().findViewById(R.id.homeTeamStatText).setBackgroundColor(getResources().getColor(R.color.white));
			getView().findViewById(R.id.awayTeamStatText).setBackgroundColor(getResources().getColor(R.color.white));
		}
		else{
			getView().findViewById(R.id.homeTeamStatText).setBackgroundColor(getResources().getColor(R.color.white));
			if(!_playerView)
				getView().findViewById(R.id.awayTeamStatText).setBackgroundColor(getResources().getColor(R.color.robin_egg_blue));
		}
		if(!_playerView){
			getView().findViewById(R.id.homeTeamStatText).setOnClickListener(homeTeamListener);
			getView().findViewById(R.id.awayTeamStatText).setOnClickListener(awayTeamListener);
		}
		removeAllViews();
		addTextViews();
	}
	
	private void addTextViews(){
		LinearLayout layout = ((LinearLayout) getView().findViewById(R.id.playerListLayout));
		GameInfo _gameInfo = ((SoccerStatsActivity) getActivity()).getGameInfo();
		//NEW
		_games = ((SoccerStatsActivity) getActivity()).getGames();
		_player = ((SoccerStatsActivity) getActivity()).getPlayer();
		_teams = ((SoccerStatsActivity) getActivity()).getTeams();

		if(_playerView){
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
        else{
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
		textView.setTextColor(Color.WHITE);
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
			if(_playerView){
				if(!_player.equals("All Players")){
					if(average){
						intent.putExtra(StaticFinalVars.TEAM_INFO, ((SoccerStatsActivity) getActivity()).getGameInfo().getHomeTeam());
			        	intent.putExtra(StaticFinalVars.TEAM_INFO2, ((SoccerStatsActivity) getActivity()).getGameInfo().getAwayTeam());
			        	ArrayList<Players> _ps = ((SoccerStatsActivity) getActivity()).getGameInfo().getHomePlayers();
			        	_ps.addAll(((SoccerStatsActivity) getActivity()).getGameInfo().getAwayPlayers());
						intent.putExtra(StaticFinalVars.PLAYERS_INFO, _ps);
						intent.putExtra(StaticFinalVars.GAME_ID, ((SoccerStatsActivity) getActivity()).getGameInfo().getgid());
						intent.putExtra(StaticFinalVars.HOME_OR_AWAY, _lookingAtHome);
						intent.putExtra(StaticFinalVars.SHOT_CHART, new ArrayList<ShotChartCoords>());			
				
						
					}
					else{
			        	intent.putExtra(StaticFinalVars.TEAM_INFO, ((SoccerStatsActivity) getActivity()).getGameInfo().getHomeTeam());
			        	intent.putExtra(StaticFinalVars.TEAM_INFO2, ((SoccerStatsActivity) getActivity()).getGameInfo().getAwayTeam());
			        	ArrayList<Players> _ps = ((SoccerStatsActivity) getActivity()).getGameInfo().getHomePlayers();
			        	_ps.addAll(((SoccerStatsActivity) getActivity()).getGameInfo().getAwayPlayers());
						intent.putExtra(StaticFinalVars.PLAYERS_INFO, _ps);
						Games game = null;
						String[] lines = ((TextView)v).getText().toString().split("\n");
						for(Games g: _games){
							if(g.getDate().equals(lines[1])){
								game = g;
								break;
							}
						}
						intent.putExtra(StaticFinalVars.GAME_ID, game.getgid());
						intent.putExtra(StaticFinalVars.HOME_OR_AWAY, _lookingAtHome);
						intent.putExtra(StaticFinalVars.SHOT_CHART, new ArrayList<ShotChartCoords>());			
					}
				}
				else{
					if(_lookingAtHome){
						intent.putExtra(StaticFinalVars.TEAM_INFO, ((SoccerStatsActivity) getActivity()).getGameInfo().getHomeTeam());
			        	intent.putExtra(StaticFinalVars.TEAM_INFO2, ((SoccerStatsActivity) getActivity()).getGameInfo().getAwayTeam());
						intent.putExtra(StaticFinalVars.PLAYERS_INFO, ((SoccerStatsActivity) getActivity()).getGameInfo().getHomePlayers());
						intent.putExtra(StaticFinalVars.GAME_ID, ((SoccerStatsActivity) getActivity()).getGameInfo().getgid());
						intent.putExtra(StaticFinalVars.HOME_OR_AWAY, _lookingAtHome);
						intent.putExtra(StaticFinalVars.SHOT_CHART, ((SoccerStatsActivity) getActivity()).getHomeShotChart());
					}
					else{
						intent.putExtra(StaticFinalVars.TEAM_INFO, ((SoccerStatsActivity) getActivity()).getGameInfo().getAwayTeam());
			        	intent.putExtra(StaticFinalVars.TEAM_INFO2, ((SoccerStatsActivity) getActivity()).getGameInfo().getHomeTeam());
						intent.putExtra(StaticFinalVars.PLAYERS_INFO, ((SoccerStatsActivity) getActivity()).getGameInfo().getAwayPlayers());
						intent.putExtra(StaticFinalVars.GAME_ID, ((SoccerStatsActivity) getActivity()).getGameInfo().getgid());
						intent.putExtra(StaticFinalVars.HOME_OR_AWAY, _lookingAtHome);
						intent.putExtra(StaticFinalVars.SHOT_CHART, ((SoccerStatsActivity) getActivity()).getAwayShotChart());
					}
					intent.putExtra(StaticFinalVars.PLAYER_NAME, ((TextView)v).getText().toString());
					intent.putExtra(StaticFinalVars.GAME_INFO, ((SoccerStatsActivity) getActivity()).getGameInfo());
					intent.putExtra(StaticFinalVars.DISPLAY_TYPE, 0);
				}
			}
	        else{
	        	//END NEW
				if(_lookingAtHome){
					intent.putExtra(StaticFinalVars.TEAM_INFO, ((SoccerStatsActivity) getActivity()).getGameInfo().getHomeTeam());
		        	intent.putExtra(StaticFinalVars.TEAM_INFO2, ((SoccerStatsActivity) getActivity()).getGameInfo().getAwayTeam());
					intent.putExtra(StaticFinalVars.PLAYERS_INFO, ((SoccerStatsActivity) getActivity()).getGameInfo().getHomePlayers());
					intent.putExtra(StaticFinalVars.GAME_ID, ((SoccerStatsActivity) getActivity()).getGameInfo().getgid());
					intent.putExtra(StaticFinalVars.HOME_OR_AWAY, _lookingAtHome);
					intent.putExtra(StaticFinalVars.SHOT_CHART, ((SoccerStatsActivity) getActivity()).getHomeShotChart());
				}
				else{
					intent.putExtra(StaticFinalVars.TEAM_INFO, ((SoccerStatsActivity) getActivity()).getGameInfo().getAwayTeam());
		        	intent.putExtra(StaticFinalVars.TEAM_INFO2, ((SoccerStatsActivity) getActivity()).getGameInfo().getHomeTeam());
					intent.putExtra(StaticFinalVars.PLAYERS_INFO, ((SoccerStatsActivity) getActivity()).getGameInfo().getAwayPlayers());
					intent.putExtra(StaticFinalVars.GAME_ID, ((SoccerStatsActivity) getActivity()).getGameInfo().getgid());
					intent.putExtra(StaticFinalVars.HOME_OR_AWAY, _lookingAtHome);
					intent.putExtra(StaticFinalVars.SHOT_CHART, ((SoccerStatsActivity) getActivity()).getAwayShotChart());
				}
				intent.putExtra(StaticFinalVars.PLAYER_NAME, ((TextView)v).getText().toString());
				intent.putExtra(StaticFinalVars.GAME_INFO, ((SoccerStatsActivity) getActivity()).getGameInfo());
				intent.putExtra(StaticFinalVars.DISPLAY_TYPE, 0);
	        }
			//NEW
			intent.putExtra(StaticFinalVars.PLAYER, _player);
			intent.putExtra(StaticFinalVars.AVERAGE, average);
			//END NEW
			startActivity(intent);
		}
	};
}
