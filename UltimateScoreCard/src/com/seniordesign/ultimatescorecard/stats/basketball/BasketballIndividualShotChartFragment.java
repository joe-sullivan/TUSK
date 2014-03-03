package com.seniordesign.ultimatescorecard.stats.basketball;

import java.util.ArrayList;

import com.seniordesign.ultimatescorecard.R;
import com.seniordesign.ultimatescorecard.sqlite.helper.Players;
import com.seniordesign.ultimatescorecard.sqlite.helper.ShotChartCoords;
import com.seniordesign.ultimatescorecard.sqlite.helper.Teams;
import com.seniordesign.ultimatescorecard.stats.basketball.BasketballIndividualStatActivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class BasketballIndividualShotChartFragment extends Fragment{
	private RelativeLayout _shotIcons;
	
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
		ArrayList<ShotChartCoords> shots = ((BasketballIndividualStatActivity) getActivity())._shots;
		String name = ((BasketballIndividualStatActivity) getActivity())._name;
		ArrayList<Players> players = ((BasketballIndividualStatActivity) getActivity())._players;
		Teams team = ((BasketballIndividualStatActivity) getActivity())._team;

		
		if(name.equals(team.getabbv() + " Stats")){
			for(ShotChartCoords shot: shots){
				int[] location = new int[2];
				location[0] = shot.getx();
				location[1] = shot.gety();
				if(shot.getmade().equals("make")){
					displayShots(true, location);
				}
				else if(shot.getmade().equals("miss")){
					displayShots(false, location);
				}		
			}	
		}
		
		else{
		
			Players player = null;
			for(Players p: players){
				if(p.getpname().equals(name)){
					player = p;
				}
			}		
	
			for(ShotChartCoords shot: shots){
				if(player.getpid()==shot.getpid()){
					int[] location = new int[2];
					location[0] = shot.getx();
					location[1] = shot.gety();
					if(shot.getmade().equals("make")){
						displayShots(true, location);
					}
					else if(shot.getmade().equals("miss")){
						displayShots(false, location);
					}
				}
			}
		}
	}
	
	private void addCourtImage(){
		ImageView hardwood = new ImageView(getActivity());
		hardwood.setImageDrawable(getResources().getDrawable(R.drawable.hardwood));
		ImageView courtLines = new ImageView(getActivity());
		courtLines.setImageDrawable(getResources().getDrawable(R.drawable.basketballcourt));
		
		_shotIcons= new RelativeLayout(getActivity());
		RelativeLayout.LayoutParams rp = new RelativeLayout.LayoutParams
				(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
		_shotIcons.setLayoutParams(rp);
		
		((FrameLayout)getView().findViewById(R.id.shotChartFrame)).addView(hardwood);
		((FrameLayout)getView().findViewById(R.id.shotChartFrame)).addView(courtLines);
		((FrameLayout)getView().findViewById(R.id.shotChartFrame)).addView(_shotIcons);
	}
	
	private void displayShots(boolean hitMiss, int[] shotLocation){
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
		_shotIcons.addView(iv);
	}	
	
}
