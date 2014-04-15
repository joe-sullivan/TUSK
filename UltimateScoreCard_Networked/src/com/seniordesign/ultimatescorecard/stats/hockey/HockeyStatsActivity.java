package com.seniordesign.ultimatescorecard.stats.hockey;

import java.util.ArrayList;

import com.seniordesign.ultimatescorecard.R;
import com.seniordesign.ultimatescorecard.data.GameInfo;
import com.seniordesign.ultimatescorecard.sqlite.helper.Games;
import com.seniordesign.ultimatescorecard.sqlite.helper.PlayByPlay;
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
	private GameInfo _gameInfo;
	private ArrayList<PlayByPlay> _gameLog;
	private ArrayList<ShotChartCoords> _homeShots, _awayShots;
	private ArrayList<Games> _games;
	private String _player;
	private ArrayList<Teams> _teams;


	@SuppressWarnings("unchecked")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        _gameInfo = (GameInfo) getIntent().getSerializableExtra(StaticFinalVars.GAME_INFO);
        _gameLog = (ArrayList<PlayByPlay>) getIntent().getSerializableExtra(StaticFinalVars.GAME_LOG);
        _homeShots = (ArrayList<ShotChartCoords>) getIntent().getSerializableExtra(StaticFinalVars.SHOT_CHART_HOME);       
        _awayShots = (ArrayList<ShotChartCoords>) getIntent().getSerializableExtra(StaticFinalVars.SHOT_CHART_AWAY);       
        int value = getIntent().getIntExtra(StaticFinalVars.DISPLAY_TYPE, 0);
        //NEW
        _games = (ArrayList<Games>) getIntent().getSerializableExtra(StaticFinalVars.GAMES);       
        _player = getIntent().getStringExtra(StaticFinalVars.PLAYER_NAME);    
        _teams = (ArrayList<Teams>) getIntent().getSerializableExtra(StaticFinalVars.TEAMS);       
        _pager = (ViewPager) findViewById(R.id.statsPager);
    	if(_player!=null){
	        if(!_player.equals("All Players")){
	        	_pagerAdapter = new HockeyStatsPageAdapter(getSupportFragmentManager(), 1);
	        }
	        else{
	        	_pagerAdapter = new HockeyStatsPageAdapter(getSupportFragmentManager(), 2);
	        }
    	}
    	else{
        	_pagerAdapter = new HockeyStatsPageAdapter(getSupportFragmentManager(), 2);
    	}
        //END NEW
        _pager.setAdapter(_pagerAdapter);
        _pager.setCurrentItem(value);
    }

    public GameInfo getGameInfo(){
    	return _gameInfo;
    }
    
    public ArrayList<PlayByPlay> getGameLog(){
    	return _gameLog;
    }
    
    public ArrayList<ShotChartCoords> getHomeShotChart(){
    	return _homeShots;
    }
    
    public ArrayList<ShotChartCoords> getAwayShotChart(){
    	return _awayShots;
    }
    
    public ViewPager getPager(){
    	return _pager;
    }
    //NEW
    public ArrayList<Games> getGames(){
    	return _games;
    }
    
    public String getPlayer(){
    	return _player;
    }
    
    public ArrayList<Teams> getTeams(){
    	return _teams;
    }
    //END NEW
}

