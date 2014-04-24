package com.seniordesign.ultimatescorecard.data.soccer;

import java.util.ArrayList;

import com.seniordesign.ultimatescorecard.R;
import com.seniordesign.ultimatescorecard.clock.GameClock;
import com.seniordesign.ultimatescorecard.data.GameInfo;
import com.seniordesign.ultimatescorecard.data.UndoInstance;
import com.seniordesign.ultimatescorecard.data.UndoManager;
import com.seniordesign.ultimatescorecard.sqlite.helper.PlayByPlay;
import com.seniordesign.ultimatescorecard.sqlite.helper.ShotChartCoords;
import com.seniordesign.ultimatescorecard.sqlite.soccer.SoccerDatabaseHelper;
import com.seniordesign.ultimatescorecard.stats.soccer.SoccerStatsActivity;
import com.seniordesign.ultimatescorecard.substitution.SoccerSubstitutionActivity;
import com.seniordesign.ultimatescorecard.view.ShotIconAdder;
import com.seniordesign.ultimatescorecard.view.StaticFinalVars;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SoccerActivity extends Activity{
	public static final int SUBSTITUTION_CODE = 1;
	private boolean _allowMenu = true;
	
	RelativeLayout _homeLayout, _awayLayout;
	TextView _homeTextView, _awayTextView, _gameClockView, _quarterNumberView;											//all the different items on the screen
	TextView _homeScoreTextView, _awayScoreTextView;
	ImageView _soccerField;
	Bitmap _bitmap;
	Button _p1Button, _p2Button, _p3Button, _p4Button, _p5Button, _otherButton, _otherButton2;
	Button _option1Button, _option2Button, _option3Button, _option4Button, _option5Button;

	public SoccerDatabaseHelper _soccer_db;
	
	private GameClock _gameClock;															//strings containing name of home and away team
	private SoccerGameTime _gti;
	private SoccerGameLog _gameLog = new SoccerGameLog();
	private ArrayList<PlayByPlay> _playbyplay;
	private ShotIconAdder _iconAdder;
	private GameInfo _gameInfo;
	private long g_id;
	private ArrayList<ShotChartCoords> _homeShots, _awayShots;
	
	private int[] _team1SO = new int[]{0,0,0,0,0};
	private int[] _team2SO = new int[]{0,0,0,0,0};
	
	private UndoManager _undoManager;
	public UndoInstance _undoInstance;
	private int ot = 0;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//databases
		_soccer_db = new SoccerDatabaseHelper(getApplicationContext());		
				
		_gti = (SoccerGameTime) getIntent().getSerializableExtra(StaticFinalVars.GAME_TIME);
		setContentView(getLayoutInflater().inflate(R.layout.activity_soccer, null));
		
		//databases
		_gti.setContext(this);

		
		_undoInstance = new UndoInstance();
		_gameLog.setUndoInstance(_undoInstance);
		_soccer_db.setUndoInstance(_undoInstance);
		g_id = _gti.createTeams();
		_undoManager = new UndoManager(_soccer_db, _gti.getHomeTeamInstance(),_gti.getAwayTeamInstance());
		_gti.setUndoInstance(_undoInstance);
		_undoInstance.setgid(g_id);

			
		_gameLog.setdb(_soccer_db);
		_gameLog.setgid(g_id);
		_gameInfo = _gti.getGameInfo();
		_playbyplay = new ArrayList<PlayByPlay>();
				
		
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
		
		_soccerField = (ImageView)findViewById(R.id.soccerfield);
		
		_bitmap = ((BitmapDrawable) _soccerField.getDrawable()).getBitmap();
		
		_homeLayout = (RelativeLayout)findViewById(R.id.homeShotIcons);
		_awayLayout = (RelativeLayout)findViewById(R.id.awayShotIcons);
		
		_iconAdder = new ShotIconAdder(_homeLayout, _awayLayout, getApplicationContext(), "soccer", _undoInstance);
		_undoManager.setLayouts(_homeLayout, _awayLayout);
		
		
		_p1Button = (Button)findViewById(R.id.extendButton1);										//our slide out buttons
		_p2Button = (Button)findViewById(R.id.extendButton2);
		_p3Button = (Button)findViewById(R.id.extendButton3);
		_p4Button = (Button)findViewById(R.id.extendButton4);
		_p5Button = (Button)findViewById(R.id.extendButton5);
		_otherButton = new Button(this);
		_otherButton2 = new Button(this);
		
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
		allowMenuAndChangingPossession();
		setTextAndListener(_option4Button, shotTakenListener, "Shot");
		setTextAndListener(_option5Button, penaltyListener, "Penalty");
		
		if(_undoInstance.gettstats().size()>0){
			_undoManager.addInstance(_undoInstance);
		}
		_undoInstance = new UndoInstance();
		_gameLog.setUndoInstance(_undoInstance);
		_soccer_db.setUndoInstance(_undoInstance);
		_gti.setUndoInstance(_undoInstance);
		_iconAdder.setUndoInstance(_undoInstance);
		_undoInstance.setgid(g_id);

	}
	
	private void shotButtonSet(){
		buttonSwap(true);
		setTextAndListener(_option1Button, noGoalListener(true), "Saved");
		setTextAndListener(_option2Button, goalScoredListener, "Goal");
		setTextAndListener(_option3Button, noGoalListener(false), "Missed");
		_gameClockView.setOnClickListener(null);
		zeroTimeDisabler();	
	}
	
	private void assistButtonSet(String player){
		buttonSwap(true);
		setTextAndListener(_option1Button, null, "Assist?");
		setTextAndListener(_option2Button, assistListener(player), "Yes");
		setTextAndListener(_option3Button, noAssistListener(player), "No");
		zeroTimeDisabler();	
	}
	
	private void penaltyCardsButtonSet(){
		buttonSwap(false);
		setTextAndListener(_option4Button, penaltyCardListener(false), "Yellow Card");
		setTextAndListener(_option5Button, penaltyCardListener(true), "Red Card");
		_gameClockView.setOnClickListener(null);
		zeroTimeDisabler();	
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
	
	private void zeroTimeDisabler(){
		if(zeroTime()){
			mainButtonSet();
			disableButtons();
		}
	}

//-----------------------------------------------------------------------------------------------	
	private OnClickListener shotTakenListener = new OnClickListener(){
		@Override
		public void onClick(View view) {
			disallowMenuAndChangingPossession();
			shotButtonSet();
		}
	};
	
	private OnClickListener penaltyListener = new OnClickListener(){
		@Override
		public void onClick(View view) {
			disallowMenuAndChangingPossession();
			stopClockNotButtons();
			penaltyCardsButtonSet();
		}
	};
	
	private OnClickListener noGoalListener(final boolean saved){
		OnClickListener noGoalListener = new OnClickListener(){
			@Override
			public void onClick(View view) {
				Builder builder = new Builder(SoccerActivity.this);
				builder.setTitle("Shot Taken by:");
				final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String> (SoccerActivity.this, android.R.layout.select_dialog_singlechoice);
				if(_gti.getPossession()){
					if(_gti.getTheHomeTeam().getRoster().size() >= 11){
						for(int i=0; i<11; i++){
							arrayAdapter.add(_gti.getTheHomeTeam().getRoster().get(i).getpname());
						}
					}
					else{
						Log.e("ERROR", "NOT ENOUGH PLAYERS FOR A HOME TEAM");
					}
				}
				else{
					if(_gti.getTheAwayTeam().getRoster().size() >= 11){
						for(int i=0; i<11; i++){
							arrayAdapter.add(_gti.getTheAwayTeam().getRoster().get(i).getpname());
						}
					}
					else{
						Log.e("ERROR", "NOT ENOUGH PLAYERS FOR A AWAY TEAM");	
					}
				}			
				builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						allowMenuAndChangingPossession();
					}
				});			
				builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String player = arrayAdapter.getItem(which);
						if(saved){
							_gti.getOppoTeam().getGoalie().saved();	
							_gti.getTeam().getPlayer(player).shotOnGoalMissed();
							_gameLog.shootsAndMisses(player, _gti.getOppoTeam().getGoalie().getpname(), _gameClockView.getText().toString());
						}
						else{
							_gti.getTeam().getPlayer(player).shotMissed();
							_gameLog.shootsAndMisses(player, "", _gameClockView.getText().toString());
						}
						_iconAdder.setPlayer(player);
						_soccerField.setOnTouchListener(courtInteraction(false));
						disableButtons();
					}
				});
				builder.show();
			}
		};
		return noGoalListener;
	}
	
	private OnClickListener goalScoredListener = new OnClickListener(){
		@Override
		public void onClick(View view) {
			Builder builder = new Builder(SoccerActivity.this);
			stopClockNotButtons();
			builder.setTitle("Goal Scored by:");
			final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String> (SoccerActivity.this,
					android.R.layout.select_dialog_singlechoice);
			
			if(_gti.getPossession()){
				if(_gti.getTheHomeTeam().getRoster().size() >= 11){
					for(SoccerPlayer hp : _gti.getTheHomeTeam().getRoster()){
						arrayAdapter.add(hp.getpname());
					}
				}
				else{
					Log.e("ERROR", "NOT ENOUGH PLAYERS FOR A HOME TEAM");
				}
			}
			else{
				if(_gti.getTheAwayTeam().getRoster().size() >= 11){
					for(SoccerPlayer hp : _gti.getTheAwayTeam().getRoster()){
						arrayAdapter.add(hp.getpname());
					}
				}
				else{
					Log.e("ERROR", "NOT ENOUGH PLAYERS FOR A AWAY TEAM");	
				}
			}		
			
			builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					allowMenuAndChangingPossession();
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
				_gti.getOppoTeam().getGoalie().goalAllowed();
				_gti.getTeam().increaseScore(1);
				updateScore();
				_gameLog.shootsAndScores(goalScorer, "", _gameClockView.getText().toString());
				_iconAdder.setPlayer(goalScorer);
				_soccerField.setOnTouchListener(courtInteraction(true));
				disableButtons();
			}
		};
		return noAssistListener;
	}
	
	private OnClickListener assistListener(final String goalScorer) {
		OnClickListener assistListener = new OnClickListener(){
			@Override
			public void onClick(View view) {
				Builder builder = new Builder(SoccerActivity.this);
				builder.setTitle("Assist by:");
				final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String> (SoccerActivity.this,
						android.R.layout.select_dialog_singlechoice);
				
				if(_gti.getPossession()){
					if(_gti.getTheHomeTeam().getRoster().size() >= 11){
						for(int i=0; i<11; i++){
							if(!_gti.getTheHomeTeam().getRoster().get(i).getpname().equals(goalScorer)){
								arrayAdapter.add(_gti.getTheHomeTeam().getRoster().get(i).getpname());
							}
						}
					}
					else{
						Log.e("ERROR", "NOT ENOUGH PLAYERS FOR A HOME TEAM");
					}
				}
				else{
					if(_gti.getTheAwayTeam().getRoster().size() >= 11){
						for(int i=0; i<11; i++){
							if(!_gti.getTheAwayTeam().getRoster().get(i).getpname().equals(goalScorer)){
								arrayAdapter.add(_gti.getTheAwayTeam().getRoster().get(i).getpname());
							}
						}
					}
					else{
						Log.e("ERROR", "NOT ENOUGH PLAYERS FOR A AWAY TEAM");	
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
						_gti.getOppoTeam().getGoalie().goalAllowed();
						_gti.getTeam().getPlayer(player).assisted();
						_gti.getTeam().increaseScore(1);
						updateScore();
						_gameLog.shootsAndScores(goalScorer, player, _gameClockView.getText().toString());
						_iconAdder.setPlayer(goalScorer);
						_soccerField.setOnTouchListener(courtInteraction(true));
						disableButtons();
					}
				});
				builder.show();						
			}
		};
		return assistListener;
	}
	
	private OnClickListener penaltyCardListener(final boolean red) {
		OnClickListener penaltyCardListener = new OnClickListener(){
			@Override
			public void onClick(View view) {
				Builder builder = new Builder(SoccerActivity.this);
				if(red){
					builder.setTitle("Red Card Earned By:");
				}
				else{
					builder.setTitle("Yellow Card Earned By:");
				}
				final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String> (SoccerActivity.this,
						android.R.layout.select_dialog_singlechoice);
				
				if(_gti.getPossession()){
					if(_gti.getTheHomeTeam().getRoster().size() >= 11){
						for(int i=0; i<11; i++){
							arrayAdapter.add(_gti.getTheHomeTeam().getRoster().get(i).getpname());
						}
					}
					else{
						Log.e("ERROR", "NOT ENOUGH PLAYERS FOR A HOME TEAM");
					}
				}
				else{
					if(_gti.getTheAwayTeam().getRoster().size() >= 11){
						for(int i=0; i<11; i++){
							arrayAdapter.add(_gti.getTheAwayTeam().getRoster().get(i).getpname());
						}
					}
					else{
						Log.e("ERROR", "NOT ENOUGH PLAYERS FOR A AWAY TEAM");	
					}
				}		
				builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						allowMenuAndChangingPossession();
					}
				});			
				builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String player = arrayAdapter.getItem(which);
						_gameLog.penaltyCard(player, red, _gameClockView.getText().toString());
						if(red){
							_gti.getTeam().getPlayer(player).penaltyRed();
						}
						else{
							_gti.getTeam().getPlayer(player).penaltyYellow();
						}
						mainButtonSet();
						_gameClockView.setOnClickListener(timerClickListener);
					}
				});
				allowMenuAndChangingPossession();
				disableButtons();
				builder.show();						
			}
		};
		return penaltyCardListener;
	}

