package com.seniordesign.ultimatescorecard.stats.soccer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class SoccerIndividualStatPageAdapter extends FragmentStatePagerAdapter {
	private int _numberPages = 2;
	
	public SoccerIndividualStatPageAdapter(FragmentManager fm) {
		super(fm);
	}

    @Override
    public Fragment getItem(int position) {
    	if(position == 0){
    		return new SoccerIndividualStatFragment();
    	}
    	else {
    		return new SoccerIndividualShotChartFragment();
    	}
    }

    @Override
    public int getCount() {
        return _numberPages;
    }
}