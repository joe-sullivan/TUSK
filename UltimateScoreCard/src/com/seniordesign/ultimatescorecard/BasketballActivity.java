package com.seniordesign.ultimatescorecard;

import com.seniordesign.ultimatescorecard.view.viewgroup.FlyOutContainer;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class BasketballActivity extends Activity{
	FlyOutContainer _root;
	TextView _homeTextView, _awayTextView;																			//all the different items on the screen
	TextView _homeScoreTextView, _awayScoreTextView;
	ImageView _basketballCourt, _basketballCourtMask;
	Bitmap _bitmap;
	Button _p1Button, _p2Button, _p3Button, _p4Button, _p5Button;
	Button _turnoverButton, _foulButton, _moreButton;
	Button _madeButton, _missButton, _shotCancelButton;															
	private boolean _possession = true;																				//tracking possession true is home, false is away
	private String _away, _home;																					//strings containing name of home and away team
	private GetTeamInformation _gti;																				//class for getting team information
	
	//what the program should do when this page is created
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		_root = (FlyOutContainer)this.getLayoutInflater().inflate(R.layout.activity_basketball, null);				//root is modified view with fly-out container
		_home = getIntent().getExtras().getString("home");															//get strings that we had sent from previous activity
		_away = getIntent().getExtras().getString("away"); 															
		_gti = new GetTeamInformation(_away, _home);																//create our team informations class
		setContentView(_root);
		
		_awayTextView = (TextView)findViewById(R.id.awayTextView);													//referencing the different views displayed via their id
		_awayTextView.setText(_gti.getAwayAbbr());																	//change text to denote home and away team
		_homeTextView = (TextView)findViewById(R.id.homeTextView);
		_homeTextView.setText(_gti.getHomeAbbr());
		
		_awayScoreTextView = (TextView)findViewById(R.id.awayScoreTextView);
		_homeScoreTextView = (TextView)findViewById(R.id.homeScoreTextView);
		
		_basketballCourt = (ImageView)findViewById(R.id.basketballCourt);
		_basketballCourt.setOnTouchListener(courtInteraction);														//setting the onTouch listener for our interactive court
		_basketballCourtMask = (ImageView)findViewById(R.id.basketballCourtMask);
		_bitmap = ((BitmapDrawable) _basketballCourtMask.getDrawable()).getBitmap();								//bitmap of our courtMask, needed for map to work
		
		_p1Button = (Button)findViewById(R.id.extendButton1);														//our slide out buttons
		_p2Button = (Button)findViewById(R.id.extendButton2);
		_p3Button = (Button)findViewById(R.id.extendButton3);
		_p4Button = (Button)findViewById(R.id.extendButton4);
		_p5Button = (Button)findViewById(R.id.extendButton5);
		setSlideOutButtonText(_possession);																			//change text on slide out menu based on possession
		
		_turnoverButton = (Button)findViewById(R.id.turnoverButton);												//more buttons and setting onClick listeners
		_turnoverButton.setOnClickListener(turnoverListener);
		_foulButton = (Button)findViewById(R.id.foulButton);
		_moreButton = (Button)findViewById(R.id.moreButton);
		_moreButton.setOnClickListener(moreButtonListener);
		
		_madeButton = (Button)findViewById(R.id.madeButton);														//again these are buttons, this set is hidden initially
		_missButton = (Button)findViewById(R.id.missButton);
	}
	//activate the slide out menu
	private void toggleMenu(OnClickListener listener, String title){
		changeMenu(listener, title);
		_root.toggleMenu();																							//tell 'root' (our distinct view) to bring out the menu
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
	
	//making the bottom row buttons appear or disappear
	private void buttonSwap (boolean whichSet){
		if(whichSet){																								//from original to prompting made or missed shot
			findViewById(R.id.buttonRow).setVisibility(View.INVISIBLE);
			findViewById(R.id.madeAndMissRow).setVisibility(View.VISIBLE);
		}
		else{																										//the opposite of the above to bring back original
			findViewById(R.id.madeAndMissRow).setVisibility(View.INVISIBLE);
			findViewById(R.id.buttonRow).setVisibility(View.VISIBLE);			
		}
	}
	
	//listener to control what happen when court is touched
	public OnTouchListener courtInteraction = new OnTouchListener(){
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if(event.getAction() == MotionEvent.ACTION_DOWN){														//need this if statement to keep method execution to one
				int pixel = _bitmap.getPixel((int)event.getX(), (int)event.getY());									//get where the user touches
				int redValue = Color.red(pixel);																	//get the color of that location and divide it into RGB values
				int blueValue = Color.blue(pixel);
				int greenValue = Color.green(pixel);
				if(redValue > blueValue && redValue > greenValue){													//red is the three point range
					setMadeMissListeners(3);
					buttonSwap(true);																				//we call our button swap method
				}
				else if (blueValue > redValue && blueValue > greenValue){											//blue and green are 2 points (blue mean in the paint)
					setMadeMissListeners(2);	
					buttonSwap(true);
				}
				else if (greenValue > blueValue && greenValue > redValue){
					setMadeMissListeners(2);
					buttonSwap(true);
				}
			}
			return false;
		}
	};
	
	//set what our made and miss buttons will set as their onClick listener
	private void setMadeMissListeners(int points){
		_basketballCourt.setOnTouchListener(shotCancelListener);
		if (points == 3){																							//three point shot was taken
			_madeButton.setOnClickListener(threePtsMadeListener);
			_missButton.setOnClickListener(threePtsMissListener);
		}																											//two point shot was taken
		else if(points == 2){
			_madeButton.setOnClickListener(twoPtsMadeListener);
			_missButton.setOnClickListener(twoPtsMissListener);
		}
		else {
			_madeButton.setOnClickListener(freeThrowMadeListener);													//set the miss button to do something onClick
			_missButton.setOnClickListener(freeThrowMissListener);
		}																											
	}
	//three pointer is attempted and made, this is what made button would do
	public OnClickListener threePtsMadeListener = new OnClickListener(){
		@Override
		public void onClick(View v) {
			buttonSwap(false);																						//bring back original set of buttons
			toggleMenu(threePtsMadePlayerSelectListener, "Scored by:");												//toggle menu to select which player scored
		}
	};
	
	//two pointer is attempted and made, this is what made button would do, similar to 'threePtsMadeListener'
	public OnClickListener twoPtsMadeListener = new OnClickListener(){
		@Override
		public void onClick(View v) {
			buttonSwap(false);
			toggleMenu(twoPtsMadePlayerSelectListener, "Scored by:");
		}
	};
	
	//three pointer is attempted and missed, this is what miss button would do
	public OnClickListener threePtsMissListener = new OnClickListener(){
		@Override
		public void onClick(View v) {
			buttonSwap(false);																						//bring back original set of buttons
			toggleMenu(threePtsMissPlayerSelectListener, "Missed by:");												
		}
	};
	
	//two pointer is attempted and missed, this is what miss button would do, similar to 'threePtsMissListener'
	public OnClickListener twoPtsMissListener = new OnClickListener(){
		@Override
		public void onClick(View v) {
			buttonSwap(false);
			toggleMenu(twoPtsMissPlayerSelectListener, "Missed by:");
		}
	};
	
	//free throws made (not implemented)
	public OnClickListener freeThrowMadeListener = new OnClickListener(){
		@Override
		public void onClick(View v) {
			buttonSwap(false);
		}
	};
	
	//free throws miss (not implemented)
	public OnClickListener freeThrowMissListener = new OnClickListener(){
		@Override
		public void onClick(View v) {
			buttonSwap(false);
		}
	};
	
	//in toggle menu, selecting which player score the three points
	public OnClickListener threePtsMadePlayerSelectListener = new OnClickListener(){
		@Override
		public void onClick(View view) {
			scoreChange(3, ((TextView)view).getText().toString());														//change the score and update player points
			changeMenu(assistListener, "Assisted by:");																	//change the fly-out menu to ask for assists
		}
	};
	
	//in toggle menu, selecting which player score the two points, similar to 'threePtsMadePlayerSelectListener'
	public OnClickListener twoPtsMadePlayerSelectListener = new OnClickListener(){
		@Override
		public void onClick(View view) {
			scoreChange(2, ((TextView)view).getText().toString());
			changeMenu(assistListener, "Assisted by:");
		}
	};
	
	//in toggle menu, selecting which player missed the three point shot
	public OnClickListener threePtsMissPlayerSelectListener = new OnClickListener(){
		@Override
		public void onClick(View view) {
			_gti.getPlayer(whichTeamInPossession(_possession), ((TextView)view).getText().toString()).missThree();		//update the player statisctics
			changePossession();																							//change possession
			changeMenu(reboundListener, "Rebound by:");																	//change the fly-out menu to ask for rebound
		}
	};
		
	//in toggle menu, selecting which player missed the two point shot, similar to 'threePtsMissPlayerSelectListener'
	public OnClickListener twoPtsMissPlayerSelectListener = new OnClickListener(){
		@Override
		public void onClick(View view) {
			_gti.getPlayer(whichTeamInPossession(_possession), ((TextView)view).getText().toString()).missTwo();
			changePossession();
			changeMenu(reboundListener, "Rebound by:");
		}
	};
	
	//in toggle menu, selecting which player grabbed the rebound
	public OnClickListener reboundListener = new OnClickListener(){
		@Override
		public void onClick(View view) {
			_gti.getPlayer(whichTeamInPossession(_possession), ((TextView)view).getText().toString()).grabRebound();	//increase player rebound total
			_basketballCourt.setOnTouchListener(courtInteraction);
			_root.toggleMenu();																							//close the menu
		}
	};
	
	//in toggle menu, selecting which player had the assist on the play
	public OnClickListener assistListener = new OnClickListener(){
		@Override
		public void onClick(View view) {
			_gti.getPlayer(whichTeamInPossession(_possession), ((TextView)view).getText().toString()).dishAssist();		//increase player assist count
			_basketballCourt.setOnTouchListener(courtInteraction);
			_root.toggleMenu();																							//close the menu
			changePossession();																							//scoring play process over, possession to opponent		
		}
	};
	
	//turnover listener (preliminary implementation - just changes possession)
	public OnClickListener turnoverListener = new OnClickListener(){
		@Override
		public void onClick(View view) {
			changePossession();																							//change possession
		}
	};
	
	//more button listener (preliminary implementation - just goes to box-score)
	public OnClickListener moreButtonListener = new OnClickListener(){
		@Override
		public void onClick(View view) {
			Intent intent = new Intent(getApplicationContext(), BoxscoreActivity.class);								//set box score activity as new intent
			intent.putExtra("teamInfo", _gti);																			//serialize gti for next activity
			startActivity(intent);																						//start the activity
		}
	};
	
	//if shot taken was incorrect
	public OnTouchListener shotCancelListener = new OnTouchListener(){
		@Override
		public boolean onTouch(View view, MotionEvent event) {
			if(event.getAction() == MotionEvent.ACTION_DOWN){
				buttonSwap(false);
				_basketballCourt.setOnTouchListener(courtInteraction);
			}
			return false;
		}
	};
	
	//updates the score and individual stats given the point value and player name
	private void scoreChange(int points, String playerName){
		int score = 0;
		if(_possession){
			score = Integer.parseInt(_homeScoreTextView.getText().toString()) + points;
			if(score < 10)
				_homeScoreTextView.setText("00" +score);
			else if (score < 100)
				_homeScoreTextView.setText("0" +score);
			else
				_homeScoreTextView.setText(""+ score);
		}
		else{
			score = Integer.parseInt(_awayScoreTextView.getText().toString()) + points;
			if(score < 10)
				_awayScoreTextView.setText("00" +score);
			else if (score < 100)
				_awayScoreTextView.setText("0" +score);
			else
				_awayScoreTextView.setText(""+ score);
		}
		if(points == 3){
			_gti.getPlayer(whichTeamInPossession(_possession), playerName).madeThree();
		}
		else if(points == 2){
			_gti.getPlayer(whichTeamInPossession(_possession), playerName).madeTwo();
		}
		else{
			_gti.getPlayer(whichTeamInPossession(_possession), playerName).madeFreeThrow();
		}
	}
	
	//returns a string of team name which possession of tha ball
	private String whichTeamInPossession(boolean possession){
		if(possession){
			return _home;
		}
		return _away;
	}
	
	//change possession
	private void changePossession(){
		_possession = !_possession;																					//flipping boolean possession
		setSlideOutButtonText(_possession);																			//changing the texts on slide out menu
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
	
	//again handling the back button being pressed, no more explaining needed
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
}