//---------------------------------------------------------------------------------------------------------------------------
	
	private void allowMenuAndChangingPossession(){
		_awayScoreTextView.setOnClickListener(changePossessionListener(false));
		_homeScoreTextView.setOnClickListener(changePossessionListener(true));
		_allowMenu = true;
	}
	
	private void disallowMenuAndChangingPossession(){
		_awayScoreTextView.setOnClickListener(null);
		_homeScoreTextView.setOnClickListener(null);
		_allowMenu = false;
	}
	
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
					_iconAdder.createShot(null, g_id, _gti.gethometid(), _gti.getawaytid());
					mainButtonSet();
					if(!goal){
						enableButtons();
					}
					_gameClockView.setOnClickListener(timerClickListener);
					_soccerField.setOnTouchListener(null);
				}
				return false;
			}
		};
		return courtInteraction;
	}

//----------------------------------------------------------------------------------------------------------------------
	private OnClickListener startGameListener = new OnClickListener(){
		@Override
		public void onClick(View view) {
			SharedPreferences prefs = getApplicationContext().getSharedPreferences("GameClock", Context.MODE_PRIVATE);
			int minuteTime = Integer.parseInt(prefs.getString("perLenSoccer", StaticFinalVars.SOCCER_DEFAULT).split(" ")[0]);
			_gameClock = new GameClock(minuteTime*60*1000, _gameClockView);
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
				stopClockAllowShotChartChange();
			}
		}
	};
	
	private void startClock(){
		_gameClock.start();
		enableButtons();
		mainButtonSet();
	}
	
	private void stopClockNotButtons(){
		_gameClock.stop();
		_awayScoreTextView.setOnClickListener(null);
		_homeScoreTextView.setOnClickListener(null);
	}
	
	private void stopClockAllowShotChartChange(){
		_gameClock.stop();
		disableButtons();
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
		tipOffAlert.setMessage("Which team starts with possession?");
		tipOffAlert.setPositiveButton(_gti.getAwayAbbr(), new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				_gti.setPossession(false);
				changePossessionMarker();
				startClock();
			}
		});
		tipOffAlert.setNegativeButton(_gti.getHomeAbbr(), new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				_gti.setPossession(true);
				changePossessionMarker();
				startClock();
			}
		});
		tipOffAlert.show();	
	}
	
	private void updateScore(){
		_homeScoreTextView.setText(_gti.getHomeScoreText());
		_awayScoreTextView.setText(_gti.getAwayScoreText());
	}

