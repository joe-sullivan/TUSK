package com.seniordesign.ultimatescorecard;

import com.seniordesign.ultimatescorecard.data.DoubleParamOnClickListener;
import com.seniordesign.ultimatescorecard.data.GameLog;
import com.seniordesign.ultimatescorecard.data.GameTime;
import com.seniordesign.ultimatescorecard.stats.StatsActivity;
import com.seniordesign.ultimatescorecard.substitution.SubstitutionActivity;
import com.seniordesign.ultimatescorecard.view.FlyOutContainer;
import com.seniordesign.ultimatescorecard.view.StaticFinalVars;
import com.seniordesign.ultimatescorecard.clock.GameClock;
import com.seniordesign.ultimatescorecard.view.ShotIconAdder;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BasketballActivity extends Activity{
	public static final int SUBSTITUTION_CODE = 1;
	public static final int COLLEGE_TIME = 1200000;
	public static final int PRO_TIME = 900000;
	
	FlyOutContainer _root;
	RelativeLayout _homeLayout, _awayLayout;
	TextView _homeTextView, _awayTextView, _gameClockView, _quarterNumberView;											//all the different items on the screen
	TextView _homeScoreTextView, _awayScoreTextView;
	ImageView _basketballCourt, _basketballCourtMask;
	Bitmap _bitmap;
	Button _p1Button, _p2Button, _p3Button, _p4Button, _p5Button, _otherButton;
	Button _option1Button, _option2Button, _option3Button, _option4Button, _option5Button;

	private GameClock _gameClock;															//strings containing name of home and away team
	private GameTime _gti;
	private GameLog _gameLog = new GameLog();
	private ShotIconAdder _iconAdder;
	
	//on creation of the page, trying to save all items that will appear on screen into a member variable
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		_root = (FlyOutContainer)this.getLayoutInflater().inflate(R.layout.activity_basketball, null);	//root is modified view with fly-out container														
		_gti = (GameTime) getIntent().getSerializableExtra(StaticFinalVars.GAME_INFO); 					//get our team informations class
		setContentView(_root);
		
		_awayTextView = (TextView)findViewById(R.id.awayTextView);									//referencing the different views displayed via their id
		_awayTextView.setText(_gti.getAwayAbbr());													//change text to denote home and away team
		_homeTextView = (TextView)findViewById(R.id.homeTextView);
		_homeTextView.setText(_gti.getHomeAbbr());
		
		Typeface quartz = Typeface.createFromAsset(getAssets(), "fonts/quartz.ttf");				//changing the font style to our own
		_awayScoreTextView = (TextView)findViewById(R.id.awayScoreTextView);						//our personal font type is stored in the assets folder
		_awayScoreTextView.setTypeface(quartz);
		_homeScoreTextView = (TextView)findViewById(R.id.homeScoreTextView);
		_homeScoreTextView.setTypeface(quartz);
	
		_quarterNumberView = (TextView) findViewById(R.id.quarterNumber);
		
		_gameClockView = (TextView) findViewById(R.id.gameClock);									//this is the view that will become the game clock
		_gameClockView.setOnClickListener(startGameListener);										//it will also serve as the button that will start the recording
		_gameClockView.setTypeface(quartz);
		
		_basketballCourt = (ImageView)findViewById(R.id.basketballCourt);
		_basketballCourtMask = (ImageView)findViewById(R.id.basketballCourtMask);
		_bitmap = ((BitmapDrawable) _basketballCourtMask.getDrawable()).getBitmap();				//bitmap of our courtMask, needed for map to work
		
		_homeLayout = (RelativeLayout)findViewById(R.id.homeShotIcons);
		_awayLayout = (RelativeLayout)findViewById(R.id.awayShotIcons);
		_iconAdder = new ShotIconAdder(_homeLayout, _awayLayout, getApplicationContext());
		
		_p1Button = (Button)findViewById(R.id.extendButton1);										//our slide out buttons
		_p2Button = (Button)findViewById(R.id.extendButton2);
		_p3Button = (Button)findViewById(R.id.extendButton3);
		_p4Button = (Button)findViewById(R.id.extendButton4);
		_p5Button = (Button)findViewById(R.id.extendButton5);
		_otherButton = new Button(this);
		
		_option1Button = (Button)findViewById(R.id.optionButton1);								//more buttons and setting onClick listeners
		_option2Button = (Button)findViewById(R.id.optionButton2);
		_option3Button = (Button)findViewById(R.id.optionButton3);
		_option4Button = (Button)findViewById(R.id.optionButton4);									//again these are buttons, this set is hidden initially
		_option5Button = (Button)findViewById(R.id.optionButton5);
		
		_option1Button.setText("Turnover");
		_option2Button.setText("Fouls");
		_option3Button.setText("Block Shot");
	}
	
