package com.seniordesign.ultimatescorecard.stats;

import com.seniordesign.ultimatescorecard.R;
import com.seniordesign.ultimatescorecard.data.BasketballGameLog;
import com.seniordesign.ultimatescorecard.data.BasketballGameTime;
import com.seniordesign.ultimatescorecard.view.StaticFinalVars;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

public class StatsActivity extends FragmentActivity{
	private ViewPager _pager;
	private PagerAdapter _pagerAdapter;
	private BasketballGameTime _gti;
	private BasketballGameLog _gameLog;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        _gti = (BasketballGameTime) getIntent().getSerializableExtra(StaticFinalVars.GAME_INFO);
        _gameLog = (BasketballGameLog) getIntent().getSerializableExtra(StaticFinalVars.GAME_LOG);
        int value = getIntent().getIntExtra(StaticFinalVars.DISPLAY_TYPE, 0);
        
        _pager = (ViewPager) findViewById(R.id.statsPager);
        _pagerAdapter = new StatsPageAdapter(getSupportFragmentManager());
        _pager.setAdapter(_pagerAdapter);
        _pager.setCurrentItem(value);
    }

    public BasketballGameTime getGameInfo(){
    	return _gti;
    }
    
    public BasketballGameLog getGameLog(){
    	return _gameLog;
    }
}

