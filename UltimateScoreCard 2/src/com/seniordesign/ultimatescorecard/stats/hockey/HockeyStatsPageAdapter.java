package com.seniordesign.ultimatescorecard.stats.hockey;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class HockeyStatsPageAdapter extends FragmentStatePagerAdapter {
	private int _numberPages = 2;
	
	public HockeyStatsPageAdapter(FragmentManager fm) {
		super(fm);
	}

    @Override
    public Fragment getItem(int position) {
    	if(position == 0){
    		return new HockeyBoxscoreFragment();
    	}
    	else {
    		return new HockeyPlayListFragment();
    	}
    }

    @Override
    public int getCount() {
        return _numberPages;
    }
}