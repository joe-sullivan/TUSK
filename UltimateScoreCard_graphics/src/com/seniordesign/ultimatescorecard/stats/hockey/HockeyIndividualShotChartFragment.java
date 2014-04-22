package com.seniordesign.ultimatescorecard.stats.hockey;

import java.util.ArrayList;

import com.seniordesign.ultimatescorecard.R;
import com.seniordesign.ultimatescorecard.data.GameInfo;
import com.seniordesign.ultimatescorecard.sqlite.hockey.HockeyGames;
import com.seniordesign.ultimatescorecard.sqlite.helper.Games;
import com.seniordesign.ultimatescorecard.sqlite.helper.Players;
import com.seniordesign.ultimatescorecard.sqlite.helper.ShotChartCoords;
import com.seniordesign.ultimatescorecard.sqlite.helper.Teams;
import com.seniordesign.ultimatescorecard.stats.hockey.HockeyIndividualStatActivity;
import com.seniordesign.ultimatescorecard.view.DoubleParamOnClickListener;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class HockeyIndividualShotChartFragment extends Fragment{
	private RelativeLayout _shotIcons;
	protected GameInfo _gameInfo;
	private Button _option1Button, _option2Button, _option3Button;
	private String name;
	private Teams team;
	private ArrayList<ShotChartCoords> shots;
	private ArrayList<Players> players;
	private String player;
	private Games _game;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = (View) inflater.inflate(R.layout.fragment_shot_chart_hockey, container, false);
		setHasOptionsMenu(true);
		return view;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		addCourtImage();
		_option1Button = (Button)(TextView)getView().findViewById(R.id.optionButton1);								//more buttons and setting onClick listeners
		_option2Button = (Button)(TextView)getView().findViewById(R.id.optionButton2);
		_option3Button = (Button)(TextView)getView().findViewById(R.id.optionButton3);
		setTextAndListener(_option1Button, AllShotsListener(), "All Shots");
		setTextAndListener(_option2Button, madeListener(), "Made");
		setTextAndListener(_option3Button, missedListener(), "Missed");
		
		shots = ((HockeyIndividualStatActivity) getActivity())._shots;
		name = ((HockeyIndividualStatActivity) getActivity())._name;
		players = ((HockeyIndividualStatActivity) getActivity())._players;
		team = ((HockeyIndividualStatActivity) getActivity())._team;
		_gameInfo = ((HockeyIndividualStatActivity) getActivity())._gameInfo;
		
		//NEW
		_game = ((HockeyIndividualStatActivity) getActivity())._game;

		String _player = ((HockeyIndividualStatActivity) getActivity())._player;
		if(_player!=null){
			if(!_player.equals("All Players")){
				name = _player;
				TextView homeScore = (TextView)getView().findViewById(R.id.homeScoreTextView);
				homeScore.setText(((HockeyGames)_game).getHomeScoreText());
				TextView awayScore = (TextView)getView().findViewById(R.id.awayScoreTextView);
				homeScore.setText(((HockeyGames)_game).getAwayScoreText());
				TextView homeAbbr = (TextView)getView().findViewById(R.id.homeTextView);
				TextView awayAbbr = (TextView)getView().findViewById(R.id.awayTextView);
			}
		}
		else{
			TextView homeScore = (TextView)getView().findViewById(R.id.homeScoreTextView);
			homeScore.setText(_gameInfo.getHomeScore());
			TextView awayScore = (TextView)getView().findViewById(R.id.awayScoreTextView);
			awayScore.setText(_gameInfo.getAwayScore());
			TextView homeAbbr = (TextView)getView().findViewById(R.id.homeTextView);
			homeAbbr.setText(_gameInfo.getHomeTeam().getabbv());
			TextView awayAbbr = (TextView)getView().findViewById(R.id.awayTextView);
			awayAbbr.setText(_gameInfo.getAwayTeam().getabbv());
		}
		TextView nameText = (TextView)getView().findViewById(R.id.gameClock);

		//END NEW
		if(name.equals(team.getabbv() + " Stats")){
			nameText.setText(team.getabbv());
			for(ShotChartCoords shot: shots){
				if(shot.gettid()==team.gettid()){

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
		
		else{
			nameText.setText(name);

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
		RelativeLayout.LayoutParams rp = new RelativeLayout.LayoutParams
				(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		
		_shotIcons= new RelativeLayout(getActivity());
		_shotIcons.setLayoutParams(rp);
		
		((RelativeLayout)getView().findViewById(R.id.interactiveFrame)).addView(_shotIcons);
	}
	
	private void displayShots(boolean hitMiss, int[] shotLocation){
		LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		//lp.leftMargin = shotLocation[0]+25;
		//lp.topMargin = shotLocation[1]-60;
		ImageView iv = new ImageView(getActivity());
		if(hitMiss){
			iv.setBackgroundResource(R.drawable.made_shot);
			Bitmap b = ((BitmapDrawable)iv.getBackground()).getBitmap();
			int w = b.getWidth();
			int h = b.getHeight();
			lp.leftMargin = shotLocation[0]-w/2;
			lp.topMargin = shotLocation[1]-h/2;
		}
		else{
			iv.setBackgroundResource(R.drawable.missed_shot);
			Bitmap b = ((BitmapDrawable)iv.getBackground()).getBitmap();
			int w = b.getWidth();
			int h = b.getHeight();
			lp.leftMargin = shotLocation[0]-w/2;
			lp.topMargin = shotLocation[1]-h/2;
		}
		iv.setLayoutParams(lp);
		_shotIcons.addView(iv);
	}	
	
	public OnClickListener madeListener(){
		OnClickListener madeListener = new OnClickListener(){
			@Override
			public void onClick(View v) {
				_shotIcons.removeAllViews();
				if(name.equals(team.getabbv() + " Stats")){
					for(ShotChartCoords shot: shots){
						int[] location = new int[2];
						location[0] = shot.getx();
						location[1] = shot.gety();
						if(shot.getmade().equals("make")){
							displayShots(true, location);
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
						}
					}
				}
			}
		};
		return madeListener;
	}
	
	public OnClickListener missedListener(){
		OnClickListener missedListener = new OnClickListener(){
			@Override
			public void onClick(View v) {
				_shotIcons.removeAllViews();
				if(name.equals(team.getabbv() + " Stats")){
					for(ShotChartCoords shot: shots){
						int[] location = new int[2];
						location[0] = shot.getx();
						location[1] = shot.gety();
						if(shot.getmade().equals("miss")){
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
							if(shot.getmade().equals("miss")){
								displayShots(false, location);
							}
						}
					}
				}
			}
		};
		return missedListener;
	}
	
	public OnClickListener AllShotsListener(){
		OnClickListener AllShotsListener = new OnClickListener(){
			@Override
			public void onClick(View v) {
				_shotIcons.removeAllViews();
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
		};
		return AllShotsListener;
	}
	
	private void setTextAndListener(Button button, OnClickListener listener, String text){
		button.setText(text);
		button.setOnClickListener(listener);
	}
	
}
