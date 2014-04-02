package com.seniordesign.ultimatescorecard.stats.soccer;

import java.util.ArrayList;

import com.seniordesign.ultimatescorecard.R;
import com.seniordesign.ultimatescorecard.sqlite.helper.PlayByPlay;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SoccerPlayListFragment extends Fragment{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = (View) inflater.inflate(R.layout.fragment_game_log, container, false);
		view.setBackgroundResource(R.drawable.background_soccer);

        return view;
	}
	
	@Override
	public void onResume() {
		super.onResume();		
		addTextViews();
		((TextView)getView().findViewById(R.id.playByPlaytitle)).setTextColor(Color.WHITE);
	}
	
	private void addTextViews(){
		LinearLayout layout = ((LinearLayout) getView().findViewById(R.id.listofPlays));
		ArrayList<PlayByPlay> log = ((SoccerStatsActivity) getActivity()).getGameLog();
		
		if(layout.getChildCount()==0){
			for(PlayByPlay pbp: log){
				layout.addView(newTextView(pbp.getaction()));	
			}
		}
	}
	
	private TextView newTextView(String teamName){
		final TextView textView = new TextView(getActivity());														//these are all the stuff that you can do statically in xml
		textView.setText(teamName);																			//here, we're dynamically programming them in Java
		textView.setPadding(5,5,5,5);
		textView.setTextSize(16);
		textView.setTextColor(Color.WHITE);
		return textView;
	}
}
