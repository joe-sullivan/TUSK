package com.seniordesign.ultimatescorecard.stats.basketball;

import java.util.ArrayList;

import com.seniordesign.ultimatescorecard.R;
import com.seniordesign.ultimatescorecard.data.basketball.BasketballPlayer;
import com.seniordesign.ultimatescorecard.data.basketball.BasketballTeam;
import com.seniordesign.ultimatescorecard.sqlite.basketball.BasketballDatabaseHelper;
import com.seniordesign.ultimatescorecard.sqlite.basketball.BasketballGameStats;
import com.seniordesign.ultimatescorecard.sqlite.helper.*;
import com.seniordesign.ultimatescorecard.view.StaticFinalVars;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

public class BasketballIndividualStatActivity extends FragmentActivity{

	public BasketballDatabaseHelper _basketball_db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_individual);
		
		Teams team = (Teams) getIntent().getSerializableExtra(StaticFinalVars.TEAM_INFO);
		ArrayList<Players> players = (ArrayList<Players>) getIntent().getSerializableExtra(StaticFinalVars.PLAYERS_INFO);
		long g_id = (Long) getIntent().getSerializableExtra(StaticFinalVars.GAME_ID);
		String name = getIntent().getStringExtra(StaticFinalVars.PLAYER_NAME);
		Players player = null;
		for(Players p: players){
			if(p.getpname().equals(name)){
				player = p;
			}
		}
		
		_basketball_db = new BasketballDatabaseHelper(getApplicationContext());
		BasketballGameStats stats = _basketball_db.getPlayerGameStats(g_id, player.getpid());
		
		((TextView)findViewById(R.id.playerName)).setText(player.getpname());
		((TextView)findViewById(R.id.teamName)).setText(team.gettname());
		((TextView)findViewById(R.id.pointTotal)).setText("Points: "+stats.getpts());	
		((TextView)findViewById(R.id.fieldGoalTotal)).setText(				
				"Field Goals:" +
				"\n Made: " + stats.getfgm() +
				"\t\t Missed: " + stats.getfga() +
				"\n FG %: " + stats.getfgpercent());				
		((TextView)findViewById(R.id.freeThrowTotal)).setText(
				"Free Throws:" +
				"\n Made: " + stats.getftm() +
				"\t\t Missed: " + stats.getfta() +
				"\n FT %: " + stats.getftpercent());	
	
		((TextView)findViewById(R.id.reboundTotal)).setText("Rebounds: "+(stats.getdreb()+stats.getoreb()));
		((TextView)findViewById(R.id.dReboundTotal)).setText("Defensive Rebounds: "+stats.getdreb());
		((TextView)findViewById(R.id.oReboundTotal)).setText("Offensive Rebounds: "+stats.getoreb());
		((TextView)findViewById(R.id.assistTotal)).setText("Assists: "+stats.getast());
		((TextView)findViewById(R.id.stealTotal)).setText("Steals: "+stats.getstl());
		((TextView)findViewById(R.id.blocksTotal)).setText("Blocks: "+stats.getblk());
		((TextView)findViewById(R.id.foulTotal)).setText("Fouls: "+stats.getpf());
		((TextView)findViewById(R.id.techFouls)).setText("Technical Fouls: "+stats.gettech());
		((TextView)findViewById(R.id.flagrantFouls)).setText("Flagrant Fouls: "+stats.getflagrant());
		
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}
}
