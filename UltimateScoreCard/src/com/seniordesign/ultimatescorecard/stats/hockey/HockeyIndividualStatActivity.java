package com.seniordesign.ultimatescorecard.stats.hockey;

import java.util.ArrayList;

import com.seniordesign.ultimatescorecard.R;
import com.seniordesign.ultimatescorecard.data.hockey.HockeyPlayer;
import com.seniordesign.ultimatescorecard.data.hockey.HockeyTeam;
import com.seniordesign.ultimatescorecard.sqlite.helper.Players;
import com.seniordesign.ultimatescorecard.sqlite.helper.Teams;
import com.seniordesign.ultimatescorecard.sqlite.hockey.HockeyDatabaseHelper;
import com.seniordesign.ultimatescorecard.sqlite.hockey.HockeyGameStats;
import com.seniordesign.ultimatescorecard.sqlite.hockey.HockeyGames;
import com.seniordesign.ultimatescorecard.view.StaticFinalVars;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

public class HockeyIndividualStatActivity extends FragmentActivity{

	public HockeyDatabaseHelper _hockey_db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_individual_hockey);
		
		Teams team = (Teams) getIntent().getSerializableExtra(StaticFinalVars.TEAM_INFO);
		ArrayList<Players> players = (ArrayList<Players>) getIntent().getSerializableExtra(StaticFinalVars.PLAYERS_INFO);
		long g_id = (Long) getIntent().getSerializableExtra(StaticFinalVars.GAME_ID);
		String name = getIntent().getStringExtra(StaticFinalVars.PLAYER_NAME);
		boolean home = getIntent().getBooleanExtra(StaticFinalVars.HOME_OR_AWAY, true);

		_hockey_db = new HockeyDatabaseHelper(getApplicationContext());
		HockeyGames game = (HockeyGames) _hockey_db.getGame(g_id);
		
		if(name.equals(team.getabbv() + " Stats")){
			if(home){
				
				((TextView)findViewById(R.id.playerName)).setText(team.getabbv());
				((TextView)findViewById(R.id.teamName)).setText(team.gettname());
				((TextView)findViewById(R.id.goalTotal)).setText("Goals: "+game.gethomegoals());
				((TextView)findViewById(R.id.assistTotal)).setText("Assists: "+game.gethomeast());
				((TextView)findViewById(R.id.shotOnGoalTotal)).setText("Shots On Goal: "+game.gethomesog());
				((TextView)findViewById(R.id.penaltyTotal)).setText("Penalties: "+(game.gethomepenmajor()+game.gethomepenminor()+game.gethomepenmisconduct()));
				((TextView)findViewById(R.id.penaltyTypeTotal)).setText(
						" Minors:" + game.gethomepenminor() +
						"\n Majors: " + game.gethomepenmajor() +
						"\n Misconduct: " + game.gethomepenmisconduct());
				((TextView)findViewById(R.id.penaltyMinsTotal)).setText("Penalty Minutes: "+game.gethomepenminutes());
				//Goalie Stats
				((TextView)findViewById(R.id.goalieTitle)).setText("Goalie Stats");
				((TextView)findViewById(R.id.goalieStats)).setText(
					" Saves:" + game.gethomesaves() +
					"\n Goals Allowed: " + game.gethomegoalsallowed() +
					"\n Save %: " + game.gethomesavepercent());

			}
			else{
				((TextView)findViewById(R.id.playerName)).setText(team.getabbv());
				((TextView)findViewById(R.id.teamName)).setText(team.gettname());
				((TextView)findViewById(R.id.goalTotal)).setText("Goals: "+game.getawaygoals());
				((TextView)findViewById(R.id.assistTotal)).setText("Assists: "+game.getawayast());
				((TextView)findViewById(R.id.shotOnGoalTotal)).setText("Shots On Goal: "+game.getawaysog());
				((TextView)findViewById(R.id.penaltyTotal)).setText("Penalties: "+(game.getawaypenmajor()+game.getawaypenminor()+game.getawaypenmisconduct()));
				((TextView)findViewById(R.id.penaltyTypeTotal)).setText(
						" Minors:" + game.getawaypenminor() +
						"\n Majors: " + game.getawaypenmajor() +
						"\n Misconduct: " + game.getawaypenmisconduct());
				((TextView)findViewById(R.id.penaltyMinsTotal)).setText("Penalty Minutes: "+game.getawaypenminutes());
				//Goalie Stats
				((TextView)findViewById(R.id.goalieTitle)).setText("Goalie Stats");
				((TextView)findViewById(R.id.goalieStats)).setText(
					" Saves:" + game.getawaysaves() +
					"\n Goals Allowed: " + game.getawaygoalsallowed() +
					"\n Save %: " + game.getawaysavepercent());
			}
		}
		
		else{
			Players player = null;
			for(Players p: players){
				if(p.getpname().equals(name)){
					player = p;
				}
			}
			HockeyGameStats stats = _hockey_db.getPlayerGameStats(g_id, player.getpid());
				
			((TextView)findViewById(R.id.playerName)).setText(player.getpname());
			((TextView)findViewById(R.id.teamName)).setText(team.gettname());
			((TextView)findViewById(R.id.goalTotal)).setText("Goals: "+stats.getgoals());
			((TextView)findViewById(R.id.assistTotal)).setText("Assists: "+stats.getast());
			((TextView)findViewById(R.id.shotOnGoalTotal)).setText("Shots On Goal: "+stats.getsog());
			((TextView)findViewById(R.id.penaltyTotal)).setText("Penalties: "+(stats.getpenmajor()+stats.getpenminor()+stats.getpenmisconduct()));
			((TextView)findViewById(R.id.penaltyTypeTotal)).setText(
					" Minors:" + stats.getpenminor() +
					"\n Majors: " + stats.getpenmajor() +
					"\n Misconduct: " + stats.getpenmisconduct());
			((TextView)findViewById(R.id.penaltyMinsTotal)).setText("Penalty Minutes: "+stats.getpenminutes());
			//Goalie Stats
			((TextView)findViewById(R.id.goalieTitle)).setText("Goalie Stats");
			((TextView)findViewById(R.id.goalieStats)).setText(
				" Saves:" + stats.getsaves() +
				"\n Goals Allowed: " + stats.getgoalsallowed() +

				"\n Save %: " + stats.getsavepercent());
		}
	}
		

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}
}
