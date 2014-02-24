package com.seniordesign.ultimatescorecard.data.hockey;

import com.seniordesign.ultimatescorecard.R;
import com.seniordesign.ultimatescorecard.stats.hockey.HockeyStatsActivity;
import com.seniordesign.ultimatescorecard.substitution.SubstitutionActivity;
import com.seniordesign.ultimatescorecard.view.GameClock;
import com.seniordesign.ultimatescorecard.view.ShotIconAdder;
import com.seniordesign.ultimatescorecard.view.StaticFinalVars;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HockeyActivity extends Activity{
	public static final int SUBSTITUTION_CODE = 1;
	
	RelativeLayout _homeLayout, _awayLayout;
	TextView _homeTextView, _awayTextView, _gameClockView, _quarterNumberView;											//all the different items on the screen
	TextView _homeScoreTextView, _awayScoreTextView;
	ImageView _iceHockeyRink;
	Bitmap _bitmap;
	Button _option1Button, _option2Button, _option3Button, _option4Button, _option5Button;

	private HockeyGameTime _gti;
	private HockeyGameLog _gameLog = new HockeyGameLog();;
	private GameClock _gameClock;
	private ShotIconAdder _iconAdder;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		_gti = (HockeyGameTime) getIntent().getSerializableExtra(StaticFinalVars.GAME_INFO);
		setContentView(getLayoutInflater().inflate(R.layout.activity_hockey, null));
		
		_awayTextView = (TextView)findViewById(R.id.awayTextView);									//change text to denote home and away team
		_awayTextView.setText(_gti.getAwayAbbr());	
		_homeTextView = (TextView)findViewById(R.id.homeTextView);
		_homeTextView.setText(_gti.getHomeAbbr());	
		
		Typeface quartz = Typeface.createFromAsset(getAssets(), "fonts/quartz.ttf");				//changing the font style to our own
		_awayScoreTextView = (TextView)findViewById(R.id.awayScoreTextView);						//our personal font type is stored in the assets folder
		_awayScoreTextView.setTypeface(quartz);		
		_homeScoreTextView = (TextView)findViewById(R.id.homeScoreTextView);
		_homeScoreTextView.setTypeface(quartz);
	
		_quarterNumberView = (TextView) findViewById(R.id.quarterNumber);
		
		_gameClockView = (TextView) findViewById(R.id.gameClock);									//it will also serve as the button that will start the recording
		_gameClockView.setOnClickListener(startGameListener);	
		_gameClockView.setTypeface(quartz);
		
		_iceHockeyRink = (ImageView)findViewById(R.id.iceHockeyRink);
		
		_bitmap = ((BitmapDrawable) _iceHockeyRink.getDrawable()).getBitmap();
		
		_homeLayout = (RelativeLayout)findViewById(R.id.homeShotIcons);
		_awayLayout = (RelativeLayout)findViewById(R.id.awayShotIcons);
		_iconAdder = new ShotIconAdder(_homeLayout, _awayLayout, getApplicationContext());
		
		_option1Button = (Button)findViewById(R.id.optionButton1);								//more buttons and setting onClick listeners
		_option2Button = (Button)findViewById(R.id.optionButton2);
		_option3Button = (Button)findViewById(R.id.optionButton3);
		_option4Button = (Button)findViewById(R.id.optionButton4);									//again these are buttons, this set is hidden initially
		_option5Button = (Button)findViewById(R.id.optionButton5);
		
		mainButtonSet();
		disableButtons();
	}
	
	private void mainButtonSet(){
		buttonSwap(false);
		setTextAndListener(_option4Button, shotTakenListener, "Shot");
		setTextAndListener(_option5Button, penaltyListener, "Penalty");
	}
	
	private void shotButtonSet(){
		buttonSwap(true);
		setTextAndListener(_option1Button, noGoalListener(true), "Saved");
		setTextAndListener(_option2Button, goalScoredListener, "Goal");
		setTextAndListener(_option3Button, noGoalListener(false), "Missed");
	}
	
	private void penaltyShotSet(){
		buttonSwap(true);
		setTextAndListener(_option4Button, penaltyShotListener(false), "Saved");
		setTextAndListener(_option5Button, penaltyShotListener(true), "Goal");
	}
	
	private void assistButtonSet(String player){
		buttonSwap(true);
		setTextAndListener(_option1Button, noAssistListener(player), "No Assist");
		setTextAndListener(_option2Button, assistListener(player, false), "1 Assist");
		setTextAndListener(_option3Button, assistListener(player, true), "2 Assists");
	}
	
	private void buttonSwap (boolean whichSet){
		if(whichSet) {
			findViewById(R.id.twinOptionRow).setVisibility(View.INVISIBLE);
			findViewById(R.id.tripleOptionRow).setVisibility(View.VISIBLE);
		}
		else {	
			findViewById(R.id.tripleOptionRow).setVisibility(View.INVISIBLE);
			findViewById(R.id.twinOptionRow).setVisibility(View.VISIBLE);			
		}
	}
	
	private void setTextAndListener(Button button, OnClickListener listener, String text){
		button.setText(text);
		button.setOnClickListener(listener);
	}
	
	private OnClickListener shotTakenListener = new OnClickListener(){
		@Override
		public void onClick(View view) {
			shotButtonSet();
		}
	};
	
	private void disableButtons(){
		_option1Button.setEnabled(false);
		_option2Button.setEnabled(false);
		_option3Button.setEnabled(false);
		_option4Button.setEnabled(false);
		_option5Button.setEnabled(false);
	}
	
	private void enableButtons(){
		_option1Button.setEnabled(true);
		_option2Button.setEnabled(true);
		_option3Button.setEnabled(true);
		_option4Button.setEnabled(true);
		_option5Button.setEnabled(true);
	}
	
