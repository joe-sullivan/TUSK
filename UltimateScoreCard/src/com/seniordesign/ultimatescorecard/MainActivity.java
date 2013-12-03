package com.seniordesign.ultimatescorecard;


import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

//this class refers to the main (opening screen)
public class MainActivity extends Activity{
	//BasketballActivity _basketball = new BasketballActivity();
	public Button _basketballButton, _footballButton, _baseballButton, _soccerButton; 						//these are the sport selection buttons
	public Button _viewStatsButton, _optionsButton, _liveStatButtons; 										//these are set up for the other buttons
	
	//what the program should do when screen is created
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);																	//default stuff
		setContentView(R.layout.activity_main);																//making the activity_main.xml page appear
		
		_basketballButton = (Button) findViewById(R.id.basketballButton);									//referring to the basketball button
		_basketballButton.setOnClickListener(basketballButtonListener);										//setting a click listener for the button
		
		_footballButton = (Button) findViewById(R.id.footballButton);										//referring to the football button
		_footballButton.setOnClickListener(footballButtonListener);											//setting a click listener for the button
	
		_baseballButton = (Button) findViewById(R.id.baseballButton);										//referring to the baseball button
		_baseballButton.setOnClickListener(baseballButtonListener);											//setting a click listener for the button
		
		_soccerButton = (Button) findViewById(R.id.soccerButton);											//referring to the soccer button
		_soccerButton.setOnClickListener(soccerButtonListener);												//setting a click listener for the button
	}
		
	// click listener for basketball button
	public OnClickListener basketballButtonListener = new OnClickListener(){
		@Override
		public void onClick(View view) {																	//on click
			Intent intent = new Intent(getApplicationContext(), ChooseTeamActivity.class);					//create new intent (you have intentions to do something)
			intent.putExtra("SPORT", "basketball");
			startActivity(intent);																			//execute the intent
		}
	};
	
	//click listener for football button
	public OnClickListener footballButtonListener = new OnClickListener(){
		@Override
		public void onClick(View view) {
			Intent intent = new Intent(getApplicationContext(), ChooseTeamActivity.class);					//create new intent (you have intentions to do something)
			intent.putExtra("SPORT", "football");
			startActivity(intent);	
		}
	};
	
	//click listener for baseball button
	public OnClickListener baseballButtonListener = new OnClickListener(){
		@Override
		public void onClick(View view) {
			Intent intent = new Intent(getApplicationContext(), ChooseTeamActivity.class);					//create new intent (you have intentions to do something)	
			intent.putExtra("SPORT", "baseball");
			startActivity(intent);														
		}
	};
	
	//click listener for soccer button
	public OnClickListener soccerButtonListener = new OnClickListener(){
		@Override
		public void onClick(View view) {
			Intent intent = new Intent(getApplicationContext(), ChooseTeamActivity.class);					//create new intent (you have intentions to do something)
			intent.putExtra("SPORT", "soccer");
			startActivity(intent);																
		}
	};
	
	//if you press the back button in the main screen prompt a message box asking to comfirm the action
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){																//if key pressed is the back key
			Builder alert = new Builder(this);																//creating the alert message
			alert.setTitle("Exit this Application?");
			alert.setMessage("Are you sure you want to exit?");
			
			alert.setPositiveButton("Yes", new DialogInterface.OnClickListener(){							//give it the YES button
				@Override
				public void onClick(DialogInterface dialog, int which) {
					onBackPressed();												
					System.exit(0);																			//exit the program as a whole
				}
			});
			alert.setNegativeButton("No", new DialogInterface.OnClickListener(){							//give the message box a NO button
				@Override
				public void onClick(DialogInterface dialog, int which) {	
					//do nothing, just close message box					
				}
			});
			alert.show();																					//make the alert message box show up
			return true;
		}
		else{
			return super.onKeyDown(keyCode, event);
		}
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}
	
	
}
