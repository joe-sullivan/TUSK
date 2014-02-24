package com.seniordesign.ultimatescorecard.stats.basketball;

import com.seniordesign.ultimatescorecard.R;
import com.seniordesign.ultimatescorecard.data.basketball.BasketballTeam;
import com.seniordesign.ultimatescorecard.view.StaticFinalVars;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BasketballBoxscoreFragment extends Fragment{ 
	private boolean _lookingAtHome = true;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = (View) inflater.inflate(R.layout.fragment_boxscore, container, false);
        return view;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		((TextView)getView().findViewById(R.id.homeTeamStatText)).setText(((BasketballStatsActivity) getActivity()).getGameInfo().getTheHomeTeam().getTeamName());
		((TextView)getView().findViewById(R.id.awayTeamStatText)).setText(((BasketballStatsActivity) getActivity()).getGameInfo().getTheAwayTeam().getTeamName());
		
		if(_lookingAtHome){
			getView().findViewById(R.id.homeTeamStatText).setBackgroundColor(getResources().getColor(R.color.robin_egg_blue));
			getView().findViewById(R.id.awayTeamStatText).setBackgroundColor(getResources().getColor(R.color.white));
		}
		else{
			getView().findViewById(R.id.homeTeamStatText).setBackgroundColor(getResources().getColor(R.color.white));
			getView().findViewById(R.id.awayTeamStatText).setBackgroundColor(getResources().getColor(R.color.robin_egg_blue));
		}
		
		getView().findViewById(R.id.homeTeamStatText).setOnClickListener(homeTeamListener);
		getView().findViewById(R.id.awayTeamStatText).setOnClickListener(awayTeamListener);
		
		removeAllViews();
		addTextViews();
	}
	
	private void addTextViews(){
		LinearLayout layout = ((LinearLayout) getView().findViewById(R.id.playerListLayout));
		BasketballTeam team = null;
		if(_lookingAtHome){
			team = ((BasketballStatsActivity) getActivity()).getGameInfo().getTheHomeTeam();
		}
		else{
			team = ((BasketballStatsActivity) getActivity()).getGameInfo().getTheAwayTeam();
		}
		for(int i=0; i<team.numberPlayers(); i++){
			layout.addView(newTextView(team.getPlayer(i).getName()));	
		}
	}
	
	private void removeAllViews(){
		LinearLayout layout = ((LinearLayout) getView().findViewById(R.id.playerListLayout));
		layout.removeAllViews();
	}
	
	private TextView newTextView(String teamName){
		final TextView textView = new TextView(getActivity());
		textView.setText(teamName);
		textView.setPadding(5,5,5,5);
		textView.setTextSize(20);
		textView.setOnClickListener(selectPlayerListener);
		return textView;
	}
	
	private OnClickListener homeTeamListener = new OnClickListener(){
		@Override
		public void onClick(View v) {
			getView().findViewById(R.id.homeTeamStatText).setBackgroundColor(getResources().getColor(R.color.robin_egg_blue));
			getView().findViewById(R.id.awayTeamStatText).setBackgroundColor(getResources().getColor(R.color.white));
			if(!_lookingAtHome){
				_lookingAtHome = true;
				removeAllViews();
				addTextViews();
				
			}
		}
	};
	
	private OnClickListener awayTeamListener = new OnClickListener(){
		@Override
		public void onClick(View v) {
			getView().findViewById(R.id.homeTeamStatText).setBackgroundColor(getResources().getColor(R.color.white));
			getView().findViewById(R.id.awayTeamStatText).setBackgroundColor(getResources().getColor(R.color.robin_egg_blue));
			if(_lookingAtHome){
				_lookingAtHome = false;
				removeAllViews();
				addTextViews();			
			}
		}
	};
	
	private OnClickListener selectPlayerListener = new OnClickListener(){
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(getActivity().getApplicationContext(), BasketballIndividualStatActivity.class);
			if(_lookingAtHome){
				intent.putExtra(StaticFinalVars.TEAM_INFO, ((BasketballStatsActivity) getActivity()).getGameInfo().getTheHomeTeam());
			}
			else{
				intent.putExtra(StaticFinalVars.TEAM_INFO, ((BasketballStatsActivity) getActivity()).getGameInfo().getTheAwayTeam());
			}
			intent.putExtra(StaticFinalVars.PLAYER_NAME, ((TextView)v).getText().toString());
			startActivity(intent);
		}
	};
}
