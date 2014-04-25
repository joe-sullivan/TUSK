package com.seniordesign.ultimatescorecard.stats.hockey;

import java.util.ArrayList;

import com.seniordesign.ultimatescorecard.R;
import com.seniordesign.ultimatescorecard.data.GameInfo;
import com.seniordesign.ultimatescorecard.sqlite.helper.Games;
import com.seniordesign.ultimatescorecard.sqlite.helper.PlayByPlay;
import com.seniordesign.ultimatescorecard.sqlite.helper.Players;
import com.seniordesign.ultimatescorecard.sqlite.helper.ShotChartCoords;
import com.seniordesign.ultimatescorecard.sqlite.helper.Teams;
import com.seniordesign.ultimatescorecard.view.StaticFinalVars;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

public class HockeyStatsActivity extends FragmentActivity{
	private ViewPager _pager;
	private PagerAdapter _pagerAdapter;
	protected GameInfo _gameInfo;
	protected ArrayList<PlayByPlay> _gameLog;
	protected ArrayList<ShotChartCoords> _homeShots, _awayShots;

	protected Players _player;
	protected Teams _team;
	protected ArrayList<Games> _games;
	protected ArrayList<Teams> _teams;

	protected boolean _ifPlayerView, _ifGameView;

	@SuppressWarnings("unchecked")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

		_ifPlayerView = getIntent().getBooleanExtra(StaticFinalVars.IF_PLAYER_VIEW, true);
		_ifGameView = getIntent().getBooleanExtra(StaticFinalVars.IF_GAME_VIEW, true);
        int value = getIntent().getIntExtra(StaticFinalVars.DISPLAY_TYPE, 0);
        _pager = (ViewPager) findViewById(R.id.statsPager);

		if(_ifGameView){
	        _gameInfo = (GameInfo) getIntent().getSerializableExtra(StaticFinalVars.GAME_INFO);
	        _gameLog = (ArrayList<PlayByPlay>) getIntent().getSerializableExtra(StaticFinalVars.GAME_LOG);
	        _homeShots = (ArrayList<ShotChartCoords>) getIntent().getSerializableExtra(StaticFinalVars.SHOT_CHART_HOME);       
	        _awayShots = (ArrayList<ShotChartCoords>) getIntent().getSerializableExtra(StaticFinalVars.SHOT_CHART_AWAY);       
       
	        _pagerAdapter = new HockeyStatsPageAdapter(getSupportFragmentManager(), 2);
		}
		
		else if(_ifPlayerView){
			
	        _player = (Players) getIntent().getSerializableExtra(StaticFinalVars.PLAYER);    
	        _team = (Teams) getIntent().getSerializableExtra(StaticFinalVars.TEAM);    
	        _games = (ArrayList<Games>) getIntent().getSerializableExtra(StaticFinalVars.GAMES); 
	        _teams = (ArrayList<Teams>) getIntent().getSerializableExtra(StaticFinalVars.TEAMS);    
	        
	        _pagerAdapter = new HockeyStatsPageAdapter(getSupportFragmentManager(), 1);
		}

        _pager.setAdapter(_pagerAdapter);
        _pager.setCurrentItem(value);
    }
}

