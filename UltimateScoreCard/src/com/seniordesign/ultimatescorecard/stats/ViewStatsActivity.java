package com.seniordesign.ultimatescorecard.stats;

import java.io.File;
import java.util.ArrayList;

import com.seniordesign.ultimatescorecard.R;
import com.seniordesign.ultimatescorecard.data.GameInfo;
import com.seniordesign.ultimatescorecard.sqlite.basketball.BasketballDatabaseHelper;
import com.seniordesign.ultimatescorecard.sqlite.basketball.BasketballGames;
import com.seniordesign.ultimatescorecard.sqlite.football.FootballDatabaseHelper;
import com.seniordesign.ultimatescorecard.sqlite.helper.DatabaseHelper;
import com.seniordesign.ultimatescorecard.sqlite.helper.Games;
import com.seniordesign.ultimatescorecard.sqlite.helper.PlayByPlay;
import com.seniordesign.ultimatescorecard.sqlite.helper.Players;
import com.seniordesign.ultimatescorecard.sqlite.helper.ShotChartCoords;
import com.seniordesign.ultimatescorecard.sqlite.helper.Teams;
import com.seniordesign.ultimatescorecard.sqlite.hockey.HockeyDatabaseHelper;
import com.seniordesign.ultimatescorecard.sqlite.hockey.HockeyGames;
import com.seniordesign.ultimatescorecard.sqlite.soccer.SoccerDatabaseHelper;
import com.seniordesign.ultimatescorecard.sqlite.soccer.SoccerGames;
import com.seniordesign.ultimatescorecard.stats.basketball.BasketballStatsActivity;
import com.seniordesign.ultimatescorecard.stats.football.FootballStatsActivity;
import com.seniordesign.ultimatescorecard.stats.hockey.HockeyStatsActivity;
import com.seniordesign.ultimatescorecard.stats.soccer.SoccerStatsActivity;
import com.seniordesign.ultimatescorecard.view.StaticFinalVars;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;

