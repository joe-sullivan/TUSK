package com.seniordesign.ultimatescorecard.stats.soccer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class SoccerStatsPageAdapter extends FragmentStatePagerAdapter {
	//NEW
	private int _numberPages;
		
	public SoccerStatsPageAdapter(FragmentManager fm, int pages) {
		super(fm);
		_numberPages = pages;
	}
	//END NEW

    @Override
    public Fragment getItem(int position) {
    	if(position == 0){
    		return new SoccerBoxscoreFragment();
    	}
    	else {
    		return new SoccerPlayListFragment();
    	}
    }

    @Override
    public int getCount() {
        return _numberPages;
    }
}