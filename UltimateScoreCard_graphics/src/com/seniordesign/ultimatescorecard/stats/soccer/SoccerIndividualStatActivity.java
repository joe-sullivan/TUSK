package com.seniordesign.ultimatescorecard.stats.soccer;

import java.util.ArrayList;

import com.seniordesign.ultimatescorecard.R;
import com.seniordesign.ultimatescorecard.data.GameInfo;
import com.seniordesign.ultimatescorecard.sqlite.soccer.SoccerDatabaseHelper;
import com.seniordesign.ultimatescorecard.sqlite.soccer.SoccerGames;
import com.seniordesign.ultimatescorecard.sqlite.helper.*;
import com.seniordesign.ultimatescorecard.view.StaticFinalVars;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

public class SoccerIndividualStatActivity extends FragmentActivity{
	private ViewPager _pager;
	private PagerAdapter _pagerAdapter;
	protected SoccerDatabaseHelper _db;
	protected String _name;
	protected Teams _team;
	protected SoccerGames _game;
	protected ArrayList<ShotChartCoords> _shots;
	protected GameInfo _gameInfo;
	protected boolean _average, _ifPlayerView, _ifGameView, _ifTeamStats, _home;
	protected Players _player;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stats);
		
		_db = new SoccerDatabaseHelper(SoccerIndividualStatActivity.this);
		
		_ifPlayerView = getIntent().getBooleanExtra(StaticFinalVars.IF_PLAYER_VIEW, true);
		_ifGameView = getIntent().getBooleanExtra(StaticFinalVars.IF_GAME_VIEW, true);
		int value = getIntent().getIntExtra(StaticFinalVars.DISPLAY_TYPE, 0);

		if(_ifGameView){
			_gameInfo = (GameInfo) getIntent().getSerializableExtra(StaticFinalVars.GAME_INFO);
			_shots = (ArrayList<ShotChartCoords>) getIntent().getSerializableExtra(StaticFinalVars.SHOT_CHART);
			_home = getIntent().getBooleanExtra(StaticFinalVars.HOME_OR_AWAY, true);
			_name = getIntent().getStringExtra(StaticFinalVars.PLAYER_NAME);
			_game = (SoccerGames) _db.getGame(_gameInfo.getgid());
			
			_ifTeamStats = _name.equals(_gameInfo.getHomeTeam().getabbv()+" Stats") || 
					_name.equals(_gameInfo.getAwayTeam().getabbv()+" Stats");
			if(!_ifTeamStats){
				if(_home){
					for(Players p: _gameInfo.getHomePlayers()){
						if(p.getpname().equals(_name)){
							_player = p;
						}
					}
				}
				else if(!_home){
					for(Players p: _gameInfo.getAwayPlayers()){
						if(p.getpname().equals(_name)){
							_player = p;
						}
					}
				}
				_shots = (ArrayList<ShotChartCoords>) _db.getAllPlayerShotsGame(_gameInfo.getgid(), _player.getpid());
				//_stats will be set in Fragment
			}
		}
		else if(_ifPlayerView){
			_average = getIntent().getBooleanExtra(StaticFinalVars.AVERAGE, false);
			_player = (Players) getIntent().getSerializableExtra(StaticFinalVars.PLAYER);
			_team = (Teams) getIntent().getSerializableExtra(StaticFinalVars.TEAM);
			
			if(!_average){
				_game = (SoccerGames) getIntent().getSerializableExtra(StaticFinalVars.GAME);
				
				Teams home = _db.getTeam(_game.gethomeid());
				Teams away = _db.getTeam(_game.getawayid());
				long g_id = _game.getgid();
				_gameInfo = new GameInfo(home, away, null, null, g_id);
				_gameInfo.setAwayScore(_game.getAwayScoreText());
				_gameInfo.setHomeScore(_game.getHomeScoreText());
				
				_shots = (ArrayList<ShotChartCoords>) _db.getAllPlayerShotsGame(_game.getgid(), _player.getpid());
			}
        }

        _pager = (ViewPager) findViewById(R.id.statsPager);
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
