package com.seniordesign.ultimatescorecard;


import java.util.ArrayList;
import java.util.List;

import com.seniordesign.ultimatescorecard.data.basketball.BasketballPlayer;
import com.seniordesign.ultimatescorecard.data.hockey.HockeyPlayer;
import com.seniordesign.ultimatescorecard.data.soccer.SoccerPlayer;
import com.seniordesign.ultimatescorecard.sqlite.basketball.*;
import com.seniordesign.ultimatescorecard.sqlite.football.FootballDatabaseHelper;
import com.seniordesign.ultimatescorecard.sqlite.helper.Games;
import com.seniordesign.ultimatescorecard.sqlite.helper.Players;
import com.seniordesign.ultimatescorecard.sqlite.helper.ShotChartCoords;
import com.seniordesign.ultimatescorecard.sqlite.helper.Teams;
import com.seniordesign.ultimatescorecard.sqlite.hockey.HockeyDatabaseHelper;
import com.seniordesign.ultimatescorecard.sqlite.soccer.SoccerDatabaseHelper;
import com.seniordesign.ultimatescorecard.view.StaticFinalVars;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

//this class refers to the main (opening screen)
public class MainActivity extends Activity{
	public Button _basketballButton, _footballButton, _hockeyButton, _soccerButton; 						//these are the sport selection buttons
	public Button _viewStatsButton, _optionsButton, _liveStatButtons; 										//these are set up for the other buttons
	//databases
	public BasketballDatabaseHelper _basketball_db;
	public FootballDatabaseHelper _football_db;
	public SoccerDatabaseHelper _soccer_db;
	public HockeyDatabaseHelper _hockey_db;

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
	
		_hockeyButton = (Button) findViewById(R.id.hockeyButton);										//referring to the baseball button
		_hockeyButton.setOnClickListener(hockeyButtonListener);												//setting a click listener for the button
		
		_soccerButton = (Button) findViewById(R.id.soccerButton);											//referring to the soccer button
		_soccerButton.setOnClickListener(soccerButtonListener);												//setting a click listener for the button
	
		//databases
		_basketball_db = new BasketballDatabaseHelper(getApplicationContext());
		_football_db = new FootballDatabaseHelper(getApplicationContext());
		_soccer_db = new SoccerDatabaseHelper(getApplicationContext());
		_hockey_db = new HockeyDatabaseHelper(getApplicationContext());

		//.onUpgrade will reset databases (i.e. erase all data stored in them)
		_basketball_db.onCreate(_basketball_db.getWritableDatabase());
		//_basketball_db.onUpgrade(_basketball_db.getWritableDatabase(),0,0);
		
		_football_db.onCreate(_football_db.getWritableDatabase());
		//_football_db.onUpgrade(_football_db.getWritableDatabase(),0,0);
		
		//_soccer_db.onCreate(_soccer_db.getWritableDatabase());
		_soccer_db.onUpgrade(_soccer_db.getWritableDatabase(),0,0);
		
