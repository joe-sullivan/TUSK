package com.seniordesign.ultimatescorecard.data.soccer;

import java.util.ArrayList;

import com.seniordesign.ultimatescorecard.R;
import com.seniordesign.ultimatescorecard.data.GameInfo;
import com.seniordesign.ultimatescorecard.sqlite.helper.PlayByPlay;
import com.seniordesign.ultimatescorecard.sqlite.helper.ShotChartCoords;
import com.seniordesign.ultimatescorecard.sqlite.soccer.SoccerDatabaseHelper;
import com.seniordesign.ultimatescorecard.stats.soccer.SoccerStatsActivity;
import com.seniordesign.ultimatescorecard.substitution.SoccerSubstitutionActivity;
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
import android.util.Log;
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

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//databases
		_soccer_db = new SoccerDatabaseHelper(getApplicationContext());		
				
		_gti = (SoccerGameTime) getIntent().getSerializableExtra(StaticFinalVars.GAME_TIME);
		setContentView(getLayoutInflater().inflate(R.layout.activity_soccer, null));
		
		//databases
		_gti.setContext(this);
		g_id = _gti.createTeams();
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
		_iconAdder = new ShotIconAdder(_homeLayout, _awayLayout, getApplicationContext(), "soccer");

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
	}
	
	private void shotButtonSet(){
		buttonSwap(true);
		setTextAndListener(_option1Button, noGoalListener(true), "Saved");
		setTextAndListener(_option2Button, goalScoredListener, "Goal");
		setTextAndListener(_option3Button, noGoalListener(false), "Missed");
	}
	
	private void assistButtonSet(String player){
		buttonSwap(true);
		setTextAndListener(_option1Button, null, "Assist?");
		setTextAndListener(_option2Button, assistListener(player), "Yes");
		setTextAndListener(_option3Button, noAssistListener(player), "No");
	}
	
	private void penaltyCardsButtonSet(){
		buttonSwap(false);
		setTextAndListener(_option4Button, penaltyCardListener(false), "Yellow Card");
		setTextAndListener(_option5Button, penaltyCardListener(true), "Red Card");
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
	
	private int whatTimeScale(){
		return 1200000;
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
		
		case R.id.settings:
			break;
		
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
			intent = new Intent(getApplicationContext(), SoccerSubstitutionActivity.class);			
			intent.putExtra(StaticFinalVars.SUB_INFO, _gti.getGameInfo());
			startActivityForResult(intent, SUBSTITUTION_CODE);	
			break;
		}
		return true;
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
