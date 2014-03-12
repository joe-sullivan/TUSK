package com.seniordesign.ultimatescorecard;


import com.seniordesign.ultimatescorecard.data.basketball.BasketballPlayer;
import com.seniordesign.ultimatescorecard.data.football.FootballPlayer;
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
import com.seniordesign.ultimatescorecard.stats.ViewStatsActivity;
import com.seniordesign.ultimatescorecard.view.StaticFinalVars;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
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
		_basketballButton.setBackgroundResource(R.drawable.view_style_plain_short);
		_basketballButton.setOnClickListener(basketballButtonListener);										//setting a click listener for the button
		_basketballButton.setOnTouchListener(shortButtonTouchListener);
		
		_footballButton = (Button) findViewById(R.id.footballButton);										//referring to the football button
		_footballButton.setBackgroundResource(R.drawable.view_style_plain_short);
		_footballButton.setOnClickListener(footballButtonListener);											//setting a click listener for the button
		_footballButton.setOnTouchListener(shortButtonTouchListener);
		
		_hockeyButton = (Button) findViewById(R.id.hockeyButton);											//referring to the baseball button
		_hockeyButton.setBackgroundResource(R.drawable.view_style_plain_short);
		_hockeyButton.setOnClickListener(hockeyButtonListener);												//setting a click listener for the button
		_hockeyButton.setOnTouchListener(shortButtonTouchListener);
		
		_soccerButton = (Button) findViewById(R.id.soccerButton);											//referring to the soccer button
		_soccerButton.setBackgroundResource(R.drawable.view_style_plain_short);
		_soccerButton.setOnClickListener(soccerButtonListener);												//setting a click listener for the button
		_soccerButton.setOnTouchListener(shortButtonTouchListener);
		
		_viewStatsButton = (Button) findViewById(R.id.viewStatisticButton);
		_viewStatsButton.setBackgroundResource(R.drawable.view_style_plain_long);
		_viewStatsButton.setOnClickListener(viewStatsListener);
		_viewStatsButton.setOnTouchListener(longButtonTouchListener);
		
		_optionsButton = (Button) findViewById(R.id.optionButton);
		_optionsButton.setBackgroundResource(R.drawable.view_style_plain_long);
		_optionsButton.setOnClickListener(optionListener);
		_optionsButton.setOnTouchListener(longButtonTouchListener);
		
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
		
		_soccer_db.onCreate(_soccer_db.getWritableDatabase());
		//_soccer_db.onUpgrade(_soccer_db.getWritableDatabase(),0,0);
		
		_hockey_db.onCreate(_hockey_db.getWritableDatabase());
		//_hockey_db.onUpgrade(_hockey_db.getWritableDatabase(),0,0);
