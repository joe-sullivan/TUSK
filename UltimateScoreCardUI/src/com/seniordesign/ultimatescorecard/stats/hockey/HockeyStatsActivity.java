package com.seniordesign.ultimatescorecard.stats.hockey;

import com.seniordesign.ultimatescorecard.R;
import com.seniordesign.ultimatescorecard.data.hockey.HockeyGameLog;
import com.seniordesign.ultimatescorecard.data.hockey.HockeyGameTime;
import com.seniordesign.ultimatescorecard.view.StaticFinalVars;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

public class HockeyStatsActivity extends FragmentActivity{
	private ViewPager _pager;
	private PagerAdapter _pagerAdapter;
	private HockeyGameTime _gti;
	private HockeyGameLog _gameLog;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        _gti = (HockeyGameTime) getIntent().getSerializableExtra(StaticFinalVars.GAME_INFO);
        _gameLog = (HockeyGameLog) getIntent().getSerializableExtra(StaticFinalVars.GAME_LOG);
        int value = getIntent().getIntExtra(StaticFinalVars.DISPLAY_TYPE, 0);
        
        _pager = (ViewPager) findViewById(R.id.statsPager);
        _pagerAdapter = new HockeyStatsPageAdapter(getSupportFragmentManager());
        _pager.setAdapter(_pagerAdapter);
        _pager.setCurrentItem(value);
    }

	public HockeyGameTime getGameInfo(){
    	return _gti;
    }
    
    public HockeyGameLog getGameLog(){
    	return _gameLog;
    }
}

