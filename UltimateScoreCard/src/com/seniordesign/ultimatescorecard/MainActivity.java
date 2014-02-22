package com.seniordesign.ultimatescorecard;


import com.seniordesign.ultimatescorecard.data.BasketballPlayer;
import com.seniordesign.ultimatescorecard.sqlite.basketball.*;
import com.seniordesign.ultimatescorecard.sqlite.helper.Players;
import com.seniordesign.ultimatescorecard.sqlite.helper.Teams;
import com.seniordesign.ultimatescorecard.view.StaticFinalVars;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

//this class refers to the main (opening screen)
public class MainActivity extends Activity{
	public Button _basketballButton, _footballButton, _baseballButton, _soccerButton; 						//these are the sport selection buttons
	public Button _viewStatsButton, _optionsButton, _liveStatButtons; 										//these are set up for the other buttons
	//databases
	public BasketballDatabaseHelper _basketball_db;
	public Context context = this;
	

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
	
		//databases
		_basketball_db = new BasketballDatabaseHelper(getApplicationContext());
		_basketball_db.onUpgrade(_basketball_db.getWritableDatabase(), 0, 0);

		
		Teams spurs = new Teams("San Antonio Spurs", "SAS", "Gregg Popovich", "Basketball");
		Teams rockets = new Teams("Houston Rockets", "HOU", "Kevin McHale", "Basketball");
		Teams huskies = new Teams("UConn Huskies", "UCONN", "Kevin Ollie", "Basketball");

		
		long spurs_id = _basketball_db.createTeams(spurs);
		long rockets_id = _basketball_db.createTeams(rockets);
		long huskies_id = _basketball_db.createTeams(huskies);

		
		BasketballPlayer MG20 = new BasketballPlayer(spurs_id, "Manu Ginobili", 20);
		BasketballPlayer TD21 = new BasketballPlayer(spurs_id, "Tim Duncan", 21);
		BasketballPlayer KL2 = new BasketballPlayer(spurs_id, "Kawhi Leonard", 2);
		BasketballPlayer TP9 = new BasketballPlayer(spurs_id, "Tony Parker", 9);
		BasketballPlayer TS22 = new BasketballPlayer(spurs_id, "Tiago Splitter", 22);
		BasketballPlayer MB7 = new BasketballPlayer(spurs_id, "Marco Belinelli", 7);
		
		BasketballPlayer JH13 = new BasketballPlayer(rockets_id, "James Harden", 13);
		BasketballPlayer DH12 = new BasketballPlayer(rockets_id, "Dwight Howard", 12);
		BasketballPlayer JL7 = new BasketballPlayer(rockets_id, "Jeremy Lin", 7);
		BasketballPlayer CP25 = new BasketballPlayer(rockets_id, "Chandler Parsons", 25);
		BasketballPlayer TJ6 = new BasketballPlayer(rockets_id, "Terrence Jones", 6);
		BasketballPlayer AB0 = new BasketballPlayer(rockets_id, "Aaron Brooks", 0);
		
		BasketballPlayer PN0 = new BasketballPlayer(huskies_id, "Phillip Nolan", 0);
		BasketballPlayer DD2 = new BasketballPlayer(huskies_id, "Deandre Daniels", 2);
		BasketballPlayer TS3 = new BasketballPlayer(huskies_id, "Terrence Samuel", 3);
		BasketballPlayer NG5 = new BasketballPlayer(huskies_id, "Niels Giffey", 5);
		BasketballPlayer TO10 = new BasketballPlayer(huskies_id, "Tyler Olander", 10);
		BasketballPlayer RB11 = new BasketballPlayer(huskies_id, "Ryan Boatright", 11);
		BasketballPlayer KF12 = new BasketballPlayer(huskies_id, "Kentan Facey", 12);
		BasketballPlayer SN13 = new BasketballPlayer(huskies_id, "Shabazz Napier", 13);
		BasketballPlayer PL14 = new BasketballPlayer(huskies_id, "Pat Lenehan", 14);
		BasketballPlayer LK20 = new BasketballPlayer(huskies_id, "Lasan Kromah", 20);
		BasketballPlayer OC21 = new BasketballPlayer(huskies_id, "Omar Calhoun", 21);
		BasketballPlayer LT22 = new BasketballPlayer(huskies_id, "Leon Tolksdorf", 22);
		BasketballPlayer NA23 = new BasketballPlayer(huskies_id, "Nnamdi Amilo", 23);
		BasketballPlayer TW25 = new BasketballPlayer(huskies_id, "Tor Watts", 25);
		BasketballPlayer AB35 = new BasketballPlayer(huskies_id, "Amida Brimah", 35);

		
		long MG20_id = _basketball_db.createPlayers(MG20);
		long TD21_id = _basketball_db.createPlayers(TD21);
		long KL2_id = _basketball_db.createPlayers(KL2);
		long TP9_id = _basketball_db.createPlayers(TP9);
		long TS22_id = _basketball_db.createPlayers(TS22);
		long MB7_id = _basketball_db.createPlayers(MB7);
		