//[BBARV]:Add/Remove Views------------------------------------------------------------------------------------------------------------------------------------
/*A dedicated section to add and remove views from the screen. */
	
	//adding a view to the screen
	private void addView(View view, String text){		
		LinearLayout slideoutButtons = (LinearLayout)view;
		LayoutParams parameters = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f);
		_otherButton.setText(text);
		slideoutButtons.addView(_otherButton, parameters);
	}
	
	//removing a view from the screen
	private void removeView(View view){
		((LinearLayout)view.getParent()).removeView(view);
	}
	
//[BBSOM]:Slide-Out Menu-----------------------------------------------------------------------------------------------------------------------------------------
/* These are the implemented methods that will allow our slide-out menus to work effectively
 we can slide the menu in and out, and dynamically change settings of the menu to get it to work
 the way we want it to. */
	
	//activate the slide out menu
	private void toggleMenu(OnClickListener listener, String title){
		changeMenu(listener, title);
		_root.toggleMenu();
	}
		
	//changing the functionality of the buttons on slide out menu, we pass in the click listener
	private void changeMenu(OnClickListener listener, String title){
		((TextView)findViewById(R.id.slideoutTitle)).setText(title);
		_p1Button.setOnClickListener(listener);
		_p2Button.setOnClickListener(listener);
		_p3Button.setOnClickListener(listener);
		_p4Button.setOnClickListener(listener);
		_p5Button.setOnClickListener(listener);
	}
	
	//changing the texts found on the slide out menu
	private void setSlideOutButtonText(boolean whichTeam){
		String teamName = null;
		if(whichTeam){
			teamName = _gti.getHomeTeam();
		}
		else{
			teamName = _gti.getAwayTeam();
		}
		_p1Button.setText(_gti.getPlayer(teamName, 0).getName());
		_p2Button.setText(_gti.getPlayer(teamName, 1).getName());
		_p3Button.setText(_gti.getPlayer(teamName, 2).getName());
		_p4Button.setText(_gti.getPlayer(teamName, 3).getName());
		_p5Button.setText(_gti.getPlayer(teamName, 4).getName());
	}
	
	//activates disable buttons on the slide out menu
	private void reactivateButton(){
		if(!_p1Button.isEnabled()){
			_p1Button.setEnabled(true);
		}
		else if(!_p2Button.isEnabled()){
			_p2Button.setEnabled(true);
		}
		else if(!_p3Button.isEnabled()){
			_p3Button.setEnabled(true);
		}
		else if(!_p4Button.isEnabled()){
			_p4Button.setEnabled(true);
		}
		else{
			_p5Button.setEnabled(true);
		}
	}

//[BBIC]:Interactive Court----------------------------------------------------------------------------------------------------------------------------------------	
/*Our interactive court is consist of three images, two of them are displayed on screen while the last one is hidden
 this hidden image is a mask, it allows us to calculate where the user touched on the screen and how many point that shot is */
	
	//listener to control what happen when court is touched
	public OnTouchListener courtInteraction = new OnTouchListener(){
		@SuppressLint("NewApi")
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if(event.getAction() == MotionEvent.ACTION_DOWN){									//need this if statement to keep method execution to one
				int pixel = _bitmap.getPixel((int)event.getX(), (int)event.getY());				//get where the user touches
				int redValue = Color.red(pixel);												//get the color of that location and divide it into RGB values
				int blueValue = Color.blue(pixel);
				int greenValue = Color.green(pixel);
				_iconAdder.setShotLocation((int)event.getX(), (int)event.getY());
				if(redValue > blueValue && redValue > greenValue){								//red is the three point range
					setMadeMissListeners(3);														//we call our button swap method
				}
				else if (blueValue > redValue && blueValue > greenValue){						//blue and green are 2 points (blue mean in the paint)
					setMadeMissListeners(2);
				}
				else if (greenValue > blueValue && greenValue > redValue){
					setMadeMissListeners(2);
				}
			}
			return false;
		}
	};
	
	public OnTouchListener cancelListener = new OnTouchListener(){
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if(event.getAction() == MotionEvent.ACTION_DOWN){
				resetFeatures();
			}
			return false;
		}
	}; 
	