//----------------------------------------------------------------------------------------------------------------------------
	
	private void editTime(){
		Builder builder = new Builder(SoccerActivity.this);
		final AlertDialog alert = builder.create();
		alert.setTitle("Edit Time:");
		
		LayoutInflater inflater = this.getLayoutInflater();
		final View layout = inflater.inflate(R.layout.dialog_time_edit, null);
		alert.setView(layout);	
		
		((TextView)layout.findViewById(R.id.minutes_box)).setText(parseMinute());
		((TextView)layout.findViewById(R.id.seconds_box)).setText(":"+parseSecond());
		
		final SharedPreferences prefs = getApplicationContext().getSharedPreferences("GameClock", Context.MODE_PRIVATE);		
		((ImageButton)layout.findViewById(R.id.fast_forward_button)).setOnClickListener(new OnClickListener(){			
			@Override
			public void onClick(View v) {
				int minutes = Integer.parseInt(((TextView)layout.findViewById(R.id.minutes_box)).getText().toString());
				
				if(ot == 0 && minutes < Integer.parseInt(prefs.getString("perLenSoccer", StaticFinalVars.SOCCER_OT_DEFAULT).split(" ")[0])){
					minutes++;
				}
				else if (ot != 0 && minutes < Integer.parseInt(prefs.getString("otLenSoccer", StaticFinalVars.SOCCER_DEFAULT).split(" ")[0])){
					minutes++;
				}				
				if(minutes < 10){
					((TextView)layout.findViewById(R.id.minutes_box)).setText("0"+minutes);
				}
				else{
					((TextView)layout.findViewById(R.id.minutes_box)).setText(""+minutes);
				}
			}			
		});		
		
		((ImageButton)layout.findViewById(R.id.fast_rewind_button)).setOnClickListener(new OnClickListener(){			
			@Override
			public void onClick(View v) {
				int minutes = Integer.parseInt(((TextView)layout.findViewById(R.id.minutes_box)).getText().toString());				
				if(minutes > 0){
					minutes--;
				}				
				if(minutes < 10){
					((TextView)layout.findViewById(R.id.minutes_box)).setText("0"+minutes);
				}
				else{
					((TextView)layout.findViewById(R.id.minutes_box)).setText(""+minutes);
				}
			}			
		});	
		
		((ImageButton)layout.findViewById(R.id.rewind_button)).setOnClickListener(new OnClickListener(){			
			@Override
			public void onClick(View v) {
				int seconds = Integer.parseInt(((TextView)layout.findViewById(R.id.seconds_box)).getText().toString().substring(1));				
				if(seconds > 0){ 
					seconds--; 
				}	
				else{
					seconds = 59;
				}
				if(seconds < 10){
					((TextView)layout.findViewById(R.id.seconds_box)).setText(":0"+seconds);
				}
				else{
					((TextView)layout.findViewById(R.id.seconds_box)).setText(":"+seconds);
				}
			}			
		});	
		
		((ImageButton)layout.findViewById(R.id.forward_button)).setOnClickListener(new OnClickListener(){			
			@Override
			public void onClick(View v) {
				int seconds = Integer.parseInt(((TextView)layout.findViewById(R.id.seconds_box)).getText().toString().substring(1));				
				if(seconds < 59){
					seconds++;
				}
				else{
					seconds = 0;
				}				
				if(seconds < 10){
					((TextView)layout.findViewById(R.id.seconds_box)).setText(":0"+seconds);
				}
				else{
					((TextView)layout.findViewById(R.id.seconds_box)).setText(":"+seconds);
				}
			}			
		});	
		((Button)layout.findViewById(R.id.done_button)).setOnClickListener(new OnClickListener(){			
			@Override
			public void onClick(View v) {
				int minutes = Integer.parseInt(((TextView)layout.findViewById(R.id.minutes_box)).getText().toString());	
				int seconds = Integer.parseInt(((TextView)layout.findViewById(R.id.seconds_box)).getText().toString().substring(1));
				setGameClockText(minutes, seconds);
				alert.dismiss();
			}			
		});			
		alert.show();
	}
	
	private void setGameClockText(int minutes, int seconds){
		_gameClock.setCurrentTime(minutes, seconds);
		if(minutes < 10 && seconds < 10){
			_gameClockView.setText("0"+minutes+":0"+seconds);
		}
		else if(minutes < 10){
			_gameClockView.setText("0"+minutes+":"+seconds);
		}
		else if(seconds < 10){
			_gameClockView.setText(minutes+":0"+seconds);
		}
		else{
			_gameClockView.setText(minutes+":"+seconds);
		}
	}
	
	private String parseMinute(){
		return _gameClockView.getText().toString().substring(0, 2);
	}
	
	private String parseSecond(){
		return _gameClockView.getText().toString().substring(3, 5);
	}
	