//----------------------------------------------------------------------------------------------------------------------
	
	private OnClickListener noGoalListener(final boolean saved){
		OnClickListener noGoalListener = new OnClickListener(){
			@Override
			public void onClick(View view) {
				Builder builder = new Builder(HockeyActivity.this);
				builder.setTitle("Shot Taken by:");
				final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String> (HockeyActivity.this,
						android.R.layout.select_dialog_singlechoice);
				
				if(_gti.getPossession()){
					for(HockeyPlayer hp : _gti.getTheHomeTeam().getRoster()){
						arrayAdapter.add(hp.getName());
					}
				}
				else{
					for(HockeyPlayer hp : _gti.getTheAwayTeam().getRoster()){
						arrayAdapter.add(hp.getName());
					}
				}			
				builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});			
				builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String player = arrayAdapter.getItem(which);
						_gti.getTeam().getPlayer(player).shotMissed();
						if(saved){
							_gti.getTeam().getGoalie().saved();		
							_gameLog.shootsAndMisses(player, _gti.getOppoTeam().getGoalie().getName());
						}
						else{
							_gameLog.shootsAndMisses(player, "");
						}
						_iceHockeyRink.setOnTouchListener(courtInteraction(false));
						disableButtons();
					}
				});
				builder.show();
			}
		};
		return noGoalListener;
	}
	
	private OnClickListener penaltyListener = new OnClickListener(){
		@Override
		public void onClick(View view) {
			Builder builder = new Builder(HockeyActivity.this);
			builder.setTitle("Penalty Type:");
			final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String> (HockeyActivity.this, android.R.layout.select_dialog_singlechoice);
			arrayAdapter.add("Minor");
			arrayAdapter.add("Major");
			arrayAdapter.add("Misconduct");
			arrayAdapter.add("Game Misconduct");
			arrayAdapter.add("Match");
			arrayAdapter.add("Penalty Shot");
							
			builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});			
			builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					awardingPenalty(arrayAdapter.getItem(which));				
				}
			});
			builder.show();
		}
	};
	
	private OnClickListener penaltyShotListener(final boolean goal){
		OnClickListener penaltyShotListener = new OnClickListener(){
			@Override
			public void onClick(View view) {
				Builder builder = new Builder(HockeyActivity.this);
				builder.setTitle("Penalty by:");
				final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String> (HockeyActivity.this,
						android.R.layout.select_dialog_singlechoice);
				
				if(_gti.getPossession()){
					for(HockeyPlayer hp : _gti.getTheHomeTeam().getRoster()){
						arrayAdapter.add(hp.getName());
					}
				}
				else{
					for(HockeyPlayer hp : _gti.getTheAwayTeam().getRoster()){
						arrayAdapter.add(hp.getName());
					}
				}			
				builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});			
				builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String player = arrayAdapter.getItem(which);
						_gti.getTeam().getPlayer(player).scoreGoal();
						_gameLog.penaltyShot(goal, player, _gti.getOppoTeam().getGoalie().getName());						
						_iceHockeyRink.setOnTouchListener(courtInteraction(true));
						disableButtons();						
					}
				});
				builder.show();
			}
		};
		return penaltyShotListener;
	}
	
	private void awardingPenalty(final String type){
		Builder builder = new Builder(HockeyActivity.this);
		builder.setTitle("Goal Scored by:");
		final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String> (HockeyActivity.this,
				android.R.layout.select_dialog_singlechoice);
		
		if(_gti.getPossession()){
			for(HockeyPlayer hp : _gti.getTheHomeTeam().getRoster()){
				arrayAdapter.add(hp.getName());
			}
		}
		else{
			for(HockeyPlayer hp : _gti.getTheAwayTeam().getRoster()){
				arrayAdapter.add(hp.getName());
			}
		}			
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});			
		builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String player = arrayAdapter.getItem(which);
				if(type.equals("Minor")){
					_gti.getTeam().getPlayer(player).minorPenalty();
					_gti.getTeam().getPlayer(player).addPenaltyMins(2);
					_gameLog.penalty(player, type);
				}
				else if(type.equals("Major")){
					_gti.getTeam().getPlayer(player).majorPenalty();
					_gti.getTeam().getPlayer(player).addPenaltyMins(5);
					_gameLog.penalty(player, type);
				}
				else if(type.equals("Misconduct")){
					_gti.getTeam().getPlayer(player).misconductPenalty();
					_gti.getTeam().getPlayer(player).addPenaltyMins(10);
					_gameLog.penalty(player, type);
				}
				else if(type.equals("Penalty")){
					_gti.getTeam().getPlayer(player).minorPenalty();
					penaltyShotSet();
				}
				else{
					//ejections?
				}
				dialog.dismiss();
			}
		});
		builder.show();		
	}
	
	private OnClickListener goalScoredListener = new OnClickListener(){
		@Override
		public void onClick(View view) {
			Builder builder = new Builder(HockeyActivity.this);
			builder.setTitle("Goal Scored by:");
			final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String> (HockeyActivity.this,
					android.R.layout.select_dialog_singlechoice);
			
			if(_gti.getPossession()){
				for(HockeyPlayer hp : _gti.getTheHomeTeam().getRoster()){
					arrayAdapter.add(hp.getName());
				}
			}
			else{
				for(HockeyPlayer hp : _gti.getTheAwayTeam().getRoster()){
					arrayAdapter.add(hp.getName());
				}
			}			
			builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});			
			builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					String player = arrayAdapter.getItem(which);
					assistButtonSet(player);
				}
			});
			builder.show();						
		}
	};
	
	private OnClickListener noAssistListener(final String goalScorer) {
		OnClickListener noAssistListener = new OnClickListener(){
			@Override
			public void onClick(View view) {
				_gti.getTeam().getPlayer(goalScorer).scoreGoal();
				_gameLog.shootsAndScores(goalScorer, "", "");
				_iceHockeyRink.setOnTouchListener(courtInteraction(true));
				disableButtons();
			}
		};
		return noAssistListener;
	}
	
	private OnClickListener assistListener(final String goalScorer, final boolean second) {
		OnClickListener oneAssistListener = new OnClickListener(){
			@Override
			public void onClick(View view) {
				Builder builder = new Builder(HockeyActivity.this);
				builder.setTitle("Assist by:");
				final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String> (HockeyActivity.this,
						android.R.layout.select_dialog_singlechoice);
				
				if(_gti.getPossession()){
					for(HockeyPlayer hp : _gti.getTheHomeTeam().getRoster()){
						if(!hp.getName().equals(goalScorer)){
							arrayAdapter.add(hp.getName());
						}
					}
				}
				else{
					for(HockeyPlayer hp : _gti.getTheAwayTeam().getRoster()){
						if(!hp.getName().equals(goalScorer)){
							arrayAdapter.add(hp.getName());
						}
					}
				}			
				builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});			
				builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String player = arrayAdapter.getItem(which);
						if(second){
							secondAssist(goalScorer, player);
						}
						else {
							_gti.getTeam().getPlayer(goalScorer).scoreGoal();
							_gti.getTeam().getPlayer(player).assisted();
							_gameLog.shootsAndScores(goalScorer, player, "");
							_iceHockeyRink.setOnTouchListener(courtInteraction(true));
							disableButtons();
						}
					}
				});
				builder.show();						
			}
		};
		return oneAssistListener;
	}
	
	private void secondAssist(final String goalScorer, final String assist1){
		Builder builder = new Builder(HockeyActivity.this);
		builder.setTitle("Assist by:");
		final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String> (HockeyActivity.this,
				android.R.layout.select_dialog_singlechoice);
		
		if(_gti.getPossession()){
			for(HockeyPlayer hp : _gti.getTheHomeTeam().getRoster()){
				if(!hp.getName().equals(goalScorer) && !hp.getName().equals(assist1)){
					arrayAdapter.add(hp.getName());
				}
			}
		}
		else{
			for(HockeyPlayer hp : _gti.getTheAwayTeam().getRoster()){
				if(!hp.getName().equals(goalScorer) && !hp.getName().equals(assist1)){
					arrayAdapter.add(hp.getName());
				}
			}
		}			
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});			
		builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String player = arrayAdapter.getItem(which);
				_gti.getTeam().getPlayer(goalScorer).scoreGoal();
				_gti.getTeam().getPlayer(assist1).assisted();
				_gti.getTeam().getPlayer(player).assisted();
				_gameLog.shootsAndScores(goalScorer, assist1, player);
				_iceHockeyRink.setOnTouchListener(courtInteraction(true));
				disableButtons();
			}
		});
		builder.show();	
	}
	