//[BBBB]:Bottom Buttons--------------------------------------------------------------------------------------------------------------------------------------	
/*There are two sets of buttons on the bottom of the screen, one set is hidden initially.
 This section of code modifies these buttons, whether it is to change the values, or swapping 
 their visibility values.*/
	
	private void setMainSettings(){
		buttonSwap(true);
		_basketballCourt.setOnTouchListener(courtInteraction);
		setTextAndListener(_option1Button, turnoverListener, "Turnover");
		setTextAndListener(_option2Button, foulListener, "Fouled");
		setTextAndListener(_option3Button, blockListener, "Block Shot");
		zeroTimeDisabler();
	}
	
	public void disableMainSettings(){
		buttonSwap(true);
		_basketballCourt.setOnTouchListener(null);
		setTextAndListener(_option1Button, null, "Turnover");
		setTextAndListener(_option2Button, null, "Fouled");
		setTextAndListener(_option3Button, null, "Block Shot");
	}
	
	//change the text and clickListeners for the hidden row - made or miss shot (after interactive court is touched)
	private void setMadeMissListeners(int points){
		buttonSwap(true);
		_basketballCourt.setOnTouchListener(cancelListener);
		setTextAndListener(_option1Button, madeListener(points), "Shot Made");
		setTextAndListener(_option2Button, missListener(points), "Shot Missed");
		setTextAndListener(_option3Button, shootingFoulListener(points), "Fouled");
		zeroTimeDisabler();
																										
	}
	
	//change the text and clickListeners for the hidden row - steal or un-forced turnover (when turnover button is clicked)
	private void setTurnoverOptionListener(){
		buttonSwap(true);
		_basketballCourt.setOnTouchListener(cancelListener);
		setTextAndListener(_option1Button, stealListener, "Steal");
		setTextAndListener(_option2Button, unforcedTOListener, "Unforced Turnover");
		setTextAndListener(_option3Button, teamTOListener, "Team Turnover");
		zeroTimeDisabler();
	}
	
	private void setFoulOptionListener(){
		buttonSwap(true);
		_basketballCourt.setOnTouchListener(cancelListener);
		setTextAndListener(_option1Button, personalFoulListener, "Personal Foul");
		setTextAndListener(_option2Button, technicalFoulListener, "Technical Foul");
		setTextAndListener(_option3Button, flagrantFoulListener, "Flagrant Foul");
		zeroTimeDisabler();
	}
	
	private void setPersonalFoulListener(){
		buttonSwap(true);
		_basketballCourt.setOnTouchListener(cancelListener);
		setTextAndListener(_option1Button, offensiveFoulListener, "Offensive Foul");
		setTextAndListener(_option2Button, defensiveFoulListener, "Defensive Foul");
		setTextAndListener(_option3Button, intentionalFoulListener, "Intentional Foul");
		zeroTimeDisabler();
	}
	
	private void setFlagrantFoulListener(){
		buttonSwap(false);
		_basketballCourt.setOnTouchListener(cancelListener);
		setTextAndListener(_option4Button, flagrant1Listener, "Flagrant 1");
		setTextAndListener(_option5Button, flagrant2Listener, "Flagrant 2");
	}
	
	private void setMadeMissListener(int points){
		buttonSwap(false);
		_basketballCourt.setOnTouchListener(null);
		setTextAndListener(_option4Button, madeListener(points), "Shot Made");
		setTextAndListener(_option5Button, missListener(points), "Shot Miss");
	}
	
	private void setFTOptionListener(int value, String str){
		buttonSwap(false);
		_basketballCourt.setOnTouchListener(null);
		setTextAndListener(_option4Button, FTMadeListener(value, str), "Free Throw Made");
		setTextAndListener(_option5Button, FTMissListener(value, str), "Free Throw Miss");
	}
		
	private void setTextAndListener(Button button, OnClickListener listener, String text){
		button.setText(text);
		button.setOnClickListener(listener);
	}
	
	//switching the set of buttons on the bottom of the screen
	private void buttonSwap (boolean whichSet){
		if(whichSet){																			//from original to prompting made or missed shot
			findViewById(R.id.twinOptionRow).setVisibility(View.INVISIBLE);
			findViewById(R.id.tripleOptionRow).setVisibility(View.VISIBLE);
		}
		else{																					//the opposite of the above to bring back original
			findViewById(R.id.tripleOptionRow).setVisibility(View.INVISIBLE);
			findViewById(R.id.twinOptionRow).setVisibility(View.VISIBLE);			
		}
	}
	
	private void zeroTimeDisabler(){
		if(zeroTime()){
			disableMainSettings();
		}
	}
	
