package com.seniordesign.ultimatescorecard.stats.soccer;

import java.util.ArrayList;

import com.seniordesign.ultimatescorecard.R;
import com.seniordesign.ultimatescorecard.data.soccer.SoccerPlayer;
import com.seniordesign.ultimatescorecard.data.soccer.SoccerTeam;
import com.seniordesign.ultimatescorecard.sqlite.helper.Players;
import com.seniordesign.ultimatescorecard.sqlite.helper.Teams;
import com.seniordesign.ultimatescorecard.sqlite.hockey.HockeyDatabaseHelper;
import com.seniordesign.ultimatescorecard.sqlite.hockey.HockeyGameStats;
import com.seniordesign.ultimatescorecard.sqlite.hockey.HockeyGames;
import com.seniordesign.ultimatescorecard.sqlite.soccer.SoccerDatabaseHelper;
import com.seniordesign.ultimatescorecard.sqlite.soccer.SoccerGameStats;
import com.seniordesign.ultimatescorecard.sqlite.soccer.SoccerGames;
import com.seniordesign.ultimatescorecard.view.StaticFinalVars;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

public class SoccerIndividualStatActivity extends FragmentActivity{

	private SoccerDatabaseHelper _soccer_db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_individual_soccer);
		
		Teams team = (Teams) getIntent().getSerializableExtra(StaticFinalVars.TEAM_INFO);
		ArrayList<Players> players = (ArrayList<Players>) getIntent().getSerializableExtra(StaticFinalVars.PLAYERS_INFO);
		long g_id = (Long) getIntent().getSerializableExtra(StaticFinalVars.GAME_ID);
		String name = getIntent().getStringExtra(StaticFinalVars.PLAYER_NAME);
		boolean home = getIntent().getBooleanExtra(StaticFinalVars.HOME_OR_AWAY, true);

		_soccer_db = new SoccerDatabaseHelper(getApplicationContext());
		SoccerGames game = (SoccerGames) _soccer_db.getGame(g_id);

		if(name.equals(team.getabbv() + " Stats")){
			if(home){
				
				((TextView)findViewById(R.id.playerName)).setText(team.getabbv());
				((TextView)findViewById(R.id.teamName)).setText(team.gettname());
				((TextView)findViewById(R.id.goalTotal)).setText("Goals: "+game.gethomegoals());
				((TextView)findViewById(R.id.assistTotal)).setText("Assists: "+game.gethomeast());
				((TextView)findViewById(R.id.shotOnGoalTotal)).setText("Shots On Goal: "+game.gethomesog());
				((TextView)findViewById(R.id.penaltyTypeTotal)).setText(
						" Yellow Cards:" + game.gethomeycard() +
						"\n Red Cards: " + game.gethomercard());
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
				((TextView)findViewById(R.id.penaltyTypeTotal)).setText(
						" Yellow Cards:" + game.getawayycard() +
						"\n Red Cards: " + game.getawayrcard());
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
			SoccerGameStats stats = _soccer_db.getPlayerGameStats(g_id, player.getpid());
				
			((TextView)findViewById(R.id.playerName)).setText(player.getpname());
			((TextView)findViewById(R.id.teamName)).setText(team.gettname());
			((TextView)findViewById(R.id.goalTotal)).setText("Goals: "+stats.getgoals());
			((TextView)findViewById(R.id.assistTotal)).setText("Assists: "+stats.getast());
			((TextView)findViewById(R.id.shotOnGoalTotal)).setText("Shots On Goal: "+stats.getsog());
			((TextView)findViewById(R.id.penaltyTypeTotal)).setText(
					" Yellow Cards:" + stats.getycard() +
					"\n Red Cards: " + stats.getrcard());
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