/*
		//Test Basketball Teams and players
		Teams spurs = new Teams("San Antonio Spurs", "SAS", "Gregg Popovich", "Basketball");
		Teams rockets = new Teams("Houston Rockets", "HOU", "Kevin McHale", "Basketball");
		Teams mavericks = new Teams("Dallas Mavericks", "DAL", "Rick Carlisle", "Basketball");
		Teams grizzlies = new Teams("Memphis Grizzlies", "MEM", "David Joerger", "Basketball");
		Teams pelicans = new Teams("New Orleans Pelicans", "NO", "Monte Williams", "Basketball");
		Teams huskies = new Teams("UConn Huskies", "UCONN", "Kevin Ollie", "Basketball");
	
		long spurs_id = _basketball_db.createTeams(spurs);
		long rockets_id = _basketball_db.createTeams(rockets);
		long mavericks_id = _basketball_db.createTeams(mavericks);
		long grizzlies_id = _basketball_db.createTeams(grizzlies);
		long pelicans_id = _basketball_db.createTeams(pelicans);
		long huskies_id = _basketball_db.createTeams(huskies);
		
		BasketballPlayer TP9 = new BasketballPlayer(spurs_id, "Tony Parker", 9);
		BasketballPlayer DG4 = new BasketballPlayer(spurs_id, "Danny Green", 4);		
		BasketballPlayer KL2 = new BasketballPlayer(spurs_id, "Kawhi Leonard", 2);
		BasketballPlayer TD21 = new BasketballPlayer(spurs_id, "Tim Duncan", 21);
		BasketballPlayer TS22 = new BasketballPlayer(spurs_id, "Tiago Splitter", 22);
		BasketballPlayer PM8 = new BasketballPlayer(spurs_id, "Patty Mills", 8);
		BasketballPlayer MG20 = new BasketballPlayer(spurs_id, "Manu Ginobili", 20);
		BasketballPlayer AD23 = new BasketballPlayer(spurs_id, "Austin Daye", 23);
		BasketballPlayer MB15 = new BasketballPlayer(spurs_id, "Matt Bonner", 15);
		BasketballPlayer BD33 = new BasketballPlayer(spurs_id, "Boris Diaw", 33);
		BasketballPlayer CJ5 = new BasketballPlayer(spurs_id, "Cory Joesph", 5);
		BasketballPlayer MB3 = new BasketballPlayer(spurs_id, "Marco Belinelli", 3);
		BasketballPlayer AB16 = new BasketballPlayer(spurs_id, "Aron Baynes", 16);
				
		BasketballPlayer PB2 = new BasketballPlayer(rockets_id, "Patrick Beverly", 2);
		BasketballPlayer JH13 = new BasketballPlayer(rockets_id, "James Harden", 13);
		BasketballPlayer CP25 = new BasketballPlayer(rockets_id, "Chandler Parsons", 25);
		BasketballPlayer TJ6 = new BasketballPlayer(rockets_id, "Terrence Jones", 6);
		BasketballPlayer DW12 = new BasketballPlayer(rockets_id, "Dwight Howard", 12);
		BasketballPlayer JL7 = new BasketballPlayer(rockets_id, "Jeremy Lin", 7);
		BasketballPlayer FG32 = new BasketballPlayer(rockets_id, "Francisco Garcia", 32);
		BasketballPlayer OC18 = new BasketballPlayer(rockets_id, "Omri Casspi", 18);
		BasketballPlayer DM20 = new BasketballPlayer(rockets_id, "Donatas Motigjunas", 20);
		BasketballPlayer OA3 = new BasketballPlayer(rockets_id, "Omer Asik", 3);
		BasketballPlayer AB0 = new BasketballPlayer(rockets_id, "Aaron Brooks", 0);
		BasketballPlayer JH5 = new BasketballPlayer(rockets_id, "Jordan Hamilton", 5);
		BasketballPlayer GS4 = new BasketballPlayer(rockets_id, "Greg Smith", 4);
		
		BasketballPlayer JC8 = new BasketballPlayer(mavericks_id, "Jose Calderon", 8);
		BasketballPlayer ME11 = new BasketballPlayer(mavericks_id, "Monte Ellis", 11);
		BasketballPlayer SM0 = new BasketballPlayer(mavericks_id, "Shawn Marion", 0);
		BasketballPlayer DN41 = new BasketballPlayer(mavericks_id, "Dirk Nowitzki", 41);
		BasketballPlayer SD1 = new BasketballPlayer(mavericks_id, "Samuel Dalembert", 1);
		BasketballPlayer DH20 = new BasketballPlayer(mavericks_id, "Devin Harris", 20);
		BasketballPlayer WE21 = new BasketballPlayer(mavericks_id, "Wayne Ellington", 21);
		BasketballPlayer VC25 = new BasketballPlayer(mavericks_id, "Vince Carter", 25);
		BasketballPlayer BW34 = new BasketballPlayer(mavericks_id, "Brandan Wright", 34);
		BasketballPlayer DB45 = new BasketballPlayer(mavericks_id, "DeJuan Blair", 45);
		BasketballPlayer SL3 = new BasketballPlayer(mavericks_id, "Shane Larkin", 3);
		BasketballPlayer JC9 = new BasketballPlayer(mavericks_id, "Jae Crowder", 9);
		BasketballPlayer BJ5 = new BasketballPlayer(mavericks_id, "Bernard James", 5);
				
		BasketballPlayer MC11 = new BasketballPlayer(grizzlies_id, "Mike Conley", 11);	
		BasketballPlayer CL5 = new BasketballPlayer(grizzlies_id, "Courtney Lee", 5);	
		BasketballPlayer TP21 = new BasketballPlayer(grizzlies_id, "Tayshaun Prince", 21);	
		BasketballPlayer ZR50 = new BasketballPlayer(grizzlies_id, "Zach Randolph", 50);	
		BasketballPlayer MG33 = new BasketballPlayer(grizzlies_id, "Mark Gasol", 33);	
		BasketballPlayer BU19 = new BasketballPlayer(grizzlies_id, "Beno Udrih", 19);	
		BasketballPlayer MM13 = new BasketballPlayer(grizzlies_id, "Mike Miller", 13);	
		BasketballPlayer JJ3 = new BasketballPlayer(grizzlies_id, "James Johnson", 3);	
		BasketballPlayer ED32 = new BasketballPlayer(grizzlies_id, "Ed Davis", 32);	
		BasketballPlayer KK41 = new BasketballPlayer(grizzlies_id, "Kosta Koufos", 41);	
		BasketballPlayer NC12 = new BasketballPlayer(grizzlies_id, "Nick Calathes", 12);	
		BasketballPlayer TA9 = new BasketballPlayer(grizzlies_id, "Tony Allen", 9);	
		BasketballPlayer QP20 = new BasketballPlayer(grizzlies_id, "Quincy Pondexter", 20);
		
		BasketballPlayer BR22 = new BasketballPlayer(pelicans_id, "Brian Roberts", 22);
		BasketballPlayer EG10 = new BasketballPlayer(pelicans_id, "Eric Gordan", 10);
		BasketballPlayer AA0 = new BasketballPlayer(pelicans_id, "Al-Farouq Aminu", 0);
		BasketballPlayer ADa23 = new BasketballPlayer(pelicans_id, "Anthony Davis", 23);
		BasketballPlayer AA42 = new BasketballPlayer(pelicans_id, "Alexis Ajinca", 42);
		BasketballPlayer AR25 = new BasketballPlayer(pelicans_id, "Austin Rivers", 25);
		BasketballPlayer AM3 = new BasketballPlayer(pelicans_id, "Anthony Marrow", 3);
		BasketballPlayer TE1 = new BasketballPlayer(pelicans_id, "Tyreke Evans", 1);
		BasketballPlayer RA33 = new BasketballPlayer(pelicans_id, "Ryan Anderson", 33);
		BasketballPlayer GS34 = new BasketballPlayer(pelicans_id, "Greg Stiemsma", 34);
		BasketballPlayer JH11 = new BasketballPlayer(pelicans_id, "Jrue Holliday", 11);
		BasketballPlayer JW5 = new BasketballPlayer(pelicans_id, "Jeff Withey", 5);
		BasketballPlayer JS14 = new BasketballPlayer(pelicans_id, "Jason Smith", 14);
		
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

		long TP9_id = _basketball_db.createPlayers(TP9);
		long DG4_id = _basketball_db.createPlayers(DG4);
		long KL2_id = _basketball_db.createPlayers(KL2);
		long TD21_id = _basketball_db.createPlayers(TD21);
		long TS22_id = _basketball_db.createPlayers(TS22);
		long PM8_id = _basketball_db.createPlayers(PM8);
		long MG20_id = _basketball_db.createPlayers(MG20);
		long AD23_id = _basketball_db.createPlayers(AD23);
		long MB15_id = _basketball_db.createPlayers(MB15);
		long BD33_id = _basketball_db.createPlayers(BD33);
		long CJ5_id = _basketball_db.createPlayers(CJ5);
		long MB3_id = _basketball_db.createPlayers(MB3);
		long AB16_id = _basketball_db.createPlayers(AB16);
		
		long PB2_id = _basketball_db.createPlayers(PB2);
		long JH13_id = _basketball_db.createPlayers(JH13);
		long CP25_id = _basketball_db.createPlayers(CP25);
		long TJ6_id = _basketball_db.createPlayers(TJ6);
		long DW12_id = _basketball_db.createPlayers(DW12);
		long JL7_id = _basketball_db.createPlayers(JL7);
		long FG32_id = _basketball_db.createPlayers(FG32);
		long OC18_id = _basketball_db.createPlayers(OC18);
		long DM20_id = _basketball_db.createPlayers(DM20);
		long OA3_id = _basketball_db.createPlayers(OA3);
		long AB0_id = _basketball_db.createPlayers(AB0);
		long JH5_id = _basketball_db.createPlayers(JH5);
		long GS4_id = _basketball_db.createPlayers(GS4);
		
		long JC8_id = _basketball_db.createPlayers(JC8);
		long ME11_id = _basketball_db.createPlayers(ME11);
		long SM0_id = _basketball_db.createPlayers(SM0);
		long DN41_id = _basketball_db.createPlayers(DN41);
		long SD1_id = _basketball_db.createPlayers(SD1);
		long DH20_id = _basketball_db.createPlayers(DH20);
		long WE21_id = _basketball_db.createPlayers(WE21);
		long VC25_id = _basketball_db.createPlayers(VC25);
		long BW34_id = _basketball_db.createPlayers(BW34);
		long DB45_id = _basketball_db.createPlayers(DB45);
		long SL3_id = _basketball_db.createPlayers(SL3);
		long JC9_id = _basketball_db.createPlayers(JC9);
		long BJ5_id = _basketball_db.createPlayers(BJ5);
		
		long MC11_id = _basketball_db.createPlayers(MC11);
		long CL5_id = _basketball_db.createPlayers(CL5);
		long TP21_id = _basketball_db.createPlayers(TP21);
		long ZR50_id = _basketball_db.createPlayers(ZR50);
		long MG33_id = _basketball_db.createPlayers(MG33);
		long BU19_id = _basketball_db.createPlayers(BU19);
		long MM13_id = _basketball_db.createPlayers(MM13);
		long JJ3_id = _basketball_db.createPlayers(JJ3);
		long ED32_id = _basketball_db.createPlayers(ED32);
		long KK41_id = _basketball_db.createPlayers(KK41);
		long NC12_id = _basketball_db.createPlayers(NC12);
		long TA9_id = _basketball_db.createPlayers(TA9);
		long QP20_id = _basketball_db.createPlayers(QP20);
		
		long BR22_id = _basketball_db.createPlayers(BR22);
		long EG10_id = _basketball_db.createPlayers(EG10);
		long AA0_id = _basketball_db.createPlayers(AA0);
		long ADa23_id = _basketball_db.createPlayers(ADa23);
		long AA42_id = _basketball_db.createPlayers(AA42);
		long AR25_id = _basketball_db.createPlayers(AR25);
		long AM3_id = _basketball_db.createPlayers(AM3);
		long TE1_id = _basketball_db.createPlayers(TE1);
		long RA33_id = _basketball_db.createPlayers(RA33);
		long GS34_id = _basketball_db.createPlayers(GS34);
		long JH11_id = _basketball_db.createPlayers(JH11);
		long JW5_id = _basketball_db.createPlayers(JW5);
		long JS14_id = _basketball_db.createPlayers(JS14);
	
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
		//Test Football Teams and players
		Teams patriots = new Teams("New England Patriots", "NWE", "Bill Belichick", "Football");
		Teams jets = new Teams("New York Jets", "NYJ", "Rex Ryan", "Football");
		Teams bears = new Teams("Chicago Bears", "CHI", "Marc Trestman", "Football");
		Teams cowboys = new Teams("Dallas Cowboys", "DAL", "Jason Garrett", "Football");
		Teams saints = new Teams("New Orleans Saints", "NO", "Sean Payton", "Football");
	
		long patriots_id = _football_db.createTeams(patriots);
		long jets_id = _football_db.createTeams(jets);
		long bears_id = _football_db.createTeams(bears);
		long cowboys_id = _football_db.createTeams(cowboys);
		long saints_id = _football_db.createTeams(saints);
		
		FootballPlayer TB12 = new FootballPlayer(patriots_id, "Tom Brady", 12);
		FootballPlayer LB29 = new FootballPlayer(patriots_id, "LaGarette Blount", 29);		
		FootballPlayer SR22 = new FootballPlayer(patriots_id, "Stevan Ridley", 22);
		FootballPlayer SV34 = new FootballPlayer(patriots_id, "Shane Vereen", 34);
		FootballPlayer JE11 = new FootballPlayer(patriots_id, "Julian Edelman", 11);
		FootballPlayer AD17 = new FootballPlayer(patriots_id, "Aaron Dobson", 17);
		FootballPlayer DA80 = new FootballPlayer(patriots_id, "Danny Amendola", 80);
		FootballPlayer KT85 = new FootballPlayer(patriots_id, "Kenbrell Thompkins", 85);
		FootballPlayer RG87 = new FootballPlayer(patriots_id, "Rob Gronkowski", 87);
		FootballPlayer MH47 = new FootballPlayer(patriots_id, "Michael Hoomanawanui", 47);
		FootballPlayer VW75 = new FootballPlayer(patriots_id, "Vince Wilfork", 75);
		FootballPlayer JM51 = new FootballPlayer(patriots_id, "Jerod Mayo", 51);
		FootballPlayer DM32 = new FootballPlayer(patriots_id, "Devin McCourty", 32);
				
		FootballPlayer GS7 = new FootballPlayer(jets_id, "Geno Smith", 7);
		FootballPlayer CI33 = new FootballPlayer(jets_id, "Chris Ivory", 33);
		FootballPlayer BP29 = new FootballPlayer(jets_id, "Bilal Powell", 29);
		FootballPlayer DR24 = new FootballPlayer(jets_id, "Darius Reynaud", 24);
		FootballPlayer JK11 = new FootballPlayer(jets_id, "Jeremy Kerley", 11);
		FootballPlayer SH10 = new FootballPlayer(jets_id, "Santonio Holmes", 10);
		FootballPlayer GS17 = new FootballPlayer(jets_id, "Greg Salas", 17);
		FootballPlayer DN86 = new FootballPlayer(jets_id, "David Nelson", 86);
		FootballPlayer ZS82 = new FootballPlayer(jets_id, "Zach Sudfeld", 82);
		FootballPlayer JC87 = new FootballPlayer(jets_id, "Jeff Cumberland", 87);
		FootballPlayer SR91 = new FootballPlayer(jets_id, "Sheldon Richardson", 91);
		FootballPlayer CP97 = new FootballPlayer(jets_id, "Calvin Pace", 97);
		FootballPlayer DM27 = new FootballPlayer(jets_id, "Dee Milliner", 27);
		
		FootballPlayer JC6 = new FootballPlayer(bears_id, "Jay Cutler", 6);
		FootballPlayer MF22 = new FootballPlayer(bears_id, "Matt Forte", 22);
		FootballPlayer MB29 = new FootballPlayer(bears_id, "Michael Bush", 29);
		FootballPlayer BM15 = new FootballPlayer(bears_id, "Brandon Marshall", 15);
		FootballPlayer AJ17 = new FootballPlayer(bears_id, "Alshon Jeffreys", 17);
		FootballPlayer DH21 = new FootballPlayer(bears_id, "Devin Hester", 21);
		FootballPlayer EB80 = new FootballPlayer(bears_id, "Earl Bennett", 80);
		FootballPlayer DR88 = new FootballPlayer(bears_id, "Dante Rosario", 88);
		FootballPlayer MB83 = new FootballPlayer(bears_id, "Martellus Bennett", 83);
		FootballPlayer TF43 = new FootballPlayer(bears_id, "Tony Fiametta", 43);
		FootballPlayer JP90 = new FootballPlayer(bears_id, "Julius Peppers", 90);
		FootballPlayer LB60 = new FootballPlayer(bears_id, "Lance Briggs", 60);
		FootballPlayer MW21 = new FootballPlayer(bears_id, "Major Wright", 21);
				
		FootballPlayer TR9 = new FootballPlayer(cowboys_id, "Tony Romo", 9);	
		FootballPlayer DM29 = new FootballPlayer(cowboys_id, "DeMarco Murray", 29);	
		FootballPlayer JR21 = new FootballPlayer(cowboys_id, "Joesph Randle", 21);	
		FootballPlayer DB88 = new FootballPlayer(cowboys_id, "Dez Bryant", 88);	
		FootballPlayer MA19 = new FootballPlayer(cowboys_id, "Miles Austin", 19);	
		FootballPlayer TW83 = new FootballPlayer(cowboys_id, "Terrance Williams", 83);	
		FootballPlayer DH17 = new FootballPlayer(cowboys_id, "Dwayne Harris", 17);	
		FootballPlayer CB11 = new FootballPlayer(cowboys_id, "Cole Beasley", 11);	
		FootballPlayer JW82 = new FootballPlayer(cowboys_id, "Jason Witten", 82);	
		FootballPlayer JH84 = new FootballPlayer(cowboys_id, "James Hanna", 84);	
		FootballPlayer DW94 = new FootballPlayer(cowboys_id, "DeMarcus Ware", 94);	
		FootballPlayer SL50 = new FootballPlayer(cowboys_id, "Sean Lee", 50);	
		FootballPlayer MC24 = new FootballPlayer(cowboys_id, "Morris Claiborne", 24);
		
		FootballPlayer DB9 = new FootballPlayer(saints_id, "Drew Brees", 9);
		FootballPlayer MI22 = new FootballPlayer(saints_id, "Mark Ingram", 22);
		FootballPlayer DS43 = new FootballPlayer(saints_id, "Darren Sproles", 43);
		FootballPlayer KR29 = new FootballPlayer(saints_id, "Khiry Robinson", 29);
		FootballPlayer MC12 = new FootballPlayer(saints_id, "Marques Colston", 12);
		FootballPlayer KS84 = new FootballPlayer(saints_id, "Kenny Stills", 84);
		FootballPlayer LM16 = new FootballPlayer(saints_id, "Lance Moore", 16);
		FootballPlayer RM17 = new FootballPlayer(saints_id, "Robert Meachem", 17);
		FootballPlayer JG80 = new FootballPlayer(saints_id, "Jimmy Graham", 80);
		FootballPlayer BW82 = new FootballPlayer(saints_id, "Benjamin Watson", 82);
		FootballPlayer JC94 = new FootballPlayer(saints_id, "Jordan Cameron", 94);
		FootballPlayer CL50 = new FootballPlayer(saints_id, "Curtis Lofton", 50);
		FootballPlayer MJ27 = new FootballPlayer(saints_id, "Malcolm Jenkins", 27);
				
		long TB12_id = _football_db.createPlayers(TB12);
		long LB29_id = _football_db.createPlayers(LB29);
		long SR22_id = _football_db.createPlayers(SR22);
		long SV34_id = _football_db.createPlayers(SV34);
		long JE11_id = _football_db.createPlayers(JE11);
		long AD17_id = _football_db.createPlayers(AD17);
		long DA80_id = _football_db.createPlayers(DA80);
		long KT85_id = _football_db.createPlayers(KT85);
		long RG87_id = _football_db.createPlayers(RG87);
		long MH47_id = _football_db.createPlayers(MH47);
		long VW75_id = _football_db.createPlayers(VW75);
		long JM51_id = _football_db.createPlayers(JM51);
		long DM32_id = _football_db.createPlayers(DM32);
		
		long GS7_id = _football_db.createPlayers(GS7);
		long CI33_id = _football_db.createPlayers(CI33);
		long BP29_id = _football_db.createPlayers(BP29);
		long DR24_id = _football_db.createPlayers(DR24);
		long JK11_id = _football_db.createPlayers(JK11);
		long SH10_id = _football_db.createPlayers(SH10);
		long GS17_id = _football_db.createPlayers(GS17);
		long DN86_id = _football_db.createPlayers(DN86);
		long ZS82_id = _football_db.createPlayers(ZS82);
		long JC67_id = _football_db.createPlayers(JC87);
		long SR91_id = _football_db.createPlayers(SR91);
		long CP97_id = _football_db.createPlayers(CP97);
		long DM27_id = _football_db.createPlayers(DM27);
		
		long JC6_id = _football_db.createPlayers(JC6);
		long MF22_id = _football_db.createPlayers(MF22);
		long MB29_id = _football_db.createPlayers(MB29);
		long BM15_id = _football_db.createPlayers(BM15);
		long AJ17_id = _football_db.createPlayers(AJ17);
		long DH21_id = _football_db.createPlayers(DH21);
		long EB80_id = _football_db.createPlayers(EB80);
		long DR88_id = _football_db.createPlayers(DR88);
		long MB83_id = _football_db.createPlayers(MB83);
		long TF43_id = _football_db.createPlayers(TF43);
		long JP90_id = _football_db.createPlayers(JP90);
		long LB60_id = _football_db.createPlayers(LB60);
		long MW21_id = _football_db.createPlayers(MW21);
		
		long TR9_id = _football_db.createPlayers(TR9);
		long DM29_id = _football_db.createPlayers(DM29);
		long JR21_id = _football_db.createPlayers(JR21);
		long DB88_id = _football_db.createPlayers(DB88);
		long MA19_id = _football_db.createPlayers(MA19);
		long TW83_id = _football_db.createPlayers(TW83);
		long DH17_id = _football_db.createPlayers(DH17);
		long CB11_id = _football_db.createPlayers(CB11);
		long JW82_id = _football_db.createPlayers(JW82);
		long JH84_id = _football_db.createPlayers(JH84);
		long DW94_id = _football_db.createPlayers(DW94);
		long SL50_id = _football_db.createPlayers(SL50);
		long MC24_id = _football_db.createPlayers(MC24);
		
		long DB9_id = _football_db.createPlayers(DB9);
		long MI22_id = _football_db.createPlayers(MI22);
		long DS43_id = _football_db.createPlayers(DS43);
		long KR29_id = _football_db.createPlayers(KR29);
		long MC12_id = _football_db.createPlayers(MC12);
		long KS84_id = _football_db.createPlayers(KS84);
		long LM16_id = _football_db.createPlayers(LM16);
		long RM17_id = _football_db.createPlayers(RM17);
		long JG80_id = _football_db.createPlayers(JG80);
		long BW82_id = _football_db.createPlayers(BW82);
		long JC94_id = _football_db.createPlayers(JC94);
		long CL50_id = _football_db.createPlayers(CL50);
		long MJ27_id = _football_db.createPlayers(MJ27);
*/
	