//------------------------------------------------------------------------------------------------------

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.hockey_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		if(_allowMenu){
			if(_gameClock != null){	
				stopClockAllowShotChartChange();
			}
			return super.onMenuOpened(featureId, menu);
		}
		else{
			return false;
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = null;
		switch(item.getItemId()){
			
		case R.id.boxscore:
			intent = new Intent(getApplicationContext(), SoccerStatsActivity.class);			
			_gameInfo = _gti.getGameInfo();
			_gameInfo.setAwayScore(_gti.getAwayScoreText());
			_gameInfo.setHomeScore(_gti.getHomeScoreText());
			_playbyplay = (ArrayList<PlayByPlay>) _soccer_db.getPlayByPlayGame(g_id);
			_homeShots = (ArrayList<ShotChartCoords>) _soccer_db.getAllTeamShotsGame(_gti.gethometid(), g_id);
			_awayShots = (ArrayList<ShotChartCoords>) _soccer_db.getAllTeamShotsGame(_gti.getawaytid(), g_id);
			intent.putExtra(StaticFinalVars.GAME_INFO, _gameInfo);	
			intent.putExtra(StaticFinalVars.GAME_LOG, _playbyplay);
			intent.putExtra(StaticFinalVars.SHOT_CHART_HOME, _homeShots);
			intent.putExtra(StaticFinalVars.SHOT_CHART_AWAY, _awayShots);
			intent.putExtra(StaticFinalVars.DISPLAY_TYPE, 0);
			startActivity(intent);		
			break;
			
		case R.id.gameLog:
			intent = new Intent(getApplicationContext(), SoccerStatsActivity.class);	
			_gameInfo = _gti.getGameInfo();
			_gameInfo.setAwayScore(_gti.getAwayScoreText());
			_gameInfo.setHomeScore(_gti.getHomeScoreText());
			_playbyplay = (ArrayList<PlayByPlay>) _soccer_db.getPlayByPlayGame(g_id);
			_homeShots = (ArrayList<ShotChartCoords>) _soccer_db.getAllTeamShotsGame(_gti.gethometid(), g_id);
			_awayShots = (ArrayList<ShotChartCoords>) _soccer_db.getAllTeamShotsGame(_gti.getawaytid(), g_id);
			intent.putExtra(StaticFinalVars.GAME_INFO, _gameInfo);	
			intent.putExtra(StaticFinalVars.GAME_LOG, _playbyplay);
			intent.putExtra(StaticFinalVars.SHOT_CHART_HOME, _homeShots);
			intent.putExtra(StaticFinalVars.SHOT_CHART_AWAY, _awayShots);
			intent.putExtra(StaticFinalVars.DISPLAY_TYPE, 1);
			startActivity(intent);
			break;
		
		case R.id.editTime:
			if(_gameClock != null){
				editTime();
			}
			break;
			
		case R.id.nextPeriod:
			SharedPreferences prefs = getApplicationContext().getSharedPreferences("GameClock", Context.MODE_PRIVATE);
			int numPer = Integer.parseInt(prefs.getString("numPerSoccer", "2 Halves").split(" ")[0]);
			String quarter = ((TextView)findViewById(R.id.periodNumber)).getText().toString();
			quarter = ((TextView)findViewById(R.id.periodNumber)).getText().toString();
			if(zeroTime()){
				if(quarter.equals("1ST") && numPer > 1){
					((TextView)findViewById(R.id.periodNumber)).setText("2ND");
				}
				else if((quarter.equals("1ST") && numPer == 1) || quarter.equals("2ND")){
					if(Integer.parseInt(_homeScoreTextView.getText().toString()) == Integer.parseInt(_awayScoreTextView.getText().toString())){
						((TextView)findViewById(R.id.periodNumber)).setText("OT");
						ot = 1;
						break;
					}
					else{
						endGame(false);
						break;
					}
				}
				else{
					if(Integer.parseInt(_homeScoreTextView.getText().toString()) == Integer.parseInt(_awayScoreTextView.getText().toString())){
						whichTeamShootFirst();
					}
					else{
						endGame(false);
						break;
					}
				}
				if(ot == 0){
					int minuteTime = Integer.parseInt(prefs.getString("perLenSoccer", StaticFinalVars.SOCCER_DEFAULT).split(" ")[0]);
					_gameClock.restartTimer(minuteTime*60*1000);
					_gameClockView.setOnClickListener(startGameListener);
				}
				else{
					int minuteTime = Integer.parseInt(prefs.getString("otLenSoccer", StaticFinalVars.SOCCER_OT_DEFAULT).split(" ")[0]);
					_gameClock.restartTimer(minuteTime*60*1000);
					_gameClockView.setOnClickListener(startGameListener);
				}
			}
			break;
		
		case R.id.substitution:
			intent = new Intent(getApplicationContext(), SoccerSubstitutionActivity.class);			
			intent.putExtra(StaticFinalVars.SUB_INFO, _gti.getGameInfo());
			startActivityForResult(intent, SUBSTITUTION_CODE);	
			break;
		
		case R.id.undo:
			_undoManager.undo();
			break;
		
		case R.id.redo:
			_undoManager.redo();
			break;
		}
		_awayScoreTextView.setText(_gti.getAwayScoreText());
		_homeScoreTextView.setText(_gti.getHomeScoreText());
		return true;
	}
	