		_hockey_db.onCreate(_hockey_db.getWritableDatabase());
		//_hockey_db.onUpgrade(_hockey_db.getWritableDatabase(),0,0);

/*
		//Test Teams and players
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
	*/	
/*		
		//Test Hockey Teams and players
		Teams spurs = new Teams("San Antonio Spurs", "SAS", "Gregg Popovich", "Hockey");
		Teams rockets = new Teams("Houston Rockets", "HOU", "Kevin McHale", "Hockey");
		Teams huskies = new Teams("UConn Huskies", "UCONN", "Kevin Ollie", "Hockey");

		
		long spurs_id = _hockey_db.createTeams(spurs);
		long rockets_id = _hockey_db.createTeams(rockets);
		long huskies_id = _hockey_db.createTeams(huskies);

		
		HockeyPlayer MG20 = new HockeyPlayer(spurs_id, "Manu Ginobili", 20);
		HockeyPlayer TD21 = new HockeyPlayer(spurs_id, "Tim Duncan", 21);
		HockeyPlayer KL2 = new HockeyPlayer(spurs_id, "Kawhi Leonard", 2);
		HockeyPlayer TP9 = new HockeyPlayer(spurs_id, "Tony Parker", 9);
		HockeyPlayer TS22 = new HockeyPlayer(spurs_id, "Tiago Splitter", 22);
		HockeyPlayer MB7 = new HockeyPlayer(spurs_id, "Marco Belinelli", 7);
		
		HockeyPlayer JH13 = new HockeyPlayer(rockets_id, "James Harden", 13);
		HockeyPlayer DH12 = new HockeyPlayer(rockets_id, "Dwight Howard", 12);
		HockeyPlayer JL7 = new HockeyPlayer(rockets_id, "Jeremy Lin", 7);
		HockeyPlayer CP25 = new HockeyPlayer(rockets_id, "Chandler Parsons", 25);
		HockeyPlayer TJ6 = new HockeyPlayer(rockets_id, "Terrence Jones", 6);
		HockeyPlayer AB0 = new HockeyPlayer(rockets_id, "Aaron Brooks", 0);
		
		HockeyPlayer PN0 = new HockeyPlayer(huskies_id, "Phillip Nolan", 0);
		HockeyPlayer DD2 = new HockeyPlayer(huskies_id, "Deandre Daniels", 2);
		HockeyPlayer TS3 = new HockeyPlayer(huskies_id, "Terrence Samuel", 3);
		HockeyPlayer NG5 = new HockeyPlayer(huskies_id, "Niels Giffey", 5);
		HockeyPlayer TO10 = new HockeyPlayer(huskies_id, "Tyler Olander", 10);
		HockeyPlayer RB11 = new HockeyPlayer(huskies_id, "Ryan Boatright", 11);
		HockeyPlayer KF12 = new HockeyPlayer(huskies_id, "Kentan Facey", 12);
		HockeyPlayer SN13 = new HockeyPlayer(huskies_id, "Shabazz Napier", 13);
		HockeyPlayer PL14 = new HockeyPlayer(huskies_id, "Pat Lenehan", 14);
		HockeyPlayer LK20 = new HockeyPlayer(huskies_id, "Lasan Kromah", 20);
		HockeyPlayer OC21 = new HockeyPlayer(huskies_id, "Omar Calhoun", 21);
		HockeyPlayer LT22 = new HockeyPlayer(huskies_id, "Leon Tolksdorf", 22);
		HockeyPlayer NA23 = new HockeyPlayer(huskies_id, "Nnamdi Amilo", 23);
		HockeyPlayer TW25 = new HockeyPlayer(huskies_id, "Tor Watts", 25);
		HockeyPlayer AB35 = new HockeyPlayer(huskies_id, "Amida Brimah", 35);

		
		long MG20_id = _hockey_db.createPlayers(MG20);
		long TD21_id = _hockey_db.createPlayers(TD21);
		long KL2_id = _hockey_db.createPlayers(KL2);
		long TP9_id = _hockey_db.createPlayers(TP9);
		long TS22_id = _hockey_db.createPlayers(TS22);
		long MB7_id = _hockey_db.createPlayers(MB7);
		
		long JH13_id = _hockey_db.createPlayers(JH13);
		long DH12_id = _hockey_db.createPlayers(DH12);
		long JL7_id = _hockey_db.createPlayers(JL7);
		long CP25_id = _hockey_db.createPlayers(CP25);
		long TJ6_id = _hockey_db.createPlayers(TJ6);
		long AB0_id = _hockey_db.createPlayers(AB0);
		
		long PN0_id = _hockey_db.createPlayers(PN0);
		long DD2_id = _hockey_db.createPlayers(DD2);
		long TS3_id = _hockey_db.createPlayers(TS3);
		long NG5_id = _hockey_db.createPlayers(NG5);
		long TO10_id = _hockey_db.createPlayers(TO10);
		long RB11_id = _hockey_db.createPlayers(RB11);
		long KF12_id = _hockey_db.createPlayers(KF12);
		long SN13_id = _hockey_db.createPlayers(SN13);
		long PL14_id = _hockey_db.createPlayers(PL14);
		long LK20_id = _hockey_db.createPlayers(LK20);
		long OC21_id = _hockey_db.createPlayers(OC21);
		long LT22_id = _hockey_db.createPlayers(LT22);
		long NA23_id = _hockey_db.createPlayers(NA23);
		long TW25_id = _hockey_db.createPlayers(TW25);
		long AB35_id = _hockey_db.createPlayers(AB35);
*/
		//Test Teams and players
		Teams spurs = new Teams("San Antonio Spurs", "SAS", "Gregg Popovich", "Soccer");
		Teams rockets = new Teams("Houston Rockets", "HOU", "Kevin McHale", "Soccer");
		Teams huskies = new Teams("UConn Huskies", "UCONN", "Kevin Ollie", "Soccer");

		
		long spurs_id = _soccer_db.createTeams(spurs);
		long rockets_id = _soccer_db.createTeams(rockets);
		long huskies_id = _soccer_db.createTeams(huskies);

		
		SoccerPlayer MG20 = new SoccerPlayer(spurs_id, "Manu Ginobili", 20);
		SoccerPlayer TD21 = new SoccerPlayer(spurs_id, "Tim Duncan", 21);
		SoccerPlayer KL2 = new SoccerPlayer(spurs_id, "Kawhi Leonard", 2);
		SoccerPlayer TP9 = new SoccerPlayer(spurs_id, "Tony Parker", 9);
		SoccerPlayer TS22 = new SoccerPlayer(spurs_id, "Tiago Splitter", 22);
		SoccerPlayer MB7 = new SoccerPlayer(spurs_id, "Marco Belinelli", 7);
		SoccerPlayer MG202 = new SoccerPlayer(spurs_id, "Manu Ginobili2", 20);
		SoccerPlayer TD212 = new SoccerPlayer(spurs_id, "Tim Duncan2", 21);
		SoccerPlayer KL22 = new SoccerPlayer(spurs_id, "Kawhi Leonard2", 2);
		SoccerPlayer TP92 = new SoccerPlayer(spurs_id, "Tony Parker2", 9);
		SoccerPlayer TS222 = new SoccerPlayer(spurs_id, "Tiago Splitter2", 22);
		SoccerPlayer MB72 = new SoccerPlayer(spurs_id, "Marco Belinelli2", 7);
		