//[BBFGA]:Field Goal Attempted----------------------------------------------------------------------------------------------------------------------------------------
/*After the user touches the interactive map, two buttons will appear on the bottom of the screen (see Bottom Buttons).
 These are the listeners for those buttons*/
	
	//shot is attempted and made, this is what made button would do
	public OnClickListener madeListener(int value){
		OnClickListener madeListener = new DoubleParamOnClickListener(value, null){
			@Override
			public void onClick(View v) {
				_iconAdder.setShotHitMiss(_gti.getPossession(), true);
				toggleMenu(madeFGPlayerSelectListener(this.getValue()), "Scored by:");
			}
		};
		return madeListener;
	}
	
	//shot is attempted and missed, this is what missed button would do
	public OnClickListener missListener(int value){
		OnClickListener missListener = new DoubleParamOnClickListener(value, null){
			@Override
			public void onClick(View v) {
				_iconAdder.setShotHitMiss(_gti.getPossession(), false);
				toggleMenu(missFGPlayerSelectListener(this.getValue()), "Missed by:");
			}
		};
		return missListener;
	}
	
//[BBPSB]:Points Scored By--------------------------------------------------------------------------------------------------------------------------------------	
/* After clicking whether made or miss (see [BBFGA]:Field Goal Attempted), the program will prompt the user to select
	which player scored, these are the listeners for the toggle menu buttons to indicate which player is selected 
 	and increase the statistics as indicated */
	
	//in toggle menu, selecting which player score the FG
	public OnClickListener madeFGPlayerSelectListener(int value){
		OnClickListener madeFGPlayerSelectListener = new DoubleParamOnClickListener(value, null){
			@Override
			public void onClick(View view) {
				_gameLog.shooting(this.getValue(), true, ((TextView)view).getText().toString());
				_gti.scoreChange(_gti.getPossession(), this.getValue(), ((TextView)view).getText().toString());						//change the score and update player points
				addView((View)view.getParent(), "No Assist");
				_otherButton.setOnClickListener(noAssistListener(this.getValue(), ((TextView)view).getText().toString()));
				((Button)view).setEnabled(false);
				changeMenu(assistListener(this.getValue(), ((TextView)view).getText().toString()), "Assisted by:");
			}
		};
		return madeFGPlayerSelectListener;
	}
	
	//in toggle menu, selecting which player missed the FG
	public OnClickListener missFGPlayerSelectListener(int value){
		OnClickListener missPlayerSelectListener = new DoubleParamOnClickListener(value, null){
			@Override
			public void onClick(View view) {
				if(_gti.foulOccurred()){
					changeMenu(fouledByListener(this.getValue(), ((TextView)view).getText().toString()), "Fouled by:");	
					setSlideOutButtonText(!_gti.getPossession());
				}
				else {
					_gameLog.shooting(this.getValue(), false, ((TextView)view).getText().toString());
					if(this.getValue() == 3){
						_gti.getPlayer(((TextView)view).getText().toString()).missThree();	
					}
					else{ 
						_gti.getPlayer(((TextView)view).getText().toString()).missTwo();
					}
					addView((View)view.getParent(), "O-Rebound");
					_otherButton.setOnClickListener(offReboundListener);
					changeMenu(reboundListener, "Rebound by:");						//change the fly-out menu to ask for rebound
					setSlideOutButtonText(!_gti.getPossession());
				}
			}
		};
		return missPlayerSelectListener;
	}
		
		
