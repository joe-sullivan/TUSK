package com.seniordesign.ultimatescorecard.data.football;

import java.util.ArrayList;

import com.seniordesign.ultimatescorecard.R;
import com.seniordesign.ultimatescorecard.clock.GameClock;
import com.seniordesign.ultimatescorecard.data.GameInfo;
import com.seniordesign.ultimatescorecard.sqlite.football.FootballDatabaseHelper;
import com.seniordesign.ultimatescorecard.sqlite.helper.PlayByPlay;
import com.seniordesign.ultimatescorecard.stats.football.FootballStatsActivity;
import com.seniordesign.ultimatescorecard.view.StaticFinalVars;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class FootballActivity extends Activity{
	public static final int PRO_TIME = 900000;
	public static final String FIRSTDOWNLINE = "yellow";
	public static final String LINEOFSCRIMMAGE = "blue";
	public static final String RESET = "reset";
	
	RelativeLayout _root;
	TextView _homeTextView, _awayTextView, _gameClockView, _quarterNumberView;											//all the different items on the screen
	TextView _homeScoreTextView, _awayScoreTextView;
	Button _p1Button, _p2Button, _p3Button, _p4Button, _p5Button, _otherButton;
	Button _option1Button, _option2Button, _option3Button, _option4Button, _option5Button;
	ScrollView _fieldScroll;
	LinearLayout _fieldOfPlay;
	
	private FootballDatabaseHelper _football_db;
	
	private GameClock _gameClock;
	private FootballGameTime _gti;
	private FootballGameLog _gameLog = new FootballGameLog();
	private boolean _fieldActive = false;
	private ArrayList<PlayByPlay> _playbyplay;
	private GameInfo _gameInfo;
	private long g_id;
	
	private int ot = 0;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//databases
		_football_db = new FootballDatabaseHelper(getApplicationContext());
		
		_root = (RelativeLayout) getLayoutInflater().inflate(R.layout.activity_football, null);	//root is modified view with fly-out container														
		_gti = (FootballGameTime) getIntent().getSerializableExtra(StaticFinalVars.GAME_TIME); 					//get our team informations class
		
		_gameLog = new FootballGameLog();
		setContentView(_root);
		
		//databases
		_gti.setContext(this);
		g_id = _gti.createTeams();
		_gameLog.setdb(_football_db);
		_gameLog.setgid(g_id);
		_gameInfo = _gti.getGameInfo();
		_playbyplay = new ArrayList<PlayByPlay>();

		_awayTextView = (TextView)findViewById(R.id.awayTextView);
		_awayTextView.setText(_gti.getAwayAbbr());					
		_homeTextView = (TextView)findViewById(R.id.homeTextView);
		_homeTextView.setText(_gti.getHomeAbbr());
		
		Typeface quartz = Typeface.createFromAsset(getAssets(), "fonts/quartz.ttf");
		_awayScoreTextView = (TextView)findViewById(R.id.awayScoreTextView);	
		_awayScoreTextView.setTypeface(quartz);
		_homeScoreTextView = (TextView)findViewById(R.id.homeScoreTextView);
		_homeScoreTextView.setTypeface(quartz);
		
		_quarterNumberView = (TextView) findViewById(R.id.quarterNumber);
		
		_gameClockView = (TextView) findViewById(R.id.gameClock);
		_gameClockView.setTypeface(quartz);
		_gameClockView.setOnClickListener(startGameListener);
				
		_fieldScroll = (ScrollView)findViewById(R.id.footballField);
		_fieldOfPlay = (LinearLayout)findViewById(R.id.fieldOfPlayList);
		createField();	
				
		_option1Button = (Button)findViewById(R.id.optionButton1);								
		_option2Button = (Button)findViewById(R.id.optionButton2);
		_option3Button = (Button)findViewById(R.id.optionButton3);
		_option4Button = (Button)findViewById(R.id.optionButton4);
		_option5Button = (Button)findViewById(R.id.optionButton5);
		
		kickOffButtonSet();
		disableButtons();
	}
	
