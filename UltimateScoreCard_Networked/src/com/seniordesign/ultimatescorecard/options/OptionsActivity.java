package com.seniordesign.ultimatescorecard.options;

import com.seniordesign.ultimatescorecard.R;
import com.seniordesign.ultimatescorecard.view.StaticFinalVars;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class OptionsActivity extends FragmentActivity {
	private Fragment _gameClockMgmt = new ClockManagementFragment();
	private Fragment _graphicsMgmt = new GraphicsManagementFragment();
	private String _whichPage = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_options);
		
		TextView view = (TextView)findViewById(R.id.gameClocksText);
		view.setOnClickListener(managingGameClock);
		view.setBackgroundColor(getResources().getColor(R.color.robin_egg_blue));
		
		TextView view2 = (TextView)findViewById(R.id.graphicalText);
		view2.setOnClickListener(managingGraphics);
		
		_whichPage = StaticFinalVars.GAME_CLOCK;
		this.addFragment(_gameClockMgmt);
	}

	private OnClickListener managingGameClock = new OnClickListener(){
		@Override
		public void onClick(View v) {
			if(!_whichPage.equals(StaticFinalVars.GAME_CLOCK)){
				findViewById(R.id.gameClocksText).setBackgroundColor(getResources().getColor(R.color.robin_egg_blue));
				findViewById(R.id.graphicalText).setBackgroundColor(getResources().getColor(R.color.white));
				switchPages(_whichPage, StaticFinalVars.GAME_CLOCK);
			}
		}
	};
	
	private OnClickListener managingGraphics = new OnClickListener(){
		@Override
		public void onClick(View v) {
			if(!_whichPage.equals(StaticFinalVars.GRAPHICS)){
				findViewById(R.id.gameClocksText).setBackgroundColor(getResources().getColor(R.color.white));
				findViewById(R.id.graphicalText).setBackgroundColor(getResources().getColor(R.color.robin_egg_blue));
				switchPages(_whichPage, StaticFinalVars.GRAPHICS);
			}
		}
	};
	
	private void addFragment(Fragment newFragment){
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.add(R.id.fragmentLayout, newFragment);
		transaction.commit();
	}
	
	public void switchPages(String prevPage, String newPage){
		if(prevPage.equals(StaticFinalVars.GAME_CLOCK) && newPage.equals(StaticFinalVars.GRAPHICS)){
			fragmentSlideReplace(_gameClockMgmt, _graphicsMgmt, true);
			_whichPage = StaticFinalVars.GRAPHICS;
		}
		else{
			fragmentSlideReplace(_graphicsMgmt, _gameClockMgmt, false);
			_whichPage = StaticFinalVars.GAME_CLOCK;
		}
	}
	
	private void fragmentSlideReplace(Fragment previous, Fragment newFragment, boolean rightToLeft){
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		if(rightToLeft){
			transaction.setCustomAnimations(R.anim.slide_in_right,R.anim.slide_out_left);
		}
		else{			
			transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
		}
		transaction.replace(previous.getId(), newFragment);
		transaction.commit();
	}
}
