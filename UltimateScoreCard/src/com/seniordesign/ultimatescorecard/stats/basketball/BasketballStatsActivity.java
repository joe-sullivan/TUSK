package com.seniordesign.ultimatescorecard.stats.basketball;

import java.util.ArrayList;

import com.seniordesign.ultimatescorecard.R;
import com.seniordesign.ultimatescorecard.data.basketball.BasketballGameInfo;
import com.seniordesign.ultimatescorecard.data.basketball.BasketballGameLog;
import com.seniordesign.ultimatescorecard.data.basketball.BasketballGameTime;
import com.seniordesign.ultimatescorecard.sqlite.helper.PlayByPlay;
import com.seniordesign.ultimatescorecard.sqlite.helper.Teams;
import com.seniordesign.ultimatescorecard.view.StaticFinalVars;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

public class BasketballStatsActivity extends FragmentActivity{
	private ViewPager _pager;
	private PagerAdapter _pagerAdapter;
	private BasketballGameInfo _gameInfo;
	private ArrayList<PlayByPlay> _gameLog;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        /*
        long _gameTest = (Long) getIntent().getExtra(StaticFinalVars.GAME_TEST);
*/
        _gameInfo = (BasketballGameInfo) getIntent().getSerializableExtra(StaticFinalVars.GAME_INFO);
        _gameLog = (ArrayList<PlayByPlay>) getIntent().getSerializableExtra(StaticFinalVars.GAME_LOG);
       
        int value = getIntent().getIntExtra(StaticFinalVars.DISPLAY_TYPE, 0);
        
        _pager = (ViewPager) findViewById(R.id.statsPager);
        _pagerAdapter = new BasketballStatsPageAdapter(getSupportFragmentManager());
        _pager.setAdapter(_pagerAdapter);
        _pager.setCurrentItem(value);
    }

    public BasketballGameInfo getGameInfo(){
    	return _gameInfo;
    }
    
    public ArrayList<PlayByPlay> getGameLog(){
    	return _gameLog;
    }
}