		SoccerPlayer JH13 = new SoccerPlayer(rockets_id, "James Harden", 13);
		SoccerPlayer DH12 = new SoccerPlayer(rockets_id, "Dwight Howard", 12);
		SoccerPlayer JL7 = new SoccerPlayer(rockets_id, "Jeremy Lin", 7);
		SoccerPlayer CP25 = new SoccerPlayer(rockets_id, "Chandler Parsons", 25);
		SoccerPlayer TJ6 = new SoccerPlayer(rockets_id, "Terrence Jones", 6);
		SoccerPlayer AB0 = new SoccerPlayer(rockets_id, "Aaron Brooks", 0);
		
		SoccerPlayer PN0 = new SoccerPlayer(huskies_id, "Phillip Nolan", 0);
		SoccerPlayer DD2 = new SoccerPlayer(huskies_id, "Deandre Daniels", 2);
		SoccerPlayer TS3 = new SoccerPlayer(huskies_id, "Terrence Samuel", 3);
		SoccerPlayer NG5 = new SoccerPlayer(huskies_id, "Niels Giffey", 5);
		SoccerPlayer TO10 = new SoccerPlayer(huskies_id, "Tyler Olander", 10);
		SoccerPlayer RB11 = new SoccerPlayer(huskies_id, "Ryan Boatright", 11);
		SoccerPlayer KF12 = new SoccerPlayer(huskies_id, "Kentan Facey", 12);
		SoccerPlayer SN13 = new SoccerPlayer(huskies_id, "Shabazz Napier", 13);
		SoccerPlayer PL14 = new SoccerPlayer(huskies_id, "Pat Lenehan", 14);
		SoccerPlayer LK20 = new SoccerPlayer(huskies_id, "Lasan Kromah", 20);
		SoccerPlayer OC21 = new SoccerPlayer(huskies_id, "Omar Calhoun", 21);
		SoccerPlayer LT22 = new SoccerPlayer(huskies_id, "Leon Tolksdorf", 22);
		SoccerPlayer NA23 = new SoccerPlayer(huskies_id, "Nnamdi Amilo", 23);
		SoccerPlayer TW25 = new SoccerPlayer(huskies_id, "Tor Watts", 25);
		SoccerPlayer AB35 = new SoccerPlayer(huskies_id, "Amida Brimah", 35);

		
		long MG20_id = _soccer_db.createPlayers(MG20);
		long TD21_id = _soccer_db.createPlayers(TD21);
		long KL2_id = _soccer_db.createPlayers(KL2);
		long TP9_id = _soccer_db.createPlayers(TP9);
		long TS22_id = _soccer_db.createPlayers(TS22);
		long MB7_id = _soccer_db.createPlayers(MB7);
		