public class ViewStatsActivity extends Activity{
	private Button _sportButton, _teamButton, _gameButton, _searchButton, _sendButton;
	private ArrayList<Teams> _teams = new ArrayList<Teams>();
	private ArrayList<Games> _games = new ArrayList<Games>();
	private DatabaseHelper _db;
	private String _sport;
	private Teams _team;
	private Games _game;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.activity_view_stats);
		
		_sportButton = (Button)findViewById(R.id.choose_sport_button);
		_sportButton.setOnClickListener(selectSportListener);
		_teamButton = (Button)findViewById(R.id.choose_team_button);
		_teamButton.setOnClickListener(selectTeamListener);
		_gameButton = (Button)findViewById(R.id.choose_game_button);
		_gameButton.setOnClickListener(selectGameListener);
		_searchButton = (Button)findViewById(R.id.search_button);
		_searchButton.setOnClickListener(searchListener);	
		_sendButton = (Button)findViewById(R.id.send_game_button);
		_sendButton.setOnClickListener(sendListener);
	}
	
	private OnClickListener selectSportListener = new OnClickListener(){
		@Override
		public void onClick(View view) {
			Builder builder = new Builder(ViewStatsActivity.this);
			builder.setTitle("Select Sport");
			final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String> (ViewStatsActivity.this, android.R.layout.select_dialog_singlechoice);
			arrayAdapter.add("Basketball");
			arrayAdapter.add("Football");
			arrayAdapter.add("Hockey");
			arrayAdapter.add("Soccer");
			builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});			
			builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					_sportButton.setText(arrayAdapter.getItem(which));
					changeBackground(arrayAdapter.getItem(which));
					buttonEnabler(true, false, false);
					resetButtonText(true, true);
					dialog.dismiss();
				}
			});
			builder.show();
		}		
	};
	
	private OnClickListener selectTeamListener = new OnClickListener(){
		@Override
		public void onClick(View view) {
			Builder builder = new Builder(ViewStatsActivity.this);
			builder.setTitle("Select Sport");
			final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String> (ViewStatsActivity.this, android.R.layout.select_dialog_singlechoice);
	
			//databases
			_sport = _sportButton.getText().toString();
			if(_sport.equals("Basketball")){
				_db = new BasketballDatabaseHelper(ViewStatsActivity.this);
			}
			else if(_sport.equals("Football")){
				_db = new FootballDatabaseHelper(ViewStatsActivity.this);
			}
			else if(_sport.equals("Soccer")){
				_db = new SoccerDatabaseHelper(ViewStatsActivity.this);
			}
			else{ // _sport.equals("Hockey")
				_db = new HockeyDatabaseHelper(ViewStatsActivity.this);
			}
			_teams = (ArrayList<Teams>) _db.getAllTeams();
			
			for(Teams team: _teams){
				arrayAdapter.add(team.gettname());
			}			
			builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});			
			builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					_teamButton.setText(arrayAdapter.getItem(which));
					buttonEnabler(true, true, false);
					resetButtonText(false, true);
					dialog.dismiss();
				}
			});
			builder.show();
		}		
	};
	
	private OnClickListener selectGameListener = new OnClickListener(){
		@Override
		public void onClick(View view) {
			Builder builder = new Builder(ViewStatsActivity.this);
			builder.setTitle("Select Game");
			final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String> (ViewStatsActivity.this, android.R.layout.select_dialog_singlechoice);
			
			String team = _teamButton.getText().toString();
			for(Teams t: _teams){
				if(t.gettname().equals(team)){
					_team = t;
					break;
				}
			}
			//databases
			if(_sport.equals("Basketball")){
				_games = (ArrayList<Games>) (((BasketballDatabaseHelper) _db).getAllGamesTeam(_team.gettid()));
			}
			else if(_sport.equals("Football")){
				_games = (ArrayList<Games>) (((FootballDatabaseHelper) _db).getAllGamesTeam(_team.gettid()));
			}
			else if(_sport.equals("Soccer")){
				_games = (ArrayList<Games>) (((SoccerDatabaseHelper) _db).getAllGamesTeam(_team.gettid()));
			}
			else{ // _sport.equals("Hockey")
				_games = (ArrayList<Games>) (((HockeyDatabaseHelper) _db).getAllGamesTeam(_team.gettid()));
			}
			
			for(Games game: _games){
				arrayAdapter.add(game.getDate());
			}			
			builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});			
			builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					_gameButton.setText(arrayAdapter.getItem(which));
					buttonEnabler(true, true, true);
					dialog.dismiss();
				}
			});
			builder.show();
		}		
	};
	
	private OnClickListener searchListener = new OnClickListener(){
		@Override
		public void onClick(View view) {
			Intent intent;
			String game = _gameButton.getText().toString();
			for(Games g: _games){
				if(g.getDate().equals(game)){
					_game = g;
					break;
				}
			}
			
			ArrayList<Players> _homeTeamPlayersPull = (ArrayList<Players>) _db.getPlayersTeam2(_game.gethomeid());
			ArrayList<Players> _awayTeamPlayersPull = (ArrayList<Players>) _db.getPlayersTeam2(_game.getawayid());
			Teams _home = _db.getTeam(_game.gethomeid());
			Teams _away = _db.getTeam(_game.getawayid());
			long g_id = _game.getgid();
			GameInfo _gameInfo = new GameInfo(_home, _away, _homeTeamPlayersPull, _awayTeamPlayersPull, g_id);
			
			ArrayList<PlayByPlay> _playbyplay = (ArrayList<PlayByPlay>) _db.getPlayByPlayGame(g_id);
			
			ArrayList<ShotChartCoords> _homeShots = (ArrayList<ShotChartCoords>) _db.getAllTeamShotsGame(_game.gethomeid(), g_id);
			ArrayList<ShotChartCoords> _awayShots = (ArrayList<ShotChartCoords>) _db.getAllTeamShotsGame(_game.getawayid(), g_id);
			
			if(_sportButton.getText().equals("Basketball")){
				_gameInfo.setAwayScore(((BasketballGames)_game).getAwayScoreText());
				_gameInfo.setHomeScore(((BasketballGames)_game).getHomeScoreText());
				intent = new Intent(getApplicationContext(), BasketballStatsActivity.class);
				intent.putExtra(StaticFinalVars.GAME_INFO, _gameInfo);			
				intent.putExtra(StaticFinalVars.GAME_LOG, _playbyplay);
				intent.putExtra(StaticFinalVars.SHOT_CHART_HOME, _homeShots);
				intent.putExtra(StaticFinalVars.SHOT_CHART_AWAY, _awayShots);
				intent.putExtra(StaticFinalVars.DISPLAY_TYPE, 0);
			}
			else if (_sportButton.getText().equals("Football")){
				//_gameInfo.setAwayScore(((FootballGames)_game).getAwayScoreText());
				//_gameInfo.setHomeScore(((FootballGames)_game).getHomeScoreText());
				intent = new Intent(getApplicationContext(), FootballStatsActivity.class);
				intent.putExtra(StaticFinalVars.GAME_INFO, _gameInfo);			
				intent.putExtra(StaticFinalVars.GAME_LOG, _playbyplay);
				intent.putExtra(StaticFinalVars.SHOT_CHART_HOME, _homeShots);
				intent.putExtra(StaticFinalVars.SHOT_CHART_AWAY, _awayShots);
				intent.putExtra(StaticFinalVars.DISPLAY_TYPE, 0);
			}
			else if (_sportButton.getText().equals("Hockey")){
				_gameInfo.setAwayScore(((HockeyGames)_game).getAwayScoreText());
				_gameInfo.setHomeScore(((HockeyGames)_game).getHomeScoreText());
				intent = new Intent(getApplicationContext(), HockeyStatsActivity.class);
				intent.putExtra(StaticFinalVars.GAME_INFO, _gameInfo);			
				intent.putExtra(StaticFinalVars.GAME_LOG, _playbyplay);
				intent.putExtra(StaticFinalVars.SHOT_CHART_HOME, _homeShots);
				intent.putExtra(StaticFinalVars.SHOT_CHART_AWAY, _awayShots);
				intent.putExtra(StaticFinalVars.DISPLAY_TYPE, 0);
			}
			else { // Soccer
				_gameInfo.setAwayScore(((SoccerGames)_game).getAwayScoreText());
				_gameInfo.setHomeScore(((SoccerGames)_game).getHomeScoreText());
				intent = new Intent(getApplicationContext(), SoccerStatsActivity.class);
				intent.putExtra(StaticFinalVars.GAME_INFO, _gameInfo);			
				intent.putExtra(StaticFinalVars.GAME_LOG, _playbyplay);
				intent.putExtra(StaticFinalVars.SHOT_CHART_HOME, _homeShots);
				intent.putExtra(StaticFinalVars.SHOT_CHART_AWAY, _awayShots);
				intent.putExtra(StaticFinalVars.DISPLAY_TYPE, 0);
			}
			startActivity(intent);
		}		
	};
	
	public OnClickListener sendListener = new OnClickListener(){
		@Override
		public void onClick(View view) {
			/*if(isExternalStorageWritable()){
				File sdCard = Environment.getExternalStorageDirectory();
				File file = new File(sdCard, "/tempScoreCard.txt");
				FileOutputStream fos;
				try {
					fos = new FileOutputStream(file);
					OutputStreamWriter osw = new OutputStreamWriter(fos);
					osw.write("A Debilitating Virus \n");
					osw.write("A Very Debilitating Virus");
					osw.flush();
					osw.close();
				} catch (Exception e) {
					e.printStackTrace();
				}				
			  
				Intent intent = new Intent(Intent.ACTION_SEND);
				intent.putExtra(Intent.EXTRA_SUBJECT, "Very Important Email");
				intent.putExtra(Intent.EXTRA_EMAIL, new String[] {"cheung.chen@uconn.edu"});
				intent.putExtra(Intent.EXTRA_TEXT, "Your computer is now infected with a debilitating virus.");
				intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(Environment.getExternalStorageDirectory().toString()+"/tempScoreCard.txt")));
				intent.setType("text/plain");
				startActivityForResult(Intent.createChooser(intent, "Send Mail"), StaticFinalVars.EMAIL);
			}*/
		}
	};
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		if(requestCode == StaticFinalVars.EMAIL){
			File sdCard = Environment.getExternalStorageDirectory();
			File file = new File(sdCard, "/tempScoreCard.txt");
            if(file.exists()){
            	file.delete();  
            }
		}
	}
	
	@SuppressWarnings("unused")
	private boolean isExternalStorageWritable(){
		String state = Environment.getExternalStorageState();
	    if (Environment.MEDIA_MOUNTED.equals(state)) {
	        return true;
	    }
	    return false;		
	}
	
	private void buttonEnabler(boolean team, boolean game, boolean search){
		_teamButton.setEnabled(team);
		_gameButton.setEnabled(game);
		_searchButton.setEnabled(search);
		_sendButton.setEnabled(search);
	}
	
	private void resetButtonText(boolean team, boolean game){
		if(team){
			_teamButton.setText(getResources().getString(R.string.choose_team));
		}
		if(game){
			_gameButton.setText(getResources().getString(R.string.choose_game));
		}
	}
	
	private void changeBackground(String sport){
		if(sport.equals("Basketball")){
			((LinearLayout)findViewById(R.id.view_stats_background)).setBackgroundResource(R.drawable.background_basketball);
		}
		else if (sport.equals("Football")){
			((LinearLayout)findViewById(R.id.view_stats_background)).setBackgroundResource(R.drawable.background_football);
		}
		else if (sport.equals("Soccer")){
			((LinearLayout)findViewById(R.id.view_stats_background)).setBackgroundResource(R.drawable.background_soccer);
		}
		else {
			((LinearLayout)findViewById(R.id.view_stats_background)).setBackgroundResource(R.drawable.background_hockey);
		}
	}
}