//TODO Button Sets -----------------------------------------------------------------------------------------------------------
	
	private void kickOffButtonSet(){
		buttonSwap(true);
		setTextAndListener(_option1Button, kickOffListener(), "Kick Off");
		setTextAndListener(_option2Button, onsideKickListener(), "Onside Kick");
		setTextAndListener(_option3Button, penaltyListener("kickoff"), "Penalty");
	}
	
	private void offenseButtonSet(){
		buttonSwap(true);
		setTextAndListener(_option1Button, passListener(), "Pass");
		setTextAndListener(_option2Button, rushListener(), "Rush");
		setTextAndListener(_option3Button, moreOptionsListener(false), "More...");
	}
	
	private void negOffenseButtonSet(){
		buttonSwap(true);
		setTextAndListener(_option1Button, turnoverListener(), "Turnover");
		setTextAndListener(_option2Button, penaltyListener("driving"), "Penalty");
		setTextAndListener(_option3Button, moreOptionsListener(true), "Back...");
	}
	
	private void turnoverButtonSet(){
		buttonSwap(true);
		setTextAndListener(_option1Button, fumLostListener(), "Fumble Lost");
		setTextAndListener(_option2Button, interceptionListener(), "Interception");
		setTextAndListener(_option3Button, moreOptionsListener(false), "Back...");
	}
	
	private void intReturnedButtonSet(){
		buttonSwap(true);
		setTextAndListener(_option1Button, null, "Returned?");
		setTextAndListener(_option2Button, intReturnListener(true), "Yes");
		setTextAndListener(_option3Button, intReturnListener(false), "No");
	}
	
	private void fumReturnedButtonSet(){
		buttonSwap(true);
		setTextAndListener(_option1Button, null, "Returned?");
		setTextAndListener(_option2Button, fumReturnListener(true), "Yes");
		setTextAndListener(_option3Button, fumReturnListener(false), "No");
	}
	
	private void fourthDownButtonSet(){
		buttonSwap(true);
		setTextAndListener(_option1Button, puntListener(), "Punting");
		setTextAndListener(_option2Button, fieldGoalListener(), "Field Goal");
		setTextAndListener(_option3Button, conversionListener(), "Conversion");
	}
	
	private void fieldGoalButtonSet(){
		buttonSwap(true);
		setTextAndListener(_option1Button, fieldGoalMadeListener(), "Kick Good");
		setTextAndListener(_option2Button, fieldGoalMissListener(), "Kick Missed");
		setTextAndListener(_option3Button, penaltyListener("fieldgoal"), "Penalty");
	}
	
	private void extraPointButtonSet(){
		buttonSwap(true);
		setTextAndListener(_option1Button, PATListener(), "PAT");
		setTextAndListener(_option2Button, twoPtsListener(), "2pt Conv.");
		setTextAndListener(_option3Button, penaltyListener("fieldgoal"), "Penalty");
	}
	
	private void XPResultButtonSet(int pts){
		buttonSwap(false);
		setTextAndListener(_option4Button, successListener(pts), "Success");
		setTextAndListener(_option5Button, failedListener(pts), "Failed");
	}
	
	private void catchKickButtonSet(boolean kickoff){
		buttonSwap(true);
		if(_gti.getLineOfScrimmageAsValue()==100){
			setTextAndListener(_option1Button, fairCatchListener(), "Touch Back");
		}
		else{
			setTextAndListener(_option1Button, fairCatchListener(), "Fair Catch");
		}
		setTextAndListener(_option2Button, returnKickListener(kickoff), "Returned");
		setTextAndListener(_option3Button, penaltyListener("return"), "Penalty");
	}
	
	private void passOptionButtonSet(){
		buttonSwap(true);
		setTextAndListener(_option1Button, completePassListener(), "Complete");
		setTextAndListener(_option2Button, incompletePassListener(), "Incomplete");
		setTextAndListener(_option3Button, qbSackedListener(), "QB Sacked");
	}
	
	private void setTextAndListener(Button button, OnClickListener listener, String text){
		button.setText(text);
		button.setOnClickListener(listener);
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
	
//TODO Kickoff Listeners ------------------------------------------------------------------------------------------------------------
	
	private OnClickListener kickOffListener(){
		OnClickListener kickOffListener = new OnClickListener(){
			@Override
			public void onClick(View view) {
				changePossession();
				_gti.setGameState("kickoff");
				_fieldActive = true;
				disableButtons();
			}
		};
		return kickOffListener;
	}
	
	private OnClickListener onsideKickListener(){
		OnClickListener onsideKickListener = new OnClickListener(){
			@Override
			public void onClick(View view) {
				_gti.setGameState("onside");
				_fieldActive = true;
				disableButtons();
			}
		};
		return onsideKickListener;
	}

//TODO Return Kickoff Listeners -----------------------------------------------------------------------------------------------------
	
	private OnClickListener fairCatchListener(){
		OnClickListener fairCatchListener = new OnClickListener(){
			@Override
			public void onClick(View view) {			//fair caught
				if(_gti.getLineOfScrimmageAsValue() != 0){
					editField(_gti.getLineOfScrimmageAsValue()-10, FIRSTDOWNLINE);						
					_gti.setFirstDown();
					offenseButtonSet();
				}
				else{									//touch back
					_gti.setLineOfScrimmage(75);
					editField(_gti.getLineOfScrimmageAsValue(), LINEOFSCRIMMAGE);
					editField(_gti.getLineOfScrimmageAsValue()-10, FIRSTDOWNLINE);						
					_gti.setFirstDown();
					offenseButtonSet();
				}
			}
		};
		return fairCatchListener;
	}
	
	private OnClickListener returnKickListener(final boolean kickoff){
		OnClickListener returnKickListener = new OnClickListener(){
			@Override
			public void onClick(View view) {
				if(kickoff){
					_gti.setGameState("kick return");
					selectPlayerDialog("return");
					disableButtons();
				}
				else{
					_gti.setGameState("punt return");
					selectPlayerDialog("return");
					disableButtons();
				}
			}
		};
		return returnKickListener;
	}
	
//TODO Offensive Button Listeners ---------------------------------------------------------------------------------------------------
	
	private OnClickListener passListener(){
		OnClickListener passListener = new OnClickListener(){
			@Override
			public void onClick(View view) {
				_gti.setGameState("passing");
				passOptionButtonSet();
			}
		};
		return passListener;
	}
	
	private OnClickListener rushListener(){
		OnClickListener rushListener = new OnClickListener(){
			@Override
			public void onClick(View view) {
				_gti.setGameState("rushing");
				selectPlayerDialog("rushing");
			}
		};
		return rushListener;
	}
	
//TODO Negative Plays Listeners ------------------------------------------------------------------------------------------------------
	
	private OnClickListener qbSackedListener(){
		OnClickListener qbSackedListener = new OnClickListener(){
			@Override
			public void onClick(View view) {
				_gti.setGameState("qb sack");
				selectOppoPlayerDialog("sacking");
			}
		};
		return qbSackedListener;
	}
	
	private OnClickListener turnoverListener(){
		OnClickListener turnoverListener = new OnClickListener(){
			@Override
			public void onClick(View view) {
				turnoverButtonSet();
			}
		};
		return turnoverListener;
	}
	
	private OnClickListener fumLostListener(){
		OnClickListener fumLostListener = new OnClickListener(){
			@Override
			public void onClick(View view) {
				_gti.setGameState("fumble lost");
				selectOppoPlayerDialog("fumRec");
			}
		};
		return fumLostListener;
	}
	
	private OnClickListener interceptionListener(){
		OnClickListener interceptionListener = new OnClickListener(){
			@Override
			public void onClick(View view) {
				_gti.setGameState("interception");
				selectOppoPlayerDialog("interceptor");
			}
		};
		return interceptionListener;
	}
	
	private OnClickListener intReturnListener(final boolean returned){
		OnClickListener intReturnListener = new OnClickListener(){
			@Override
			public void onClick(View view) {
				if(returned){
					_gti.setGameState("int return");
					_fieldActive = true;
					disableButtons();
				}
				else{
					editField(_gti.getLineOfScrimmageAsValue(), RESET);
					_gti.switchField();
					editField(_gti.getLineOfScrimmageAsValue(), LINEOFSCRIMMAGE);
					editField(_gti.getLineOfScrimmageAsValue()-10, FIRSTDOWNLINE);
					changePossession();
					offenseButtonSet();
					enableButtons();
				}
			}
		};
		return intReturnListener;
	}
	
	private OnClickListener fumReturnListener(final boolean returned){
		OnClickListener fumReturnListener = new OnClickListener(){
			@Override
			public void onClick(View view) {
				if(returned){
					_gti.setGameState("fum return");
					_fieldActive = true;
					disableButtons();
				}
				else{
					editField(_gti.getLineOfScrimmageAsValue(), RESET);
					_gti.switchField();
					editField(_gti.getLineOfScrimmageAsValue(), LINEOFSCRIMMAGE);
					editField(_gti.getLineOfScrimmageAsValue()-10, FIRSTDOWNLINE);
					changePossession();
					offenseButtonSet();
					enableButtons();
				}
			}
		};
		return fumReturnListener;
	}
	
//TODO Passing Listeners ------------------------------------------------------------------------------------------------------------
	
	private OnClickListener completePassListener(){
		OnClickListener completePassListener = new OnClickListener(){
			@Override
			public void onClick(View view) {
				selectPlayerDialog("passing");
			}
		};
		return completePassListener;
	}
	
	private OnClickListener incompletePassListener(){
		OnClickListener incompletePassListener = new OnClickListener(){
			@Override
			public void onClick(View view) {
				selectPlayerDialog("incomplete pass");
			}
		};
		return incompletePassListener;
	}
	
//TODO Fourth Down Options Listeners ------------------------------------------------------------------------------------------------
	
	private OnClickListener puntListener(){
		OnClickListener puntListener = new OnClickListener(){
			@Override
			public void onClick(View view) {
				_gti.setGameState("punting");
				selectPlayerDialog("punting");
				disableButtons();
			}
		};
		return puntListener;
	}
	
	private OnClickListener fieldGoalListener(){
		OnClickListener fieldGoalListener = new OnClickListener(){
			@Override
			public void onClick(View view) {
				selectPlayerDialog("kicking");
			}
		};
		return fieldGoalListener;
	}
	
	private OnClickListener conversionListener(){
		OnClickListener conversionListener = new OnClickListener(){
			@Override
			public void onClick(View view) {
				offenseButtonSet();
			}
		};
		return conversionListener;
	}

//TODO Field Goal Listeners ---------------------------------------------------------------------------------------------------------	

	private OnClickListener fieldGoalMadeListener(){
		OnClickListener fieldGoalMadeListener = new OnClickListener(){
			@Override
			public void onClick(View view) {
				editField(_gti.getLineOfScrimmageAsValue(), RESET);
				editField(_gti.getLineOfScrimmageAsValue()-_gti.getDistance(), RESET);
				_gti.scoreChange(_gti.getPossession(), "FG", 3, null, null);
				_gameLog.formRecord("field goal made", _gti.getLineOfScrimmageAsValue());
				recordActivity();
				kickOffButtonSet();
			}
		};
		return fieldGoalMadeListener;
	}
	
	private OnClickListener fieldGoalMissListener(){
		OnClickListener fieldGoalMissListener = new OnClickListener(){
			@Override
			public void onClick(View view) {
				editField(_gti.getLineOfScrimmageAsValue(), RESET);
				editField(_gti.getLineOfScrimmageAsValue()-_gti.getDistance(), RESET);
				_gameLog.formRecord("field goal miss", _gti.getLineOfScrimmageAsValue());
				recordActivity();
				kickOffButtonSet();
			}
		};
		return fieldGoalMissListener;
	}
	
//TODO Extra Point Listeners --------------------------------------------------------------------------------------------------------
	
	private OnClickListener PATListener(){
		OnClickListener PATListener = new OnClickListener(){
			@Override
			public void onClick(View view) {
				XPResultButtonSet(1);
			}
		};
		return PATListener;
	}
	
	private OnClickListener twoPtsListener(){
		OnClickListener twoPtsListener = new OnClickListener(){
			@Override
			public void onClick(View view) {
				XPResultButtonSet(2);
			}
		};
		return twoPtsListener;
	}
	
	private OnClickListener successListener(final int pts){
		OnClickListener successListener = new OnClickListener(){
			@Override
			public void onClick(View view) {
				_gti.scoreChange(_gti.getPossession(), "XP", pts, null, null);
				recordActivity();
				kickOffButtonSet();
				editField(0, RESET);
			}
		};
		return successListener;
	}
	
	private OnClickListener failedListener(final int pts){
		OnClickListener failedListener = new OnClickListener(){
			@Override
			public void onClick(View view) {
				kickOffButtonSet();
				editField(0, RESET);
			}
		};
		return failedListener;
	}

//TODO Penalty Listeners ------------------------------------------------------------------------------------------------------------
	
	private OnClickListener penaltyListener(final String condition){
		OnClickListener penaltyListener = new OnClickListener(){
			@Override
			public void onClick(View view) {
				
			}
		};
		return penaltyListener;
	}

	private OnClickListener moreOptionsListener(final boolean condition){
		OnClickListener penaltyListener = new OnClickListener(){
			@Override
			public void onClick(View view) {
				if(condition){
					offenseButtonSet();
				}
				else{
					negOffenseButtonSet();
				}
			}
		};
		return penaltyListener;
	}
	
//TODO Field Listener ----------------------------------------------------------------------------------------------------------------
	
	private OnClickListener fieldListener(final int value, final String direction){
		OnClickListener fieldListener = new OnClickListener(){
			@Override
			public void onClick(View v) {				
				if(_fieldActive){													//field needs to be activate
					boolean allClear = true;
					if(_gti.getGameState().equals("kickoff")){						//establishes kickoff procedure
						editField(value, LINEOFSCRIMMAGE);
						_gti.setLineOfScrimmage(value);
						catchKickButtonSet(true);
					}
					else if(_gti.getGameState().equals("onside")){					//establishes onside kick procedure
						editField(value, LINEOFSCRIMMAGE);
						editField(value-10, FIRSTDOWNLINE);
						_gti.setFirstDown();
						_gti.setLineOfScrimmage(value);
						offenseButtonSet();
					}
					else if(_gti.getGameState().equals("punting")){						//establishes punting procedure
						editField(_gti.getLineOfScrimmageAsValue(), RESET);
						editField(_gti.getLineOfScrimmageAsValue()-_gti.getDistance(), RESET);
						_gameLog.formRecord(_gti.getGameState(), Math.abs(value - _gti.getLineOfScrimmageAsValue()));
						editField(value, LINEOFSCRIMMAGE);
						_gti.setLineOfScrimmage(value);
						catchKickButtonSet(false);
					}
					else if(_gti.getGameState().equals("kick return") || _gti.getGameState().equals("punt return")){				//establishes returning kickoff procedure
						editField(_gti.getLineOfScrimmageAsValue(), RESET);
						_gameLog.formRecord(_gti.getGameState(), Math.abs(value - _gti.getLineOfScrimmageAsValue()));
						if(value != 0){				
							editField(value, LINEOFSCRIMMAGE);
							editField(value-10, FIRSTDOWNLINE);						
							_gti.setFirstDown();
							_gti.setLineOfScrimmage(value);
							offenseButtonSet();
						}
						else{
							editField(value, FIRSTDOWNLINE);
							_gti.setLineOfScrimmage(0);
							_gti.scoreChange(_gti.getPossession(), _gti.getGameState(), 6, null, null);
							extraPointButtonSet();
						}
						recordActivity();
					}
					else if(_gti.getGameState().equals("passing") || _gti.getGameState().equals("rushing")){ //passing and rushing
						editField(_gti.getLineOfScrimmageAsValue(), RESET);
						editField(_gti.getLineOfScrimmageAsValue()-_gti.getDistance(), RESET);
						_gameLog.formRecord(_gti.getGameState(), Math.abs(value - _gti.getLineOfScrimmageAsValue()), direction);
						if(value != 0){	
							if(_gti.getDowns() < 4){															//not fourth down
								_gti.advanceDownAndDistance(_gti.getLineOfScrimmageAsValue(), value);
								_gti.setLineOfScrimmage(value);
								editField(value, LINEOFSCRIMMAGE);
								editField(value-_gti.getDistance(), FIRSTDOWNLINE);
								if(_gti.getDowns() == 4){														//becoming fourth down
									fourthDownButtonSet();
								}
								else{																			//downs 2 or 3
									offenseButtonSet();
								}
							}
							else{																				//on the fourth down
								if(_gti.checkFirstDown(_gti.getLineOfScrimmageAsValue(), value)){				//if conversion is good
									editField(value, LINEOFSCRIMMAGE);
									editField(value-10, FIRSTDOWNLINE);	
								}
								else{																			//if conversion is no good
									changePossession();
									editField(100-value, LINEOFSCRIMMAGE);
									editField(100-value-10, FIRSTDOWNLINE);
								}
								offenseButtonSet();
							}
						}
						else{ 				//OFFENSIVE TOUCHDOWN
							editField(value, FIRSTDOWNLINE);
							_gti.setLineOfScrimmage(0);
							_gti.scoreChange(_gti.getPossession(), _gti.getGameState(), 6, null, null);
							recordActivity();
							extraPointButtonSet();
						}
						recordActivity();
					}
					else if(_gti.getGameState().equals("qb sack")){				//establishes qb sacking procedure
						if(value > _gti.getLineOfScrimmageAsValue()){			//new LOS must be greater than previous (lost of yards)
							editField(_gti.getLineOfScrimmageAsValue(), RESET);
							_gameLog.formRecord(_gti.getGameState(), Math.abs(value - _gti.getLineOfScrimmageAsValue()));
							if(value != 100){
								if(_gti.getDowns() < 4){															//not fourth down
									_gti.advanceDownAndDistance(_gti.getLineOfScrimmageAsValue(), value);
									_gti.setLineOfScrimmage(value);
									editField(value, LINEOFSCRIMMAGE);
									if(_gti.getDowns() == 4){				//becoming fourth down on sack
										fourthDownButtonSet();
									}
									else{									//downs 2 or 3
										offenseButtonSet();
									}
								}
								else{										//if sacked on a fourth down
									changePossession();
									editField(100-value, LINEOFSCRIMMAGE);
									editField(100-value-10, FIRSTDOWNLINE);
								}
							}
							else{
								_gti.scoreChange(!_gti.getPossession(), "safety", 2, null, null);
								recordActivity();
								kickOffButtonSet();
							}
						}
						else{
							//maybe display a message that tells user that sack can't go for positive yardage
							allClear = false;
						}
						recordActivity();
					}
					else if (_gti.getGameState().equals("interception")){
						editField(_gti.getLineOfScrimmageAsValue(), RESET);
						editField(_gti.getLineOfScrimmageAsValue()-_gti.getDistance(), RESET);
						_gameLog.formRecord(_gti.getGameState());
						editField(value, LINEOFSCRIMMAGE);
						_gti.setLineOfScrimmage(value);
						intReturnedButtonSet();
						recordActivity();
					}
					else if (_gti.getGameState().equals("fumble lost")){
						editField(_gti.getLineOfScrimmageAsValue(), RESET);
						editField(_gti.getLineOfScrimmageAsValue()-_gti.getDistance(), RESET);	
						_gameLog.formRecord(_gti.getGameState());
						editField(value, LINEOFSCRIMMAGE);
						_gti.setLineOfScrimmage(value);
						fumReturnedButtonSet();
						recordActivity();
					}
					else if (_gti.getGameState().equals("int return") || _gti.getGameState().equals("fum return")){
						editField(_gti.getLineOfScrimmageAsValue(), RESET);	
						_gameLog.formRecord(_gti.getGameState(), Math.abs(value - _gti.getLineOfScrimmageAsValue()));
						changePossession();
						if(value == 100){
							_gti.setLineOfScrimmage(value);
							_gti.switchField();
							editField(_gti.getLineOfScrimmageAsValue(), LINEOFSCRIMMAGE);	
							editField(_gti.getLineOfScrimmageAsValue()-10, FIRSTDOWNLINE);	
							offenseButtonSet();
						}
						else{			//turnover returned for a touchdown
							_gti.setLineOfScrimmage(0);
							editField(0, FIRSTDOWNLINE);
							_gti.scoreChange(_gti.getPossession(), _gti.getGameState(), 6, null, null);							
							extraPointButtonSet();
						}
						recordActivity();
					}
					if(allClear){
						enableButtons();
						_fieldActive = false;
					}
				}
			}
		};
		return fieldListener;
	}

//TODO Create Field --------------------------------------------------------------------------------------------
	
	private void createField(){
		for(int i=0; i<=100; i++){
			_fieldOfPlay.addView(createYard(i, RESET));
		}
	}
	
	private void editField(int line, String color){
		if(line > 0){
			_fieldOfPlay.removeViewAt(line);
			_fieldOfPlay.addView(createYard(50-Math.abs(50-line), color), line);
		}
		else{
			_fieldOfPlay.removeViewAt(0);
			_fieldOfPlay.addView(createYard(0, color), 0);
		}
	}
	
	private LinearLayout createYard(int line, String color){
		LinearLayout layout = new LinearLayout(this);			
		layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));		
		
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		params.weight = 0.3f;
		
		TextView tv1 = new TextView(this);
		tv1.setLayoutParams(params);
		tv1.setOnClickListener(fieldListener(line, "left"));
		
		if(color.equals(FIRSTDOWNLINE)){
			tv1.setBackgroundResource(R.drawable.football_left_yellow);
		}
		else if(color.equals(LINEOFSCRIMMAGE)){
			tv1.setBackgroundResource(R.drawable.football_left_blue);
		}
		else{
			if(line%5 == 0){
				tv1.setBackgroundResource(R.drawable.football_left);
			}
			else{
				tv1.setBackgroundResource(R.drawable.football_lefthash);
			}
		}
		
		TextView tv2 = new TextView(this);
		tv2.setLayoutParams(params);
		tv2.setOnClickListener(fieldListener(line, "middle"));
		tv2.setGravity(Gravity.CENTER);
		
		if(color.equals(FIRSTDOWNLINE)){			
			tv2.setTextColor(getResources().getColor(R.color.yellow));
			if(line == 0){
				tv2.setBackgroundResource(R.drawable.football_middlehash);
				tv2.setText("Touch Down");	
			}
			else if(line == 50){
				tv2.setBackgroundResource(R.drawable.football_middlehash);
				tv2.setText("Midfield");	
			}
			else{
				tv2.setBackgroundResource(R.drawable.football_middle_yellow);
				tv2.setText((50-Math.abs(50-line))+"");					
			}					
		}
		else if(color.equals(LINEOFSCRIMMAGE)){
			tv2.setBackgroundResource(R.drawable.football_middle_blue);
			tv2.setTextColor(getResources().getColor(R.color.blue));
			tv2.setText((50-Math.abs(50-line))+"");
		}
		else{
			tv2.setTextColor(getResources().getColor(R.color.white));
			if(line%50 == 0){
				tv2.setBackgroundResource(R.drawable.football_middlehash);
				tv2.setGravity(Gravity.CENTER);
				if(line==50){
					tv2.setText("Midfield");	
				}
				else if(line==0){
					tv2.setText("Touch Down");
				}	
				else{
					tv2.setText("End Zone");
				}
			}			
			else if(line%5 == 0){
				tv2.setBackgroundResource(R.drawable.football_middle);
				tv2.setText((50-Math.abs(50-line))+"");					
			}
			else{
				tv2.setBackgroundResource(R.drawable.football_middlehash);
			}	
		}
		
		TextView tv3 = new TextView(this);
		tv3.setLayoutParams(params);
		tv3.setOnClickListener(fieldListener(line, "right"));
		if(color.equals(FIRSTDOWNLINE)){
			tv3.setBackgroundResource(R.drawable.football_right_yellow);
		}
		else if(color.equals(LINEOFSCRIMMAGE)){
			tv3.setBackgroundResource(R.drawable.football_right_blue);
		}
		else{
			if(line%5 == 0){
				tv3.setBackgroundResource(R.drawable.football_right);
			}
			else{
				tv3.setBackgroundResource(R.drawable.football_righthash);
			}			
		}
		layout.addView(tv1);
		layout.addView(tv2);
		layout.addView(tv3);
		return layout;
	}