//[BBRAA]:Rebound and Assist----------------------------------------------------------------------------------------------------------------------------------------
/* After prompting for who score the basket, the application will as for who gets the assist or rebound depending
  on whether the shot has a hit or a miss. Here is the listener for the set of player names that can be clicked*/
	
	//in toggle menu, selecting which player grabbed the rebound
	public OnClickListener reboundListener = new OnClickListener(){
		@Override
		public void onClick(View view) {	
			_gameLog.rebounding(((TextView)view).getText().toString());
			_gti.getPlayer(((TextView)view).getText().toString()).grabRebound();			//increase player rebound total				
			if(_otherButton.getText().equals("O-Rebound")){									//if O-Rebound is on screen, then the play must have been a D-Rebound
				changePossession();															//after D-Rebound, we change possession
			}
			recordActivity();																//record the activity
			if(_gti.foulOccurred()){														//additional stuff to do if that rebound came after a foul
				_gti.setFoulVariable(false);												//no longer a foul play
				_gameClockView.setOnClickListener(timerClickListener);
			}															
			resetFeatures();
		}
	};
	
	//in toggle menu, selecting which player grabbed the rebound
	public OnClickListener offReboundListener = new OnClickListener(){
		@Override
		public void onClick(View view) {
			if(_otherButton.getText().equals("O-Rebound")){	
				setSlideOutButtonText(_gti.getPossession());
				_otherButton.setText("D-Rebound");
			}
			else{	
				setSlideOutButtonText(!_gti.getPossession());
				_otherButton.setText("O-Rebound");
			}
		}
	};
	
	//in toggle menu, selecting which player had the assist on the play
	public OnClickListener assistListener(int value, String str){
		OnClickListener assistListener = new DoubleParamOnClickListener(value, str){
			@Override
			public void onClick(View view) {
				_gameLog.assisting(((TextView)view).getText().toString());
				_gti.getPlayer(((TextView)view).getText().toString()).dishAssist();				//increase player assist count
				reactivateButton();																//reactivate disabled button
				recordActivity();																
				if(_gti.foulOccurred()){																	//if there is a foul on the play
					removeView(_otherButton);
					setSlideOutButtonText(!_gti.getPossession());										//setting up slide out menu to record who committed foul
					changeMenu(fouledByListener(1, this.getString()), "Fouled by:");																				
				}
				else{																			//otherwise
					changePossession();															//scoring play process over, possession to opponent		
					resetFeatures();
				}
			}
		};
		return assistListener;
	}
		
	//in toggle menu, to select that not player assisted on the play
	public OnClickListener noAssistListener (int value, String str){
		OnClickListener noAssistListener = new DoubleParamOnClickListener(value, str){
			@Override
			public void onClick(View view){
				reactivateButton();																//reactivate disabled button
				recordActivity();																
				if(_gti.foulOccurred()){
					removeView(_otherButton);
					setSlideOutButtonText(!_gti.getPossession());										
					changeMenu(fouledByListener(1, this.getString()), "Fouled by:");																					
				}
				else{
					changePossession();															//scoring play process over, possession to opponent		
					resetFeatures();
				}
			}
		};
		return noAssistListener;
	}

//[BBTA]:Turnover Actions---------------------------------------------------------------------------------------------------------------------------------------
/*This section of code is the listeners for when the turnover button is clicked and all the subsequent listeners that are needed
 for buttons that will altogether enforce turnovers in our program. */
	
	//turnover listener (preliminary implementation - just changes possession)
	public OnClickListener turnoverListener = new OnClickListener(){
		@Override
		public void onClick(View view) {
			setTurnoverOptionListener();
		}
	};
	
	//in new set of buttons, it initials the steal
	public OnClickListener stealListener = new OnClickListener(){
		@Override
		public void onClick(View view) {
			setSlideOutButtonText(!_gti.getPossession());
			toggleMenu(stealByListener, "Stolen by:");
		}
	};
	
	//in toggle menu, listener for selection of who stole the ball
	public OnClickListener stealByListener = new OnClickListener(){
		@Override
		public void onClick(View view) {
			_gti.getPlayer(((Button)view).getText().toString()).turnedOver();
			setSlideOutButtonText(_gti.getPossession());
			changeMenu(stealFromListener(((Button)view).getText().toString()), "Stolen From");
		}
	};
	
	//in toggle menu, listener for selecting who the ball was stolen from
	private OnClickListener stealFromListener(String player){
		OnClickListener stealFromListener = new DoubleParamOnClickListener(0, player){
			@Override
			public void onClick(View view) {
				_gameLog.stealing(this.getString(), ((Button)view).getText().toString());
				_gti.getPlayer(((Button)view).getText().toString()).stealsBall();
				recordActivity();
				changePossession();
				resetFeatures();
			}
		};
		return stealFromListener;
	}
	
	//in new set of buttons, listener for un-forced turnovers
	public OnClickListener unforcedTOListener = new OnClickListener(){
		@Override
		public void onClick(View view) {
			setSlideOutButtonText(_gti.getPossession());
			turnOverType();
		}
	};
	
	//in toggle menu, selection of who the un-forced turnover was committed by
	public OnClickListener UTOByListener(String type){
		OnClickListener UTOByListener = new DoubleParamOnClickListener(0, type){
			@Override
			public void onClick(View view) {
				_gameLog.turnover(true, ((Button)view).getText().toString(), this.getString());
				_gti.getPlayer(((Button)view).getText().toString()).turnedOver();
				recordActivity();
				changePossession();
				resetFeatures();
			}		
		};
		return UTOByListener;
	}
	
	public OnClickListener teamTOListener = new OnClickListener(){
		@Override
		public void onClick(View view) {
			changePossession();
			recordActivity();
			resetFeatures();
		}
	};
	//brings up a alert dialog asking what type of un-forced turnover it is
	private void turnOverType(){
		final CharSequence[] options = {"Traveling", "Out of Bounds", "Double-Dribble", "Carrying" , "Offensive 3 Seconds"}; 
		Builder turnoverAlert = new Builder(this);
		turnoverAlert.setTitle("Please Select One");
		turnoverAlert.setItems(options, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {	
				toggleMenu(UTOByListener(options[which].toString()), "Turnover By");
			}
		});
		turnoverAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				resetFeatures();
			}
		});
		turnoverAlert.show();
	}