/*		
		//Test Hockey Teams and players
		Teams rangers = new Teams("New York Rangers", "NYR", "Alain Vigneault", "Hockey");
		Teams bruins = new Teams("Boston Bruins", "BOS", "Claude Julien", "Hockey");
		Teams blackhawks = new Teams("Chicago Blackhawks", "CHI", "Joel Quenneville", "Hockey");
		Teams penguins = new Teams("Pittsburg Penguins", "PIT", "Dan Bylsma", "Hockey");
		Teams kings = new Teams("Los Angeles Kings", "LA", "Darryl Sutter", "Hockey");
		
		long rangers_id = _hockey_db.createTeams(rangers);
		long bruins_id = _hockey_db.createTeams(bruins);
		long blackhawks_id = _hockey_db.createTeams(blackhawks);
		long penguins_id = _hockey_db.createTeams(penguins);
		long kings_id = _hockey_db.createTeams(kings);
		
		HockeyPlayer HL30 = new HockeyPlayer(rangers_id, "Henrik Lunqvist", 30);
		HockeyPlayer DG5 = new HockeyPlayer(rangers_id, "Dan Girardi", 5);
		HockeyPlayer RM27 = new HockeyPlayer(rangers_id, "Ryan McDonagh", 27);
		HockeyPlayer MS18 = new HockeyPlayer(rangers_id, "Marc Staal", 18);
		HockeyPlayer JM17 = new HockeyPlayer(rangers_id, "John Moore", 17);
		HockeyPlayer BR19 = new HockeyPlayer(rangers_id, "Brad Richards", 19);
		HockeyPlayer DS21 = new HockeyPlayer(rangers_id, "Derek Stepan", 21);
		HockeyPlayer DB16 = new HockeyPlayer(rangers_id, "Derick Brassard", 16);
		HockeyPlayer MZ36 = new HockeyPlayer(rangers_id, "Mats Zuccarello", 36);
		HockeyPlayer CK20 = new HockeyPlayer(rangers_id, "Chris Kreider", 20);
		HockeyPlayer RN61 = new HockeyPlayer(rangers_id, "Rick Nash", 61);
		HockeyPlayer RC24 = new HockeyPlayer(rangers_id, "Ryan Callahan", 24);
		
		HockeyPlayer TR40 = new HockeyPlayer(bruins_id, "Tuukka Rask", 40);
		HockeyPlayer TK47 = new HockeyPlayer(bruins_id, "Torey Krug", 47);
		HockeyPlayer ZC33 = new HockeyPlayer(bruins_id, "Zdeno Chara", 33);
		HockeyPlayer JB55 = new HockeyPlayer(bruins_id, "Johnny Boychuk", 55);
		HockeyPlayer MB43 = new HockeyPlayer(bruins_id, "Matt Bartkowski", 43);
		HockeyPlayer DK46 = new HockeyPlayer(bruins_id, "David Krejci", 46);
		HockeyPlayer PB37 = new HockeyPlayer(bruins_id, "Patrice Bergeron", 37);
		HockeyPlayer CS34 = new HockeyPlayer(bruins_id, "Carl Soderberg", 34);
		HockeyPlayer JI12 = new HockeyPlayer(bruins_id, "Jarome Iginla", 12);
		HockeyPlayer ML17 = new HockeyPlayer(bruins_id, "Milan Lucic", 17);
		HockeyPlayer RS18 = new HockeyPlayer(bruins_id, "Reilly Smith", 18);
		HockeyPlayer BM63 = new HockeyPlayer(bruins_id, "Brad Marchand", 63);
		
		HockeyPlayer CC50 = new HockeyPlayer(blackhawks_id, "Corey Crawford", 50);
		HockeyPlayer DK2 = new HockeyPlayer(blackhawks_id, "Duncan Keith", 2);
		HockeyPlayer BS7 = new HockeyPlayer(blackhawks_id, "Brent Seabrook", 7);
		HockeyPlayer NL8 = new HockeyPlayer(blackhawks_id, "Nick Leddy", 8);
		HockeyPlayer NH4 = new HockeyPlayer(blackhawks_id, "Niklas Hjalmarsson", 4);
		HockeyPlayer JT19 = new HockeyPlayer(blackhawks_id, "Jonathan Toews", 19);
		HockeyPlayer AS65 = new HockeyPlayer(blackhawks_id, "Andrew Shaw", 65);
		HockeyPlayer MK16 = new HockeyPlayer(blackhawks_id, "Marcus Kruger", 16);
		HockeyPlayer PK88 = new HockeyPlayer(blackhawks_id, "Patrick Kane", 88);
		HockeyPlayer PS10 = new HockeyPlayer(blackhawks_id, "Patick Sharp", 10);
		HockeyPlayer MH81 = new HockeyPlayer(blackhawks_id, "Marian Hossa", 81);
		HockeyPlayer BS20 = new HockeyPlayer(blackhawks_id, "Brandon Saad", 20);
		
		HockeyPlayer MF29 = new HockeyPlayer(penguins_id, "Marc-Andre Fleury", 29);
		HockeyPlayer MN2 = new HockeyPlayer(penguins_id, "Matt Niskanen", 2);
		HockeyPlayer OM3 = new HockeyPlayer(penguins_id, "Olli Maatta", 3);
		HockeyPlayer KL58 = new HockeyPlayer(penguins_id, "Kris Letang", 58);
		HockeyPlayer BO44 = new HockeyPlayer(penguins_id, "Brooks Orpik", 44);
		HockeyPlayer SC87 = new HockeyPlayer(penguins_id, "Sidney Crosby", 87);
		HockeyPlayer EM71 = new HockeyPlayer(penguins_id, "Evgeni Malkin", 71);
		HockeyPlayer BS16 = new HockeyPlayer(penguins_id, "Brandon Sutter", 16);
		HockeyPlayer CK14 = new HockeyPlayer(penguins_id, "Chris Kunitz", 14);
		HockeyPlayer JN18 = new HockeyPlayer(penguins_id, "James Neal", 18);
		HockeyPlayer JJ36 = new HockeyPlayer(penguins_id, "Jussi Jokinen", 36);
		HockeyPlayer PD9 = new HockeyPlayer(penguins_id, "Pascal Dupuis", 9);
		
		HockeyPlayer JQ32 = new HockeyPlayer(kings_id, "Jonathan Quick", 32);
		HockeyPlayer DD8 = new HockeyPlayer(kings_id, "Drew Doughty", 8);
		HockeyPlayer SV26 = new HockeyPlayer(kings_id, "Slava Voynov", 26);
		HockeyPlayer JM6 = new HockeyPlayer(kings_id, "Jake Muzzin", 6);
		HockeyPlayer RR44 = new HockeyPlayer(kings_id, "Robyn Regehr", 44);
		HockeyPlayer AK11 = new HockeyPlayer(kings_id, "Anze Kopitar", 11);
		HockeyPlayer JC77 = new HockeyPlayer(kings_id, "Jeff Carter", 77);
		HockeyPlayer MR10 = new HockeyPlayer(kings_id, "Mike Richards", 10);
		HockeyPlayer TT73 = new HockeyPlayer(kings_id, "Tyler Toffoli", 73);
		HockeyPlayer JW14 = new HockeyPlayer(kings_id, "Justin Williams", 14);
		HockeyPlayer DK74 = new HockeyPlayer(kings_id, "Dwight King", 74);
		HockeyPlayer DB23 = new HockeyPlayer(kings_id, "Dustin Brown", 23);	
		
		long HL30_id = _hockey_db.createPlayers(HL30);
		long DG5_id = _hockey_db.createPlayers(DG5);
		long RM27_id = _hockey_db.createPlayers(RM27);
		long MS18_id = _hockey_db.createPlayers(MS18);
		long JM17_id = _hockey_db.createPlayers(JM17);
		long BR19_id = _hockey_db.createPlayers(BR19);
		long DS21_id = _hockey_db.createPlayers(DS21);
		long DB16_id = _hockey_db.createPlayers(DB16);
		long MZ36_id = _hockey_db.createPlayers(MZ36);
		long CK20_id = _hockey_db.createPlayers(CK20);
		long RN61_id = _hockey_db.createPlayers(RN61);
		long RC24_id = _hockey_db.createPlayers(RC24);
		
		long TR40_id = _hockey_db.createPlayers(TR40);
		long TK47_id = _hockey_db.createPlayers(TK47);
		long ZC33_id = _hockey_db.createPlayers(ZC33);
		long JB55_id = _hockey_db.createPlayers(JB55);
		long MB43_id = _hockey_db.createPlayers(MB43);
		long DK46_id = _hockey_db.createPlayers(DK46);		
		long PB37_id = _hockey_db.createPlayers(PB37);
		long CS34_id = _hockey_db.createPlayers(CS34);
		long JI12_id = _hockey_db.createPlayers(JI12);
		long ML17_id = _hockey_db.createPlayers(ML17);
		long RS18_id = _hockey_db.createPlayers(RS18);
		long BM63_id = _hockey_db.createPlayers(BM63);
		
		long CC50_id = _hockey_db.createPlayers(CC50);
		long DK2_id = _hockey_db.createPlayers(DK2);
		long BS7_id = _hockey_db.createPlayers(BS7);
		long NL8_id = _hockey_db.createPlayers(NL8);
		long NH4_id = _hockey_db.createPlayers(NH4);
		long JT19_id = _hockey_db.createPlayers(JT19);
		long AS65_id = _hockey_db.createPlayers(AS65);
		long MK16_id = _hockey_db.createPlayers(MK16);
		long PK88_id = _hockey_db.createPlayers(PK88);
		long PS10_id = _hockey_db.createPlayers(PS10);
		long MH81_id = _hockey_db.createPlayers(MH81);
		long BS20_id = _hockey_db.createPlayers(BS20);

		long MF29_id = _hockey_db.createPlayers(MF29);
		long MN2_id = _hockey_db.createPlayers(MN2);
		long OM3_id = _hockey_db.createPlayers(OM3);
		long KL58_id = _hockey_db.createPlayers(KL58);
		long BO44_id = _hockey_db.createPlayers(BO44);
		long SC87_id = _hockey_db.createPlayers(SC87);
		long EM71_id = _hockey_db.createPlayers(EM71);
		long BS16_id = _hockey_db.createPlayers(BS16);
		long CK14_id = _hockey_db.createPlayers(CK14);
		long JN18_id = _hockey_db.createPlayers(JN18);
		long JJ36_id = _hockey_db.createPlayers(JJ36);
		long PD9_id = _hockey_db.createPlayers(PD9);
		
		long JQ32_id = _hockey_db.createPlayers(JQ32);
		long DD8_id = _hockey_db.createPlayers(DD8);
		long SV26_id = _hockey_db.createPlayers(SV26);
		long JM6_id = _hockey_db.createPlayers(JM6);
		long RR44_id = _hockey_db.createPlayers(RR44);
		long AK11_id = _hockey_db.createPlayers(AK11);
		long JC77_id = _hockey_db.createPlayers(JC77);
		long MR10_id = _hockey_db.createPlayers(MR10);
		long TT73_id = _hockey_db.createPlayers(TT73);
		long JW14_id = _hockey_db.createPlayers(JW14);
		long DK74_id = _hockey_db.createPlayers(DK74);
		long DB23_id = _hockey_db.createPlayers(DB23);		
*/

