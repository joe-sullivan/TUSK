package com.seniordesign.ultimatescorecard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

//this class refers to the main (opening screen)
public class MainActivity extends Activity{
	BasketballActivity basketball = new BasketballActivity();
	public Button basketballButton, footballButton;
	
	//what the program should do when screen is created
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);									//default stuff
		setContentView(R.layout.activity_main);								//making the activity_main.xml page appear
		
		basketballButton = (Button) findViewById(R.id.basketballButton);	//referring to the basketball button
		basketballButton.setOnClickListener(basketballButtonListener);		//setting a click listener for the button
	
		footballButton = (Button) findViewById(R.id.footballButton);	//referring to the football button
		footballButton.setOnClickListener(footballButtonListener);		//setting a click listener for the button
	
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public OnClickListener basketballButtonListener = new OnClickListener(){
		@Override
		public void onClick(View view) {													//on click
			Intent intent = new Intent(getApplicationContext(), ChooseTeamActivity.class);	//create new intent
			startActivity(intent);															//execute the intent
		}
	};
	
	//setting up the click listener
	public OnClickListener footballButtonListener = new OnClickListener(){
		@Override
		public void onClick(View view) {
																		
		}
	};
}
