package com.seniordesign.ultimatescorecard.substitution;

import com.seniordesign.ultimatescorecard.R;
import com.seniordesign.ultimatescorecard.data.basketball.BasketballGameInfo;
import com.seniordesign.ultimatescorecard.data.basketball.BasketballGameTime;
import com.seniordesign.ultimatescorecard.view.StaticFinalVars;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class SubstitutionActivity extends FragmentActivity{
	private BasketballGameInfo _gameInfo;
	private Fragment _active = new ActivePageFragment();
	private Fragment _bench = new BenchPageFragment();
	private boolean _homeTeam = true;
	private boolean _activePage = true;
	private String[] _swapPlayer = new String[2];
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_substitution);
		_gameInfo = (BasketballGameInfo) getIntent().getSerializableExtra(StaticFinalVars.SUB_INFO);
		
		TextView view = (TextView)findViewById(R.id.homeTeamSubText);
		view.setOnClickListener(setHomeTeam);
		view.setBackgroundColor(getResources().getColor(R.color.robin_egg_blue));
		
		TextView view2 = (TextView)findViewById(R.id.awayTeamSubText);
		view2.setOnClickListener(setAwayTeam);
		
		addFragment(_active);
	}
	
	private OnClickListener setHomeTeam = new OnClickListener(){
		@Override
		public void onClick(View v) {
			_homeTeam = true;
			findViewById(R.id.homeTeamSubText).setBackgroundColor(getResources().getColor(R.color.robin_egg_blue));
			findViewById(R.id.awayTeamSubText).setBackgroundColor(getResources().getColor(R.color.white));
			if(_activePage){
				((ActivePageFragment) _active).refresh();
			}
			else{
				switchPages();
				((BenchPageFragment) _bench).refresh();
			}
		}
	};
	
	private OnClickListener setAwayTeam = new OnClickListener(){
		@Override
		public void onClick(View v) {
			_homeTeam = false;
			findViewById(R.id.homeTeamSubText).setBackgroundColor(getResources().getColor(R.color.white));
			findViewById(R.id.awayTeamSubText).setBackgroundColor(getResources().getColor(R.color.robin_egg_blue));
			if(_activePage){
				((ActivePageFragment) _active).refresh();
			}
			else{
				switchPages();
				((BenchPageFragment) _bench).refresh();
			}
		}
	};
	
	private void addFragment(Fragment newFragment){
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.add(R.id.fragmentLayout, newFragment);
		transaction.commit();
	}
	
	public void switchPages(){
		if(_activePage){
			fragmentSlideReplace(_active, _bench);
			_activePage = false;
		}
		else{
			fragmentSlideReplace(_bench, _active);
			_activePage = true;
		}
	}
	
	private void fragmentSlideReplace(Fragment previous, Fragment newFragment){
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		if(_activePage){
			transaction.setCustomAnimations(R.anim.slide_in_right,R.anim.slide_out_left);
		}
		else{			
			transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
		}
		transaction.replace(previous.getId(), newFragment);
		transaction.commit();
	}
	
	public BasketballGameInfo getTeamInfo(){
		return _gameInfo;
	}
	
	public boolean getHomeTeam(){
		return _homeTeam;
	}
	
	public void setPlayerOut(String name){
		_swapPlayer[0] = name;
	}
	
	public void setPlayerIn(String name){
		_swapPlayer[1] = name;
		confirmSwitch();
	}
	
	private void confirmSwitch(){
		if(_homeTeam){
			_gameInfo.swapPlayer(_swapPlayer[0], _swapPlayer[1],_homeTeam);
		}
		else{
			_gameInfo.swapPlayer(_swapPlayer[0], _swapPlayer[1], _homeTeam);
		}
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent();
		intent.putExtra(StaticFinalVars.SUB_INFO, _gameInfo);
		setResult(Activity.RESULT_OK, intent);
		finish();	
	}
}
