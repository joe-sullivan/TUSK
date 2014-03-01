package com.seniordesign.ultimatescorecard.stats.hockey;

import java.util.ArrayList;

import com.seniordesign.ultimatescorecard.R;
import com.seniordesign.ultimatescorecard.sqlite.helper.ShotChartCoords;
import com.seniordesign.ultimatescorecard.stats.hockey.HockeyStatsActivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

public class HockeyShotChartFragment extends Fragment {
	private RelativeLayout _shotIconsHome;
	private RelativeLayout _shotIconsAway;
	private boolean _showHomeShotChart = false;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = (View) inflater.inflate(R.layout.fragment_shot_chart, container, false);
        setHasOptionsMenu(true);
		return view;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		addCourtImage();
		
		ArrayList<ShotChartCoords> homeShots = ((HockeyStatsActivity) getActivity()).getHomeShotChart();
		ArrayList<ShotChartCoords> awayShots = ((HockeyStatsActivity) getActivity()).getAwayShotChart();
		
		for(ShotChartCoords shot: homeShots){
			int[] location = new int[2];
			location[0] = shot.getx();
			location[1] = shot.gety();
			if(shot.getmade().equals("make")){
				displayShots(true, true, location);
			}
			else if(shot.getmade().equals("miss")){
				displayShots(true, false, location);
			}
		}
		for(ShotChartCoords shot: awayShots){
			int[] location = new int[2];
			location[0] = shot.getx();
			location[1] = shot.gety();
			if(shot.getmade().equals("make")){
				displayShots(false, true, location);
			}
			else if(shot.getmade().equals("miss")){
				displayShots(false, false, location);
			}
		}
	}
	
	private void addCourtImage(){
		ImageView icefloor = new ImageView(getActivity());
		icefloor.setImageDrawable(getResources().getDrawable(R.drawable.icefloor));
		ImageView rinkLines = new ImageView(getActivity());
		rinkLines.setImageDrawable(getResources().getDrawable(R.drawable.hockeyice));
		
		_shotIconsHome = new RelativeLayout(getActivity());
		_shotIconsAway = new RelativeLayout(getActivity());
		RelativeLayout.LayoutParams rp = new RelativeLayout.LayoutParams
				(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
		_shotIconsHome.setLayoutParams(rp);
		_shotIconsAway.setLayoutParams(rp);
		
		((FrameLayout)getView().findViewById(R.id.shotChartFrame)).addView(icefloor);
		((FrameLayout)getView().findViewById(R.id.shotChartFrame)).addView(rinkLines);
		((FrameLayout)getView().findViewById(R.id.shotChartFrame)).addView(_shotIconsHome);
		((FrameLayout)getView().findViewById(R.id.shotChartFrame)).addView(_shotIconsAway);
		((FrameLayout)getView().findViewById(R.id.shotChartFrame)).setOnClickListener(shotChartListener);
		changeShotChart();
	}
	
	private void displayShots(boolean home, boolean hitMiss, int[] shotLocation){
		LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lp.leftMargin = shotLocation[0];
		lp.topMargin = shotLocation[1];
		ImageView iv = new ImageView(getActivity());
		if(hitMiss){
			iv.setBackgroundResource(R.drawable.made_shot);
		}
		else{
			iv.setBackgroundResource(R.drawable.missed_shot);
		}
		iv.setLayoutParams(lp);
		if(home){
			_shotIconsHome.addView(iv);
		}
		else{
			_shotIconsAway.addView(iv);
		}
	}
	
	private OnClickListener shotChartListener = new OnClickListener(){
		@Override
		public void onClick(View view) {
			changeShotChart();
		}		
	};
	
	private void changeShotChart(){
		if(_showHomeShotChart){
			_showHomeShotChart = false;
			String awayAbbv = ((HockeyStatsActivity) getActivity()).getGameInfo().getAwayTeam().getabbv();
			((TextView)getActivity().findViewById(R.id.shot_chart_title)).setText("Shot Chart: " + awayAbbv);
			_shotIconsAway.setVisibility(ImageView.VISIBLE);
			_shotIconsHome.setVisibility(ImageView.INVISIBLE);
		}
		else{
			_showHomeShotChart = true;
			String homeAbbv = ((HockeyStatsActivity) getActivity()).getGameInfo().getHomeTeam().getabbv();
			((TextView)getActivity().findViewById(R.id.shot_chart_title)).setText("Shot Chart: " + homeAbbv);
			_shotIconsHome.setVisibility(ImageView.VISIBLE);
			_shotIconsAway.setVisibility(ImageView.INVISIBLE);
		}
	}
	
//---------------------------------------------------------------------------------------------------------------------------------	

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.stats_shot_chart_menu, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
			case R.id.boxscore:
				((HockeyStatsActivity)getActivity()).getPager().setCurrentItem(0);
				break;				
			case R.id.play_by_play:
				((HockeyStatsActivity)getActivity()).getPager().setCurrentItem(1);
				break;
		}
		return true;
	}
}
