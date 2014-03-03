package com.seniordesign.ultimatescorecard.stats.basketball;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class BasketballIndividualStatPageAdapter extends FragmentStatePagerAdapter {
	private int _numberPages = 2;
	
	public BasketballIndividualStatPageAdapter(FragmentManager fm) {
		super(fm);
	}

    @Override
    public Fragment getItem(int position) {
    	if(position == 0){
    		return new BasketballIndividualStatFragment();
    	}
    	else {
    		return new BasketballIndividualShotChartFragment();
    	}
    }

    @Override
    public int getCount() {
        return _numberPages;
    }
}