//TODO Select Player Dialog ---------------------------------------------------------------------------------------------------------
	
	private void selectPlayerDialog(final String event){
		Builder builder = new Builder(this);
		builder.setTitle(titleChooser(event));
		final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String> (this, android.R.layout.select_dialog_singlechoice);
		if(_gti.getPossession()){
			for(FootballPlayer fp : _gti.getTheHomeTeam().getRoster()){
				arrayAdapter.add(fp.getName());
			}
		}
		else{
			for(FootballPlayer fp : _gti.getTheAwayTeam().getRoster()){
				arrayAdapter.add(fp.getName());
			}
		}		
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				enableButtons();
			}
		});		
		builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String player = arrayAdapter.getItem(which);
				disableButtons();
				eventHandler(event, player);
			}
		});
		builder.show();
	}
	
	private void selectOppoPlayerDialog(final String event){
		Builder builder = new Builder(this);
		builder.setTitle(titleChooser(event));
		final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String> (this, android.R.layout.select_dialog_singlechoice);
		if(_gti.getPossession()){
			for(FootballPlayer fp : _gti.getTheAwayTeam().getRoster()){
				arrayAdapter.add(fp.getName());
			}
		}
		else{
			for(FootballPlayer fp : _gti.getTheHomeTeam().getRoster()){
				arrayAdapter.add(fp.getName());
			}
		}		
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				enableButtons();
			}
		});		
		builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String player = arrayAdapter.getItem(which);
				disableButtons();
				eventHandler(event, player);
			}
		});
		builder.show();
	}
	
	private String titleChooser(String type){
		if(type.equals("passing") || type.equals("incomplete pass") || type.equals("qb sack") || type.equals("intercepted")){
			return "Passer";
		}
		else if (type.equals("rushing") || type.equals("fumLost")){
			return "Rusher";
		}
		else if (type.equals("receiving")){
			return "Receiver";	
		}
		else if (type.equals("sacking") || type.equals("fumRec") || type.equals("interceptor")){
			return "Defender";
		}
		else if (type.equals("return")){
			return "Returner";
		}
		else if (type.equals("kicking")){
			return "Kicker";
		}
		else if (type.equals("punting")){
			return "Punter";
		}
		else{
			return "ERROR";
		}
	}
	
	private void eventHandler(String type, String player){
		if(type.equals("passing")){
			selectPlayerDialog("receiving");
			_gameLog.setPlayer1(player);
		}
		else if(type.equals("incomplete pass")){
			_gameLog.setPlayer1(player);
			if(_gti.getDowns() < 4){
				_gti.increaseDownsByOne();
				if(_gti.getDowns() == 4){
					fourthDownButtonSet();
				}
				else{
					offenseButtonSet();
				}
			}
			else{
				changePossession();
				editField(_gti.getLineOfScrimmageAsValue(), RESET);
				editField(_gti.getLineOfScrimmageAsValue()-_gti.getDistance(), RESET);
				editField(100-_gti.getLineOfScrimmageAsValue(), LINEOFSCRIMMAGE);
				editField(100-_gti.getLineOfScrimmageAsValue()-10, FIRSTDOWNLINE);
				offenseButtonSet();				
			}
			enableButtons();
		}
		else if (type.equals("rushing")){
			_fieldActive = true;
			_gameLog.setPlayer1(player);
		}
		else if (type.equals("receiving")){
			_fieldActive = true;
			_gameLog.setPlayer2(player);
		}
		
		else if (type.equals("sacking")){
			selectPlayerDialog("qb sack");
			_gameLog.setPlayer1(player);
		}
		else if (type.equals("qb sack")){
			_fieldActive = true;
			_gameLog.setPlayer2(player);
		}
		
		else if (type.equals("fumRec")){
			selectPlayerDialog("fumLost");
			_gameLog.setPlayer1(player);
		}
		else if (type.equals("fumLost")){
			_fieldActive = true;
			_gameLog.setPlayer2(player);
		}
		
		else if (type.equals("interceptor")){
			selectPlayerDialog("intercepted");
			_gameLog.setPlayer1(player);
		}
		else if (type.equals("intercepted")){
			_fieldActive = true;
			_gameLog.setPlayer2(player);
		}
		
		else if (type.equals("return")){
			_fieldActive = true;
			_gameLog.setPlayer1(player);
		}
		else if (type.equals("kicking")){
			_gameLog.setPlayer1(player);
			fieldGoalButtonSet();
			enableButtons();
		}
		else if (type.equals("punting")){
			_fieldActive = true;
			_gameLog.setPlayer1(player);
		}
		else{
			
		}
	}

