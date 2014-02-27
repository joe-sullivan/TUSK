package com.seniordesign.ultimatescorecard.stats.hockey;

import java.util.ArrayList;

import com.seniordesign.ultimatescorecard.R;
import com.seniordesign.ultimatescorecard.data.hockey.HockeyGameInfo;
import com.seniordesign.ultimatescorecard.data.hockey.HockeyGameLog;
import com.seniordesign.ultimatescorecard.data.hockey.HockeyGameTime;
import com.seniordesign.ultimatescorecard.sqlite.helper.PlayByPlay;
import com.seniordesign.ultimatescorecard.view.StaticFinalVars;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

public class HockeyStatsActivity extends FragmentActivity{
	private ViewPager _pager;
	private PagerAdapter _pagerAdapter;
	private HockeyGameInfo _gameInfo;
	private ArrayList<PlayByPlay> _gameLog;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        _gameInfo = (HockeyGameInfo) getIntent().getSerializableExtra(StaticFinalVars.GAME_INFO);
        _gameLog = (ArrayList<PlayByPlay>) getIntent().getSerializableExtra(StaticFinalVars.GAME_LOG);
        int value = getIntent().getIntExtra(StaticFinalVars.DISPLAY_TYPE, 0);
        
        _pager = (ViewPager) findViewById(R.id.statsPager);
        _pagerAdapter = new HockeyStatsPageAdapter(getSupportFragmentManager());
        _pager.setAdapter(_pagerAdapter);
        _pager.setCurrentItem(value);
    }

	public HockeyGameInfo getGameInfo(){
    	return _gameInfo;
    }
    
    public ArrayList<PlayByPlay> getGameLog(){
    	return _gameLog;
    }
}