/*
		//Test Soccer Teams and players
		Teams america = new Teams("United States", "USA", "Jurgen Klinsmann", "Soccer");
		Teams spain = new Teams("Spain", "ESP", "Vincente del Bosque", "Soccer");
		Teams germany = new Teams("Germany", "GER", "Joachim Low", "Soccer");
		Teams portugal = new Teams("Portugal", "POR", "Paulo Bento", "Soccer");
		Teams england = new Teams("England", "ENG", "Roy Hodgson", "Soccer");
		Teams argentina = new Teams("Argentina", "ARG", "Alejandro Sabella", "Soccer");
		Teams brazil = new Teams("Brazil", "BRZ", "Luiz Felipe Scolari", "Soccer");
		
		
		long america_id = _soccer_db.createTeams(america);
		long spain_id = _soccer_db.createTeams(spain);
		long germany_id = _soccer_db.createTeams(germany);
		long portugal_id = _soccer_db.createTeams(portugal);
		long england_id = _soccer_db.createTeams(england);
		long argentina_id = _soccer_db.createTeams(argentina);
		long brazil_id = _soccer_db.createTeams(brazil);
		
		SoccerPlayer TH1 = new SoccerPlayer(america_id, "Tim Howard", 1);
		SoccerPlayer DB11 = new SoccerPlayer(america_id, "DaMarcus Beasley", 11);
		SoccerPlayer OO8 = new SoccerPlayer(america_id, "Oguchi Onyewu", 8);
		SoccerPlayer CG44 = new SoccerPlayer(america_id, "Clarence Goodson", 44);
		SoccerPlayer MB4 = new SoccerPlayer(america_id, "Michael Bradley", 4);
		SoccerPlayer ME7 = new SoccerPlayer(america_id, "Maurice Edu", 7);
		SoccerPlayer KB5 = new SoccerPlayer(america_id, "Kyle Beckerman", 5);
		SoccerPlayer BF22 = new SoccerPlayer(america_id, "Benny Feilhaber", 22);
		SoccerPlayer LD10 = new SoccerPlayer(america_id, "Landon Donovan", 10);
		SoccerPlayer CD9 = new SoccerPlayer(america_id, "Clint Dempsey", 9);
		SoccerPlayer JA17 = new SoccerPlayer(america_id, "Jozy Altidore", 17);
		
		SoccerPlayer IC1 = new SoccerPlayer(spain_id, "Iker Casillas", 1);
		SoccerPlayer SR15 = new SoccerPlayer(spain_id, "Sergio Ramos", 15);
		SoccerPlayer AA17 = new SoccerPlayer(spain_id, "Alvaro Arbeloa", 17);
		SoccerPlayer RA2 = new SoccerPlayer(spain_id, "Raul Albiol", 2);
		SoccerPlayer XA14 = new SoccerPlayer(spain_id, "Xabi Alonso", 14);
		SoccerPlayer AI6 = new SoccerPlayer(spain_id, "Andres Iniesta", 6);
		SoccerPlayer SB16 = new SoccerPlayer(spain_id, "Sergio Busquets", 16);
		SoccerPlayer SC20 = new SoccerPlayer(spain_id, "Santi Cazorla", 20);
		SoccerPlayer DV9 = new SoccerPlayer(spain_id, "David Villa", 9);
		SoccerPlayer FT7 = new SoccerPlayer(spain_id, "Fernando Torres", 7);
		SoccerPlayer P5 = new SoccerPlayer(spain_id, "Pedro", 5);
		
		SoccerPlayer MN1 = new SoccerPlayer(germany_id, "Manuel Neuer", 1);
		SoccerPlayer PL16 = new SoccerPlayer(germany_id, "Philipp Lahm", 16);
		SoccerPlayer PM17 = new SoccerPlayer(germany_id, "Per Mertesacker", 17);
		SoccerPlayer JB20 = new SoccerPlayer(germany_id, "Jerome Boateng", 20);
		SoccerPlayer HW4 = new SoccerPlayer(germany_id, "Heiko Westermann", 4);
		SoccerPlayer BS7a = new SoccerPlayer(germany_id, "Bastian Schweisteiger", 7);
		SoccerPlayer MO8 = new SoccerPlayer(germany_id, "Mesut Ozil", 8);
		SoccerPlayer TM25 = new SoccerPlayer(germany_id, "Thomas Muller", 25);
		SoccerPlayer MG19 = new SoccerPlayer(germany_id, "Mario Gotze", 19);
		SoccerPlayer MR11 = new SoccerPlayer(germany_id, "Marco Reus", 11);
		SoccerPlayer MK10 = new SoccerPlayer(germany_id, "Max Kruse", 10);
		
		SoccerPlayer JH1 = new SoccerPlayer(england_id, "Joe Hart", 1);
		SoccerPlayer AC3 = new SoccerPlayer(england_id, "Ashley Cole", 3);
		SoccerPlayer GJ2 = new SoccerPlayer(england_id, "Glen Johnson", 2);
		SoccerPlayer PJ6 = new SoccerPlayer(england_id, "Phil Jagielka", 6);
		SoccerPlayer GC24 = new SoccerPlayer(england_id, "Gary Cahill", 24);
		SoccerPlayer SG8 = new SoccerPlayer(england_id, "Steven Gerrard", 8);
		SoccerPlayer FL9 = new SoccerPlayer(england_id, "Frank Lampard", 9);
		SoccerPlayer JM7 = new SoccerPlayer(england_id, "James Milner", 7);
		SoccerPlayer WR10 = new SoccerPlayer(england_id, "Wayne Rooney", 10);
		SoccerPlayer JD18 = new SoccerPlayer(england_id, "Jermaine Defoe", 18);
		SoccerPlayer DS15 = new SoccerPlayer(england_id, "Daniel Sturridge", 15);
		
		SoccerPlayer E1 = new SoccerPlayer(portugal_id, "Eduardo", 1);
		SoccerPlayer BA22 = new SoccerPlayer(portugal_id, "Bruno Alves", 22);
		SoccerPlayer P3 = new SoccerPlayer(portugal_id, "Pepe", 3);
		SoccerPlayer FC5 = new SoccerPlayer(portugal_id, "Fabio Coentrao", 5);
		SoccerPlayer N10 = new SoccerPlayer(portugal_id, "Nani", 10);
		SoccerPlayer JM8 = new SoccerPlayer(portugal_id, "Joao Moutinho", 8);
		SoccerPlayer RM16 = new SoccerPlayer(portugal_id, "Raul Meireles", 16);
		SoccerPlayer MV4 = new SoccerPlayer(portugal_id, "Miguel Veloso", 4);
		SoccerPlayer RS11 = new SoccerPlayer(portugal_id, "Rafa Silva", 11);
		SoccerPlayer CR7 = new SoccerPlayer(portugal_id, "Cristiano Ronaldo", 7);
		SoccerPlayer HP9 = new SoccerPlayer(portugal_id, "Helder Postiga", 9);
		
		SoccerPlayer SR1 = new SoccerPlayer(argentina_id, "Sergio Romero", 1);
		SoccerPlayer PZ5 = new SoccerPlayer(argentina_id, "Pablo Zabaleta", 5);
		SoccerPlayer FF21 = new SoccerPlayer(argentina_id, "Federico Fernandez", 21);
		SoccerPlayer MR6 = new SoccerPlayer(argentina_id, "Marco Rojo", 6);
		SoccerPlayer FG8 = new SoccerPlayer(argentina_id, "Fernando Gago", 8);
		SoccerPlayer AM22 = new SoccerPlayer(argentina_id, "Angel Di Maria", 22);
		SoccerPlayer JM14 = new SoccerPlayer(argentina_id, "Javier Mascherano", 14);
		SoccerPlayer EB19 = new SoccerPlayer(argentina_id, "Ever Benega", 19);
		SoccerPlayer RA11 = new SoccerPlayer(argentina_id, "Ricardo Alvarez", 11);
		SoccerPlayer LM10 = new SoccerPlayer(argentina_id, "Lionel Messi", 10);
		SoccerPlayer GH9 = new SoccerPlayer(argentina_id, "Gonzalo Higuain", 9);

		SoccerPlayer JC12 = new SoccerPlayer(brazil_id, "Juilo Cesar", 12);
		SoccerPlayer TS3a = new SoccerPlayer(brazil_id, "Thaigo Silva", 3);
		SoccerPlayer DA2 = new SoccerPlayer(brazil_id, "Dani Alves", 2);
		SoccerPlayer DL4 = new SoccerPlayer(brazil_id, "David Luiz", 4);
		SoccerPlayer M6 = new SoccerPlayer(brazil_id, "Marcelo", 6);
		SoccerPlayer R16 = new SoccerPlayer(brazil_id, "Ramires", 16);
		SoccerPlayer O11 = new SoccerPlayer(brazil_id, "Oscar", 11);
		SoccerPlayer LG17 = new SoccerPlayer(brazil_id, "Luis Gustavo", 17);
		SoccerPlayer P18 = new SoccerPlayer(brazil_id, "Paulinho", 18);
		SoccerPlayer R7 = new SoccerPlayer(brazil_id, "Robinho", 7);
		SoccerPlayer Ne10 = new SoccerPlayer(brazil_id, "Neymar", 10);
		
		long TH1_id = _soccer_db.createPlayers(TH1);
		long DB11_id = _soccer_db.createPlayers(DB11);
		long OO8_id = _soccer_db.createPlayers(OO8);
		long CG44_id = _soccer_db.createPlayers(CG44);
		long MB4_id = _soccer_db.createPlayers(MB4);
		long ME7_id = _soccer_db.createPlayers(ME7);
		long KB5_id = _soccer_db.createPlayers(KB5);
		long BF22_id = _soccer_db.createPlayers(BF22);
		long LD10_id = _soccer_db.createPlayers(LD10);
		long CD9_id = _soccer_db.createPlayers(CD9);
		long JA17_id = _soccer_db.createPlayers(JA17);
		
		long IC1_id = _soccer_db.createPlayers(IC1);
		long SR15_id = _soccer_db.createPlayers(SR15);
		long AA17_id = _soccer_db.createPlayers(AA17);
		long RA2_id = _soccer_db.createPlayers(RA2);
		long XA14_id = _soccer_db.createPlayers(XA14);
		long AI6_id = _soccer_db.createPlayers(AI6);
		long SB16_id = _soccer_db.createPlayers(SB16);
		long SC20_id = _soccer_db.createPlayers(SC20);
		long DV9_id = _soccer_db.createPlayers(DV9);
		long FT7_id = _soccer_db.createPlayers(FT7);
		long P5_id = _soccer_db.createPlayers(P5);
		
		long MN1_id = _soccer_db.createPlayers(MN1);
		long PL16_id = _soccer_db.createPlayers(PL16);
		long PM17_id = _soccer_db.createPlayers(PM17);
		long JB20_id = _soccer_db.createPlayers(JB20);
		long HW4_id = _soccer_db.createPlayers(HW4);
		long BS7a_id = _soccer_db.createPlayers(BS7a);
		long MO8_id = _soccer_db.createPlayers(MO8);
		long TM25_id = _soccer_db.createPlayers(TM25);
		long MG19_id = _soccer_db.createPlayers(MG19);
		long MR11_id = _soccer_db.createPlayers(MR11);
		long MK10_id = _soccer_db.createPlayers(MK10);
		
		long JH1_id = _soccer_db.createPlayers(JH1);
		long AC3_id = _soccer_db.createPlayers(AC3);
		long GJ2_id = _soccer_db.createPlayers(GJ2);
		long PJ6_id = _soccer_db.createPlayers(PJ6);
		long GC24_id = _soccer_db.createPlayers(GC24);
		long SG8_id = _soccer_db.createPlayers(SG8);
		long FL9_id = _soccer_db.createPlayers(FL9);
		long JM7_id = _soccer_db.createPlayers(JM7);
		long WR10_id = _soccer_db.createPlayers(WR10);
		long JD18_id = _soccer_db.createPlayers(JD18);
		long DS15_id = _soccer_db.createPlayers(DS15);
		
		long E1_id = _soccer_db.createPlayers(E1);
		long BA22_id = _soccer_db.createPlayers(BA22);
		long P3_id = _soccer_db.createPlayers(P3);
		long FC5_id = _soccer_db.createPlayers(FC5);
		long N10_id = _soccer_db.createPlayers(N10);
		long JM8_id = _soccer_db.createPlayers(JM8);
		long RM16_id = _soccer_db.createPlayers(RM16);
		long MV4_id = _soccer_db.createPlayers(MV4);
		long RS11_id = _soccer_db.createPlayers(RS11);
		long CR7_id = _soccer_db.createPlayers(CR7);
		long HP9_id = _soccer_db.createPlayers(HP9);	
		
		long SR1_id = _soccer_db.createPlayers(SR1);
		long PZ5_id = _soccer_db.createPlayers(PZ5);
		long FF21_id = _soccer_db.createPlayers(FF21);
		long MR6_id = _soccer_db.createPlayers(MR6);
		long FG8_id = _soccer_db.createPlayers(FG8);
		long AM22_id = _soccer_db.createPlayers(AM22);
		long JM14_id = _soccer_db.createPlayers(JM14);
		long EB19_id = _soccer_db.createPlayers(EB19);
		long RA11_id = _soccer_db.createPlayers(RA11);
		long LM10_id = _soccer_db.createPlayers(LM10);
		long GH9_id = _soccer_db.createPlayers(GH9);
	
		long JC12_id = _soccer_db.createPlayers(JC12);
		long TS3a_id = _soccer_db.createPlayers(TS3a);
		long DA2_id = _soccer_db.createPlayers(DA2);
		long DL4_id = _soccer_db.createPlayers(DL4);
		long M6_id = _soccer_db.createPlayers(M6);
		long R16_id = _soccer_db.createPlayers(R16);
		long O11_id = _soccer_db.createPlayers(O11);
		long LG17_id = _soccer_db.createPlayers(LG17);
		long P18_id = _soccer_db.createPlayers(P18);
		long R7_id = _soccer_db.createPlayers(R7);
		long Ne10_id = _soccer_db.createPlayers(Ne10);
*/	
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
		public void onClick(View view) {
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
	
	//touch listener for short buttons - recoloring when clicked
	public OnTouchListener shortButtonTouchListener = new OnTouchListener(){
		@Override
		public boolean onTouch(View view, MotionEvent event) {
			if(event.getAction() == MotionEvent.ACTION_DOWN){
				view.setBackgroundResource(R.drawable.view_style_plain_short_clicked);
				((Button)view).setTextColor(getResources().getColor(R.color.white));		
			}
			else if(event.getAction() == MotionEvent.ACTION_UP){
				view.setBackgroundResource(R.drawable.view_style_plain_short);
				((Button)view).setTextColor(getResources().getColor(R.color.black));	
			}
			return false;
		}
	};
	
	//touch listener for long buttons - recoloring when clicked
	public OnTouchListener longButtonTouchListener = new OnTouchListener(){
		@Override
		public boolean onTouch(View view, MotionEvent event) {
			if(event.getAction() == MotionEvent.ACTION_DOWN){
				view.setBackgroundResource(R.drawable.view_style_plain_long_clicked);
				((Button)view).setTextColor(getResources().getColor(R.color.white));		
			}
			else if(event.getAction() == MotionEvent.ACTION_UP){
				view.setBackgroundResource(R.drawable.view_style_plain_long);
				((Button)view).setTextColor(getResources().getColor(R.color.black));	
			}
			return false;
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
	
	public OnClickListener viewStatsListener = new OnClickListener(){
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getApplicationContext(), ViewStatsActivity.class);
				startActivity(intent);																
			}
		};
		
	public OnClickListener optionListener = new OnClickListener(){
		@Override
		public void onClick(View view) {
			
		}
	};
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}
	
}