//[BBFOUL]:Foul Related--------------------------------------------------------------------------------------------------------------------------------------------
	
	public OnClickListener foulListener = new OnClickListener(){
		@Override
		public void onClick(View view) {
			_gti.setFoulVariable(true);
			stopClock();
			setFoulOptionListener();
		}
	};
	
	public OnClickListener personalFoulListener = new OnClickListener(){
		@Override
		public void onClick(View view) {
			setPersonalFoulListener();
		}
	};
	
	public OnClickListener technicalFoulListener = new OnClickListener(){
		@Override
		public void onClick(View view) {
			setSlideOutButtonText(!_gti.getPossession());
			toggleMenu(fouledByListener(0, "Technical Foul"), "Fouled By");
		}
	};
	
	public OnClickListener offensiveFoulListener = new OnClickListener(){
		@Override
		public void onClick(View view) {
			toggleMenu(fouledByListener(0, "Offensive Foul"), "Fouled By");
		}
	};
	
	public OnClickListener defensiveFoulListener = new OnClickListener(){
		@Override
		public void onClick(View view) {
			setSlideOutButtonText(!_gti.getPossession());
			toggleMenu(fouledByListener(0, "Defensive Foul"), "Fouled By");
		}
	};
	
	public OnClickListener intentionalFoulListener = new OnClickListener(){
		@Override
		public void onClick(View view) {
			setSlideOutButtonText(!_gti.getPossession());
			toggleMenu(fouledByListener(0, "Intentional Foul"), "Fouled By");
		}
	};
	
	public OnClickListener flagrantFoulListener = new OnClickListener(){
		@Override
		public void onClick(View view) {
			setFlagrantFoulListener();
		}
	};
	
	public OnClickListener flagrant1Listener = new OnClickListener(){
		@Override
		public void onClick(View view) {
			setSlideOutButtonText(!_gti.getPossession());
			toggleMenu(fouledByListener(0, "Technical Foul"), "Fouled By");
		}
	};
	
	public OnClickListener flagrant2Listener = new OnClickListener(){
		@Override
		public void onClick(View view) {
			setSlideOutButtonText(!_gti.getPossession());
			toggleMenu(fouledByListener(0, "Technical Foul"), "Fouled By");
		}
	};	
	

