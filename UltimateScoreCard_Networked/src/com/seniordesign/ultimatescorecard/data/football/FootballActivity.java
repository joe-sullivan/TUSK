package com.seniordesign.ultimatescorecard.data.football;

import java.util.ArrayList;

import com.seniordesign.ultimatescorecard.R;
import com.seniordesign.ultimatescorecard.R.color;
import com.seniordesign.ultimatescorecard.R.drawable;
import com.seniordesign.ultimatescorecard.R.id;
import com.seniordesign.ultimatescorecard.R.layout;
import com.seniordesign.ultimatescorecard.clock.GameClock;
import com.seniordesign.ultimatescorecard.data.GameInfo;
import com.seniordesign.ultimatescorecard.data.basketball.BasketballGameTime;
import com.seniordesign.ultimatescorecard.sqlite.basketball.BasketballDatabaseHelper;
import com.seniordesign.ultimatescorecard.sqlite.football.FootballDatabaseHelper;
import com.seniordesign.ultimatescorecard.sqlite.helper.PlayByPlay;
import com.seniordesign.ultimatescorecard.sqlite.helper.ShotChartCoords;
import com.seniordesign.ultimatescorecard.view.FlyOutContainer;
import com.seniordesign.ultimatescorecard.view.StaticFinalVars;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

public class FootballActivity extends Activity{
	public static final int PRO_TIME = 900000;
	public static final String FIRSTDOWNLINE = "yellow";
	public static final String LINEOFSCRIMMAGE = "blue";
	public static final String RESET = "reset";
	
	FlyOutContainer _root;
	RelativeLayout _homeLayout, _awayLayout;
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
	private ArrayList<ShotChartCoords> _homeShots, _awayShots;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//databases
		_football_db = new FootballDatabaseHelper(getApplicationContext());
		
		_root = (FlyOutContainer)this.getLayoutInflater().inflate(R.layout.activity_football, null);	//root is modified view with fly-out container														
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
		
		_fieldScroll = (ScrollView)findViewById(R.id.footballField);
		_fieldOfPlay = (LinearLayout)findViewById(R.id.fieldOfPlayList);
		createField();	
				
		_option1Button = (Button)findViewById(R.id.optionButton1);								
		_option2Button = (Button)findViewById(R.id.optionButton2);
		_option3Button = (Button)findViewById(R.id.optionButton3);
		_option4Button = (Button)findViewById(R.id.optionButton4);
		_option5Button = (Button)findViewById(R.id.optionButton5);
		
		kickOffButtonSet();
	}
	
