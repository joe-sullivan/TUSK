package com.seniordesign.ultimatescorecard.view;

import com.seniordesign.ultimatescorecard.R;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class ShotIconAdder {
	private RelativeLayout _homeLayout; 
	private RelativeLayout _awayLayout;
	private Context _context;
	private int[] _shotLocation = new int[2];
	
	public ShotIconAdder (RelativeLayout home, RelativeLayout away, Context context){
		_homeLayout = home;
		_awayLayout = away;
		_context = context;
	}
	
	public void setShotLocation(int x, int y){
		_shotLocation[0] = x;
		_shotLocation[1] = y;
	}
	
	public void setShotHitMiss(boolean possession, boolean hitMiss){
		LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lp.leftMargin = _shotLocation[0]-25;
		lp.topMargin = _shotLocation[1]+60;
		ImageView iv = new ImageView(_context);
		if(hitMiss){
			iv.setBackgroundResource(R.drawable.made_shot);
		}
		else{
			iv.setBackgroundResource(R.drawable.missed_shot);
		}
		iv.setLayoutParams(lp);
		if(possession){
			_homeLayout.addView(iv);
		}
		else{
			_awayLayout.addView(iv);
		}
	}
}
