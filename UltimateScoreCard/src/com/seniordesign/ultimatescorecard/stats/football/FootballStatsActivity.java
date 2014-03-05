package com.seniordesign.ultimatescorecard.stats.football;

import com.seniordesign.ultimatescorecard.R;
import com.seniordesign.ultimatescorecard.data.GameLog;
import com.seniordesign.ultimatescorecard.data.football.FootballGameLog;
import com.seniordesign.ultimatescorecard.data.football.FootballGameTime;
import com.seniordesign.ultimatescorecard.view.StaticFinalVars;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

public class FootballStatsActivity extends FragmentActivity{
	private ViewPager _pager;
	private PagerAdapter _pagerAdapter;
	private FootballGameTime _gti;
	private FootballGameLog _gameLog;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        _gti = (FootballGameTime) getIntent().getSerializableExtra(StaticFinalVars.GAME_INFO);
        _gameLog = (FootballGameLog) getIntent().getSerializableExtra(StaticFinalVars.GAME_LOG);
        int value = getIntent().getIntExtra(StaticFinalVars.DISPLAY_TYPE, 0);
        
        _pager = (ViewPager) findViewById(R.id.statsPager);
        _pagerAdapter = new FootballStatsPageAdapter(getSupportFragmentManager());
        _pager.setAdapter(_pagerAdapter);
        _pager.setCurrentItem(value);
    }

	public FootballGameTime getGameInfo(){
    	return _gti;
    }
    
    public GameLog getGameLog(){
    	return _gameLog;
    }
}

