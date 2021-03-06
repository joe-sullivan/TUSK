package com.seniordesign.ultimatescorecard.stats.soccer;

import java.util.ArrayList;

import com.seniordesign.ultimatescorecard.R;
import com.seniordesign.ultimatescorecard.data.GameInfo;
import com.seniordesign.ultimatescorecard.sqlite.soccer.SoccerDatabaseHelper;
import com.seniordesign.ultimatescorecard.sqlite.soccer.SoccerGames;
import com.seniordesign.ultimatescorecard.sqlite.basketball.BasketballGames;
import com.seniordesign.ultimatescorecard.sqlite.helper.*;
import com.seniordesign.ultimatescorecard.stats.ViewStatsActivity;
import com.seniordesign.ultimatescorecard.stats.basketball.BasketballIndividualStatPageAdapter;
import com.seniordesign.ultimatescorecard.view.StaticFinalVars;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

public class SoccerIndividualStatActivity extends FragmentActivity{
	private ViewPager _pager;
	private PagerAdapter _pagerAdapter;
	protected SoccerDatabaseHelper _soccer_db;
	protected String _name;
	protected long g_id;
	protected Teams _team, _team2;
	protected ArrayList<Players> _players;
	protected boolean _home;
	protected SoccerGames _game;
	protected ArrayList<ShotChartCoords> _shots;
	protected GameInfo _gameInfo;
	protected String _player;
	protected ArrayList<SoccerGames> _games;
	protected boolean _average = false;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stats);
		
		_team = (Teams) getIntent().getSerializableExtra(StaticFinalVars.TEAM_INFO);
		_players = (ArrayList<Players>) getIntent().getSerializableExtra(StaticFinalVars.PLAYERS_INFO);
		g_id = (Long) getIntent().getSerializableExtra(StaticFinalVars.GAME_ID);
		_name = getIntent().getStringExtra(StaticFinalVars.PLAYER_NAME);
		_home = getIntent().getBooleanExtra(StaticFinalVars.HOME_OR_AWAY, true);
		_shots = (ArrayList<ShotChartCoords>) getIntent().getSerializableExtra(StaticFinalVars.SHOT_CHART);
		//NEW
		_soccer_db = new SoccerDatabaseHelper(SoccerIndividualStatActivity.this);

		_player = getIntent().getStringExtra(StaticFinalVars.PLAYER);
		_team2 = (Teams) getIntent().getSerializableExtra(StaticFinalVars.TEAM_INFO2);
		//AVE
		_games = (ArrayList<SoccerGames>) getIntent().getSerializableExtra(StaticFinalVars.GAMES);
		_average = getIntent().getBooleanExtra(StaticFinalVars.AVERAGE, false);
				
		if(_name!=null){
			if(_shots.isEmpty()&&!_name.equals(_team.getabbv()+" Stats")){
				_shots = (ArrayList<ShotChartCoords>) _soccer_db.getAllTeamShotsGame(_team.gettid(), g_id);
			}
			else if(_shots.isEmpty()&&!_name.equals(_team2.getabbv()+" Stats")){
				_shots = (ArrayList<ShotChartCoords>) _soccer_db.getAllTeamShotsGame(_team2.gettid(), g_id);
			}
			else if(_shots.isEmpty()&&!_name.equals("All Players")){
				Players p2 = null;
				for(Players p: _players){
					if(p.getpname().equals(_name)){
						p2 = p;
						break;
					}
				}
				long t_id = -1;
				if(p2.gettid()==_team.gettid()){
					t_id = _team.gettid();
				}
				else if(p2.gettid()==_team2.gettid()){
					t_id = _team2.gettid();
				}
				_shots = (ArrayList<ShotChartCoords>) _soccer_db.getAllTeamShotsGame(t_id, g_id);
			}
		}
		if(_player!=null){
			if(_shots.isEmpty()&&!_player.equals("All Players")){
				Players p2 = null;
				for(Players p: _players){
					if(p.getpname().equals(_player)){
						p2 = p;
						break;
					}
				}
				long t_id = -1;
				if(p2.gettid()==_team.gettid()){
					t_id = _team.gettid();
				}
				else if(p2.gettid()==_team2.gettid()){
					t_id = _team2.gettid();
				}
				_shots = (ArrayList<ShotChartCoords>) _soccer_db.getAllTeamShotsGame(t_id, g_id);
			}
		}

		//END NEW
		_gameInfo = (GameInfo) getIntent().getSerializableExtra(StaticFinalVars.GAME_INFO);
		int value = getIntent().getIntExtra(StaticFinalVars.DISPLAY_TYPE, 0);

		_soccer_db = new SoccerDatabaseHelper(getApplicationContext());
		_game = (SoccerGames) _soccer_db.getGame(g_id);

        _pager = (ViewPager) findViewById(R.id.statsPager);
      //AVE
        if(_average){
            _pagerAdapter = new SoccerIndividualStatPageAdapter(getSupportFragmentManager(), 1);
        }
        else{
        	_pagerAdapter = new SoccerIndividualStatPageAdapter(getSupportFragmentManager(), 2);
        }        
        _pager.setAdapter(_pagerAdapter);
        _pager.setCurrentItem(value);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}
}
