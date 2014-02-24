package com.seniordesign.ultimatescorecard.stats.soccer;

import com.seniordesign.ultimatescorecard.R;
import com.seniordesign.ultimatescorecard.data.soccer.SoccerGameLog;
import com.seniordesign.ultimatescorecard.data.soccer.SoccerGameTime;
import com.seniordesign.ultimatescorecard.view.StaticFinalVars;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

public class SoccerStatsActivity extends FragmentActivity{
	private ViewPager _pager;
	private PagerAdapter _pagerAdapter;
	private SoccerGameTime _gti;
	private SoccerGameLog _gameLog;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        _gti = (SoccerGameTime) getIntent().getSerializableExtra(StaticFinalVars.GAME_INFO);
        _gameLog = (SoccerGameLog) getIntent().getSerializableExtra(StaticFinalVars.GAME_LOG);
        int value = getIntent().getIntExtra(StaticFinalVars.DISPLAY_TYPE, 0);
        
        _pager = (ViewPager) findViewById(R.id.statsPager);
        _pagerAdapter = new SoccerStatsPageAdapter(getSupportFragmentManager());
        _pager.setAdapter(_pagerAdapter);
        _pager.setCurrentItem(value);
    }

	public SoccerGameTime getGameInfo(){
    	return _gti;
    }
    
    public SoccerGameLog getGameLog(){
    	return _gameLog;
    }
}

