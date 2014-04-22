package com.seniordesign.ultimatescorecard.stats.football;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class FootballStatsPageAdapter extends FragmentStatePagerAdapter {
	private int _numberPages = 2;
	
	public FootballStatsPageAdapter(FragmentManager fm) {
		super(fm);
	}

    @Override
    public Fragment getItem(int position) {
    	if(position == 0){
    		return new FootballBoxscoreFragment();
    	}
    	else{
    		return new FootballPlayListFragment();
    	}
    }

    @Override
    public int getCount() {
        return _numberPages;
    }
}