//----------------------------------------------------------------------------------------------------
	
	private OnClickListener changePossessionListener(final boolean home){
		OnClickListener changePossessionListener = new OnClickListener(){
			@Override
			public void onClick(View view) {
				_gti.setPossession(home);
				changePossessionMarker();
			}				
		};
		return changePossessionListener;
	}
	
	private void changePossessionMarker(){
		if(_gti.getPossession()){
			findViewById(R.id.possessionHome).setVisibility(View.VISIBLE);
			findViewById(R.id.possessionAway).setVisibility(View.INVISIBLE);
			_awayLayout.setVisibility(View.INVISIBLE);
			_homeLayout.setVisibility(View.VISIBLE);
		}
		else{
			findViewById(R.id.possessionHome).setVisibility(View.INVISIBLE);
			findViewById(R.id.possessionAway).setVisibility(View.VISIBLE);
			_homeLayout.setVisibility(View.INVISIBLE);
			_awayLayout.setVisibility(View.VISIBLE);
		}
	}
	
	private OnTouchListener courtInteraction(final boolean goal){
		OnTouchListener courtInteraction = new OnTouchListener(){
			@SuppressLint("NewApi")
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_DOWN){
					_iconAdder.setShotLocation((int)event.getX(), (int)event.getY());
					_iconAdder.setShotHitMiss(_gti.getPossession(), goal);
					mainButtonSet();
					enableButtons();
					_iceHockeyRink.setOnTouchListener(null);
				}
				return false;
			}
		};
		return courtInteraction;
	}
	
