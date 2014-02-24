package com.seniordesign.ultimatescorecard.data.soccer;

import com.seniordesign.ultimatescorecard.R;
import com.seniordesign.ultimatescorecard.R.id;
import com.seniordesign.ultimatescorecard.R.layout;
import com.seniordesign.ultimatescorecard.view.StaticFinalVars;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SoccerActivity extends Activity{
	public static final int SUBSTITUTION_CODE = 1;
	
	RelativeLayout _homeLayout, _awayLayout;
	TextView _homeTextView, _awayTextView, _gameClockView, _quarterNumberView;											//all the different items on the screen
	TextView _homeScoreTextView, _awayScoreTextView;
	ImageView _soccerField;
	Bitmap _bitmap;
	Button _p1Button, _p2Button, _p3Button, _p4Button, _p5Button, _otherButton, _otherButton2;
	Button _option1Button, _option2Button, _option3Button, _option4Button, _option5Button;

	private SoccerGameTime _gti;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		_gti = (SoccerGameTime) getIntent().getSerializableExtra(StaticFinalVars.GAME_INFO);
		setContentView(getLayoutInflater().inflate(R.layout.activity_soccer, null));
		
		_awayTextView = (TextView)findViewById(R.id.awayTextView);									//change text to denote home and away team
		_homeTextView = (TextView)findViewById(R.id.homeTextView);
		
		Typeface quartz = Typeface.createFromAsset(getAssets(), "fonts/quartz.ttf");				//changing the font style to our own
		_awayScoreTextView = (TextView)findViewById(R.id.awayScoreTextView);						//our personal font type is stored in the assets folder
		_awayScoreTextView.setTypeface(quartz);
		_homeScoreTextView = (TextView)findViewById(R.id.homeScoreTextView);
		_homeScoreTextView.setTypeface(quartz);
	
		_quarterNumberView = (TextView) findViewById(R.id.quarterNumber);
		
		_gameClockView = (TextView) findViewById(R.id.gameClock);									//it will also serve as the button that will start the recording
		_gameClockView.setTypeface(quartz);
		
		_soccerField = (ImageView)findViewById(R.id.soccerfield);
		
		_homeLayout = (RelativeLayout)findViewById(R.id.homeShotIcons);
		_awayLayout = (RelativeLayout)findViewById(R.id.awayShotIcons);
		
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
	}
	
	private void mainButtonSet(){
		buttonSwap(false);
		setTextAndListener(_option4Button, null, "Shot");
		setTextAndListener(_option5Button, null, "Penalty");
	}
	
	private void shotButtonSet(){
		buttonSwap(true);
		setTextAndListener(_option1Button, null, "Saved");
		setTextAndListener(_option2Button, null, "Goal");
		setTextAndListener(_option3Button, null, "Missed");
	}
	
	private void kickingButtonSet(){
		buttonSwap(false);
		setTextAndListener(_option4Button, null, "Free Kick");
		setTextAndListener(_option5Button, null, "Penalty Kick");
	}
	
	private void assistButtonSet(){
		buttonSwap(false);
		setTextAndListener(_option1Button, null, "Assist?");
		setTextAndListener(_option2Button, null, "Yes");
		setTextAndListener(_option3Button, null, "No");
	}
	
	private void freeKickButtonSet(){
		buttonSwap(false);
		setTextAndListener(_option1Button, null, "Shot on Goal?");
		setTextAndListener(_option2Button, null, "Yes");
		setTextAndListener(_option3Button, null, "No");
	}
	
	private void penaltyCardsButtonSet(){
		buttonSwap(false);
		setTextAndListener(_option4Button, null, "Yellow Card");
		setTextAndListener(_option5Button, null, "Red Card");
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
	
}