//TODO Update Score Method ---------------------------------------------------------------------------------------------------------
	
	private void recordActivity(){
		_gameLog.recordActivity(_gameClockView.getText().toString());
		updateScore();	
	}
	
	private void updateScore(){
		_awayScoreTextView.setText(_gti.getAwayScoreText());
		_homeScoreTextView.setText(_gti.getHomeScoreText());
	}
	
//TODO Change Possession Methods ----------------------------------------------------------------------------------------------------
	
	private void changePossession(){
		_gti.changePossession();
		_gti.setFirstDown();
		possessionMarkerChange();		
	}
	
	private void possessionMarkerChange(){
		if(_gti.getPossession()){
			findViewById(R.id.possessionHome).setVisibility(View.VISIBLE);
			findViewById(R.id.possessionAway).setVisibility(View.INVISIBLE);
		}
		else{
			findViewById(R.id.possessionHome).setVisibility(View.INVISIBLE);
			findViewById(R.id.possessionAway).setVisibility(View.VISIBLE);
		}
	}
			
//TODO Start Game Methods ----------------------------------------------------------------------------------------------------------
	
	private OnClickListener startGameListener = new OnClickListener(){
		@Override
		public void onClick(View view) {
			startGameDialog();
			SharedPreferences prefs = getApplicationContext().getSharedPreferences("GameClock", Context.MODE_PRIVATE);
			int minuteTime = Integer.parseInt(prefs.getString("perLenFootball", StaticFinalVars.FOOTBALL_DEFAULT).split(" ")[0]);
			_gameClock = new GameClock(minuteTime*60*1000, _gameClockView);
			_gameClockView.setOnClickListener(timerClickListener);			
		}
	};
	
	private void startGameDialog(){
		Builder startAlert = new Builder(this);
		startAlert.setTitle("Game Time");
		startAlert.setMessage("Which team receives ball first?");
		startAlert.setPositiveButton(_gti.getAwayAbbr(), new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				_gti.setPossession(true);
				enableButtons();
				possessionMarkerChange();				
			}
		});
		startAlert.setNegativeButton(_gti.getHomeAbbr(), new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				_gti.setPossession(false);
				enableButtons();
				possessionMarkerChange();
			}
		});
		startAlert.show();	
	}
	
	//starting and stopping the game clock by clicking it
		public OnClickListener timerClickListener = new OnClickListener(){
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
	}
	
	private void stopClock(){
		_gameClock.stop();
		disableButtons();
	}
	private void stopClockNoButtonReset(){
		_gameClock.stop();
	}
	
	private boolean zeroTime(){
		if(_gameClockView.getText().toString().equals("00:00"))
			return true;
		else
			return false;
	}