//------------------------------------------------------------------------------------------------------
	
	private OnClickListener startGameListener = new OnClickListener(){
		@Override
		public void onClick(View view) {
			_gameClock = new GameClock(whatTimeScale(), _gameClockView);
			_gameClockView.setOnClickListener(timerClickListener);
			tipOff();
		}
	};
	
	//starting and stopping the game clock by clicking it
	private OnClickListener timerClickListener = new OnClickListener(){
		@Override
		public void onClick(View view) {
			if(!_gameClock.isStarted()){
				startClock();
			}
			else{
				stopClock();
			}
		}
	};
	
	private void startClock(){
		_gameClock.start();
		enableButtons();
		_awayScoreTextView.setOnClickListener(changePossessionListener(false));
		_homeScoreTextView.setOnClickListener(changePossessionListener(true));
	}
	
	private void stopClock(){
		_gameClock.stop();
		disableButtons();
		_awayScoreTextView.setOnClickListener(null);
		_homeScoreTextView.setOnClickListener(null);
	}
	
	private boolean zeroTime(){
		if(_gameClockView.getText().toString().equals("00:00"))
			return true;
		else
			return false;
	}
				
	//prompt dialog to tell which team won tip-off and will start the game with the ball
	private void tipOff(){
		Builder tipOffAlert = new Builder(this);
		tipOffAlert.setTitle("Game Time");
		tipOffAlert.setMessage("Which team won face-off?");
		tipOffAlert.setPositiveButton("Away", new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				_gti.setPossession(false);
				changePossessionMarker();
				startClock();
			}
		});
		tipOffAlert.setNegativeButton("Home", new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				_gti.setPossession(true);
				changePossessionMarker();
				startClock();
			}
		});
		tipOffAlert.show();	
	}
	