		long MG20_id2 = _soccer_db.createPlayers(MG202);
		long TD21_id2 = _soccer_db.createPlayers(TD212);
		long KL2_id2= _soccer_db.createPlayers(KL22);
		long TP9_id2 = _soccer_db.createPlayers(TP92);
		long TS22_id2 = _soccer_db.createPlayers(TS222);
		long MB7_id2 = _soccer_db.createPlayers(MB72);
		
		long JH13_id = _soccer_db.createPlayers(JH13);
		long DH12_id = _soccer_db.createPlayers(DH12);
		long JL7_id = _soccer_db.createPlayers(JL7);
		long CP25_id = _soccer_db.createPlayers(CP25);
		long TJ6_id = _soccer_db.createPlayers(TJ6);
		long AB0_id = _soccer_db.createPlayers(AB0);
		
		long PN0_id = _soccer_db.createPlayers(PN0);
		long DD2_id = _soccer_db.createPlayers(DD2);
		long TS3_id = _soccer_db.createPlayers(TS3);
		long NG5_id = _soccer_db.createPlayers(NG5);
		long TO10_id = _soccer_db.createPlayers(TO10);
		long RB11_id = _soccer_db.createPlayers(RB11);
		long KF12_id = _soccer_db.createPlayers(KF12);
		long SN13_id = _soccer_db.createPlayers(SN13);
		long PL14_id = _soccer_db.createPlayers(PL14);
		long LK20_id = _soccer_db.createPlayers(LK20);
		long OC21_id = _soccer_db.createPlayers(OC21);
		long LT22_id = _soccer_db.createPlayers(LT22);
		long NA23_id = _soccer_db.createPlayers(NA23);
		long TW25_id = _soccer_db.createPlayers(TW25);
		long AB35_id = _soccer_db.createPlayers(AB35);
	


/*	
		Log.d("GameStats count", "GameStats Count: " + _basketball_db.getAllGameStats().size());

		ArrayList<ShotChartCoords> shots = (ArrayList<ShotChartCoords>) _basketball_db.getAllShots();
		Log.d("Shot count", "Shot Count: " + shots.size());

		for(ShotChartCoords shot: shots){
			Log.d("Shot count", "Shot Count: " + shot.getshotid());
			Log.d("Shot count", "Shot Count: " + shot.getpid());
			Log.d("Shot count", "Shot Count: " + shot.getmade());
			Log.d("Shot count", "Shot Count: " + shot.getx());
			Log.d("Shot count", "Shot Count: " + shot.gety());

		}
*/
		
		
		
		//close database helper
		_basketball_db.close();
		_football_db.close();
		_soccer_db.close();
		_hockey_db.close();

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
	
	//click listener for hockey button
	public OnClickListener hockeyButtonListener = new OnClickListener(){
		@Override
		public void onClick(View view) {
			Intent intent = new Intent(getApplicationContext(), ChooseTeamActivity.class);					//create new intent (you have intentions to do something)	
			intent.putExtra(StaticFinalVars.SPORT_TYPE, "hockey");
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
