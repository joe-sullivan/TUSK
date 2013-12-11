package com.seniordesign.ultimatescorecard.stats;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class StatsPageAdapter extends FragmentStatePagerAdapter {
	private int _numberPages = 2;
	
	public StatsPageAdapter(FragmentManager fm) {
		super(fm);
	}

    @Override
    public Fragment getItem(int position) {
    	if(position == 0){
    		return new BoxscoreFragment();
    	}
    	else{
    		return new PlayListFragment();
    	}
    }

    @Override
    public int getCount() {
        return _numberPages;
    }
}