//------------------------------------------------------------------------------------------------------
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.hockey_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		if(_gameClock != null){	
			stopClock();
		}
		return super.onMenuOpened(featureId, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = null;
		switch(item.getItemId()){
		
		case R.id.settings:
			break;
		
		case R.id.boxscore:
			intent = new Intent(getApplicationContext(), HockeyStatsActivity.class);			
			intent.putExtra(StaticFinalVars.GAME_INFO, _gti);			
			intent.putExtra(StaticFinalVars.GAME_LOG, _gameLog);
			intent.putExtra(StaticFinalVars.DISPLAY_TYPE, 0);
			startActivity(intent);		
			break;
			
		case R.id.gameLog:
			intent = new Intent(getApplicationContext(), HockeyStatsActivity.class);	
			intent.putExtra(StaticFinalVars.GAME_INFO, _gti);	
			intent.putExtra(StaticFinalVars.GAME_LOG, _gameLog);
			intent.putExtra(StaticFinalVars.DISPLAY_TYPE, 1);
			startActivity(intent);
			break;
		
		case R.id.editGame:
			break;
		
		case R.id.nextPeriod:
			String quarter = ((TextView)findViewById(R.id.periodNumber)).getText().toString();
			if(zeroTime()){
				if(quarter.equals("1ST")){
					((TextView)findViewById(R.id.periodNumber)).setText("2ND");
				}
				else if(quarter.equals("2ND")){
					((TextView)findViewById(R.id.periodNumber)).setText("3RD");
				}
				else if(quarter.equals("3RD")){
					((TextView)findViewById(R.id.periodNumber)).setText("OT");
				}
				else{
					((TextView)findViewById(R.id.periodNumber)).setText("k-OT");
				}
				_gameClock.restartTimer(whatTimeScale());
				_gameClockView.setOnClickListener(startGameListener);
			}
			break;
		
		case R.id.substitution:
			intent = new Intent(getApplicationContext(), SubstitutionActivity.class);			
			intent.putExtra(StaticFinalVars.SUB_INFO, _gti);
			startActivityForResult(intent, SUBSTITUTION_CODE);	
			break;
		}
		return true;
	}
	
	private int whatTimeScale(){
		return 1200000;
	}
}
