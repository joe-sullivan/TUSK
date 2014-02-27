package com.seniordesign.ultimatescorecard.stats.hockey;

import java.util.ArrayList;

import com.seniordesign.ultimatescorecard.R;
import com.seniordesign.ultimatescorecard.sqlite.helper.PlayByPlay;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HockeyPlayListFragment extends Fragment{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = (View) inflater.inflate(R.layout.fragment_game_log, container, false);
        return view;
	}
	
	@Override
	public void onResume() {
		super.onResume();		
		addTextViews();
	}
	
	private void addTextViews(){
		LinearLayout layout = ((LinearLayout) getView().findViewById(R.id.listofPlays));
		ArrayList<PlayByPlay> log = ((HockeyStatsActivity) getActivity()).getGameLog();
		
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
		textView.setTextSize(10);
		return textView;
	}
}