//----------------------------------------------------------------------------------------------------------------
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.football_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		if(_gameClock != null){	
			stopClockNoButtonReset();
		}
		return super.onMenuOpened(featureId, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = null;
		switch(item.getItemId()){

		case R.id.boxscore:
			intent = new Intent(getApplicationContext(), FootballStatsActivity.class);			
			_gameInfo = _gti.getGameInfo();
			_gameInfo.setAwayScore(_gti.getAwayScoreText());
			_gameInfo.setHomeScore(_gti.getHomeScoreText());
			
			intent.putExtra(StaticFinalVars.GAME_INFO, _gameInfo);	
			intent.putExtra(StaticFinalVars.GAME_LOG, _playbyplay);
			intent.putExtra(StaticFinalVars.IF_PLAYER_VIEW, false);
			intent.putExtra(StaticFinalVars.IF_GAME_VIEW, true);
			intent.putExtra(StaticFinalVars.DISPLAY_TYPE, 0);
			startActivity(intent);		
			break;
			
		case R.id.gameLog:
			intent = new Intent(getApplicationContext(), FootballStatsActivity.class);	
			_gameInfo = _gti.getGameInfo();
			_gameInfo.setAwayScore(_gti.getAwayScoreText());
			_gameInfo.setHomeScore(_gti.getHomeScoreText());
			intent.putExtra(StaticFinalVars.GAME_INFO, _gameInfo);	
			intent.putExtra(StaticFinalVars.GAME_LOG, _playbyplay);
			intent.putExtra(StaticFinalVars.IF_PLAYER_VIEW, false);
			intent.putExtra(StaticFinalVars.IF_GAME_VIEW, true);
			intent.putExtra(StaticFinalVars.DISPLAY_TYPE, 1);
			startActivity(intent);
			break;
		
		case R.id.editTime:
			if(_gameClock != null){
				editTime();
			}
			break;
			
		case R.id.nextQuarter:
			SharedPreferences prefs = getApplicationContext().getSharedPreferences("GameClock", Context.MODE_PRIVATE);
			int numPer = Integer.parseInt(prefs.getString("numPerFootball", "1 Period").split(" ")[0]);
			String quarter = ((TextView)findViewById(R.id.quarterNumber)).getText().toString();
			if(zeroTime()){
				if(quarter.equals("1ST") && numPer > 1){
					((TextView)findViewById(R.id.quarterNumber)).setText("2ND");
				}
				else if(quarter.equals("2ND") && numPer > 2){
					((TextView)findViewById(R.id.quarterNumber)).setText("3RD");
				}
				else if(quarter.equals("3RD") && numPer > 3){
					((TextView)findViewById(R.id.quarterNumber)).setText("4TH");
				}
				else if((quarter.equals("1ST") && numPer == 1) || (quarter.equals("2ND") && numPer == 2) ||
						(quarter.equals("3RD") && numPer == 3) || quarter.equals("4TH")){
					if(Integer.parseInt(_homeScoreTextView.getText().toString()) == Integer.parseInt(_awayScoreTextView.getText().toString())){
						((TextView)findViewById(R.id.quarterNumber)).setText("OT");
						ot = 1;
					}
					else{
						endGame();
						break;
					}
				}
				else{
					if(Integer.parseInt(_homeScoreTextView.getText().toString()) == Integer.parseInt(_awayScoreTextView.getText().toString())){
						ot++;
						((TextView)findViewById(R.id.quarterNumber)).setText(ot+"-OT");
					}
					else{
						endGame();
						break;
					}
				}
				if(ot == 0){
					int minuteTime = Integer.parseInt(prefs.getString("perLenFootball", StaticFinalVars.FOOTBALL_DEFAULT).split(" ")[0]);
					_gameClock.restartTimer(minuteTime*60*1000);
					_gameClockView.setOnClickListener(startGameListener);
				}
				else{
					int minuteTime = Integer.parseInt(prefs.getString("otLenFootball", StaticFinalVars.FOOTBALL_OT_DEFAULT).split(" ")[0]);
					_gameClock.restartTimer(minuteTime*60*1000);
					_gameClockView.setOnClickListener(startGameListener);
				}
			}
			break;
		}
		_awayScoreTextView.setText(_gti.getAwayScoreText());
		_homeScoreTextView.setText(_gti.getHomeScoreText());
		return true;
	}
	
	private void endGame(){
		_gameClockView.setText("FINAL");
		_gameClockView.setOnClickListener(null);
		_fieldActive = false;
		disableButtons();
	}
	
//TODO Editing Time -----------------------------------------------------------------------------------------------------------------
	
	private void editTime(){
		Builder builder = new Builder(this);
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
				
				if(ot == 0 && minutes < Integer.parseInt(prefs.getString("perLenFootball", "12 minutes").split(" ")[0])){
					minutes++;
				}
				else if (ot != 0 && minutes < Integer.parseInt(prefs.getString("otLenFootball", "5 minutes").split(" ")[0])){
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
}