//[BBSF]:Shooting Foul Related Controls-----------------------------------------------------------------------------------------------------------------------------
/*	
 */
	
	public OnClickListener shootingFoulListener (int points){
		OnClickListener shootingFoulListener = new DoubleParamOnClickListener(points, null){
			@Override
			public void onClick(View view) {
				_gti.setFoulVariable(true);
				stopClock();
				setMadeMissListener(getValue());
				_gameClockView.setOnClickListener(null);
			}
		};
		return shootingFoulListener;
	}
	
	public OnClickListener fouledByListener(int value, String str){
		OnClickListener fouledByListener = new DoubleParamOnClickListener(value, str){
			@Override
			public void onClick(View view) {
				if(this.getString().equals("Technical Foul")){
					_gameLog.foulCommitted(((TextView)view).getText().toString(), 2);
					_gti.getPlayer(((TextView)view).getText().toString()).commitTechFoul();
				}
				else if(this.getString().equals("Flagrant Foul")){
					_gameLog.foulCommitted(((TextView)view).getText().toString(), 3);
					_gti.getPlayer(((TextView)view).getText().toString()).commitFlagFoul();
				}
				else{
					_gameLog.foulCommitted(((TextView)view).getText().toString(), 1);
					_gti.getPlayer(((TextView)view).getText().toString()).commitFoul();
				}
				recordActivity();
				_basketballCourt.setOnTouchListener(null);
				if(this.getString().equals("Offensive Foul")){
					changePossession();
					_root.toggleMenu();
					resetFeatures();
				}
				else if(this.getString().equals("Defensive Foul")){
					setSlideOutButtonText(_gti.getPossession());
					changeMenu(wasFouledListener(2, this.getString()), "Fouled");
				}
				else if(this.getString().equals("Intentional Foul")){
					_gti.willKeepPossession(true);
					setSlideOutButtonText(_gti.getPossession());
					changeMenu(wasFouledListener(2, this.getString()), "Fouled");
				}
				else if (this.getString().equals("Technical Foul") || this.getString().equals("Flagrant Foul")){
					_gti.willKeepPossession(true);
					setSlideOutButtonText(_gti.getPossession());
					changeMenu(wasFouledListener(2, this.getString()), "Free Throws By");
				}
				else{
					setFTOptionListener(this.getValue(), this.getString());
					_root.toggleMenu();
				}
				
			}
		};
		return fouledByListener;
	}
	
	public OnClickListener wasFouledListener(int value, String str){
		OnClickListener wasFouledListener = new DoubleParamOnClickListener(value, str){
			@Override
			public void onClick(View view){
				setFTOptionListener(this.getValue(), ((Button)view).getText().toString());
				setSlideOutButtonText(!_gti.getPossession());													//set Slide Out Buttons to opposing team because they may rebound a missed FT
				_root.toggleMenu();
			}
		};
		return wasFouledListener;
	}
	
	public OnClickListener FTMadeListener(int value, String str){
		OnClickListener FTMadeListener = new DoubleParamOnClickListener(value, str){
			@Override
			public void onClick(View view) {
				_gameLog.freeThrow(true, this.getString());				
				_gti.scoreChange(_gti.getPossession(), 1, this.getString());
				recordActivity();
				if(this.getValue() == 1){
					_basketballCourt.setOnTouchListener(courtInteraction);
					_gameClockView.setOnClickListener(timerClickListener);
					if(!_gti.keepPossession()){
						changePossession();
					}
					resetFeatures();
					disableMainSettings();
				}
				else{
					_option4Button.setOnClickListener(FTMadeListener(this.getValue()-1, this.getString()));
					_option5Button.setOnClickListener(FTMissListener(this.getValue()-1, this.getString()));					
				}
			}
			
		};
		return FTMadeListener;
	}
	
	public OnClickListener FTMissListener(int value, String str){
		OnClickListener FTMissListener = new DoubleParamOnClickListener(value, str){
			@Override
			public void onClick(View view) {				
				_gameLog.freeThrow(false, this.getString());
				_gti.getPlayer(this.getString()).missFreeThrow();
				recordActivity();
				view.setOnClickListener(FTMadeListener(this.getValue()-1, this.getString()));
				if(this.getValue() == 1){
					if(_gti.keepPossession()){
						resetFeatures();
						disableMainSettings();
					}
					else{
						addView((View)_p1Button.getParent(), "O-Rebound");
						_otherButton.setOnClickListener(offReboundListener);
						toggleMenu(reboundListener,"Rebound By:");
						startClock();
					}
				}
				else{
					_option4Button.setOnClickListener(FTMadeListener(this.getValue()-1, this.getString()));
					_option5Button.setOnClickListener(FTMissListener(this.getValue()-1, this.getString()));
				}
			}
		};
		return FTMissListener;
	}
	
//[BBBS]: Block Shots Listener---------------------------------------------------------------------------------------------------------------------------------------
		
	private OnClickListener blockListener = new OnClickListener(){
		@Override
		public void onClick(View v) {
			setSlideOutButtonText(!_gti.getPossession());
			toggleMenu(blockByListener, "Blocked By:");
		}
	};
	
	private OnClickListener blockByListener = new OnClickListener(){
		@Override
		public void onClick(View v) {
			_gti.getPlayer(((TextView)v).getText().toString()).blocksShot();			
			changeMenu(blockAgainstListener(((TextView)v).getText().toString()), "Blocked Against:");
			setSlideOutButtonText(_gti.getPossession());
		}
	};
	
	private OnClickListener blockAgainstListener(String name){
		OnClickListener blockAgainstListener = new DoubleParamOnClickListener(0, name){
			@Override
			public void onClick(View v) {
				_gti.getPlayer(((TextView)v).getText().toString()).missTwo();
				_gameLog.blocking(this.getString(), ((TextView)v).getText().toString());
				changePossession();
				recordActivity();
				resetFeatures();
			}
		};
		return blockAgainstListener;
	}
	