//---------------------------------------------------------------------------------------------------------------------
	private void whichTeamShootFirst(){
		Builder wtsfDialog = new Builder(SoccerActivity.this);
		wtsfDialog.setTitle("Shootout:");
		wtsfDialog.setMessage("Which team shoots first?");
		
		wtsfDialog.setPositiveButton(_gti.getHomeAbbr(), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				shootOut(true);				
			}
		});	
		wtsfDialog.setNegativeButton(_gti.getAwayAbbr(), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				shootOut(false);				
			}
		});	
		wtsfDialog.show();
		
	}
	
	private void shootOut(boolean home){
		Builder builder = new Builder(SoccerActivity.this);
		AlertDialog alert = builder.create();
		alert.setTitle("Shootout:");
		
		LayoutInflater inflater = this.getLayoutInflater();
		final View layout = inflater.inflate(R.layout.dialog_shootout, null);
		alert.setView(layout);
		
		if(home){
			((TextView)layout.findViewById(R.id.textTeam1)).setText(_gti.getHomeTeam());
			((TextView)layout.findViewById(R.id.textTeam2)).setText(_gti.getAwayTeam());
		}
		else{
			((TextView)layout.findViewById(R.id.textTeam1)).setText(_gti.getAwayTeam());
			((TextView)layout.findViewById(R.id.textTeam2)).setText(_gti.getHomeTeam());
		}
		
		((ImageView)layout.findViewById(R.id.imageViewH1)).setOnClickListener(shootOutGoalMadeListener(true,0));		
		((Button)layout.findViewById(R.id.finishButton)).setOnClickListener(analyzeSOInputListener(home, alert));
		alert.show();
	}
	
	private OnClickListener shootOutGoalMadeListener(final boolean home, final int shotNumber){
		OnClickListener shootOutGoalMadeListener = new OnClickListener(){
			@Override
			public void onClick(View view) {
				((ImageView)view).setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.soccerball_check));
				if(home){
					_team1SO[shotNumber] = 1;
				}
				else{
					_team2SO[shotNumber] = 1;
				}				
				unlockNextShot(view);
				view.setOnClickListener(shootOutGoalMissListener(home, shotNumber));
			}		
		};
		return shootOutGoalMadeListener;
	}
	
	private OnClickListener shootOutGoalMissListener(final boolean home, final int shotNumber){
		OnClickListener shootOutGoalMadeListener = new OnClickListener(){
			@Override
			public void onClick(View view) {
				((ImageView)view).setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.soccerball_x));
				if(home){
					_team1SO[shotNumber] = -1;
				}
				else{
					_team2SO[shotNumber] = -1;
				}
				view.setOnClickListener(shootOutGoalMadeListener(home, shotNumber));
			}		
		};
		return shootOutGoalMadeListener;
	}
	
	private void unlockNextShot(View view){
		if(view.getId() == R.id.imageViewH1){
			((View)view.getParent().getParent()).findViewById(R.id.imageViewA1).setOnClickListener(shootOutGoalMadeListener(false,0));
		}
		else if(view.getId() == R.id.imageViewA1){
			((View)view.getParent().getParent()).findViewById(R.id.imageViewH2).setOnClickListener(shootOutGoalMadeListener(true,1));
		}
		else if(view.getId() == R.id.imageViewH2){
			((View)view.getParent().getParent()).findViewById(R.id.imageViewA2).setOnClickListener(shootOutGoalMadeListener(false,1));
		}
		else if(view.getId() == R.id.imageViewA2){
			((View)view.getParent().getParent()).findViewById(R.id.imageViewH3).setOnClickListener(shootOutGoalMadeListener(true,2));
		}
		else if(view.getId() == R.id.imageViewH3){
			((View)view.getParent().getParent()).findViewById(R.id.imageViewA3).setOnClickListener(shootOutGoalMadeListener(false,2));
		}
		else if(view.getId() == R.id.imageViewA3){
			((View)view.getParent().getParent()).findViewById(R.id.imageViewH4).setOnClickListener(shootOutGoalMadeListener(true,3));
		}
		else if(view.getId() == R.id.imageViewH4){
			((View)view.getParent().getParent()).findViewById(R.id.imageViewA4).setOnClickListener(shootOutGoalMadeListener(false,3));
		}
		else if(view.getId() == R.id.imageViewA4){
			((View)view.getParent().getParent()).findViewById(R.id.imageViewH5).setOnClickListener(shootOutGoalMadeListener(true,4));
		}
		else{
			((View)view.getParent().getParent()).findViewById(R.id.imageViewA5).setOnClickListener(shootOutGoalMadeListener(false,4));
		}
	}
	
	private OnClickListener analyzeSOInputListener(final boolean home, final AlertDialog alert){
		OnClickListener analyzeSOInputListener =  new OnClickListener(){
			@Override
			public void onClick(View view) {
				int team1Now = 0, team2Now = 0, maxTeam1 = 5, maxTeam2 = 5;
				for(int i=0; i<5; i++){
					if(_team1SO[i] != 0){
						if(_team1SO[i] == 1){ team1Now++; }
						else{ maxTeam1--; }					
						if(_team2SO[i] != 0){
							if(_team2SO[i] == 1){ team2Now++; }
							else{ maxTeam2--; }
						}
						else{ break; }
					}				
					else{ break; }
				}
				if(maxTeam1 < team2Now){
					if(home){ //team 1 is home, team 2 is away --> team 2 (away) wins
						_gti.getAwayTeamInstance().increaseScore(1);
					}
					else{ //team 2 is home, team 1 is away --> team 1 (home) wins
						_gti.getHomeTeamInstance().increaseScore(1);
					}
					alert.cancel();
					endGame(true);
				}
				else if(maxTeam2 < team1Now){
					if(home){ //team 1 is home, team 2 is away --> team 1 (home) wins
						_gti.getHomeTeamInstance().increaseScore(1);
					}
					else{ //team 2 is home, team 1 is away --> team 2 (away) wins
						_gti.getAwayTeamInstance().increaseScore(1);
					}
					alert.cancel();
					endGame(true);
				}
				else if(_team1SO[4] != 0 && _team1SO[4] != 0 && maxTeam1 == maxTeam2){
					alert.cancel();
					suddenDeathSO();
				}
			}
		};
		return analyzeSOInputListener;
	}
	
	private void suddenDeathSO(){
		Builder builder = new Builder(SoccerActivity.this);
		builder.setTitle("Sudden Death Shootout:");
		builder.setMessage("Who wins?");
		builder.setPositiveButton(_gti.getAwayAbbr(), new DialogInterface.OnClickListener() {			
			@Override
			public void onClick(DialogInterface dialog, int which) {				
				_gti.getAwayTeamInstance().increaseScore(1);
				endGame(true);
				dialog.dismiss();				
			}
		});
		builder.setNegativeButton(_gti.getHomeAbbr(), new DialogInterface.OnClickListener() {			
			@Override
			public void onClick(DialogInterface dialog, int which) {				
				_gti.getHomeTeamInstance().increaseScore(1);
				endGame(true);
				dialog.dismiss();				
			}
		});
		builder.show();
	}
		
	private void endGame(boolean shootOut){		
		if(shootOut){
			updateScore();
			_gameClockView.setText("FINAL(SO)");
		}
		else{
			_gameClockView.setText("FINAL");
		}
		_gameClockView.setOnClickListener(null);
		_soccerField.setOnClickListener(null);
		disableButtons();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == SUBSTITUTION_CODE){
			if(resultCode == Activity.RESULT_OK){
				_gameInfo = (GameInfo)data.getSerializableExtra(StaticFinalVars.SUB_INFO);
				_gti.setGameInfo(_gameInfo);
			}
		}
	}
}