		long JH13_id = _basketball_db.createPlayers(JH13);
		long DH12_id = _basketball_db.createPlayers(DH12);
		long JL7_id = _basketball_db.createPlayers(JL7);
		long CP25_id = _basketball_db.createPlayers(CP25);
		long TJ6_id = _basketball_db.createPlayers(TJ6);
		long AB0_id = _basketball_db.createPlayers(AB0);
		
		long PN0_id = _basketball_db.createPlayers(PN0);
		long DD2_id = _basketball_db.createPlayers(DD2);
		long TS3_id = _basketball_db.createPlayers(TS3);
		long NG5_id = _basketball_db.createPlayers(NG5);
		long TO10_id = _basketball_db.createPlayers(TO10);
		long RB11_id = _basketball_db.createPlayers(RB11);
		long KF12_id = _basketball_db.createPlayers(KF12);
		long SN13_id = _basketball_db.createPlayers(SN13);
		long PL14_id = _basketball_db.createPlayers(PL14);
		long LK20_id = _basketball_db.createPlayers(LK20);
		long OC21_id = _basketball_db.createPlayers(OC21);
		long LT22_id = _basketball_db.createPlayers(LT22);
		long NA23_id = _basketball_db.createPlayers(NA23);
		long TW25_id = _basketball_db.createPlayers(TW25);
		long AB35_id = _basketball_db.createPlayers(AB35);
		
		_basketball_db.close();
	}
		
	// click listener for basketball button
	public OnClickListener basketballButtonListener = new OnClickListener(){
		@Override
		public void onClick(View view) {																	//on click
			Intent intent = new Intent(getApplicationContext(), ChooseTeamActivity.class);					//create new intent (you have intentions to do something)
			intent.putExtra(StaticFinalVars.SPORT_TYPE, "basketball");
			startActivity(intent);																			//execute the intent
		}
	};
	
	//click listener for football button
	public OnClickListener footballButtonListener = new OnClickListener(){
		@Override
		public void onClick(View view) {
			Intent intent = new Intent(getApplicationContext(), ChooseTeamActivity.class);					//create new intent (you have intentions to do something)
			intent.putExtra(StaticFinalVars.SPORT_TYPE, "football");
			startActivity(intent);	
		}
	};
	
	//click listener for baseball button
	public OnClickListener baseballButtonListener = new OnClickListener(){
		@Override
		public void onClick(View view) {
			Intent intent = new Intent(getApplicationContext(), ChooseTeamActivity.class);					//create new intent (you have intentions to do something)	
			intent.putExtra(StaticFinalVars.SPORT_TYPE, "baseball");
			startActivity(intent);														
		}
	};
	
	//click listener for soccer button
	public OnClickListener soccerButtonListener = new OnClickListener(){
		@Override
		public void onClick(View view) {
			Intent intent = new Intent(getApplicationContext(), ChooseTeamActivity.class);					//create new intent (you have intentions to do something)
			intent.putExtra(StaticFinalVars.SPORT_TYPE, "soccer");
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