//[BBGCM]:Game Clock Manager------------------------------------------------------------------------------------------------------------------------------------
/* This section of code manages our game clock situation. From starting the clock, pausing and restarting.
 Very simple Android specific (maybe ineffective design). The game clock view will also be the button to initiate tipoff
 and the start of the game.*/
	
	//treat our game clock as a button that will start our game
	public OnClickListener startGameListener = new OnClickListener(){
		@Override
		public void onClick(View view) {
			_gameClock = new GameClock(PRO_TIME, _gameClockView);
			_gameClockView.setOnClickListener(timerClickListener);
			tipOff();
		}
	};
	
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
		possessionMarkerChange();
		resetFeatures();
	}
	
	private void stopClock(){
		_gameClock.stop();
		disableMainSettings();
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
		tipOffAlert.setMessage("Which team won tip-off?");
		tipOffAlert.setPositiveButton("Away", new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				_gti.setPossession(false);
			}
		});
		tipOffAlert.setNegativeButton("Home", new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				_gti.setPossession(true);
			}
		});
		tipOffAlert.show();	
	}

//[BBSTAT]:Statistical -------------------------------------------------------------------------------------------------------------------------------------	
/* Methods to update the score or change possession of the ball. This section is to manage some of the 
  important overall game statistics */
		
	//change possession
	private void changePossession(){
		_gti.changePossession();
		setSlideOutButtonText(_gti.getPossession());
		possessionMarkerChange();		
	}
	
	//changes the marker that denotes which team has possession
	private void possessionMarkerChange(){
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
	
	//record the plays of the game by sending string _gameLog to be stored in an ArrayList
	private void recordActivity(){
		_gameLog.recordActivity(_gameClockView.getText().toString());
		_awayScoreTextView.setText(_gti.getAwayScoreText());
		_homeScoreTextView.setText(_gti.getHomeScoreText());
	}	
	
	private void resetFeatures() {
		setSlideOutButtonText(_gti.getPossession());
		reactivateButton();	
		setMainSettings();
		_gameLog.setLogSize();
		_gti.setFoulVariable(false);
		_gti.willKeepPossession(false);
		_gameClockView.setOnClickListener(timerClickListener);
		if(_otherButton.getParent() != null){	
			removeView(_otherButton);			
		}
		if(_root.menuOpened()){		
			_root.toggleMenu();
		}
	}

//[BBOverride] Override Methods:---------------------------------------------------------------------------------------------------------------------------------------
/* Overriding some features of Activity to enhance the capabilities of these superclass methods. */
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			Builder alert = new Builder(this);
			alert.setTitle("Return to Team Selection");
			alert.setMessage("Are you sure?");
			alert.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {
					onBackPressed();
					finish();
				}
			});
			alert.setNegativeButton("No", new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {
										
				}
			});
			alert.show();
			return true;
		}
		else{
			return super.onKeyDown(keyCode, event);
		}
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		if(_gti.isGameStarted()){
			resetFeatures();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.basketball_menu, menu);
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
			intent = new Intent(getApplicationContext(), StatsActivity.class);			
			intent.putExtra(StaticFinalVars.GAME_INFO, _gti);			
			intent.putExtra(StaticFinalVars.GAME_LOG, _gameLog);
			intent.putExtra(StaticFinalVars.DISPLAY_TYPE, 0);
			startActivity(intent);		
			break;
			
		case R.id.gameLog:
			intent = new Intent(getApplicationContext(), StatsActivity.class);	
			intent.putExtra(StaticFinalVars.GAME_INFO, _gti);	
			intent.putExtra(StaticFinalVars.GAME_LOG, _gameLog);
			intent.putExtra(StaticFinalVars.DISPLAY_TYPE, 1);
			startActivity(intent);
			break;
		
		case R.id.editGame:
			break;
		
		case R.id.nextQuarter:
			String quarter = ((TextView)findViewById(R.id.quarterNumber)).getText().toString();
			if(zeroTime()){
				if(quarter.equals("1ST")){
					((TextView)findViewById(R.id.quarterNumber)).setText("2ND");
				}
				else if(quarter.equals("2ND")){
					((TextView)findViewById(R.id.quarterNumber)).setText("3RD");
				}
				else if(quarter.equals("3RD")){
					((TextView)findViewById(R.id.quarterNumber)).setText("4TH");
				}
				else if(quarter.equals("4TH")){
					((TextView)findViewById(R.id.quarterNumber)).setText("OT");
				}
				else{
					((TextView)findViewById(R.id.quarterNumber)).setText("k-OT");
				}
				_gameClock.restartTimer(PRO_TIME);
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == SUBSTITUTION_CODE){
			if(resultCode == Activity.RESULT_OK){
				_gti = (GameTime)data.getSerializableExtra(StaticFinalVars.SUB_INFO);
			}
		}
	}
}