package com.seniordesign.ultimatescorecard.stats.hockey;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class HockeyStatsPageAdapter extends FragmentStatePagerAdapter {
	//NEW
	private int _numberPages;
		
	public HockeyStatsPageAdapter(FragmentManager fm, int pages) {
		super(fm);
		_numberPages = pages;
	}
	//END NEW
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