//-----------------------------------------------------------------------------------------------------------------------------------------
	
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
		setTextAndListener(_option3Button, penaltyListener("driving"), "Penalty");
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
	
	private void twoPtsResultButtonSet(){
		buttonSwap(false);
		setTextAndListener(_option4Button, successListener(), "Success");
		setTextAndListener(_option5Button, failedListener(), "Failed");
	}
	
	private void onsideKickButtonSet(){
		buttonSwap(false);
		setTextAndListener(_option4Button, onsideKickSuccessListener(), "Success");
		setTextAndListener(_option5Button, onsideKickFailedListener(), "Failed");
	}
	
	private void catchKickButtonSet(){
		buttonSwap(true);
		setTextAndListener(_option1Button, fairCatchListener(), "Fair Catch");
		setTextAndListener(_option2Button, returnKickListener(), "Returned");
		setTextAndListener(_option3Button, penaltyListener("return"), "Penalty");
	}
	
	private void passOptionButtonSet(String passer){
		buttonSwap(true);
		setTextAndListener(_option1Button, completePassListener(passer), "Complete");
		setTextAndListener(_option2Button, incompletePassListener(passer), "Incomplete");
		setTextAndListener(_option3Button, qbSackedListener(passer), "QB Sacked");
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
	
//----------------------------------------------------------------------------------------------------------------------------------------
	
	private OnClickListener kickOffListener(){
		OnClickListener kickOffListener = new OnClickListener(){
			@Override
			public void onClick(View view) {
				_fieldActive = true;
				catchKickButtonSet();
				disableButtons();
			}
		};
		return kickOffListener;
	}
	
	private OnClickListener fieldGoalMadeListener(){
		OnClickListener fieldGoalMadeListener = new OnClickListener(){
			@Override
			public void onClick(View view) {
				
			}
		};
		return fieldGoalMadeListener;
	}
	
	private OnClickListener fieldGoalMissListener(){
		OnClickListener fieldGoalMissListener = new OnClickListener(){
			@Override
			public void onClick(View view) {
				
			}
		};
		return fieldGoalMissListener;
	}
	
	private OnClickListener onsideKickListener(){
		OnClickListener onsideKickListener = new OnClickListener(){
			@Override
			public void onClick(View view) {
				
			}
		};
		return onsideKickListener;
	}
	
	private OnClickListener onsideKickSuccessListener(){
		OnClickListener onsideKickSuccessListener = new OnClickListener(){
			@Override
			public void onClick(View view) {
				
			}
		};
		return onsideKickSuccessListener;
	}
	
	private OnClickListener onsideKickFailedListener(){
		OnClickListener onsideKickFailedListener = new OnClickListener(){
			@Override
			public void onClick(View view) {
				
			}
		};
		return onsideKickFailedListener;
	}
	
	private OnClickListener fairCatchListener(){
		OnClickListener fairCatchListener = new OnClickListener(){
			@Override
			public void onClick(View view) {
				
			}
		};
		return fairCatchListener;
	}
	
	private OnClickListener returnKickListener(){
		OnClickListener returnKickListener = new OnClickListener(){
			@Override
			public void onClick(View view) {
				_gti.returning(true);
				selectPlayerDialog("returning");
			}
		};
		return returnKickListener;
	}
	
	private OnClickListener passListener(){
		OnClickListener passListener = new OnClickListener(){
			@Override
			public void onClick(View view) {
				selectPlayerDialog("passing");
			}
		};
		return passListener;
	}
	
	private OnClickListener rushListener(){
		OnClickListener rushListener = new OnClickListener(){
			@Override
			public void onClick(View view) {
				selectPlayerDialog("rushing");
			}
		};
		return rushListener;
	}
	
	private OnClickListener penaltyListener(final String condition){
		OnClickListener penaltyListener = new OnClickListener(){
			@Override
			public void onClick(View view) {
				
			}
		};
		return penaltyListener;
	}
	
	private OnClickListener qbSackedListener(final String passer){
		OnClickListener qbSackedListener = new OnClickListener(){
			@Override
			public void onClick(View view) {
				
			}
		};
		return qbSackedListener;
	}
	
	private OnClickListener completePassListener(final String passer){
		OnClickListener completePassListener = new OnClickListener(){
			@Override
			public void onClick(View view) {
				
			}
		};
		return completePassListener;
	}
	
	private OnClickListener incompletePassListener(final String passer){
		OnClickListener incompletePassListener = new OnClickListener(){
			@Override
			public void onClick(View view) {
				
			}
		};
		return incompletePassListener;
	}
	
	private OnClickListener puntListener(){
		OnClickListener puntListener = new OnClickListener(){
			@Override
			public void onClick(View view) {
				
			}
		};
		return puntListener;
	}
	
	private OnClickListener fieldGoalListener(){
		OnClickListener fieldGoalListener = new OnClickListener(){
			@Override
			public void onClick(View view) {
				
			}
		};
		return fieldGoalListener;
	}
	
	private OnClickListener conversionListener(){
		OnClickListener conversionListener = new OnClickListener(){
			@Override
			public void onClick(View view) {
				
			}
		};
		return conversionListener;
	}
	
	private OnClickListener PATListener(){
		OnClickListener PATListener = new OnClickListener(){
			@Override
			public void onClick(View view) {
				
			}
		};
		return PATListener;
	}
	
	private OnClickListener twoPtsListener(){
		OnClickListener twoPtsListener = new OnClickListener(){
			@Override
			public void onClick(View view) {
				
			}
		};
		return twoPtsListener;
	}
	
	private OnClickListener successListener(){
		OnClickListener successListener = new OnClickListener(){
			@Override
			public void onClick(View view) {
				
			}
		};
		return successListener;
	}
	
	private OnClickListener failedListener(){
		OnClickListener failedListener = new OnClickListener(){
			@Override
			public void onClick(View view) {
				
			}
		};
		return failedListener;
	}

//------------------------------------------------------------------------------------------------------------------------------------------
	
	private OnClickListener fieldListener(final int value, final String direction){
		OnClickListener fieldListener = new OnClickListener(){
			@Override
			public void onClick(View v) {
				if(_fieldActive){
					if(_gti.getDownDistance()[0] != 0){
						editField(_gti.getLineOfScrimmage(), RESET, RESET);
						_gameLog.formRecord(_gti.getLineOfScrimmage()-value, direction);
						_gameLog.recordActivity(_gti.getDownDistance()[0]+" and "+_gti.getDownDistance()[1]);
					}									
					_gti.setLineOfScrimmage(value);
					editField(value, LINEOFSCRIMMAGE, FIRSTDOWNLINE);
					enableButtons();
					_fieldActive = false;
					
				}
				if(_gti.isReturned()){
					_gti.returning(false);
				}
			}
		};
		return fieldListener;
	}
	
	private void createField(){
		for(int i=0; i<=100; i++){
			_fieldOfPlay.addView(createYard(i, RESET));
		}
	}
	
	private void editField(int line, String color, String color2){
		_fieldOfPlay.removeViewAt(line);
		_fieldOfPlay.addView(createYard(50-Math.abs(50-line), color), line);
		
		_fieldOfPlay.removeViewAt(line-_gti.getDownDistance()[1]);
		_fieldOfPlay.addView(createYard(50-Math.abs(50-(line-_gti.getDownDistance()[1])), color2), line-_gti.getDownDistance()[1]);
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
			tv2.setBackgroundResource(R.drawable.football_middle_yellow);
			tv2.setTextColor(getResources().getColor(R.color.yellow));
			tv2.setText((50-Math.abs(50-line))+"");
			
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
					if(_gti.getAwayTeam().split(" ").length > 2){
						tv2.setText(_gti.getAwayTeam().split(" ")[0] +" "+ _gti.getAwayTeam().split(" ")[1]);
					}
					else{
						tv2.setText(_gti.getAwayTeam());
					}
				}	
				else{
					if(_gti.getHomeTeam().split(" ").length > 2){
						tv2.setText(_gti.getHomeTeam().split(" ")[0] +" "+_gti.getHomeTeam().split(" ")[1]);
					}
					else{
						tv2.setText(_gti.getHomeTeam());
					}
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

//------------------------------------------------------------------------------------------------------------------------------------
	
	private void selectPlayerDialog(final String event){
		Builder builder = new Builder(this);
		builder.setTitle(titleChooser(event));
		final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String> (this,
				android.R.layout.select_dialog_singlechoice);
		
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
	
	private String titleChooser(String type){
		if(type.equals("passing")){
			return "Passer";
		}
		else if (type.equals("rushing")){
			return "Rusher";
		}
		else if (type.equals("receiving")){
			return "Receiver";	
		}
		else if (type.equals("defending")){
			return "Defender";
		}
		else if (type.equals("returning")){
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
			_fieldActive = false;
			passOptionButtonSet(player);
			selectPlayerDialog("receiving");
			_gameLog.setPlayer1(player, type);
		}
		else if (type.equals("rushing")){
			_fieldActive = true;
			offenseButtonSet();
			_gameLog.setPlayer1(player, type);
		}
		else if (type.equals("receiving")){
			_fieldActive = true;
			offenseButtonSet();
			_gameLog.setPlayer2(player);
		}
		else if (type.equals("defending")){
			
		}
		else if (type.equals("returning")){
			_fieldActive = true;
			offenseButtonSet();
			_gameLog.setPlayer1(player, type);
		}
		else if (type.equals("kicking")){
			
		}
		else if (type.equals("punting")){
			
		}
		else{
			
		}
	}
}
