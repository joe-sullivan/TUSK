package com.seniordesign.ultimatescorecard.stats.basketball;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class BasketballStatsPageAdapter extends FragmentStatePagerAdapter {
	private int _numberPages = 2;
	
	public BasketballStatsPageAdapter(FragmentManager fm) {
		super(fm);
	}

    @Override
    public Fragment getItem(int position) {
    	if(position == 0){
    		return new BasketballBoxscoreFragment();
    	}
    	else {
    		return new BasketballPlayListFragment();
    	}
    }

    @Override
    public int getCount() {
        return _numberPages;
    }
}